package com.clockworks.bigture;

import java.util.Date;
import java.util.List;

import com.clockworks.bigture.delegate.ArtClassDelegate;
import com.clockworks.bigture.delegate.ArtClassModel;
import com.clockworks.bigture.delegate.ArtworkDelegate;
import com.clockworks.bigture.delegate.PuzzleArtworkDelegate;
import com.clockworks.bigture.delegate.PuzzleDelegate;
import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassCollection;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.PuzzlePart;
import com.clockworks.bigture.entity.User;

public interface IArtClassService {
	
	List<ArtClassDelegate> findUserClass(User user,boolean isMy,int start,int limit,ClassStatus status);
	List<ArtClassDelegate> findOwnClass(User user,int start,int limit, ClassStatus status);
	List<ArtClassDelegate> findJoinedClass(User user,int start,int limit,ClassStatus status);
	List<ArtClassDelegate> findCollectedClass(User user,int start,int limit,ClassStatus status);
	List<ArtClassDelegate> findAllClass(User user,int start,int limit,SimpleSortOption sortOption);
	List<ArtClassDelegate> findClassByOwner(User user,int start,int limit,SimpleSortOption sortOption);
	
	int countOwnClass(User user,ClassStatus status);
	int countJoinedClass(User user,ClassStatus status);
	int countCollectedClass(User user,ClassStatus status);
	int countAllClass(User user);
	int countClassByOwner(User user);

	ArtClass createArtClass(User owner,ArtClassModel model) throws Exception;
	
	boolean addCollection(User user,Long classId);
	boolean removeCollection(User user,Long classId);
	ArtClass loadArtClass(Long id);
	List<User> getJoinedMembers(Long classId);
	String changeCoverImage(Long classId,Long coverImageId,boolean coverFromArtwork) throws Exception;
	List<ArtworkDelegate> listClassArtworks(User currentUser,Long classId,int start,int limits,SortOption sort);
	int countClassArtworks(Long classId);
	void deleteArtClass(User user, Long classId) throws Exception;
	void editArtClass(User user,Long classId,Date startDate,Date endDate,String description) throws Exception;
	
	List<ArtClassCollection> findCollection(User owner);
	public boolean canRegistArtwork(ArtClass artClass,User owner);
	public boolean isJoined(ArtClass artClass,User owner);
	public boolean addClassMember(Long classId, List<String> addedMemberIds);
	
	public void registArtwork(User user,Long classId,Long artworkId) throws Exception;
	public void registPuzzleArtwork(User user,Long classId, Long puzzleId, Long artworkId, PuzzlePart part) throws Exception;
	public void reservePuzzleArtwork(User user,Long classId, Long puzzleId,  PuzzlePart part) throws Exception;
	public void cancelPuzzleArtwork(User user,Long classId, Long puzzleId,  PuzzlePart part) throws Exception;
	
	public List<PuzzleDelegate> listPuzzle(Long classId);
	public List<PuzzleArtworkDelegate> listPuzzleArtworks(Long classId,Long puzzleId);
	public ArtClassPuzzle loadPuzzle(Long puzzleId);
	public ArtClassPuzzleArtwork findPuzzleArtwork(Long puzzleId,PuzzlePart part);
	public List<ArtClassDelegate> getSimpleClass(int limits);
	
}
