package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.delegate.PostCardDelegate;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.PostCardReceiver;
import com.clockworks.bigture.entity.User;

public interface IPostCardDAO {
	
	List<PostCardDelegate> getSendedPostCards(User owner,int start,int limits,boolean myPage);
	List<PostCardDelegate> getReceivedPostCards(User receiver,int start,int limits,boolean myPage);
	
	void createCard(PostCard card);
	void updateCard(PostCard card);
	PostCard loadCard(Long id);
	List<PostCardDelegate> getAllPostCards(User user,int start,int limits);
}
