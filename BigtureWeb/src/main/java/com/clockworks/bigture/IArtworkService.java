package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.common.ImageInfo;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.ArtworkRegistModel;
import com.clockworks.bigture.delegate.CommentWrapper;
import com.clockworks.bigture.delegate.RegionCommentCount;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.User;

public interface IArtworkService {
	public List<ArtworkDelegate> findUserArtworks(User owner,User currentUser,boolean isMy,ArtworkType type,SortOption sort,int start,int limit);
	public List<ArtworkDelegate> findAllArtworks(User owner,ArtworkType type,SortOption sort,int start,int limit);
	public int countAllArtworks(ArtworkType type);
	public int countUserArtworks(User owner,boolean isMy,ArtworkType type);
	public int countFriendsArtworks(User owner);
	public int countCollectedArtworks(User owner);

	public List<ArtworkDelegate> findFriendsArtworks(User owner,SortOption sort,int start,int limit);
	public List<ArtworkDelegate> findCollectedArtworks(User owner,SortOption sort,int start,int limit);
	
	public CommentWrapper getCommentInfo(User owner,Long artworkId,int start,int limits);
	public void sendSticker(User owner,Long artworkId, StickerType sticker) throws Exception;
	public void sendComment(User owner,Long artworkId, String comment);
	
	public boolean addCollection(User owner,Long artworkId) throws Exception;
	public void removeCollection(User owner,Long artworkId) throws Exception;
	
	public void reportSpam(User user,Long artworkId) throws Exception;
	public void editArtwork(User user,Long artworkId,String title,String comment,ShareType shareType) throws Exception;
	public void deleteArtwork(User user,Long artworkId) throws Exception;
	
	public void requestContest(User user,Long artworkId,Long contestId) throws Exception;
	public void cancelFromContest(User user,Long artworkId,Long contestId) throws Exception;
	public Artwork loadArtwork(Long artworkId);
	public List<ArtworkCollection> findAllCollection(User user);
	
	public Artwork registArtwork(User user,ArtworkRegistModel model,ImageInfo image,String drawingPath) throws Exception;
	public void updateArtwork(Artwork artwork);
	
	public ArtworkDelegate getRandomArtwork();
	public List<ArtworkDelegate> getSimpleArtwork(int limits);
	public List<RegionCommentCount> getRegionCountComment(Long artworkId);
	public void deleteCommentOrSticker(User user,Long commentId) throws Exception;
}
