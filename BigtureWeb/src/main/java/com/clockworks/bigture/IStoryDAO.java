package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.entity.Story;
import com.clockworks.bigture.entity.StoryAfterReading;
import com.clockworks.bigture.entity.StoryCollection;
import com.clockworks.bigture.entity.StoryMember;
import com.clockworks.bigture.entity.StoryPage;
import com.clockworks.bigture.entity.StoryPageArtwork;
import com.clockworks.bigture.entity.StoryStatus;
import com.clockworks.bigture.entity.User;
import com.opensymphony.module.sitemesh.Page;

public interface IStoryDAO {
	List<Story> findStory(User owner,boolean justOwned,StoryStatus status,SortOption sort,int start,int limit,boolean excludeDraft);
	List<Story> findOwnedStory(User owner,List<StoryStatus> states,SimpleSortOption sort,int start,int limit);
	
	List<StoryMember> findStoryMember(User owner);
	List<StoryCollection> findCollection(User owner);
	List<Story> findJoinedStory(User owner,List<StoryStatus> states,SimpleSortOption sort,int start,int limit);
	List<Story> findCollectedStory(User owner,List<StoryStatus> states,SimpleSortOption sort,int start,int limit);
	List<Story> listAllStory(List<StoryStatus> states,SimpleSortOption sort,int start,int limit);

	int countAllStory(List<StoryStatus> states);
	
	int countOwnedStory(User owner,List<StoryStatus> states);
	int countJoinedStory(User owner,List<StoryStatus> states);
	int countCollectedStory(User owner,List<StoryStatus> states);
	
	Story loadStory(Long storyId);
	void createStory(Story story);
	void updateStory(Story story);
	
	
	void createCollection(StoryCollection collection);
	void deleteCollection(StoryCollection collection);
	StoryCollection getCollection(Story story,User owner);
	List<StoryPage> listPages(Story story);
	List<StoryPageArtwork> listPageArtworks(Story story);
	List<StoryPageArtwork> listPageArtworks(StoryPage page);
	StoryPage loadPage(Long pageId);
	
	void createAfterReading(StoryAfterReading entity);
	void createPageArtworks(StoryPageArtwork pa);
	List<StoryAfterReading> listAfterReading(Story story);
}
