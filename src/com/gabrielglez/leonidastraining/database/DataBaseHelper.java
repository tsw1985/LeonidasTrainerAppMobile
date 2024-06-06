package com.gabrielglez.leonidastraining.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gabrielglez.leonidastraining.model.BodyPlace;
import com.gabrielglez.leonidastraining.model.BodyWeight;
import com.gabrielglez.leonidastraining.model.ConfigurationExerciseTimes;
import com.gabrielglez.leonidastraining.model.ConfigurationTrainer;
import com.gabrielglez.leonidastraining.model.Day;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.gabrielglez.leonidastraining.model.ExerciseDoneHistory;
import com.gabrielglez.leonidastraining.model.ExerciseTimesHistory;
import com.gabrielglez.leonidastraining.model.SportActivity;
import com.gabrielglez.leonidastraining.model.Trainer;
import com.gabrielglez.leonidastraining.model.TrainerCounter;
import com.gabrielglez.leonidastraining.model.TrainerDownloaded;
import com.gabrielglez.leonidastraining.model.UserGym;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
	
	private final static String DATABASENAME = "leonidastraining.db";

	private Dao<UserGym    , Integer>    userGymDAO    = getUserGymDao();
	private Dao<TrainerCounter, Integer> trainerCounterDAO = getTrainerCounterDao();
	
	private Dao<Trainer, Integer>        trainerDAO = getTrainerDao();
	private Dao<SportActivity, Integer>  sportActivityDAO = getSportActivityDao();
	
	private Dao<Exercise, Integer>       exerciseDAO = getExerciseDao();
	private Dao<Day, Integer>            dayDAO = getDayDao();
	
	private Dao<ConfigurationTrainer,Integer> configurationTrainerDAO = getConfigurationTrainerDao();
	private Dao<BodyPlace ,Integer>           bodyPlaceDAO = getBodyPlaceDao();
	
	private Dao<TrainerDownloaded, Integer> trainerDownloadedDAO = getTrainerDownloadedDao();
	private Dao<ConfigurationExerciseTimes,Integer> configurationExerciseTimesDAO = getConfigurationExerciseTimesDao();
	
	private Dao<ExerciseTimesHistory, Integer> exerciseTimesHistoryDAO = getExerciseTimesHistoryDao();
	private Dao<ExerciseDoneHistory , Integer> exerciseDoneHistoryDAO = getExerciseDoneHistoryDao();
	
	private Dao<BodyWeight , Integer> bodyWeightDAO = getBodyWeightDao();
	
	
	public DataBaseHelper(Context context) {
		super(context, DATABASENAME, null, 1);
	}
	
	


	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		
		try {
			
			Log.i(DataBaseHelper.class.getName() , "onCreate");
			TableUtils.createTable(connectionSource, UserGym.class);
			TableUtils.createTable(connectionSource, TrainerCounter.class);
			TableUtils.createTable(connectionSource, SportActivity.class);
			TableUtils.createTable(connectionSource, Exercise.class);
			TableUtils.createTable(connectionSource, Day.class);
			TableUtils.createTable(connectionSource, BodyPlace.class);
			TableUtils.createTable(connectionSource, Trainer.class);
			TableUtils.createTable(connectionSource, ConfigurationTrainer.class);
			TableUtils.createTable(connectionSource, TrainerDownloaded.class);
			TableUtils.createTable(connectionSource, ConfigurationExerciseTimes.class);
			TableUtils.createTable(connectionSource, ExerciseTimesHistory.class);
			TableUtils.createTable(connectionSource, ExerciseDoneHistory.class);
			TableUtils.createTable(connectionSource, BodyWeight.class);
			
			
			
			
		} catch (SQLException e) {
			Log.e("SQL" , "Error al crear las tablas" + e.toString() );
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,	int arg3) {
		
		try {
			TableUtils.dropTable(connectionSource, UserGym.class ,true);
			TableUtils.dropTable(connectionSource, TrainerCounter.class, true);
			TableUtils.dropTable(connectionSource, SportActivity.class, true);
			TableUtils.dropTable(connectionSource, Exercise.class, true);
			TableUtils.dropTable(connectionSource, Day.class, true);
			TableUtils.dropTable(connectionSource, BodyPlace.class, true);
			TableUtils.dropTable(connectionSource, Trainer.class, true);
			TableUtils.dropTable(connectionSource, ConfigurationTrainer.class, true);
			TableUtils.dropTable(connectionSource, TrainerDownloaded.class, true);
			TableUtils.dropTable(connectionSource, ConfigurationExerciseTimes.class, true);
			TableUtils.dropTable(connectionSource, ExerciseTimesHistory.class, true);
			TableUtils.dropTable(connectionSource, ExerciseDoneHistory.class, true);
			TableUtils.dropTable(connectionSource, BodyWeight.class, true);
			
		} catch (SQLException e) {
			Log.e("SQL" , "Error al borrar las tablas" + e.toString() );
			e.printStackTrace();
		}
	}
	
	

	private Dao<UserGym, Integer> getUserGymDao() {
		
		try {
			
			if (userGymDAO == null){
				userGymDAO = getDao(UserGym.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en userGymDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userGymDAO;
	}
	
	private Dao<TrainerCounter, Integer> getTrainerCounterDao() {
		
		try {
			
			if (trainerCounterDAO == null){
				trainerCounterDAO = getDao(TrainerCounter.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en trainerCounterDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trainerCounterDAO;
	}
	
	private Dao<Trainer, Integer> getTrainerDao() {
		
		try {
			if (trainerDAO == null){
				trainerDAO = getDao(Trainer.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en trainerDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trainerDAO;
	}
	
	
	private Dao<SportActivity, Integer> getSportActivityDao() {
		
		try {
			if (sportActivityDAO == null){
				sportActivityDAO = getDao(SportActivity.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en sportActivityDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sportActivityDAO;
	}
	
	
	private Dao<Exercise, Integer> getExerciseDao() {
		
		try {
			if (exerciseDAO == null){
				exerciseDAO = getDao(Exercise.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en exerciseDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exerciseDAO;
	}
	
	private Dao<Day, Integer> getDayDao() {
		
		try {
			if (dayDAO == null){
				dayDAO = getDao(Day.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en dayDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dayDAO;
	}
	
	
	private Dao<ConfigurationTrainer, Integer> getConfigurationTrainerDao() {
		
		try {
			if (configurationTrainerDAO == null){
				configurationTrainerDAO = getDao(ConfigurationTrainer.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en configurationTrainerDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return configurationTrainerDAO;
	}
	
	
	private Dao<BodyPlace, Integer> getBodyPlaceDao() {
		
		try {
			if (bodyPlaceDAO == null){
				bodyPlaceDAO = getDao(BodyPlace.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en bodyPlaceDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bodyPlaceDAO;
	}
	
	private Dao<TrainerDownloaded, Integer> getTrainerDownloadedDao() {
		
		try {
			if (trainerDownloadedDAO == null){
				trainerDownloadedDAO = getDao(TrainerDownloaded.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en trainerDownloadedDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trainerDownloadedDAO;
	}
	
	private Dao<ConfigurationExerciseTimes, Integer> getConfigurationExerciseTimesDao() {
		
		try {
			if (configurationExerciseTimesDAO == null){
				configurationExerciseTimesDAO = getDao(ConfigurationExerciseTimes.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en configurationExerciseTimesDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return configurationExerciseTimesDAO;
	}
	

	private Dao<ExerciseTimesHistory, Integer> getExerciseTimesHistoryDao() {
		
		try {
			if (exerciseTimesHistoryDAO == null){
				exerciseTimesHistoryDAO = getDao(ExerciseTimesHistory.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en ExerciseTimesHistoryDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exerciseTimesHistoryDAO;
	}
	
	private Dao<ExerciseDoneHistory, Integer> getExerciseDoneHistoryDao() {
		
		try {
			if (exerciseDoneHistoryDAO == null){
				exerciseDoneHistoryDAO = getDao(ExerciseDoneHistory.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en ExerciseDoneHistoryDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exerciseDoneHistoryDAO;
	}
	
	
	private Dao<BodyWeight, Integer> getBodyWeightDao() {
		
		try {
			if (bodyWeightDAO == null){
				bodyWeightDAO = getDao(BodyWeight.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en bodyWeightDAO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bodyWeightDAO;
	}
	
	
	
	public Dao<TrainerDownloaded, Integer > getTrainerDownloadedDAO(){
		return trainerDownloadedDAO;
	}

	public Dao<UserGym, Integer> getUserGymDAO() {
		return userGymDAO;
	}
	public Dao<TrainerCounter, Integer> getTrainerCounterDAO() {
		return trainerCounterDAO;
	}
	public Dao<Trainer, Integer> getTrainerDAO() {
		return trainerDAO;
	}
	public Dao<SportActivity, Integer> getSportActivityDAO() {
		return sportActivityDAO;
	}
	public Dao<Exercise, Integer> getExerciseDAO() {
		return exerciseDAO;
	}
	public Dao<Day, Integer> getDayDAO() {
		return dayDAO;
	}
	public Dao<ConfigurationTrainer, Integer> getConfigurationTrainerDAO() {
		return configurationTrainerDAO;
	}
	public Dao<BodyPlace, Integer> getBodyPlaceDAO() {
		return bodyPlaceDAO;
	}
	public Dao<ConfigurationExerciseTimes, Integer> getConfigurationExerciseTimesDAO() {
		return configurationExerciseTimesDAO;
	}
	public Dao<ExerciseTimesHistory, Integer> getExerciseTimesHistoryDAO() {
		return exerciseTimesHistoryDAO;
	}
	public Dao<ExerciseDoneHistory, Integer> getExerciseDoneHistoryDAO() {
		return exerciseDoneHistoryDAO;
	}
	public Dao<BodyWeight, Integer> getBodyWeightDAO() {
		return bodyWeightDAO;
	}
}