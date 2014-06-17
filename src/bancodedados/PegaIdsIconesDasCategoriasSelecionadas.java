package bancodedados;

import java.util.LinkedList;

import com.phiworks.sumosenseinew.R;

public class PegaIdsIconesDasCategoriasSelecionadas {
	public static Integer [] pegarIndicesIconesDasCategoriasSelecionadas(LinkedList<String> categoriasSelecionadas)
	{
		LinkedList<Integer> indicesIconesDasCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			String umaCategoria = categoriasSelecionadas.get(i);
			if(umaCategoria.compareTo("adjetivos") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_adjetivos);
			}
			else if(umaCategoria.compareTo("calendário") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_calendario);
			}
			else if(umaCategoria.compareTo("contar coisas") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_contar_coisas);
			}
			else if(umaCategoria.compareTo("cotidiano") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_cotidiano);
			}
			else if(umaCategoria.compareTo("Números e dinheiro") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_numeros_e_dinheiro);
			}
			else if(umaCategoria.compareTo("o Japão") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_o_japao);
			}
			else if(umaCategoria.compareTo("o tempo") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_o_tempo);
			}
			else if(umaCategoria.compareTo("para quando for viajar") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_para_quando_for_viajar);
			}
			else if(umaCategoria.compareTo("posições e direções") == 0)
			{
				indicesIconesDasCategorias.add(R.drawable.categoria_posicoes_e_direcoes);
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
