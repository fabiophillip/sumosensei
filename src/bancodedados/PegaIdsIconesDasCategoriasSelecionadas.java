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
			else if(umaCategoria.compareTo("Calend�rio") == 0)
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
			else if(umaCategoria.compareTo("N�meros") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.iconcat_numerosedinheiro);
			}
			else if(umaCategoria.compareTo("Jap�o") == 0)
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
			else if(umaCategoria.compareTo("posi��es e dire��es") == 0)
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

}
