package com.gabrielglez.leonidastraining;

import java.util.List;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gabrielglez.leonidastraining.adapter.spinner.ListTrainerContentListViewAdapter;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.BodyPlace;
import com.gabrielglez.leonidastraining.model.Day;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

//En esta actividad se mustran los ejercicios por dia seleccionado
//leonidas color azul 0583F9

public class TrainerContentActivity extends ActionBarActivity {
	
	private Spinner daySpinner ;
	private Spinner bodyPlaceSpinner;
	private ListView exercicestrainersListListView;
	private TextView titleTrainerTextView;
	private Chronometer trainerChronometer;
	
	private Button startChronometer;
	private Button stopChrometer;
	
	private CheckBox showLikeGroupCheckBox;

	
	long mLastStopTime = 0L;
	
	private boolean trainerStarted = false;
	private ImageView rowDownDayImageView;
	private ImageView rowDownBodyPlaceImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_trainer_content);
		initContent();
		setOrientation();
		initListener();
		loadData();
		
	}

	
	private void setOrientation() {
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}


	private void loadData() {
		titleTrainerTextView.setText(StaticValues.TEXT_SELECTED_TRAINER);
		loadDays();
		
	}
	
	private void loadDays(){
		List<Day> dayList = DataBaseHelperManager.getConfigurationTrainerDAO().getDaysInTrainerByIdTrainer(StaticValues.ID_SELECTED_TRAINER);
		if ( dayList != null){
			ArrayAdapter<Day> dayActivityArrayAdapter = new ArrayAdapter<Day>(this,android.R.layout.simple_dropdown_item_1line,dayList);
			daySpinner.setAdapter(dayActivityArrayAdapter);
		}
	}

	private void loadBodyPlacesByIdTrainerAndIdDay(Integer idTrainer , Integer idDay){
		
		List<BodyPlace> bodyPlaceList = DataBaseHelperManager
									   .getConfigurationTrainerDAO()
									   .getBodyPlacesInTrainerByIdTrainerAndIdDay(idTrainer , idDay);

		if ( bodyPlaceList != null){
			ArrayAdapter<BodyPlace> bodyPlaceArrayAdapter = new ArrayAdapter<BodyPlace>(this,android.R.layout.simple_dropdown_item_1line,bodyPlaceList);
			bodyPlaceSpinner.setAdapter(bodyPlaceArrayAdapter);
		}
	}
	
	private void showAllExerciseTogether(Integer idTrainer , Integer idDay){
		

		//Obtenemos los ejercicios en este dia para el entrenamiento seleccionado
		List<Exercise> exerciseList = DataBaseHelperManager.getConfigurationTrainerDAO().getExercisesInTrainerTogether(idTrainer, idDay);
		if ( exerciseList != null){
			ListTrainerContentListViewAdapter trainerContentAdapter = new ListTrainerContentListViewAdapter(TrainerContentActivity.this ,exerciseList , showLikeGroupCheckBox.isChecked()  );
			exercicestrainersListListView.setAdapter(trainerContentAdapter);
		}
		
	}
	
	
	private void loadTrainerByIdDayAndBodyPlace(Integer idTrainer , Integer idBodyPlace , Integer idDay){
		

		//Obtenemos los ejercicios en este dia para el entrenamiento seleccionado
		List<Exercise> exerciseList = DataBaseHelperManager.getConfigurationTrainerDAO().getExercisesInTrainerByIdTrainerIdBodyPlaceAndIdDay(idTrainer, idBodyPlace, idDay);
		if ( exerciseList != null){
			ListTrainerContentListViewAdapter trainerContentAdapter = new ListTrainerContentListViewAdapter(TrainerContentActivity.this ,exerciseList , showLikeGroupCheckBox.isChecked());
			exercicestrainersListListView.setAdapter(trainerContentAdapter);
		}
		
	}
	

	private void initContent() {
		exercicestrainersListListView = (ListView)findViewById(R.id.exercises_in_trainer_list_listview);
		daySpinner = (Spinner)findViewById(R.id.day_trainer_spinner);
		bodyPlaceSpinner = (Spinner)findViewById(R.id.body_place_trainer_spinner);
		titleTrainerTextView = (TextView)findViewById(R.id.title_trainer_textview);
		trainerChronometer = (Chronometer)findViewById(R.id.trainer_chronometer);
		startChronometer = (Button)findViewById(R.id.start_cron_button);
		stopChrometer = (Button)findViewById(R.id.stop_cron_button);
        rowDownDayImageView = (ImageView)findViewById(R.id.day_row_down_image_view);
        rowDownBodyPlaceImageView = (ImageView)findViewById(R.id.body_place_image_view);
        showLikeGroupCheckBox = (CheckBox)findViewById(R.id.body_place_checkBox);
       
	}
	
	private void initListener() {
		setDaySpinnerListener();
		setBodyPlaceSpinner();
		setChronButtons();
		setImageViewListener();
		setBodyPlaceCheckBox();
	}
	
	private void setBodyPlaceCheckBox(){
		
		showLikeGroupCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				BodyPlace bodyPlaceSelected = (BodyPlace)bodyPlaceSpinner.getSelectedItem();
				StaticValues.ID_SELECTED_BODY_PLACE = bodyPlaceSelected.getBodyPlaceIdServer();
				
				
				if ( ! showLikeGroupCheckBox.isChecked() ){
					
					
					if ( bodyPlaceSelected != null){
					showAllExerciseTogether(StaticValues.ID_SELECTED_TRAINER, 
											StaticValues.ID_SELECTED_DAY);
					
					//bodyPlaceSpinner.setEnabled(false);
					disableBodyPlaceSpinner();
					
					Log.v("CHECKBOX", "CHECKBOX- NO CHECKED");
					}
					
					
				}else{
					
					//BodyPlace bodyPlaceSelected = (BodyPlace)bodyPlaceSpinner.getSelectedItem();
					if ( bodyPlaceSelected != null){
						
						
						//StaticValues.ID_SELECTED_BODY_PLACE = bodyPlaceSelected.getBodyPlaceIdServer();
						
						//loadTrainerByIdDayAndBodyPlace(StaticValues.ID_SELECTED_TRAINER , 
						//							   StaticValues.ID_SELECTED_BODY_PLACE,
						//		   					   StaticValues.ID_SELECTED_DAY);
						
						loadTrainerByIdDayAndBodyPlace(StaticValues.ID_SELECTED_TRAINER , 
								  bodyPlaceSelected.getBodyPlaceIdServer() ,
			   			   	      StaticValues.ID_SELECTED_DAY);
						

						
						enableBodyPlaceSpinner();
						
						Log.v("CHECKBOX", "CHECKBOX- IN CHECKED");
					
					}
					

				}
				
			}
		});
		
	}

	
	private void setImageViewListener() {
		
		 rowDownDayImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					daySpinner.performClick();
					
				}
			});
		 
		 rowDownBodyPlaceImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					bodyPlaceSpinner.performClick();
				}
				
			});
		
	}
	
	
	public void enableBodyPlaceSpinner(){
		bodyPlaceSpinner.setEnabled(true);
		rowDownBodyPlaceImageView.setEnabled(true);
		
	}
	
	public void disableBodyPlaceSpinner(){
		bodyPlaceSpinner.setEnabled(false);
		rowDownBodyPlaceImageView.setEnabled(false);
	}


	private void setChronButtons() {
		
		startChronometer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if ( trainerStarted == false){
				
					final Dialog dialog = new Dialog(TrainerContentActivity.this);
					dialog.setContentView(R.layout.dialog_confirmation_yes_no);
					dialog.setTitle(StaticValues.MSG_TITLE_DIALOG_INFO);
	
					TextView text = (TextView) dialog.findViewById(R.id.text);
					text.setText(StaticValues.MSG_START_TRAINER);
					ImageView image = (ImageView) dialog.findViewById(R.id.image);
					image.setImageResource(R.drawable.ic_launcher);
	
					Button buttonYes = (Button) dialog.findViewById(R.id.dialogButtonYes);
					// if button is clicked, close the custom dialog
					buttonYes.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							trainerStarted = true;
							
							chronometer(true);
							
							
							dialog.dismiss();
							
						}
	
					});
					
					
					Button buttonNo = (Button) dialog.findViewById(R.id.dialogButtonNO);
					buttonNo.setOnClickListener(new OnClickListener() {
						
						@Override 
						public void onClick(View v) {
							trainerStarted = false;
							dialog.dismiss();
						}
	
					});
					dialog.show();
			
				}
				else{
					
					chronometer(false);
					
				}//end if trainerStarted
					
			}
			
		});
		
		
		stopChrometer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				

				final Dialog dialog = new Dialog(TrainerContentActivity.this);
				dialog.setContentView(R.layout.dialog_confirmation_yes_no);
				dialog.setTitle(StaticValues.MSG_TITLE_DIALOG_INFO);

				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText(StaticValues.MSG_STOP_TRAINER);
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.ic_launcher);

				Button buttonYes = (Button) dialog.findViewById(R.id.dialogButtonYes);
				// if button is clicked, close the custom dialog
				buttonYes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						trainerStarted = false;
						//chronometer();
						trainerChronometer.stop();
						trainerChronometer.setText("00:00");
						startChronometer.setText("Start");
						
						dialog.dismiss();
						
						onBackPressed();
						
					}

				});
				
				
				Button buttonNo = (Button) dialog.findViewById(R.id.dialogButtonNO);
				buttonNo.setOnClickListener(new OnClickListener() {
					
					@Override 
					public void onClick(View v) {
						//trainerStarted = false;
						dialog.dismiss();
					}

				});
				dialog.show();
				
				
				
			}
		});
		
	}


	private void chronometer(boolean reset){
		
		if ( reset ){
			
			trainerChronometer.setBase(SystemClock.elapsedRealtime()); 
			trainerChronometer.start();
			startChronometer.setText("Pause");
			
		}else{
			
			if ( startChronometer.getText().equals("Start") ){
				
				trainerChronometer.setBase(SystemClock.elapsedRealtime() + mLastStopTime); 
				trainerChronometer.start();
				startChronometer.setText("Pause");
				
		}
		else{

			mLastStopTime = trainerChronometer.getBase() - SystemClock.elapsedRealtime(); 
			trainerChronometer.stop();
			startChronometer.setText("Start");
			
		}

			
			
		}
		
		
		
	}
	
	
	private void setDaySpinnerListener(){
		
		daySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			//al elegir el dia cargamos los ejercicios de ESTE DIA y ESTE ENTRENAMIENTO
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				Day dayItem = (Day)daySpinner.getSelectedItem();

				if ( dayItem != null ){
					StaticValues.ID_SELECTED_DAY = dayItem.getDayIdServer();
					
					
					loadBodyPlacesByIdTrainerAndIdDay(StaticValues.ID_SELECTED_TRAINER , dayItem.getDayIdServer());
					
					if ( showLikeGroupCheckBox.isChecked()){
						
						
						//	Al cambiar de dia cargamos las partes del cuerpo de ese entrenamiento
						Log.v("Salida", "Cargo por zonas");
						
					}
					else{
						
						showAllExerciseTogether(StaticValues.ID_SELECTED_TRAINER, 
												StaticValues.ID_SELECTED_DAY);
						
						//bodyPlaceSpinner.setEnabled(false);
						disableBodyPlaceSpinner();
						
						Log.v("Salida", "Empieza carga todo junto");
					}
					
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.v("Salida", "Nada seleccionado");
			}
		});;
	}
	
	
	private void setBodyPlaceSpinner(){
		
		bodyPlaceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			//al elegir el dia cargamos los ejercicios de ESTE DIA y ESTE ENTRENAMIENTO
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				BodyPlace bodyPlaceItem = (BodyPlace)bodyPlaceSpinner.getSelectedItem();
				
				if ( showLikeGroupCheckBox.isChecked()){
					
					//BodyPlace bodyPlaceItem = (BodyPlace)bodyPlaceSpinner.getSelectedItem();
					if ( bodyPlaceItem != null ){
						StaticValues.ID_SELECTED_BODY_PLACE = bodyPlaceItem.getBodyPlaceIdServer();
						
						
						
						loadTrainerByIdDayAndBodyPlace(StaticValues.ID_SELECTED_TRAINER, 
													   StaticValues.ID_SELECTED_BODY_PLACE,
													   StaticValues.ID_SELECTED_DAY);

						Log.v("Salida",bodyPlaceItem.getBodyPlaceDescription());
						
					}
	
				}else{
					
					if ( bodyPlaceItem != null ){
					
						StaticValues.ID_SELECTED_BODY_PLACE = bodyPlaceItem.getBodyPlaceIdServer();
						showAllExerciseTogether(StaticValues.ID_SELECTED_TRAINER, 
												StaticValues.ID_SELECTED_DAY);
					
					}
	
					//bodyPlaceSpinner.setEnabled(false);
					
				}
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.v("Salida", "Nada seleccionado");
			}
		});
		
	}
	

	
}
