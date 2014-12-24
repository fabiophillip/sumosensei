package docompeticao;

public class DadosUsuarioNoRanking {
	private String idUsuario;
	private int pontuacaoTotal;
	private int vitoriasCompeticao;
	private int derrotasCompeticao;
	private int posicaoJogadorNoRanking;//posicao dele no ranking
	
	public DadosUsuarioNoRanking()
	{
		
	}
	
	public DadosUsuarioNoRanking(String idUsuario, int pontuacaoAtual, int vitoriasCompeticao, int derrotasCompeticao, int position)
	{
		this.idUsuario = idUsuario;
		this.pontuacaoTotal = pontuacaoAtual;
		this.vitoriasCompeticao = vitoriasCompeticao;
		this.derrotasCompeticao = derrotasCompeticao;
		this.posicaoJogadorNoRanking = position;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}
	public void setPontuacaoTotal(int pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}
	public int getVitoriasCompeticao() {
		return vitoriasCompeticao;
	}
	public void setVitoriasCompeticao(int vitoriasCompeticao) {
		this.vitoriasCompeticao = vitoriasCompeticao;
	}
	public int getDerrotasCompeticao() {
		return derrotasCompeticao;
	}
	public void setDerrotasCompeticao(int derrotasCompeticao) {
		this.derrotasCompeticao = derrotasCompeticao;
	}

	public int getPosicaoJogadorNoRanking() {
		return posicaoJogadorNoRanking;
	}

	public void setPosicaoJogadorNoRanking(int posicaoJogadorNoRanking) {
		this.posicaoJogadorNoRanking = posicaoJogadorNoRanking;
	}
	
	
	
	

}
