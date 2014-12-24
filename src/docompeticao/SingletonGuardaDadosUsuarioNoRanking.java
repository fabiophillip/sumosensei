package docompeticao;

public class SingletonGuardaDadosUsuarioNoRanking {
	
	private static SingletonGuardaDadosUsuarioNoRanking instancia;
	private DadosUsuarioNoRanking dadosSalvosUsuarioNoRanking;
	private String tituloDoJogadorCalculadoRecentemente;
	
	public static SingletonGuardaDadosUsuarioNoRanking getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonGuardaDadosUsuarioNoRanking();
		}
		
		return instancia;
	}

	public DadosUsuarioNoRanking getDadosSalvosUsuarioNoRanking() {
		return dadosSalvosUsuarioNoRanking;
	}

	public void setDadosSalvosUsuarioNoRanking(
			DadosUsuarioNoRanking dadosSalvosUsuarioNoRanking) {
		this.dadosSalvosUsuarioNoRanking = dadosSalvosUsuarioNoRanking;
	}

	public String getTituloDoJogadorCalculadoRecentemente() {
		return tituloDoJogadorCalculadoRecentemente;
	}

	public void setTituloDoJogadorCalculadoRecentemente(
			String tituloDoJogadorCalculadoRecentemente) {
		this.tituloDoJogadorCalculadoRecentemente = tituloDoJogadorCalculadoRecentemente;
	}
	
	
	

}
