package com.clockworks.bigture.identity.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.clockworks.bigture.entity.Career;
import com.clockworks.bigture.entity.FriendGroup;
import com.clockworks.bigture.entity.FriendGroupMember;
import com.clockworks.bigture.entity.FriendStatus;
import com.clockworks.bigture.entity.Friends;
import com.clockworks.bigture.entity.News;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.entity.UserRoles;
import com.clockworks.bigture.entity.UserStatus;
import com.clockworks.bigture.identity.IUserDAO;
import com.clockworks.bigture.identity.SocialType;




@Repository("userDAO")
public class UserDAO implements IUserDAO,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8277465541242028189L;

	//private Log log = LogFactory.getLog(UserDAO.class);
	
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	//private DateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
	
	@Override
	public User load(Long id) {
		try{
			Query query = em.createQuery("SELECT a from User a WHERE a.id = :id");
			query.setParameter("id", id);
			return (User)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public User findByLoginId(String loginId) {
		try{
			Query query = em.createQuery("SELECT a from User a WHERE a.loginId = :loginId");
			query.setParameter("loginId", loginId);
			//query.setHint("org.hibernate.cacheable", true);
			return (User)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	
	
	@Override
	public User findActiveByLoginId(String loginId) {
		try{
			Query query = em.createQuery("SELECT a from User a WHERE a.loginId = :loginId and a.status = :status");
			query.setParameter("loginId", loginId);
			query.setParameter("status", UserStatus.ACTIVE);
			//query.setHint("org.hibernate.cacheable", true);
			return (User)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<User> findByName(String name) {
		try{
			Query query = em.createQuery("SELECT a from User a WHERE a.nickName = :name");
			query.setParameter("name", name);
			//query.setHint("org.hibernate.cacheable", true);
			return query.getResultList();
		}catch(Exception e){
			return null;
		}
	}

	public void createUser(User user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		em.persist(user);
	}
	
	
	public void updateUser(User user) {
		user.setUpdated(new Date());
		em.merge(user);
	}

	/**
	 * 해당 사용자에게 해당 role을 부여한다.
	 * 이미 해당 role이 할당되어있으면 추가적인 작업을 하지는 않는다.
	 * 이 내부에서는 JPA의 State변화를 일으키지 않은다.
	 * 즉 이 함수를 호출한 측에서 User를 persist나 merge를 호출해야 적용된다. 
	 */
	public void addRoleToUser(User user, String roleName) {
		Set<UserRoles> roles = user.getRoles();
		if(roles == null){
			roles = new HashSet<UserRoles>();
			user.setRoles(roles);
		}
		boolean needAdd = true;
		
		for(UserRoles role : roles){
			if(role.getRoleName().equals(roleName)){
				needAdd = false;
				break;
			}
		}
		
		if(needAdd){
			UserRoles newRole = new UserRoles();
			newRole.setUser(user);
			newRole.setRoleName(roleName);
			roles.add(newRole);
		}
	}

	@Override
	public void replaceRoleToUser(User user, String oldRole, String newRoleName) {
		Set<UserRoles> roles = user.getRoles();
		if(roles == null){
			roles = new HashSet<UserRoles>();
			user.setRoles(roles);
		}
		boolean needAdd = true;
		
		Iterator<UserRoles> iter = roles.iterator();
		
		while(iter.hasNext()){
			UserRoles role = iter.next();
			if(role.getRoleName().equals(oldRole))
				iter.remove();
		}
		
		UserRoles newRole = new UserRoles();
		newRole.setUser(user);
		newRole.setRoleName(newRoleName);
		roles.add(newRole);
		
		em.merge(user);
	}

	@Override
	public User loadUser(String userId) {
		return em.getReference(User.class, userId);
	}

	@Override
	public List<User> getUsers(String role) {
		String queryString = "select userRoles.user from UserRoles as userRoles where userRoles.roleName = :role";
		Query query = em.createQuery(queryString);
		query.setParameter("role", role);
		return (List<User>)query.getResultList();
	}

	@Override
	public List<User> findLike(User user, boolean likeMe,Long regionId,String keyword) {
		try{
			StringBuffer queryStr = new StringBuffer("SELECT ");
			
			if(likeMe){
				queryStr.append("a.owner FROM Friends a WHERE a.friend = :user ");
				if(regionId != null){
					queryStr.append("AND a.owner.country.region.id = :regionId ");
				}
				if(keyword != null){
					queryStr.append("AND (a.owner.nickName like :keyword OR a.owner.country.name like :keyword) ");
				}
			}else{
				queryStr.append("a.friend FROM Friends a WHERE a.owner = :user ");
				if(regionId != null){
					queryStr.append("AND a.friend.country.region.id = :regionId ");
				}
				if(keyword != null){
					queryStr.append("AND (a.friend.nickName like :keyword OR a.friend.country.name like :keyword) ");
				}
			}
			
			
			queryStr.append("Order by a.created asc");
			Query query = em.createQuery(queryStr.toString());
			query.setParameter("user", user);
			if(regionId != null){
				query.setParameter("regionId", regionId);
			}
			if(keyword != null){
				query.setParameter("keyword", "%"+keyword+"%");
			}
			
			query.setHint("org.hibernate.cacheable", true);
			return (List<User>)query.getResultList();
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public Friends findFriends(User user, User friend) {
		String sql = "FROM Friends a WHERE a.owner = :owner AND a.friend = :friend";
		
		Query query = em.createQuery(sql);
		query.setParameter("owner", user);
		query.setParameter("friend", friend);
		query.setHint("org.hibernate.cacheable", true);
		
		List<Friends> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		return null;
	}

	@Override
	public void makeFriends(User user, User friend) {
		Friends friends = new Friends();
		friends.setOwner(user);
		friends.setFriend(friend);
		friends.setStatus(FriendStatus.FRIEND);
		friends.setCreated(new Date());
		
		em.persist(friends);
	}

	@Override
	public void saveFriends(Friends friends) {
		em.merge(friends);
	}

	@Override
	public void deleteFriends(Friends friends) {
		em.remove(friends);
	}

	@Override
	public User findBySocialId(SocialType type, String socialId) {
		StringBuffer sql = new StringBuffer("FROM User a WHERE a.status = :status ");
		if(type.equals(SocialType.FACEBOOK)){
			sql.append("AND a.facebook = :socialId");
		}else{
			sql.append("AND a.twitter = :socialId");
		}
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("socialId", socialId);
		List<User> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		return null;
	}

	@Override
	public Career loadCareer(Long id) {
		try{
			Query query = em.createQuery("SELECT a from Career a WHERE a.id = :id");
			query.setParameter("id", id);
			return (Career)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Career> findCareer(Long userId) {
		Query query = em.createQuery("FROM Career a WHERE a.user.id = :userId Order by a.startTime desc");
		query.setParameter("userId", userId);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public void createCareer(Career entity) {
		entity.setCreated(new Date());
		entity.setUpdated(new Date());
		em.persist(entity);
		
	}

	@Override
	public void updateCareer(Career entity) {
		entity.setUpdated(new Date());
		em.merge(entity);
	}

	@Override
	public void deleteCareer(Career entity) {
		em.remove(entity);
	}

	@Override
	public List<News> findNews(Long userId) {
		Query query = em.createQuery("FROM News a WHERE a.user.id = :userId Order by a.created desc");
		query.setParameter("userId", userId);
		//query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public News loadNews(Long id) {
		try{
			Query query = em.createQuery("SELECT a from News a WHERE a.id = :id");
			query.setParameter("id", id);
			return (News)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void createNews(News entity) {
		entity.setCreated(new Date());
		entity.setUpdated(new Date());
		em.persist(entity);
	}

	@Override
	public void updateNews(News entity) {
		entity.setUpdated(new Date());
		em.merge(entity);
	}

	@Override
	public void deleteNews(News entity) {
		em.remove(entity);
		
	}

	@Override
	public FriendGroup loadGroup(Long id) {
		try{
			Query query = em.createQuery("SELECT a from FriendGroup a WHERE a.id = :id");
			query.setParameter("id", id);
			return (FriendGroup)query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void createGroup(FriendGroup group) {
		group.setCreated(new Date());
		group.setUpdated(new Date());
		em.persist(group);
	}

	@Override
	public void updateGroup(FriendGroup group) {
		group.setUpdated(new Date());
		em.merge(group);
	}

	@Override
	public void deleteGroup(FriendGroup group) {
		em.remove(group);
		
	}

	@Override
	public List<FriendGroup> getGroups(User owner) {
		String sql = "FROM FriendGroup a WHERE a.owner = :owner Order by a.groupName asc";
		
		Query query = em.createQuery(sql);
		query.setParameter("owner", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> getGroupMembers(FriendGroup group, int start, int limits) {
		String sql = "SELECT a.friend.friend FROM FriendGroupMember a WHERE a.group = :group Order by a.friend.friend.nickName asc";
		
		Query query = em.createQuery(sql);
		query.setParameter("group", group);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> searchUsers(String keyword, int start, int limits) {
		StringBuffer sql = new StringBuffer("FROM User a WHERE a.status = :status ");
		
		if(keyword != null){
			sql.append("AND (a.country.name like :keyword OR a.nickName like :keyword) ");
		}
		sql.append("Order by a.nickName asc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("status", UserStatus.ACTIVE);
		if(keyword != null)
			query.setParameter("keyword", "%"+keyword+"%");
		query.setFirstResult(start);
		query.setMaxResults(limits);
		
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public int getCountGroup(User owner) {
		Query query = em.createQuery("SELECT count(*) FROM FriendGroup a WHERE a.owner = :owner");
		query.setParameter("owner", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		Number number = (Number)query.getSingleResult();
		
		return number.intValue();
	}

	@Override
	public List<FriendGroupMember> findMembers(User owner) {
		Query query = em.createQuery("FROM FriendGroupMember a WHERE a.group.owner = :owner Order by a.group.groupName asc");
		query.setParameter("owner", owner);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> searchLikeYous(String keyword, User owner, int start,
			int limits) {
		StringBuffer sql = new StringBuffer("SELECT a.friend FROM Friends a WHERE a.owner = :owner ");
		
		if(keyword != null){
			sql.append("AND (a.friend.country.name like :keyword OR a.friend.nickName like :keyword) ");
		}
		sql.append("Order by a.friend.nickName asc");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("owner", owner);
		
		if(keyword != null)
			query.setParameter("keyword", "%"+keyword+"%");
		query.setFirstResult(start);
		query.setMaxResults(limits);
		
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public void deleteGroupMember(FriendGroupMember member) {
		em.remove(member);
	}

	@Override
	public List<User> findExperts() {
		Query query = em.createQuery("SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<String> listJobs() {
		Query query = em.createQuery("SELECT distinct(a.job) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> findExpertsByJob(String job) {
		Query query = em.createQuery("SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName AND a.job = :job");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		query.setParameter("job", job);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> findExpertsByNameCountry(String keyword) {
		String sql = "SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName ";
		if(keyword != null && !"".equals(keyword)){
			sql += " AND (a.nickName like :nickName OR a.country.name like :country)";
		}
		
		Query query = em.createQuery(sql);
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		if(keyword != null && !"".equals(keyword)){
			query.setParameter("nickName", "%"+keyword+"%");
			query.setParameter("country", "%"+keyword+"%");
		}
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> findExpertsByLastActivity(int start, int limits) {
		Query query = em.createQuery("SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName Order by a.lastLoginDate desc");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> listNormalUsers() {
		Query query = em.createQuery("SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public PushSettings loadSettings(User user) {
		Query query = em.createQuery("FROM PushSettings a WHERE a.user = :user");
		query.setParameter("user", user);
		query.setHint("org.hibernate.cacheable", true);
		
		List<PushSettings> results = query.getResultList();
		if(results.size() > 0){
			return results.get(0);
		}
		return null;
	}

	@Override
	public void createSettings(PushSettings settings) {
		settings.setCreated(new Date());
		settings.setUpdated(new Date());
		em.persist(settings);
	}

	@Override
	public void updateSettings(PushSettings settings) {
		settings.setUpdated(new Date());
		em.merge(settings);
	}

	@Override
	public List<User> findExpertsByJob() {
		Query query = em.createQuery("SELECT distinct(a) FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName Order by a.job asc");
		query.setParameter("status", UserStatus.ACTIVE);
		query.setParameter("roleName", "ROLE_USER_EXPERT");
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	
	@Override
	public List<User> findUserByGcmId(String gcmId) {
		Query query = em.createQuery("FROM User a WHERE a.gcmId = :gcmId");
		query.setParameter("gcmId", gcmId);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public List<User> searchUsers(String email, String nickName,UserStatus status, boolean isExpert, int start, int limits) {
		Query query = makeQuery(false, email, nickName, status, isExpert);
		query.setFirstResult(start);
		query.setMaxResults(limits);
		query.setHint("org.hibernate.cacheable", true);
		
		return query.getResultList();
	}

	@Override
	public int countUsers(String email, String nickName, UserStatus status, boolean isExpert) {
		Query query = makeQuery(true, email, nickName, status, isExpert);
		query.setHint("org.hibernate.cacheable", true);
		
		return ((Number)query.getSingleResult()).intValue();
	}

	private Query makeQuery(boolean isCount,String email, String nickName, UserStatus status, boolean isExpert ){
		StringBuilder str = new StringBuilder();
		
		if(isCount){
			str.append("SELECT count(*) ");
		}
		
		str.append("FROM User a join a.roles b WHERE a.status = :status AND b.roleName = :roleName ");
	
		if(email != null){
			str.append("AND a.loginId like :email ");
		}
		if(nickName != null){
			str.append("AND a.nickName like :nickName ");
		}
		
		
		Query query = em.createQuery(str.toString());
		query.setParameter("status", status);
		if(isExpert){
			query.setParameter("roleName", "ROLE_USER_EXPERT");
		}else{
			query.setParameter("roleName", "ROLE_USER");
		}
		
		if(email != null){
			query.setParameter("email", email);
		}
		if(nickName != null){
			query.setParameter("nickName", nickName);
		}
		
		return query;
	}
	
	
}
