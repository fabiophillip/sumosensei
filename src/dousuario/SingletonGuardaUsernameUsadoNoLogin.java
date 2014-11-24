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


	

}
