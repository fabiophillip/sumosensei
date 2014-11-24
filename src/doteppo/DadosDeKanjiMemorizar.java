package doteppo;

public class DadosDeKanjiMemorizar {
	private String kanjiMemorizar;
	private String hiraganaMemorizar;
	private String traducaoMemorizar;
	
	public DadosDeKanjiMemorizar(String kanjiMemorizar, String hiraganaMemorizar, String traducaoMemorizar)
	{
		this.kanjiMemorizar = kanjiMemorizar;
		this.hiraganaMemorizar = hiraganaMemorizar;
		this.traducaoMemorizar = traducaoMemorizar;
	}
	
	public String getKanjiMemorizar() {
		return kanjiMemorizar;
	}
	public void setKanjiMemorizar(String kanjiMemorizar) {
		this.kanjiMemorizar = kanjiMemorizar;
	}
	public String getHiraganaMemorizar() {
		return hiraganaMemorizar;
	}
	public void setHiraganaMemorizar(String hiraganaMemorizar) {
		this.hiraganaMemorizar = hiraganaMemorizar;
	}
	public String getTraducaoMemorizar() {
		return traducaoMemorizar;
	}
	public void setTraducaoMemorizar(String traducaoMemorizar) {
		this.traducaoMemorizar = traducaoMemorizar;
	}
	

}
