package doteppo;

import java.util.LinkedList;

public class DadosParametroPegarKanjisMenosTreinados {
	private LinkedList<String> categoriasSelecionadas;
	private String nomeDeUsuario;
	
	public LinkedList<String> getCategoriasSelecionadas() {
		return categoriasSelecionadas;
	}
	public void setCategoriasSelecionadas(LinkedList<String> categoriasSelecionadas) {
		this.categoriasSelecionadas = categoriasSelecionadas;
	}
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	
	public String getCategoriasSelecionadasSeparadasPorVirgula()
	{
		String categoriasSelecionadasEmString = "";
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			categoriasSelecionadasEmString = categoriasSelecionadasEmString + categoriasSelecionadas.get(i);
			if(i + 1 != categoriasSelecionadas.size())
			{
				categoriasSelecionadasEmString = categoriasSelecionadasEmString + ",";
			}
		}
		return categoriasSelecionadasEmString;
	}
	
	

}
