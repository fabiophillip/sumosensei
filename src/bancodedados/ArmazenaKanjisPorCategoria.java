package bancodedados;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ArmazenaKanjisPorCategoria {
	private HashMap<String, LinkedList<KanjiTreinar>> kanjisSeparadosPorCategoria;
	private static ArmazenaKanjisPorCategoria singleton;
	
	private ArmazenaKanjisPorCategoria()
	{
		kanjisSeparadosPorCategoria = new HashMap<String, LinkedList<KanjiTreinar>>();
	}
	
	public static ArmazenaKanjisPorCategoria pegarInstancia()
	{
		if(singleton == null)
		{
			singleton = new ArmazenaKanjisPorCategoria();
		}
		return singleton;
	}
	
	/**
	 * retorna a lista de kanjis pra treinar ou null se ainda não tem uma lista com essa categoria
	 * @param categoria
	 * @return
	 */
	public LinkedList<KanjiTreinar> getListaKanjisTreinar(String categoria)
	{
		return this.kanjisSeparadosPorCategoria.get(categoria);
	}
	
	public void adicionarKanjiACategoria(String categoria, KanjiTreinar kanjiPraTreinar)
	{
		LinkedList<KanjiTreinar> kanjisDaCategoria = this.kanjisSeparadosPorCategoria.get(categoria);
		if(kanjisDaCategoria == null)
		{
			//ainda não temos nenhuma lista de kanjis da categoria solicitada.
			kanjisDaCategoria = new LinkedList<KanjiTreinar>();
			kanjisDaCategoria.add(kanjiPraTreinar);
			this.kanjisSeparadosPorCategoria.put(categoria, kanjisDaCategoria);
		}
		else
		{
			//já temos uma lista de kanjis da categoria. vamos ver se o kanji é repetido
			boolean kanjiJaEstahNaLista = false;
			for(int i = 0; i < kanjisDaCategoria.size(); i++)
			{
				String kanjiJahNaLista = kanjisDaCategoria.get(i).getKanji();
				String kanjiParaAdicionar = kanjiPraTreinar.getKanji();
				if(kanjiJahNaLista.compareTo(kanjiParaAdicionar) == 0)
				{
					kanjiJaEstahNaLista = true;
				}
			}
			
			if(kanjiJaEstahNaLista == false)
			{
				//kanji nao estah na lista, vamos add ele
				kanjisDaCategoria.add(kanjiPraTreinar);
				this.kanjisSeparadosPorCategoria.put(categoria, kanjisDaCategoria);
			}
		}
	}
	
	public LinkedList<String> getCategoriasDeKanjiArmazenadas()
	{
		Set<String> setCategoriasDeKanjis = this.kanjisSeparadosPorCategoria.keySet();
		Iterator<String> iteratorCategoriasDeKanjis = setCategoriasDeKanjis.iterator();
		LinkedList<String> categoriasDosKanjis = new LinkedList<String>();
		while(iteratorCategoriasDeKanjis.hasNext())
		{
			String umaCategoria = iteratorCategoriasDeKanjis.next();
			categoriasDosKanjis.add(umaCategoria);
		}
		
		return categoriasDosKanjis;
	}
	
	/**
	 * pega as categorias dos kanjis pra treinar de acordo com o JLPT(pega só as categorias do JLPT especificado)
	 * @param nivelJlpt nivel do jlpt em string Ex: "5", "4"...
	 * @return
	 */
	public LinkedList<String> getCategoriasDeKanjiArmazenadas(String nivelJlpt)
	{
		Set<String> setCategoriasDeKanjis = this.kanjisSeparadosPorCategoria.keySet();
		Iterator<String> iteratorCategoriasDeKanjis = setCategoriasDeKanjis.iterator();
		LinkedList<String> categoriasDosKanjis = new LinkedList<String>();
		while(iteratorCategoriasDeKanjis.hasNext())
		{
			String umaCategoria = iteratorCategoriasDeKanjis.next();
			LinkedList<KanjiTreinar> kanjisDaCategoria = this.kanjisSeparadosPorCategoria.get(umaCategoria);
			KanjiTreinar umKanjiDaCategoria = kanjisDaCategoria.getFirst();
			String jlptDaCategoria = umKanjiDaCategoria.getJlptAssociado();
			if(jlptDaCategoria.compareTo(nivelJlpt) == 0)
			{
				categoriasDosKanjis.add(umaCategoria);
			}
			
		}
		
		return categoriasDosKanjis;
	}
	
	/*com base no texto do kanji e na categoria, irei achar o objeto KanjiTreinar*/
	public KanjiTreinar acharKanji(String categoria, String kanji)
	{
		LinkedList<KanjiTreinar> kanjisDaCategoria = this.kanjisSeparadosPorCategoria.get(categoria);
		
		for(int i = 0; i < kanjisDaCategoria.size(); i++)
		{
			KanjiTreinar umKanji = kanjisDaCategoria.get(i);
			if(umKanji.getKanji().compareTo(kanji) == 0)
			{
				//achei o kanji
				return umKanji;
			}
		}
		
		return null;
		
	}
	
	public int quantasPalavrasTemACategoria(String categoria)
	{
		if(this.kanjisSeparadosPorCategoria.containsKey(categoria) == true)
		{
			LinkedList<KanjiTreinar> palavrasDaCategoria = this.kanjisSeparadosPorCategoria.get(categoria);
			return palavrasDaCategoria.size();
		}
		else
		{
			return 0;
		}
	}
	
	
			
	

}
