package com.gabrielglez.leonidastraining;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HistoryActivity extends ActionBarActivity {
	
	private Button myevolutionkg_button ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_history);
		initComponent();
		initListener();
		setOrientation();
	}

	private void initComponent() {
		myevolutionkg_button = (Button)findViewById(R.id.myevolutionkg_button);
		
	}
	
	private void initListener() {
		myevolutionkg_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openMyEvolutionKgActivity();
			}
			
		});
		
	}

	private void setOrientation() {
		int current = getRequestedOrientation();
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	    }
	}
	
	
	private void openMyEvolutionKgActivity(){
		Intent mainIntent = new Intent(HistoryActivity.this , MyEvolutionKgActivity.class);
        startActivity(mainIntent);
        //finish();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
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
