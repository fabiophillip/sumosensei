package lojinha;

import android.content.Context;

import com.phiworks.sumosenseinew.R;

public class CorrigeNomesDosMacetesComAcento {
	
	public static String corrigirNomesMaceteInserirAcento(
			String nomeUrlMaceteSemNomeMacete, Context contextoAplicacao) {
		
		if(nomeUrlMaceteSemNomeMacete.contains("te_mao"))
		{
			nomeUrlMaceteSemNomeMacete = "te_" + contextoAplicacao.getResources().getString(R.string.label_mao); 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("ocha_cha"))
		{
			nomeUrlMaceteSemNomeMacete = "ocha_" + contextoAplicacao.getResources().getString(R.string.label_cha); 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("akachan_bebe"))
		{
			nomeUrlMaceteSemNomeMacete = "akachan_" + contextoAplicacao.getResources().getString(R.string.label_bebe); 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("atama_cabeca"))
		{
			nomeUrlMaceteSemNomeMacete = "atama_" + contextoAplicacao.getResources().getString(R.string.label_cabeca); 
		}
		return nomeUrlMaceteSemNomeMacete;
	}
	
	public static String corrigirNomesMaceteRemoverAcento(
			String nomeUrlMaceteSemNomeMacete, Context contextoAplicacao) {
		
		if(nomeUrlMaceteSemNomeMacete.contains("te_mão"))
		{
			nomeUrlMaceteSemNomeMacete = "te_mao"; 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("ocha_chá"))
		{
			nomeUrlMaceteSemNomeMacete = "ocha_cha"; 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("akachan_bebê"))
		{
			nomeUrlMaceteSemNomeMacete = "akachan_bebe"; 
		}
		else if(nomeUrlMaceteSemNomeMacete.contains("atama_cabeça"))
		{
			nomeUrlMaceteSemNomeMacete = "atama_cabeca"; 
		}
		return nomeUrlMaceteSemNomeMacete;
	}

}
