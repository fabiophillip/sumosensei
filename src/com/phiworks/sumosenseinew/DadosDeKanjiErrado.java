package com.phiworks.sumosenseinew;

public class DadosDeKanjiErrado {
	private String kanjiErrado;
	private String traducaoErrada;
	private String hiraganaErrado;
	private int quantasVezesKanjiFoiErrado;
	
	public DadosDeKanjiErrado()
	{
		
	}
	
	public DadosDeKanjiErrado(String kanjiErrou, String traducaoErrou, String hiraganaErrou, int quantasVezesErrou)
	{
		this.kanjiErrado = kanjiErrou;
		this.traducaoErrada = traducaoErrou;
		this.hiraganaErrado = hiraganaErrou;
		this.quantasVezesKanjiFoiErrado = quantasVezesErrou;
	}
	
	public String getKanjiErrado() {
		return kanjiErrado;
	}
	public void setKanjiErrado(String kanjiErrado) {
		this.kanjiErrado = kanjiErrado;
	}
	public String getTraducaoErrada() {
		return traducaoErrada;
	}
	public void setTraducaoErrada(String traducaoErrada) {
		this.traducaoErrada = traducaoErrada;
	}
	public String getHiraganaErrado() {
		return hiraganaErrado;
	}
	public void setHiraganaErrado(String hiraganaErrado) {
		this.hiraganaErrado = hiraganaErrado;
	}
	public int getQuantasVezesKanjiFoiErrado() {
		return quantasVezesKanjiFoiErrado;
	}
	public void setQuantasVezesKanjiFoiErrado(int quantasVezesKanjiFoiErrado) {
		this.quantasVezesKanjiFoiErrado = quantasVezesKanjiFoiErrado;
	}
	
	

}
