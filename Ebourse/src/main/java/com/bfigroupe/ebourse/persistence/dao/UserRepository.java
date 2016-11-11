package com.bfigroupe.ebourse.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

	List<User> findByEmailIgnoreCaseContaining(String email);

	@Override
	void delete(User user);

}
