package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TrainerDownloaded implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String TRAINER_DOWNLOADED_ID = "id";
	public static final String TRAINER_DOWNLOADED_JSON_DATA = "trainerJsonData";
	
	public TrainerDownloaded(){}
	
	@DatabaseField(generatedId=true , columnName=TRAINER_DOWNLOADED_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_DOWNLOADED_JSON_DATA)
	private String trainerJsonData;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrainerJsonData() {
		return trainerJsonData;
	}

	public void setTrainerJsonData(String trainerJsonData) {
		this.trainerJsonData = trainerJsonData;
	}
}