package com.gabrielglez.leonidastraining.adapter.spinner;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gabrielglez.leonidastraining.ListTrainersActivity;
import com.gabrielglez.leonidastraining.MainActivity;
import com.gabrielglez.leonidastraining.R;
import com.gabrielglez.leonidastraining.TrainerContentActivity;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.Trainer;
import com.gabrielglez.leonidastraining.model.TrainerCounter;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

public class ListTrainerListViewAdapter extends BaseAdapter{
	
	private Activity activity ;
	private List<Trainer> listTrainer;
	
	public ListTrainerListViewAdapter(Activity activity , List<Trainer> listTrainer ){
		this.activity = activity;
		this.listTrainer = listTrainer;
	}

	@Override
	public int getCount() {
		return listTrainer.size();
	}

	@Override
	public Object getItem(int position) {
		return (Trainer)listTrainer.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listTrainer.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		
		final Trainer trainer = listTrainer.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		final View item = inflater.inflate(R.layout.listview_trainers_layout, null);
        
        TextView trainerDescriptionTextView = (TextView) item.findViewById(R.id.trainer_description_textview);
        String trainerTitle = "";
        if ( trainer.getTrainerDescription().length() >= 20){
        	trainerTitle = trainer.getTrainerDescription().substring(0, 19);
        }else{
        	trainerTitle = trainer.getTrainerDescription();
        }
        
        trainerDescriptionTextView.setText(trainerTitle);

        Button openTrainerButton = (Button)item.findViewById(R.id.open_trainer_button);
        openTrainerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				//Al hacer click en el entrenamiento seteamos su id y su texto
				StaticValues.ID_SELECTED_TRAINER =trainer.getTrainerIDServer(); 
				StaticValues.TEXT_SELECTED_TRAINER = trainer.getTrainerDescription();
				
				
				//Reiniciamos las variables estaticas ya que pueden contener valores anteriores
				StaticValues.ID_SELECTED_BODY_PLACE = 0;
				StaticValues.ID_SELECTED_DAY = 0;

				
				
				//Creamos un contador nuevo y lo seteamos a la variable global statica
				TrainerCounter trainer_counter = new TrainerCounter();
				trainer_counter.setTrainerCounterValue(0);
				DataBaseHelperManager.getTrainerCounterDAO().create(trainer_counter);
				
				//Guardamos el contador y cargamos el ultimo id
				StaticValues.ID_LAST_TRAINER_COUNTER = DataBaseHelperManager.getTrainerCounterDAO().getLastId();
				Log.v("Salida","ID LAST COUNTER EN ADAPTER --------- " + StaticValues.ID_LAST_TRAINER_COUNTER);
				
				
				openMyTrainersContentActivity(trainer.getTrainerIDServer());
				Log.v("Salida","Pulsado entrenamiento " + trainer.getTrainerDescription() );
				Log.v("Salida","VALOR ESTATICO IDTRAINER " + StaticValues.ID_LAST_TRAINER_COUNTER );
			}
        	
        });

        return item;
		
	}
	
	private void openMyTrainersContentActivity(Integer idTrainer){
		Intent trainerContentIntent = new Intent(activity , TrainerContentActivity.class);
        activity.startActivity(trainerContentIntent);
	}

}
