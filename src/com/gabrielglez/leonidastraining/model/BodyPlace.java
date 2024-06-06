package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class BodyPlace implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String BODYPLACE_ID = "id";
	public static final String BODYPLACE_ID_SERVER = "bodyPlaceIdServer";
	public static final String BODYPLACE_DESCRIPTION = "bodyPlaceDescription";
	
	
	public BodyPlace(){}

	@DatabaseField(generatedId=true , columnName=BODYPLACE_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=BODYPLACE_ID_SERVER)
	private Integer bodyPlaceIdServer;
	
	@DatabaseField(canBeNull=false , columnName=BODYPLACE_DESCRIPTION,defaultValue="")
	private String bodyPlaceDescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBodyPlaceIdServer() {
		return bodyPlaceIdServer;
	}

	public void setBodyPlaceIdServer(Integer bodyPlaceIdServer) {
		this.bodyPlaceIdServer = bodyPlaceIdServer;
	}

	public String getBodyPlaceDescription() {
		return bodyPlaceDescription;
	}

	public void setBodyPlaceDescription(String bodyPlaceDescription) {
		this.bodyPlaceDescription = bodyPlaceDescription;
	}
	
	@Override
	public String toString(){
		return this.bodyPlaceDescription;
	}
	
	
}