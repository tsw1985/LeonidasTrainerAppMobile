package com.gabrielglez.leonidastraining.adapter.spinner;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabrielglez.leonidastraining.R;
import com.gabrielglez.leonidastraining.TrainerContentActivity;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.gabrielglez.leonidastraining.model.ExerciseDoneHistory;
import com.gabrielglez.leonidastraining.model.ExerciseTimesHistory;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

public class ListTrainerContentListViewAdapter extends BaseAdapter{
	
	//ADAPTADOR PARA : Este adaptador es el encargado de cargar el contenido de un entrenamiento 
	//es decir , una vez pulsamos sobre un entrenamiento , este muestra sus ejercicios.
	
	
	
	
	private Activity activity ;
	private List<Exercise> listExercises;
	private Bitmap decodedByte = null;
	private boolean  showLikeGroup;
	
	
	public ListTrainerContentListViewAdapter(Activity activity , List<Exercise> listExercises , boolean showLikeGroup){
		this.activity = activity;
		this.listExercises = listExercises;
		this.showLikeGroup = showLikeGroup;
	}

	@Override
	public int getCount() {
		return listExercises.size();
	}

	@Override
	public Object getItem(int position) {
		return (Exercise)listExercises.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listExercises.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		
		final Exercise exercise = listExercises.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		final View item = inflater.inflate(R.layout.listview_trainers_content_layout, null);
        
        TextView exerciseDescriptionTextView = (TextView) item.findViewById(R.id.exercise_description_textview);
        setTrainerTitle(exercise ,exerciseDescriptionTextView );
        

        final TextView counter_actual_repetitions_textview = (TextView)item.findViewById(R.id.counter_actual_repetitions_textview);
        TextView total_repetitions_textview = (TextView)item.findViewById(R.id.total_repetitions_textview);
        setRepetitionData(total_repetitions_textview , counter_actual_repetitions_textview , position , exercise ,item );

        final TextView value_total_repetitionsTextView = (TextView)item.findViewById(R.id.value_total_repetition_textview);
        final TextView kg_saved_text_view = (TextView)item.findViewById(R.id.kg_saved_text_view);
        
        ImageView add_time_imageview = (ImageView)item.findViewById(R.id.add_time_imageview);
        add_time_imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addTimeRepetition(exercise,value_total_repetitionsTextView ,counter_actual_repetitions_textview, kg_saved_text_view , position);
			} // end click
			
		});
        
        
        ImageView substract_time_imageview = (ImageView)item.findViewById(R.id.substract_time_imageview);
        substract_time_imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				subtractTimeRepetition(exercise,
							          value_total_repetitionsTextView , 
							          counter_actual_repetitions_textview, 
							          kg_saved_text_view , 
							          position);
			
			} //end click
			
			
		});
        
        
        Button save_repetitions_data_button = (Button)item.findViewById(R.id.set_repetitions_button);
        save_repetitions_data_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final Dialog dialog = new Dialog(activity);
				dialog.setContentView(R.layout.dialog_save_repetitions);
					 
				Button dialogButton = (Button) dialog.findViewById(R.id.saveKgButton);
				dialogButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						saveRepetitionData( exercise, kg_saved_text_view , dialog);
						dialog.dismiss();
					}
					
				});

				dialog.show();
				
			}
		});
        
        
        
 //CheckBox -------------------------------------------------------
        final CheckBox exerciseDoneCheckBox = (CheckBox)item.findViewById(R.id.exercise_done_checkbox);
        exerciseDoneCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exercise.setCheckSelected(exerciseDoneCheckBox.isChecked());
				

				
				saveExerciseDone( exerciseDoneCheckBox.isChecked() , exercise );
			}
			
		});
        
        if ( getExerciseDone( exercise.getExerciseIdServer() , exercise.getActualTime() , showLikeGroup , exercise )){
        	exerciseDoneCheckBox.setChecked(true);
        }else {
        	exerciseDoneCheckBox.setChecked(false);
        }
        
  //end checkbox -------------------------------------
        
        
        

        
        //Click in exercise info -----------------
        ImageView exerciseInfo = (ImageView)item.findViewById(R.id.imageinfo);

        if ( ! exercise.getExerciseImage().equals("")){
        	exerciseInfo.setImageBitmap( getExerciseImage(exercise));
        }
        
        
        exerciseInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showExerciseInfo(exercise);
			}
			
		});

        return item;
		
	}
	
	
	
	//METHODS
	private void setRepetitionData(TextView total_repetitions_textview ,TextView counter_actual_repetitions_textview , int position , Exercise exercise , View item){
		
		   total_repetitions_textview.setText(String.valueOf(exercise.getListTimes().size()     )); // -1
	        
	        //iniciamos el contador a 1
	        counter_actual_repetitions_textview.setText(String.valueOf(exercise.getActualTime() + 1 ));
	        
	        
	        final TextView value_total_repetitions = (TextView)item.findViewById(R.id.value_total_repetition_textview);
	        
	        //seteamos el primer valor nada mas arrancar la lista
	        if ( listExercises.get(position).getListTimes().size() > 0) {
	        	Integer actualTime = exercise.getActualTime();
	        	value_total_repetitions.setText(String.valueOf( listExercises.get(position).getListTimes().get( actualTime ) ));
	        }
	        
	        
	        //Kg en esa serie
	        final TextView kg_saved_text_view = (TextView)item.findViewById(R.id.kg_saved_text_view);
	        if ( listExercises.get(position).getListKgByTime().size() > 0) {
	        	Integer actualTime = exercise.getActualTime();
	        	//kg_saved_text_view.setText("Kg: " + String.valueOf( listExercises.get(position).getListKgByTime().get( actualTime ) ));
	        	
	        	//------------
	        	
	        	Integer kgInTime = getTimesInRepetition(exercise.getExerciseIdServer() , actualTime , exercise);
				kg_saved_text_view.setText("Kg:" + String.valueOf(kgInTime));
	        	
	        }
	}
	
	
	private void setTrainerTitle(Exercise exercise , TextView exerciseDescriptionTextView){
		String trainerTitle = "";
        if ( exercise.getExerciseName().length() >= 20){
        	trainerTitle = exercise.getExerciseName().substring(0, 19);
        }else{
        	trainerTitle = exercise.getExerciseName();
        }
        
        exerciseDescriptionTextView.setText(trainerTitle);
	}
	
	
	private Integer getTimesInRepetition(Integer idExercise , Integer actualTime , Exercise exerciseParam){
		
		Integer idTrainer = StaticValues.ID_SELECTED_TRAINER;
		Integer idDaySelected = StaticValues.ID_SELECTED_DAY;
		Integer idBodyPlace = exerciseParam.getIdBodyPlace(); //StaticValues.ID_SELECTED_BODY_PLACE;
    	
    	Integer totalKg = DataBaseHelperManager
				 .getExerciseTimesHistoryDAO()
				 .getExerciseRepetition(idTrainer, 
						 			    idExercise, 
						 			    idDaySelected, 
						 			    idBodyPlace, 
						 			    StaticValues.ID_LAST_TRAINER_COUNTER,
						 			    actualTime);
    	
    	return totalKg;
		
	}
	
	
	private boolean getExerciseDone(Integer idExercise , Integer actualTime , boolean showLikeGroupParam , Exercise exerciseParam){
		
		
		
		Integer idTrainer = StaticValues.ID_SELECTED_TRAINER;
		Integer idDaySelected = StaticValues.ID_SELECTED_DAY;
		//Integer idBodyPlace = StaticValues.ID_SELECTED_BODY_PLACE;
		Integer idBodyPlace = exerciseParam.getIdBodyPlace();
		Integer trainerCounter = StaticValues.ID_LAST_TRAINER_COUNTER;
		
		boolean done = false;
		
		if ( showLikeGroupParam ){
			
			done = DataBaseHelperManager
				       .getExerciseDoneHistoryDAO()
				       .getExerciseDone(
				    		            idTrainer, 
				    		            idExercise, 
				    		            idDaySelected, 
				    		            idBodyPlace, 
				    		            trainerCounter, 
				    		            actualTime);
		}
		else{
			
			done = DataBaseHelperManager
				       .getExerciseDoneHistoryDAO()
				       .getExerciseDoneAllExercisesTogether(
				    		            idTrainer, 
				    		            idExercise, 
				    		            idDaySelected, 
				    		            trainerCounter, 
				    		            actualTime);
			
		}
		
		return done;
		
		
	}
	
	
	private void addTimeRepetition(Exercise exercise , 
								   TextView value_total_repetitions_text_view , 
								   TextView counter_actual_repetitions_textview , 
								   TextView kg_saved_text_view , 
								   Integer position){
		
		Integer actualTime = exercise.getActualTime();
		actualTime++;
		
		if ( actualTime < exercise.getListTimes().size() ){
			
			Integer times = exercise.getListTimes().get( actualTime );
			if ( times != null ){
				
				value_total_repetitions_text_view.setText(String.valueOf( times ));
				exercise.setActualTime(actualTime);
				counter_actual_repetitions_textview.setText(String.valueOf(exercise.getActualTime() + 1 ));
				Log.v("Salida","ADD Valor indice actualTime " + exercise.getActualTime() + " LONGITUD LISTA: " + (exercise.getListTimes().size() - 1 )  );
				
				//Vaciamos etiqueta de kg
				//Si es mayor a 0 quiere decir que ya se han introducido repeticiones
				if (  listExercises.get(position).getListTimes().size() > 0){
					//kg_saved_text_view.setText("Kg: " + String.valueOf( listExercises.get(position).getListKgByTime().get( actualTime ) ));
					
					Integer kgInTime = getTimesInRepetition(exercise.getExerciseIdServer() , actualTime , exercise);
					kg_saved_text_view.setText("Kg:" + String.valueOf(kgInTime));
				}
				else{ //sino pues lo ponemos en vacio
					kg_saved_text_view.setText("Kg:");
				}
			}
			
		}
		
	}
	
	
	private void subtractTimeRepetition(Exercise exercise , 
			   TextView value_total_repetitions_text_view , 
			   TextView counter_actual_repetitions_textview , 
			   TextView kg_saved_text_view , 
			   Integer position){
		
		Integer actualTime = exercise.getActualTime();
		
		actualTime --;
		
		if ( actualTime >=0){
		
			Integer times = exercise.getListTimes().get( actualTime );
			if ( times != null ){
				value_total_repetitions_text_view.setText(String.valueOf( times ));
				exercise.setActualTime(actualTime);
				counter_actual_repetitions_textview.setText(String.valueOf(exercise.getActualTime() + 1));
				Log.v("Salida","SUBSTRACT Valor indice ActualTime " + exercise.getActualTime() + " LONGITUD LISTA: " + (exercise.getListTimes().size() - 1 ) ) ;

				//Vaciamos etiqueta de kg
				//Si es mayor a 0 quiere decir que ya se han introducido repeticiones
				if (  listExercises.get(position).getListTimes().size() > 0){
					//kg_saved_text_view.setText("Kg:" + String.valueOf( listExercises.get(position).getListKgByTime().get( actualTime ) ));
					
					Integer kgInTime = getTimesInRepetition(exercise.getExerciseIdServer() , actualTime , exercise);
					kg_saved_text_view.setText("Kg:" + String.valueOf(kgInTime));
					
				}
				else{ //sino pues lo ponemos en vacio
					kg_saved_text_view.setText("Kg:");
				}
			}
		}
		
	}
	

	private void saveRepetitionData(Exercise exerciseItem , TextView kg_saved_text_view , Dialog dialog){
		
		Integer actualRepetition = exerciseItem.getActualTime();
		EditText kg = (EditText) dialog.findViewById(R.id.totalKgInRepetitionEditText);
		
		if ( ! kg.getText().toString().equals("") && exerciseItem.getListKgByTime().size() > 0 ){
		
			exerciseItem.getListKgByTime().set( actualRepetition, Integer.parseInt(kg.getText().toString()) );
			//save the repetition in database
		
		
			Integer idTrainer = StaticValues.ID_SELECTED_TRAINER;
			Integer idExercise = exerciseItem.getExerciseIdServer();
			Integer idDaySelected = StaticValues.ID_SELECTED_DAY;
			Integer idBodyPlace = exerciseItem.getIdBodyPlace(); //StaticValues.ID_SELECTED_BODY_PLACE;
			Integer times = Integer.parseInt(kg.getText().toString());
			
			//We save the kg history and later we load this value again
			ExerciseTimesHistory exerciseHistory = new ExerciseTimesHistory();
			exerciseHistory.setBodyPlace(idBodyPlace);
			exerciseHistory.setExercise(idExercise);
			exerciseHistory.setDay(idDaySelected);
			exerciseHistory.setTrainer(idTrainer);
			exerciseHistory.setTimes(times);
			exerciseHistory.setDateHistory(new Date());
			exerciseHistory.setConfIdTrainerCounter(StaticValues.ID_LAST_TRAINER_COUNTER);
			exerciseHistory.setActualTime(actualRepetition);
			
			//save the object 
			DataBaseHelperManager.getExerciseTimesHistoryDAO().create(exerciseHistory);
			
			Integer kgInTime = getTimesInRepetition(exerciseItem.getExerciseIdServer() , actualRepetition , exerciseItem);
			kg_saved_text_view.setText("Kg:" + String.valueOf(kgInTime));
		}
		
	}
	
	
	private void saveExerciseDone(boolean done , Exercise exercise){
		
		Integer idTrainer = StaticValues.ID_SELECTED_TRAINER;
		Integer idExercise = exercise.getExerciseIdServer();
		Integer idDaySelected = StaticValues.ID_SELECTED_DAY;
		Integer idBodyPlace = exercise.getIdBodyPlace();
		Integer actualTime = exercise.getActualTime();
		
		ExerciseDoneHistory exerciseDone = new ExerciseDoneHistory();
		exerciseDone.setActualTime(actualTime);
		exerciseDone.setBodyPlace( idBodyPlace );
		exerciseDone.setConfIdTrainerCounter(StaticValues.ID_LAST_TRAINER_COUNTER);
		exerciseDone.setDay(idDaySelected);
		exerciseDone.setDateHistory(new Date());
		exerciseDone.setExercise(idExercise);
		exerciseDone.setExerciseDone(done);
		exerciseDone.setTrainer(idTrainer);
		
		DataBaseHelperManager.getExerciseDoneHistoryDAO().create(exerciseDone);
		
	}
	
	
	private Bitmap getExerciseImage(Exercise exercise){
		
		Bitmap bitmap = null;
		
		//final Bitmap decodedByte = null;
		if ( exercise.getExerciseImage() != null){
			byte[] decodedString = Base64.decode(exercise.getExerciseImage(), Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
		}
		
		return bitmap;
		
	}
	
	
	private void showExerciseInfo(Exercise exercise){
		
		final Dialog dialog = new Dialog(activity);
		dialog.setContentView(R.layout.dialog_show_exercise_info);

		ImageView image = (ImageView)dialog.findViewById(R.id.exercise_image);
		

		//load the image in base64
		//final Bitmap decodedByte = null;
		if ( exercise.getExerciseImage() != null){
			byte[] decodedString = Base64.decode(exercise.getExerciseImage(), Base64.DEFAULT);
			decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
			image.setImageBitmap(decodedByte);
		}
		
		TextView titleExercise = (TextView)dialog.findViewById(R.id.title_exercise_textview);
		if ( exercise.getExerciseName() != null){
			titleExercise.setText(exercise.getExerciseName());
		}
		
		
		
		EditText exerciseDescription = (EditText)dialog.findViewById(R.id.exercise_description);
		
		if ( exercise.getExerciseDescription() != null){
			exerciseDescription.setText(exercise.getExerciseDescription());
		}

		Button closeInfo = (Button) dialog.findViewById(R.id.close_info_button);
		closeInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( decodedByte != null){
					decodedByte.recycle();
				}
				dialog.dismiss();
			}
		});
	
		dialog.show();
		
	}
	
	
	
	private void openMyTrainersContentActivity(Integer idTrainer){
		Intent trainerContentIntent = new Intent(activity , TrainerContentActivity.class);
        activity.startActivity(trainerContentIntent);
	}

}
