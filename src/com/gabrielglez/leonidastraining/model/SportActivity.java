package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class SportActivity implements Serializable {
	
	public static final long serialVersionUID = 1L;
	public static final String SPORT_ACTIVITY_ID = "id";
	public static final String SPORT_ACTIVITY_ID_SERVER = "sportActivityIdServer";
	public static final String SPORT_ACTIVITY_DESCRIPTION = "sportActivityDescription";
	
	public SportActivity(){}
	
	
	@DatabaseField(generatedId=true , columnName=SPORT_ACTIVITY_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=SPORT_ACTIVITY_ID_SERVER)
	private Integer sportActivityIdServer;
	
	@DatabaseField(canBeNull=true , columnName=SPORT_ACTIVITY_DESCRIPTION,defaultValue="")
	private String sportActivityDescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSportActivityIdServer() {
		return sportActivityIdServer;
	}

	public void setSportActivityIdServer(Integer sportActivityIdServer) {
		this.sportActivityIdServer = sportActivityIdServer;
	}

	public String getSportActivityDescription() {
		return sportActivityDescription;
	}

	public void setSportActivityDescription(String sportActivityDescription) {
		this.sportActivityDescription = sportActivityDescription;
	}
	
	@Override
	public String toString(){
		return this.sportActivityDescription;
	}
	
	
	
}
