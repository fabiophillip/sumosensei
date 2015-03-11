package com.phiworks.domodocasual;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;

public class AssociaCategoriaComIcone 
{
	private static LinkedList<String> nomeCategoriasDoJogo = 
			new LinkedList<String>(Arrays.asList("Adjetivos", "Calendário", "Contagem", "Cotidiano",
													"Números", "Japão", "Tempo", "Viagem",
													"posições e direções"));
	private static LinkedList<String> nomeImagensCategoria =
			new LinkedList<String>(Arrays.asList("iconcat_adjetivos", "iconcat_calendario", "iconcat_contarcoisas", "iconcat_cotidiano",
					"iconcat_numerosedinheiro", "iconcat_ojapao", "iconcat_otempo", "iconcat_paraquandoforviajar",
					"iconcat_posicoesedirecoes"));
	/**
	 * pega o ID da imagem de uma categoria para vc usar setImageResource() no ImageView e setar imagem
	 * @param contextoDaActivity getAplicationContext() da Activity
	 * @param nomeCategoria nome de uma categoria do jogo
	 * @return o id para vc usar setImageResource passando o id para setar imagem ou -1 caso busca deu defeito
	 */
	public static int pegarIdImagemDaCategoria(Context contextoDaActivity, String nomeCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeCategoriasDoJogo.size(); i++)
		{
			String umaCategoria = nomeCategoriasDoJogo.get(i);
			if(umaCategoria.compareToIgnoreCase(nomeCategoria) == 0)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i);
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	public static int pegarIdImagemDaCategoria(Context contextoDaActivity, int idDaCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeImagensCategoria.size(); i++)
		{
			if(i + 1 == idDaCategoria)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i);
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	public static int pegarIdImagemDaCategoriaTeppo(Context contextoDaActivity, String nomeCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeCategoriasDoJogo.size(); i++)
		{
			String umaCategoria = nomeCategoriasDoJogo.get(i);
			if(umaCategoria.compareToIgnoreCase(nomeCategoria) == 0)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i) + "_teppo";
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	public static int pegarIdImagemDaCategoriaTeppo(Context contextoDaActivity, int idDaCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeImagensCategoria.size(); i++)
		{
			if(i + 1 == idDaCategoria)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i) + "_teppo";
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	public static int pegarIdImagemDaCategoriaMaior(Context contextoDaActivity, String nomeCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeCategoriasDoJogo.size(); i++)
		{
			String umaCategoria = nomeCategoriasDoJogo.get(i);
			if(umaCategoria.compareToIgnoreCase(nomeCategoria) == 0)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i) + "_maior";
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	
	
	public static int pegarIdImagemDaCategoriaMaior(Context contextoDaActivity, int idCategoria)
	{
		String nomeDaImagemAssociada = "";
		for(int i = 0; i < nomeImagensCategoria.size(); i++)
		{
			if(i + 1 == idCategoria)
			{
				nomeDaImagemAssociada = nomeImagensCategoria.get(i) + "_maior";
			}
		}
		int idImagemCategoria = -1;
		if(nomeDaImagemAssociada.length() > 0)
		{
			idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
		}
		
		return idImagemCategoria;
	}
	
	

}
