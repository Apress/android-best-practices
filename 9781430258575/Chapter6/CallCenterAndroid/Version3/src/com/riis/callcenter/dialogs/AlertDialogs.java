package com.riis.callcenter.dialogs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

public class AlertDialogs {
	
	public static ProgressDialog createAndShowLoadingDialog(Context context, OnCancelListener cancelListener) {
		ProgressDialog loadingDialog = ProgressDialog.show(context, "", "Loading...");
		loadingDialog.setCancelable(true);
		loadingDialog.setOnCancelListener(cancelListener);
		
		return loadingDialog;
	}
	
	public static void showEmptyFieldsAlertDialog(Context context) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setMessage("Please fill in every field.");
		dialogBuilder.setPositiveButton("OK", null);
		dialogBuilder.create().show();
	}
	
	public static void showLoginFailedAlertDialog(Context context, String failureMessage) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setMessage("Login failed: " + failureMessage);
		dialogBuilder.setPositiveButton("OK", null);
		dialogBuilder.create().show();
	}
	
	public static void showLoadingFailedAlertDialog(Context context, String failureMessage) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setMessage("Unable to load: ");
		dialogBuilder.setPositiveButton("OK", null);
		dialogBuilder.create().show();
	}
}
