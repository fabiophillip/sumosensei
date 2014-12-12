package bancodedados;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/*singleton que armazena as categorias usadas no jogo relacionadas aos seus nomes*/
public class SingletonArmazenaCategoriasDoJogo 
{
	private HashMap<String,Categoria> categoriasESeusNomes;
	private static SingletonArmazenaCategoriasDoJogo instancia;
	
	private SingletonArmazenaCategoriasDoJogo()
	{
		categoriasESeusNomes = new HashMap<String,Categoria>();
	}
	
	public static SingletonArmazenaCategoriasDoJogo getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonArmazenaCategoriasDoJogo();
		}
		
		return instancia;
	}

	public HashMap<String, Categoria> getCategoriasESeusNomes() {
		return categoriasESeusNomes;
	}

	public void setCategoriasESeusNomes(
			HashMap<String, Categoria> categoriasESeusNomes) {
		this.categoriasESeusNomes = categoriasESeusNomes;
	}
	
	public void armazenarNovaCategoria(String nome, Categoria categoria)
	{
		if(this.categoriasESeusNomes.containsKey(nome) == false)
		{
			this.categoriasESeusNomes.put(nome, categoria);
		}
	}
	
	public int pegarIdDaCategoria(String nomeDaCategoria)
	{
		return this.categoriasESeusNomes.get(nomeDaCategoria).getId();
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
