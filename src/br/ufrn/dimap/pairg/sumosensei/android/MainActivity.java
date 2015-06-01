package br.ufrn.dimap.pairg.sumosensei.android;



import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;


import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaDinheiroDoJogador;

import bancodedados.ChecaVersaoAtualDoSistemaTask;
import bancodedados.SolicitaKanjisParaTreinoTask;

import com.google.android.gms.internal.ar;

import doteppo.ArmazenaMostrarRegrasTreinamento;
import dousuario.SingletonDeveMostrarTelaDeLogin;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import dousuario.TaskAcharUsuarioPorEMailRecuperarSenha;
import dousuario.TaskAcharUsuarioPorEmail;
import dousuario.TaskEnviaEMail;
import dousuario.TaskInserirUsuarioNoBd;
import br.ufrn.dimap.pairg.sumosensei.android.R;
import android.support.v4.app.Fragment;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActivityDoJogoComSom implements ActivityQueChecaPorConexao {
	
	private ProgressDialog popupCarregandoSeUsuarioEstahNaVersaoAtual;
	private String ehParasalvarSenha;
	private boolean mostrarTelaLogin;
	
	private final static int[] SCREENS = {
	    R.id.tela_erro_versao_do_jogo, R.id.tela_inicial_sumo_sensei, R.id.tela_cadastro_sumo_sensei
	};
	
	private void switchToScreen(int screenId) {
		// make the requested screen visible; hide all others.
		for (int id : SCREENS) {
		    findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
		}
	}
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		switchToScreen(R.id.tela_inicial_sumo_sensei);
		
		String fontpathChines = "fonts/Wonton.ttf";
	    Typeface tfChines = Typeface.createFromAsset(getAssets(), fontpathChines);
		Button botaoTeppo = (Button)findViewById(R.id.botaoTeppo);
		Button botaoJogarOnline = (Button) findViewById(R.id.botaoJogarOnline);
		Button botaoModoCompeticao = (Button) findViewById(R.id.botaoModoCompeticao);
		botaoTeppo.setTypeface(tfChines);
		botaoJogarOnline.setTypeface(tfChines);
		botaoModoCompeticao.setTypeface(tfChines);
		
		botaoTeppo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Toast.makeText(getApplicationContext(), "botão ir pra teppo acionado", Toast.LENGTH_SHORT).show();
				ArmazenaMostrarRegrasTreinamento mostrarExplicacaoDoTeppoAntes = ArmazenaMostrarRegrasTreinamento.getInstance();
				boolean mostrarExplicacaoDoTeppo = mostrarExplicacaoDoTeppoAntes.getMostrarRegrasDoTreinamento(getApplicationContext());
				Intent iniciaTelaTreinoIndividual;
				if(mostrarExplicacaoDoTeppo == true)
				{
					iniciaTelaTreinoIndividual = new Intent(MainActivity.this, ExplicacaoTeppo.class);
				}
				else
				{
					iniciaTelaTreinoIndividual = new Intent(MainActivity.this, EscolhaNivelActivity.class);
				}
				
				startActivity(iniciaTelaTreinoIndividual);
				
				
			}
		});
		
		
		
		this.popupCarregandoSeUsuarioEstahNaVersaoAtual = 
				ProgressDialog.show(MainActivity.this, getResources().getString(R.string.checando_versao_atual), 
									getResources().getString(R.string.por_favor_aguarde));
		ChecaVersaoAtualDoSistemaTask taskChecaVersaoAtualDoSistema = new ChecaVersaoAtualDoSistemaTask(this, this.popupCarregandoSeUsuarioEstahNaVersaoAtual);
		taskChecaVersaoAtualDoSistema.execute("");
		
		
	}
	
	
	public void trocarTelaDeAcordoComVersaoDoSistema(String usuarioEstahComVersaoAtualDoJogo)
	{
		if(usuarioEstahComVersaoAtualDoJogo.compareTo("true") == 0)
		{
			
			SingletonDeveMostrarTelaDeLogin sabeSeDeveMostrarLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
			boolean mostrarTelaLogin = sabeSeDeveMostrarLogin.getDeveMostrarTelaDeLogin(getApplicationContext());
			this.mostrarTelaLogin = mostrarTelaLogin;
			boolean mostrarTelaLoginTemporario = sabeSeDeveMostrarLogin.getDeveMostrarTelaLoginTemporario();
			if(mostrarTelaLogin == true && mostrarTelaLoginTemporario == true)
			{
				switchToScreen(R.id.tela_cadastro_sumo_sensei);
				
				//NOVO PASSAR PRA ANDREWS
				getGameHelper().signOut();
				
				
				String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
			    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
			    
			    TextView labelCampoUsuario = (TextView)findViewById(R.id.label_nome_usuario);
			    labelCampoUsuario.setTypeface(tfBrPraTexto);
			    TextView labelCampoEmail= (TextView)findViewById(R.id.label_preencher_com_email);
			    labelCampoEmail.setTypeface(tfBrPraTexto);
			    TextView labelExplicacao2 = (TextView)findViewById(R.id.explicacaoTelaLogin2);
			    labelExplicacao2.setTypeface(tfBrPraTexto);
			    TextView labelExplicacaoLoginInicial = (TextView)findViewById(R.id.explicacao_login_inicial);
			    labelExplicacaoLoginInicial.setTypeface(tfBrPraTexto);
			    
			    
			    
				SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
				String emailJogadorArmazenado = pegarUsernameUsadoPeloJogador.getEmailJogador(getApplicationContext());
				String senhaArmazenada = pegarUsernameUsadoPeloJogador.getSenhaJogador(getApplicationContext());
				String salvarSenha = pegarUsernameUsadoPeloJogador.getSalvarSenha(getApplicationContext());
				if(emailJogadorArmazenado.compareTo("fulanozhjhjagug") != 0)
				{
					EditText textoUsername = (EditText)findViewById(R.id.campo_preencher_email);
					textoUsername.setText(emailJogadorArmazenado);
					if(salvarSenha.compareTo("sim") == 0)
					{
						EditText textoSenha = (EditText)findViewById(R.id.campo_preencher_senha);
						textoSenha.setText(senhaArmazenada);
					}
				}
				
				this.ehParasalvarSenha = salvarSenha;
				Button checkboxSalvarSenha = (Button) findViewById(R.id.checkbox_salvar_senha);
				if(ehParasalvarSenha.compareTo("não") == 0)
				{
					checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
				}
				else
				{
					checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
				}
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
			
		}
		else
		{
			switchToScreen(R.id.tela_erro_versao_do_jogo);
			if(usuarioEstahComVersaoAtualDoJogo.compareTo("false;versaoDesatualizada") != 0)
			{
				//houve erro de conexao
				String mensagemSemConexao = getResources().getString(R.string.mensagem_erro_conexao);
				TextView textViewErro= (TextView)findViewById(R.id.mensagem_erro_sumo_sensei);
				textViewErro.setText(mensagemSemConexao);
				TextView textviewVersaoDoSistema = (TextView) findViewById(R.id.versaoDoSistema);
				String versaoDoApk = "v" + ChecaVersaoAtualDoSistemaTask.versaoDoSistema;
				textviewVersaoDoSistema.setText(versaoDoApk);
			}
		}
		
		TextView textViewVersaoDoJogo = (TextView)findViewById(R.id.versaoDoJogo);
		String versaoDoApk = "v" + ChecaVersaoAtualDoSistemaTask.versaoDoSistema;
		textViewVersaoDoJogo.setText(versaoDoApk);
	}
	
	@Override
	protected void onResume()
	{
		 super.onResume();
			/* View decorView = getWindow().getDecorView();
			// Hide both the navigation bar and the status bar.
			// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
			// a general rule, you should design your app to hide the status bar whenever you
			// hide the navigation bar.
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			              | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);*/
		 View decorView = getWindow().getDecorView();
		 decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		                               | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		                               | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		                               | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                               | View.SYSTEM_UI_FLAG_FULLSCREEN
		                               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
	}
	
	@Override
	protected void onPause()
	{
		
		//TocadorMusicaBackground.getInstance().pausarTocadorMusica();
		if(this.isFinishing())
		{
			Toast.makeText(MainActivity.this, "is finishing will stop service", Toast.LENGTH_SHORT).show();
			Intent iniciaMusicaFundo = new Intent(MainActivity.this, BackgroundSoundService.class);
			stopService(iniciaMusicaFundo);
		}
		super.onPause();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	/**
	 * chamada após o jogador escolher a opcao do multiplayer 
	 */
	public void irParaTelaModoCasual(View v)
	{
		//vamos ver se o jogador tem uma google account associada ao dispositivo. Ele só pode jogar casual se tiver...
		if(deviceHasGoogleAccount() == false)
		{
			Toast.makeText(getApplicationContext(), getResources().getText(R.string.erro_precisa_google_acount_pra_casual), Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent iniciaTelaMOdoCasual = new Intent(MainActivity.this, TelaModoCasual.class);
			startActivity(iniciaTelaMOdoCasual);
		}
		
	}
	
	private boolean deviceHasGoogleAccount(){
        AccountManager accMan = AccountManager.get(this);
        Account[] accArray = accMan.getAccountsByType("com.google");
        return accArray.length >= 1 ? true : false;
}
	
	public void irParaTreinoIndividual(View v)
	{
		Toast.makeText(getApplicationContext(), "botão ir pra teppo acionado", Toast.LENGTH_SHORT).show();
		ArmazenaMostrarRegrasTreinamento mostrarExplicacaoDoTeppoAntes = ArmazenaMostrarRegrasTreinamento.getInstance();
		boolean mostrarExplicacaoDoTeppo = mostrarExplicacaoDoTeppoAntes.getMostrarRegrasDoTreinamento(getApplicationContext());
		Intent iniciaTelaTreinoIndividual;
		if(mostrarExplicacaoDoTeppo == true)
		{
			iniciaTelaTreinoIndividual = new Intent(MainActivity.this, ExplicacaoTeppo.class);
		}
		else
		{
			iniciaTelaTreinoIndividual = new Intent(MainActivity.this, EscolhaNivelActivity.class);
		}
		
		startActivity(iniciaTelaTreinoIndividual);
	}
	
	public void irParaModoCompeticao(View v)
	{
		//vamos ver se o jogador tem uma google account associada ao dispositivo. Ele só pode jogar casual se tiver...
		if(deviceHasGoogleAccount() == false)
		{
			Toast.makeText(getApplicationContext(), getResources().getText(R.string.erro_precisa_google_acount_pra_casual), Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent iniciaTelaCompeticao = new Intent(MainActivity.this, TelaModoCompeticao.class);
			startActivity(iniciaTelaCompeticao);
		}
		
	}
	
	public void irParaLojinha(View v)
	{
		/*
		Intent iniciaTelaLojinha = new Intent(MainActivity.this, LojinhaMaceteKanjiActivity.class);
		startActivity(iniciaTelaLojinha);*/
		
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.lojinha_desabilitada), Toast.LENGTH_SHORT).show();
	}

	

	@Override
	public void onSignInFailed() {
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void adicionarDinheirinho(View v)
	{
		
		DAOAcessaDinheiroDoJogador acessaDinheiroDoJogador = ConcreteDAOAcessaDinheiroDoJogador.getInstance();
		acessaDinheiroDoJogador.adicionarCredito(1500, this);
		int creditoAtual = acessaDinheiroDoJogador.getCreditoQuePossui(this);
		
	}
	
	public void irADadosPartidasAnteriores(View v)
	{
		Intent iniciaTelaLog = new Intent(MainActivity.this, DadosPartidasAnteriores.class);
		startActivity(iniciaTelaLog);
		
	}
	
	public void irParaConfiguracoes(View view)
	 {
		 Intent irParaConfiguracoes =
					new Intent(MainActivity.this, Configuracoes.class);
			startActivity(irParaConfiguracoes);
	 }
	
	ProgressDialog caixaDeDialogoLogandoUsuario;
	
	
	public void cadastrarUsuario(View view)
	{
		/*
		EditText campoPreencherUsername = (EditText) findViewById(R.id.campo__preencher_nome_usuario);
		String username = campoPreencherUsername.getText().toString();
		EditText campoPreencherEmail = (EditText) findViewById(R.id.campo_preencher_email);
		String email = campoPreencherEmail.getText().toString();
		
		SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		guardaUsername.setEmailJogador(email, getApplicationContext());
		guardaUsername.setNomeJogador(username, getApplicationContext());
		
		caixaDeDialogoLogandoUsuario = ProgressDialog.show(this, getResources().getString(R.string.titulo_cadastrando_usuario), getResources().getString(R.string.por_favor_aguarde) );
		TaskInserirUsuarioNoBd registraUsuario = new TaskInserirUsuarioNoBd(caixaDeDialogoLogandoUsuario, this);
		registraUsuario.execute(username, email);*/
		
		Intent intentChamaTelaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
		startActivity(intentChamaTelaCadastro);
	}
	
	public void logarUsuario(View view)
	{
		
		getGameHelper().signOut();
		EditText campoPreencherEmail = (EditText) findViewById(R.id.campo_preencher_email);
		String email = campoPreencherEmail.getText().toString();
		EditText campoPreencherSenha = (EditText) findViewById(R.id.campo_preencher_senha);
		String senha = campoPreencherSenha.getText().toString();
		
		caixaDeDialogoLogandoUsuario = ProgressDialog.show(this, getResources().getString(R.string.titulo_entrando_suario), getResources().getString(R.string.por_favor_aguarde) );
		TaskAcharUsuarioPorEmail logaUsuario = new TaskAcharUsuarioPorEmail(caixaDeDialogoLogandoUsuario, this);
		logaUsuario.execute(email, senha);
	}
	
	public void trocarParaTelaPrincipal()
	{
		//FAZER DESAPARECER TECLADO VIRTUAL
		InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE); 
		View currentFocus = getCurrentFocus();
		if(currentFocus != null)
		{
			inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
	                   InputMethodManager.HIDE_NOT_ALWAYS);
			//FIM DO FAZER DESAPARECER TECLADO VIRTUAL
		}
		
		switchToScreen(R.id.tela_inicial_sumo_sensei);
		String textoCumprimentarJogador = getResources().getString(R.string.introducao_login);
		SingletonGuardaUsernameUsadoNoLogin guardaNomeDeUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		textoCumprimentarJogador = textoCumprimentarJogador + guardaNomeDeUsuario.getNomeJogador(getApplicationContext()) + "!"; 
		Toast.makeText(getApplicationContext(), textoCumprimentarJogador, Toast.LENGTH_SHORT).show();
		
		//agora que ele já logou uma vez, não precisa mostrar a tela de login
		SingletonDeveMostrarTelaDeLogin sabeSeDeveMostrarTelaLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
		sabeSeDeveMostrarTelaLogin.setDeveMostrarTelaLoginTemporario(false);
		
	}
	
	public void mostrarMensagemErroUsuarioESenhaIncorretos()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuario_ou_email_incorretos), Toast.LENGTH_SHORT).show();
	}
	
	public void mostrarMensagemErroUsuarioJahExiste()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuario_jah_existe), Toast.LENGTH_SHORT).show();
	}
	
	
	public void mudarValorSalvarSenha(View v)
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
		
		Button checkboxSalvarSenha = (Button) findViewById(R.id.checkbox_salvar_senha);
		if(ehParasalvarSenha.compareTo("não") == 0)
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
		}
		else
		{
			checkboxSalvarSenha.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
		}
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
	
	public void recuperarSenhaUsuario(View v)
	{
		Intent chamaTelaRecuperarSenha = new Intent(MainActivity.this, TelaRecuperarSenha.class);
		startActivity(chamaTelaRecuperarSenha);
		finish();
				
	}
	
	
	
	
	
	

}
