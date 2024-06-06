package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Trainer implements Serializable {
	
	public static final long serialVersionUID = 1L;
	public static final String TRAINER_ID = "id";
	public static final String TRAINER_ID_SERVER = "trainerIdServer";
	public static final String TRAINER_DESCRIPTION = "trainerDescription";
	public static final String TRAINER_TOTAL_DAYS = "trainerTotalDays";
	public static final String TRAINER_ID_SPORT_ACTIVITY = "trainerIdSportActivity";
	
	public Trainer(){}
	
	
	@DatabaseField(generatedId=true , columnName=TRAINER_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_ID_SERVER)
	private Integer trainerIDServer;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_DESCRIPTION,defaultValue="")
	private String trainerDescription;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_TOTAL_DAYS)
	private Integer trainerTotalDays;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_ID_SPORT_ACTIVITY)
	private Integer trainerIdSportActivity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrainerIDServer() {
		return trainerIDServer;
	}

	public void setTrainerIDServer(Integer trainerIDServer) {
		this.trainerIDServer = trainerIDServer;
	}

	public String getTrainerDescription() {
		return trainerDescription;
	}

	public void setTrainerDescription(String trainerDescription) {
		this.trainerDescription = trainerDescription;
	}

	public Integer getTrainerTotalDays() {
		return trainerTotalDays;
	}

	public void setTrainerTotalDays(Integer trainerTotalDays) {
		this.trainerTotalDays = trainerTotalDays;
	}

	public Integer getTrainerIdSportActivity() {
		return trainerIdSportActivity;
	}

	public void setTrainerIdSportActivity(Integer trainerIdSportActivity) {
		this.trainerIdSportActivity = trainerIdSportActivity;
	}
	
	
	
}