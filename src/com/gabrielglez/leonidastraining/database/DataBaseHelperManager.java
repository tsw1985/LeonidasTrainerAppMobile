package com.gabrielglez.leonidastraining.database;

import com.gabrielglez.leonidastraining.DAO.BodyPlaceDAO;
import com.gabrielglez.leonidastraining.DAO.BodyWeightDAO;
import com.gabrielglez.leonidastraining.DAO.ConfigurationExerciseTimesDAO;
import com.gabrielglez.leonidastraining.DAO.ConfigurationTrainerDAO;
import com.gabrielglez.leonidastraining.DAO.DayDAO;
import com.gabrielglez.leonidastraining.DAO.ExerciseDAO;
import com.gabrielglez.leonidastraining.DAO.ExerciseDoneHistoryDAO;
import com.gabrielglez.leonidastraining.DAO.ExerciseTimesHistoryDAO;
import com.gabrielglez.leonidastraining.DAO.SportActivityDAO;
import com.gabrielglez.leonidastraining.DAO.TrainerCounterDAO;
import com.gabrielglez.leonidastraining.DAO.TrainerDAO;
import com.gabrielglez.leonidastraining.DAO.TrainerDownloadDAO;
import com.gabrielglez.leonidastraining.DAO.UserGymDAO;

public class DataBaseHelperManager {
		
	private static UserGymDAO userGymDAO;
	private static TrainerDownloadDAO trainerDownloadedDAO;
	private static TrainerDAO trainerDAO;
	private static TrainerCounterDAO trainerCounterDAO;
	private static DayDAO dayDAO;
	private static BodyPlaceDAO bodyPlaceDAO;
	private static ExerciseDAO exerciseDAO;
	private static ConfigurationExerciseTimesDAO configurationExerciseTimesDAO;
	private static ConfigurationTrainerDAO configurationTrainerDAO;
	private static SportActivityDAO sportActivityDAO;
	private static ExerciseTimesHistoryDAO exerciseTimesHistoryDAO;
	private static ExerciseDoneHistoryDAO exerciseDoneHistoryDAO;
	private static BodyWeightDAO bodyWeigthDAO;
	
	public DataBaseHelperManager(DataBaseHelper dataBaseHelper){
		userGymDAO = new UserGymDAO(dataBaseHelper);
		trainerDownloadedDAO = new TrainerDownloadDAO(dataBaseHelper);
		trainerDAO = new TrainerDAO(dataBaseHelper);
		trainerCounterDAO = new TrainerCounterDAO(dataBaseHelper);
		dayDAO = new DayDAO(dataBaseHelper);
		bodyPlaceDAO = new BodyPlaceDAO(dataBaseHelper);
		exerciseDAO = new ExerciseDAO(dataBaseHelper);
		configurationExerciseTimesDAO = new ConfigurationExerciseTimesDAO(dataBaseHelper);
		configurationTrainerDAO = new ConfigurationTrainerDAO(dataBaseHelper);
		sportActivityDAO = new SportActivityDAO(dataBaseHelper);
		exerciseTimesHistoryDAO = new ExerciseTimesHistoryDAO(dataBaseHelper);
		exerciseDoneHistoryDAO = new ExerciseDoneHistoryDAO(dataBaseHelper);
		bodyWeigthDAO = new BodyWeightDAO(dataBaseHelper);
		
	}

	public static UserGymDAO getUserGymDAO() {
		return userGymDAO;
	}
	public static TrainerDownloadDAO getTrainerDownloadedDAO() {
		return trainerDownloadedDAO;
	}
	public static TrainerDAO getTrainerDAO() {
		return trainerDAO;
	}
	public static DayDAO getDayDAO() {
		return dayDAO;
	}
	public static BodyPlaceDAO getBodyPlaceDAO() {
		return bodyPlaceDAO;
	}
	public static ExerciseDAO getExerciseDAO() {
		return exerciseDAO;
	}
	public static ConfigurationExerciseTimesDAO getConfigurationExerciseTimesDAO() {
		return configurationExerciseTimesDAO;
	}
	public static ConfigurationTrainerDAO getConfigurationTrainerDAO() {
		return configurationTrainerDAO;
	}
	public static SportActivityDAO getSportActivityDAO() {
		return sportActivityDAO;
	}
	public static ExerciseTimesHistoryDAO getExerciseTimesHistoryDAO() {
		return exerciseTimesHistoryDAO;
	}
	public static TrainerCounterDAO getTrainerCounterDAO() {
		return trainerCounterDAO;
	}
	public static ExerciseDoneHistoryDAO getExerciseDoneHistoryDAO() {
		return exerciseDoneHistoryDAO;
	}
	public static BodyWeightDAO getBodyWeigthDAO() {
		return bodyWeigthDAO;
	}
}