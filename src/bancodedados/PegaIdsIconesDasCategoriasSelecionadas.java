package bancodedados;

import java.util.LinkedList;

import br.ufrn.dimap.pairg.sumosensei.app.R;

public class PegaIdsIconesDasCategoriasSelecionadas {
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
	
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadasPequenoProTeppo(LinkedList<String> categoriasSelecionadas)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			String umaCategoria = categoriasSelecionadas.get(i);
			if(umaCategoria.compareTo("Adjetivos") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_adjetivos_teppo_menor);
			}
			else if(umaCategoria.compareTo("Calendário") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_calendario_teppo_menor);
			}
			else if(umaCategoria.compareTo("Contagem") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_contarcoisas_teppo_menor);
			}
			else if(umaCategoria.compareTo("Cotidiano") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_cotidiano_teppo_menor);
			}
			else if(umaCategoria.compareTo("Números") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_numerosedinheiro_teppo_menor);
			}
			else if(umaCategoria.compareTo("Japão") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_ojapao_teppo_menor);
			}
			else if(umaCategoria.compareTo("Tempo") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_otempo_teppo_menor);
			}
			else if(umaCategoria.compareTo("Viagem") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_paraquandoforviajar_teppo_menor);
			}
			else if(umaCategoria.compareTo("posições e direções") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_posicoesedirecoes_teppo_menor);
			}
		}
		
		Integer [] arrayIdsIconesCategorias = new Integer[indicesIconesDasCategorias.size()];
		for(int j = 0; j < indicesIconesDasCategorias.size(); j++)
		{
			arrayIdsIconesCategorias[j] = indicesIconesDasCategorias.get(j);
		}
		
		return arrayIdsIconesCategorias;
		
	}

}
