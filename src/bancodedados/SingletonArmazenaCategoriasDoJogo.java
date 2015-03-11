package bancodedados;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/*singleton que armazena as categorias usadas no jogo relacionadas aos seus nomes*/
public class SingletonArmazenaCategoriasDoJogo 
{
	private HashMap<String,Categoria> categoriasESeusNomes;
	private HashMap<Integer, Categoria> categoriasESeusIds;
	private static SingletonArmazenaCategoriasDoJogo instancia;
	
	private SingletonArmazenaCategoriasDoJogo()
	{
		categoriasESeusNomes = new HashMap<String,Categoria>();
		categoriasESeusIds = new HashMap<Integer, Categoria>();
	}
	
	public static SingletonArmazenaCategoriasDoJogo getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonArmazenaCategoriasDoJogo();
		}
		
		return instancia;
	}
	
	public void limparListas()
	{
		categoriasESeusIds.clear();
		categoriasESeusNomes.clear();
	}

	

	
	public void armazenarNovaCategoria(String nome, Categoria categoria)
	{
		if(this.categoriasESeusNomes.containsKey(nome) == false)
		{
			this.categoriasESeusNomes.put(nome, categoria);
			this.categoriasESeusIds.put(categoria.getId(), categoria);
		}
	}
	
	public int pegarIdDaCategoria(String nomeDaCategoria)
	{
		return this.categoriasESeusNomes.get(nomeDaCategoria).getId();
	}
	
	public String pegarNomeCategoria(int idDaCategoria)
	{
		return this.categoriasESeusIds.get(idDaCategoria).getNome();
	}
	
	public LinkedList<Integer> pegarIdsCategorias(LinkedList<String> categoriasSelecionadas)
	{
		LinkedList<Integer> idsCategorias = new LinkedList<Integer>();
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			String umaCategoriaSelecionada = categoriasSelecionadas.get(i);
			Categoria objetoCategoriaDaCategoriaSelecionada = this.categoriasESeusNomes.get(umaCategoriaSelecionada);
			if(objetoCategoriaDaCategoriaSelecionada!= null)
			{
				int idDaCategoriaSelecionada = objetoCategoriaDaCategoriaSelecionada.getId();
				idsCategorias.add(idDaCategoriaSelecionada);
				
			}
		}
		return idsCategorias;
	}
	
	public LinkedList<String> pegarNomesCategorias()
	{
		LinkedList<String> categorias = new LinkedList<String>();
		Set<String> keyset = this.categoriasESeusNomes.keySet();
		Iterator<String> iteraCategorias = keyset.iterator();
		while(iteraCategorias.hasNext())
		{
			String umaCategoria = iteraCategorias.next();
			categorias.add(umaCategoria);
			
		}
		
		return categorias;
	}
	
	public String pegarNomesCategoriasSeparadosPorString()
	{
		String categorias = "";
		Set<String> keyset = this.categoriasESeusNomes.keySet();
		Iterator<String> iteraCategorias = keyset.iterator();
		while(iteraCategorias.hasNext())
		{
			String umaCategoria = iteraCategorias.next();
			categorias = categorias + umaCategoria;
			if(iteraCategorias.hasNext())
			{
				//adicionar virgula
				categorias = categorias + ",";
			}
			
		}
		
		return categorias;
	}
	
	public String pegarIdsCategoriasSeparadosPorString(LinkedList<String> categoriasSelecionadas)
	{
		String idsCategoriasSeparadosPorVirgula = "";
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			String umaCategoriaSelecionada = categoriasSelecionadas.get(i);
			Categoria objetoCategoriaDaCategoriaSelecionada = this.categoriasESeusNomes.get(umaCategoriaSelecionada);
			if(objetoCategoriaDaCategoriaSelecionada!= null)
			{
				int idDaCategoriaSelecionada = objetoCategoriaDaCategoriaSelecionada.getId();
				idsCategoriasSeparadosPorVirgula = idsCategoriasSeparadosPorVirgula + idDaCategoriaSelecionada;
				if(i != categoriasSelecionadas.size() - 1)
				{
					//adicionar virgula
					idsCategoriasSeparadosPorVirgula = idsCategoriasSeparadosPorVirgula + ",";
				}
			}
		}
		return idsCategoriasSeparadosPorVirgula;
	}
	
	
}
