package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.BodyWeight;
import com.gabrielglez.leonidastraining.model.UserGym;
import com.j256.ormlite.dao.Dao;

public class UserGymDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<UserGym ,Integer> dao;
	
	
	public UserGymDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getUserGymDAO();
	}
	
	
	public boolean create(UserGym userGym){
		
		try {
			dao.create(userGym);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving userGym " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean update(UserGym userGym){
		
		try {
			dao.update(userGym);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating userGym " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public UserGym get(Integer id){
		try{
			UserGym userGym = dao.queryForId(id);
			return userGym;
		}catch(SQLException ex){
			Log.e("SQL","Error getting usergym " + ex.toString() );
			return null;
		}
	}

	/*
	public List<Customer> getAllCustomer(){
		
		try{
			List<Customer> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
	}*/
	
	
	

}
