package com.phiworks.dapartida;

import android.content.Context;
import android.content.SharedPreferences;

public class ArmazenaMostrarRegrasModosOnline {
	
private static ArmazenaMostrarRegrasModosOnline instancia;
	
	private ArmazenaMostrarRegrasModosOnline()
	{
		
	}
	
	public static ArmazenaMostrarRegrasModosOnline getInstance()
	{
		if(instancia == null)
		{
			instancia = new ArmazenaMostrarRegrasModosOnline();
		}
		
		return instancia;
	}
	
	public boolean getMostrarExplicacaoItens(Context contextoAplicacao)
	{
		SharedPreferences configuracoesSalvas = contextoAplicacao.getSharedPreferences("mostrar_explicacao_itens", Context.MODE_PRIVATE);
		boolean mostrarExplicacaoItens = configuracoesSalvas.getBoolean("mostrar_explicacao_itens", true);
		return mostrarExplicacaoItens;
	}
	
	public void alterarMostrarExplicacaoItens(Context contextoAplicacao, boolean novoValor) 
	{
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("mostrar_explicacao_itens", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putBoolean("mostrar_explicacao_itens", novoValor);
		editorConfig.commit();
	}

}
