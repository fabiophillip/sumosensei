package lojinha;

public class TransformaPontosEmCredito {
	public static int converterPontosEmCredito(int pontosFeitosNaPartida)
	{
		if(pontosFeitosNaPartida <= 0)
		{
			return 0;
		}
		else if(pontosFeitosNaPartida <= 100)
		{
			return 500;
		}
		else if(pontosFeitosNaPartida <= 150)
		{
			return 1000;
		}
		else if(pontosFeitosNaPartida <= 200)
		{
			return 2000;
		}
		else
		{
			return 3000;
		}
	}

}
