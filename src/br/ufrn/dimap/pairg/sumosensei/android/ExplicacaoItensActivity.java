package br.ufrn.dimap.pairg.sumosensei.android;

import com.phiworks.dapartida.ArmazenaMostrarRegrasModosOnline;

import doteppo.ArmazenaMostrarRegrasTreinamento;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExplicacaoItensActivity extends ActivityDoJogoComSom {

	private boolean mostrarExplicacaoItensNovamente;//inicialmente isso é true
	private String qualDosModosOnlineMostrarEmSeguida;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		setContentView(R.layout.activity_explicacao_itens);
		
		Bundle containerDadosQualModoDeJogoJogar = getIntent().getExtras();
		if(containerDadosQualModoDeJogoJogar != null)
		{
			qualDosModosOnlineMostrarEmSeguida = containerDadosQualModoDeJogoJogar.getString("qual_dos_modos_online_jogar");
		}
		mostrarExplicacaoItensNovamente = true;
		String fontpath = "fonts/Wonton.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
		TextView textviewTituloExplicaItens = (TextView) findViewById(R.id.tituloTelaExplicacaoItens);
		textviewTituloExplicaItens.setTypeface(tf);
		TextView textviewTituloExplicaItem1 = (TextView) findViewById(R.id.texto_titulo_item1);
		textviewTituloExplicaItem1.setTypeface(tf);
		TextView textviewTituloExplicaItem2 = (TextView) findViewById(R.id.texto_titulo_item2);
		textviewTituloExplicaItem2.setTypeface(tf);
		TextView textviewTituloExplicaItem3 = (TextView) findViewById(R.id.texto_titulo_item3);
		textviewTituloExplicaItem3.setTypeface(tf);
		TextView textviewTituloExplicaItem4 = (TextView) findViewById(R.id.texto_titulo_item4);
		textviewTituloExplicaItem4.setTypeface(tf);
		
		String fontpath2 = "fonts/chifont.ttf";
		Typeface tf2 = Typeface.createFromAsset(getAssets(), fontpath2);
		TextView textviewSubtituloExplicaItem1 = (TextView) findViewById(R.id.texto_subtitulo_item1);
		textviewSubtituloExplicaItem1.setTypeface(tf2);
		TextView textviewSubtituloExplicaItem2 = (TextView) findViewById(R.id.texto_subtitulo_item2);
		textviewSubtituloExplicaItem2.setTypeface(tf2);
		TextView textviewSubtituloExplicaItem3 = (TextView) findViewById(R.id.texto_subtitulo_item3);
		textviewSubtituloExplicaItem3.setTypeface(tf2);
		TextView textviewSubtituloExplicaItem4 = (TextView) findViewById(R.id.texto_subtitulo_item4);
		textviewSubtituloExplicaItem4.setTypeface(tf2);
		
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	    
	    TextView textviewExplicacaoItens = (TextView) findViewById(R.id.explicacao_itens);
	    TextView textviewExplicacaoItem1 = (TextView) findViewById(R.id.texto_explica_item1);
	    TextView textviewExplicacaoItem2 = (TextView) findViewById(R.id.texto_explica_item2);
	    TextView textviewExplicacaoItem3 = (TextView) findViewById(R.id.texto_explica_item3);
	    TextView textviewExplicacaoItem4 = (TextView) findViewById(R.id.texto_explica_item4);
	    TextView textviewExplicarItensNovamente = (TextView) findViewById(R.id.texto_mostrar_explicacao_itens);
	    
	    
	    textviewExplicacaoItens.setTypeface(tfBrPraTexto);
	    textviewExplicacaoItem1.setTypeface(tfBrPraTexto);
	    textviewExplicacaoItem2.setTypeface(tfBrPraTexto);
	    textviewExplicacaoItem3.setTypeface(tfBrPraTexto);
	    textviewExplicacaoItem4.setTypeface(tfBrPraTexto);
	    textviewExplicarItensNovamente.setTypeface(tfBrPraTexto);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void mudarValorMostrarAvisoItens(View v)
	{
		Button checkboxMostrarExplicacaoItensNovamente = (Button)findViewById(R.id.checkbox_mostrar_aviso_itens);
		if(this.mostrarExplicacaoItensNovamente == false)
		{
			this.mostrarExplicacaoItensNovamente = true;
			checkboxMostrarExplicacaoItensNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarExplicacaoItensNovamente = false;
			checkboxMostrarExplicacaoItensNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		ArmazenaMostrarRegrasModosOnline guardaConfiguracoes = ArmazenaMostrarRegrasModosOnline.getInstance();
		guardaConfiguracoes.alterarMostrarExplicacaoItens(getApplicationContext(), mostrarExplicacaoItensNovamente);
	}
	
	public void jogarModoOnline(View v)
	{
		if(this.qualDosModosOnlineMostrarEmSeguida.compareTo("casual") == 0)
		{
			Intent iniciaTelaCasual = new Intent(ExplicacaoItensActivity.this, TelaModoCasual.class);
			startActivity(iniciaTelaCasual);
			finish();
		}
		else
		{
			Intent iniciaTelaCompeticao = new Intent(ExplicacaoItensActivity.this, TelaModoCompeticao.class);
			startActivity(iniciaTelaCompeticao);
			finish();
		}
	}
}
