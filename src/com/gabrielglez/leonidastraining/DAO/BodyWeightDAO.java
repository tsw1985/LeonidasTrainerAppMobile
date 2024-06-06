package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.BodyWeight;
import com.gabrielglez.leonidastraining.model.ExerciseTimesHistory;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class BodyWeightDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<BodyWeight ,Integer> dao;
	
	
	public BodyWeightDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getBodyWeightDAO();
	}
	
	
	public boolean create(BodyWeight bodyWeight){
		
		try {
			dao.create(bodyWeight);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving weight " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(BodyWeight bodyWeight){
		
		try {
			dao.create(bodyWeight);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating weight " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
public BodyWeight getLastBodyWeightByStringDate(String date){
		
		BodyWeight value = null;
		
		try{

			String query = "select id, " + BodyWeight.BODY_WEIGTH_KG + ", date(" + BodyWeight.BODY_WEIGTH_DATE + "/1000 , 'unixepoch' ) as datew "
			         + " from BodyWeight order by id desc limit 1";
			
			GenericRawResults<String[]> rawResults = dao.queryRaw(query);
			
			List<String[]> results = rawResults.getResults();
			
			
			if ( results != null && results.size() > 0){
				
				String[] resultArray =	results.get(0);
				String id = resultArray[0];
				
				Integer kg = Integer.valueOf(resultArray[1]);
						
				BodyWeight bw = new BodyWeight();
				bw.setId(Integer.valueOf(id));
				bw.setBodyWeigthKg(kg);
						
						;				
				Log.v("Salida","------------Valor de contador result " + resultArray[0]);
				value = bw;

			}
			
			return value;
			
		}catch(SQLException ex){
			Log.e("Salida","Error getting TrainerCounter getLastId " + ex.toString() );
		}
		
		return value;
		
	}

	
	public BodyWeight getToUpdateBodyWeightByStringDate(String date){
		
		BodyWeight value = null;
		
		try{

			String query = "select id, " + BodyWeight.BODY_WEIGTH_KG + ", date(" + BodyWeight.BODY_WEIGTH_DATE + "/1000 , 'unixepoch' ) as datew "
			         + " from BodyWeight where datew = '" + date + "' order by id desc limit 1";
			
			GenericRawResults<String[]> rawResults = dao.queryRaw(query);
			
			List<String[]> results = rawResults.getResults();
			
			
			if ( results != null && results.size() > 0){
				
				String[] resultArray =	results.get(0);
				String id = resultArray[0];
				
				Integer kg = Integer.valueOf(resultArray[1]);
						
				BodyWeight bw = new BodyWeight();
				bw.setId(Integer.valueOf(id));
				bw.setBodyWeigthKg(kg);
						
						;				
				Log.v("Salida","------------Valor de contador result " + resultArray[0]);
				value = bw;

			}
			
			return value;
			
		}catch(SQLException ex){
			Log.e("Salida","Error getting TrainerCounter getLastId " + ex.toString() );
		}
		
		return value;
		
	}
	
	
	
	public List<BodyWeight> listAllBodyWeight(){
		try{
			List<BodyWeight> bodyWeightList = dao.queryForAll();
			return bodyWeightList;
		}catch(SQLException ex){
			Log.e("SQL","Error getting BodyWeightList " + ex.toString() );
			return null;
		}
	}
	
	
	
	public BodyWeight get(Integer id){
		try{
			BodyWeight bodyWeight = dao.queryForId(id);
			return bodyWeight;
		}catch(SQLException ex){
			Log.e("SQL","Error getting BodyWeight " + ex.toString() );
			return null;
		}
	}

	
	public List<BodyWeight> getBodyWeightListBetweenDates(Date startDate, Date endDate){

		
		List<BodyWeight> listBodyWeight = null;

		
		try{
			
			QueryBuilder<BodyWeight, Integer> queryBuilder = dao.queryBuilder();
			
			listBodyWeight = queryBuilder	  .groupBy(BodyWeight.BODY_WEIGTH_DATE)
											  .orderBy(BodyWeight.BODY_WEIGTH_DATE, true)
											  .where()
											  .between(BodyWeight.BODY_WEIGTH_DATE, startDate, endDate)
											  .query();
			
		}catch(Exception ex){
			Log.v("SQL_LEO", "Error getting bodyweight between dates " + ex.toString() );
		}
		
		return listBodyWeight;
		
		
	}
	
	
	

}
