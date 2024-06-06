package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;
import android.util.Log;
import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.j256.ormlite.dao.Dao;

public class ExerciseDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Exercise ,Integer> dao;
	
	
	public ExerciseDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getExerciseDAO();
	}
	
	
	public boolean create(Exercise exercise){
		
		try {
			dao.create(exercise);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving exercise " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Exercise exercise){
		
		try {
			dao.update(exercise);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating exercise " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public Exercise get(Integer id){
		try{
			Exercise exercise = dao.queryForId(id);
			return exercise;
		}catch(SQLException ex){
			Log.e("SQL","Error getting exercise " + ex.toString() );
			return null;
		}
	}
	
	public Exercise getExerciseByIdServer(Integer id){
		
		Exercise exercise = null;
		
		try{
			List<Exercise> exerciseList = dao.queryForEq(Exercise.EXERSICE_ID_SERVER, id);
			if ( exerciseList.size() > 0){
				exercise = exerciseList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting exercise by id Server " + ex.toString() );
		}
		return exercise;
	}
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all exercises " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<Exercise> getAllExercises(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL exercises " + ex.toString() );
			return null;
		}
	}


}
