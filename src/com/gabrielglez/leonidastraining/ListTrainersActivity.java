package com.gabrielglez.leonidastraining;

import java.util.List;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.gabrielglez.leonidastraining.adapter.spinner.ListTrainerListViewAdapter;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.SportActivity;
import com.gabrielglez.leonidastraining.model.Trainer;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

public class ListTrainersActivity extends ActionBarActivity {
	
	
	private Spinner sportActivitySpinner ;
	private ListView trainersListListView;
	private ImageView rowDownSportImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_list_trainers);
		initComponent();
		setOrientation();
		loadData();
		initListener();
	}

	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
}

	

	private void initComponent() {
		sportActivitySpinner = (Spinner)findViewById(R.id.sport_activities_spinner);
		trainersListListView = (ListView)findViewById(R.id.trainers_list_listview);
		rowDownSportImageView = (ImageView)findViewById(R.id.row_down_sport_imageView);
	}
	
	private void initListener() {
		
		
		sportActivitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				SportActivity sportActivity = (SportActivity)sportActivitySpinner.getSelectedItem();
				if ( sportActivity != null ){
					
					StaticValues.ID_SELECTED_SPORT_ACTIVITY = sportActivity.getSportActivityIdServer();
					
					//Obtenemos los entrenamientos para cargarlos en la lista
					List<Trainer> trainerList = DataBaseHelperManager.getTrainerDAO().getTrainersByIdSportActivity(sportActivity.getSportActivityIdServer());
					if (trainerList != null){
						ListTrainerListViewAdapter listTrainerAdapter = new ListTrainerListViewAdapter(ListTrainersActivity.this , trainerList);
						trainersListListView.setAdapter(listTrainerAdapter);
					}
					
					Log.v("Salida",sportActivity.getSportActivityDescription());
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.v("Salida", "Nada seleccionado");
			}
		});
		
		
		//rowview
		rowDownSportImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sportActivitySpinner.performClick();
			}
			
		});
		
		
	}
	
	private void loadTrainersByIdTrainer(){
		
	}
	
	
	
	private void loadData() {
		loadSportActivities();
	}
	
	private void loadSportActivities(){
		
		List<SportActivity> sportActivityList = DataBaseHelperManager.getSportActivityDAO().getAllSportActivities();
		if ( sportActivityList != null){
			ArrayAdapter<SportActivity> sportActivityArrayAdapter = new ArrayAdapter<SportActivity>(this, 
																									android.R.layout.simple_dropdown_item_1line,
																									sportActivityList);
			sportActivitySpinner.setAdapter(sportActivityArrayAdapter);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_trainers, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
