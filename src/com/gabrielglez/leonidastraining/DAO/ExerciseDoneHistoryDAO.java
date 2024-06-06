package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.ExerciseDoneHistory;
import com.gabrielglez.leonidastraining.model.ExerciseTimesHistory;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

public class ExerciseDoneHistoryDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<ExerciseDoneHistory ,Integer> dao;
	
	public ExerciseDoneHistoryDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getExerciseDoneHistoryDAO();
	}
	
	
	public boolean create(ExerciseDoneHistory exerciseTimes){
		
		try {
			dao.create(exerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving ExerciseDoneHistory " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(ExerciseDoneHistory exerciseTimes){
		
		try {
			dao.update(exerciseTimes);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating exerciseTimesHistory " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public ExerciseDoneHistory get(Integer id){
		try{
			ExerciseDoneHistory exerciseTimesHistory = dao.queryForId(id);
			return exerciseTimesHistory;
		}catch(SQLException ex){
			Log.e("SQL","Error getting ExerciseTimesHistory " + ex.toString() );
			return null;
		}
	}
	
	public ExerciseTimesHistory getConfigurationExerciseTimesHistoryByIdServer(Integer id){
		return null;
	}
	
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all ExerciseDoneHistory " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<ExerciseDoneHistory> getAllConfigurationExerciseTimesHistory(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL ExerciseTimesHistory " + ex.toString() );
			return null;
		}
	}
	
	
	public boolean getExerciseDone(Integer idTrainer ,Integer idExercise, Integer idDay , Integer idBodyPlace , Integer trainerCounter , Integer actualRepetition){
		
		boolean done = false;
		
		try{
			
			QueryBuilder<ExerciseDoneHistory, Integer> queryBuilder = dao.queryBuilder();
			
			List<ExerciseDoneHistory> exerciseDoneList = queryBuilder
																	  .orderBy(ExerciseDoneHistory.ID_CONFIGURATION_TRAINER, false)
																	  .limit(1L)
																	  .selectColumns(ExerciseDoneHistory.CONF_EXERCISE_DONE)
																	  .where()
																	  .eq(ExerciseDoneHistory.CONF_TRAINER_ID, idTrainer)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_DAY_ID, idDay)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_EXERCISE_ID, idExercise)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_BODY_PLACE_ID, idBodyPlace)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_ID_TRAINER_COUNTER, trainerCounter)
																	  .query();
																	   //.and()
																	   //.eq(ExerciseDoneHistory.CONF_ACTUAL_TIME_COUNTER, actualRepetition)
			
			if ( exerciseDoneList != null && exerciseDoneList.size() > 0){
				done = exerciseDoneList.get(0).isExerciseDone();
			}
			

		}catch(SQLException ex){
			Log.e("SQL","Error  getting getExerciseDone " + ex.toString() );
		}
		
		return done;
		
		
	}

	
public boolean getExerciseDoneAllExercisesTogether(Integer idTrainer ,Integer idExercise, Integer idDay , Integer trainerCounter , Integer actualRepetition){
		
		boolean done = false;
		
		try{
			
			QueryBuilder<ExerciseDoneHistory, Integer> queryBuilder = dao.queryBuilder();
			
			List<ExerciseDoneHistory> exerciseDoneList = queryBuilder
																	  .orderBy(ExerciseDoneHistory.ID_CONFIGURATION_TRAINER, false)
																	  .limit(1L)
																	  .selectColumns(ExerciseDoneHistory.CONF_EXERCISE_DONE)
																	  .where()
																	  .eq(ExerciseDoneHistory.CONF_TRAINER_ID, idTrainer)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_DAY_ID, idDay)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_EXERCISE_ID, idExercise)
																	  .and()
																	  .eq(ExerciseDoneHistory.CONF_ID_TRAINER_COUNTER, trainerCounter)
																	  .query();
																	   //.and()
																	   //.eq(ExerciseDoneHistory.CONF_ACTUAL_TIME_COUNTER, actualRepetition)
			
			if ( exerciseDoneList != null && exerciseDoneList.size() > 0){
				done = exerciseDoneList.get(0).isExerciseDone();
			}
			

		}catch(SQLException ex){
			Log.e("SQL","Error  getting getExerciseDoneAllExercisesTogether " + ex.toString() );
		}
		
		return done;
		
		
	}

	

}