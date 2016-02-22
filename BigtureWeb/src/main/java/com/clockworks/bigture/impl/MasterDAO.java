package com.clockworks.bigture.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.clockworks.bigture.IMasterDAO;
import com.clockworks.bigture.entity.Country;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;

@Repository
public class MasterDAO implements IMasterDAO,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -57072286508575827L;

	private Log log = LogFactory.getLog(MasterDAO.class);
	
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@Override
	public List<Country> getCountries() {
		
		Query query = em.createQuery("FROM Country a Order by a.name desc");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public Country findByCode(String code) {
		
		Query query = em.createQuery("FROM Country a WHERE a.code = :code");
		query.setParameter("code", code);
		query.setHint("org.hibernate.cacheable", true);
		
		List<Country> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}

	@Override
	public void saveTempImage(TempImage img) {
		img.setCreated(new Date());
		em.persist(img);
	}

	@Override
	public TempImage loadTempImage(Long id) {
		try{
			Query query = em.createQuery("SELECT a from TempImage a WHERE a.id = :id");
			query.setParameter("id", id);
			return (TempImage)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	
	
}
