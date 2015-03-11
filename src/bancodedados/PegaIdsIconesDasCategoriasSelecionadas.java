package bancodedados;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import br.ufrn.dimap.pairg.sumosensei.app.R;

public class PegaIdsIconesDasCategoriasSelecionadas {
	private static LinkedList<String> nomeImagensCategoria =
			new LinkedList<String>(Arrays.asList("iconcat_adjetivos", "iconcat_calendario", "iconcat_contarcoisas", "iconcat_cotidiano",
					"iconcat_numerosedinheiro", "iconcat_ojapao", "iconcat_otempo", "iconcat_paraquandoforviajar",
					"iconcat_posicoesedirecoes"));
	
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadas(LinkedList<String> categoriasSelecionadas)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			String umaCategoria = categoriasSelecionadas.get(i);
			if(umaCategoria.compareTo("Adjetivos") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_adjetivos);
			}
			else if(umaCategoria.compareTo("Calendário") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_calendario);
			}
			else if(umaCategoria.compareTo("Contagem") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_contarcoisas);
			}
			else if(umaCategoria.compareTo("Cotidiano") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_cotidiano);
			}
			else if(umaCategoria.compareTo("Números") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_numerosedinheiro);
			}
			else if(umaCategoria.compareTo("Japão") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_ojapao);
			}
			else if(umaCategoria.compareTo("Tempo") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_otempo);
			}
			else if(umaCategoria.compareTo("Viagem") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_paraquandoforviajar);
			}
			else if(umaCategoria.compareTo("posições e direções") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_posicoesedirecoes);
			}
		}
		
		Integer [] arrayIdsIconesCategorias = new Integer[indicesIconesDasCategorias.size()];
		for(int j = 0; j < indicesIconesDasCategorias.size(); j++)
		{
			arrayIdsIconesCategorias[j] = indicesIconesDasCategorias.get(j);
		}
		
		return arrayIdsIconesCategorias;
		
	}
	
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadas(LinkedList<Integer> categoriasSelecionadas, Context contextoDaActivity)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			int umIdCategoria = categoriasSelecionadas.get(i);
			int idCategoriaNaLinkedListDaClasse = umIdCategoria - 1;
			String nomeDaImagemAssociada = nomeImagensCategoria.get(idCategoriaNaLinkedListDaClasse);
			int idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
			indicesIconesDasCategorias.add(idImagemCategoria);
		}
		
		Integer [] arrayIdsIconesCategorias = new Integer[indicesIconesDasCategorias.size()];
		for(int j = 0; j < indicesIconesDasCategorias.size(); j++)
		{
			arrayIdsIconesCategorias[j] = indicesIconesDasCategorias.get(j);
		}
		
		return arrayIdsIconesCategorias;
		
	}
	
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadasPraPratida(LinkedList<Integer> categoriasSelecionadas, Context contextoDaActivity)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			int umIdCategoria = categoriasSelecionadas.get(i);
			int idCategoriaNaLinkedListDaClasse = umIdCategoria - 1;
			String nomeDaImagemAssociada = nomeImagensCategoria.get(idCategoriaNaLinkedListDaClasse) + "_azul";
			int idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
			indicesIconesDasCategorias.add(idImagemCategoria);
		}
		
		Integer [] arrayIdsIconesCategorias = new Integer[indicesIconesDasCategorias.size()];
		for(int j = 0; j < indicesIconesDasCategorias.size(); j++)
		{
			arrayIdsIconesCategorias[j] = indicesIconesDasCategorias.get(j);
		}
		
		return arrayIdsIconesCategorias;
		
	}
	
	
	
	
	
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadasPequenoProTeppo(LinkedList<Integer> categoriasSelecionadas, Context contextoDaActivity)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			int umIdCategoria = categoriasSelecionadas.get(i);
			int idCategoriaNaLinkedListDaClasse = umIdCategoria - 1;
			String nomeDaImagemAssociada = nomeImagensCategoria.get(idCategoriaNaLinkedListDaClasse) + "_teppo_menor";
			int idImagemCategoria = contextoDaActivity.getResources().getIdentifier(nomeDaImagemAssociada, "drawable", contextoDaActivity.getPackageName());
			indicesIconesDasCategorias.add(idImagemCategoria);
			
		}
		
		Integer [] arrayIdsIconesCategorias = new Integer[indicesIconesDasCategorias.size()];
		for(int j = 0; j < indicesIconesDasCategorias.size(); j++)
		{
			arrayIdsIconesCategorias[j] = indicesIconesDasCategorias.get(j);
		}
		
		return arrayIdsIconesCategorias;
		
	}

}
