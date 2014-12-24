package br.ufrn.dimap.pairg.sumosensei;

import doteppo.ArmazenaMostrarDicaTreinamento;
import doteppo.ArmazenaMostrarRegrasTreinamento;
import dousuario.SingletonDeveMostrarTelaDeLogin;
import br.ufrn.dimap.pairg.sumosensei.app.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Configuracoes extends ActivityDoJogoComSom
{
	private boolean mostrarRegrasTreinamento;
	private boolean mostrarDicasTeppoNovamente;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		
		
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
							ArmazenaMostrarRegrasTreinamento.getInstance();
		this.mostrarRegrasTreinamento = 
				armazenaMostrarRegras.getMostrarRegrasDoTreinamento(this);
		
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		
		if(this.mostrarRegrasTreinamento == true)
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
		
		ArmazenaMostrarDicaTreinamento armazenaMostrarDicasTreinamento = ArmazenaMostrarDicaTreinamento.getInstance();
		
		this.mostrarDicasTeppoNovamente = 
				armazenaMostrarDicasTreinamento.getMostrarDicaDoTreinamento(this);
		
		Button botaoCheckboxMostrarKanjisMemorizar = (Button) findViewById(R.id.checkbox_mostrar_kanjis_memorizar_teppo);
		
		if(this.mostrarDicasTeppoNovamente == true)
		{
			botaoCheckboxMostrarKanjisMemorizar.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckboxMostrarKanjisMemorizar.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
		
		//mudar fonte do texto da tela de configurações
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	    
	    TextView tituloConfiguracoes = (TextView)findViewById(R.id.titulo_configuracoes);
	    tituloConfiguracoes.setTypeface(tfBrPraTexto);
	    TextView labelMostrarRegrasTreinamento = (TextView)findViewById(R.id.texto_mostrar_regras_treinamento);
	    labelMostrarRegrasTreinamento.setTypeface(tfBrPraTexto);
	    TextView labelMostrarKanjisMemorizarTreinamento = (TextView)findViewById(R.id.texto_mostrar_kanjis_memorizar_teppo);
	    labelMostrarKanjisMemorizarTreinamento.setTypeface(tfBrPraTexto);
	    TextView labelConfiguracoesDoTeppo = (TextView) findViewById(R.id.titulo_config_do_teppo);
	    labelConfiguracoesDoTeppo.setTypeface(tfBrPraTexto);
	    TextView labelConfiguracoesIdioma = (TextView) findViewById(R.id.titulo_config_idioma);
	    labelConfiguracoesIdioma.setTypeface(tfBrPraTexto);
	    RadioButton radioPortugues = (RadioButton) findViewById(R.id.radioPortugues);
	    radioPortugues.setTypeface(tfBrPraTexto);
	    RadioButton radioIngles = (RadioButton) findViewById(R.id.radioIngles);
	    radioIngles.setTypeface(tfBrPraTexto);
	    RadioButton radioEspanhol = (RadioButton) findViewById(R.id.radioEspanhol);
	    radioEspanhol.setTypeface(tfBrPraTexto);
	}

	
	
	public void salvarConfiguracoes(View v)
	{
		//armazenar o valor da checkbox mostrar regras modo treinamento
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
				ArmazenaMostrarRegrasTreinamento.getInstance();
		armazenaMostrarRegras.alterarMostrarRegrasDoTreinamento(this, this.mostrarRegrasTreinamento);
		//armazenar o valor da checkbox mostrar kanjis treinados modo treinamento
		ArmazenaMostrarDicaTreinamento armazenaMostrarKanjisTreinamento = 
				ArmazenaMostrarDicaTreinamento.getInstance();
		armazenaMostrarKanjisTreinamento.alterarMostrarDicaDoTreinamento(this, this.mostrarDicasTeppoNovamente);
		
		
		
		Intent voltaAoMenuPrincipal =
				new Intent(this, MainActivity.class);
		voltaAoMenuPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(voltaAoMenuPrincipal);
	}
	
	public void usarConfiguracoesPadrao(View v)
	{
		//armazenar valor padrao de mostrar regras modo treinamento
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
				ArmazenaMostrarRegrasTreinamento.getInstance();
		armazenaMostrarRegras.alterarMostrarRegrasDoTreinamento(this, true);
		
		mostrarRegrasTreinamento = true;
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		
		ArmazenaMostrarDicaTreinamento armazenaMostrarDica =
				ArmazenaMostrarDicaTreinamento.getInstance();
		armazenaMostrarDica.alterarMostrarDicaDoTreinamento(this, true);
		
		mostrarDicasTeppoNovamente = true;
		Button botaoCheckboxMostrarKanjisTreinar = (Button) findViewById(R.id.checkbox_mostrar_kanjis_memorizar_teppo);
		botaoCheckboxMostrarKanjisTreinar.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
	}
	
	public void mudarValorMostrarRegrasTreinamento(View v)
	{
		if(this.mostrarRegrasTreinamento == true)
		{
			this.mostrarRegrasTreinamento = false;
		}
		else
		{
			this.mostrarRegrasTreinamento = true;
		}
		
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		
		if(this.mostrarRegrasTreinamento == true)
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
	}
	
	public void mudarValorMostrarKanjisMemorizar(View view)
	{
		Button checkboxMostrarRegrasNovamente = (Button)findViewById(R.id.checkbox_mostrar_kanjis_memorizar_teppo);
		if(this.mostrarDicasTeppoNovamente == false)
		{
			this.mostrarDicasTeppoNovamente = true;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarDicasTeppoNovamente = false;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		ArmazenaMostrarDicaTreinamento guardaConfiguracoes = ArmazenaMostrarDicaTreinamento.getInstance();
		guardaConfiguracoes.alterarMostrarDicaDoTreinamento(getApplicationContext(), mostrarDicasTeppoNovamente);
	}
	
	public void trocarUsuario(View v)
	{
		SingletonDeveMostrarTelaDeLogin sabeSeDeveMostrarLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
		sabeSeDeveMostrarLogin.setDeveMostrarTelaDeLogin(true, getApplicationContext());
		sabeSeDeveMostrarLogin.setDeveMostrarTelaLoginTemporario(true);
		
		Intent chamaTelaLogin = new Intent(Configuracoes.this, MainActivity.class);
		startActivity(chamaTelaLogin);
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
