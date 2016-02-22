package com.clockworks.bigture.identity.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.IMasterDAO;
import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.Role;
import com.clockworks.bigture.aws.ISESService;
import com.clockworks.bigture.common.CommonUtils;
import com.clockworks.bigture.common.IImageService;
import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.common.ImagePathType;
import com.clockworks.bigture.common.ImageService;
import com.clockworks.bigture.delegate.ExpertDelegate;
import com.clockworks.bigture.delegate.ExpertListWrapper;
import com.clockworks.bigture.delegate.FriendGroupDelegate;
import com.clockworks.bigture.delegate.FriendGroupMemberDelegate;
import com.clockworks.bigture.delegate.NameDelegate;
import com.clockworks.bigture.delegate.NewsDelegate;
import com.clockworks.bigture.delegate.SimpleProfileDelegate;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.delegate.UserSearchDelegate;
import com.clockworks.bigture.delegate.WordWrapper;
import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.DeviceType;
import com.clockworks.bigture.entity.FriendGroup;
import com.clockworks.bigture.entity.FriendGroupMember;
import com.clockworks.bigture.entity.FriendStatus;
import com.clockworks.bigture.entity.Friends;
import com.clockworks.bigture.entity.Gender;
import com.clockworks.bigture.entity.News;
import com.clockworks.bigture.entity.NewsImage;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.TempImage;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.clockworks.bigture.entity.UserStatus;
import com.clockworks.bigture.external.IMailService;
import com.clockworks.bigture.identity.ILogin;
import com.clockworks.bigture.identity.IUserDAO;
import com.clockworks.bigture.identity.IUserService;
import com.clockworks.bigture.identity.SocialType;
import com.clockworks.bigture.ui.param.UserSearchModel;
import com.clockworks.bigture.ui.param.UserSearchRole;
import com.clockworks.bigture.ui.param.UserSearchType;




@Service("UserService")
public class UserService implements IUserService,ILogin,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1115499380412201135L;
	@Autowired private IUserDAO dao;
	@Autowired private IMasterDAO masterDao;
	@Autowired private IMailService mailService;
	@Autowired private ISESService sesService;
	@Autowired private IImageService imageService;
	@Autowired private INotificationManager notiManager;
	
	Logger log = Logger.getLogger(UserService.class);
	
	private DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	@Transactional
	@Override
	public void initAdmin() {
		try{
			User adminUser = dao.findByLoginId(IUserService.ADMIN_ID);
			if(adminUser == null){
				adminUser = new User();
				adminUser.setLoginId(IUserService.ADMIN_ID);	
				adminUser.setPwd(new String(CommonUtils.md5(IUserService.ADMIN_PASSWORD)));
				adminUser.setFirstName("admin");
				adminUser.setStatus(UserStatus.ACTIVE);
				adminUser.setCreated(new Date());
				adminUser.setFantasticCount(0);
				adminUser.setAwesomeCount(0);
				adminUser.setFunCount(0);
				adminUser.setGender(Gender.F);
				adminUser.setWowCount(0);
				adminUser.setLoveCount(0);
				
				dao.addRoleToUser(adminUser,Role.ADMIN_ROLE);
				dao.createUser(adminUser);
			}
		}catch(Exception e){
			log.error("Init admin error : " + e);
		}
	}
	
	
	
	@Transactional
	@Override
	public void initUser() {
		try{
//			User seller1 = new User();
//			seller1.setLoginId("seller1");
//			seller1.setPwd(new String(CommonUtils.md5("seller1234")));
//			seller1.setStatus(UserStatus.ACTIVE);
//			seller1.setCreated(new Date());
//			seller1.setUpdated(new Date());
//			seller1.setApproved(new Date());
//			seller1.setBizLicenseNo("123456-567890");
//			seller1.setChargePerson("담당자1");
//			
//		//	dao.addRoleToUser(seller1, Role.USER_ROLE);
//		//	dao.createUser(seller1);
//			
//			User supplier1 = new User();
//			supplier1.setLoginId("supplier1");
//			supplier1.setPassword(new String(CommonUtils.md5("supplier1234")));
//			supplier1.setStatus(UserStatus.ACTIVE);
//			supplier1.setBusinessName("공급자1");
//			supplier1.setCreated(new Date());
//			supplier1.setUpdated(new Date());
//			supplier1.setApproved(new Date());
//			supplier1.setBizLicenseNo("123999-567890");
//			supplier1.setChargePerson("담당자1");
//			
//		//	dao.addRoleToUser(supplier1, Role.USER_ROLE);
//		//	dao.createUser(supplier1);
//		
//			User styleNanda = new User();
//			styleNanda.setLoginId("stylenanda");
//			styleNanda.setPassword(new String(CommonUtils.md5("12345678")));
//			styleNanda.setStatus(UserStatus.ACTIVE);
//			styleNanda.setBusinessName("(주)난다");
//			styleNanda.setCreated(new Date());
//			styleNanda.setUpdated(new Date());
//			styleNanda.setApproved(new Date());
//			styleNanda.setBizLicenseNo("321888-567890");
//			styleNanda.setChargeContact1("010-222-1111");
//			styleNanda.setChargePerson("최용석");
//			styleNanda.setZipCode("222-111");
//			styleNanda.setAddress("인천광역시 계양국 걔산4동 계양우체국 사서함 1117호");
//			
//			dao.addRoleToUser(styleNanda, Role.USER_ROLE);
//			dao.createUser(styleNanda);
			
			
		}catch(Exception e){
		
			
		}
		
	}




	@Override
	public User findByLoginId(String loginId) {
		return dao.findActiveByLoginId(loginId);
	}

	@Override
	public List<User> getUsers(String role) {
		return dao.getUsers(role);

	}

	@Transactional(readOnly=true)
	@Override
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal != null){
			if(principal instanceof String){
				return null; //익명의 사용자이다.
			}
			org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User)principal;
			User user = dao.findActiveByLoginId(springUser.getUsername());
			return user;
		}
		return null;
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isInRole(User user, String roleName) {
		for(UserRoles ur : user.getRoles()){
			if(ur.getRoleName().equals(roleName))
				return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void updateLastLoginDate(User user) {
		user.setLastLoginDate(new Date());
		dao.updateUser(user);
		
	}


	/**
	 * 로그인시에 사용자의 추가정보들을 업데이트한다.
	 */
	@Transactional
	@Override
	public void updateLoginData(User user, Date lastLoginDate,
			DeviceType deviceType, OsType osType, String osVersion, String appVersion) {
		user.setLastLoginDate(lastLoginDate);
		user.setAppVersion(appVersion);
		user.setOsVersion(osVersion);
		user.setOsType(osType);
		user.setDeviceType(deviceType);
		//여기에서 loginToken도 새로 발행해준다.
		user.setLoginToken(makeLoginToken(user));
		Calendar cur = Calendar.getInstance();
		cur.add(Calendar.MONTH, 1);	//우선 한달뒤로 설정한다. 
		user.setTokenExpireDate(cur.getTime());
		
		dao.updateUser(user);
		
		
		
	}

	private String makeLoginToken(User user){
		Date curr = new Date();
		return user.getId() + "-" + curr.getTime();
	}

/**
 * 해당 loginId에 대해서 loginToken이 유효한지 판단한다.
 */
	@Override
	public boolean isValidToken(String loginId, String loginToken) {
		
		User user = findByLoginId(loginId);
		if(user != null){
			Date currDate = new Date();
			if(loginToken.equals(user.getLoginToken()) && user.getTokenExpireDate() != null 
					&& user.getTokenExpireDate().after(currDate)){
				return true;
			}
		}
		
		return false;
	}



	@Override
	public List<User> getLikeMe(User user,Long regionId) {
		return dao.findLike(user, true,regionId,null);
	}
	
	
	
	@Override
	public List<User> getLikeYou(User user,Long regionId,String keyword) {
		return dao.findLike(user, false,regionId,keyword);
	}

	@Override
	@Transactional
	public void likeYou(User user,String taregetUserId){
		User friend = dao.load(new Long(taregetUserId));
		
		Friends friends = dao.findFriends(user, friend);
		if(friends != null){
			if(friends.getStatus() != FriendStatus.FRIEND){
				friends.setStatus(FriendStatus.FRIEND);
				friends.setCreated(new Date());
				dao.saveFriends(friends);
				notiManager.likeU(user, friend);
			}
		}else{
			dao.makeFriends(user, friend);
			notiManager.likeU(user, friend);
		}
	}


	@Transactional
	@Override
	public void unlike(User user, String taregetUserId) {
		User friend = dao.load(new Long(taregetUserId));
		
		Friends friends = dao.findFriends(user, friend);
		if(friends != null){
			dao.deleteFriends(friends);
		}
	}

	@Transactional
	@Override
	public User registUser(UserDelegate delegate) throws Exception {
		boolean useSns = (delegate.getSocialType() == null || delegate.getSocialType().length() == 0 ||
				delegate.getSocialType().equals("BIGTURE")) ? false : true;
		
		if(delegate.getLoginId() == null || delegate.getLoginId().length() == 0){
			throw new Exception("LoginId is required");
		}
		
		
		
		if( !useSns && 
				(delegate.getPassword() == null || delegate.getPassword().length() == 0)){
			//SNS사용자가 아닌경우.
			throw new Exception("Password is required");
		}
		
		if(delegate.getGender() == null || delegate.getGender().length() == 0){
			throw new Exception("Gender is required");
		}
		
		if(delegate.getNickName() == null || delegate.getNickName().length() == 0){
			throw new Exception("Nickname is required");
		}
		
		if(delegate.getCountryCode() == null || delegate.getCountryCode().length() == 0){
			throw new Exception("CountryCode is required");
		}
		
		if(findByLoginId(delegate.getLoginId()) != null){
			throw new Exception("Already exist account!");
		}
		
		User user = null;
		boolean isNew = false;
		if(useSns){
			user = dao.findBySocialId(SocialType.valueOf(delegate.getSocialType()), delegate.getSocialId());
			if(user == null){
				user = new User();
				isNew = true;
			}
		}else{
			user = new User();
			isNew = true;
		}
		
		user.setLoginId(delegate.getLoginId());
		user.setEmail(delegate.getLoginId());
		user.setNickName(delegate.getNickName());
		user.setBirthday(fm.parse(delegate.getBirthDay()));
		user.setGender(Gender.valueOf(delegate.getGender()));
		
		if(useSns){
			if(delegate.getSocialType().equals(SocialType.FACEBOOK.name())){
				user.setFacebook(delegate.getSocialId());
			}else{
				user.setTwitter(delegate.getSocialId());
			}
			user.setStatus(UserStatus.ACTIVE);
		}else{
			user.setPwd(new String(CommonUtils.md5(delegate.getPassword())));
			user.setStatus(UserStatus.VERIFY_REQUESTED);
		}
		user.setCountry(masterDao.findByCode(delegate.getCountryCode()));
		
		dao.addRoleToUser(user,Role.USER_ROLE);
		if(isNew){
			
			if( dao.findActiveByLoginId(user.getLoginId()) != null) {
				throw new Exception("Already exist account!");
			}
			
			
			dao.createUser(user);
			
			PushSettings settings = new PushSettings();
			settings.setUser(user);
			settings.setCardPush(true);
			settings.setClassPush(true);
			settings.setContestPush(true);
			settings.setLikePush(true);
			settings.setMyClassPush(true);
			settings.setPicMePush(true);
			settings.setTalkCommentPush(true);
			settings.setStoryPush(true);
			updateSettings(settings);
		}else{
			dao.updateUser(user);
		}
		
		if(!useSns){
			//인증 메일을 전송한다.
			sendVerifyEmail(user);
		}
		
		return user;
	}

	
	
	@Override
	public void sendVerifyEmail(User user) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("name", user.getNickName());
		vars.put("id",user.getId());
		
		String title = "[Bigture] Please verify your registration";
		String template = "verifyEn";
		
		if(user.isKorean()){
			title = "[빅쳐] 회원가입 인증을 해주세요.";
			template = "verifyKo";
		}
		
		//mailService.sendEmail(user.getLoginId(), title, template, vars);
		sesService.sendEmail(user.getLoginId(), title, template, vars);
	}

	public void sendPasswordEmail(User user,String pwd) throws Exception{
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("name", user.getNickName());
		vars.put("newPassword",pwd);
		
		String title = "[Bigture] Temporary Password from Bigture";
		String template = "passwordEn";
		
		if(user.isKorean()){
			title = "[빅쳐]" + user.getNickName() + " 님,임시 비밀번호가 발급되었습니다.";
			template = "passwordKo";
		}
		//mailService.sendEmail(user.getLoginId(), title, template, vars);
		sesService.sendEmail(user.getLoginId(), title, template, vars);
	}

	@Transactional
	@Override
	public User activateUser(Long userId) throws Exception {
		User user = dao.load(userId);
		if(user == null){
			throw new Exception("User does not exist");
		}
		
		user.setStatus(UserStatus.ACTIVE);
		dao.updateUser(user);
		return user;
	}



	@Override
	public User loadUser(Long id) {
		return dao.load(id);
	}


	@Transactional
	@Override
	public void resetPassword(String loginId) throws Exception {
		
		Date curr = new Date();
		String newPassword = curr.getTime()+"";
		
		newPassword = newPassword.substring(0, 6);
		
		User user = findByLoginId(loginId);
		if(user == null){
			throw new Exception("Login Id does not exist");
		}
		
		user.setPwd(new String(CommonUtils.md5(newPassword)));
		user.setLoginToken(makeLoginToken(user));
		Calendar cur = Calendar.getInstance();
		cur.add(Calendar.MONTH, 1);	//우선 한달뒤로 설정한다. 
		user.setTokenExpireDate(cur.getTime());
		
		dao.updateUser(user);
		
		sendPasswordEmail(user,newPassword);
	}


	@Transactional
	@Override
	public boolean updateRegistrationId(Long userId, String regId) {
		User user = dao.load(userId);
		
		if(user != null){
			user.setGcmId(regId);
			dao.updateUser(user);
			
			//동일한 regId로 되어있는 다른 사용자의 regId를 지운다.
			List<User> users = dao.findUserByGcmId(regId);
			for(User u : users){
				if(u.getId().equals(user.getId())){
					continue;
				}
				
				u.setGcmId(null);
				dao.updateUser(u);
			}
			
			return true;
		}
		
		return false;
	}

	

	@Override
	public User findBySocialId(SocialType type, String socialId) {
		return dao.findBySocialId(type, socialId);
	}



	@Override
	public boolean alreadyLikeYou(User user, Long targetId) {
		User target = dao.load(targetId);
		
		Friends friends = dao.findFriends(user, target);
		if(friends == null || !friends.getStatus().equals(FriendStatus.FRIEND))
			return false;
		return true;
	}


	@Transactional
	@Override
	public void saveCareer(User owner, Long id, String content,
			String startTime, String endTime) throws Exception{
		Career career = null;
		if(id == null){
			career = new Career();
			career.setUser(owner);
		}else{
			career = dao.loadCareer(id);
		}
		
		career.setCareer(content);
		career.setStartTime(fm.parse(startTime));
		if(endTime != null && !"".equals(endTime)){
			career.setEndTime(fm.parse(endTime));
		}
		
		if(id == null){
			dao.createCareer(career);
		}else{
			dao.updateCareer(career);
		}
	}


	@Transactional
	@Override
	public void deleteCareer(User currentUser, Long id) throws Exception {
		Career career = dao.loadCareer(id);
		
		if(!career.getUser().equals(currentUser))
			throw new Exception("You have not permission");
		
		dao.deleteCareer(career);
	}



	@Override
	public List<Career> findCareer(Long ownerId) {
		return dao.findCareer(ownerId);
	}



	@Override
	public List<News> findNews(Long ownerId) {
		return dao.findNews(ownerId);
	}



	@Override
	public News loadNews(Long newsId) {
		return dao.loadNews(newsId);
	}


	@Transactional
	@Override
	public void saveNews(User owner, NewsDelegate delegate) throws Exception {
		News news = null;
		if(delegate.getId() == null){
			news = new News();
			news.setContents(delegate.getContents());
			news.setTitle(delegate.getTitle());
			news.setUser(owner);
			
			if(delegate.getAddedImages() == null){
				news.setImageCount(0);
			}else{
				List<NewsImage> images = new ArrayList<NewsImage>();
				NewsImage master = null;
				//added image는 무조건 temp이미지이다. 그렇기 때문에 여기서 실제 path로 이동을 시키도 데이타는 download가능한 path로 저장시킨다.
				for(int i = 0; i < delegate.getAddedImages().size(); i++){
					 String tmpId = delegate.getAddedImages().get(i);
				
					TempImage tmp = masterDao.loadTempImage( new Long(tmpId) );
					ImageInfo info = imageService.transferFile(tmp, ImagePathType.NEWS);
					NewsImage img = new NewsImage();
					img.setNews(news);
					img.setPhoto(info.getPhotoPath());
					img.setThumbnail(info.getThumbnailPath());
					img.setCreated(new Date());
					images.add(img);
					if(i == 0) master = img;
				}
				news.setImageCount(delegate.getAddedImages().size());
				news.setImages(images);
				
				if(master != null){
					news.setPhoto(master.getPhoto());
					news.setThumbnail(master.getThumbnail());
				}
			}
			
			dao.createNews(news);
		}else{
			news = dao.loadNews(delegate.getId());
			news.setContents(delegate.getContents());
			news.setTitle(delegate.getTitle());
			
			List<NewsImage> images = news.getImages();
			if(images == null){
				images = new ArrayList<NewsImage>();
				news.setImages(images);
			}
			
			if(delegate.getAddedImages() != null){
				//added image는 무조건 temp이미지이다. 그렇기 때문에 여기서 실제 path로 이동을 시키도 데이타는 download가능한 path로 저장시킨다.
				for(String tmpId : delegate.getAddedImages()){
					TempImage tmp = masterDao.loadTempImage( new Long(tmpId) );
					ImageInfo info = imageService.transferFile(tmp, ImagePathType.NEWS);
					NewsImage img = new NewsImage();
					img.setNews(news);
					img.setPhoto(info.getPhotoPath());
					img.setThumbnail(info.getThumbnailPath());
					images.add(img);
				}
				
			}
			boolean mainDeleted = false;
			if(delegate.getDeletedImages() != null && delegate.getDeletedImages().size() > 0){
				Map<Long,String> keyMap = new HashMap<Long,String>();
				for(String dId : delegate.getDeletedImages()){
					keyMap.put(new Long(dId), dId);
				}
				
				Iterator<NewsImage> iter = images.iterator();
				while(iter.hasNext()){
					NewsImage n = iter.next();
					if(keyMap.containsKey(n.getId())){
						if(n.getPhoto().equals(news.getPhoto())){
							mainDeleted = true;
						}
						iter.remove();
					}
				}
			}
			
			news.setImageCount(images.size());
			if(mainDeleted){
				if(images.size() > 0){
					NewsImage master = images.get(0);
					
					news.setPhoto(master.getPhoto());
					news.setThumbnail(master.getThumbnail());
				}else{
					news.setPhoto(null);
					news.setThumbnail(null);
				}
				
			}
			
			dao.updateNews(news);
		}
		
	}


	@Transactional
	@Override
	public void deleteNews(User user, Long newsId) throws Exception {
		News news = dao.loadNews(newsId);
		if(!news.getUser().equals(user)){
			throw new Exception("You have not permission");
		}
		dao.deleteNews(news);
	}



	@Override
	public List<FriendGroup> getGroups(User user) {
		return dao.getGroups(user);
	}


	@Transactional
	@Override
	public void saveGroup(Long groupId, User owner, String groupName)
			throws Exception {
		FriendGroup group = null;
		if(groupId == null){
			group = new FriendGroup();
			group.setOwner(owner);
		}else{
			group = dao.loadGroup(groupId);
		}
		
		if(!group.getOwner().equals(owner)){
			throw new Exception("You have not permission");
		}
		group.setGroupName(groupName);
		
		if(groupId == null){
			dao.createGroup(group);
		}else{
			dao.updateGroup(group);
		}
	}


	@Transactional
	@Override
	public void deleteGroup(Long groupId, User owner) throws Exception {
		FriendGroup group = dao.loadGroup(groupId);
		if(!group.getOwner().equals(owner)){
			throw new Exception("You have not permission");
		}
		
		dao.deleteGroup(group);
	}



	@Override
	public List<SimpleProfileDelegate> getGroupMembers(Long groupId, int start,
			int limits) {
		FriendGroup group = dao.loadGroup(groupId);
		List<User> friends = dao.getGroupMembers(group, start, limits);
		
		return convertDelegate(friends,true);
	}

	private List<SimpleProfileDelegate> convertDelegate(List<User> users,boolean alreadyLike){
		List<SimpleProfileDelegate> results = new ArrayList<SimpleProfileDelegate>();
		
		for(User user : users){
			SimpleProfileDelegate delegate = new SimpleProfileDelegate(user);
			delegate.setAlreadyLike(alreadyLike);
			results.add(delegate);
		}
		
		return results;
	}

	@Override
	public List<SimpleProfileDelegate> searchUser(String keyword, User owner,
			int start, int limits) {
		List<User> users = dao.searchUsers(keyword, start, limits);
		
		List<User> likeU = dao.findLike(owner, false, null, null);
		
		return convertDelegate(users, likeU,owner);
	}

	private List<SimpleProfileDelegate> convertDelegate(List<User> users,List<User> likeU,User except){
		Map<Long,User> likeMap = new HashMap<Long, User>();
		
		if(likeU != null){
			for(User user : likeU){
				likeMap.put(user.getId(), user);
			}
		}
		
		List<SimpleProfileDelegate> results = new ArrayList<SimpleProfileDelegate>();
		
		for(User user : users){
			if(user.getId().equals(except.getId())) continue;
			SimpleProfileDelegate delegate = new SimpleProfileDelegate(user);
			if(likeU == null){
				delegate.setAlreadyLike(true);
			}else{
				delegate.setAlreadyLike(likeMap.containsKey(user.getId()));
			}
			results.add(delegate);
		}
		
		return results;
	}

	@Transactional
	@Override
	public void editGroupMember(FriendGroupDelegate delegate, User owner)
			throws Exception {
		FriendGroup group = dao.loadGroup(delegate.getId());
		if(!group.getOwner().equals(owner)){
			throw new Exception("You have not permission");
		}
		
		List<FriendGroupMember> members = group.getMembers();
		if(members == null){
			members = new ArrayList<FriendGroupMember>();
			group.setMembers(members);
		}
		
		for(String id : delegate.getAddedMemberIds()){
			Friends friends = dao.findFriends(owner,dao.load(new Long(id)));
			if(friends != null){
				FriendGroupMember member = new FriendGroupMember();
				member.setCreated(new Date());
				member.setGroup(group);
				member.setFriend(friends);
				members.add(member);
			}
		}
		
		for(String id : delegate.getDeletedMemberIds()){
			Friends friends = dao.findFriends(owner,dao.load(new Long(id)));
			
			if(friends == null) continue;
			
			Iterator<FriendGroupMember> iter = members.iterator();
			while(iter.hasNext()){
				FriendGroupMember member = iter.next();
				if(member.getFriend().equals(friends)){
					iter.remove();
					dao.deleteGroupMember(member);
				}
			}
		}
		group.setFriendCount(group.getMembers().size());
		
		dao.updateGroup(group);
	}



	@Override
	public int getCountGroup(User owner) {
		return dao.getCountGroup(owner);
	}


	/**
	 * 한번에 해당 user의 like group과 해당 group에 속한 member까지 얻어온다.
	 */
	@Override
	public List<FriendGroupMemberDelegate> findGroupAndMember(User owner) {
		List<FriendGroupMemberDelegate> results = new ArrayList<FriendGroupMemberDelegate>();
		Map<Long,FriendGroupMemberDelegate> groupMap = new HashMap<Long, FriendGroupMemberDelegate>();
		
		List<FriendGroupMember> members = dao.findMembers(owner);
		
		for(FriendGroupMember g : members){
			FriendGroupMemberDelegate delegate = null;
			if(groupMap.containsKey(g.getGroup().getId())){
				delegate = groupMap.get(g.getGroup().getId());
			}else{
				delegate = new FriendGroupMemberDelegate();
				delegate.setId(g.getGroup().getId());
				delegate.setGroupName(g.getGroup().getGroupName());
				delegate.setFriendCount(g.getGroup().getFriendCount());
				delegate.setMembers(new ArrayList<SimpleProfileDelegate>());
				groupMap.put(g.getGroup().getId(), delegate);
				results.add(delegate);
			}
			
			SimpleProfileDelegate sd = new SimpleProfileDelegate(g.getFriend().getFriend());
			delegate.getMembers().add(sd);
			
		}
		
		return results;
	}



	@Override
	public List<SimpleProfileDelegate> searchLikeYous(String keyword,
			User owner, int start, int limits) {
		List<User> users = dao.searchLikeYous(keyword, owner, start, limits);
		
		return convertDelegate(users, null,owner);
	}


	@Transactional
	@Override
	public void updateStickerCount(User user, StickerType sticker) {
		synchronized (user) {
			switch(sticker){
			case TYPE_AWESOME:
				user.setAwesomeCount(user.getAwesomeCount() + 1);
				break;
			case TYPE_FANTASTIC:
				user.setFantasticCount(user.getFantasticCount() + 1);
				break;
			case TYPE_FUN:
				user.setFunCount(user.getFunCount() + 1);
				break;
			case TYPE_LOVE:
				user.setLoveCount(user.getLoveCount() + 1);
				break;
			case TYPE_WOW:
				user.setWowCount(user.getWowCount() + 1);
				break;
			}
			dao.updateUser(user);
		}
	}



	@Override
	public ExpertListWrapper findExpertByName() {
		List<User> experts = dao.findExperts();
		
		Map<Character,List<ExpertDelegate>> nameMap = new HashMap<Character, List<ExpertDelegate>>();
		
		for(User user : experts){
			String name = user.getNickName();
			Character firstCh = CommonUtils.getFirstElement(name);
			List<ExpertDelegate> users = null;
			if(nameMap.containsKey(firstCh)){
				users = nameMap.get(firstCh);
			}else{
				users = new ArrayList<ExpertDelegate>();
				nameMap.put(firstCh, users);
			}
			
			ExpertDelegate delegate = new ExpertDelegate(user);
			users.add(delegate);
		}
		
		ExpertListWrapper wrapper = new ExpertListWrapper();
		List<Character> keyArray = new ArrayList<Character>(nameMap.keySet());
		Collections.sort(keyArray);
		for(Character ch : keyArray){
			WordWrapper word = new WordWrapper();
			word.setInitialWord(ch.toString());
			word.setExperts(nameMap.get(ch));
			wrapper.addWord(word);
		}
		
		return wrapper;
	}


	@Override
	public List<NameDelegate> listJobs() {
		List<String> results = dao.listJobs();
		
		List<NameDelegate> delegates = new ArrayList<NameDelegate>();
		
		for(String result : results){
			NameDelegate delegate = new NameDelegate();
			delegate.setName(result);
			delegates.add(delegate);
		}
		
		return delegates;
	}



	@Override
	public List<ExpertDelegate> findExpertByJob(String jobName) {
		List<ExpertDelegate> delegates = new ArrayList<ExpertDelegate>();
		
		List<User> results = dao.findExpertsByJob(jobName);
		for(User user : results){
			ExpertDelegate delegate = new ExpertDelegate(user);
			delegates.add(delegate);
		}
		
		return delegates;
	}



	@Override
	public List<ExpertDelegate> findExpertByNameCountry(String keyword) {
		List<ExpertDelegate> delegates = new ArrayList<ExpertDelegate>();
		
		List<User> results = dao.findExpertsByNameCountry(keyword);
		for(User user : results){
			ExpertDelegate delegate = new ExpertDelegate(user);
			delegates.add(delegate);
		}
		
		return delegates;
	}



	@Override
	public List<ExpertDelegate> getSimpleExperts(int limits) {
		List<ExpertDelegate> delegates = new ArrayList<ExpertDelegate>();
		
		List<User> results = dao.findExpertsByLastActivity(0, limits);
		for(User user : results){
			ExpertDelegate delegate = new ExpertDelegate(user);
			delegates.add(delegate);
		}
		
		return delegates;
	}


	@Transactional
	@Override
	public void updateUser(User user, UserDelegate delegate) throws Exception {
		
		if( delegate.getSaveMode().equals("ALL") || delegate.getSaveMode().equals("ONLY_PROFILE")){
			user.setNickName(delegate.getNickName());
			user.setBirthday(fm.parse(delegate.getBirthDay()));
			user.setGender(Gender.valueOf(delegate.getGender()));
			user.setCountry(masterDao.findByCode(delegate.getCountryCode()));
			user.setComment(delegate.getComment());
			user.setJob(delegate.getJob());
			
			if(delegate.getTempImageId() != null && !"".equals(delegate.getTempImageId())){
				TempImage tmp = masterDao.loadTempImage( new Long(delegate.getTempImageId()) );
				ImageInfo info = imageService.transferFile(tmp, ImagePathType.PROFILE);
				user.setPhotoPath(info.getThumbnailPath());
			}
			
			dao.updateUser(user);
		
		}
		
		PushSettings settings = findSetting(user);
		if(settings == null){
			settings = new PushSettings();
			settings.setUser(user);
			settings.setCardPush(true);
			settings.setClassPush(true);
			settings.setContestPush(true);
			settings.setLikePush(true);
			settings.setMyClassPush(true);
			settings.setPicMePush(true);
			settings.setTalkCommentPush(true);
			settings.setStoryPush(true);
			settings.setPushAlert(true);
		}
		
		if( delegate.getSaveMode().equals("ALL") || delegate.getSaveMode().equals("ONLY_SETTING")){
			settings.setCardPush(delegate.isCardPush());
			settings.setClassPush(delegate.isClassPush());
			settings.setContestPush(delegate.isContestPush());
			settings.setLikePush(delegate.isLikePush());
			settings.setMyClassPush(delegate.isMyClassPush());
			settings.setPicMePush(delegate.isPicMePush());
			settings.setTalkCommentPush(delegate.isTalkCommentPush());
			settings.setStoryPush(delegate.isStoryPush());
			settings.setPushAlert(delegate.isPushAlert());
		}
		updateSettings(settings);
	}


	@Transactional
	@Override
	public void changePassword(User user, String currentPassword,
			String newPassword) throws Exception {
		String curr = new String(CommonUtils.md5(currentPassword));
		
		if(!user.getPwd().equals(curr)){
			throw new Exception("Password does not match!");
		}
		
		String newPa =  new String(CommonUtils.md5(newPassword));
		user.setPwd(newPa);
		dao.updateUser(user);
	}

	@Override
	public PushSettings findSetting(User user) {
		return dao.loadSettings(user);
	}


	@Transactional
	@Override
	public void updateSettings(PushSettings setting) {
		if(setting.getId() == null){
			dao.createSettings(setting);
		}else{
			dao.updateSettings(setting);
		}
		
	}



	@Override
	public List<NameDelegate> listGroupByJob() {
		List<User> experts = dao.findExpertsByJob();
		
		Map<String,List<ExpertDelegate>> jobMap = new HashMap<String, List<ExpertDelegate>>(); 
		for(User user : experts){
			if(user.getJob() == null) continue;
			if(!jobMap.containsKey(user.getJob())){
				List<ExpertDelegate> users = new ArrayList<ExpertDelegate>();
				jobMap.put(user.getJob(), users);
			}
			List<ExpertDelegate> users = jobMap.get(user.getJob());
			ExpertDelegate del = new ExpertDelegate(user);
			users.add(del);
		}
		
		List<String> jobs = new ArrayList<String>(jobMap.keySet());
		Collections.sort(jobs);
		List<NameDelegate> results = new ArrayList<NameDelegate>();
		
		for(String job : jobs){
			NameDelegate del = new NameDelegate();
			del.setName(job);
			del.setExperts(jobMap.get(job));
			results.add(del);
		}
		
		return results;
	}


	@Transactional
	@Override
	public void updateUser(User user) {
		dao.updateUser(user);
	}



	@Override
	public List<UserSearchDelegate> searchUser(UserSearchModel condition) {
		
		UserStatus status = condition.isVerified() ? UserStatus.ACTIVE : UserStatus.VERIFY_REQUESTED;
		
		List<User> users;
		
		if(condition.getUserSearchType().equals(UserSearchType.EMAIL)){
			users = dao.searchUsers(condition.getKeyword(), null, status, condition.getUserSearchRole().equals(UserSearchRole.NORMAL) ? false : true, condition.getStart(), condition.getLimit());
		}else{
			users = dao.searchUsers(null, condition.getKeyword(), status, condition.getUserSearchRole().equals(UserSearchRole.NORMAL) ? false : true, condition.getStart(), condition.getLimit());
		}
		
		List<UserSearchDelegate> results = new ArrayList<UserSearchDelegate>();
		for(User user : users){
			UserSearchDelegate delegate = new UserSearchDelegate(user);
			results.add(delegate);
		}
		
		return results;
	}
	

	@Override
	public int countUser(UserSearchModel condition) {
		UserStatus status = condition.isVerified() ? UserStatus.ACTIVE : UserStatus.VERIFY_REQUESTED;
		
		int count = 0;
		
		if(condition.getUserSearchType().equals(UserSearchType.EMAIL)){
			count = dao.countUsers(condition.getKeyword(), null, status, condition.getUserSearchRole().equals(UserSearchRole.NORMAL) ? false : true);
		}else{
			count = dao.countUsers(null, condition.getKeyword(), status, condition.getUserSearchRole().equals(UserSearchRole.NORMAL) ? false : true);
		}
		
		return count;
	}
	
	
	
}
