package com.gabrielglez.leonidastraining;

import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.dialogservice.DialogService;
import com.gabrielglez.leonidastraining.model.UserGym;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

import android.support.v7.app.ActionBarActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends ActionBarActivity {
	
	private Button saveConfigurationButton;
	private EditText dniUserEditText;
	private EditText phoneUserEditText;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_configuration);
		
		initComponent();
		setOrientation();
		initListener();
		initValues();
		
		
	}

	private void setOrientation() {
	    
		int current = getRequestedOrientation();

	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	    
}

	
	private void initValues() {
		dniUserEditText.setText(StaticValues.USER_DNI);
		phoneUserEditText.setText(StaticValues.USER_PHONE);
		
	}

	private void initComponent() {
		saveConfigurationButton = (Button)findViewById(R.id.save_configuration_button);
		dniUserEditText = (EditText)findViewById(R.id.dniUserEditText);
		phoneUserEditText = (EditText)findViewById(R.id.phoneUserEditText);
	}

	
	
	
	
	private void initListener() {
		saveConfigurationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				UserGym userGym = DataBaseHelperManager.getUserGymDAO().get(1);
				if ( userGym != null){
					
					if ( !phoneUserEditText.getText().toString().equals("") && 
						 ! phoneUserEditText.getText().toString().equals("") ){  
					
						userGym.setUserGymDni(dniUserEditText.getText().toString());
						userGym.setUserGymPhone(phoneUserEditText.getText().toString());
						
						if(DataBaseHelperManager.getUserGymDAO().update(userGym)){
							//Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_LONG).show();
							Log.v("Salida", "Usuario actualizado");
							
							//Actualizamos los valores del usuario online
							StaticValues.USER_DNI = userGym.getUserGymDni();
							StaticValues.USER_PHONE = userGym.getUserGymPhone();
							
							Toast.makeText(ConfigurationActivity.this, 
									       StaticValues.MSG_USER_UPDATED_SUCCESS, 
									       Toast.LENGTH_LONG).show();
							onBackPressed();
						}
					
					}else{
						DialogService.informationDialogService(ConfigurationActivity.this,
															   StaticValues.MSG_USER_PHONE_DNI_IS_EMPTY,
															   StaticValues.MSG_TITLE_DIALOG_INFO ,
															   StaticValues.MSG_BUTTON_TITLE_ACCEPT);
					}
					
				}
			}
			
		});
		
	}


	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuration, menu);
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
	}*/
}
