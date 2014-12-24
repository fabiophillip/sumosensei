package dousuario;

import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import android.content.Context;
import android.content.SharedPreferences;

public class SingletonGuardaUsernameUsadoNoLogin {
	
private static volatile SingletonGuardaUsernameUsadoNoLogin instanciaUnica;
	
	public static SingletonGuardaUsernameUsadoNoLogin getInstance()
	{
		if(instanciaUnica == null)
		{
			instanciaUnica = new SingletonGuardaUsernameUsadoNoLogin();
		}
		
		return instanciaUnica;
	}

	public String getNomeJogador(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("nome_jogador", Context.MODE_PRIVATE);
		String username = configuracoesSalvar.getString("nome_jogador", "uzuario44904490");
		return username;
	}

	public void setNomeJogador(String novoNome, Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("nome_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putString("nome_jogador", novoNome);
		editorConfig.commit();
		
	}
	
	public String getEmailJogador(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("email_jogador", Context.MODE_PRIVATE);
		String email = configuracoesSalvar.getString("email_jogador", "fulanozhjhjagug");
		return email;
	}

	public void setEmailJogador(String novoEmail, Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("email_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putString("email_jogador", novoEmail);
		editorConfig.commit();
		
	}
	
	public String getSenhaJogador(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("senha_jogador", Context.MODE_PRIVATE);
		String senha = configuracoesSalvar.getString("senha_jogador", "");
		return senha;
	}

	public void setSenhaJogador(String novaSenha, Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("senha_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putString("senha_jogador", novaSenha);
		editorConfig.commit();
		
	}
	
	public String getSalvarSenha(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("salvar_senha", Context.MODE_PRIVATE);
		String senha = configuracoesSalvar.getString("salvar_senha", "não");
		return senha;
	}

	public void setSalvarSenha(String novoSalvarSenha, Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("salvar_senha", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putString("salvar_senha", novoSalvarSenha);
		editorConfig.commit();
		
	}


	

}
