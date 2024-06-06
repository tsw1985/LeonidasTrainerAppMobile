package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ConfigurationExerciseTimes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String ID_CONFIGURATION_TRAINER = "idConfigurationTrainer";
	public static final String CONF_TRAINER_ID_SERVER = "confTrainerIdServer";
	public static final String CONF_TRAINER_ID = "confTrainerId";
	public static final String CONF_EXERCISE_ID= "confExerciseId";
	public static final String CONF_DAY_ID = "confDayId";
	public static final String CONF_BODY_PLACE_ID = "confBbodyplaceId";
	public static final String CONF_TOTAL_TIMES = "times";
	
	public ConfigurationExerciseTimes(){}
	
	@DatabaseField(generatedId=true , columnName=ID_CONFIGURATION_TRAINER)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=CONF_TRAINER_ID_SERVER)
	private Integer confTrainerIdServer;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_TRAINER_ID)
	private Integer trainer;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_EXERCISE_ID)
	private Integer exercise;
	
	@DatabaseField(canBeNull=false , columnName=CONF_DAY_ID)
	private Integer day;
	
	@DatabaseField(canBeNull=false  , columnName=CONF_BODY_PLACE_ID)
	private Integer bodyPlace;
	
	@DatabaseField(canBeNull=false , columnName=CONF_TOTAL_TIMES)
	private Integer times;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfTrainerIdServer() {
		return confTrainerIdServer;
	}

	public void setConfTrainerIdServer(Integer confTrainerIdServer) {
		this.confTrainerIdServer = confTrainerIdServer;
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

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	
	
	
}