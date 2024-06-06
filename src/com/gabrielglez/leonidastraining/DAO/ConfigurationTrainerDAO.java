package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.BodyPlace;
import com.gabrielglez.leonidastraining.model.ConfigurationTrainer;
import com.gabrielglez.leonidastraining.model.Day;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ConfigurationTrainerDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<ConfigurationTrainer ,Integer> dao;
	
	
	public ConfigurationTrainerDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getConfigurationTrainerDAO();
	}
	
	
	public boolean create(ConfigurationTrainer configurationTrainer){
		
		try {
			dao.create(configurationTrainer);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving ConfigurationTrainer " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(ConfigurationTrainer configurationTrainer){
		
		try {
			dao.update(configurationTrainer);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating ConfigurationTrainer " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public ConfigurationTrainer get(Integer id){
		try{
			ConfigurationTrainer configurationTrainer = dao.queryForId(id);
			return configurationTrainer;
		}catch(SQLException ex){
			Log.e("SQL","Error getting ConfigurationTrainer " + ex.toString() );
			return null;
		}
	}
	
	public ConfigurationTrainer getConfigurationTrainerByIdServer(Integer id){
		
		ConfigurationTrainer configurationTrainer = null;
		
		try{
			List<ConfigurationTrainer> configurationTrainerList = dao.queryForEq(ConfigurationTrainer.ID_CONFIGURATION_TRAINER, id);
			if ( configurationTrainerList.size() > 0){
				configurationTrainer = configurationTrainerList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting configurationTrainer by id Server " + ex.toString() );
		}
		return configurationTrainer;
	}
	
	
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all configurationTrainer " + ex.toString() );
			return false;
		}
	}
	
	public List<ConfigurationTrainer> getAllConfigurationTrainer(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL configurationTrainer " + ex.toString() );
			return null;
		}
	}
	
	
	public List<Day> getDaysInTrainerByIdTrainer(Integer idTrainer){
		
		List<Day> dayList = null;
		
		try{
			
			//List<ConfigurationTrainer> configurationTrainerList = dao.queryForEq(ConfigurationTrainer.CONF_TRAINER_ID, idTrainer);
			QueryBuilder<ConfigurationTrainer,Integer> queryBuilder = dao.queryBuilder();
			List<ConfigurationTrainer> configurationTrainerList = queryBuilder.selectColumns(ConfigurationTrainer.CONF_DAY_ID)
																			  .groupBy(ConfigurationTrainer.CONF_DAY_ID)
																			  .where()
																			  .eq(ConfigurationTrainer.CONF_TRAINER_ID , idTrainer)
																			  .query();
			
			if (configurationTrainerList != null){
				
				dayList = new ArrayList<Day>();
			
				for(ConfigurationTrainer confTrainerItem : configurationTrainerList){
					Day day = DataBaseHelperManager.getDayDAO().getDayByIdServer(confTrainerItem.getDay());
					if ( day != null ){
						dayList.add(day);
					}
				}
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL getDaysInTrainerByIdTrainer " + ex.toString() );
		}
		
		return dayList;
		
	}
	
	
	public List<BodyPlace> getBodyPlacesInTrainerByIdTrainerAndIdDay(Integer idTrainer , Integer idDay){
		
		List<BodyPlace> bodyPlaceList = null;
		
		try{
			
			//List<ConfigurationTrainer> configurationTrainerList = dao.queryForEq(ConfigurationTrainer.CONF_TRAINER_ID, idTrainer);
			QueryBuilder<ConfigurationTrainer,Integer> queryBuilder = dao.queryBuilder();
			List<ConfigurationTrainer> configurationTrainerList = queryBuilder.selectColumns(ConfigurationTrainer.CONF_BODY_PLACE_ID)
																			  .groupBy(ConfigurationTrainer.CONF_BODY_PLACE_ID)
																			  .where()
																			  .eq(ConfigurationTrainer.CONF_TRAINER_ID , idTrainer)
																			  .and()
																			  .eq(ConfigurationTrainer.CONF_DAY_ID , idDay)
																			  .query();
			
			if (configurationTrainerList != null){
				
				bodyPlaceList = new ArrayList<BodyPlace>();
			
				for(ConfigurationTrainer confTrainerItem : configurationTrainerList){
					BodyPlace bodyPlace = DataBaseHelperManager.getBodyPlaceDAO().getBodyPlaceByIdServer(confTrainerItem.getBodyPlace());
					if ( bodyPlace != null ){
						bodyPlaceList.add(bodyPlace);
					}
				}
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL getBodyPlacesInTrainerByIdTrainerAndIdDay " + ex.toString() );
		}
		
		return bodyPlaceList;
		
	}
	
	
	public List<Exercise> getExercisesInTrainerByIdTrainerIdBodyPlaceAndIdDay(Integer idTrainer, Integer idBodyPlace , Integer idDay){
		
		List<Exercise> exerciseList = null;
		
		try{
			
			QueryBuilder<ConfigurationTrainer, Integer> queryBuilder = dao.queryBuilder();
			
			List<ConfigurationTrainer> configurationTrainerList = queryBuilder.selectColumns(ConfigurationTrainer.CONF_EXERCISE_ID,
																							 ConfigurationTrainer.CONF_BODY_PLACE_ID)
																			  .groupBy(ConfigurationTrainer.CONF_EXERCISE_ID)
																			  .where()
																			  .eq(ConfigurationTrainer.CONF_TRAINER_ID, idTrainer)
																			  .and()
																			  .eq(ConfigurationTrainer.CONF_DAY_ID, idDay)
																			  .and()
																			  .eq(ConfigurationTrainer.CONF_BODY_PLACE_ID, idBodyPlace)
																			  .query();
			if (configurationTrainerList != null){
				
				exerciseList = new ArrayList<Exercise>();
				List<Integer> kgInTimeList = new ArrayList<Integer>();
			
				for(ConfigurationTrainer confTrainerItem : configurationTrainerList){
					Exercise exercise = DataBaseHelperManager.getExerciseDAO().getExerciseByIdServer(confTrainerItem.getExercise());
					if( exercise != null){
						
						
						exercise.setIdBodyPlace(confTrainerItem.getBodyPlace());
						
						//We need get the repetitions in this exercise
						List<Integer> listTimes = DataBaseHelperManager
																	  .getConfigurationExerciseTimesDAO()
																	  .getExerciseRepetitions(idTrainer, exercise.getExerciseIdServer(), idDay, idBodyPlace);

						//load the times with it empty kgByBepetitionsList
						if ( listTimes != null){
							
							exercise.setListTimes(listTimes);
							
							for ( int i = 0 ; i < listTimes.size() ; i++){
								kgInTimeList.add(0);
							}
							
							exercise.setListKgByTime(kgInTimeList);
						}
						
						exerciseList.add(exercise);
					}
				}
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL getExercisesInTrainerByIdTrainerAndIdDay " + ex.toString() );
		}
		
		return exerciseList;
		
	}
	
	
	
public List<Exercise> getExercisesInTrainerTogether(Integer idTrainer, Integer idDay){
		
		List<Exercise> exerciseList = null;
		
		try{
			
			QueryBuilder<ConfigurationTrainer, Integer> queryBuilder = dao.queryBuilder();
			
			List<ConfigurationTrainer> configurationTrainerList = queryBuilder.selectColumns( 
					                                                                           ConfigurationTrainer.CONF_EXERCISE_ID , 
					                                                                           ConfigurationTrainer.CONF_BODY_PLACE_ID )
					                                                                           
																			  .groupBy(ConfigurationTrainer.CONF_EXERCISE_ID)
																			  .where()
																			  .eq(ConfigurationTrainer.CONF_TRAINER_ID, idTrainer)
																			  .and()
																			  .eq(ConfigurationTrainer.CONF_DAY_ID, idDay)
																			  .query();
			if (configurationTrainerList != null){
				
				exerciseList = new ArrayList<Exercise>();
				List<Integer> kgInTimeList = new ArrayList<Integer>();
			
				for(ConfigurationTrainer confTrainerItem : configurationTrainerList){
					Exercise exercise = DataBaseHelperManager.getExerciseDAO().getExerciseByIdServer(confTrainerItem.getExercise());
					if( exercise != null){
						
						exercise.setIdBodyPlace(confTrainerItem.getBodyPlace());
						
						//We need get the repetitions in this exercise
						List<Integer> listTimes = DataBaseHelperManager
																	  .getConfigurationExerciseTimesDAO()
																	  .getExerciseRepetitions(idTrainer, 
																			  				  exercise.getExerciseIdServer(), 
																			  				  idDay, 
																			  				  confTrainerItem.getBodyPlace() );

						//load the times with it empty kgByBepetitionsList
						if ( listTimes != null){
							
							exercise.setListTimes(listTimes);
							
							for ( int i = 0 ; i < listTimes.size() ; i++){
								kgInTimeList.add(0);
							}
							
							exercise.setListKgByTime(kgInTimeList);
						}
						
						exerciseList.add(exercise);
					}
				}
			}
			
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL getExercisesInTrainerByIdTrainerAndIdDay " + ex.toString() );
		}
		
		return exerciseList;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
