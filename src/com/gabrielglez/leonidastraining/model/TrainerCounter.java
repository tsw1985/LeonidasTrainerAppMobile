package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TrainerCounter implements Serializable{
	
	public static final long serialVersionUID = 1L;
	public static final String TRAINER_COUNTER_ID = "id";
	public static final String TRAINER_COUNTER_VALUE = "trainerCounterValue";
	
	public TrainerCounter(){}
	
	@DatabaseField(generatedId=true , columnName=TRAINER_COUNTER_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=TRAINER_COUNTER_VALUE)
	private Integer trainerCounterValue;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrainerCounterValue() {
		return trainerCounterValue;
	}
	public void setTrainerCounterValue(Integer trainerCounterValue) {
		this.trainerCounterValue = trainerCounterValue;
	}
	
}