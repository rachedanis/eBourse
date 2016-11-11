package com.bfigroupe.ebourse.persistence.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.Value;

public interface MarketValueRepository extends JpaRepository<MarketValue, Long>{
	
	public List<MarketValue> findByValueIsin(String isin);

	public List<MarketValue> findAll();

	public void deleteById(Long id);

	public List<MarketValue> findByValue(Value value);

	public List<MarketValue> findByDate(Date date);
	
	public List<MarketValue> findByDateBetween(Date startDate, Date endDate);

	public MarketValue findByValueIsinAndDate(String isin, Date date);
	
	public MarketValue findTopByValueIsinAndDateOrderByDateDesc(String isin, Date date);

	public MarketValue findTopByValueIsinOrderByDateDesc(String isin);
	
	public List<MarketValue> findByValueIsinAndDateBetween(String isin, Date startDate, Date endDate);

}
