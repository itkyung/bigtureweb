package com.clockworks.bigture.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.clockworks.bigture.SimpleSortOption;
import com.clockworks.bigture.FindClassType;
import com.clockworks.bigture.IArtClassDAO;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassCollection;
import com.clockworks.bigture.entity.ArtClassMember;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.ClassShare;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.PuzzleStatus;
import com.clockworks.bigture.entity.User;

@Repository
public class ArtClassDAO implements IArtClassDAO {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@Override
	public List<ArtClass> findClass(User user, int start, int limit,
			boolean onlyOpened, List<ClassStatus> statusList) {
		StringBuffer sql = new StringBuffer("SELECT a FROM ArtClass a LEFT OUTER JOIN a.members as b WHERE (a.owner = :owner OR b.member = :member) ");
		if(onlyOpened){
			sql.append("AND a.shareType = :shareType ");
		}
		
		sql.append("AND a.status in (");
		
		int i=1;
		for(ClassStatus s : statusList){
			sql.append(":status"+i);
			if(i < statusList.size())
				sql.append(",");
			
			i++;
		}
		
		sql.append(") Order by a.created desc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", user);
		query.setParameter("member", user);
		if(onlyOpened)
			query.setParameter("shareType", ClassShare.OPEN);
		
		i = 1;
		for(ClassStatus s : statusList){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<ArtClassCollection> findCollection(User user) {
		Query query = em.createQuery("FROM ArtClassCollection a WHERE a.user = :user");
		query.setParameter("user",user);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<ArtClass> findCollection(User user,List<ClassStatus> statusList,int start,int limit){
		StringBuffer sql = new StringBuffer("SELECT a.artClass FROM ArtClassCollection a WHERE a.user = :user ");
		
		sql.append("AND a.artClass.status in (");
		int i=1;
		for(ClassStatus s : statusList){
			sql.append(":status"+i);
			if(i < statusList.size())
				sql.append(",");
			
			i++;
		}
		sql.append(") ");
	
		Query query = em.createQuery(sql.toString());
		query.setParameter("user",user);
		
		i = 1;
		for(ClassStatus s : statusList){
			query.setParameter("status" + i, s);
			i++;
		}
		
		
		query.setHint("org.hibernate.cacheable", true);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		
		
		return query.getResultList();
	}
	
	
	@Override
	public List<ArtClassMember> findMember(User owner) {
		Query query = em.createQuery("FROM ArtClassMember a WHERE a.member = :member");
		query.setParameter("member",owner);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<ArtClass> findClass(User user, int start, int limit,
			FindClassType findType, List<ClassStatus> statusList, SimpleSortOption sortOption) {
		
		StringBuffer sql = new StringBuffer("SELECT a FROM ArtClass a ");
		switch (findType) {
		case OWN:
			sql.append("WHERE a.owner = :owner ");
			break;
		case JOINED:
			sql.append("LEFT OUTER JOIN a.members as b WHERE b.member = :member ");
			break;
		case ALL:
			sql.append("WHERE a.created <= :today ");
			break;
		default:
			break;
		}
		
		sql.append("AND a.status in (");
		int i=1;
		for(ClassStatus s : statusList){
			sql.append(":status"+i);
			if(i < statusList.size())
				sql.append(",");
			
			i++;
		}
		sql.append(") ");
		
	
		
		switch (sortOption) {
		case RECENT:
			sql.append("ORDER BY a.created desc");
			break;
		case CLASSNAME:
			sql.append("ORDER BY a.className asc");
			break;
		case EXPERTNAME:
			sql.append("ORDER BY a.owner.nickName asc");
			break;
		default:
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		switch (findType) {
		case OWN:
			query.setParameter("owner", user);
			break;
		case JOINED:
			query.setParameter("member", user);
			break;
		case ALL:
			query.setParameter("today", new Date());
			break;
		default:
			break;
		}
		
		i = 1;
		for(ClassStatus s : statusList){
			query.setParameter("status" + i, s);
			i++;
		}
		
		
		query.setHint("org.hibernate.cacheable", true);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

	@Override
	public int countClass(User user, FindClassType findType, List<ClassStatus> statusList) {
		
		StringBuffer sql = new StringBuffer("SELECT count(a) FROM ArtClass a ");
		switch (findType) {
		case OWN:
			sql.append("WHERE a.owner = :owner ");
			break;
		case JOINED:
			sql.append("LEFT OUTER JOIN a.members as b WHERE b.member = :member ");
			break;
		case ALL:
			sql.append("WHERE a.created <= :today ");
			break;
		default:
			break;
		}
		
		sql.append("AND a.status in (");
		int i=1;
		for(ClassStatus s : statusList){
			sql.append(":status"+i);
			if(i < statusList.size())
				sql.append(",");
			
			i++;
		}
		sql.append(") ");
		
		Query query = em.createQuery(sql.toString());
		switch (findType) {
		case OWN:
			query.setParameter("owner", user);
			break;
		case JOINED:
			query.setParameter("member", user);
			break;
		case ALL:
			query.setParameter("today", new Date());
			break;
		default:
			break;
		}
		i = 1;
		for(ClassStatus s : statusList){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setHint("org.hibernate.cacheable", true);
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public int countCollectedClass(User user, List<ClassStatus> statusList) {
		StringBuffer sql = new StringBuffer("SELECT count(a) FROM ArtClassCollection a WHERE a.user = :user ");
		
		sql.append("AND a.artClass.status in (");
		int i=1;
		for(ClassStatus s : statusList){
			sql.append(":status"+i);
			if(i < statusList.size())
				sql.append(",");
			
			i++;
		}
		sql.append(") ");
	
		Query query = em.createQuery(sql.toString());
		query.setParameter("user",user);
		
		i = 1;
		for(ClassStatus s : statusList){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setHint("org.hibernate.cacheable", true);
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
		
	}

	@Override
	public void create(ArtClass artClass) {
		artClass.setCreated(new Date());
		em.persist(artClass);
	}

	@Override
	public void update(ArtClass artClass) {
		em.merge(artClass);
	}

	@Override
	public void create(ArtClassCollection collection) {
		collection.setCreated(new Date());
		em.persist(collection);
	}

	@Override
	public ArtClassCollection loadCollection(Long id) {
		Query query = em.createQuery("FROM ArtClassCollection a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		return (ArtClassCollection)query.getSingleResult();
	}

	@Override
	public void remove(ArtClassCollection collection) {
		em.remove(collection);
	}

	@Override
	public ArtClass loadClass(Long id) {
		Query query = em.createQuery("FROM ArtClass a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		return (ArtClass)query.getSingleResult();
	}

	@Override
	public ArtClassCollection findCollection(User user, ArtClass artClass) {
		Query query = em.createQuery("FROM ArtClassCollection a WHERE a.user = :user AND a.artClass = :artClass");
		query.setParameter("user", user);
		query.setParameter("artClass", artClass);
		query.setHint("org.hibernate.cacheable", true);
		
		List<ArtClassCollection> result = query.getResultList();
		if(result.size() > 0){
			return result.get(0);
		}
		
		return null;
	}

	
	@Override
	public List<User> findJoinedMembers(ArtClass artClass) {
		Query query = em.createQuery("SELECT distinct(a.member) FROM ArtClassMember a WHERE a.artClass = :artClass Order by a.member.nickName asc");
		query.setParameter("artClass", artClass);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public boolean isJoined(ArtClass artClass, User owner) {
		Query query = em.createQuery("SELECT count(a) FROM ArtClassMember a WHERE a.artClass = :artClass AND a.member = :member");
		query.setParameter("artClass", artClass);
		query.setParameter("member", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		Number number = (Number)query.getSingleResult();
		if(number.intValue() > 0) return true;
		
		return false;
	}

	@Override
	public List<Artwork> listClassArtworks(ArtClass artClass, int start,
			int limits, SortOption sort) {
		StringBuffer sql = new StringBuffer("FROM Artwork a WHERE a.artClass = :artClass AND a.type = :type ");
		switch (sort) {
		case RECENT:
			sql.append("Order by a.created desc");
			break;

		default:
			break;
		}
		
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("artClass", artClass);
		query.setParameter("type", ArtworkType.CLASS);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public int countClassArtworks(ArtClass artClass) {
		Query query = em.createQuery("SELECT count(a) FROM Artwork a WHERE a.artClass = :artClass AND a.type = :type");
		query.setParameter("artClass", artClass);
		query.setHint("org.hibernate.cacheable", true);
		query.setParameter("type", ArtworkType.CLASS);
		
		Number number = (Number)query.getSingleResult();
		
		return number.intValue();
	}

	@Override
	public ArtClassPuzzle loadPuzzle(Long id) {
		Query query = em.createQuery("From ArtClassPuzzle a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		return (ArtClassPuzzle)query.getSingleResult();
	}

	@Override
	public void createPuzzle(ArtClassPuzzle puzzle) {
		puzzle.setCreated(new Date());
		em.persist(puzzle);
	}

	@Override
	public void updatePuzzle(ArtClassPuzzle puzzle) {
		em.merge(puzzle);
		
	}

	@Override
	public List<ArtClassPuzzle> listPuzzle(ArtClass artClass) {
		Query query = em.createQuery("From ArtClassPuzzle a WHERE a.artClass = :artClass Order by a.created desc");
		query.setParameter("artClass", artClass);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<ArtClassPuzzleArtwork> listPuzzleArtworks(ArtClass artClass,
			ArtClassPuzzle puzzle) {
		Query query = em.createQuery("From ArtClassPuzzleArtwork a WHERE a.puzzle = :puzzle AND a.status != :status Order by a.created desc");
		query.setParameter("puzzle", puzzle);
		query.setParameter("status", PuzzleStatus.CANCEL);
		query.setHint("org.hibernate.cacheable", true);
		
		
		return query.getResultList();
	}
	
	
	
}
