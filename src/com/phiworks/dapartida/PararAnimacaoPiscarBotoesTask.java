package com.phiworks.dapartida;



import android.os.AsyncTask;
import android.view.View;

public class PararAnimacaoPiscarBotoesTask extends AsyncTask<View, Integer, Void>{

	@Override
	protected Void doInBackground(View... params) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < params.length; i++)
		{
			View umaViewParar = params[i];
			umaViewParar.clearAnimation();
		}
		
		return null;
	}

}
