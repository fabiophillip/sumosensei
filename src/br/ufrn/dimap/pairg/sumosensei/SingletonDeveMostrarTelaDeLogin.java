package br.ufrn.dimap.pairg.sumosensei;

public class SingletonDeveMostrarTelaDeLogin {
	private static SingletonDeveMostrarTelaDeLogin instancia;
	private boolean deveMostrarTelaDeLogin;
	
	public SingletonDeveMostrarTelaDeLogin()
	{
		deveMostrarTelaDeLogin = true;//no inicio, mostra tela de login
	}
	
	public static SingletonDeveMostrarTelaDeLogin getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonDeveMostrarTelaDeLogin();
		}
		
		return instancia;
	}

	public boolean getDeveMostrarTelaDeLogin() {
		return deveMostrarTelaDeLogin;
	}

	public void setDeveMostrarTelaDeLogin(boolean deveMostrarTelaDeLogin) {
		this.deveMostrarTelaDeLogin = deveMostrarTelaDeLogin;
	}
	
	

}
