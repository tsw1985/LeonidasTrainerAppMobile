package com.gabrielglez.leonidastraining.dialogservice;

import com.gabrielglez.leonidastraining.MainActivity;
import com.gabrielglez.leonidastraining.R;
import com.gabrielglez.leonidastraining.staticvalue.StaticValues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogService {
	
	
	public static void informationDialogService(Context activity ,String title , String message , String titleButton){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setCancelable(false);
		alert.setTitle(title);
		alert.setCancelable(false);
		alert.setMessage(message);
		alert.setPositiveButton(titleButton, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		alert.show();

	}
	
	public static void informationCustomDialog(Context context ,String title , String message , String titleButton){
		
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_information);
		dialog.setTitle(title);

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(message);
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});

		dialog.show();
		
	}
}