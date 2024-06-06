package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.SportActivity;
import com.j256.ormlite.dao.Dao;

public class SportActivityDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<SportActivity ,Integer> dao;
	
	
	public SportActivityDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getSportActivityDAO();
	}
	
	
	public boolean create(SportActivity sportActivity){
		
		try {
			dao.create(sportActivity);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving SportActivity " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(SportActivity sportActivity){
		
		try {
			dao.update(sportActivity);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating SportActivity " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public SportActivity get(Integer id){
		try{
			SportActivity sportActivity = dao.queryForId(id);
			return sportActivity;
		}catch(SQLException ex){
			Log.e("SQL","Error getting SportActivity " + ex.toString() );
			return null;
		}
	}
	
	public SportActivity getSportActivityByIdServer(Integer id){
		
		SportActivity sportActivity = null;
		
		try{
			List<SportActivity> sportActivityList = dao.queryForEq(SportActivity.SPORT_ACTIVITY_ID_SERVER, id);
			if ( sportActivityList.size() > 0){
				sportActivity = sportActivityList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting SportActivity by id Server " + ex.toString() );
		}
		return sportActivity;
	}
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all SportActivity " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<SportActivity> getAllSportActivities(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL SportActivity " + ex.toString() );
			return null;
		}
	}

}
