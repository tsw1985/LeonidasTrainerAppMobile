package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.ConfigurationExerciseTimes;
import com.gabrielglez.leonidastraining.model.ConfigurationTrainer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ConfigurationExerciseTimesDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<ConfigurationExerciseTimes ,Integer> dao;
	
	
	public ConfigurationExerciseTimesDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getConfigurationExerciseTimesDAO();
	}
	
	
	public boolean create(ConfigurationExerciseTimes configurationExerciseTimes){
		
		try {
			dao.create(configurationExerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving ConfigurationExerciseTimes " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(ConfigurationExerciseTimes configurationExerciseTimes){
		
		try {
			dao.update(configurationExerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating ConfigurationExerciseTimes " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public ConfigurationExerciseTimes get(Integer id){
		try{
			ConfigurationExerciseTimes configurationExerciseTimes = dao.queryForId(id);
			return configurationExerciseTimes;
		}catch(SQLException ex){
			Log.e("SQL","Error getting ConfigurationExerciseTimes " + ex.toString() );
			return null;
		}
	}
	
	public ConfigurationExerciseTimes getConfigurationExerciseTimesByIdServer(Integer id){
		
		ConfigurationExerciseTimes configurationExerciseTimes = null;
		
		try{

			List<ConfigurationExerciseTimes> configurationExerciseTimesList = dao.queryForEq("id", id);
			if ( configurationExerciseTimesList.size() > 0){
				configurationExerciseTimes = configurationExerciseTimesList.get(0);
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error getting ConfigurationExerciseTimes " + ex.toString() );
		}
		
		return configurationExerciseTimes;
	}
	
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all ConfigurationExerciseTimes " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<ConfigurationExerciseTimes> getAllConfigurationExerciseTimes(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL ConfigurationExerciseTimes " + ex.toString() );
			return null;
		}
	}
	
	
	public List<Integer> getExerciseRepetitions(Integer idTrainer ,Integer idExercise, Integer idDay , Integer idBodyPlace){
		
		List<Integer> listTimes = null;
		
		try{
			
			QueryBuilder<ConfigurationExerciseTimes, Integer> queryBuilder = dao.queryBuilder();
			
			List<ConfigurationExerciseTimes> configurationTrainerList = queryBuilder
																			  .orderBy(ConfigurationExerciseTimes.ID_CONFIGURATION_TRAINER, true)
																			  .selectColumns(ConfigurationExerciseTimes.CONF_TOTAL_TIMES)
																			  .where()
																			  .eq(ConfigurationExerciseTimes.CONF_TRAINER_ID, idTrainer)
																			  .and()
																			  .eq(ConfigurationExerciseTimes.CONF_DAY_ID, idDay)
																			  .and()
																			  .eq(ConfigurationExerciseTimes.CONF_EXERCISE_ID, idExercise)
																			  .and()
																			  .eq(ConfigurationExerciseTimes.CONF_BODY_PLACE_ID, idBodyPlace)
																			  .query();
			
			if ( configurationTrainerList != null){
				listTimes = new ArrayList<Integer>();
				
				for ( ConfigurationExerciseTimes confItem : configurationTrainerList ){
					listTimes.add(confItem.getTimes());
				}
			}
			

		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL getExerciseRepetitions " + ex.toString() );
		}
		
		return listTimes;
		
	}


}