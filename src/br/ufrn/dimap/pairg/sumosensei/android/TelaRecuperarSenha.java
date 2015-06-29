package br.ufrn.dimap.pairg.sumosensei.android;

import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import dousuario.TaskAcharUsuarioPorEMailRecuperarSenha;
import dousuario.TaskEnviaEMail;
import br.ufrn.dimap.pairg.sumosensei.android.R;
import br.ufrn.dimap.pairg.sumosensei.android.R.id;
import br.ufrn.dimap.pairg.sumosensei.android.R.layout;
import br.ufrn.dimap.pairg.sumosensei.android.R.menu;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TelaRecuperarSenha extends ActivityDoJogoComSom {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_recuperar_senha);
		String fontpathChines = "fonts/Wonton.ttf";
	    Typeface tfChines = Typeface.createFromAsset(getAssets(), fontpathChines);
	    Button botaoRecuperar = (Button) findViewById(R.id.botao_recuperar_senha);
	    botaoRecuperar.setTypeface(tfChines);
	    TextView tituloTela = (TextView) findViewById(R.id.titulo_tela_esqueceu_senha);
	    tituloTela.setTypeface(tfChines);
	    
	    String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	    
	    TextView labelCampoEmail= (TextView)findViewById(R.id.label_preencher_com_email);
	    labelCampoEmail.setTypeface(tfBrPraTexto);
		
	}

	

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void recuperarSenhaJogador(View v)
	{
		EditText campoEmailUsuario = (EditText) findViewById(R.id.campo_preencher_email);
		String emailUsuario = campoEmailUsuario.getText().toString();
		if(emailUsuario.length() > 0)
		{
			ProgressDialog popupRecuperandoSenha = 
					ProgressDialog.show(TelaRecuperarSenha.this, getResources().getString(R.string.titulo_email_recupera_senha), 
										getResources().getString(R.string.por_favor_aguarde));
			TaskAcharUsuarioPorEMailRecuperarSenha taskProcurarSenhaJogador = new TaskAcharUsuarioPorEMailRecuperarSenha(popupRecuperandoSenha, this);
			taskProcurarSenhaJogador.execute(emailUsuario);
		}
		else
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro_recupera_senha), Toast.LENGTH_LONG).show();
		}
				
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent chamaTelaInicial = new Intent(TelaRecuperarSenha.this, MainActivity.class);
		startActivity(chamaTelaInicial);
		finish();
	}
	
	public void mandarMensagemRecuperaSenha()
	{
		SingletonGuardaUsernameUsadoNoLogin singletonGuardaDadosUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String senhaJogador = singletonGuardaDadosUsuario.getSenhaJogador(getApplicationContext());
		String mensagemCompletaMandarSenhaJogador = getResources().getString(R.string.aviso_do_email_recuperar_senha) + senhaJogador;
		mensagemCompletaMandarSenhaJogador = mensagemCompletaMandarSenhaJogador + "\n" + getResources().getString(R.string.final_mail_sumo_sensei);
		//GMailSender sender = new GMailSender("fabioandrewsrochamarques@gmail.com", "Doremirocks4");
        String emailDestinatario = singletonGuardaDadosUsuario.getEmailJogador(getApplicationContext());
				try 
				{
					TaskEnviaEMail task = new TaskEnviaEMail();
					String assuntoDoEmail = getResources().getString(R.string.titulo_email_recupera_senha);
					String corpoDoEmail = mensagemCompletaMandarSenhaJogador;
					String quemEnviouOEmail = "sumosenseipairg@gmail.com";
					
					task.execute(assuntoDoEmail, corpoDoEmail, quemEnviouOEmail, emailDestinatario);
					Toast t = Toast.makeText(this, getResources().getString(R.string.aviso_senha), Toast.LENGTH_LONG);
				    t.show();
				    Intent chamaTelaInicial = new Intent(TelaRecuperarSenha.this, MainActivity.class);
					startActivity(chamaTelaInicial);
					finish();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast t = Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG);
				    t.show();
				} 
		
	}
}
