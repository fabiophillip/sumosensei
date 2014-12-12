package br.ufrn.dimap.pairg.sumosensei;


import doteppo.ArmazenaMostrarRegrasTreinamento;
import br.ufrn.dimap.pairg.sumosensei.app.R;

import bancodedados.ChecaVersaoAtualDoSistemaTask;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExplicacaoTeppo extends ActivityDoJogoComSom implements ActivityQueChecaPorConexao {

	private boolean mostrarRegrasNovamente;//inicialmente isso é falso
	private ProgressDialog popupCarregandoSeUsuarioEstahNaVersaoAtual;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explicacao_teppo);
		mostrarRegrasNovamente = true;
		
		TextView tituloExplicacaoTeppo = (TextView) findViewById(R.id.titulo_explica_teppo);
		TextView explicacaoTeppo = (TextView) findViewById(R.id.explicacao_teppo);
		TextView mostrarRegrasNovamente = (TextView) findViewById(R.id.texto_mostrar_regras_treinamento);
		String fontpath = "fonts/Wonton.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
		
		explicacaoTeppo.setTypeface(tfBrPraTexto);
		mostrarRegrasNovamente.setTypeface(tfBrPraTexto);
		
		String fontpathJapones = "fonts/Wonton.ttf";
		Typeface tfJapones = Typeface.createFromAsset(getAssets(), fontpathJapones);
		tituloExplicacaoTeppo.setTypeface(tfJapones);
		TextView subtituloExplicacaoTeppoJapones = (TextView) findViewById(R.id.subtitulo_teppo);
		subtituloExplicacaoTeppoJapones.setTypeface(tf);
		
		this.popupCarregandoSeUsuarioEstahNaVersaoAtual = 
				ProgressDialog.show(ExplicacaoTeppo.this, getResources().getString(R.string.checando_conexao), 
									getResources().getString(R.string.por_favor_aguarde));
		ChecaVersaoAtualDoSistemaTask taskChecaVersaoAtualDoSistema = new ChecaVersaoAtualDoSistemaTask(this, this.popupCarregandoSeUsuarioEstahNaVersaoAtual);
		taskChecaVersaoAtualDoSistema.execute("");

		
	}
	
	public void irParaSelecionarCategoriasTeppo(View v)
	{
		ArmazenaMostrarRegrasTreinamento guardaConfiguracoes = ArmazenaMostrarRegrasTreinamento.getInstance();
		boolean mostrarExplicacaoAprenderKanjisPrimeiro = guardaConfiguracoes.getMostrarAvisoAprenderKanjisAntes(getApplicationContext());
		Intent iniciaTelaTreinoIndividual;
		if(mostrarExplicacaoAprenderKanjisPrimeiro == false)
		{
			iniciaTelaTreinoIndividual = new Intent(ExplicacaoTeppo.this, EscolhaNivelActivity.class);
		}
		else
		{
			iniciaTelaTreinoIndividual = new Intent(ExplicacaoTeppo.this, ExplicacaoAprenderKanjisPrimeiroActivity.class);
		}
		iniciaTelaTreinoIndividual.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iniciaTelaTreinoIndividual);
		//finish();
	}
	
	public void mudarValorMostrarRegrasTreinamento(View v)
	{
		Button checkboxMostrarRegrasNovamente = (Button)findViewById(R.id.checkbox_mostrar_regras_treinamento);
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
		guardaConfiguracoes.alterarMostrarRegrasDoTreinamento(getApplicationContext(), mostrarRegrasNovamente);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trocarTelaDeAcordoComVersaoDoSistema(
			String mensagemUsuarioEstahComVersaoAtualDoSistema) 
	{
		if(mensagemUsuarioEstahComVersaoAtualDoSistema.contains("erroConexao") == true)
		{
			TextView textoExplicandoTeppo = (TextView) findViewById(R.id.explicacao_teppo);
			textoExplicandoTeppo.setText(getResources().getString(R.string.mensagem_erro_conexao));
			Button botaoSeguinte = (Button) findViewById(R.id.botao_quero_jogar_teppo);
			botaoSeguinte.setVisibility(View.INVISIBLE);
			LinearLayout checkboxNaoAparecerNovamente  = (LinearLayout) findViewById(R.id.layout_botao_e_checkbox_nao_aparecer_mais);
			checkboxNaoAparecerNovamente.setVisibility(View.INVISIBLE);
			
		}
		else if(mensagemUsuarioEstahComVersaoAtualDoSistema.contains("versaoDesatualizada") == true)
		{
			TextView textoExplicandoTeppo = (TextView) findViewById(R.id.explicacao_teppo);
			textoExplicandoTeppo.setText(getResources().getString(R.string.mensagem_erro_versao));
			Button botaoSeguinte = (Button) findViewById(R.id.botao_quero_jogar_teppo);
			botaoSeguinte.setVisibility(View.INVISIBLE);
			LinearLayout checkboxNaoAparecerNovamente  = (LinearLayout) findViewById(R.id.layout_botao_e_checkbox_nao_aparecer_mais);
			checkboxNaoAparecerNovamente.setVisibility(View.INVISIBLE);
			
		}
		
	}

	

	

	

}
