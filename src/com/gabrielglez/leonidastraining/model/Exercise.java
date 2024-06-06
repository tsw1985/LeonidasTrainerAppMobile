package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Exercise implements Serializable{
	
	public static final long serialVersionUID = 1L;
	public static final String EXERCISE_ID = "id";
	public static final String EXERSICE_ID_SERVER = "exerciseIdServer";
	public static final String EXERCISE_NAME = "exerciseName";
	public static final String EXERCISE_DESCRIPTION = "exerciseDescription";
	public static final String EXERCISE_IMAGE = "exerciseImage";
	public static final String EXERCISE_VIDEO = "exerciseVideo";
	
	public Exercise(){}

	@DatabaseField(generatedId=true , columnName=EXERCISE_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=EXERSICE_ID_SERVER)
	private Integer exerciseIdServer;
	
	@DatabaseField(canBeNull=false , columnName=EXERCISE_NAME , defaultValue="")
	private String exerciseName;
	
	@DatabaseField(canBeNull=false , columnName=EXERCISE_DESCRIPTION , defaultValue="")
	private String exerciseDescription;
	
	@DatabaseField(canBeNull=false , columnName=EXERCISE_IMAGE , defaultValue="")
	private String exerciseImage;
	
	@DatabaseField(canBeNull=false , columnName=EXERCISE_VIDEO , defaultValue="")
	private String exerciseVideo;
	
	private List<Integer> listTimes;
	private Integer actualTime = 0;
	private Integer IdBodyPlace;
	private List<Integer> listKgByTime; 
	private boolean checkSelected = false;

	
	
	

	public Integer getIdBodyPlace() {
		return IdBodyPlace;
	}

	public void setIdBodyPlace(Integer idBodyPlace) {
		IdBodyPlace = idBodyPlace;
	}

	public boolean isCheckSelected() {
		return checkSelected;
	}

	public void setCheckSelected(boolean checkSelected) {
		this.checkSelected = checkSelected;
	}

	public List<Integer> getListKgByTime() {
		return listKgByTime;
	}

	public void setListKgByTime(List<Integer> listKgByTime) {
		this.listKgByTime = listKgByTime;
	}

	public Integer getActualTime() {
		return actualTime;
	}

	public void setActualTime(Integer actualTime) {
		this.actualTime = actualTime;
	}

	public List<Integer> getListTimes() {
		return listTimes;
	}

	public void setListTimes(List<Integer> listTimes) {
		this.listTimes = listTimes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExerciseIdServer() {
		return exerciseIdServer;
	}

	public void setExerciseIdServer(Integer exerciseIdServer) {
		this.exerciseIdServer = exerciseIdServer;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getExerciseDescription() {
		return exerciseDescription;
	}

	public void setExerciseDescription(String exerciseDescription) {
		this.exerciseDescription = exerciseDescription;
	}

	public String getExerciseImage() {
		return exerciseImage;
	}

	public void setExerciseImage(String exerciseImage) {
		this.exerciseImage = exerciseImage;
	}

	public String getExerciseVideo() {
		return exerciseVideo;
	}

	public void setExerciseVideo(String exerciseVideo) {
		this.exerciseVideo = exerciseVideo;
	}
}