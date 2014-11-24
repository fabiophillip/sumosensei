package br.ufrn.dimap.pairg.sumosensei;

public class RetornaNomeCategoriaEscritaEmKanji 
{
	public static String retornarNomeCategoriaEscritaEmKanji(String categoria)
	{
		if(categoria.compareToIgnoreCase("Números") == 0)
		{
			return "数字";
		}
		else if(categoria.compareToIgnoreCase("Tempo") == 0)
		{
			return "時間";
		}
		else if(categoria.compareToIgnoreCase("Japão") == 0)
		{
			return "日本";
		}
		else if(categoria.compareToIgnoreCase("Adjetivos") == 0)
		{
			return "形容詞";
		}
		else if(categoria.compareToIgnoreCase("Viagem") == 0)
		{
			return "旅行";
		}
		else if(categoria.compareToIgnoreCase("Contagem") == 0)
		{
			return "数える";
		}
		else if(categoria.compareToIgnoreCase("posições e direções") == 0)
		{
			return "立場/方角";
		}
		else if(categoria.compareToIgnoreCase("Calendário") == 0)
		{
			return "カレンダー";
		}
		else if(categoria.compareToIgnoreCase("Cotidiano") == 0)
		{
			return "日常";
		}
		else
		{
			return "";
		}
	}

}
