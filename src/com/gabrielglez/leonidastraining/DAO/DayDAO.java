package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.Day;
import com.j256.ormlite.dao.Dao;

public class DayDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Day ,Integer> dao;
	
	
	public DayDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getDayDAO();
	}
	
	
	public boolean create(Day day){
		
		try {
			dao.create(day);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving day " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Day day){
		
		try {
			dao.update(day);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating day " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public Day get(Integer id){
		try{
			Day day = dao.queryForId(id);
			return day;
		}catch(SQLException ex){
			Log.e("SQL","Error getting day " + ex.toString() );
			return null;
		}
	}
	
	public Day getDayByIdServer(Integer id){
		
		Day day = null;
		
		try{
			List<Day> dayList = dao.queryForEq(Day.DAY_ID_SERVER, id);
			if ( dayList.size() > 0){
				day = dayList.get(0);
			}

		}catch(SQLException ex){
			Log.e("SQL","Error getting day by id Server " + ex.toString() );
		}
		return day;
	}
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all days " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<Day> getAllDays(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL days " + ex.toString() );
			return null;
		}
	}

}
