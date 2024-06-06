package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.ExerciseTimesHistory;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ExerciseTimesHistoryDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<ExerciseTimesHistory ,Integer> dao;
	
	
	
	
	
	public ExerciseTimesHistoryDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getExerciseTimesHistoryDAO();
	}
	
	
	public boolean create(ExerciseTimesHistory exerciseTimes){
		
		try {
			dao.create(exerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving exerciseTimes " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(ExerciseTimesHistory exerciseTimes){
		
		try {
			dao.update(exerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating exerciseTimesHistory " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public ExerciseTimesHistory get(Integer id){
		try{
			ExerciseTimesHistory exerciseTimesHistory = dao.queryForId(id);
			return exerciseTimesHistory;
		}catch(SQLException ex){
			Log.e("SQL","Error getting ExerciseTimesHistory " + ex.toString() );
			return null;
		}
	}
	
	public ExerciseTimesHistory getConfigurationExerciseTimesHistoryByIdServer(Integer id){
		
		/*ConfigurationExerciseTimes configurationExerciseTimes = null;
		
		try{

			List<ConfigurationExerciseTimes> configurationExerciseTimesList = dao.queryForEq("id", id);
			if ( configurationExerciseTimesList.size() > 0){
				configurationExerciseTimes = configurationExerciseTimesList.get(0);
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error getting ConfigurationExerciseTimes " + ex.toString() );
		}
		
		return configurationExerciseTimes;*/
		
		return null;
	}
	
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all ExerciseTimesHistory " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<ExerciseTimesHistory> getAllConfigurationExerciseTimesHistory(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL ExerciseTimesHistory " + ex.toString() );
			return null;
		}
	}
	
	
	public Integer getExerciseRepetition(Integer idTrainer ,Integer idExercise, Integer idDay , Integer idBodyPlace , Integer trainerCounter , Integer actualRepetition){
		
		List<Integer> listTimes = null;
		Integer totalKg = 0;
		
		try{
			
			QueryBuilder<ExerciseTimesHistory, Integer> queryBuilder = dao.queryBuilder();
			
			List<ExerciseTimesHistory> configurationTrainerList = queryBuilder
																			  .orderBy(ExerciseTimesHistory.ID_CONFIGURATION_TRAINER, false)
																			  .limit(1L)
																			  .selectColumns(ExerciseTimesHistory.CONF_TOTAL_TIMES)
																			  .where()
																			  .eq(ExerciseTimesHistory.CONF_TRAINER_ID, idTrainer)
																			  .and()
																			  .eq(ExerciseTimesHistory.CONF_DAY_ID, idDay)
																			  .and()
																			  .eq(ExerciseTimesHistory.CONF_EXERCISE_ID, idExercise)
																			  .and()
																			  .eq(ExerciseTimesHistory.CONF_BODY_PLACE_ID, idBodyPlace)
																			  .and()
																			  .eq(ExerciseTimesHistory.CONF_ID_TRAINER_COUNTER, trainerCounter)
																			  .and()
																			  .eq(ExerciseTimesHistory.CONF_ACTUAL_TIME_COUNTER, actualRepetition)
																			  .query();
			
			/*List<ExerciseTimesHistory> configurationTrainerList = queryBuilder
					  .orderBy(ExerciseTimesHistory.ID_CONFIGURATION_TRAINER, true)
					  .selectColumns(ExerciseTimesHistory.CONF_TOTAL_TIMES)
					  .where()
					  .eq(ExerciseTimesHistory.CONF_TRAINER_ID, idTrainer)
					  .and()
					  .eq(ExerciseTimesHistory.CONF_DAY_ID, idDay)
					  .and()
					  .eq(ExerciseTimesHistory.CONF_EXERCISE_ID, idExercise)
					  .and()
					  .eq(ExerciseTimesHistory.CONF_BODY_PLACE_ID, idBodyPlace)
					  .and()
					  .eq(ExerciseTimesHistory.CONF_ID_TRAINER_COUNTER, trainerCounter)
					  .and()
					  .eq(ExerciseTimesHistory.CONF_ACTUAL_TIME_COUNTER, actualRepetition)
					  .query();*/
			
			if ( configurationTrainerList != null && configurationTrainerList.size() > 0){
				totalKg = configurationTrainerList.get(0).getTimes();
			}
			

		}catch(SQLException ex){
			Log.e("SQL","Error  getting getExerciseRepetition " + ex.toString() );
		}
		
		return totalKg;
		
		
	}


}