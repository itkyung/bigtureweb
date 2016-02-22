package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ContestRank;
import com.clockworks.bigture.entity.User;

public interface IContestDAO {
	List<Contest> listActiveContest(int start,int limits);
	List<Contest> listAllContest(int start,int limits);
	int countAllContest();
	
	List<Artwork> listContestArtworks(Contest contest,int start,int limits,SortOption sort);
	int countContestArtworks(Contest contest);
	
	List<ContestRank> listWinner(Contest contest);
	
	Contest loadContest(Long id);
	void createContest(Contest contest);
	void updateContest(Contest contest);
	List<User> findMembers(Contest contest);
}
