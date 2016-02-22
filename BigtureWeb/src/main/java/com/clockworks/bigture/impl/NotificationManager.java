package com.clockworks.bigture.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.IContestDAO;
import com.clockworks.bigture.IContestService;
import com.clockworks.bigture.IIOsPush;
import com.clockworks.bigture.INotificationDAO;
import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.IAndroidPush;
import com.clockworks.bigture.delegate.NoticeDelegate;
import com.clockworks.bigture.delegate.NotificationGroupDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassMember;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.Notice;
import com.clockworks.bigture.entity.Notification;
import com.clockworks.bigture.entity.NotificationType;
import com.clockworks.bigture.entity.OsType;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.PushSettings;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.IUserDAO;

@Service
public class NotificationManager implements INotificationManager {
	@Autowired INotificationDAO dao;
	@Autowired IAndroidPush androidPushService;
	@Autowired IIOsPush iosPushService;
	@Autowired IContestDAO contestDao;
	@Autowired IUserDAO userDao;
	
	DateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
	
	@Transactional
	@Override
	public void writeComment(User actor, Artwork artwork,boolean sticker) {
		if(actor.equals(artwork.getUser())){
			return;
		}
		Notification noti = new Notification();
		noti.setOwner(artwork.getUser());
		noti.setActorId(actor.getId());
		noti.setActorName(actor.getNickName());
		noti.setActorJob(actor.getJob());
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setRefId(artwork.getId());
		noti.setTitle(artwork.getTitle());
		noti.setType(NotificationType.COMMENT);
		noti.setActorImage(actor.getPhotoPath());
		
		String msg=null;
		if(noti.getOwner().isKorean()){
			if(sticker){
				msg = actor.getNickName() +" 님이 " + artwork.getTitle() + "을 감상했습니다.";
			}else{
				msg = actor.getNickName() +" 님이 " + artwork.getTitle() + "에 감상 글을 남겼습니다";
			}
			
		}else{
			if(sticker){
				msg = actor.getNickName() + " has left sticker on " + artwork.getTitle();
			}else{
				msg = actor.getNickName() + " has commented on " + artwork.getTitle();
			}
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti);
	}

	@Transactional
	@Override
	public void spamArtwork(Artwork artwork) {
		Notification noti = new Notification();
		noti.setOwner(artwork.getUser());
	
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setRefId(artwork.getId());
		noti.setTitle(artwork.getTitle());
		noti.setType(NotificationType.SPAM);
	
		String msg=null;
		if(noti.getOwner().isKorean()){
			msg = artwork.getTitle() + " 은 Bigture의 성격과 맞지 않아 숨김처리되었습니다.";
		}else{
			msg = artwork.getTitle() + " is hided "; 
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti);

	}

	@Async
	@Transactional
	@Override
	public void openContest(Contest contest) {
		List<User> users = userDao.listNormalUsers();
		
		for(User user : users){
			String msg = null;
			if(user.isKorean()){
				msg = contest.getMainTitle() + " 컨테스트가 개최중입니다.참가해 보세요!";
			}else{
				msg = contest.getMainTitle() + " is now on";
			}
			saveContestMsg(contest,NotificationType.CONTEST,user,msg);
			try{
				Thread.sleep(200);	
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
	
	private void saveContestMsg(Contest contest,NotificationType type,User receiver,String msg){
		Notification noti = new Notification();
		noti.setOwner(receiver);
	
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setRefId(contest.getId());
		noti.setTitle(contest.getMainTitle());
		noti.setType(type);
		noti.setMsg(msg);
		noti.setContestStartDate(contest.getStartTime().getTime());
		noti.setContestEndDate(contest.getEndTime().getTime());
		
		dao.create(noti);
		
		sendPush(noti,receiver);
	}
	

	@Transactional
	@Override
	public void selectWinner(Contest contest, User winner) {
		Notification noti = new Notification();
		noti.setOwner(winner);
	
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setRefId(contest.getId());
		noti.setTitle(contest.getMainTitle());
		noti.setType(NotificationType.CONTEST_WINNER);
	
		String msg=null;
		if(noti.getOwner().isKorean()){
			msg = "축하합니다! " + contest.getMainTitle() + " 에서 위너로 선정 되셨습니다!";
		}else{
			msg = "Contratuation! You are selected to winner for " + contest.getMainTitle();
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti,winner);
	}

	@Async
	@Transactional
	@Override
	public void remindContest(Contest contest) {
		List<User> users = userDao.listNormalUsers();
		List<User> members = contestDao.findMembers(contest);
		
		for(User user : users){
			if(members.contains(user)) continue;
			String msg = null;
			if(user.isKorean()){
				msg = contest.getMainTitle() + " 컨테스트 마감 하루전입니다. 아직 참가하지 못한 Artist는 서두르세요.";
			}else{
				msg = contest.getMainTitle() + " closing is one day away!";
			}
			saveContestMsg(contest,NotificationType.CONTEST_DUE,user,msg);
			try{
				Thread.sleep(200);	
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}		
	}

	@Async
	@Transactional
	@Override
	public void notice(Notice notice) {
		List<User> users = userDao.listNormalUsers();
		
		for(User user : users){
			Notification noti = new Notification();
			noti.setOwner(user);
		
			noti.setReadFlag(false);
			noti.setDeleted(false);
			noti.setRefId(notice.getId());
			noti.setTitle(notice.getTitle());
			noti.setType(NotificationType.NOTICE);
			noti.setMsg(notice.getContent());
			
			dao.create(noti);
			
			sendPush(noti,user);
			try{
				Thread.sleep(200);	
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
	
	@Transactional
	@Override
	public void likeU(User actor, User owner) {
		Notification noti = new Notification();
		noti.setOwner(owner);
		noti.setActorId(actor.getId());
		noti.setActorName(actor.getNickName());
		noti.setActorJob(actor.getJob());
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setType(NotificationType.LIKEU);
		noti.setActorImage(actor.getPhotoPath());
		
		String msg=null;
		if(noti.getOwner().isKorean()){
			msg = actor.getNickName() +" 님이 Like U 하셨습니다.";
		}else{
			msg = actor.getNickName() + " like U";
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti);
	}

	@Transactional
	@Override
	public void requestPic(User actor, User owner) {
		Notification noti = new Notification();
		noti.setOwner(owner);
		noti.setActorId(actor.getId());
		noti.setActorName(actor.getNickName());
		noti.setActorJob(actor.getJob());
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setType(NotificationType.REQ_PICTURE);
		noti.setActorImage(actor.getPhotoPath());
		
		String msg=null;
		if(noti.getOwner().isKorean()){
			msg = actor.getNickName() +" 님이 그림을 요청하셨습니다. 지금 그려보세요!";
		}else{
			msg = actor.getNickName() + " request you to draw picture";
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti);
	}

	@Transactional
	@Override
	public void sendPostcard(PostCard card, User receiver) {
		Notification noti = new Notification();
		noti.setOwner(receiver);
		noti.setActorId(card.getOwner().getId());
		noti.setActorName(card.getOwner().getNickName());
		noti.setActorJob(card.getOwner().getJob());
		noti.setReadFlag(false);
		noti.setDeleted(false);
		noti.setType(NotificationType.POSTCARD);
		noti.setRefId(card.getId());
		noti.setTitle(card.getComment());
		noti.setActorImage(card.getOwner().getPhotoPath());
		
		String msg=null;
		if(noti.getOwner().isKorean()){
			msg = card.getOwner().getNickName() +" 님이 Postcard를 보냈습니다. 지금 확인해보세요.";
		}else{
			msg = card.getOwner().getNickName() + " sended postcard to you";
		}
		noti.setMsg(msg);
		dao.create(noti);
		
		sendPush(noti);
	}

	@Async
	@Transactional
	@Override
	public void openClass(ArtClass artClass) {
		List<User> users = userDao.listNormalUsers();
		
		for(User user : users){
			Notification noti = new Notification();
			noti.setOwner(user);
			noti.setActorId(artClass.getOwner().getId());
			noti.setActorName(artClass.getOwner().getNickName());
			noti.setActorJob(artClass.getOwner().getJob());
			noti.setReadFlag(false);
			noti.setDeleted(false);
			noti.setType(NotificationType.ARTCLASS);
			noti.setRefId(artClass.getId());
			noti.setTitle(artClass.getClassName());
			noti.setActorImage(artClass.getOwner().getPhotoPath());
			
			String msg=null;
			if(noti.getOwner().isKorean()){
				msg = artClass.getOwner().getNickName() +" 님이 클래스를 오픈했습니다. 지금 확인해보세요.";
			}else{
				msg = artClass.getOwner().getNickName() + " opened class.";
			}
			noti.setMsg(msg);
			dao.create(noti);
			
			sendPush(noti,user);
			try{
				Thread.sleep(200);	
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
		
		
	}

	@Async
	@Transactional
	@Override
	public void inviteClass(ArtClass artClass,List<ArtClassMember> members) {
		for(ArtClassMember member : members){
			Notification noti = new Notification();
			noti.setOwner(member.getMember());
			noti.setActorId(artClass.getOwner().getId());
			noti.setActorName(artClass.getOwner().getNickName());
			noti.setActorJob(artClass.getOwner().getJob());
			noti.setActorImage(artClass.getOwner().getPhotoPath());
			noti.setReadFlag(false);
			noti.setDeleted(false);
			noti.setType(NotificationType.INVITE_CLASS);
			noti.setRefId(artClass.getId());
			noti.setTitle(artClass.getClassName());
			
			String msg=null;
			if(noti.getOwner().isKorean()){
				msg = artClass.getOwner().getNickName() +" 님이 클래스에 초대하였습니다. 지금 참여해보세요.";
			}else{
				msg = artClass.getOwner().getNickName() + " invited you to class";
			}
			noti.setMsg(msg);
			dao.create(noti);
			
			sendPush(noti,member.getMember());
			try{
				Thread.sleep(200);	
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
		
		

	}

	@Transactional
	@Override
	public void completePuzzle(ArtClassPuzzle puzzle) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void deleteNotification(User owner,String createDate) {
		dao.updateDeleteFlag(owner, createDate);
	}
	
	
	@Transactional
	@Override
	public void updateRead(User owner) {
		dao.updateReadFlag(owner);
	}
	
	

	@Override
	public List<NotificationGroupDelegate> listNotification(User owner) {
		List<Notification> results = dao.listNotification(owner, 0, 100);
		List<NotificationGroupDelegate> delegates = new ArrayList<NotificationGroupDelegate>();
		Map<String,NotificationGroupDelegate> map = new LinkedHashMap<String,NotificationGroupDelegate>();
		
		
		for(Notification noti : results){
			NotificationGroupDelegate delegate = null;
			String key = fm.format(noti.getCreated());
			if(map.containsKey(key)){
				delegate = map.get(key);
			}else{
				delegate = new NotificationGroupDelegate();
				delegate.setDate(key);
				map.put(key, delegate);
			}
			
			delegate.getNotifications().add(noti);
		}
		
		delegates.addAll(map.values());
		
		return delegates;
	}

	
	private void sendPush(Notification noti){
		if(noti.getOwner().getOsType().equals(OsType.Android)){
			androidPushService.sendPush(noti.getType().name(), noti.getMsg(), noti.getOwner());
		}else{
			iosPushService.sendPush(noti.getType().name(), noti.getMsg(), noti.getOwner());
		}
	}
	
	private void sendPush(Notification noti,User receiver){
		PushSettings setting = userDao.loadSettings(receiver);
		if(setting != null){
			if(noti.getOwner().getOsType().equals(OsType.Android)){
				androidPushService.sendPush(noti.getType().name(), noti.getMsg(), receiver);
			}else{
				iosPushService.sendPush(noti.getType().name(), noti.getMsg(), receiver);
			}
		}
	}

	@Override
	public List<NoticeDelegate> listNotice(String lang) {
		List<Notice> results = dao.listNotice(lang);
		
		List<NoticeDelegate> delegates = new ArrayList<NoticeDelegate>();
		
		for(Notice row : results){
			NoticeDelegate delegate = new NoticeDelegate(row);
			delegates.add(delegate);
		}
		
		return delegates;
	}
	
	
}
