package com.projectx.main.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;


public class Messagebox {
	
	private static ProgressDialog showProgresBar(Context context, String message){
		ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        return mProgressDialog;
	}
	public static AlertDialog DialogBox(Activity context, String title, String message){
		Builder dlg = new Builder(context);
		dlg.setTitle(title);
		dlg.setMessage(message);
        return dlg.create();
	}
	public interface DoubleRunnable {
		public void run();
		public void runUI();
	}
	public static void showProsesBar(Context context, DoubleRunnable run){
		showProsesBar(context, new Runnable() {
			private DoubleRunnable run;
			public Runnable get(DoubleRunnable run){
				this.run=run;
				return this;
			}
			public void run() {
				run.run();
			}
		}.get(run), new Runnable() {
			private DoubleRunnable run;
			public Runnable get(DoubleRunnable run){
				this.run=run;
				return this;
			}
			public void run() {
				run.runUI();
			}
		}.get(run));
	}
	public static void newTask(final Runnable run){
		newTask(new DoubleRunnable() { public void runUI() { } public void run() { run.run(); } });
	}
	public static void newTask(DoubleRunnable run){
		new AsyncTask<DoubleRunnable, Void, DoubleRunnable>(){
		      protected DoubleRunnable doInBackground(DoubleRunnable... params) {
		    	  params[0].run();
		    	  return params[0];
		      }      
		      protected void onPostExecute(DoubleRunnable result) {    
		    	  result.runUI();
		      }
		      protected void onPreExecute() {}
		      protected void onProgressUpdate(Void... values) {}
		}.execute(run);
	}	
	public static void showProsesBar(Context context, Runnable run, Runnable ui){
		new AsyncTask<Runnable, Void, Runnable>(){
		      protected Runnable doInBackground(Runnable... params) {
		    	  params[0].run();
		    	  return params[1];
		      }      
		      protected void onPostExecute(Runnable result) {
		    	  result.run();
		      }
		      protected void onPreExecute() {}
		      protected void onProgressUpdate(Void... values) {}
		}.execute(run, new Runnable() {
			private Runnable ui;
			private ProgressDialog prb;
			public Runnable get(Runnable ui, ProgressDialog prb){
				this.ui=ui;
				this.prb=prb;
				return this;
			}
			public void run() {
				prb.dismiss();
				this.ui.run();
				
			}
		}.get(ui, showProgresBar(context, "Please Wait . . . ")));
	}
	public static void showDialog(Context context, String title, String[] data, DialogInterface.OnClickListener listener){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);
		Builder dlg = new Builder(context);
		if (title!=null) {
			if (title.trim().length()>=1) {
				dlg.setTitle(title);
			}			
		}
		dlg.setAdapter(adapter, listener);
		dlg.create().show();
	}
	public static void showDialog(Context context, String title, String[] data, DialogInterface.OnClickListener listener, int layout){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, layout, data);
		Builder dlg = new Builder(context);
		if (title!=null) {
			if (title.trim().length()>=1) {
				dlg.setTitle(title);
			}			
		}
		dlg.setAdapter(adapter, listener);
		dlg.create().show();
	}
	public static void showDialogSingleChoiceItems(Context context, String title, String[] data, int i, DialogInterface.OnClickListener listener, DialogInterface.OnKeyListener onKeyListener){
		Builder dlg = new Builder(context);
		dlg.setSingleChoiceItems(data, i, listener);
		if (title!=null) {
			if (title.trim().length()>=1) {
				dlg.setTitle(title);
			}			
		}
		dlg.create().show();
	}
}
