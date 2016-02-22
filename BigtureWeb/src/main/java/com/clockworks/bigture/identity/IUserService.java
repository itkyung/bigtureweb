package com.clockworks.bigture.identity;

import java.util.Date;
import java.util.List;

import com.clockworks.bigture.delegate.ArtClassModel;
import com.clockworks.bigture.delegate.ExpertDelegate;
import com.clockworks.bigture.delegate.ExpertListWrapper;
import com.clockworks.bigture.delegate.FriendGroupDelegate;
import com.clockworks.bigture.delegate.FriendGroupMemberDelegate;
import com.clockworks.bigture.delegate.NameDelegate;
import com.clockworks.bigture.delegate.SimpleProfileDelegate;
import com.clockworks.bigture.delegate.NewsDelegate;
import com.clockworks.bigture.delegate.UserDelegate;
import com.clockworks.bigture.delegate.UserSearchDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.FriendGroup;
import com.clockworks.bigture.entity.News;
import com.clockworks.bigture.entity.NewsImage;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.ui.param.UserSearchModel;



public interface IUserService {
	final static String ADMIN_ID = "admin";
	final static String ADMIN_PASSWORD = "admin1234";
		
	void initAdmin();
	void initUser();
	User findByLoginId(String loginId);
	User findBySocialId(SocialType type,String socialId);
	List<User> getUsers(String role);
	User loadUser(Long id);
	
	boolean isValidToken(String loginId,String loginToken);
	boolean updateRegistrationId(Long userId,String regId);
	
	List<User> getLikeMe(User user,Long regionId);
	List<User> getLikeYou(User user,Long regionId,String keyword);
	
	List<FriendGroup> getGroups(User user);
	void saveGroup(Long groupId,User owner,String groupName) throws Exception;
	void deleteGroup(Long groupId,User owner) throws Exception;
	List<SimpleProfileDelegate> getGroupMembers(Long groupId,int start,int limits);
	
	
	boolean alreadyLikeYou(User user,Long targetId);
	void likeYou(User user,String friendId);
	void unlike(User user,String friendId);
	
	User registUser(UserDelegate delegate) throws Exception;
	User activateUser(Long userId) throws Exception;
	
	void updateUser(User user, UserDelegate delegate) throws Exception;
	void changePassword(User user,String currentPassword,String newPassword) throws Exception;
	
	void sendVerifyEmail(User user) throws Exception;
	void resetPassword(String loginId) throws Exception;
	
	void saveCareer(User owner,Long id,String content,String startTime,String endTime) throws Exception;
	void deleteCareer(User currentUser,Long id) throws Exception;
	List<Career> findCareer(Long ownerId);
	
	List<News> findNews(Long ownerId);
	News loadNews(Long newsId);
	void saveNews(User owner,NewsDelegate delegate) throws Exception;
	void deleteNews(User user,Long newsId) throws Exception;
	
	List<SimpleProfileDelegate> searchUser(String keyword,User owner,int start,int limits);
	List<SimpleProfileDelegate> searchLikeYous(String keyword,User owner,int start,int limits);
	void editGroupMember(FriendGroupDelegate delegate,User owner) throws Exception;
	int getCountGroup(User owner);
	
	List<FriendGroupMemberDelegate> findGroupAndMember(User owner);
	
	void updateStickerCount(User user,StickerType sticker);
	
	ExpertListWrapper findExpertByName();
	List<NameDelegate> listJobs();
	List<ExpertDelegate> findExpertByJob(String jobName);
	List<ExpertDelegate> findExpertByNameCountry(String keyword);
	List<ExpertDelegate> getSimpleExperts(int limits);
	List<NameDelegate> listGroupByJob();

	PushSettings findSetting(User user);
	void updateSettings(PushSettings setting);
	
	List<UserSearchDelegate> searchUser(UserSearchModel condition);
	int countUser(UserSearchModel condition);
	
}
