package com.gabrielglez.leonidastraining;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.gabrielglez.leonidastraining.database.DataBaseHelperManager;
import com.gabrielglez.leonidastraining.model.BodyWeight;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

//http://www.android-graphview.org/documentation/how-to-create-a-simple-graph
public class MyEvolutionKgActivity extends ActionBarActivity {
	
	
	private Button fromDateButton;
	private Button toDateButton;
	private Button searchKgEvoButton;
	private TextView fromDateTextView;
	private TextView toDateTextView;
	
	private Date fromDate;
	private Date toDate;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_my_evolution_kg);
		initComponent();
		initListener();
	}



	private void initComponent() {
		fromDateButton = (Button)findViewById(R.id.from_date_button);
		toDateButton = (Button)findViewById(R.id.to_date_button);
		searchKgEvoButton = (Button)findViewById(R.id.search_kg_evo_button);
		
		fromDateTextView = (TextView)findViewById(R.id.from_date_textview);
		toDateTextView = (TextView)findViewById(R.id.to_date_text_view);
		
		
		
		//initChart();
	}
	
	
	private void initListener() {
		
		fromDateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setDateInTextView(fromDateTextView);
			}
			
		});
		
		
		toDateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setDateInTextView(toDateTextView);
			}
			
		});
		
		searchKgEvoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadWeightChart();
			}

			
		});
		
	}
	
	private void loadWeightChart() {

		if ( !fromDateTextView.getText().equals("") &&
			! toDateTextView.getText().equals("") ){
			
			try{
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				
				Date fromDate = df.parse(fromDateTextView.getText().toString() + " 00:00:00");
				Date toDate   = df.parse(toDateTextView.getText().toString() + " 23:59:59");
				
				List<BodyWeight> listBodyWeight = DataBaseHelperManager
												  .getBodyWeigthDAO()
												  .getBodyWeightListBetweenDates(fromDate, toDate);
				
				if ( listBodyWeight != null){
					
					GraphView graph = (GraphView) findViewById(R.id.graph);
					
					ArrayList<DataPoint> dpList = new ArrayList<DataPoint>(); 
					
					DataPoint[] dp = new DataPoint[listBodyWeight.size()] ;
					
					int index = 0;
					

					DateTimeFormatter ft = DateTimeFormat.forPattern("dd-MM-yyyy");
					DateTime one = ft.parseDateTime(fromDateTextView.getText().toString());
					DateTime two = ft.parseDateTime(toDateTextView.getText().toString());
					
					int days = Days.daysBetween(one.toLocalDateTime() , two.toLocalDateTime()).getDays();

					//Calculamos las fechas entre esas fechas para coger ese dia
					final List<DateTime> listLocalDates = new ArrayList<DateTime>();
					for ( int i = 0; i < days ; i++){
						DateTime d = one.withFieldAdded(DurationFieldType.days(), i);
						listLocalDates.add(d);
					}
					
					//dibujamos el eje X para dibujar el grafico
					for ( BodyWeight bodyItem : listBodyWeight){
						//cal.setTime(bodyItem.getDateWeight());
						
						dp[index] = new DataPoint( index,  
												   bodyItem.getBodyWeigthKg() );
						index++;
					}
					
					/*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
					          new DataPoint(0, 1),
					          new DataPoint(1, 5),
					          new DataPoint(2, 9),
					});*/
					
					LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>( dp );

					
					graph.addSeries(series);

					
					graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
					    @Override
					    public String formatLabel(double value, boolean isValueX) {
					    if (isValueX) {
					        // show normal x values
					    	Log.v("Salida","Valor en X " + value);
					    	
					    	//obtenemos el dia del array
					    	if ( value < listLocalDates.size()){
					    		listLocalDates.get((int)value).getDayOfMonth();
					    	}
					    	
					    	//return super.formatLabel(listLocalDates.get((int)value).getDayOfMonth(), isValueX  )+ " D";
					        return super.formatLabel(value, isValueX  )+ " D";
					    } else {
					        // show currency for y values
					    	Log.v("Salida","Valor en Y " + value);
					        return super.formatLabel(value, isValueX) + " Kg";
					    }
					    }
					});
					
					
					graph.invalidate();
					
				}
				
				
				
			}catch (Exception ex){
				Log.e("Salida", "Error parsing bodyweight dates " + ex.toString() );
			}
			
			
			
		}
		
		
		
	}
	
	
	private void setDateInTextView(final TextView textView){
		
		final Dialog dialog = new Dialog(MyEvolutionKgActivity.this);
		dialog.setContentView(R.layout.dialog_set_date);
		
		
			 
		Button dialogButton = (Button) dialog.findViewById(R.id.close_date_button);
		dialogButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePicker setDatePicker = (DatePicker)dialog.findViewById(R.id.set_datepicker);
				textView.setText(getTextInDatePicker(setDatePicker));
				dialog.dismiss();
			}
			
		});

		dialog.show();
		
	}
	
	
	private String getTextInDatePicker(DatePicker datePicker){
		
		String day = String.valueOf(datePicker.getDayOfMonth() );
		String month = String.valueOf(datePicker.getMonth() + 1);
		String year = String.valueOf(datePicker.getYear());
		
		return day + "-" + month + "-" + year;
		
		
	}
	
	
	private void initChart(){
		
		GraphView graph = (GraphView) findViewById(R.id.graph);
		LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
		          new DataPoint(0, 1),
		          new DataPoint(1, 5),
		          new DataPoint(2, 9),
		          

		});
		
		
		LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
		          new DataPoint(0, 11),
		          new DataPoint(11, 15),
		          new DataPoint(12, 19),
		          new DataPoint(13, 20),
		          new DataPoint(14, 21),
		          new DataPoint(15, 22),
		          new DataPoint(16, 23),
		          new DataPoint(17, 24),
		          new DataPoint(18, 25),
		          new DataPoint(19, 26),
		          new DataPoint(20, 27),
		          new DataPoint(21, 28),
		          new DataPoint(525, 29)
		          

		});

		
		
		graph.addSeries(series);
		graph.addSeries(series2);

		
		graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
		    @Override
		    public String formatLabel(double value, boolean isValueX) {
		    if (isValueX) {
		        // show normal x values
		    	Log.v("Salida","Valor en X " + value);
		        return super.formatLabel(value, isValueX  )+ " F";
		    } else {
		        // show currency for y values
		    	Log.v("Salida","Valor en Y " + value);
		        return super.formatLabel(value, isValueX) + " €";
		    }
		    }
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_evolution_kg, menu);
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
