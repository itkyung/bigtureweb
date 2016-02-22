package com.clockworks.bigture.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.clockworks.bigture.IStoryDAO;
import com.clockworks.bigture.SimpleSortOption;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryAfterReading;
import com.clockworks.bigture.entity.StoryCollection;
import com.clockworks.bigture.entity.StoryMember;
import com.clockworks.bigture.entity.StoryPage;
import com.clockworks.bigture.entity.StoryPageArtwork;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.User;
import com.opensymphony.module.sitemesh.Page;

@Repository
public class StoryDAO implements IStoryDAO {
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
		
	@Override
	public List<Story> findStory(User owner, boolean justOwned,
			StoryStatus status, SortOption sort, int start, int limit,boolean excludeDraft) {
		StringBuffer sql = new StringBuffer("");
		
		if(justOwned){
			sql.append("FROM Story a WHERE a.owner = :owner ");
		}else{
			sql.append("SELECT distinct(a) FROM Story a LEFT OUTER JOIN a.members as b WHERE (a.owner = :owner OR b.member = :member) ");
		}
		
		if(excludeDraft){
			sql.append("AND a.status != :status1 AND a.status != :status2 ");
		}else{
			if(status != null){
				sql.append("AND a.status = :status ");
			}else{
				sql.append("AND a.status != :status ");
			}
		}

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
		
		if(justOwned){
			query.setParameter("owner", owner);
		}else{
			query.setParameter("owner", owner);
			query.setParameter("member", owner);
		}
		
		if(excludeDraft){
			query.setParameter("status1", StoryStatus.DRAFT);
			query.setParameter("status2", StoryStatus.DELETED);
		}else{
			if(status != null){
				query.setParameter("status", status);
			}else{
				query.setParameter("status", StoryStatus.DELETED);
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<StoryMember> findStoryMember(User owner) {

		Query query = em.createQuery("FROM StoryMember a WHERE a.member = :owner");
		query.setParameter("owner", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<StoryCollection> findCollection(User owner) {
		Query query = em.createQuery("FROM StoryCollection a WHERE a.user = :owner");
		query.setParameter("owner",owner);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<Story> findOwnedStory(User owner, List<StoryStatus> states,
			SimpleSortOption sort, int start, int limit) {
		
		StringBuffer sql = new StringBuffer("FROM Story a WHERE a.owner = :owner ");
		
		sql.append("AND a.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		
		switch (sort) {
		case RECENT:
			sql.append(") Order by a.created desc");
			break;
		
		default:
			sql.append(") Order by a.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<Story> findJoinedStory(User owner, List<StoryStatus> states,
			SimpleSortOption sort, int start, int limit) {
		StringBuffer sql = new StringBuffer("SELECT a.story FROM StoryMember a WHERE a.member = :owner ");
		sql.append("AND a.story.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		switch (sort) {
		case RECENT:
			sql.append(") Order by a.story.created desc");
			break;
		
		default:
			sql.append(") Order by a.story.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<Story> findCollectedStory(User owner, List<StoryStatus> states,
			SimpleSortOption sort, int start, int limit) {
		StringBuffer sql = new StringBuffer("SELECT a.story FROM StoryCollection a WHERE a.user = :owner ");
		sql.append("AND a.story.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		switch (sort) {
		case RECENT:
			sql.append(") Order by a.story.created desc");
			break;
		
		default:
			sql.append(") Order by a.story.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<Story> listAllStory(List<StoryStatus> states,
			SimpleSortOption sort, int start, int limit) {
		
		StringBuffer sql = new StringBuffer("FROM Story a WHERE ");
		
		sql.append(" a.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		
		switch (sort) {
		case RECENT:
			sql.append(") Order by a.created desc");
			break;
		
		default:
			sql.append(") Order by a.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}



	@Override
	public int countAllStory(List<StoryStatus> states) {

		StringBuffer sql = new StringBuffer("SELECT count(a) FROM Story a WHERE ");
		
		sql.append(" a.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		sql.append(")");
		
		Query query = em.createQuery(sql.toString());
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
	
		query.setHint("org.hibernate.cacheable", true);
		
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public int countOwnedStory(User owner, List<StoryStatus> states) {
		
		StringBuffer sql = new StringBuffer("SELECT count(a) FROM Story a WHERE a.owner = :owner ");
		
		sql.append("AND a.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		sql.append(")");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setHint("org.hibernate.cacheable", true);
		
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public int countJoinedStory(User owner, List<StoryStatus> states) {
		StringBuffer sql = new StringBuffer("SELECT count(a.story) FROM StoryMember a WHERE a.member = :owner ");
		sql.append("AND a.story.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		sql.append(")");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
		
		query.setHint("org.hibernate.cacheable", true);
		
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public int countCollectedStory(User owner, List<StoryStatus> states) {
		
		StringBuffer sql = new StringBuffer("SELECT count(a.story) FROM StoryCollection a WHERE a.user = :owner ");
		sql.append("AND a.story.status in (");
		
		int i=1;
		for(StoryStatus s : states){
			sql.append(":status"+i);
			if(i < states.size())
				sql.append(",");
			
			i++;
		}
		sql.append(")");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		i = 1;
		for(StoryStatus s : states){
			query.setParameter("status" + i, s);
			i++;
		}
	
		query.setHint("org.hibernate.cacheable", true);
		
		Number n = (Number)query.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public Story loadStory(Long storyId) {
		Query query = em.createQuery("FROM Story a WHERE a.id = :id");
		query.setParameter("id", storyId);
		query.setHint("org.hibernate.cacheable", true);
		
		return (Story)query.getSingleResult();
	}

	@Override
	public void createStory(Story story) {
		story.setCreated(new Date());
		em.persist(story);
	}

	@Override
	public void updateStory(Story story) {
		em.merge(story);
		
	}

	@Override
	public void createCollection(StoryCollection collection) {
		collection.setCreated(new Date());
		em.persist(collection);
	}

	@Override
	public void deleteCollection(StoryCollection collection) {
		em.remove(collection);
	}

	@Override
	public StoryCollection getCollection(Story story, User owner) {
		Query query = em.createQuery("FROM StoryCollection a WHERE a.story = :story AND a.user = :owner");
		query.setParameter("story", story);
		query.setParameter("owner", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		List<StoryCollection> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}

	@Override
	public List<StoryPage> listPages(Story story) {
		Query query = em.createQuery("FROM StoryPage a WHERE a.story = :story Order by a.pageNo asc");
		query.setParameter("story", story);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<StoryPageArtwork> listPageArtworks(Story story) {
		Query query = em.createQuery("FROM StoryPageArtwork a WHERE a.pk.page.story = :story Order by a.pk.page.pageNo asc");
		query.setParameter("story", story);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public void createAfterReading(StoryAfterReading entity) {
		em.persist(entity);
	}

	@Override
	public void createPageArtworks(StoryPageArtwork pa) {
		pa.setCreated(new Date());
		em.persist(pa);
	}

	@Override
	public List<StoryPageArtwork> listPageArtworks(StoryPage page) {
		Query query = em.createQuery("FROM StoryPageArtwork a WHERE a.pk.page = :page Order by a.pk.page.pageNo asc");
		query.setParameter("page", page);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public StoryPage loadPage(Long pageId) {
		Query query = em.createQuery("From StoryPage a WHERE a.id = :id");
		query.setParameter("id", pageId);
		query.setHint("org.hibernate.cacheable", true);
		
		return (StoryPage)query.getSingleResult();
	}

	@Override
	public List<StoryAfterReading> listAfterReading(Story story) {
		Query query = em.createQuery("From StoryAfterReading a WHERE a.story  = :story Order by a.created desc");
		query.setParameter("story", story);
		query.setHint("org.hibernate.cacheable", true);
		return query.getResultList();
	}

	
	
}
