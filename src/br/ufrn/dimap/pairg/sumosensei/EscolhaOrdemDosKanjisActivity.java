package br.ufrn.dimap.pairg.sumosensei;

import doteppo.ArmazenaMostrarDicaTreinamento;
import doteppo.GuardaFormaComoKanjisSeraoTreinados;
import br.ufrn.dimap.pairg.sumosensei.app.R;
import br.ufrn.dimap.pairg.sumosensei.app.R.id;
import br.ufrn.dimap.pairg.sumosensei.app.R.layout;
import br.ufrn.dimap.pairg.sumosensei.app.R.menu;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class EscolhaOrdemDosKanjisActivity extends ActivityDoJogoComSom{
	
	 private String modoComoOsKanjisSeraoJogados = "aleatoriamente";//"aleatoriamente", "menos_jogados" ou "mais_errados"

	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_escolha_ordem_dos_kanjis);
		TextView tituloEscolhaCategorias = (TextView) findViewById(R.id.textoTituloEscolhaOrdemKanjis);
		TextView subTituloTela = (TextView) findViewById(R.id.subtituloEscolhaOrdemKanjis);
		RadioButton radioAleatoriamente = (RadioButton) findViewById(R.id.radioTreinarKanjisAleatoriamente);
		RadioButton radioMaisErradas = (RadioButton) findViewById(R.id.radioTreinarKanjisMaisErrados);
		RadioButton radioMenosAcertadas = (RadioButton) findViewById(R.id.radioTreinarKanjisMenosTreinados);
		  String fontpath = "fonts/Wonton.ttf";
		  Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
		  String fontpathBr = "fonts/gilles_comic_br.ttf";
		    Typeface tfBr = Typeface.createFromAsset(getAssets(), fontpathBr);
		  tituloEscolhaCategorias.setTypeface(tf);
		  subTituloTela.setTypeface(tf);
		  radioAleatoriamente.setTypeface(tfBr);
		  radioMaisErradas.setTypeface(tfBr);
		  radioMenosAcertadas.setTypeface(tfBr);
		  
		  
		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void confirmarOrdemDeTreinoDosKanjis(View v)
	{
		 GuardaFormaComoKanjisSeraoTreinados guardaFormaComoKanjisSaoApresentados =
				  GuardaFormaComoKanjisSeraoTreinados.getInstance();
		  guardaFormaComoKanjisSaoApresentados.setModoDeJogo(modoComoOsKanjisSeraoJogados);								  
		  ArmazenaMostrarDicaTreinamento mostrarDicasPalavrasDoTeppoAntes = ArmazenaMostrarDicaTreinamento.getInstance();
			boolean mostrarDicasPalavrasTeppo = mostrarDicasPalavrasDoTeppoAntes.getMostrarDicaDoTreinamento(getApplicationContext());
			Intent iniciaTelaTreinoIndividual;
			if(mostrarDicasPalavrasTeppo == true)
			{
				iniciaTelaTreinoIndividual = new Intent(EscolhaOrdemDosKanjisActivity.this, VerPalavrasTreinadasTeppoActivity.class);
			}
			else
			{
				mudarMusicaDeFundo(R.raw.headstart);
				iniciaTelaTreinoIndividual = new Intent(EscolhaOrdemDosKanjisActivity.this, TreinoTeppo.class);
			}
			
			startActivity(iniciaTelaTreinoIndividual);
			finish();
	}
	
	//NOVO DOS RADIOBUTTONS
		public void onRadioButtonClicked(View view) {
		    // Is the button now checked?
		    boolean checked = ((RadioButton) view).isChecked();
		    
		    // Check which radio button was clicked
		    switch(view.getId()) {
		        case R.id.radioTreinarKanjisAleatoriamente:
		            if (checked)
		            {
		            	this.modoComoOsKanjisSeraoJogados = "aleatoriamente";
		            }
		            break;
		        case R.id.radioTreinarKanjisMaisErrados:
		            if (checked)
		            {
		            	this.modoComoOsKanjisSeraoJogados = "mais_errados";
		            }
		            break;
		        case R.id.radioTreinarKanjisMenosTreinados:
		        	if(checked)
		        	{
		        		this.modoComoOsKanjisSeraoJogados = "menos_jogados";
		        	}
		        	break;
		    }
		}
}
