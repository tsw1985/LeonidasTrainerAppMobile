package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.ConfigurationTrainer;
import com.gabrielglez.leonidastraining.model.Trainer;
import com.j256.ormlite.dao.Dao;

public class TrainerDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Trainer ,Integer> dao;
	
	
	public TrainerDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getTrainerDAO();
	}
	
	
	public boolean create(Trainer trainer){
		
		try {
			dao.create(trainer);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving trainer " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Trainer trainer){
		
		try {
			dao.update(trainer);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating trainer " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public Trainer get(Integer id){
		try{
			Trainer trainer = dao.queryForId(id);
			return trainer;
		}catch(SQLException ex){
			Log.e("SQL","Error getting trainer " + ex.toString() );
			return null;
		}
	}
	
	public Trainer getTrainerByIdServer(Integer id){
		
		Trainer trainer = null;
		
		try{

			List<Trainer> trainerList = dao.queryForEq(Trainer.TRAINER_ID_SERVER, id);
			if ( trainerList.size() > 0){
				trainer = trainerList.get(0);
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error getting trainer " + ex.toString() );
		}
		
		return trainer;
	}
	
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all trainer " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<Trainer> getAllTrainers(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL trainer " + ex.toString() );
			return null;
		}
	}
	
	public List<Trainer> getTrainersByIdSportActivity(Integer idSportActivity){
		
		List<Trainer> trainerList = null;
		
		try{
			trainerList = dao.queryForEq(Trainer.TRAINER_ID_SPORT_ACTIVITY, idSportActivity);
		}catch(Exception ex){
			Log.e("SQL","Error: TrainerDAO - getTrainersByIdSportActivity " + ex.toString() );
		}
		
		return trainerList;
		
	}

}
