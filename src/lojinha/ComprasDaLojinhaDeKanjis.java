package lojinha;

import java.io.Serializable;
import java.util.LinkedList;

public class ComprasDaLojinhaDeKanjis implements Serializable {
	
	private LinkedList<MaceteKanjiParaListviewSelecionavel> macetesDeKanjisComprados;
	
	public ComprasDaLojinhaDeKanjis()
	{
		macetesDeKanjisComprados = new LinkedList<MaceteKanjiParaListviewSelecionavel>();
	}

	public LinkedList<MaceteKanjiParaListviewSelecionavel> getMacetesDeKanjisComprados() {
		return macetesDeKanjisComprados;
	}

	public void setMacetesDeKanjisComprados(
			LinkedList<MaceteKanjiParaListviewSelecionavel> labelsMacetesDeKanjisComprados) {
		this.macetesDeKanjisComprados = labelsMacetesDeKanjisComprados;
	}
	
	public void adicionarMaceteDeKanjiComprado(MaceteKanjiParaListviewSelecionavel novoMaceteComprado)
	{
		this.macetesDeKanjisComprados.add(novoMaceteComprado);
	}
	
	public LinkedList<String> getLabelsMacetesDeKanjisComprados()
	{
		LinkedList<String> labelsMacetesDeKanjisComprados = new LinkedList<String>();
		if(macetesDeKanjisComprados != null)
		{
			for(int i = 0; i < macetesDeKanjisComprados.size(); i++)
			{
				String umaLabelKanjiComprado = macetesDeKanjisComprados.get(i).getLabelKanji();
				labelsMacetesDeKanjisComprados.add(umaLabelKanjiComprado);
			}
		}
		
		return labelsMacetesDeKanjisComprados;
	}
	
	public boolean usuarioJahComprouOMacete(String labelMaceteDoKanjiNaLojinha)
	{
		LinkedList<String> labelsMacetesDeKanjisComprados = this.getLabelsMacetesDeKanjisComprados();
		for(int i = 0; i < labelsMacetesDeKanjisComprados.size(); i++)
		{
			String umaLabelComprado = labelsMacetesDeKanjisComprados.get(i);
			umaLabelComprado = umaLabelComprado.replaceAll("\\s+","");
			labelMaceteDoKanjiNaLojinha = labelMaceteDoKanjiNaLojinha.replaceAll("\\s+","");
			
			if(umaLabelComprado.contains(labelMaceteDoKanjiNaLojinha))
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	
	

}
