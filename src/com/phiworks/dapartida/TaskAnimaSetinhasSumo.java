package com.phiworks.dapartida;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class TaskAnimaSetinhasSumo  extends AsyncTask<String, String, Void>
{
	private ImageView setinhaPraAnimar;
	private Activity telaQueCriouATask;
	private boolean setaFicaAnimadaPraDireita;
	
	public TaskAnimaSetinhasSumo(ImageView setinhaAnimar, Activity telaCriouTask, boolean setaComecaAnimadaPraDireita)
	{
		this.setinhaPraAnimar = setinhaAnimar;
		this.telaQueCriouATask = telaCriouTask;
		this.setaFicaAnimadaPraDireita = setaComecaAnimadaPraDireita;
	}

	@Override
	protected Void doInBackground(String... params) 
	{
		while(this.isCancelled() == false)
		{
			try {
				Thread.sleep(200);
				publishProgress("");
				this.telaQueCriouATask.runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
		            	   if(setaFicaAnimadaPraDireita == true)
		            	   {
		            		   //vamos agora moverASetaParaEsquerda
		            		   setaFicaAnimadaPraDireita = false;
		            		// CODE FOR ADD MARGINS
		            		   RelativeLayout.LayoutParams relativeParams =  (LayoutParams) setinhaPraAnimar.getLayoutParams();
		            		   relativeParams.setMargins(0, 0, 10, 0);
		            		   setinhaPraAnimar.setLayoutParams(relativeParams);
		            		   setinhaPraAnimar.requestLayout();
		            	   }
		            	   else
		            	   {
		            		   //vamos agora moverASetaParaDireita
		            		   setaFicaAnimadaPraDireita = true;
		            		// CODE FOR ADD MARGINS
		            		   RelativeLayout.LayoutParams relativeParams = (LayoutParams) setinhaPraAnimar.getLayoutParams();
		            		   relativeParams.setMargins(10, 0, 0, 0);
		            		   setinhaPraAnimar.setLayoutParams(relativeParams);
		            		   setinhaPraAnimar.requestLayout();
		            	   }
		               }
		            });
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return null;
	}
	
	/*protected void onProgressUpdate(String... progress) 
	{
		if(setaFicaAnimadaPraDireita == true)
 	   {
 		   //vamos agora moverASetaParaEsquerda
 		   setaFicaAnimadaPraDireita = false;
 		// CODE FOR ADD MARGINS
 		   LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
 		           new LinearLayout.LayoutParams(
 		                   LinearLayout.LayoutParams.MATCH_PARENT,
 		                   LinearLayout.LayoutParams.WRAP_CONTENT));
 		   relativeParams.setMargins(0, 0, 10, 0);
 		   setinhaPraAnimar.setLayoutParams(relativeParams);
 		   setinhaPraAnimar.requestLayout();
 	   }
 	   else
 	   {
 		   //vamos agora moverASetaParaDireita
 		   setaFicaAnimadaPraDireita = true;
 		// CODE FOR ADD MARGINS
 		   LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
 		           new LinearLayout.LayoutParams(
 		                   LinearLayout.LayoutParams.MATCH_PARENT,
 		                   LinearLayout.LayoutParams.WRAP_CONTENT));
 		   relativeParams.setMargins(10, 0, 0, 0);
 		   setinhaPraAnimar.setLayoutParams(relativeParams);
 		   setinhaPraAnimar.requestLayout();
 	   }
        
    }*/

}
