package com.gabrielglez.leonidastraining.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class UserGym implements Serializable{
	
	public static final long serialVersionUID = 1L;
	public static final String USER_GYM_ID = "id";
	public static final String USER_GYM_ID_SERVER = "usergymIdServer";
	public static final String USER_GYM_NAME = "usergymUsername";
	public static final String USER_GYM_DNI = "usergymDni";
	public static final String USER_GYM_PHONE = "usergymPhone";
	
	public UserGym(){}
	
	@DatabaseField(generatedId=true , columnName=USER_GYM_ID)
	private Integer id;
	
	@DatabaseField(canBeNull=false , columnName=USER_GYM_ID_SERVER)
	private Integer userGymIdServer;
	
	@DatabaseField(canBeNull=false , columnName=USER_GYM_NAME,defaultValue="")
	private String userGymName;
	
	@DatabaseField(canBeNull=true , columnName=USER_GYM_PHONE,defaultValue="")
	private String userGymPhone;
	
	@DatabaseField(canBeNull=false , columnName=USER_GYM_DNI,defaultValue="")
	private String userGymDni;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserGymIdServer() {
		return userGymIdServer;
	}

	public void setUserGymIdServer(Integer userGymIdServer) {
		this.userGymIdServer = userGymIdServer;
	}

	public String getUserGymName() {
		return userGymName;
	}

	public void setUserGymName(String userGymName) {
		this.userGymName = userGymName;
	}

	public String getUserGymPhone() {
		return userGymPhone;
	}

	public void setUserGymPhone(String userGymPhone) {
		this.userGymPhone = userGymPhone;
	}

	public String getUserGymDni() {
		return userGymDni;
	}

	public void setUserGymDni(String userGymDni) {
		this.userGymDni = userGymDni;
	}
}