package br.ufrn.dimap.pairg.sumosensei.android;

import dousuario.SingletonDeveMostrarTelaDeLogin;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import dousuario.TaskInserirUsuarioNoBd;
import br.ufrn.dimap.pairg.sumosensei.android.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends ActivityDoJogoComSom {
	
	ProgressDialog caixaDeDialogoLogandoUsuario;
	private String email;
	private String senha;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void cadastrarUsuario(View v)
	{
		EditText campoPreencherUsername = (EditText) findViewById(R.id.campo__preencher_nome_usuario_cadastro);
		String username = campoPreencherUsername.getText().toString();
		EditText campoPreencherEmail = (EditText) findViewById(R.id.campo_preencher_email_cadastro);
		email = campoPreencherEmail.getText().toString();
		EditText campoPreencherSenha = (EditText) findViewById(R.id.campo_preencher_senha_cadastro);
		senha = campoPreencherSenha.getText().toString();
		if(username.length() == 0 || email.length() == 0 || senha.length() == 0)
		{
			
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro_faltou_preencher_campo_cadastro), Toast.LENGTH_SHORT).show();
		}
		else
		{
			SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
			guardaUsername.setEmailJogador(email, getApplicationContext());
			guardaUsername.setNomeJogador(username, getApplicationContext());
			guardaUsername.setSenhaJogador(senha, getApplicationContext());
			
			caixaDeDialogoLogandoUsuario = ProgressDialog.show(this, getResources().getString(R.string.titulo_cadastrando_usuario), getResources().getString(R.string.por_favor_aguarde) );
			TaskInserirUsuarioNoBd registraUsuario = new TaskInserirUsuarioNoBd(caixaDeDialogoLogandoUsuario, this);
			registraUsuario.execute(username, email, senha);
		}
		
		
	}
	
	public void trocarParaTelaLogin()
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
		
		String textoCumprimentarJogador = getResources().getString(R.string.cadastro_sucesso);
		SingletonGuardaUsernameUsadoNoLogin guardaNomeDeUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		textoCumprimentarJogador = textoCumprimentarJogador + guardaNomeDeUsuario.getNomeJogador(getApplicationContext()) + "!"; 
		Toast.makeText(getApplicationContext(), textoCumprimentarJogador, Toast.LENGTH_LONG).show();
		SingletonDeveMostrarTelaDeLogin.getInstance().setDeveMostrarTelaLoginTemporario(true);
		SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		pegarUsernameUsadoPeloJogador.setSalvarSenha("sim", getApplicationContext());
		pegarUsernameUsadoPeloJogador.setEmailJogador(email, getApplicationContext());
		pegarUsernameUsadoPeloJogador.setSenhaJogador(senha, getApplicationContext());
		Intent intentChamaTelaPrincipal = new Intent(CadastroActivity.this, MainActivity.class);
		intentChamaTelaPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intentChamaTelaPrincipal);
		finish();
		
	}
	
	public void mostrarMensagemErroUsuarioJahExiste()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuario_jah_existe), Toast.LENGTH_SHORT).show();
	}
	
	public void mostrarMensagemErroEmailJahExiste()
	{
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_jah_existe), Toast.LENGTH_SHORT).show();
	}

	
}
