package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class BodyWeight implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String BODY_WEIGHT_ID = "id";
	public static final String BODY_WEIGTH_KG = "body_kg";
	public static final String BODY_WEIGTH_DATE = "dateWeight";

	
	public BodyWeight(){}
	
	@DatabaseField(generatedId=true , columnName=BODY_WEIGHT_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=BODY_WEIGTH_KG)
	private Integer bodyWeigthKg;
	
	@DatabaseField(dataType = DataType.DATE_LONG)
	private Date dateWeight;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBodyWeigthKg() {
		return bodyWeigthKg;
	}

	public void setBodyWeigthKg(Integer bodyWeigthKg) {
		this.bodyWeigthKg = bodyWeigthKg;
	}

	public Date getDateWeight() {
		return dateWeight;
	}

	public void setDateWeight(Date dateWeight) {
		this.dateWeight = dateWeight;
	}
	
	
	
}