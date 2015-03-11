package dousuario;

import android.content.Context;
import android.content.SharedPreferences;



public class SingletonDeveMostrarTelaDeLogin {
	private static SingletonDeveMostrarTelaDeLogin instancia;
	private boolean mostrarTelaLogin;
	private boolean mostrarTelaLoginPlayTemporario;
	
	private SingletonDeveMostrarTelaDeLogin()
	{
		mostrarTelaLogin = true;
		mostrarTelaLoginPlayTemporario = true;
	}
	
	public static SingletonDeveMostrarTelaDeLogin getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonDeveMostrarTelaDeLogin();
		}
		
		return instancia;
	}

	
	
	public boolean getDeveMostrarTelaDeLogin(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("deveMostrarTelaDeLogin", Context.MODE_PRIVATE);
		boolean deveMostrarTelaDeLogin = configuracoesSalvar.getBoolean("deveMostrarTelaDeLogin", true);
		return deveMostrarTelaDeLogin;
	}

	public void setDeveMostrarTelaDeLogin(boolean novoValor, Context contextoAplicacao)
	{
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("deveMostrarTelaDeLogin", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putBoolean("deveMostrarTelaDeLogin", novoValor);
		editorConfig.commit();
		
	}
	
	public void setDeveMostrarTelaLoginTemporario(boolean novoValor)
	{
		this.mostrarTelaLogin = novoValor;
	}
	
	public boolean getDeveMostrarTelaLoginTemporario()
	{
		return this.mostrarTelaLogin;
	}

	public boolean getMostrarTelaLoginPlayTemporario() {
		return mostrarTelaLoginPlayTemporario;
	}

	public void setMostrarTelaLoginPlayTemporario(
			boolean mostrarTelaLoginPlayTemporario) {
		this.mostrarTelaLoginPlayTemporario = mostrarTelaLoginPlayTemporario;
	}
	
	
	

}
