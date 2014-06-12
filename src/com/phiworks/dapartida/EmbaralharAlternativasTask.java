package com.phiworks.dapartida;



import java.util.Collections;
import java.util.LinkedList;

import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.TelaInicialMultiplayer;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class EmbaralharAlternativasTask extends AsyncTask<String, Integer, Void>{

	private Activity activityRodandoAtualmente;//necessária para mudar o texto dos botoes.
	private Button [] botoesAlternativas;
	
	
	public EmbaralharAlternativasTask(Activity activityRodandoAtualmente)
	{
		this.activityRodandoAtualmente = activityRodandoAtualmente;
	}
	@Override
	protected Void doInBackground(String... params) {
		for(int i = 0; i < 10; i++)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			synchronized(this)
			{
				LinkedList<String> textosAlternativas = new LinkedList<String>();
				
				botoesAlternativas = new Button[4];
				botoesAlternativas[0] = (Button)activityRodandoAtualmente.findViewById(R.id.answer1);
				botoesAlternativas[1] = (Button)activityRodandoAtualmente.findViewById(R.id.answer2);
				botoesAlternativas[2] = (Button)activityRodandoAtualmente.findViewById(R.id.answer3);
				botoesAlternativas[3] = (Button)activityRodandoAtualmente.findViewById(R.id.answer4);
				for(int j = 0; j < botoesAlternativas.length; j++)
				{
					String textoUmaAlternativa = botoesAlternativas[j].getText().toString();
					textosAlternativas.add(textoUmaAlternativa);
					
				}
				
				Collections.shuffle(textosAlternativas);
				Collections.shuffle(textosAlternativas);
				
				for(int k = 0; k < textosAlternativas.size(); k++)
				{
					final String textoUmaAlternativa = textosAlternativas.get(k);
					final Button botaoAlternativa = botoesAlternativas[k];
					activityRodandoAtualmente.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							botaoAlternativa.setText(textoUmaAlternativa);
						}
					});
					
				}
			}
			
		}
		
		return null;
	}

}
