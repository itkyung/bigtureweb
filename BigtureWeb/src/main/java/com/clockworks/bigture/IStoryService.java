package com.clockworks.bigture;

import java.util.List;


import com.clockworks.bigture.delegate.AfterReadingDelegate;
import com.clockworks.bigture.delegate.StoryArtworkDelegate;
import com.clockworks.bigture.delegate.StoryDelegate;
import com.clockworks.bigture.delegate.StoryModel;
import com.clockworks.bigture.delegate.StoryPageDelegate;

import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryAfterReading;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.User;

public interface IStoryService {
	public List<StoryDelegate> findUserStory(User owner,User currentUser, boolean isMy,StoryStatus status,SortOption sort);
	public List<StoryDelegate> findOwnedStory(User owner,StoryStatus status,SimpleSortOption sort,int start,int limits);
	public List<StoryDelegate> findJoinedStory(User owner,StoryStatus status,SimpleSortOption sort,int start,int limits);
	public List<StoryDelegate> findCollectedStory(User owner,StoryStatus status,SimpleSortOption sort,int start,int limits);
	public List<StoryDelegate> findAllStory(User owner,SimpleSortOption sort,int start,int limits); //DRAFT만 제외 
	public List<StoryDelegate> findStoryByOwner(User owner,User currntUser,SimpleSortOption sort,int start,int limits); //DRAFT만 제외 
	public int countAllStory(); //DRAFT만 제외 
	public int countStoryByOwner(User owner);
	public int countOwnedStory(User owner,StoryStatus status);
	public int countJoinedStory(User owner,StoryStatus status);
	public int countCollectedStory(User owner,StoryStatus status);
	
	public boolean addCollection(Long storyId,User owner);
	public boolean removeCollection(Long storyId,User owner);
	public boolean deleteStory(Long storyId,User owner);
	
	List<StoryPageDelegate> listPages(Long storyId);
	List<StoryArtworkDelegate> listStoryArtworks(Long storyId,User currentUser);
	List<StoryArtworkDelegate> listStoryArtworksByPage(Long pageId,User currentUser);
	
	public Story saveOrUpdateStory(User owner,StoryModel model) throws Exception;
	public Story shareStory(User owner,StoryModel model) throws Exception;
	public void registArtwork(User owner,Long storyId,Long artworkId,Long pageId) throws Exception;
	public void registAfterReading(User owner,Long storyId,Long artworkId) throws Exception;
	
	List<AfterReadingDelegate> listAfterReads(Long storyId);
	public Story loadStory(Long storyId);
	List<StoryDelegate> getSimpleStories(int limits);
}
