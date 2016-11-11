package com.bfigroupe.ebourse.persistence.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.bfigroupe.ebourse.persistence.model.Request;

@NoRepositoryBean
public interface BaseRequestRepository<T extends Request> extends JpaRepository<T, Long> {

	public List<T> findAll();
	public List<T> findByDate(Date date);
}
