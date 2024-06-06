package com.gabrielglez.leonidastraining.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.model.TrainerDownloaded;
import com.j256.ormlite.dao.Dao;

public class TrainerDownloadDAO {
	
	private DataBaseHelper dataBaseHelper;
	private Dao<TrainerDownloaded ,Integer> dao;
	
	
	public TrainerDownloadDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO(){
		dao = dataBaseHelper.getTrainerDownloadedDAO();
	}
	
	
	public boolean create(TrainerDownloaded trainerDownloaded){
		
		try {
			dao.create(trainerDownloaded);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error saving trainerDownloaded " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean update(TrainerDownloaded trainerDownloaded){
		
		try {
			dao.update(trainerDownloaded);
			return true;
		} catch (SQLException ex) {
			Log.e("SQL", "Error updating trainerDownloaded " + ex.toString() );
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public TrainerDownloaded get(Integer id){
		try{
			TrainerDownloaded trainerDownloaded = dao.queryForId(id);
			return trainerDownloaded;
		}catch(SQLException ex){
			Log.e("SQL","Error getting trainerDownloaded " + ex.toString() );
			return null;
		}
	}
	
	
	public boolean deleteAll(){
		
		try{
			dao.delete(dao.queryForAll());
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error deleting all trainerDownloaded " + ex.toString() );
			return false;
		}
	}
	
	
	
	public List<TrainerDownloaded> getAllTrainersDownloaded(){
		try{
			return dao.queryForAll();
		}catch(SQLException ex){
			Log.e("SQL","Error  getting ALL trainerDownloaded " + ex.toString() );
			return null;
		}
	}

	/*
	public List<Customer> getAllCustomer(){
		
		try{
			List<Customer> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
	}*/


}
