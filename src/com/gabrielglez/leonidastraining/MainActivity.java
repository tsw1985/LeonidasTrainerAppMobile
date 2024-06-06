package com.gabrielglez.leonidastraining;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielglez.leonidastraining.asynctask.TaskGetUserTrainer;
import com.gabrielglez.leonidastraining.database.DataBaseHelper;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.BodyWeight;
import com.gabrielglez.leonidastraining.model.UserGym;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

public class MainActivity extends ActionBarActivity {
	
	public static DataBaseHelper db;
	
	private Button configurationButtonMainMenu;
	private Button syncronizeDataButtonMainMenu;
	private Button myTrainersButton;
	private Button historyButton;
	private Button aboutButton;
	private Button saveBodyWeightButton;
	
	
	
	private EditText editText ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
		initComponent();
		initListener();
		setOrientation();
		
	
		db = new DataBaseHelper(this);
		new DataBaseHelperManager(db);
		
		createLocalUser();
		
		
		
		//String jsonData = "";
		//AsyncTask<String, Void, String> getUsersTrainer = new TaskGetUserTrainer(editText);
		//getUsersTrainer.execute();
		
		/*
		try {
			String valueAsyncTask = getUsersTrainer.get();
			Toast.makeText(this, valueAsyncTask , Toast.LENGTH_LONG).show();
			Log.v("Salida", valueAsyncTask );
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	
	private void setOrientation() {
			int current = getRequestedOrientation();
		    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
		        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
		    }
	}

	
	private void initComponent() {
		//editText = (EditText)findViewById(R.id.testEditText);
		configurationButtonMainMenu = (Button)findViewById(R.id.configuration_button_main_menu);
		syncronizeDataButtonMainMenu = (Button)findViewById(R.id.syncronizeDataButton);
		myTrainersButton = (Button)findViewById(R.id.mytrainersbutton);
		aboutButton = (Button)findViewById(R.id.info_button);
		historyButton = (Button)findViewById(R.id.historybutton);
		saveBodyWeightButton = (Button)findViewById(R.id.save_body_weight_button);
	}

	
	private void initListener() {
		
		configurationButtonMainMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openConfigurationActivity();
			}
		});
		
		
		//Boton de sincronizacion
		syncronizeDataButtonMainMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.dialog_confirmation_yes_no);
				dialog.setTitle(StaticValues.MSG_TITLE_DIALOG_INFO);

				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText(StaticValues.MSG_SYNCRONIZATION_CONFIRMATION);
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.ic_launcher);

				Button buttonYes = (Button) dialog.findViewById(R.id.dialogButtonYes);
				// if button is clicked, close the custom dialog
				buttonYes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						//Comprobamos si el usuario esta configurado
						if ( StaticValues.USER_DNI.equals("") || StaticValues.USER_PHONE.equals("")){
							launchUserConfigurationDialog();
							openConfigurationActivity();
						}
						else
						{ //Si esta configurado
							
							AsyncTask<String, String, String> getUsersTrainer = new TaskGetUserTrainer(editText , MainActivity.this );
							getUsersTrainer.execute();
						}
						
						dialog.dismiss();
						
					}

				});
				
				
				Button buttonNo = (Button) dialog.findViewById(R.id.dialogButtonNO);
				buttonNo.setOnClickListener(new OnClickListener() {
					
					@Override 
					public void onClick(View v) {
						dialog.dismiss();
					}

				});
				dialog.show();
			}
			
		});
		
		
		//my trainers
		myTrainersButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openMyTrainersActivity();
			}
			
		});
		
		
		//About button
		aboutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openAboutActivity();
			}
			
		});
		
		
		//History button
		historyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openHistoryActivity();
			}
			
		});
		
		//save my weigth
		saveBodyWeightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveBodyWeightDialog();
			}
		});
	}
	
	
	
	private void saveBodyWeightDialog(){
		
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog_save_body_kg);
			 
		
		
		
		Button dialogButton = (Button) dialog.findViewById(R.id.savebodyKgButton);
		TextView lastWeightTextView = (TextView)dialog.findViewById(R.id.last_weight_textview);
		
		
		//Set label last weight entered
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		
		final String todayString = df.format(today);
		
		final BodyWeight bodyWeight = DataBaseHelperManager
                .getBodyWeigthDAO()
                .getToUpdateBodyWeightByStringDate(todayString);
		

		setLastBodyWeightText(lastWeightTextView,todayString);
		
		
		
		dialogButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText kgEditText = (EditText)dialog.findViewById(R.id.savebodylKgEditText);
				
				final TextView lastWeightTextView = (TextView)dialog.findViewById(R.id.last_weight_textview);
				
				if ( ! kgEditText.getText().toString().equals("")){
					
					
					if ( bodyWeight != null ){
						
						
						BodyWeight weight = new BodyWeight();
						weight.setDateWeight(new Date());
						weight.setBodyWeigthKg(Integer.parseInt(kgEditText.getText().toString()));
						
						if ( DataBaseHelperManager.getBodyWeigthDAO().update(weight)){
							bodyWeightDoneToast();
							Log.v("Salida","Peso creado UPDATE");
						}
						
						setLastBodyWeightText(lastWeightTextView , todayString);
						
						
					}else{
	                            
						BodyWeight bw = new BodyWeight();
						bw.setDateWeight(new Date());
						bw.setBodyWeigthKg(Integer.parseInt(kgEditText.getText().toString()));

						if ( DataBaseHelperManager.getBodyWeigthDAO().create(bw)){
							
							setLastBodyWeightText(lastWeightTextView , todayString);
							bodyWeightDoneToast();
									
							Log.v("Salida","Peso creado NEW");
									
						}
					}
					
				}
				
				dialog.dismiss();
			}
			
		});

		dialog.show();
	}
	
	private void setLastBodyWeightText(TextView lastWeightTextView ,  String todayString){
		
		BodyWeight lastBodyWeight = DataBaseHelperManager.getBodyWeigthDAO().getLastBodyWeightByStringDate(todayString);
		if ( lastBodyWeight != null){
			lastWeightTextView.setText("Último peso registrado: " +  String.valueOf(lastBodyWeight.getBodyWeigthKg() ));
		}
		
	}
	
	
	private void bodyWeightDoneToast(){
		Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
	}
	
	private void openHistoryActivity(){
		Intent mainIntent = new Intent(MainActivity.this , HistoryActivity.class);
        startActivity(mainIntent);
	}
	
	private void openAboutActivity(){
		Intent mainIntent = new Intent(MainActivity.this , AboutActivity.class);
        startActivity(mainIntent);
	}
	
	
	private void openMyTrainersActivity(){
		Intent mainIntent = new Intent(MainActivity.this , ListTrainersActivity.class);
         startActivity(mainIntent);
        //finish();
	}
	
	
	
	private void openConfigurationActivity(){
		Intent mainIntent = new Intent(MainActivity.this , ConfigurationActivity.class);
        startActivity(mainIntent);
        //finish();
	}
	


	private void createLocalUser() {
		
		UserGym userGym = DataBaseHelperManager.getUserGymDAO().get(1);
		
		if (userGym == null){
			userGym = new UserGym();
			userGym.setUserGymDni("");
			userGym.setUserGymIdServer(-1);
			userGym.setUserGymName("");
			userGym.setUserGymPhone("-1");
			
			if( DataBaseHelperManager.getUserGymDAO().create(userGym) ){
				Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, "Error creear usuario", Toast.LENGTH_LONG).show();
			}
			
		}
		else{
			//Si existe cargamos los valores locales en las variables estaticas
			//Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show();
			StaticValues.USER_DNI = userGym.getUserGymDni();
			StaticValues.USER_PHONE = userGym.getUserGymPhone();
		}
	}
	
	
	private void launchUserConfigurationDialog(){
		
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog_information);
		dialog.setTitle(StaticValues.MSG_TITLE_DIALOG_INFO);

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(StaticValues.MSG_USER_NOT_CONFIGURATED);
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openConfigurationActivity();
				dialog.dismiss();
			}

		});

		dialog.show();
	}
	
	
	


	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
