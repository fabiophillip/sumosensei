package lojinha;

public class TransformaPontosEmCredito {
	public static int converterPontosEmCredito(int pontosFeitosNaPartida)
	{
		if(pontosFeitosNaPartida <= 0)
		{
			return 0;
		}
		else if(pontosFeitosNaPartida <= 1000)
		{
			return 500;
		}
		else if(pontosFeitosNaPartida <= 1500)
		{
			return 1000;
		}
		else if(pontosFeitosNaPartida <= 2000)
		{
			return 2000;
		}
		else
		{
			return 3000;
		}
	}

}
