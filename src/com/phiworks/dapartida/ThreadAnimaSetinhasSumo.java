package com.phiworks.dapartida;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ThreadAnimaSetinhasSumo extends Thread {
	private ImageView setinhaPraAnimar;
	private Activity telaQueCriouATask;
	private boolean setaFicaAnimadaPraDireita;
	
	public ThreadAnimaSetinhasSumo(ImageView setinhaAnimar, Activity telaCriouTask, boolean setaComecaAnimadaPraDireita)
	{
		this.setinhaPraAnimar = setinhaAnimar;
		this.telaQueCriouATask = telaCriouTask;
		this.setaFicaAnimadaPraDireita = setaComecaAnimadaPraDireita;
	}
	
	@Override
	public void run()
	{
		while(this.isInterrupted() == false)
		{
			try {
				Thread.sleep(200);
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
	}

}
