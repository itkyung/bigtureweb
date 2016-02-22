package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.delegate.PostCardDelegate;
import com.clockworks.bigture.delegate.PostCardSendDelegate;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.User;

public interface IPostCardService {
	
	List<PostCardDelegate> getSendedPostCards(User owner,int start,int limits,boolean myPage);
	List<PostCardDelegate> getReceivedPostCards(User receiver,int start,int limits,boolean myPage);
	List<PostCardDelegate> getAllPostCards(User receiver,int start,int limits);
	
	void sendPostCard(PostCardSendDelegate delegate,User owner) throws Exception;
	void changeShareMode(Long cardId,User currentUser,boolean open) throws Exception;
	void removeFromList(Long cardId,User currentUser,String listType) throws Exception;
	PostCard updateViewFlag(Long cardId,User currentUser);
}
