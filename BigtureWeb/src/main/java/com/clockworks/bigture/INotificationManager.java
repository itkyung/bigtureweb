package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.delegate.NoticeDelegate;
import com.clockworks.bigture.delegate.NotificationGroupDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassMember;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.Notice;
import com.clockworks.bigture.entity.PostCard;
import com.clockworks.bigture.entity.User;

public interface INotificationManager {
	public void writeComment(User actor,Artwork artwork,boolean sticker);
	public void spamArtwork(Artwork artwork);
	public void openContest(Contest contest);
	public void selectWinner(Contest contest,User winner);
	public void remindContest(Contest contest);
	public void notice(Notice notice);
	public void likeU(User actor,User owner);
	public void requestPic(User actor,User owner);
	public void sendPostcard(PostCard card,User receiver);
	public void openClass(ArtClass artClass);
	public void inviteClass(ArtClass artClass,List<ArtClassMember> members);
	public void completePuzzle(ArtClassPuzzle puzzle);
	
	public void deleteNotification(User owner,String createDate);
	public List<NotificationGroupDelegate> listNotification(User owner);
	public void updateRead(User owner);
	
	public List<NoticeDelegate> listNotice(String lang);
}
