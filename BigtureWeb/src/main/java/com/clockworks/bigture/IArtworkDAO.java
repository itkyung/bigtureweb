package com.clockworks.bigture;

import java.util.Collection;
import java.util.List;

import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ArtworkComment;
import com.clockworks.bigture.entity.Contest;
import com.clockworks.bigture.entity.ShareType;
import com.clockworks.bigture.entity.ArtworkType;
import com.clockworks.bigture.entity.SpamLog;
import com.clockworks.bigture.entity.StickerType;
import com.clockworks.bigture.entity.User;

public interface IArtworkDAO {
	
	List<Artwork> findArtworks(User owner,ShareType share,ArtworkType type,SortOption sort,int start,int limits);
	List<Artwork> findSplashArtworks();
	
	int countArtworks(User owner,ShareType share,ArtworkType type);
	Artwork loadArtwork(Long id);
	
	
	List<ArtworkCollection> findCollection(User owner,SortOption sort,int start,int limits);
	List<Artwork> findFriendsArtworks(User owner,SortOption sort,int start,int limits);
	int countCollection(User owner);
	int countFriendsArtworks(User owner);
	int countUserArtworks(User owner,ShareType share,ArtworkType type);
	
	ArtworkCollection findCollection(User owner,Artwork artwork);
	void createCollection(ArtworkCollection collection);
	void removeCollection(ArtworkCollection collection);
	
	
	List<ArtworkComment> findComments(Artwork artwork,int start,int limits);
	Collection<ArtworkComment> findUniqueComments(Artwork artwork,int start,int limits);
	List<ArtworkComment> findUserComments(Artwork artwork,User owner,StickerType sticker);
	ArtworkComment findSticker(Artwork artwork,User owner);
	
	void createComment(ArtworkComment comment);
	
	void createArtwork(Artwork artwork);
	void updateArtwork(Artwork artwork);
	
	void createSpam(SpamLog spam);
	
	ArtworkComment loadComment(Long id);
	void delete(ArtworkComment comment);
	
}
