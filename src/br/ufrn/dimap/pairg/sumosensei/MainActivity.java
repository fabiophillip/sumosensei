package br.ufrn.dimap.pairg.sumosensei;



import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaDinheiroDoJogador;

import bancodedados.ChecaVersaoAtualDoSistemaTask;
import bancodedados.SolicitaKanjisParaTreinoTask;

import com.google.android.gms.internal.ar;

import doteppo.ArmazenaMostrarRegrasTreinamento;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import dousuario.TaskAcharUsuarioPorNome;
import dousuario.TaskInserirUsuarioNoBd;
import br.ufrn.dimap.pairg.sumosensei.app.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActivityDoJogoComSom implements ActivityQueChecaPorConexao {
	
	private ProgressDialog popupCarregandoSeUsuarioEstahNaVersaoAtual;
	
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
			boolean mostrarTelaLogin = sabeSeDeveMostrarLogin.getDeveMostrarTelaDeLogin();
			if(mostrarTelaLogin == true)
			{
				switchToScreen(R.id.tela_cadastro_sumo_sensei);
				SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
				String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
				String emailJogadorArmazenado = pegarUsernameUsadoPeloJogador.getEmailJogador(getApplicationContext());
				if(nomeJogadorArmazenado.compareTo("uzuario44904490") != 0)
				{
					EditText textoUsername = (EditText)findViewById(R.id.campo__preencher_nome_usuario);
					textoUsername.setText(nomeJogadorArmazenado);
					EditText textoEmail = (EditText)findViewById(R.id.campo_preencher_email);
					textoEmail.setText(emailJogadorArmazenado);
				}
				else
				{
					switchToScreen(R.id.tela_inicial_sumo_sensei);
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
			}
		}
		
		TextView textViewVersaoDoJogo = (TextView)findViewById(R.id.versaoDoJogo);
		TextView textviewVersaoDoSistema = (TextView) findViewById(R.id.versaoDoSistema);
		String versaoDoApk = "v" + ChecaVersaoAtualDoSistemaTask.versaoDoSistema;
		textViewVersaoDoJogo.setText(versaoDoApk);
		textviewVersaoDoSistema.setText(versaoDoApk);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
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
		Intent iniciaTelaMOdoCasual = new Intent(MainActivity.this, TelaModoCasual.class);
		startActivity(iniciaTelaMOdoCasual);
	}
	
	public void irParaTreinoIndividual(View v)
	{
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
	
	public void irParaLojinha(View v)
	{
		Intent iniciaTelaLojinha = new Intent(MainActivity.this, LojinhaMaceteKanjiActivity.class);
		startActivity(iniciaTelaLojinha);
	}

	

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
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
		EditText campoPreencherUsername = (EditText) findViewById(R.id.campo__preencher_nome_usuario);
		String username = campoPreencherUsername.getText().toString();
		EditText campoPreencherEmail = (EditText) findViewById(R.id.campo_preencher_email);
		String email = campoPreencherEmail.getText().toString();
		
		SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		guardaUsername.setEmailJogador(email, getApplicationContext());
		guardaUsername.setNomeJogador(username, getApplicationContext());
		
		caixaDeDialogoLogandoUsuario = ProgressDialog.show(this, getResources().getString(R.string.titulo_cadastrando_usuario), getResources().getString(R.string.por_favor_aguarde) );
		TaskInserirUsuarioNoBd registraUsuario = new TaskInserirUsuarioNoBd(caixaDeDialogoLogandoUsuario, this);
		registraUsuario.execute(username, email);
	}
	
	public void logarUsuario(View view)
	{
		EditText campoPreencherUsername = (EditText) findViewById(R.id.campo__preencher_nome_usuario);
		String username = campoPreencherUsername.getText().toString();
		EditText campoPreencherEmail = (EditText) findViewById(R.id.campo_preencher_email);
		String email = campoPreencherEmail.getText().toString();
		
		SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		guardaUsername.setEmailJogador(email, getApplicationContext());
		guardaUsername.setNomeJogador(username, getApplicationContext());
		
		caixaDeDialogoLogandoUsuario = ProgressDialog.show(this, getResources().getString(R.string.titulo_entrando_suario), getResources().getString(R.string.por_favor_aguarde) );
		TaskAcharUsuarioPorNome logaUsuario = new TaskAcharUsuarioPorNome(caixaDeDialogoLogandoUsuario, this);
		logaUsuario.execute(username, email);
	}
	
	public void trocarParaTelaPrincipal()
	{
		switchToScreen(R.id.tela_inicial_sumo_sensei);
		//trocou para tela principal? o jogador então não precisa mais passar pela tela de login
		SingletonDeveMostrarTelaDeLogin guardaMostrarTelaLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
		guardaMostrarTelaLogin.setDeveMostrarTelaDeLogin(false);
		String textoCumprimentarJogador = getResources().getString(R.string.introducao_login);
		SingletonGuardaUsernameUsadoNoLogin guardaNomeDeUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		textoCumprimentarJogador = textoCumprimentarJogador + guardaNomeDeUsuario.getNomeJogador(getApplicationContext()) + "!"; 
		Toast.makeText(getApplicationContext(), textoCumprimentarJogador, Toast.LENGTH_SHORT).show();
	}
	
	public void mostrarMensagemErroUsuarioESenhaIncorretos()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuario_ou_email_incorretos), Toast.LENGTH_SHORT).show();
	}
	
	public void mostrarMensagemErroUsuarioJahExiste()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuario_jah_existe), Toast.LENGTH_SHORT).show();
	}

}
