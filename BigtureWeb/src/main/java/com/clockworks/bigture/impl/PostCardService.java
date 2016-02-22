package com.clockworks.bigture.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clockworks.bigture.IArtworkDAO;
import com.clockworks.bigture.IArtworkService;
import com.clockworks.bigture.INotificationManager;
import com.clockworks.bigture.IPostCardDAO;
import com.clockworks.bigture.IPostCardService;
import com.clockworks.bigture.delegate.PostCardDelegate;
import com.clockworks.bigture.delegate.PostCardSendDelegate;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.PostCardReceiver;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.User;
import com.clockworks.bigture.identity.IUserDAO;

@Service
public class PostCardService implements IPostCardService {
	@Autowired private IPostCardDAO dao;
	@Autowired private IArtworkDAO artworkDao;
	@Autowired private IUserDAO userDao;
	@Autowired private INotificationManager notiManager;
	
	@Override
	public List<PostCardDelegate> getSendedPostCards(User owner, int start,
			int limits, boolean myPage) {
		List<PostCardDelegate> results = dao.getSendedPostCards(owner, start, limits, myPage);
		return results;
	}

	@Override
	public List<PostCardDelegate> getReceivedPostCards(User receiver,
			int start, int limits, boolean myPage) {
		List<PostCardDelegate> results = dao.getReceivedPostCards(receiver, start, limits, myPage);
		
		return results;
	}

	
	
	@Override
	public List<PostCardDelegate> getAllPostCards(User receiver, int start,
			int limits) {
		List<PostCardDelegate> results = dao.getAllPostCards(receiver, start, limits);
		
		return results;
	}

	
	@Transactional
	@Override
	public PostCard updateViewFlag(Long cardId, User currentUser) {
		PostCard card = dao.loadCard(cardId);
		if(card.getReceivers() != null){
			for(PostCardReceiver receiver : card.getReceivers()){
				if(receiver.getReceiver().getId().equals(currentUser.getId())){
					receiver.setViewed(true);
					receiver.setViewDate(new Date());
				}
			}
		}
		dao.updateCard(card);
		return card;
	}

	@Transactional
	@Override
	public void sendPostCard(PostCardSendDelegate delegate, User owner)
			throws Exception {
		PostCard postCard = new PostCard();
		
		postCard.setOwner(owner);
		postCard.setComment(delegate.getComment());
		postCard.setDeleted(false);
		postCard.setReply(delegate.isReply());
		postCard.setShareType(ShareType.PUBLIC);
		
		Artwork artwork = artworkDao.loadArtwork(delegate.getArtworkId());
		postCard.setArtwork(artwork);
		
		postCard.setReceiverCount(delegate.getReceiverIds().size());
		postCard.setReceiverEmails(delegate.getEmails());
		String firstReceiverName = null;
		int i=0;
		List<PostCardReceiver> receivers = new ArrayList<PostCardReceiver>();
		for(String receiverId : delegate.getReceiverIds()){
			User user = userDao.load(new Long(receiverId));
			if(i == 0){
				firstReceiverName = user.getNickName();
			}
			
			PostCardReceiver receiver = new PostCardReceiver();
			receiver.setCard(postCard);
			receiver.setReceiver(user);
			receiver.setDeleted(false);
			receiver.setCreated(new Date());
			receiver.setViewed(false);
			receivers.add(receiver);
			
			i++;
		}
		
		postCard.setReceivers(receivers);
		postCard.setFirstReceiverName(firstReceiverName);
		postCard.setReceiverCount(receivers.size());
		
		dao.createCard(postCard);
		
		if(delegate.getEmails() != null){
			String[] emails = delegate.getEmails().split(",");
			postCard.setEmailReceiverCount(emails.length);
			for(String email : emails){
				//TODO 여기서 email을 보내야한다.
				
			}
		}
		
		for(PostCardReceiver receiver : postCard.getReceivers()){
			notiManager.sendPostcard(postCard, receiver.getReceiver());
		}
		
	}

	@Transactional
	@Override
	public void changeShareMode(Long cardId, User currentUser, boolean open)
			throws Exception {
		PostCard card = dao.loadCard(cardId);
		if(!card.getOwner().getId().equals(currentUser.getId())){
			throw new Exception("No Permission");
		}
		
		card.setShareType(open ? ShareType.PUBLIC : ShareType.PRIVATE);
		dao.updateCard(card);
	}

	@Transactional
	@Override
	public void removeFromList(Long cardId, User currentUser, String listType)
			throws Exception {
		if(listType.equals("SENDED")){
			PostCard card = dao.loadCard(cardId);
			if(!card.getOwner().getId().equals(currentUser.getId())){
				throw new Exception("No Permission");
			}
			
			card.setDeleted(true);
			dao.updateCard(card);
		}else{
			PostCard card = dao.loadCard(cardId);
			for(PostCardReceiver receiver : card.getReceivers()){
				if(receiver.getReceiver().getId().equals(currentUser.getId())){
					receiver.setDeleted(true);
					break;
				}
			}
			dao.updateCard(card);
		}
		
	}
	
	
}
