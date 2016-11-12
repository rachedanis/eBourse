package com.bfigroupe.ebourse.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.PasswordResetTokenRepository;
import com.bfigroupe.ebourse.persistence.dao.RoleRepository;
import com.bfigroupe.ebourse.persistence.dao.UserRepository;
import com.bfigroupe.ebourse.persistence.dao.VerificationTokenRepository;
import com.bfigroupe.ebourse.persistence.model.PasswordResetToken;
import com.bfigroupe.ebourse.persistence.model.Role;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.VerificationToken;
import com.bfigroupe.ebourse.web.dto.UserDto;
import com.bfigroupe.ebourse.web.error.UserAlreadyExistException;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	@Override
	public List<User> findByEmailLike(String email) {
		return repository.findByEmailIgnoreCaseContaining(email);
	}

	@Override
	public User findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public User registerNewUserAccount(final UserDto accountDto) {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
		}
		final User user = new User();

		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setCin(accountDto.getCin());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());

		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		return repository.save(user);
	}
	
	@Override
	public User registerNewAccount(final UserDto accountDto,final List<Role> roles) {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
		}
		final User user = new User();
		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setCin(accountDto.getCin());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());
		user.setRoles(roles);
		return repository.save(user);
	}

	@Override
	public User getUser(final String verificationToken) {
		final User user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Override
	public void saveRegisteredUser(final User user) {
		repository.save(user);
	}

	@Override
	public void deleteUser(final User user) {
		VerificationToken verificationToken = tokenRepository.findByUser(user);

		if (verificationToken != null)
			tokenRepository.delete(verificationToken);

		PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

		if (passwordToken != null)
			passwordTokenRepository.delete(passwordToken);

		repository.delete(user);
	}

	@Override
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	@Override
	public User findUserByEmail(final String email) {
		return repository.findByEmail(email);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token);
	}

	@Override
	public User getUserByPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token).getUser();
	}

	@Override
	public User getUserByID(final long id) {
		return repository.findOne(id);
	}

	@Override
	public void changeUserPassword(final User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		repository.save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return TOKEN_EXPIRED;
		}

		user.setConfirmed(true);
		tokenRepository.delete(verificationToken);
		repository.save(user);
		return null;
	}

	private boolean emailExist(final String email) {
		final User user = repository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void save(User user) {
		repository.save(user);
	}

}