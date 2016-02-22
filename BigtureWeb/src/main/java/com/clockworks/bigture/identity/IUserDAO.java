package com.clockworks.bigture.identity;

import java.util.List;

import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.FriendGroup;
import com.clockworks.bigture.entity.FriendGroupMember;
import com.clockworks.bigture.entity.Friends;
import com.clockworks.bigture.entity.News;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserStatus;




public interface IUserDAO {
	User load(Long id);
	User findByLoginId(String loginId);	
	User findActiveByLoginId(String loginId);
	List<User> findByName(String name);
	User findBySocialId(SocialType type,String socialId);
	void createUser(User user);
	void addRoleToUser(User user, String roleName);
	void replaceRoleToUser(User user,String oldRole,String newRole);
	void updateUser(User user);
	User loadUser(String userId);
	
	List<User> getUsers(String role);
	List<User> searchUsers(String keyword,int start,int limits);
	List<User> searchUsers(String email,String nickName,UserStatus status,boolean isExpert, int start,int limits);
	int countUsers(String email,String nickName,UserStatus status,boolean isExpert);
	
	List<User> searchLikeYous(String keyword,User owner,int start,int limits);
	List<User> listNormalUsers();
	
	List<User> findLike(User user,boolean likeMe,Long regionId,String keyword);
	
	List<User> findUserByGcmId(String gcmId);
	
	Friends findFriends(User user,User friend);
	void makeFriends(User user,User friend);
	void saveFriends(Friends friends);
	void deleteFriends(Friends friends);
	
	Career loadCareer(Long id);
	List<Career> findCareer(Long userId);
	void createCareer(Career entity);
	void updateCareer(Career entity);
	void deleteCareer(Career entity);
	
	List<News> findNews(Long userId);
	News loadNews(Long id);
	void createNews(News entity);
	void updateNews(News entity);
	void deleteNews(News entity);
	
	FriendGroup loadGroup(Long id);
	void createGroup(FriendGroup group);
	void updateGroup(FriendGroup group);
	void deleteGroup(FriendGroup group);
	
	List<FriendGroup> getGroups(User owner);
	List<User> getGroupMembers(FriendGroup group,int start,int limits);
	int getCountGroup(User owner);
	List<FriendGroupMember> findMembers(User owner);
	void deleteGroupMember(FriendGroupMember member);
	List<User> findExperts();
	List<String> listJobs();
	List<User> findExpertsByJob(String job);
	List<User> findExpertsByNameCountry(String keyword);
	List<User> findExpertsByLastActivity(int start,int limits);
	List<User> findExpertsByJob();
	
	PushSettings loadSettings(User user);
	void createSettings(PushSettings settings);
	void updateSettings(PushSettings settings);
	
}
