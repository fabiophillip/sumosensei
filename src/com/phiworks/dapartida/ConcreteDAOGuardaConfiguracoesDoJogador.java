package com.phiworks.dapartida;

import android.content.Context;
import android.content.SharedPreferences;

public class ConcreteDAOGuardaConfiguracoesDoJogador implements DAOGuardaConfiguracoesDoJogador{

	private static volatile ConcreteDAOGuardaConfiguracoesDoJogador instanciaUnica;
	
	public static ConcreteDAOGuardaConfiguracoesDoJogador getInstance()
	{
		if(instanciaUnica == null)
		{
			instanciaUnica = new ConcreteDAOGuardaConfiguracoesDoJogador();
		}
		
		return instanciaUnica;
	}
	
	@Override
	public String obterTituloDoJogador(Context contextoDaAplicacao) {
		// TODO Auto-generated method stub
		return "Sumo Não Sei";
	}

	@Override
	public String obterNomeDoJogador(Context contextoDaAplicacao) {
		SharedPreferences configuracoesSalvas = contextoDaAplicacao.getSharedPreferences("config_jogador", Context.MODE_PRIVATE);
		String nomeAtualDoJogador = configuracoesSalvas.getString("nome_jogador", "37WsxnlttyeM*52");
		return nomeAtualDoJogador;
	}

	@Override
	public void mudarNomeDoJogador(String novoNome,
			Context contextoDaAplicacao) 
	{
		SharedPreferences configuracoesSalvar = contextoDaAplicacao.getSharedPreferences("config_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putString("nome_jogador", novoNome);
		editorConfig.commit();
	}


}
