package com.clockworks.bigture.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.clockworks.bigture.IPostCardDAO;
import com.clockworks.bigture.delegate.PostCardDelegate;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.PostCardReceiver;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.User;

@Repository
public class PostCardDAO implements IPostCardDAO {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@Override
	public List<PostCardDelegate> getSendedPostCards(User owner, int start, int limits,boolean myPage) {
		StringBuffer sql = new StringBuffer("From PostCard a WHERE a.owner = :owner and a.deleted = :deleted ");
		
		if(!myPage){
			sql.append("AND a.shareType = :shareType ");
		}
		sql.append("Order by a.created desc");
		Query query = em.createQuery(sql.toString());
			
		query.setParameter("owner", owner);
		query.setParameter("deleted", false);
		if(!myPage){
			query.setParameter("shareType", ShareType.PUBLIC);
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return convertToDelegate(query.getResultList());
	}

	@Override
	public List<PostCardDelegate> getReceivedPostCards(User receiver, int start,
			int limits,boolean myPage) {
		StringBuffer sql = new StringBuffer("FROM PostCardReceiver a WHERE a.receiver = :receiver AND a.deleted = :deleted ");
		if(!myPage){
			sql.append("AND a.card.shareType = :shareType ");
		}
		
		sql.append("Order by a.created desc");
		Query query = em.createQuery(sql.toString());
		query.setParameter("receiver", receiver);
		query.setParameter("deleted", false);
		if(!myPage){
			query.setParameter("shareType", ShareType.PUBLIC);
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return convertReceiverToDelegate(query.getResultList());
	}
	
	@Override
	public List<PostCardDelegate> getAllPostCards(User user, int start,
			int limits) {
		StringBuffer sql = new StringBuffer("SELECT a FROM PostCard a left join fetch a.receivers as b WHERE (b.receiver := receiver OR a.owner = :owner) AND a.shareType = :shareType Order by a.created");
		Query query = em.createQuery(sql.toString());
		query.setParameter("receiver", user);
		query.setParameter("owner", user);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return convertToDelegate(query.getResultList());
	}

	private List<PostCardDelegate> convertToDelegate(List<PostCard> results){
		List<PostCardDelegate> cards = new ArrayList<PostCardDelegate>();
		for(PostCard c : results){
			PostCardDelegate card = new PostCardDelegate(c,true);
			card.setViewed(true);
			card.setViewDate(card.getCreated());
			cards.add(card);
		}
		return cards;
	}
	
	private List<PostCardDelegate> convertReceiverToDelegate(List<PostCardReceiver> results){
		List<PostCardDelegate> cards = new ArrayList<PostCardDelegate>();
		for(PostCardReceiver c : results){
			PostCardDelegate card = new PostCardDelegate(c.getCard(),true);
			card.setViewed(c.isViewed());
			card.setViewDate(c.getViewDate() == null ? 0 : c.getViewDate().getTime());
			cards.add(card);
		}
		return cards;
	}

	@Override
	public void createCard(PostCard card) {
		card.setCreated(new Date());
		em.persist(card);
	}

	@Override
	public void updateCard(PostCard card) {
		em.merge(card);
	}

	@Override
	public PostCard loadCard(Long id) {
		Query query = em.createQuery("FROM PostCard a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		return (PostCard)query.getSingleResult();
	}

	
	
}
