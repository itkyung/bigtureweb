package com.clockworks.bigture.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.clockworks.bigture.IContestDAO;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ContestRank;
import com.clockworks.bigture.entity.ContestStatus;
import com.clockworks.bigture.entity.User;

@Repository
public class ContestDAO implements IContestDAO {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@Override
	public List<Contest> listActiveContest(int start,int limits) {
		StringBuffer sql = new StringBuffer("FROM Contest a WHERE a.deleted = :deleted AND a.status = :status Order by a.startTime desc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("deleted", false);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		//query.setParameter("today", cal.getTime());
		query.setParameter("status", ContestStatus.ING);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	
	
	
	@Override
	public List<Contest> listAllContest(int start, int limits) {
		StringBuffer sql = new StringBuffer("FROM Contest a WHERE a.deleted = :deleted Order by a.endTime desc,a.startTime desc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("deleted", false);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}




	@Override
	public int countAllContest() {
		Query query = em.createQuery("SELECT count(*) FROM Contest a WHERE a.deleted = :deleted");
		query.setParameter("deleted", false);
		query.setHint("org.hibernate.cacheable", true);
		
		Number number = (Number)query.getSingleResult();
		return number.intValue();
	
	}




	@Override
	public Contest loadContest(Long id) {
		
		Query query = em.createQuery("FROM Contest a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		return (Contest)query.getSingleResult();
	}

	@Override
	public void createContest(Contest contest) {
		contest.setDeleted(false);
		contest.setCreated(new Date());
		em.persist(contest);
	}

	@Override
	public void updateContest(Contest contest) {
		em.merge(contest);
		
	}




	@Override
	public List<Artwork> listContestArtworks(Contest contest, int start,
			int limits, SortOption sort) {
		StringBuffer sql = new StringBuffer("FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted AND a.contest = :contest ");
		
		switch (sort) {
		case RECENT:
			sql.append("Order by a.created desc");
			break;
		case POPULAR:
			sql.append("Order by a.score desc,a.created desc");
			break;
		case LOVE:
			sql.append("Order by a.loveCount desc,a.created desc");
			break;
		case AWESOME:
			sql.append("Order by a.awesomeCount desc,a.created desc");
			break;
		case WOW:
			sql.append("Order by a.wowCount desc,a.created desc");
			break;
		case FUN:
			sql.append("Order by a.funCount desc,a.created desc");
			break;
		case FANTASTIC:
			sql.append("Order by a.fantasticCount desc,a.created desc");
			break;
		default:
			sql.append("Order by a.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		query.setParameter("contest", contest);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}




	@Override
	public int countContestArtworks(Contest contest) {
		Query query = em.createQuery("SELECT count(*) FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted AND a.contest = :contest ");
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		query.setParameter("contest", contest);
		query.setHint("org.hibernate.cacheable", true);
		
		Number number = (Number)query.getSingleResult();
		return number.intValue();
	}


	@Override
	public List<ContestRank> listWinner(Contest contest) {
		Query query = em.createQuery("FROM ContestRank a WHERE a.contest = :contest Order by a.rank asc");
		query.setParameter("contest", contest);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}




	@Override
	public List<User> findMembers(Contest contest) {
		StringBuffer sql = new StringBuffer("SELECT distinct(a.user) FROM Artwork a WHERE a.deleted = :deleted AND a.contest = :contest ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("deleted", false);
		query.setParameter("contest", contest);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	
}
