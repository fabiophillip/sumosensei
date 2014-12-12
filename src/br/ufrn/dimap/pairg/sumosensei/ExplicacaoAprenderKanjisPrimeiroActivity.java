package br.ufrn.dimap.pairg.sumosensei;

import doteppo.ArmazenaMostrarRegrasTreinamento;
import bancodedados.ChecaVersaoAtualDoSistemaTask;
import br.ufrn.dimap.pairg.sumosensei.app.R;
import br.ufrn.dimap.pairg.sumosensei.app.R.id;
import br.ufrn.dimap.pairg.sumosensei.app.R.layout;
import br.ufrn.dimap.pairg.sumosensei.app.R.menu;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExplicacaoAprenderKanjisPrimeiroActivity extends ActivityDoJogoComSom{
	
	private boolean mostrarRegrasNovamente;//inicialmente isso é falso

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		setContentView(R.layout.activity_explicacao_aprender_kanjis_primeiro);
		mostrarRegrasNovamente = true;
		
		TextView tituloExplicacaoTeppo = (TextView) findViewById(R.id.titulo_explica_teppo);
		TextView explicacaoTeppo = (TextView) findViewById(R.id.explicacao_teppo);
		TextView mostrarRegrasNovamente = (TextView) findViewById(R.id.texto_mostrar_explicacao_aprender_kanjis);
		String fontpath = "fonts/Wonton.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
		
		explicacaoTeppo.setTypeface(tfBrPraTexto);
		mostrarRegrasNovamente.setTypeface(tfBrPraTexto);
		
		String fontpathJapones = "fonts/Wonton.ttf";
		Typeface tfJapones = Typeface.createFromAsset(getAssets(), fontpathJapones);
		tituloExplicacaoTeppo.setTypeface(tfJapones);
		TextView subtituloExplicacaoAprenderKanjisPrimeiro = (TextView) findViewById(R.id.subtitulo_explicacao_aprender_kanji);
		subtituloExplicacaoAprenderKanjisPrimeiro.setTypeface(tf);
		
	}
	
	public void irParaSelecionarCategoriasTeppo(View v)
	{
		Intent iniciaTelaTreinoIndividual = new Intent(ExplicacaoAprenderKanjisPrimeiroActivity.this, EscolhaNivelActivity.class);
		iniciaTelaTreinoIndividual.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iniciaTelaTreinoIndividual);
		finish();
	}
	
	public void mudarValorMostrarAvisoAprenderKanjisPrimeiro(View v)
	{
		Button checkboxMostrarRegrasNovamente = (Button)findViewById(R.id.checkbox_mostrar_aviso_aprender_kanji);
		if(this.mostrarRegrasNovamente == false)
		{
			this.mostrarRegrasNovamente = true;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarRegrasNovamente = false;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		ArmazenaMostrarRegrasTreinamento guardaConfiguracoes = ArmazenaMostrarRegrasTreinamento.getInstance();
		guardaConfiguracoes.alterarMostrarAvisoAprenderKanjisAntes(getApplicationContext(), mostrarRegrasNovamente);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	

	
}
