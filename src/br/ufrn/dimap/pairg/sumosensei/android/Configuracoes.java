package br.ufrn.dimap.pairg.sumosensei.android;

import java.util.Locale;

import com.phiworks.dapartida.ArmazenaMostrarRegrasModosOnline;

import doteppo.ArmazenaMostrarDicaTreinamento;
import doteppo.ArmazenaMostrarRegrasTreinamento;
import dousuario.SingletonDeveMostrarTelaDeLogin;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import br.ufrn.dimap.pairg.sumosensei.android.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracoes extends ActivityDoJogoComSom
{
	private boolean mostrarRegrasTreinamento;
	private boolean mostrarDicasTeppoNovamente;
	private boolean mostrarTelaLogin;
	private boolean mostrarExplicacaoItens;
	private String ehParasalvarSenha;
	private Locale myLocale;
	
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
	    TextView labelConfiguracoesDosModosOnline = (TextView) findViewById(R.id.titulo_config_dos_modos_online);
	    labelConfiguracoesDosModosOnline.setTypeface(tfBrPraTexto);
	    TextView labelMostrarExplicacaoItens = (TextView) findViewById(R.id.texto_mostrar_explicacaoItens);
	    labelMostrarExplicacaoItens.setTypeface(tfBrPraTexto);
	    
	    TextView labelConfiguracoesIdioma = (TextView) findViewById(R.id.titulo_config_idioma);
	    labelConfiguracoesIdioma.setTypeface(tfBrPraTexto);
	    final RadioButton radioPortugues = (RadioButton) findViewById(R.id.radioPortugues);
	    final RadioButton radioIngles = (RadioButton) findViewById(R.id.radioIngles);
	    final RadioButton radioJapones = (RadioButton) findViewById(R.id.radioJapones);
	    
	    radioPortugues.setTypeface(tfBrPraTexto);
	    radioPortugues.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setLocale("pt");
				recreate();
				
			}
		});
	   
	    radioIngles.setTypeface(tfBrPraTexto);
	    radioIngles.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setLocale("en");
				recreate();
				
			}
		});
	    
	    radioJapones.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setLocale("ja");
				recreate();
				
			}
		});
	    
	    TextView tituloLogin = (TextView) findViewById(R.id.titulo_login);
	    tituloLogin.setTypeface(tfBrPraTexto);
	    TextView textoPermanecerLogado = (TextView) findViewById(R.id.texto_permanecer_logado);
	    textoPermanecerLogado.setTypeface(tfBrPraTexto);
	    TextView textoLembrarSenha = (TextView) findViewById(R.id.texto_lembrar_senha);
	    textoLembrarSenha.setTypeface(tfBrPraTexto);
	    
	    
	    SingletonDeveMostrarTelaDeLogin sabeSeDeveMostrarLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
		boolean mostrarTelaLogin = sabeSeDeveMostrarLogin.getDeveMostrarTelaDeLogin(getApplicationContext());
		this.mostrarTelaLogin = mostrarTelaLogin;
	    Button checkboxPermanecerLogado = (Button) findViewById(R.id.checkbox_permanecer_logado);
		if(this.mostrarTelaLogin == false)
		{
			checkboxPermanecerLogado.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
		}
		else
		{
			checkboxPermanecerLogado.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
		}
		
		SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String salvarSenha = pegarUsernameUsadoPeloJogador.getSalvarSenha(getApplicationContext());
		this.ehParasalvarSenha = salvarSenha;
		Button checkboxSalvarSenha = (Button) findViewById(R.id.checkbox_lembrar_senha);
		if(ehParasalvarSenha.compareTo("não") == 0)
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
		}
		else
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
		}
		
		Resources res = getResources();
        this.myLocale = res.getConfiguration().locale;
		if(this.myLocale != null)
		{
			if(this.myLocale.getLanguage().compareTo("en") == 0)
		    {
		    	radioIngles.setChecked(true);
				radioPortugues.setChecked(false);
				radioJapones.setChecked(false);
		    }
		    else if(this.myLocale.getLanguage().compareTo("ja") == 0)
		    {
		    	radioIngles.setChecked(false);
				radioPortugues.setChecked(false);
				radioJapones.setChecked(true);
		    }
		    else if(this.myLocale.getLanguage().compareTo("pt") == 0)
		    {
		    	radioPortugues.setChecked(true);
				radioIngles.setChecked(false);
				radioJapones.setChecked(false);
		    }
		    else // en
		    {
		    	radioIngles.setChecked(true);
				radioPortugues.setChecked(false);
				radioJapones.setChecked(false);
		    }
		}
		Button botaoCheckboxMostrarExplicacaoItens = (Button) findViewById(R.id.checkbox_mostrar_explicacao_itens);
		boolean usuarioOptouPorExplicacaoDeItens = ArmazenaMostrarRegrasModosOnline.getInstance().getMostrarExplicacaoItens(getApplicationContext());
		this.mostrarExplicacaoItens = usuarioOptouPorExplicacaoDeItens;
		if(usuarioOptouPorExplicacaoDeItens == true)
		{
			botaoCheckboxMostrarExplicacaoItens.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckboxMostrarExplicacaoItens.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
		
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
		
		ArmazenaMostrarRegrasModosOnline armazenaRegrasModosOnline = ArmazenaMostrarRegrasModosOnline.getInstance();
		armazenaRegrasModosOnline.alterarMostrarExplicacaoItens(getApplicationContext(), mostrarExplicacaoItens);
		
		Intent voltaAoMenuPrincipal =
				new Intent(this, MainActivity.class);
		voltaAoMenuPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(voltaAoMenuPrincipal);
		finish();
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
		
		this.ehParasalvarSenha = "não";
		Button botaoCheckboxSalvarSenha = (Button) findViewById(R.id.checkbox_lembrar_senha);
		botaoCheckboxSalvarSenha.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		
		this.mostrarTelaLogin = true;
		Button botaoCheckboxPermanecerLogado = (Button) findViewById(R.id.checkbox_permanecer_logado);
		botaoCheckboxPermanecerLogado.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		
		this.mostrarExplicacaoItens = true;
		Button botaoCheckboxExplicarItens = (Button) findViewById(R.id.checkbox_mostrar_explicacao_itens);
		botaoCheckboxExplicarItens.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		
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
		
		//NOVO PASSAR PRA ANDREWS
		getGameHelper().signOut();
		
		Intent chamaTelaLogin = new Intent(Configuracoes.this, MainActivity.class);
		startActivity(chamaTelaLogin);
		finish();
	}
	
	public void mudarValorPermanecerLogado(View v)
	{
		if(this.mostrarTelaLogin == true)
		{
			this.mostrarTelaLogin = false;
		}
		else
		{
			this.mostrarTelaLogin = true;
		}
		SingletonDeveMostrarTelaDeLogin sabeSeDeveMostrarLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
		sabeSeDeveMostrarLogin.setDeveMostrarTelaDeLogin(mostrarTelaLogin, getApplicationContext());
		sabeSeDeveMostrarLogin.setDeveMostrarTelaLoginTemporario(mostrarTelaLogin);
		Button checkboxPermanecerLogado = (Button) findViewById(R.id.checkbox_permanecer_logado);
		if(this.mostrarTelaLogin == false)
		{
			checkboxPermanecerLogado.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
		}
		else
		{
			checkboxPermanecerLogado.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
		}
	}
	
	public void mudarValorLembrarSenha(View v)
	{
		if(this.ehParasalvarSenha.compareTo("sim") == 0)
		{
			this.ehParasalvarSenha = "não";
		}
		else
		{
			this.ehParasalvarSenha = "sim";
		}
		
		SingletonGuardaUsernameUsadoNoLogin guardaSalvarSenha = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		guardaSalvarSenha.setSalvarSenha(ehParasalvarSenha, getApplicationContext());
		
		Button checkboxSalvarSenha = (Button) findViewById(R.id.checkbox_lembrar_senha);
		if(ehParasalvarSenha.compareTo("não") == 0)
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
		}
		else
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
		}
	}
	
	public void mudarValorMostrarExplicacaoItens(View v)
	{
		Button checkboxMostrarExplicacaoItensNovamente = (Button)findViewById(R.id.checkbox_mostrar_explicacao_itens);
		if(this.mostrarExplicacaoItens == false)
		{
			this.mostrarExplicacaoItens = true;
			checkboxMostrarExplicacaoItensNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarExplicacaoItens = false;
			checkboxMostrarExplicacaoItensNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		ArmazenaMostrarRegrasModosOnline guardaConfiguracoes = ArmazenaMostrarRegrasModosOnline.getInstance();
		guardaConfiguracoes.alterarMostrarExplicacaoItens(getApplicationContext(), mostrarExplicacaoItens);
	}



	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	 public void setLocale(String lang) {
	        myLocale = new Locale(lang);
	        Resources res = getResources();
	        DisplayMetrics dm = res.getDisplayMetrics();
	        Configuration conf = res.getConfiguration();
	        conf.locale = myLocale;
	        res.updateConfiguration(conf, dm);
	        Intent refresh = new Intent(this, Configuracoes.class);
	        startActivity(refresh);
	        finish();
	    }

}
