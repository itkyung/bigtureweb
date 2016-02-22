package com.clockworks.bigture.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.SimpleSortOption;
import com.clockworks.bigture.FindClassType;
import com.clockworks.bigture.IArtClassDAO;
import com.clockworks.bigture.IArtClassService;
import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.IMasterDAO;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.common.ImagePathType;
import com.clockworks.bigture.delegate.ArtClassDelegate;
import com.clockworks.bigture.delegate.ArtClassModel;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.PuzzleArtworkDelegate;
import com.clockworks.bigture.delegate.PuzzleDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassCollection;
import com.clockworks.bigture.entity.ArtClassMember;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.ClassShare;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.ClassType;
import com.clockworks.bigture.entity.PuzzlePart;
import com.clockworks.bigture.entity.PuzzleStatus;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.IUserService;

@Service
public class ArtClassService implements IArtClassService {
	@Autowired IArtClassDAO dao;
	@Autowired private IMasterDAO masterDao;
	@Autowired private IImageService imageService;
	@Autowired private IArtworkService artworkService;
	@Autowired private IUserService userService;
	@Autowired private INotificationManager notiManager;
	
	private static DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<ArtClassDelegate> findUserClass(User user, boolean isMy,int start,int limit, ClassStatus status) {
		List<ClassStatus> statusList = new ArrayList<ClassStatus>();
		if(status == null){
			statusList.add(ClassStatus.DOING);
			statusList.add(ClassStatus.DONE);
			statusList.add(ClassStatus.READY);
		}else{
			statusList.add(status);
		}
		
		if(isMy){
			statusList.add(ClassStatus.READY);
		}
		
		List<ArtClassCollection> collections = dao.findCollection(user);
		
		List<ArtClass> classes = dao.findClass(user, start, limit, isMy ? false : true, statusList);
		
		List<ArtClassMember> members = dao.findMember(user);
		
		return convertDelegate(classes,collections,members,isMy,isMy);
	}
	
	

	@Override
	public List<ArtClassCollection> findCollection(User owner) {
		return dao.findCollection(owner);
	}



	private List<ArtClassDelegate> convertDelegate(List<ArtClass> classes,List<ArtClassCollection> collections,List<ArtClassMember> members,
			boolean forceCollected,boolean forceJoined){
		
		List<ArtClassDelegate> results = new ArrayList<ArtClassDelegate>();
		
		Map<Long,ArtClassCollection> collectionMap = new HashMap<Long, ArtClassCollection>();
		
		if(collections != null){
			for(ArtClassCollection collection : collections){
				collectionMap.put(collection.getArtClass().getId(), collection);
			}
		}
		
		Map<Long,ArtClassMember> memberMap = new HashMap<Long, ArtClassMember>();
		
		if(members != null){
			for(ArtClassMember member : members){
				memberMap.put(member.getArtClass().getId(),member);
			}
		}
		
		for(ArtClass artClass : classes){
			ArtClassDelegate delegate = new ArtClassDelegate(artClass);
			if(forceCollected){
				delegate.setCollected(true);
			}else{
				if(collectionMap.containsKey(artClass.getId())){
					delegate.setCollected(true);
				}else{
					delegate.setCollected(false);
				}
			}
			
			if(forceJoined){
				delegate.setJoined(true);
			}else{
				if(memberMap.containsKey(artClass.getId())){
					delegate.setJoined(true);
				}else{
					delegate.setJoined(false);
				}
			}
			
			
			results.add(delegate);
		}
		
		return results;
	}

	@Override
	public List<ArtClassDelegate> findOwnClass(User user, int start, int limit,
			ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.READY,ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		List<ArtClass> results = dao.findClass(user, start, limit, FindClassType.OWN, statusList, SimpleSortOption.RECENT);
		
		return convertDelegate(results,null,null,true,true);
	}

	@Override
	public List<ArtClassDelegate> findJoinedClass(User user, int start,
			int limit, ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		List<ArtClassCollection> collections = dao.findCollection(user);
		
		List<ArtClass> results = dao.findClass(user, start, limit, FindClassType.JOINED, statusList, SimpleSortOption.RECENT);
		
		return convertDelegate(results,collections,null,false,true);
	}

	@Override
	public List<ArtClassDelegate> findCollectedClass(User user, int start,
			int limit, ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		List<ArtClassMember> members = dao.findMember(user);
		
		List<ArtClass> results = dao.findCollection(user, statusList, start, limit);
		
		return convertDelegate(results,null,members,true,false);
	}

	@Override
	public List<ArtClassDelegate> findAllClass(User user, int start, int limit,SimpleSortOption sortOption) {
		
		List<ClassStatus> statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		
		List<ArtClassCollection> collections = dao.findCollection(user);
		List<ArtClassMember> members = dao.findMember(user);
		
		List<ArtClass> results = dao.findClass(user, start, limit, FindClassType.ALL, statusList, sortOption);
		
		return convertDelegate(results,collections,members,false,false);
	}

	@Override
	public List<ArtClassDelegate> findClassByOwner(User user, int start,
			int limit, SimpleSortOption sortOption) {
		
		List<ClassStatus> statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		List<ArtClassCollection> collections = dao.findCollection(user);
		List<ArtClassMember> members = dao.findMember(user);
		
		List<ArtClass> results = dao.findClass(user, start, limit, FindClassType.OWN, statusList, SimpleSortOption.RECENT);
		
		return convertDelegate(results,collections,members,false,false);
		
	}

	@Override
	public int countOwnClass(User user, ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.READY,ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		
		return dao.countClass(user, FindClassType.OWN, statusList);
	}

	@Override
	public int countJoinedClass(User user, ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		return dao.countClass(user, FindClassType.JOINED, statusList);
	}

	@Override
	public int countCollectedClass(User user, ClassStatus status) {
		List<ClassStatus> statusList;
		if(status == null){
			statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		}else{
			statusList = Arrays.asList(status);
		}
		
		return dao.countCollectedClass(user, statusList);
	}

	@Override
	public int countAllClass(User user) {
		List<ClassStatus> statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		
		
		return dao.countClass(user, FindClassType.ALL, statusList);
	}

	@Override
	public int countClassByOwner(User user) {
		List<ClassStatus> statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		
		return dao.countClass(user, FindClassType.OWN, statusList);
	}

	
	@Transactional
	@Override
	public ArtClass createArtClass(User owner, ArtClassModel model) throws Exception {
		ArtClass artClass = new ArtClass();
		artClass.setOwner(owner);
		artClass.setClassName(model.getClassName());
		artClass.setDescription(model.getDescription());
		artClass.setClassType(ClassType.valueOf(model.getClassType()));
		artClass.setShareType(ClassShare.valueOf(model.getShareType()));
		artClass.setStartDate(fm.parse(model.getStartDate()));
		artClass.setEndDate(fm.parse(model.getEndDate()));
		
		String tmpId = model.getCoverImageId();
		
		if(tmpId != null){
			if(model.isCoverFromArtwork()){
				ImageInfo info = imageService.transferFile(artworkService.loadArtwork(Long.parseLong(model.getCoverImageId())), ImagePathType.ARTCLASS);
				artClass.setCoverImage(info.getThumbnailPath());
			}else{
				TempImage tmp = masterDao.loadTempImage( new Long(tmpId) );
				ImageInfo info = imageService.transferFile(tmp, ImagePathType.ARTCLASS);
				artClass.setCoverImage(info.getThumbnailPath());
			}	
		}
		
		List<ArtClassMember> members = new ArrayList<ArtClassMember>();
		if(artClass.getShareType().equals(ClassShare.EXCLUSIVE)){
			if(model.getSharedFriends().size() == 0){
				throw new Exception("ArtClass must have at least one shared friends");
			}
			
			for(Long userId : model.getSharedFriends()){
				ArtClassMember member = new ArtClassMember();
				member.setArtClass(artClass);
				member.setMember(userService.loadUser(userId));
				member.setCreated(new Date());
				members.add(member);
			}
			
			artClass.setMembers(members);
			artClass.setMemberCount(members.size());
		}
		
		Date today = new Date();
		if(artClass.getStartDate().before(today)){
			artClass.setStatus(ClassStatus.DOING);
		}else{
			artClass.setStatus(ClassStatus.READY);
		}
		
		artClass.setHot(false);
		dao.create(artClass);
		
		if(artClass.getShareType().equals(ClassShare.EXCLUSIVE)){
			notiManager.inviteClass(artClass, members);
		}else{
			notiManager.openClass(artClass);
		}
		
		return artClass;
	}

	@Transactional
	@Override
	public boolean addCollection(User user, Long classId) {
		ArtClass artClass = dao.loadClass(classId);
		ArtClassCollection collection = new ArtClassCollection();
		
		collection.setArtClass(artClass);
		collection.setUser(user);
		dao.create(collection);
		
		return true;
	}

	@Transactional
	@Override
	public boolean removeCollection(User user, Long classId) {
		ArtClass artClass = dao.loadClass(classId);
		ArtClassCollection collection = dao.findCollection(user, artClass);
		if(collection == null) return false;
		
		dao.remove(collection);
		
		return true;
	}

	@Override
	public ArtClass loadArtClass(Long id) {
		return dao.loadClass(id);
	}

	@Override
	public List<User> getJoinedMembers(Long classId) {
		return dao.findJoinedMembers(dao.loadClass(classId));
	}
	
	@Transactional
	@Override
	public String changeCoverImage(Long classId, Long coverImageId,
			boolean coverFromArtwork) throws Exception {
		
		ArtClass artClass = dao.loadClass(classId);
		
		if(coverFromArtwork){
			ImageInfo info = imageService.transferFile(artworkService.loadArtwork(coverImageId), ImagePathType.ARTCLASS);
			artClass.setCoverImage(info.getThumbnailPath());
		}else{
			TempImage tmp = masterDao.loadTempImage(coverImageId );
			ImageInfo info = imageService.transferFile(tmp, ImagePathType.ARTCLASS);
			artClass.setCoverImage(info.getThumbnailPath());
		}	
		
		dao.update(artClass);
		
		return artClass.getCoverImage();
	}

	@Override
	public List<ArtworkDelegate> listClassArtworks(User currentUser,
			Long classId,int start,int limits, SortOption sort) {
		ArtClass artClass = dao.loadClass(classId);
		
		List<Artwork> artworks = dao.listClassArtworks(artClass, start, limits, sort);
		
		List<ArtworkCollection> collection = artworkService.findAllCollection(currentUser);
		

		Map<Long,ArtworkCollection> collectMap = new HashMap<Long,ArtworkCollection>();
		for(ArtworkCollection collect : collection){
			collectMap.put(collect.getArtwork().getId(),collect);
		}
		
		List<ArtworkDelegate> results = new ArrayList<ArtworkDelegate>();
		
		for(Artwork artwork : artworks){
			ArtworkDelegate delegate = new ArtworkDelegate(artwork);
			if(collectMap.containsKey(artwork.getId())){
				delegate.setCollected(true);
			}else{
				delegate.setCollected(false);
			}
			results.add(delegate);
		}
		
		return results;
	}

	@Override
	public int countClassArtworks(Long classId) {
		return dao.countClassArtworks(dao.loadClass(classId));
	}

	@Transactional
	@Override
	public void deleteArtClass(User user, Long classId) throws Exception {
		ArtClass artClass = dao.loadClass(classId);
		if(user.equals(artClass.getOwner())){
			artClass.setStatus(ClassStatus.DELETED);
			dao.update(artClass);
		}else{
			throw new Exception(user.getNickName() + " is'nt this artclass owner");
		}
		
	}

	@Transactional
	@Override
	public void editArtClass(User user, Long classId, Date startDate,
			Date endDate, String description) throws Exception {
		ArtClass artClass = dao.loadClass(classId);
		if(user.equals(artClass.getOwner())){
			artClass.setStartDate(startDate);
			artClass.setEndDate(endDate);
			artClass.setDescription(description);
			
			ClassStatus status = null;
			Date today = new Date();
			if(today.after(startDate)){
				if(today.before(endDate)){
					status = ClassStatus.DOING;
				}else{
					status = ClassStatus.DONE;
				}
			}else{
				status = ClassStatus.READY;
			}
			artClass.setStatus(status);
			dao.update(artClass);
			
		}else{
			throw new Exception(user.getNickName() + " is'nt this artclass owner");
		}
	}



	@Override
	public boolean isJoined(ArtClass artClass, User owner) {
		return dao.isJoined(artClass, owner);
	}


	@Transactional
	@Override
	public boolean addClassMember(Long classId, List<String> addedMemberIds) {
		ArtClass artClass = dao.loadClass(classId);
		
		List<ArtClassMember> members = artClass.getMembers();
		for(String memberId : addedMemberIds){
			ArtClassMember member = new ArtClassMember();
			member.setCreated(new Date());
			member.setArtClass(artClass);
			member.setMember(userService.loadUser( Long.parseLong(memberId) ));
			members.add(member);
		}
		
		artClass.setMemberCount(members.size());
		dao.update(artClass);
		
		return true;
	}


	@Transactional
	@Override
	public void registArtwork(User user, Long classId, Long artworkId)
			throws Exception {
		Artwork artwork = artworkService.loadArtwork(artworkId);
		ArtClass artClass = dao.loadClass(classId);
		
		if(!canRegistArtwork(artClass, user)){
			throw new Exception("Can't regist class artwork");
		}
		
		artwork.setArtClass(artClass);
		artwork.setReferenceId(classId);
		artwork.setTitle(artClass.getClassName());
		artwork.setType(ArtworkType.CLASS);
		
		artworkService.updateArtwork(artwork);
		
		if(artClass.getShareType().equals(ClassShare.OPEN)){
			//멤버로 등록시켜야한다.
			if(!isJoined(artClass, user)){
				ArtClassMember member = new ArtClassMember();
				member.setArtClass(artClass);
				member.setMember(user);
				member.setCreated(new Date());
				
				if(artClass.getMembers() == null){
					artClass.setMembers(new ArrayList<ArtClassMember>());
				}
				artClass.getMembers().add(member);
				artClass.setMemberCount(artClass.getMembers().size());
				dao.update(artClass);
			}
		}
		
	}


	@Transactional
	@Override
	public void registPuzzleArtwork(User user, Long classId, Long puzzleId,
			Long artworkId, PuzzlePart part) throws Exception {
		ArtClass artClass = dao.loadClass(classId);
		ArtClassPuzzle puzzle = dao.loadPuzzle(puzzleId);
		Artwork artwork = artworkService.loadArtwork(artworkId);
		
		if(!canRegistArtwork(artClass, user)){
			throw new Exception("Can't regist class artwork");
		}
		
		if(canRegistPuzzle(puzzle,artwork,part)){
			ArtClassPuzzleArtwork pz = findActivePuzzlePart(puzzle,part);
			if(pz == null){
				pz = new ArtClassPuzzleArtwork();
				if(puzzle.getFragments() == null){
					puzzle.setFragments(new ArrayList<ArtClassPuzzleArtwork>());
				}
				puzzle.getFragments().add(pz);
				pz.setOwner(user);
				pz.setPart(part);
				pz.setPuzzle(puzzle);
				pz.setCreated(new Date());
				pz.setStarted(new Date());
			}
			pz.setEnded(new Date());
			pz.setArtwork(artwork);
			pz.setStatus(PuzzleStatus.COMPLETED);
			
			if(checkCompleted(puzzle)){
				
				puzzle.setCompleted(true);
			}else{
				
				puzzle.setCompleted(false);
			}
			
			dao.updatePuzzle(puzzle);
		}else{
			throw new Exception("Puzzle Part[" + part.name() + "] is already reserved");
		}
	}

	private boolean checkCompleted(ArtClassPuzzle puzzle){
		if(puzzle.getFragments() == null) return false;
		boolean topLeft = false;
		boolean topRight = false;
		boolean bottomLeft = false;
		boolean bottomRight = false;
		for(ArtClassPuzzleArtwork pa : puzzle.getFragments()){
			if(pa.getStatus().equals(PuzzleStatus.COMPLETED)){
				switch(pa.getPart()){
				case TOP_LEFT:
					topLeft = true;
					break;
				case TOP_RIGHT:
					topLeft = true;
					break;
				case BOTTOM_LEFT:
					bottomLeft = true;
					break;
				case BOTTM_RIGHT:
					bottomRight = true;
					break;
				}	
			}
		}
		
		return topLeft && topRight && bottomLeft && bottomRight;
	}

	@Transactional
	@Override
	public void reservePuzzleArtwork(User user, Long classId, Long puzzleId,
			PuzzlePart part) throws Exception {
		ArtClass artClass = dao.loadClass(classId);
		ArtClassPuzzle puzzle = dao.loadPuzzle(puzzleId);

		if(!canRegistArtwork(artClass, user)){
			throw new Exception("Can't regist class artwork");
		}
		
		if(canReservePuzzle(puzzle,user,part)){
			ArtClassPuzzleArtwork pz = findActivePuzzlePart(puzzle,part);
			if(pz == null){
				pz = new ArtClassPuzzleArtwork();
				if(puzzle.getFragments() == null){
					puzzle.setFragments(new ArrayList<ArtClassPuzzleArtwork>());
				}
				puzzle.getFragments().add(pz);
				pz.setOwner(user);
				pz.setPart(part);
				pz.setPuzzle(puzzle);
				pz.setCreated(new Date());
				pz.setStarted(new Date());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, 1);	//1시간을 최대시간으로 잡는다.
				pz.setExpireTime(cal.getTime()); //1시간 후를 expire시간으로 잡는다.
			}
			
			pz.setStatus(PuzzleStatus.ING);
			
			dao.updatePuzzle(puzzle);
		}else{
			throw new Exception("Puzzle Part[" + part.name() + "] is already reserved");
		}
	}


	@Transactional
	@Override
	public void cancelPuzzleArtwork(User user, Long classId, Long puzzleId,
			 PuzzlePart part) throws Exception {
		ArtClassPuzzle puzzle = dao.loadPuzzle(puzzleId);
		ArtClassPuzzleArtwork pz = findActivePuzzlePart(puzzle,part);
		if(pz == null){
			throw new Exception("Active puzzle does'nt exist");
		}
		if(pz.getOwner().equals(user)){
			//본인것이면 취소가 가능하다.
			pz.setEnded(new Date());
			pz.setStatus(PuzzleStatus.CANCEL);
			dao.updatePuzzle(puzzle);
		}else{
			throw new Exception("Active puzzle does'nt exist");
		}
	}
	
	/**
	 * 해당 puzzle을 등록할수 있는지 검사.
	 * 해당 사용자가 이미 reserve중이거나 아무도 reserve안햇으면 등록가능함.
	 * @param puzzle
	 * @param artwork
	 * @param part
	 * @return
	 */
	public boolean canRegistPuzzle(ArtClassPuzzle puzzle,Artwork artwork,PuzzlePart part){
		User owner = artwork.getUser();
		ArtClassPuzzleArtwork pz = findActivePuzzlePart(puzzle,part);
		if(pz == null){
			return true;
		}
		if(pz.equals(PuzzleStatus.ING) && pz.getOwner().equals(owner)){
			return true;
		}
		return false;
	}
	
	public boolean canReservePuzzle(ArtClassPuzzle puzzle,User owner,PuzzlePart part){
		
		ArtClassPuzzleArtwork pz = findActivePuzzlePart(puzzle,part);
		if(pz == null){
			return true;
		}
		if(pz.equals(PuzzleStatus.ING) && pz.getOwner().equals(owner)){
			return true;
		}
		return false;
	}
	
	public ArtClassPuzzleArtwork findActivePuzzlePart(ArtClassPuzzle puzzle,PuzzlePart part){
		if(puzzle.getFragments() == null) return null;
		for(ArtClassPuzzleArtwork pa : puzzle.getFragments()){
			if(pa.getPart().equals(part)){
				if(!pa.getStatus().equals(PuzzleStatus.CANCEL)){
					return pa;
				}
			}
		}
		return null;
		
	}



	@Override
	public List<PuzzleDelegate> listPuzzle(Long classId) {
		List<PuzzleDelegate> delegates = new ArrayList<PuzzleDelegate>();
		
		List<ArtClassPuzzle> puzzles = dao.listPuzzle(dao.loadClass(classId));
		for(ArtClassPuzzle puzzle : puzzles){
			PuzzleDelegate delegate = new PuzzleDelegate(puzzle);
			delegates.add(delegate);
		}
		return delegates;
	}



	@Override
	public List<PuzzleArtworkDelegate> listPuzzleArtworks(Long classId,
			Long puzzleId) {
		List<PuzzleArtworkDelegate> delegates = new ArrayList<PuzzleArtworkDelegate>();
		
		List<ArtClassPuzzleArtwork> artworks = dao.listPuzzleArtworks(dao.loadClass(classId), dao.loadPuzzle(puzzleId));
		for(ArtClassPuzzleArtwork artwork : artworks){
			PuzzleArtworkDelegate delegate = new PuzzleArtworkDelegate(artwork);
			delegates.add(delegate);
		}
		
		return delegates;
	}



	@Override
	public ArtClassPuzzle loadPuzzle(Long puzzleId) {
		return dao.loadPuzzle(puzzleId);
	}



	@Override
	public ArtClassPuzzleArtwork findPuzzleArtwork(Long puzzleId,
			PuzzlePart part) {
		ArtClassPuzzle puzzle = dao.loadPuzzle(puzzleId);
		return findActivePuzzlePart(puzzle,part);
		
	}



	@Override
	public boolean canRegistArtwork(ArtClass artClass, User owner) {
		if(artClass.getShareType().equals(ClassShare.EXCLUSIVE)){
			//멤버만 수강이 가능함.
			for(ArtClassMember member : artClass.getMembers()){
				if(member.getMember().equals(owner)) return true;
			}
			return false;
		}else{
			return true;
		}
		
	}



	@Override
	public List<ArtClassDelegate> getSimpleClass(int limits) {
		List<ClassStatus> statusList = Arrays.asList(ClassStatus.DOING,ClassStatus.DONE);
		
		List<ArtClass> results = dao.findClass(null, 0, limits, FindClassType.ALL, statusList, SimpleSortOption.RECENT);
		
		return convertDelegate(results,null,null,false,false);
	}

	
	
}
