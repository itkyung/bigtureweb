package com.clockworks.bigture.delegate;

import com.clockworks.bigture.entity.ArtClassPuzzle;
import com.google.gson.annotations.Expose;

public class PuzzleDelegate {
	@Expose
	private Long puzzleId;
	@Expose
	private Long classId;
	@Expose
	private String baseSketch;
	@Expose
	private boolean completed;
	
	@Expose
	private long created;
	
	public PuzzleDelegate(ArtClassPuzzle puzzle){
		this.puzzleId = puzzle.getId();
		this.classId = puzzle.getArtClass().getId();
		this.baseSketch = puzzle.getBaseSketch();
		this.completed = puzzle.isCompleted();
		this.created = puzzle.getCreated().getTime();
	}

	public Long getPuzzleId() {
		return puzzleId;
	}

	public void setPuzzleId(Long puzzleId) {
		this.puzzleId = puzzleId;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getBaseSketch() {
		return baseSketch;
	}

	public void setBaseSketch(String baseSketch) {
		this.baseSketch = baseSketch;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	} 
	
	
}
