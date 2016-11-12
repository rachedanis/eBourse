package com.bfigroupe.ebourse.service;

import java.util.List;

import com.bfigroupe.ebourse.persistence.model.PasswordResetToken;
import com.bfigroupe.ebourse.persistence.model.Role;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.VerificationToken;
import com.bfigroupe.ebourse.web.dto.UserDto;
import com.bfigroupe.ebourse.web.error.UserAlreadyExistException;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    List<User> getAll();
    
    List<User> findByEmailLike(String email);

    User findById(Long id);
    
    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

	void save(User user);

	User registerNewAccount(UserDto accountDto, List<Role> roles);

}
