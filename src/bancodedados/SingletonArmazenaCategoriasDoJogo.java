package bancodedados;

import java.util.HashMap;

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
}
