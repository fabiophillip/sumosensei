package docompeticao;

public class DadosDeRanking {
	
	private String posicaoNoRanking;
	private String nomeUsuario;
	private String quantidadeDeVitorias;
	private String quantidadeDeDerrotas;
	private String tituloUsuario;
	
	public DadosDeRanking()
	{
		
	}
	public DadosDeRanking(String posicaoRank, String username, String titulo, String quantVitorias, String quantDerrotas)
	{
		this.posicaoNoRanking = posicaoRank;
		this.nomeUsuario = username;
		this.tituloUsuario = titulo;
		this.quantidadeDeVitorias = quantVitorias;
		this.quantidadeDeDerrotas = quantDerrotas;
	}
	
	public String getPosicaoNoRanking() {
		return posicaoNoRanking;
	}
	public void setPosicaoNoRanking(String posicaoNoRanking) {
		this.posicaoNoRanking = posicaoNoRanking;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public String getTituloUsuario() {
		return tituloUsuario;
	}
	public void setTituloUsuario(String tituloUsuario) {
		this.tituloUsuario = tituloUsuario;
	}
	public String getQuantidadeDeVitorias() {
		return quantidadeDeVitorias;
	}
	public void setQuantidadeDeVitorias(String quantidadeDeVitorias) {
		this.quantidadeDeVitorias = quantidadeDeVitorias;
	}
	public String getQuantidadeDeDerrotas() {
		return quantidadeDeDerrotas;
	}
	public void setQuantidadeDeDerrotas(String quantidadeDeDerrotas) {
		this.quantidadeDeDerrotas = quantidadeDeDerrotas;
	}
	
	
	

}
