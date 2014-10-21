package lojinha;

public class TransformaPontosEmCredito {
	public static int converterPontosEmCredito(int pontosFeitosNaPartida)
	{
		int creditoGanho;
		if(pontosFeitosNaPartida < 10)
		{
			creditoGanho = 0;
		}
		else if(pontosFeitosNaPartida < 20)
		{
			creditoGanho = 500;
		}
		else if(pontosFeitosNaPartida < 30)
		{
			creditoGanho = 1000;
		}
		else if(pontosFeitosNaPartida < 40)
		{
			creditoGanho = 2000;
		}
		else
		{
			creditoGanho = 3000;
		}
		
		int unidadesAdicionarAoCredito = (pontosFeitosNaPartida % 10) * 10;
		creditoGanho = creditoGanho + unidadesAdicionarAoCredito;
		return creditoGanho;
	}

}
