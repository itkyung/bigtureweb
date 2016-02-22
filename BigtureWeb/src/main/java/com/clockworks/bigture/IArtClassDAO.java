package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.entity.ArtClass;
import com.clockworks.bigture.entity.ArtClassCollection;
import com.clockworks.bigture.entity.ArtClassMember;
import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.clockworks.bigture.entity.ArtClassPuzzleArtwork;
import com.clockworks.bigture.entity.Artwork;
import com.clockworks.bigture.entity.ArtworkCollection;
import com.clockworks.bigture.entity.ClassStatus;
import com.clockworks.bigture.entity.User;

public interface IArtClassDAO {
	public List<ArtClass> findClass(User user,int start,int limit,boolean onlyOpened,List<ClassStatus> statusList);
	public List<ArtClassCollection> findCollection(User user); 
	public List<ArtClassMember> findMember(User owner);
	public List<User> findJoinedMembers(ArtClass artClass);
	public List<ArtClass> findCollection(User user,List<ClassStatus> statusList,int start,int limit);
	public boolean isJoined(ArtClass artClass,User owner);
	
	public List<ArtClass> findClass(User user,int start,int limit,FindClassType findType,List<ClassStatus> statusList,SimpleSortOption sortOption);
	public int countClass(User user,FindClassType findType,List<ClassStatus> statusList);
	public int countCollectedClass(User user,List<ClassStatus> statusList);
	
	public void create(ArtClass artClass);
	public void update(ArtClass artClass);
	
	public void create(ArtClassCollection collection);
	public ArtClassCollection loadCollection(Long id);
	public void remove(ArtClassCollection collection);
	
	public ArtClass loadClass(Long id);
	public ArtClassCollection findCollection(User user,ArtClass artClass);
	public List<Artwork> listClassArtworks(ArtClass artClass, int start,int limits, SortOption sort);
	public int countClassArtworks(ArtClass artClass);
	public ArtClassPuzzle loadPuzzle(Long id);
	public void createPuzzle(ArtClassPuzzle puzzle);
	public void updatePuzzle(ArtClassPuzzle puzzle);
	
	public List<ArtClassPuzzle> listPuzzle(ArtClass artClass);
	public List<ArtClassPuzzleArtwork> listPuzzleArtworks(ArtClass artClass,ArtClassPuzzle puzzle);
	
}
