package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.BodyPlace;
import com.j256.ormlite.dao.Dao;

public class BodyPlaceDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<BodyPlace ,Integer> dao;
	
	
	public BodyPlaceDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getBodyPlaceDAO();
	}
	
	
	public boolean create(BodyPlace bodyPlace){
		
		try {
			dao.create(bodyPlace);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving bodyPlace " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(BodyPlace bodyPlace){
		
		try {
			dao.update(bodyPlace);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating bodyPlace " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public BodyPlace get(Integer id){
		try{
			BodyPlace bodyPlace = dao.queryForId(id);
			return bodyPlace;
		}catch(SQLException ex){
			Log.e("SQL","Error getting bodyPlace " + ex.toString() );
			return null;
		}
	}
	
	public BodyPlace getBodyPlaceByIdServer(Integer id){
		
		BodyPlace bodyPlace = null;
		
		try{
			List<BodyPlace> bodyPlaceList = dao.queryForEq(BodyPlace.BODYPLACE_ID_SERVER, id);
			if ( bodyPlaceList.size() > 0){
				bodyPlace = bodyPlaceList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting bodyPlace by id Server " + ex.toString() );
		}
		return bodyPlace;
	}
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all bodyPlace " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<BodyPlace> getAllBodyPlaces(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL bodyPlaces " + ex.toString() );
			return null;
		}
	}

}
