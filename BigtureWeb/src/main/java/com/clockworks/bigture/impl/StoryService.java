package com.clockworks.bigture.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.IArtworkDAO;
import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.IMasterDAO;
import com.clockworks.bigture.IStoryDAO;
import com.clockworks.bigture.IStoryService;
import com.clockworks.bigture.SimpleSortOption;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.common.ImagePathType;
import com.clockworks.bigture.delegate.AfterReadingDelegate;
import com.clockworks.bigture.delegate.StoryArtworkDelegate;
import com.clockworks.bigture.delegate.StoryDelegate;
import com.clockworks.bigture.delegate.StoryModel;
import com.clockworks.bigture.delegate.StoryPageDelegate;
import com.clockworks.bigture.delegate.StoryPageModel;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.PageArtworkId;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryAfterReading;
import com.clockworks.bigture.entity.StoryCollection;
import com.clockworks.bigture.entity.StoryMember;
import com.clockworks.bigture.entity.StoryPage;
import com.clockworks.bigture.entity.StoryPageArtwork;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.opensymphony.module.sitemesh.Page;

@Service
public class StoryService implements IStoryService {
	@Autowired private IStoryDAO dao;
	private final int MY_PAGE_SIZE = 80;
	@Autowired private IMasterDAO masterDao;
	@Autowired private IImageService imageService;
	@Autowired private IArtworkService artworkService;
	@Autowired private IArtworkDAO artworkDao;
	
	/**
	 * 일단 해당하는 대상의 내용을 찾고 이후에 조건에 따라서 내가 참여한 story를 찾아서 join형태로 처리한다.
	 */
	@Override
	public List<StoryDelegate> findUserStory(User owner, User currentUser,boolean isMy,
			StoryStatus status, SortOption sort) {
		
		List<Story> stories = dao.findStory(owner, false, status, sort, 0, MY_PAGE_SIZE,true);
		List<StoryMember> memberInfo = dao.findStoryMember(currentUser);
		List<StoryCollection> collectionInfo = null;
		//if(isMy){
			collectionInfo = dao.findCollection(currentUser);
		//}
		
		List<StoryDelegate> results = convertDelegate(stories, memberInfo,collectionInfo);
		
		return results;
	}

	@Override
	public List<StoryDelegate> findOwnedStory(User owner, StoryStatus status,
			SimpleSortOption sort, int start, int limits) {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
			states.add(StoryStatus.DRAFT);
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		List<Story> stories = dao.findOwnedStory(owner, states, sort, start, limits);
		List<StoryCollection> collectionInfo = dao.findCollection(owner);
			
		List<StoryDelegate> results = convertDelegate(stories, null	,collectionInfo);
		
		return results;
	}

	
	private List<StoryDelegate> convertDelegate(List<Story> stories,List<StoryMember> memberInfo,List<StoryCollection> collectionInfo){
		List<StoryDelegate> results = new ArrayList<StoryDelegate>();
		
		Map<Long,StoryMember> memberMap = new HashMap<Long, StoryMember>();
		
		if(memberInfo != null){
			for(StoryMember member : memberInfo){
				if(!memberMap.containsKey(member.getStory().getId())){
					memberMap.put(member.getStory().getId(), member);
				}
			}
		}
		
		Map<Long,StoryCollection> collectionMap = new HashMap<Long, StoryCollection>();
		
		if(collectionInfo != null){
			for(StoryCollection collection : collectionInfo){
				if(!collectionMap.containsKey(collection.getStory().getId())){
					collectionMap.put(collection.getStory().getId(), collection);
				}
			}
		}
		
		for(Story story : stories){
			StoryDelegate delegate = new StoryDelegate(story);
			
			if(memberInfo == null){
				//이경우에는 owned class를 조회하는 경우이다.
				delegate.setJoined(true);
				delegate.setDidItAll(true);
			}else{
				StoryMember member = memberMap.get(story.getId());
				if(member != null){
					delegate.setJoined(true);
					delegate.setDidItAll(member.isDidItAll());
				}else{
					delegate.setJoined(false);
					delegate.setDidItAll(false);
				}
			}
			
			if(collectionInfo == null){
				//이경우에는 이미 다 Collect된것을 조회하는 경우이다.
				delegate.setCollected(true);
			}else{
				if(collectionMap.containsKey(story.getId())){
					delegate.setCollected(true);
				}else{
					delegate.setCollected(false);
				}
				
			}
			results.add(delegate);
		}
		
		return results;
	}

	@Override
	public List<StoryDelegate> findJoinedStory(User owner, StoryStatus status,
			SimpleSortOption sort, int start, int limits) {
		
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		List<Story> stories = dao.findJoinedStory(owner, states, sort, start, limits);
		
		List<StoryCollection> collectionInfo = dao.findCollection(owner);
		
		List<StoryDelegate> results = convertDelegate(stories, null	,collectionInfo);
		
		return results;
	}

	@Override
	public List<StoryDelegate> findCollectedStory(User owner,
			StoryStatus status, SimpleSortOption sort, int start, int limits) {
		
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		List<Story> stories = dao.findCollectedStory(owner, states, sort, start, limits);
		
		List<StoryMember> memberInfo = dao.findStoryMember(owner);
		
		List<StoryDelegate> results = convertDelegate(stories, memberInfo,null);
		
		return results;
	}

	@Override
	public List<StoryDelegate> findAllStory(User owner, SimpleSortOption sort,
			int start, int limits) {
		
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		states.add(StoryStatus.DOING);
		states.add(StoryStatus.DONE);
		
		
		List<Story> stories = dao.listAllStory(states, sort, start, limits);
		
		List<StoryMember> memberInfo = dao.findStoryMember(owner);
		List<StoryCollection> collectionInfo = dao.findCollection(owner);
		
		List<StoryDelegate> results = convertDelegate(stories, memberInfo,collectionInfo);
		
		
		return results;
	}

	@Override
	public List<StoryDelegate> findStoryByOwner(User owner,User currntUser,
			SimpleSortOption sort, int start, int limits) {
		
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		states.add(StoryStatus.DOING);
		states.add(StoryStatus.DONE);
		
		
		List<Story> stories = dao.findOwnedStory(owner,states, sort, start, limits);
		
		List<StoryMember> memberInfo = dao.findStoryMember(currntUser);
		List<StoryCollection> collectionInfo = dao.findCollection(currntUser);
		
		List<StoryDelegate> results = convertDelegate(stories, memberInfo,collectionInfo);
		
		
		return results;
	}

	@Override
	public int countAllStory() {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		states.add(StoryStatus.DOING);
		states.add(StoryStatus.DONE);
		
		return dao.countAllStory(states);
	}

	@Override
	public int countStoryByOwner(User owner) {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		states.add(StoryStatus.DOING);
		states.add(StoryStatus.DONE);
		
		return dao.countOwnedStory(owner, states);
	}

	@Override
	public int countOwnedStory(User owner, StoryStatus status) {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
			states.add(StoryStatus.DRAFT);
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		return dao.countOwnedStory(owner, states);
	}

	@Override
	public int countJoinedStory(User owner, StoryStatus status) {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
	
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		return dao.countJoinedStory(owner, states);
	}

	@Override
	public int countCollectedStory(User owner, StoryStatus status) {
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		if(status != null){
			states.add(status);
		}else{
	
			states.add(StoryStatus.DOING);
			states.add(StoryStatus.DONE);
		}
		
		
		return dao.countCollectedStory(owner, states);
	}

	@Transactional
	@Override
	public boolean addCollection(Long storyId, User owner) {
		Story story = dao.loadStory(storyId);
		
		StoryCollection collection = new StoryCollection();
		collection.setStory(story);
		collection.setUser(owner);
		collection.setCreated(new Date());
		
		dao.createCollection(collection);
		
		return true;
	}

	@Transactional
	@Override
	public boolean removeCollection(Long storyId, User owner) {
		Story story = dao.loadStory(storyId);
		
		StoryCollection collection = dao.getCollection(story, owner);
		if(collection == null) return false;
		
		dao.deleteCollection(collection);
		
		return true;
	}

	@Transactional
	@Override
	public boolean deleteStory(Long storyId, User owner) {
		Story story = dao.loadStory(storyId);
		
		if(story.getOwner().equals(owner)){
			story.setStatus(StoryStatus.DELETED);
			dao.updateStory(story);
			return true;
		}
		
		return false;
	}

	@Override
	public List<StoryPageDelegate> listPages(Long storyId) {
		Story story = dao.loadStory(storyId);
		List<StoryPage> pages = dao.listPages(story);
		List<StoryPageDelegate> delegates = new ArrayList<StoryPageDelegate>();
		for(StoryPage page : pages){
			StoryPageDelegate delegate = new StoryPageDelegate(page);
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public List<StoryArtworkDelegate> listStoryArtworks(Long storyId,User currentUser) {
		Story story = dao.loadStory(storyId);
		List<StoryArtworkDelegate> delegates = new ArrayList<StoryArtworkDelegate>();
		List<StoryPageArtwork> artworks = dao.listPageArtworks(story);
		for(StoryPageArtwork sp : artworks){
			if(sp.getArtwork().isDeleted()) continue;
			if(sp.getArtwork().getShareType().equals(ShareType.PRIVATE)){
				if(sp.getArtwork().getUser().equals(currentUser)){
					//비공개이면 본인이 조회할때에만 보인다.
					StoryArtworkDelegate delegate = new StoryArtworkDelegate(sp);
					delegates.add(delegate);
				}
			}else{
				StoryArtworkDelegate delegate = new StoryArtworkDelegate(sp);
				delegates.add(delegate);
			}
		}
		
		return delegates;
	}

	@Transactional
	@Override
	public Story saveOrUpdateStory(User owner, StoryModel model)
			throws Exception {
		return saveOrUpdateStory(owner,model,StoryStatus.DRAFT);
	}
	
	private Story saveOrUpdateStory(User owner, StoryModel model, StoryStatus status) throws Exception{
		boolean isCreate = false;
		Story story = null;
		if(model.getId() != null && !"".equals(model.getId())){
			story = dao.loadStory(model.getId());
		}else{
			if(owner == null){
				throw new Exception("Owner is null");
			}
			isCreate = true;
			story = new Story();
			story.setOwner(owner);
			story.setHot(false);
			story.setLoveCount(0);
			story.setFantasticCount(0);
			story.setFunCount(0);
			story.setWowCount(0);
			story.setAwesomeCount(0);
			story.setAfterReading(true); //무조건 true이다.
			story.setMemberCount(0);
			story.setPageCount(0);
		}
		
		story.setTitle(model.getTitle());
		if(model.getCoverImageId() != null){
			
			if(model.isCoverFromArtwork()){
				ImageInfo info = imageService.transferFile(artworkService.loadArtwork(Long.parseLong(model.getCoverImageId())), ImagePathType.STORY);
				story.setCoverImage(info.getThumbnailPath());
			}else{
				TempImage tmp = masterDao.loadTempImage( new Long(model.getCoverImageId()) );
				ImageInfo info = imageService.transferFile(tmp, ImagePathType.STORY);
				story.setCoverImage(info.getThumbnailPath());
			}	
		}
		story.setStatus(status);
		List<StoryPage> pages = story.getPages();
		if(pages == null){
			pages = new ArrayList<StoryPage>();
			story.setPages(pages);
		}else{
			if(!story.getStatus().equals(StoryStatus.DOING)){
				//진행중인것이 아니면 이미 기존에 존재하던 page가 있으면 다 삭제한다.
				story.getPages().clear();
				dao.updateStory(story);
			}
		}
		
		for(StoryPageModel pageModel : model.getPages()){
			convertAndAddPage(story,pages,pageModel);
		}
		story.setPageCount(pages.size());
		
		
		if(isCreate){
			dao.createStory(story);
		}else{
			dao.updateStory(story);
		}
		
		return story;
	}
	
	/**
	 * 
	 * @param story
	 * @param pages
	 * @param pageModel
	 */
	private void convertAndAddPage(Story story,List<StoryPage> pages,StoryPageModel pageModel){
		StoryPage page = null;
		
		if(story.getStatus() != null && story.getStatus().equals(StoryStatus.DOING)){
			if(pageModel.getPageId() != null){
				for(StoryPage p : pages){
					if(p.getId().equals(pageModel.getPageId())){
						page = p;
						continue;
					}
				}
				
			}else{
				page = new StoryPage();
				page.setStory(story);
				page.setCreated(new Date());
				pages.add(page);
			}			
		}else{
			//무조건 기존 id를 무시하고 새로운 id로 만든다.
			page = new StoryPage();
			page.setStory(story);
			page.setCreated(new Date());
			pages.add(page);
		}
		
		page.setDescription(pageModel.getDescription());
		page.setPageNo(pageModel.getPageNo());
		
	}

	@Transactional
	@Override
	public void registArtwork(User owner, Long storyId, Long artworkId,
			Long pageId) throws Exception {
		Artwork artwork = artworkDao.loadArtwork(artworkId);
		Story story = dao.loadStory(storyId);
		StoryPage page = null;
		for(StoryPage p : story.getPages()){
			if(pageId.equals(p.getId())){
				page = p;
				break;
			}
		}
		
		if(page == null){
			throw new Exception("PageId[" + pageId + "] dose'nt exist");
		}
		
		StoryPageArtwork pa = new StoryPageArtwork();
		PageArtworkId pId = new PageArtworkId();
		pa.setPk(pId);
		pa.setArtwork(artwork);
		pa.setPage(page);
		
		dao.createPageArtworks(pa);
		
		if(story.getCoverImage() == null || "".equals(story.getCoverImage())){
			//커버이미지가 없으면 처음으로 그려지는 그림을 cover로 만든다.
			//artwork의 이미지를 storycover에 copy한다.
			try{
				ImageInfo info = imageService.transferFile(artwork, ImagePathType.STORY);
				story.setCoverImage(info.getThumbnailPath());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(!isMember(story,owner)){
			//멤버에 추가한다.
			if(story.getMembers() == null){
				story.setMembers(new ArrayList<StoryMember>());
			}
			
			StoryMember member = new StoryMember();
			member.setMember(owner);
			member.setStory(story);
			member.setCreated(new Date());
			member.setDidItAll(false);
			
			story.getMembers().add(member);
			story.setMemberCount(story.getMembers().size());
		}
			
		dao.updateStory(story);
		//TODO 추후에 해당 멤버가 모든 page에 참여했는지 검사해서 diditall을 update해야한다.
			
		
		
		artwork.setStory(story);
		artwork.setReferenceId(story.getId());
		artwork.setReferenceTitle(story.getTitle());
		artwork.setType(ArtworkType.STORY);
		artworkDao.updateArtwork(artwork);
	}

	public boolean isMember(Story story,User user){
		List<StoryMember> members = story.getMembers();
		if(members != null){
			for(StoryMember member : members){
				if(member.getMember().equals(user))
					return true;
			}
		}
		return false;
	}
	
	@Transactional
	@Override
	public void registAfterReading(User owner, Long storyId, Long artworkId)
			throws Exception {
		Artwork artwork = artworkDao.loadArtwork(artworkId);
		Story story = dao.loadStory(storyId);
		
		StoryAfterReading entity = new StoryAfterReading();
		entity.setArtwork(artwork);
		entity.setStory(story);
		entity.setCreated(new Date());
		
		dao.createAfterReading(entity);
		
		artwork.setType(ArtworkType.STORY);
		artwork.setStory(story);
		artwork.setReferenceId(story.getId());
		artwork.setReferenceTitle(story.getTitle());
		
		artworkDao.updateArtwork(artwork);
	}

	@Override
	public Story loadStory(Long storyId) {
		return dao.loadStory(storyId);
	}

	@Override
	public List<StoryArtworkDelegate> listStoryArtworksByPage(Long pageId,User currentUser) {
		StoryPage page = dao.loadPage(pageId);
		List<StoryArtworkDelegate> delegates = new ArrayList<StoryArtworkDelegate>();
		List<StoryPageArtwork> artworks = dao.listPageArtworks(page);
		for(StoryPageArtwork sp : artworks){
			if(sp.getArtwork().isDeleted()) continue;
			if(sp.getArtwork().getShareType().equals(ShareType.PRIVATE)){
				if(sp.getArtwork().getUser().equals(currentUser)){
					//비공개이면 본인이 조회할때에만 보인다.
					StoryArtworkDelegate delegate = new StoryArtworkDelegate(sp);
					delegates.add(delegate);
				}
			}else{
				StoryArtworkDelegate delegate = new StoryArtworkDelegate(sp);
				delegates.add(delegate);
			}
		}
		
		return delegates;
	}

	@Transactional
	@Override
	public Story shareStory(User owner, StoryModel model) throws Exception {
		return saveOrUpdateStory(owner,model,StoryStatus.DOING);
	}

	@Override
	public List<AfterReadingDelegate> listAfterReads(Long storyId) {
		Story story = dao.loadStory(storyId);
		List<AfterReadingDelegate> delegates = new ArrayList<AfterReadingDelegate>();
		
		List<StoryAfterReading> results = dao.listAfterReading(story);
		for(StoryAfterReading reading : results){
			AfterReadingDelegate delegate = new AfterReadingDelegate(reading);
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public List<StoryDelegate> getSimpleStories(int limits) {
		
		List<StoryStatus> states = new ArrayList<StoryStatus>();
		states.add(StoryStatus.DOING);
		states.add(StoryStatus.DONE);
		
		List<Story> stories = dao.listAllStory(states, SimpleSortOption.RECENT, 0, limits);
		
		List<StoryDelegate> results = convertDelegate(stories, null,null);
		
		return results;
	}
	
}
