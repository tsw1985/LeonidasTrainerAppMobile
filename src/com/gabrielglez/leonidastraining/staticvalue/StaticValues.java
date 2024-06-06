package com.gabrielglez.leonidastraining.staticvalue;

public class StaticValues {
	
	//USER DATA
	public static String USER_DNI = "";
	public static String USER_PHONE = "";
	
	//TRAINER DATA
	public static Integer ID_SELECTED_TRAINER = 0;
	public static Integer ID_SELECTED_DAY = 0;
	public static Integer ID_SELECTED_SPORT_ACTIVITY = 0;
	public static Integer ID_SELECTED_BODY_PLACE = 0;
	public static String TEXT_SELECTED_TRAINER = "";
	public static Integer ID_LAST_TRAINER_COUNTER = 0;
	
	
	
	//URL GET PARAMETERS
	public static final String GET_DNI_PARAM = "dniUser";
	public static final String GET_PHONE_PARAM = "phoneUser";
	public static final String GET_ID_TRAINER_PARAM = "idTrainer";
	
	
	//URLS
	//public static final String URL_GET_ALL_TRAINER_IN_USER = "http://192.168.1.4/leonidastraining/index.php/getusertrainer";
	//public static final String URL_GET_TRAINER_BY_ID = "http://192.168.1.4/leonidastraining/index.php/gettrainer";
	
	public static final String URL_GET_ALL_TRAINER_IN_USER = "http://www.webservice.leonidastraining.es/index.php/getusertrainer";
	public static final String URL_GET_TRAINER_BY_ID = "http://www.webservice.leonidastraining.es/index.php/gettrainer";
	
	//REDIS KEYS
	public static final String REDIS_LABEL_CUSTOMER_GYM_TRAINER = "[CustomerGymTrainer]";
    public static final String REDIS_LABEL_CUSTOMER_GYM_SPORT_ACTIVITY = "[CustomerGymSportActivity]";
	public static final String REDIS_LABEL_CUSTOMER_GYM_ID = "[CustomerGymID]";
	public static final String REDIS_LABEL_TRAINER_ID = "[TrainerID]";
	
	//MESSAGES SYNCRONIZATION
	public static final String MSG_SYNCRONIZATION_CONFIRMATION = "¿Desea sincronizar los entrenamientos?";
	
	
	
	//MESSAGES CONFIGURATION ACTIVITY
	public static final String MSG_USER_NOT_CONFIGURATED = "El usuario no está configurado";
	public static final String MSG_TITLE_DIALOG_INFO = "Información";
	public static final String MSG_BUTTON_TITLE_ACCEPT = "Aceptar";
	public static final String MSG_USER_DNI_IS_EMPTY = "El nombre de usuario no puede estar vacio";
	public static final String MSG_USER_PHONE_IS_EMPTY = "El telefono no puede estar vacio";
	public static final String MSG_USER_PHONE_DNI_IS_EMPTY = "El teléfono o dni están vacios";
	public static final String MSG_USER_UPDATED_SUCCESS = "Sus datos han sido guardados correctamente";
	
	
	//PROGRESS DIALOG
	public static final String MSG_SYNCRONITATION = "Conectando ... por favor espere...";
	public static final String MSG_DOWNLOADING_TRAINERS = "Descargando entrenamientos ...";
	public static final String MSG_DOWNLOADING_TRAINERS_DONE = "Entrenamientos descargados con éxito";
	public static final String MSG_USER_HAVE_NOT_TRAINERS = "Aún no tiene entrenamientos asignados, consulte a su entrenador";
	
	
	//TRAINER CONTENT
	public static final String MSG_START_TRAINER = "¿Desea iniciar el entrenamiento?";
	public static final String MSG_STOP_TRAINER = "¿Desea detener el entrenamiento realmente?";
	
	
}
