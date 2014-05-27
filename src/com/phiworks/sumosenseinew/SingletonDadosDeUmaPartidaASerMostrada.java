package com.phiworks.sumosenseinew;

import bancodedados.DadosPartidaParaOLog;

/*serve para transferir o dadospartidaparaolog da tela dadospartidasanteriores para mostrardadosumapartida*/
public class SingletonDadosDeUmaPartidaASerMostrada 
{
	private static SingletonDadosDeUmaPartidaASerMostrada instancia;
	private DadosPartidaParaOLog dadosUmaPartida;
	
	private SingletonDadosDeUmaPartidaASerMostrada()
	{
		
	}
	
	public static SingletonDadosDeUmaPartidaASerMostrada getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonDadosDeUmaPartidaASerMostrada();
		}
		
		return instancia;
	}

	public DadosPartidaParaOLog getDadosUmaPartida() {
		return dadosUmaPartida;
	}

	public void setDadosUmaPartida(DadosPartidaParaOLog dadosUmaPartida) {
		this.dadosUmaPartida = dadosUmaPartida;
	}

}
