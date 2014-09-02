package com.phiworks.dapartida;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.phiworks.sumosenseinew.TelaInicialMultiplayer;

public class TerminaPartidaTask extends AsyncTask<String, Integer, Void>{
	
	private ActivityPartidaMultiplayer activityDoMultiplayer;
	private ProgressDialog popupDeProgresso;

	public TerminaPartidaTask(ProgressDialog loadingDaTela, ActivityPartidaMultiplayer telaDoMultiplayer)
	{
		this.activityDoMultiplayer = telaDoMultiplayer;
		this.popupDeProgresso = loadingDaTela;
	}
	
	@Override
	protected Void doInBackground(String... params) 
	{
		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException exc)
		{
			
		}
		return null;
	}
	
	protected void onPostExecute(Void v) {
		this.popupDeProgresso.dismiss();
		this.activityDoMultiplayer.terminarJogoMultiplayer();
		
	}
	
	

}
