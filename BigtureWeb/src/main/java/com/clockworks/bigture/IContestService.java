package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ContestDelegate;
import com.clockworks.bigture.delegate.ContestRankDelegate;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.User;

public interface IContestService {
	List<ContestDelegate> listActiveContest(int start,int limits);
	List<ContestDelegate> listAllContest(int start,int limits);
	int countAllContest();
	List<ArtworkDelegate> listContestArtworks(User currentUser,Long contestId,int start,int limits,SortOption sort);
	int countContestArtworks(Long contestId);
	List<ContestRankDelegate> listWinner(Long contestId);
	Contest loadContest(Long contestId);
	List<ContestDelegate> getSimpleContest(int limits);
}
