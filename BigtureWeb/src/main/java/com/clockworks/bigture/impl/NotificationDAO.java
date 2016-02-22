package com.clockworks.bigture.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.clockworks.bigture.INotificationDAO;
import com.clockworks.bigture.entity.Notice;
import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.User;


@Repository
public class NotificationDAO implements INotificationDAO {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@Override
	public void create(Notification entity) {
		entity.setCreated(new Date());
		entity.setCreatedTime(entity.getCreated().getTime());
		em.persist(entity);
	}

	@Override
	public void update(Notification entity) {
		em.merge(entity);
	}

	@Override
	public List<Notification> listNotification(User owner, int start, int limits) {
		Query query = em.createQuery("FROM Notification a WHERE a.owner = :owner AND a.deleted = :deleted Order by a.created desc");
		query.setParameter("owner", owner);
		query.setParameter("deleted", false);
		query.setHint("org.hibernate.cacheable", true);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		
		return query.getResultList();
	}

	@Override
	public Notification load(Long id) {
		Query query = em.createQuery("FROM Notification a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		return (Notification)query.getSingleResult();
	}

	@Override
	public void updateReadFlag(User owner) {
		Session session = (Session)em.unwrap(Session.class);
		SQLQuery sqlQuery = session.createSQLQuery("UPDATE bt_notification a SET a.read_flag = :readFlag, a.read_date = :date  WHERE a.owner_fk = :userId")
				.addEntity(Notification.class);
		sqlQuery.setParameter("userId", owner.getId());
		sqlQuery.setParameter("readFlag", true);
		sqlQuery.setParameter("date",new Date());
		sqlQuery.executeUpdate();
	}

	
	@Override
	public void updateDeleteFlag(User owner, String createString) {
		Session session = (Session)em.unwrap(Session.class);
		SQLQuery sqlQuery = session.createSQLQuery("UPDATE bt_notification a SET a.deleted = :deleteFlag, a.delete_date = :date  WHERE a.owner_fk = :userId AND DATE_FORMAT(a.created,'%m-%d-%Y') = :dateStr")
				.addEntity(Notification.class);
		sqlQuery.setParameter("userId", owner.getId());
		sqlQuery.setParameter("deleteFlag", true);
		sqlQuery.setParameter("date",new Date());
		sqlQuery.setParameter("dateStr", createString);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public List<Notice> listNotice(String language) {
		Query query = em.createQuery("FROM Notice a WHERE a.language = :language Order by a.created desc");
		query.setParameter("language", language);
		//query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	
}
