package com.gabrielglez.leonidastraining.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.gabrielglez.leonidastraining.MainActivity;
import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.dialogservice.DialogService;
import com.gabrielglez.leonidastraining.model.BodyPlace;
import com.gabrielglez.leonidastraining.model.ConfigurationExerciseTimes;
import com.gabrielglez.leonidastraining.model.ConfigurationTrainer;
import com.gabrielglez.leonidastraining.model.Day;
import com.gabrielglez.leonidastraining.model.Exercise;
import com.gabrielglez.leonidastraining.model.SportActivity;
import com.gabrielglez.leonidastraining.model.Trainer;
import com.gabrielglez.leonidastraining.model.TrainerDownloaded;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

												//  1      2      3
//1 - Parametro de entrada en DoInBackdground
//2 - Parametro entrada en onProgressUpdate
//3 - Parametro entrada en onPostExecute
public class TaskGetUserTrainer extends AsyncTask<String, String, String>{
	
	public  String jsonDataToReturn ;
	private EditText editText ;
	
	private ProgressDialog progressDialog;
	private Context context;
	
	public TaskGetUserTrainer(EditText text ){
		this.editText = text;
	}
	
	public TaskGetUserTrainer(EditText text , Context context){
		this.progressDialog = new ProgressDialog(context);
		this.context = context;
		this.editText = text;
	}
	
	
	private void sleepPhone(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.progressDialog.setMessage(StaticValues.MSG_DOWNLOADING_TRAINERS);
		this.progressDialog.show();
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		this.progressDialog.setMessage(StaticValues.MSG_DOWNLOADING_TRAINERS_DONE);

		if ( this.progressDialog.isShowing()){
			sleepPhone();
			this.progressDialog.dismiss();
		}
		
		if ( result.equals("OK")){
			//editText.setText(result);
			
			DialogService.informationCustomDialog(context,  
					                              StaticValues.MSG_TITLE_DIALOG_INFO, 
                                                  StaticValues.MSG_DOWNLOADING_TRAINERS_DONE, 
                                                  StaticValues.MSG_BUTTON_TITLE_ACCEPT);
			
		}else if( result.equals("ERROR")){
			DialogService.informationCustomDialog( context,
												   StaticValues.MSG_TITLE_DIALOG_INFO, 
												   StaticValues.MSG_USER_HAVE_NOT_TRAINERS, 
												   StaticValues.MSG_BUTTON_TITLE_ACCEPT);
		}
		
		
		
		
		
	}
	

	
	@Override
	protected void onProgressUpdate(String... values) {
		
		super.onProgressUpdate(values);
		this.progressDialog.setMessage(values[0]);
		
	}
	
	@Override
	protected String  doInBackground(String... params) {
		
		String result = "";
		
		String URL_GET_PETITION = StaticValues.URL_GET_ALL_TRAINER_IN_USER + "?" + 
								  StaticValues.GET_DNI_PARAM + "=" + 
				                  StaticValues.USER_DNI + "&" + 
								  StaticValues.GET_PHONE_PARAM + "=" + 
				                  StaticValues.USER_PHONE;
		
		
		
		//Obtenemos los entrenamientos que viene asi : 1-3-7-4
		String userTrainers = httpData(URL_GET_PETITION);

		//Una vez tengamos los entrenamientos los partimos y recorremos
		if (!userTrainers.equals("")){
			
			List<String> trainerList = new ArrayList<String>();
			int totalTrainers = 0;
			
			
			String[] idTrainers = userTrainers.split("-");
			
			if ( idTrainers.length > 0){
				
				for (String idTrainer : idTrainers){
					
					String URL_GET_TRAINER = StaticValues.URL_GET_TRAINER_BY_ID + "?" + 
											 StaticValues.GET_DNI_PARAM + "=" + 
							                 StaticValues.USER_DNI + "&" + 
					
							                 StaticValues.GET_PHONE_PARAM + "=" + 
							                 StaticValues.USER_PHONE + "&" +
							                 
							                 StaticValues.GET_ID_TRAINER_PARAM + "=" + idTrainer;

					//Obtenemos los entrenamientos que tiene asignado el usuario
					String trainerContent = httpData(URL_GET_TRAINER);
					trainerList.add(trainerContent);
					
					totalTrainers++;
					Log.v("Trainer", trainerContent);
					
				}//enf for each
				

				//Una vez descargados los entrenamientos procedemos a comprobar si coincide 
				//el contador con la longitud de entrenamientos asignados
				if ( idTrainers.length == totalTrainers){
					
					//Eliminamos todos los trainers descargados
					if ( DataBaseHelperManager.getTrainerDownloadedDAO().deleteAll()){
						
						try {
							
							//Faltaria trainer counter
							
							//Borramos toda la base de datos para guardar todo lo nuevo ahora
							DataBaseHelperManager.getConfigurationExerciseTimesDAO().deleteAll(); //--
							DataBaseHelperManager.getBodyPlaceDAO().deleteAll(); // --
							DataBaseHelperManager.getDayDAO().deleteAll(); //--
							DataBaseHelperManager.getExerciseDAO().deleteAll();//--
							DataBaseHelperManager.getTrainerDAO().deleteAll();//--
							DataBaseHelperManager.getConfigurationTrainerDAO().deleteAll();//--
							DataBaseHelperManager.getSportActivityDAO().deleteAll();//--
							
							
							
							
							
							//recorremos el arrayList que tiene todos los elementos
							for (String trainerItem : trainerList){
								
								TrainerDownloaded trainerDownloaded = new TrainerDownloaded();
								trainerDownloaded.setTrainerJsonData(trainerItem);
								DataBaseHelperManager.getTrainerDownloadedDAO().create(trainerDownloaded);
							}
							
							//Una vez guardados en la SD , vamos a por ellos nuevamente
							List<TrainerDownloaded> listTrainersDownloaded = DataBaseHelperManager.
																			 getTrainerDownloadedDAO().
																			 getAllTrainersDownloaded();
							
							//Por Aqui es donde creamos el entrenamiento y descomponemos el JSON
							for ( TrainerDownloaded trainerDownloadedItem : listTrainersDownloaded){
								
								//Variables de configuration
								String trainerIdServer = "";
								String trainerDescription = "";
								String trainerTotalDays = "";

								String dayIdServer = "";
								String dayName = "";
								
								
								String bodyPlaceIdServer = "";
								String bodyPlaceDescription = "";
								
								
								String exerciseIdServer = "";
								String exerciseDescription = "";
								String exerciseImage = "";
								String exerciseVideo = "";
								String exerciseName = "";
								
								

								Trainer trainerToSave; 
								
								//Obtenemos el JSON en bruto
								JSONArray jsonArray = new JSONArray(trainerDownloadedItem.getTrainerJsonData());
								
								//Si hay datos entonces entramos
								if ( jsonArray.length() > 0) {
								
									String trainer_tag = jsonArray.getString(0);
									JSONObject trainer_content = new JSONObject(trainer_tag);
									
									//Aqui ya tenemos la clave trainer
									String trainerJSON_KEY = trainer_content.getString("trainer");
									
									//Este objeto es el que contiene todo
									JSONObject trainerJSON_KEY_VALUES = new JSONObject(trainerJSON_KEY);
	
									//Accedemos a la descripcion del entrenamiento -------------------------------------------
									trainerIdServer = trainerJSON_KEY_VALUES.getString("id");
									trainerDescription = trainerJSON_KEY_VALUES.getString("description");
									trainerTotalDays = trainerJSON_KEY_VALUES.getString("totaldays");

									
									String sportActivityIdServer = trainerJSON_KEY_VALUES.getString("idSportActivity");
									String sportActivityDescription = trainerJSON_KEY_VALUES.getString("sportActivity");
									
									SportActivity sportActivity ;
									sportActivity = DataBaseHelperManager.getSportActivityDAO().getSportActivityByIdServer(Integer.parseInt(sportActivityIdServer));
									if (sportActivity == null){
										sportActivity = new SportActivity();
										sportActivity.setSportActivityIdServer(Integer.parseInt(sportActivityIdServer));
										sportActivity.setSportActivityDescription(sportActivityDescription);
										DataBaseHelperManager.getSportActivityDAO().create(sportActivity);
										Log.v("SQL", "Creando objeto sportActivity NUEVO");
									}
									else{
										sportActivity.setSportActivityIdServer(Integer.parseInt(sportActivityIdServer));
										sportActivity.setSportActivityDescription(sportActivityDescription);
										DataBaseHelperManager.getSportActivityDAO().update(sportActivity);
										Log.v("SQL", "Actualizando sportActivity");
									}
									
									
									publishProgress(trainerDescription);
									
									
									trainerToSave = DataBaseHelperManager.getTrainerDAO().getTrainerByIdServer(Integer.parseInt(trainerIdServer));
									if ( trainerToSave == null){
										//y seteamos el objeto entrenamiento
										//Guardamos el entrenamiento en BD si no existe, y si existe lo actualizamos
										trainerToSave = new Trainer();
										trainerToSave.setTrainerIDServer(Integer.parseInt(trainerIdServer));
										trainerToSave.setTrainerDescription(trainerDescription);
										trainerToSave.setTrainerTotalDays(Integer.parseInt(trainerTotalDays));
										trainerToSave.setTrainerIdSportActivity(Integer.parseInt(sportActivityIdServer));
										DataBaseHelperManager.getTrainerDAO().create(trainerToSave);
										Log.v("SQL", "Creando objeto entrenamiento NUEVO");
									}
									else{
										trainerToSave.setTrainerIDServer(Integer.parseInt(trainerIdServer));
										trainerToSave.setTrainerDescription(trainerDescription);
										trainerToSave.setTrainerTotalDays(Integer.parseInt(trainerTotalDays));
										trainerToSave.setTrainerIdSportActivity(Integer.parseInt(sportActivityIdServer));
										DataBaseHelperManager.getTrainerDAO().update(trainerToSave);
										Log.v("SQL", "Actualizando objeto entrenamiento ");
									}
									
									
									//------------------------ Ahora vamos a por el contenido -----------------------------
									JSONArray trainerContentArray = new JSONArray(trainerJSON_KEY_VALUES.getString("content"));
									//Ya tenemos el contenido , pero lo primero es ir a por el DIA
									
									//Si hay contenido entramos
									if ( trainerContentArray.length() > 0){
										
										for ( int indexTrainerContentArray = 0 ; indexTrainerContentArray < trainerContentArray.length() ; indexTrainerContentArray++){
											
											//Este objeto contiene los bodyPlaces
											JSONObject trainer_content_tag_content = new JSONObject(trainerContentArray.getString(indexTrainerContentArray));
											
											Day day; //------------------------------------------------------------------------------------------
											dayIdServer = trainer_content_tag_content.getString("dayId");
											dayName = trainer_content_tag_content.getString("dayName");
											
											publishProgress(dayName);
	
											day = DataBaseHelperManager.getDayDAO().getDayByIdServer(Integer.parseInt(dayIdServer));
											if ( day == null){
												day = new Day();
												day.setDayIdServer(Integer.parseInt(dayIdServer));
												day.setDayName(dayName);
												DataBaseHelperManager.getDayDAO().create(day);
												Log.v("SQL", "Creando objeto DIA NUEVO " + dayName);
											}else{
												day.setDayIdServer(Integer.parseInt(dayIdServer));
												day.setDayName(dayName);
												DataBaseHelperManager.getDayDAO().update(day);
												Log.v("SQL", "Actualizando objeto DIA " + dayName);
											}
											
											//Ahora accedemos a los bodyPlaces
											JSONArray bodyPlacesArray = new JSONArray(trainer_content_tag_content.getString("bodyPlaces"));
											if ( bodyPlacesArray.length() > 0){
												
													for ( int indexBodyPlaceArray = 0 ; indexBodyPlaceArray < bodyPlacesArray.length() ; indexBodyPlaceArray++){
													
														BodyPlace bodyPlace = null;
														
														JSONObject bodyPlaces_tag_content = new JSONObject(bodyPlacesArray.getString(indexBodyPlaceArray));
														
														//---------------------------------------------------------------------------------------------
														bodyPlaceIdServer = bodyPlaces_tag_content.getString("id");
														bodyPlaceDescription = bodyPlaces_tag_content.getString("name");
														
														bodyPlace = DataBaseHelperManager.getBodyPlaceDAO().getBodyPlaceByIdServer(Integer.parseInt(bodyPlaceIdServer));
														if (bodyPlace == null){
															bodyPlace = new BodyPlace();
															bodyPlace.setBodyPlaceIdServer(Integer.parseInt(bodyPlaceIdServer));
															bodyPlace.setBodyPlaceDescription(bodyPlaceDescription);
															DataBaseHelperManager.getBodyPlaceDAO().create(bodyPlace);
															Log.v("SQL", "Creando objeto bodyplace " + bodyPlaceDescription);
														}
														else{
															bodyPlace.setBodyPlaceIdServer(Integer.parseInt(bodyPlaceIdServer));
															bodyPlace.setBodyPlaceDescription(bodyPlaceDescription);
															DataBaseHelperManager.getBodyPlaceDAO().update(bodyPlace);
															Log.v("SQL", "Actualizando objeto bodyplace " + bodyPlaceDescription);
														}
														
														publishProgress(bodyPlaceDescription);
														
														
														//Ahora vamos a por los ejercicios
														JSONArray exercises_array = new JSONArray(bodyPlaces_tag_content.getString("exercises"));
														if (exercises_array.length() > 0 ){
															
															for ( int indexExercisesArray = 0 ; indexExercisesArray < exercises_array.length() ; indexExercisesArray++){
															
																Exercise exercise = null;
																
																JSONObject exercices_tag = new JSONObject(exercises_array.getString(indexExercisesArray));
																
																//----------------------------------------------------------------------------------------------------
																exerciseIdServer = exercices_tag.getString("id");
																exerciseDescription = exercices_tag.getString("label");
																exerciseImage = exercices_tag.getString("image");
																exerciseVideo = exercices_tag.getString("video");
																exerciseName = exercices_tag.getString("exerciseName");
																
																exercise = DataBaseHelperManager.getExerciseDAO().getExerciseByIdServer(Integer.parseInt(exerciseIdServer));
																if ( exercise == null){
																	exercise = new Exercise();
																	exercise.setExerciseIdServer(Integer.parseInt(exerciseIdServer));
																	exercise.setExerciseName(exerciseName);
																	exercise.setExerciseVideo(exerciseVideo);
																	exercise.setExerciseImage(exerciseImage);
																	exercise.setExerciseDescription(exerciseDescription);
																	DataBaseHelperManager.getExerciseDAO().create(exercise);
																	Log.v("SQL", "Creando objeto ejercicio " + exerciseName);
																}
																else{
																	exercise.setExerciseIdServer(Integer.parseInt(exerciseIdServer));
																	exercise.setExerciseName(exerciseName);
																	exercise.setExerciseVideo(exerciseVideo);
																	//exercise.setExerciseImage(exerciseImage);
																	exercise.setExerciseDescription(exerciseDescription);
																	DataBaseHelperManager.getExerciseDAO().update(exercise);
																	Log.v("SQL", "Actualizando objeto ejercicio " + exerciseName);
																}
																
																publishProgress(exerciseName);
																
																
																//Ahora vamos a guardar las repeticiones
																
																JSONArray repetitions_array = new JSONArray(exercices_tag.getString("repetitions"));
																if ( repetitions_array.length() > 0 ){
																		
																		for (int indexRepetitionsArray = 0 ; indexRepetitionsArray < repetitions_array.length() ; indexRepetitionsArray++  ){
																			
																			JSONObject repetitions_tag = new JSONObject(repetitions_array.getString(indexRepetitionsArray));
																			
																			String repetitionIdServer = repetitions_tag.getString("id");
																			String repetitionTimes = repetitions_tag.getString("times");
																			
																			ConfigurationExerciseTimes configurationExerciseTimes = new ConfigurationExerciseTimes();
																			configurationExerciseTimes.setConfTrainerIdServer(Integer.parseInt(repetitionIdServer));
																			configurationExerciseTimes.setTrainer(Integer.parseInt(trainerIdServer));
																			configurationExerciseTimes.setExercise(Integer.parseInt(exerciseIdServer));
																			configurationExerciseTimes.setDay(Integer.parseInt(dayIdServer));
																			configurationExerciseTimes.setBodyPlace(Integer.parseInt(bodyPlaceIdServer));
																			configurationExerciseTimes.setTimes(Integer.parseInt(repetitionTimes));
																			DataBaseHelperManager.getConfigurationExerciseTimesDAO().create(configurationExerciseTimes);
																			Log.v("SQL", "TIMES: IDTRAINER " + trainerIdServer + "ID EXER " + exerciseIdServer + " IDDAY " + dayIdServer + " IDBODY " + bodyPlaceIdServer + " times " + repetitionTimes);
																			
																			publishProgress(repetitionTimes);
																			
																		} //end for indexRepetitionsArray
																	
																	
																}//end if repetitions_array.length() > 0
																
																
																//Por cada entrenamiento que llega guardamos la configuracion
																ConfigurationTrainer configurationTrainer = new ConfigurationTrainer();
																configurationTrainer.setTrainer(Integer.parseInt(trainerIdServer));
																configurationTrainer.setExercise(Integer.parseInt(exerciseIdServer));
																configurationTrainer.setDay(Integer.parseInt(dayIdServer));
																configurationTrainer.setBodyPlace(Integer.parseInt(bodyPlaceIdServer));

																DataBaseHelperManager.getConfigurationTrainerDAO().create(configurationTrainer);		
																
																
																
																
																
																
														  } // end for int indexExercisesArray
															
														} //  end exercises_array.length() > 0
														
														//Por cada entrenamiento que llega guardamos la configuracion
														/*ConfigurationTrainer configurationTrainer = new ConfigurationTrainer();
														configurationTrainer.setTrainer(Integer.parseInt(trainerIdServer));
														configurationTrainer.setExercise(Integer.parseInt(exerciseIdServer));
														configurationTrainer.setDay(Integer.parseInt(dayIdServer));
														configurationTrainer.setBodyPlace(Integer.parseInt(bodyPlaceIdServer));

														DataBaseHelperManager.getConfigurationTrainerDAO().create(configurationTrainer);	*/	
												
												} //end for indexBodyPlaceArray
												
											} //end bodyPlacesArray.length() > 0
											
										} //end for indexTrainerContentArray
										
									} //end if trainerContentArray.length() > 0
								
								} //end jsonArray.length() > 0
								
							} //end for listTrainerDownloaded
							
							
							result = "OK";
							
						} 
						catch (Exception ex){
							Log.v("Salida", "Error guardando entrenamientos en SD " + ex.toString());
							result = "ERROR";
						}
					}
					
					
					//Comentar esto , esto es para depurar
					/*List<Exercise> listExercices = DataBaseHelperManager.getExerciseDAO().getAllExercises();
					for(Exercise exerciseItem : listExercices){
						Log.v("Salida", "DESCRIPCION DE EJERCICIO GUARDADO " + exerciseItem.getExerciseName());
					}*/
					
					
					List<ConfigurationTrainer> listConfigurationTrainer = DataBaseHelperManager.getConfigurationTrainerDAO().getAllConfigurationTrainer();
					for(ConfigurationTrainer confItem : listConfigurationTrainer){
						Log.v("CONF", "CONF TRAINER ID  " + confItem.getTrainer());
						Log.v("CONF", "CONF EXERCIS ID  " + confItem.getExercise());
						Log.v("CONF", "CONF DAY     ID  " + confItem.getDay());
						Log.v("CONF", "CONF BODYPLA ID  " + confItem.getBodyPlace());
					}

					
				}  //end if idTrainers.length == totalTrainers
				
			}
			
		}
		
		
		return result;
		
	}
	
	private void emptyVariables(){
		
	}
	
	
	private String httpData(String url){
		
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try{
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			
			if ( statusCode==200 ){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				
				String line = "";
				while((line = reader.readLine()) != null){
					stringBuilder.append(line);
				}

			}
			else{
				stringBuilder.append("");
			}
		
		}catch(Exception ex){
			Log.e("HTTP-GET" , "TaskGetUserTrainer - Error getting usertrainers" + ex.toString() );
		}
		

		return stringBuilder.toString();
	}
	

}
