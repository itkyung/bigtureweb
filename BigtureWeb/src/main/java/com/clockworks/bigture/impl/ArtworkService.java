package com.clockworks.bigture.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.IArtClassDAO;
import com.clockworks.bigture.IArtworkDAO;
import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.IContestDAO;
import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.IPostCardDAO;
import com.clockworks.bigture.IStoryDAO;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.common.NotActiveException;
import com.clockworks.bigture.common.NotYourArtworkException;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ArtworkRegistModel;
import com.clockworks.bigture.delegate.CommentDelegate;
import com.clockworks.bigture.delegate.CommentWrapper;
import com.clockworks.bigture.delegate.RegionCommentCount;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ArtworkComment;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ContestStatus;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.SpamLog;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.IUserService;

@Service
public class ArtworkService implements IArtworkService {
	@Autowired private IArtworkDAO dao;
	@Autowired private IUserService userService;
	@Autowired private IContestDAO contestDao;
	@Autowired private IArtClassDAO classDao;
	@Autowired private IStoryDAO storyDao;
	@Autowired private IPostCardDAO cardDao;
	@Autowired private INotificationManager notiManager;
	
	@Override
	public List<ArtworkDelegate> findUserArtworks(User owner, User currentUser, boolean isMy,
			ArtworkType type, SortOption sort, int start, int limit) {
		
		List<ArtworkCollection> collection = dao.findCollection(currentUser, sort, 0, 300);
		List<Artwork> artworks = dao.findArtworks(owner, isMy ? null : ShareType.PUBLIC, type, sort,start,limit);
		
		return convertDelegate(artworks, collection, isMy, false);
	}

	@Override
	public List<ArtworkDelegate> findAllArtworks(User owner,ArtworkType type,
			SortOption sort, int start, int limit) {
		
		List<ArtworkCollection> collection = dao.findCollection(owner, sort, 0, 300);
		List<Artwork> artworks = dao.findArtworks(null, ShareType.PUBLIC, type, sort,start,limit);
		
		
		return convertDelegate(artworks, collection, false, false);
	}

	private List<ArtworkDelegate> convertDelegate(List<Artwork> artworks,List<ArtworkCollection> collection, boolean isMy, boolean isCollected){
		List<ArtworkDelegate> results = new ArrayList<ArtworkDelegate>();
		
		Map<Long,ArtworkCollection> collectMap = new HashMap<Long,ArtworkCollection>();
		
		if(collection != null){
			for(ArtworkCollection collect : collection){
				collectMap.put(collect.getArtwork().getId(),collect);
			}
		}
		
		for(Artwork artwork : artworks){
			ArtworkDelegate del = new ArtworkDelegate(artwork);
			if(collectMap.containsKey(artwork.getId()) || isCollected)
				del.setCollected(true);
			else
				del.setCollected(false);
			
			results.add(del);
		}
		
		return results;
	}
	
	@Override
	public List<ArtworkDelegate> findFriendsArtworks(User owner,
			SortOption sort, int start, int limits) {
		
		List<ArtworkCollection> collection = dao.findCollection(owner, sort, 0, 300);
		List<Artwork> artworks = dao.findFriendsArtworks(owner, sort, start, limits);
		
		return convertDelegate(artworks, collection, false, false);
	}

	@Override
	public List<ArtworkDelegate> findCollectedArtworks(User owner,
			SortOption sort, int start, int limits) {
		List<ArtworkDelegate> results = new ArrayList<ArtworkDelegate>();
		List<ArtworkCollection> artworkCollection = dao.findCollection(owner, sort, start, limits);
		
		for(ArtworkCollection collect : artworkCollection){
			ArtworkDelegate delegate = new ArtworkDelegate(collect.getArtwork());
			delegate.setCollected(true);
			results.add(delegate);
		}
		
		
		return results;
	}

	@Override
	public CommentWrapper getCommentInfo(User owner, Long artworkId,int start, int limits) {
		CommentWrapper wrapper = new CommentWrapper();
		wrapper.setSuccess(true);
		
		Artwork artwork = dao.loadArtwork(artworkId);
		
		ArtworkComment sticker = dao.findSticker(artwork, owner);
		if(sticker != null){
			wrapper.setAlreadySendSticker(true);
			wrapper.setSendedDate(sticker.getCreated().getTime());
			wrapper.setSendedSticker(sticker.getSticker().name());
		}else{
			wrapper.setAlreadySendSticker(false);
			wrapper.setSendedDate(0);
		}
		
		wrapper.setComments(convertComment(dao.findComments(artwork, start, limits)));
		
		return wrapper;
	}

	private List<CommentDelegate> convertComment(List<ArtworkComment> comments){
		List<CommentDelegate> results = new ArrayList<CommentDelegate>();
		
		for(ArtworkComment comment : comments){
			CommentDelegate delegate = new CommentDelegate(comment);
			results.add(delegate);
		}
		
		return results;
	}
	
	
	@Transactional
	@Override
	public void sendSticker(User owner, Long artworkId, StickerType sticker)
			throws Exception {
		//해당 artwork에 대해서 해당 사용자가 이미 스티커를 발행햇는지 여부를 확인한다.
		Artwork artwork = dao.loadArtwork(artworkId);
		ArtworkComment oldSticker = dao.findSticker(artwork, owner);
		if(oldSticker != null){
			//이미 스티커를 발행한경우.
			throw new Exception("Already issue");
		}
		
		ArtworkComment comment = new ArtworkComment();
		
		comment.setArtwork(artwork);
		comment.setUser(owner);
		comment.setSticker(sticker);
		
		dao.createComment(comment);
		
		synchronized (artwork) {
			switch(sticker){
			case TYPE_AWESOME:
				artwork.setAwesomeCount(artwork.getAwesomeCount() + 1);
				break;
			case TYPE_FANTASTIC:
				artwork.setFantasticCount(artwork.getFantasticCount() + 1);
				break;
			case TYPE_FUN:
				artwork.setFunCount(artwork.getFunCount() + 1);
				break;
			case TYPE_LOVE:
				artwork.setLoveCount(artwork.getLoveCount() + 1);
				break;
			case TYPE_WOW:
				artwork.setWowCount(artwork.getWowCount() + 1);
				break;
			}
			artwork.setScore(artwork.getScore()+1);
			dao.updateArtwork(artwork);
		}
		
		//해당 사용자의 통합 Sticker갯수가 증가시킨다.
		userService.updateStickerCount(artwork.getUser(), sticker);
		notiManager.writeComment(owner, artwork,true);
		
	}

	@Transactional
	@Override
	public void sendComment(User owner, Long artworkId, String contents) {
		ArtworkComment comment = new ArtworkComment();
		Artwork artwork = dao.loadArtwork(artworkId);
		comment.setArtwork(artwork);
		comment.setUser(owner);
		comment.setSticker(StickerType.NONE);
		comment.setComment(contents);
		
		dao.createComment(comment);
		notiManager.writeComment(owner, artwork,false);
	}

	
	@Override
	public int countUserArtworks(User owner, boolean isMy, ArtworkType type) {
		return dao.countUserArtworks(owner,  isMy ? null : ShareType.PUBLIC, type);
	}

	@Override
	public int countAllArtworks(ArtworkType type) {
		return dao.countArtworks(null,ShareType.PUBLIC, type);
	}

	@Override
	public int countFriendsArtworks(User owner) {
		return dao.countFriendsArtworks(owner);
	}

	@Override
	public int countCollectedArtworks(User owner) {
		return dao.countCollection(owner);
	}

	@Transactional
	@Override
	public boolean addCollection(User owner, Long artworkId) throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		ArtworkCollection collection = dao.findCollection(owner, artwork);
		if(collection != null){
			throw new Exception("Already collected");
		}
//		if(artwork.getUser().equals(owner)){
//			return false;
//		}
		
		collection = new ArtworkCollection();
		collection.setArtwork(artwork);
		collection.setUser(owner);
		dao.createCollection(collection);
		
		return true;
	}

	@Transactional
	@Override
	public void removeCollection(User owner, Long artworkId) throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		ArtworkCollection collection = dao.findCollection(owner, artwork);
		
		if(collection == null){
			throw new Exception("Collection does not exist");
		}
		
		dao.removeCollection(collection);
	}

	@Transactional
	@Override
	public void reportSpam(User user, Long artworkId) throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		
		artwork.setReportCount(artwork.getReportCount() + 1);
		artwork.setReportTime(new Date());
		dao.updateArtwork(artwork);
		
		SpamLog spam = new SpamLog();
		spam.setArtwork(artwork);
		spam.setOwner(user);
		dao.createSpam(spam);
	}

	@Transactional
	@Override
	public void editArtwork(User user, Long artworkId, String title,
			String comment, ShareType shareType) throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		
		if(artwork == null || 
				!artwork.getUser().equals(user)){
			throw new Exception("Artwork is not your artwork");
		}
		
		artwork.setTitle(title);
		artwork.setComment(comment);
		artwork.setShareType(shareType);
		dao.updateArtwork(artwork);
	}

	@Transactional
	@Override
	public void deleteArtwork(User user, Long artworkId) throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		if(artwork == null || 
				!artwork.getUser().equals(user)){
			throw new Exception("Artwork is not your artwork");
		}
		
		artwork.setDeleted(true);
		dao.updateArtwork(artwork);
	}

	@Transactional
	@Override
	public void requestContest(User user, Long artworkId, Long contestId)
			throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		if(artwork == null || 
				!artwork.getUser().equals(user)){
			throw new NotYourArtworkException();
		}
		
		Contest contest = contestDao.loadContest(contestId);
		if(contest == null || 
				!contest.isActive()){
			throw new NotActiveException();
		}
		
		artwork.setContest(contest);
		artwork.setReferenceId(contest.getId());
		artwork.setReferenceTitle(contest.getMainTitle());
		dao.updateArtwork(artwork);
		
	}
	
	@Transactional
	@Override
	public void cancelFromContest(User user, Long artworkId, Long contestId)
			throws Exception {
		Artwork artwork = dao.loadArtwork(artworkId);
		if(artwork == null || 
				!artwork.getUser().equals(user)){
			throw new NotYourArtworkException();
		}
		
		Contest contest = contestDao.loadContest(artwork.getReferenceId());
		
		if(contest == null || 
				!contest.isActive() || !contest.getStatus().equals(ContestStatus.ING)){
			throw new NotActiveException();
		}
		
		artwork.setContest(null);
		artwork.setReferenceId(null);
		artwork.setReferenceTitle(null);
		dao.updateArtwork(artwork);
	}

	@Override
	public Artwork loadArtwork(Long artworkId) {
		
		return dao.loadArtwork(artworkId);
	}

	@Override
	public List<ArtworkCollection> findAllCollection(User user) {
		return dao.findCollection(user, SortOption.RECENT, 0, 300);
	}

	@Transactional
	@Override
	public Artwork registArtwork(User user, ArtworkRegistModel model,
			ImageInfo image, String drawingPath) throws Exception {
		
		Artwork artwork = new Artwork();
		artwork.setUser(user);
		artwork.setOsType(OsType.valueOf(model.getOsType()));
		artwork.setTitle(model.getTitle());
		artwork.setComment(model.getComment());
		artwork.setPhoto(image.getPhotoPath());
		artwork.setThumbnail(image.getThumbnailPath());
		artwork.setType(ArtworkType.valueOf(model.getType()));
		artwork.setShareType(ShareType.valueOf(model.getShare()));
		
		artwork.setScore(0);
		artwork.setFantasticCount(0);
		artwork.setFunCount(0);
		artwork.setAwesomeCount(0);
		artwork.setBlockFlag(false);
		artwork.setReportCount(0);
		artwork.setLoveCount(0);
		artwork.setCommentsCount(0);
		artwork.setReferenceId(model.getRefId());
		artwork.setDrawingPath(drawingPath);
		
		if(model.getRefId() != null && model.getRefId() != 0){
			switch(artwork.getType()){
			case CLASS:
			case PUZZLE:
				artwork.setArtClass(classDao.loadClass(model.getRefId()));
				artwork.setReferenceTitle(artwork.getArtClass().getClassName());
				break;
			case STORY:
				artwork.setStory(storyDao.loadStory(model.getRefId()));
				artwork.setReferenceTitle(artwork.getStory().getTitle());
				break;
			case CONTEST:
				artwork.setContest(contestDao.loadContest(model.getRefId()));
				artwork.setReferenceTitle(artwork.getContest().getMainTitle());
				break;
			case POSTCARD:
				
				break;
			}
		}
		
		dao.createArtwork(artwork);
		
		return artwork;
	}

	@Transactional
	@Override
	public void updateArtwork(Artwork artwork) {
		dao.updateArtwork(artwork);
	}

	@Override
	public ArtworkDelegate getRandomArtwork() {
		List<Artwork> artworks = dao.findSplashArtworks();
		
		Random random = new Random();
		int idx = random.nextInt(artworks.size()-1);
		
		return new ArtworkDelegate(artworks.get(idx));
	}

	@Override
	public List<ArtworkDelegate> getSimpleArtwork(int limits) {
		
		List<Artwork> artworks = dao.findArtworks(null, ShareType.PUBLIC, ArtworkType.NORMAL, SortOption.RECENT,0,limits);
		return convertDelegate(artworks,null,false,false);
	}

	@Override
	public List<RegionCommentCount> getRegionCountComment(Long artworkId) {
		Artwork artwork = dao.loadArtwork(artworkId);
		Collection<ArtworkComment> comments = dao.findUniqueComments(artwork,0,150);
		Map<Long,RegionCommentCount> map = new HashMap<Long, RegionCommentCount>();
		for(ArtworkComment comment : comments){
			Long regionId = comment.getUser().getCountry().getRegion().getId();
			if(!map.containsKey(regionId)){
				RegionCommentCount entity = new RegionCommentCount();
				entity.setRegionId(regionId);
				entity.setCount(1);
				map.put(regionId, entity);
			}else{
				RegionCommentCount entity = map.get(regionId);
				entity.setCount(entity.getCount()+1);
			}
		}
		
		List<RegionCommentCount> results = new ArrayList<RegionCommentCount>();
		results.addAll(map.values());
		
		return results;
	}

	@Transactional
	@Override
	public void deleteCommentOrSticker(User user, Long commentId)
			throws Exception {
		ArtworkComment comment = dao.loadComment(commentId);
		if(comment.getUser().getId().equals(user.getId())){
			
			Artwork artwork = comment.getArtwork();
			synchronized (artwork) {
				switch(comment.getSticker()){
				case TYPE_AWESOME:
					artwork.setAwesomeCount(artwork.getAwesomeCount()-1);
					break;
				case TYPE_FANTASTIC:
					artwork.setFantasticCount(artwork.getFantasticCount()-1);
					break;
				case TYPE_FUN:
					artwork.setFunCount(artwork.getFunCount()-1);
					break;
				case TYPE_LOVE:
					artwork.setLoveCount(artwork.getLoveCount()-1);
					break;
				case TYPE_WOW:
					artwork.setWowCount(artwork.getWowCount()-1);
					break;
				
				}
				artwork.setScore(artwork.getScore()-1);
				dao.updateArtwork(artwork);
			}
			dao.delete(comment);
			
		}else{
			throw new Exception("No permission");
		}
		
	}

	
	
	
}
