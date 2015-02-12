package com.phiworks.dapartida;

import android.content.Context;

public interface DAOGuardaConfiguracoesDoJogador {
	
	public String obterNomeDoJogador(Context contextoDaAplicacao);
	public void mudarNomeDoJogador(String novoNome, Context contextoDaAplicacao);
	

}
