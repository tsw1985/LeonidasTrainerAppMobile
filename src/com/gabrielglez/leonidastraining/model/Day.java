package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Day implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String DAY_ID = "id";
	public static final String DAY_ID_SERVER = "dayIdServer";
	public static final String DAY_NAME = "dayName";
	
	public Day(){}

	@DatabaseField(generatedId=true , columnName=DAY_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=DAY_ID_SERVER)
	private Integer dayIdServer;
	
	@DatabaseField(canBeNull=false , columnName=DAY_NAME,defaultValue="")
	private String dayName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayIdServer() {
		return dayIdServer;
	}

	public void setDayIdServer(Integer dayIdServer) {
		this.dayIdServer = dayIdServer;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	
	@Override
	public String toString(){
		return this.dayName;
		
	}
	
}