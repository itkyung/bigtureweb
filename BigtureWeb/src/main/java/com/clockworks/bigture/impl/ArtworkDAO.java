package com.clockworks.bigture.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import com.clockworks.bigture.IArtworkDAO;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ArtworkComment;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.SpamLog;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.User;

@Repository
public class ArtworkDAO implements IArtworkDAO {

	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	
	@Override
	public List<Artwork> findArtworks(User owner, ShareType share,
			ArtworkType type, SortOption sort, int start, int limits) {
		
		StringBuffer sql = new StringBuffer("FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		
		if(owner != null){
			sql.append("AND a.user = :user ");
			if(share != null && share.equals(ShareType.PUBLIC)){
				//신고된것은 검색하지 않는다.
				sql.append("AND a.reportCount = 0 ");
			}
		}else{
			//신고된것은 검색하지 않는다.
			sql.append("AND a.reportCount = 0 ");
		}
		
		if(share != null){
			sql.append("AND a.shareType = :shareType ");
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				//Normal과 Contest를 같이 조회한다.
				sql.append("AND (a.type = :type1 OR a.type = :type2) ");
			}else{
				sql.append("AND a.type = :type ");
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
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		if(owner != null){
			query.setParameter("user", owner);
		}
		if(share != null){
			query.setParameter("shareType", share);
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				query.setParameter("type1", ArtworkType.NORMAL);
				query.setParameter("type2", ArtworkType.CONTEST);
			}else{
				query.setParameter("type", type);
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limits);
		//query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public int countUserArtworks(User owner, ShareType share, ArtworkType type) {
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		
		if(owner != null){
			sql.append("AND a.user = :user ");
			if(share != null && share.equals(ShareType.PUBLIC)){
				//신고된것은 검색하지 않는다.
				sql.append("AND a.reportCount = 0 ");
			}
		}else{
			//신고된것은 검색하지 않는다.
			sql.append("AND a.reportCount = 0 ");
		}
		
		if(share != null){
			sql.append("AND a.shareType = :shareType ");
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				//Normal과 Contest를 같이 조회한다.
				sql.append("AND (a.type = :type1 OR a.type = :type2) ");
			}else{
				sql.append("AND a.type = :type ");
			}
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		if(owner != null){
			query.setParameter("user", owner);
		}
		if(share != null){
			query.setParameter("shareType", share);
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				query.setParameter("type1", ArtworkType.NORMAL);
				query.setParameter("type2", ArtworkType.CONTEST);
			}else{
				query.setParameter("type", type);
			}
		}
		
		return ((Number)query.getSingleResult()).intValue();
	}
	
	@Override
	public Artwork loadArtwork(Long id) {
		Query query = em.createQuery("FROM Artwork a WHERE a.id = :id");
		query.setParameter("id", id);
		//query.setHint("org.hibernate.cacheable", true);
		
		return (Artwork)query.getSingleResult();
	}


	@Override
	public List<ArtworkCollection> findCollection(User owner, SortOption sort,
			int start, int limits) {
		
		StringBuffer sql = new StringBuffer("FROM ArtworkCollection a WHERE a.user = :owner AND a.artwork.shareType = :shareType " +
				"AND a.artwork.blockFlag = :blockFlag AND a.artwork.deleted = :deleted ");
		
		
		switch (sort) {
		case RECENT:
			sql.append("Order by a.created desc");
			break;
		case POPULAR:
			sql.append("Order by a.artwork.score desc,a.created desc");
			break;
		case LOVE:
			sql.append("Order by a.artwork.loveCount desc,a.created desc");
			break;
		case AWESOME:
			sql.append("Order by a.artwork.awesomeCount desc,a.created desc");
			break;
		case WOW:
			sql.append("Order by a.artwork.wowCount desc,a.created desc");
			break;
		case FUN:
			sql.append("Order by a.artwork.funCount desc,a.created desc");
			break;
		case FANTASTIC:
			sql.append("Order by a.artwork.fantasticCount desc,a.created desc");
			break;
		default:
			sql.append("Order by a.created desc");
			break;
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("deleted", false);
		query.setParameter("owner", owner);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setParameter("blockFlag", false);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		//query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}


	/**
	 * Normal만 구한다.
	 */
	@Override
	public List<Artwork> findFriendsArtworks(User owner, SortOption sort,
			int start, int limits) {
		
		StringBuffer sql = new StringBuffer("SELECT a FROM Artwork a,Friends b WHERE a.user = b.friend AND b.owner = :owner " +
				"AND a.shareType = :shareType AND a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		sql.append("AND (a.type = :type1 OR a.type = :type2) ");
		sql.append("AND a.reportCount = 0 ");
		
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
		query.setParameter("deleted", false);
		query.setParameter("owner", owner);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setParameter("blockFlag", false);
		query.setParameter("type1", ArtworkType.NORMAL);
		query.setParameter("type2", ArtworkType.CONTEST);
		query.setFirstResult(start);
		query.setMaxResults(limits);
	//	query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}


	@Override
	public List<ArtworkComment> findComments(Artwork artwork, int start,
			int limits) {
		
		Query query = em.createQuery("FROM ArtworkComment a WHERE a.artwork = :artwork Order by a.created desc");
		query.setParameter("artwork", artwork);
		query.setFirstResult(start);
		query.setMaxResults(limits);
	//	query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}


	@Override
	public Collection<ArtworkComment> findUniqueComments(Artwork artwork, int start,
			int limits) {
		List<ArtworkComment> comments = findComments(artwork, start, limits);
		
		Map<Long,ArtworkComment> map = new HashMap<Long,ArtworkComment>();
		for(ArtworkComment comment : comments){
			if(!map.containsKey(comment.getUser().getId())){
				map.put(comment.getUser().getId(),comment);
			}
		}
		
		return map.values();
	}


	@Override
	public List<ArtworkComment> findUserComments(Artwork artwork, User owner,
			StickerType sticker) {
		StringBuffer sql = new StringBuffer("FROM ArtworkComment a WHERE a.user = :owner AND a.artwork = :artwork ");
		if(sticker != null){
			sql.append("AND a.sticker = :sticker ");
		}
		sql.append("Order by a.created desc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner",owner);
		query.setParameter("artwork", artwork);
		if(sticker != null){
			query.setParameter("sticker", sticker);
		}
	//	query.setHint("org.hibernate.cacheable", true);
		
		
		return query.getResultList();
	}


	@Override
	public void createComment(ArtworkComment comment) {
		comment.setCreated(new Date());
		em.persist(comment);
	}


	@Override
	public ArtworkComment findSticker(Artwork artwork, User owner) {
		Query query = em.createQuery("FROM ArtworkComment a WHERE a.artwork = :artwork AND a.user = :user AND a.sticker != :sticker");
		query.setParameter("artwork", artwork);
		query.setParameter("user", owner);
		query.setParameter("sticker", StickerType.NONE);
	//	query.setHint("org.hibernate.cacheable", true);
		
		List<ArtworkComment> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}


	@Override
	public void createArtwork(Artwork artwork) {
		
		artwork.setDeleted(false);
		artwork.setCreated(new Date());
		em.persist(artwork);
	}


	@Override
	public void updateArtwork(Artwork artwork) {
		em.merge(artwork);
	}


	


	@Override
	public int countArtworks(User owner, ShareType share, ArtworkType type) {
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		
		if(owner != null){
			sql.append("AND a.user = :user ");
		}
		if(share != null){
			sql.append("AND a.shareType = :shareType ");
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				//Normal과 Contest를 같이 조회한다.
				sql.append("AND (a.type = :type1 OR a.type = :type2) ");
			}else{
				sql.append("AND a.type = :type ");
			}
			
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		if(owner != null){
			query.setParameter("user", owner);
		}
		if(share != null){
			query.setParameter("shareType", share);
		}
		if(type != null){
			if(type.equals(ArtworkType.NORMAL)){
				query.setParameter("type1", ArtworkType.NORMAL);
				query.setParameter("type2", ArtworkType.CONTEST);
			}else{
				query.setParameter("type", type);
			}
		}

	//	query.setHint("org.hibernate.cacheable", true);
		Number number = (Number)query.getSingleResult();
		return number.intValue();
	}


	@Override
	public int countCollection(User owner) {
		
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM ArtworkCollection a WHERE a.user = :owner AND a.artwork.shareType = :shareType " +
				"AND a.artwork.blockFlag = :blockFlag AND a.artwork.deleted = :deleted ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("owner", owner);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		
	//	query.setHint("org.hibernate.cacheable", true);
		Number number = (Number)query.getSingleResult();
		return number.intValue();
	}


	@Override
	public int countFriendsArtworks(User owner) {
		StringBuffer sql = new StringBuffer("SELECT count(a) FROM Artwork a,Friends b WHERE a.user = b.friend AND b.owner = :owner " +
				"AND a.shareType = :shareType AND a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		
	//	query.setHint("org.hibernate.cacheable", true);
		
		Number number = (Number)query.getSingleResult();
		return number.intValue();
	}


	@Override
	public ArtworkCollection findCollection(User owner, Artwork artwork) {
		Query query = em.createQuery("FROM ArtworkCollection a WHERE a.artwork = :artwork AND a.user = :owner");
		query.setParameter("artwork", artwork);
		query.setParameter("owner", owner);
	//	query.setHint("org.hibernate.cacheable", true);
		
		List<ArtworkCollection> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}


	@Override
	public void createCollection(ArtworkCollection collection) {
		collection.setCreated(new Date());
		em.persist(collection);
	}


	@Override
	public void removeCollection(ArtworkCollection collection) {
		em.remove(collection);
	}


	@Override
	public void createSpam(SpamLog spam) {
		spam.setCreated(new Date());
		em.persist(spam);
	}


	@Override
	public ArtworkComment loadComment(Long id) {
		Query query = em.createQuery("FROM " + ArtworkComment.class.getName() + " a WHERE a.id = :id");
		query.setParameter("id", id);
		query.setHint("org.hibernate.cacheable", true);
		
		List<ArtworkComment> results = query.getResultList();
		return results.size() == 0 ? null : results.get(0);
	}


	@Override
	public void delete(ArtworkComment comment) {
		em.remove(comment);
	}

	@Override
	public List<Artwork> findSplashArtworks() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -5);
		
		StringBuffer sql = new StringBuffer("FROM Artwork a WHERE a.blockFlag = :blockFlag AND a.deleted = :deleted ");
		
		
		//신고된것은 검색하지 않는다.
		sql.append("AND a.reportCount = 0 ");
		sql.append("AND a.shareType = :shareType ");
		sql.append("AND a.created <= :targetDate ");
		sql.append("Order by a.created desc");
		
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("blockFlag", false);
		query.setParameter("deleted", false);
		query.setParameter("shareType", ShareType.PUBLIC);
		query.setParameter("targetDate", date.getTime());
		query.setFirstResult(0);
		query.setMaxResults(20);
		//query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}


	
	
}
