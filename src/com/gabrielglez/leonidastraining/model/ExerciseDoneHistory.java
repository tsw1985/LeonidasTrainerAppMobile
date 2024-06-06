package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ExerciseDoneHistory implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	public static final String ID_CONFIGURATION_TRAINER = "idConfigurationTrainer";
	public static final String CONF_TRAINER_ID = "confTrainerId";
	public static final String CONF_EXERCISE_ID= "confExerciseId";
	public static final String CONF_DAY_ID = "confDayId";
	public static final String CONF_BODY_PLACE_ID = "confBbodyplaceId";
	public static final String CONF_EXERCISE_DONE = "exerciseDone";
	public static final String CONF_ID_TRAINER_COUNTER = "idTrainerCounter";
	public static final String CONF_ACTUAL_TIME_COUNTER = "actualTime";
	
	
	
	public ExerciseDoneHistory(){}
	
	@DatabaseField(generatedId=true , columnName=ID_CONFIGURATION_TRAINER)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=CONF_ID_TRAINER_COUNTER)
	private Integer confIdTrainerCounter;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_TRAINER_ID)
	private Integer trainer;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_EXERCISE_ID)
	private Integer exercise;
	
	@DatabaseField(canBeNull=false , columnName=CONF_DAY_ID)
	private Integer day;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_BODY_PLACE_ID)
	private Integer bodyPlace;
	
	@DatabaseField(canBeNull=false , columnName=CONF_EXERCISE_DONE)
	private boolean exerciseDone;
	
	@DatabaseField(canBeNull=false , columnName=CONF_ACTUAL_TIME_COUNTER)
	private Integer actualTime;

	@DatabaseField(dataType = DataType.DATE_LONG)
	private Date dateHistory;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrainer() {
		return trainer;
	}
	public void setTrainer(Integer trainer) {
		this.trainer = trainer;
	}
	public Integer getExercise() {
		return exercise;
	}
	public void setExercise(Integer exercise) {
		this.exercise = exercise;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getBodyPlace() {
		return bodyPlace;
	}
	public void setBodyPlace(Integer bodyPlace) {
		this.bodyPlace = bodyPlace;
	}
	public Date getDateHistory() {
		return dateHistory;
	}
	public void setDateHistory(Date dateHistory) {
		this.dateHistory = dateHistory;
	}
	public Integer getConfIdTrainerCounter() {
		return confIdTrainerCounter;
	}
	public void setConfIdTrainerCounter(Integer confIdTrainerCounter) {
		this.confIdTrainerCounter = confIdTrainerCounter;
	}
	public Integer getActualTime() {
		return actualTime;
	}
	public void setActualTime(Integer actualTime) {
		this.actualTime = actualTime;
	}
	public boolean isExerciseDone() {
		return exerciseDone;
	}
	public void setExerciseDone(boolean exerciseDone) {
		this.exerciseDone = exerciseDone;
	}
	
}