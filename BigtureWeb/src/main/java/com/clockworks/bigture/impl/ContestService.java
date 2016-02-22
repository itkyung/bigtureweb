package com.clockworks.bigture.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clockworks.bigture.IArtClassDAO;
import com.clockworks.bigture.IArtworkDAO;
import com.clockworks.bigture.IContestDAO;
import com.clockworks.bigture.IContestService;
import com.clockworks.bigture.SortOption;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ContestDelegate;
import com.clockworks.bigture.delegate.ContestRankDelegate;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ContestRank;
import com.clockworks.bigture.entity.ContestStatus;
import com.clockworks.bigture.entity.User;

@Service
public class ContestService implements IContestService {
	@Autowired IContestDAO dao;
	@Autowired IArtworkDAO artworkdDao;
	
	private String getRecentOrWinnerImage(Contest contest){
		String path = null;
		if(contest.getStatus().equals(ContestStatus.COMPLETED)){
			List<ContestRank> rank = dao.listWinner(contest);
			if(rank.size() > 0){
				path = rank.get(0).getArtwork().getThumbnail();
			}
		}else{
			List<Artwork> artworks = dao.listContestArtworks(contest, 0, 1, SortOption.RECENT);
			if(artworks.size() > 0){
				path = artworks.get(0).getThumbnail();
			}
		}
		return path;
	}	
	
	@Override
	public List<ContestDelegate> listAllContest(int start, int limits) {
		List<ContestDelegate> delegates = new ArrayList<ContestDelegate>();
		List<Contest> results = dao.listAllContest(start, limits);
		
		for(Contest contest : results){
			ContestDelegate delegate = new ContestDelegate(contest);
			delegate.setContestImgPath(getRecentOrWinnerImage(contest));
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public int countAllContest() {
		return dao.countAllContest();
	}

	@Override
	public List<ArtworkDelegate> listContestArtworks(User currentUser,Long contestId, int start,
			int limits, SortOption sort) {
		List<ArtworkDelegate> delegates = new ArrayList<ArtworkDelegate>();
		Contest contest = dao.loadContest(contestId);
		
		List<Artwork> artworks = dao.listContestArtworks(contest, start, limits, sort);
		List<ArtworkCollection> collection = artworkdDao.findCollection(currentUser, sort, 0, 300);
		
		Map<Long,ArtworkCollection> collectMap = new HashMap<Long,ArtworkCollection>();
		
		for(ArtworkCollection collect : collection){
			collectMap.put(collect.getArtwork().getId(),collect);
		}
		
		
		for(Artwork artwork : artworks){
			ArtworkDelegate delegate = new ArtworkDelegate(artwork);
			if(collectMap.containsKey(artwork.getId()))
				delegate.setCollected(true);
			else
				delegate.setCollected(false);
			
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public List<ContestRankDelegate> listWinner(Long contestId) {
		Contest contest = dao.loadContest(contestId);
		List<ContestRank> ranks = dao.listWinner(contest);
		
		List<ContestRankDelegate> delegates = new ArrayList<ContestRankDelegate>();
		
		for(ContestRank rank : ranks){
			ContestRankDelegate delegate = new ContestRankDelegate(rank);
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public int countContestArtworks(Long contestId) {
		Contest contest = dao.loadContest(contestId);
		return dao.countContestArtworks(contest);
	}

	@Override
	public List<ContestDelegate> listActiveContest(int start, int limits) {
		List<ContestDelegate> delegates = new ArrayList<ContestDelegate>();
		
		List<Contest> results = dao.listActiveContest(start,limits);
		for(Contest contest : results){
			ContestDelegate delegate = new ContestDelegate(contest);
			delegate.setContestImgPath(getRecentOrWinnerImage(contest));
			delegates.add(delegate);
		}
		
		return delegates;
	}

	@Override
	public Contest loadContest(Long contestId) {
		return dao.loadContest(contestId);
	}

	@Override
	public List<ContestDelegate> getSimpleContest(int limits) {
		List<ContestDelegate> delegates = new ArrayList<ContestDelegate>();
		List<Contest> results = dao.listAllContest(0, limits);
		
		for(Contest contest : results){
			ContestDelegate delegate = new ContestDelegate(contest);
			delegate.setContestImgPath(getRecentOrWinnerImage(contest));
			delegates.add(delegate);
		}
		
		return delegates;
	}

	
	
}
