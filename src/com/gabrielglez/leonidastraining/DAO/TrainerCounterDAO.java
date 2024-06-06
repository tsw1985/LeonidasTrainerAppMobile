package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.TrainerCounter;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

public class TrainerCounterDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<TrainerCounter ,Integer> dao;
	
	
	public TrainerCounterDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getTrainerCounterDAO();
	}
	
	
	public boolean create(TrainerCounter trainerCounter){
		
		try {
			dao.create(trainerCounter);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving TrainerCounter " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(TrainerCounter trainerCounter){
		
		try {
			dao.update(trainerCounter);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating TrainerCounter " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public TrainerCounter get(Integer id){
		try{
			TrainerCounter trainerCounter = dao.queryForId(id);
			return trainerCounter;
		}catch(SQLException ex){
			Log.e("SQL","Error getting TrainerCounter " + ex.toString() );
			return null;
		}
	}
	
	public Integer getLastId(){
		
		try{

			Integer value = 0;
			
			String query = "select id from TrainerCounter order by id desc limit 1";
			
			GenericRawResults<String[]> rawResults = dao.queryRaw(query);
			
			List<String[]> results = rawResults.getResults();
			
			
			if ( results != null && results.size() > 0){
				String[] resultArray =	results.get(0);
				String id = resultArray[0];
				Log.v("Salida","------------Valor de contador result " + resultArray[0]);
				value = Integer.parseInt(id);
			}
			
			return value;
			
		}catch(SQLException ex){
			Log.e("Salida","Error getting TrainerCounter getLastId " + ex.toString() );
			return null;
		}
	}
	
	public TrainerCounter getTrainerCounterByIdServer(Integer id){
		
		TrainerCounter trainerCounter = null;
		
		try{
			List<TrainerCounter> trainerCounterList = dao.queryForEq(TrainerCounter.TRAINER_COUNTER_ID, id);
			if ( trainerCounterList.size() > 0){
				trainerCounter = trainerCounterList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting TrainerCounter by id Server " + ex.toString() );
		}
		return trainerCounter;
	}
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all TrainerCounter " + ex.toString() );
			return false;
		}
	}
	
	public List<TrainerCounter> getAllTrainerCounter(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL TrainerCounter " + ex.toString() );
			return null;
		}
	}
}