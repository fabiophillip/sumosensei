package com.phiworks.sumosenseinew;

public class RetornaNomeCategoriaEscritaEmKanji 
{
	public static String retornarNomeCategoriaEscritaEmKanji(String categoria)
	{
		if(categoria.compareToIgnoreCase("Números e dinheiro") == 0)
		{
			return "数字とお金";
		}
		else if(categoria.compareToIgnoreCase("o Tempo") == 0)
		{
			return "時間";
		}
		else if(categoria.compareToIgnoreCase("o Japão") == 0)
		{
			return "日本";
		}
		else if(categoria.compareToIgnoreCase("adjetivos") == 0)
		{
			return "形容詞";
		}
		else if(categoria.compareToIgnoreCase("para quando for viajar") == 0)
		{
			return "旅行する時";
		}
		else if(categoria.compareToIgnoreCase("contar coisas") == 0)
		{
			return "物を数える";
		}
		else if(categoria.compareToIgnoreCase("posições e direções") == 0)
		{
			return "立場と方角";
		}
		else if(categoria.compareToIgnoreCase("calendário") == 0)
		{
			return "カレンダー";
		}
		else if(categoria.compareToIgnoreCase("cotidiano") == 0)
		{
			return "日常";
		}
		else
		{
			return "";
		}
	}

}
