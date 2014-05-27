package bancodedados;

import java.io.Serializable;
import java.util.LinkedList;

public class KanjiTreinar implements Serializable {
	
	private String jlptAssociado;
	private String categoriaAssociada;
	private String kanji;
	private String traducaoEmPortugues;
	private String hiraganaDoKanji;
	private LinkedList<String> possiveisCiladasKanji;
	private int dificuldadeDoKanji;
	
	public KanjiTreinar(String jlptAssociado, String categoriaAssociada, String kanji, String traducaoEmPortugues, String hiraganaDoKanji,
			int dificuldadeDoKanji)
	{
		this(jlptAssociado, categoriaAssociada, kanji, traducaoEmPortugues, hiraganaDoKanji, new LinkedList<String>(), dificuldadeDoKanji);
	}
	
	

	public KanjiTreinar()
	{
		
	}
	
	public KanjiTreinar(String jlptAssociado, String categoriaAssociada, String kanji, String traducaoEmPortugues, 
			String hiraganaDoKanji, LinkedList<String> possiveisCiladas, int dificuldadeDoKanji)
	{
		this.jlptAssociado = jlptAssociado;
		this.categoriaAssociada = categoriaAssociada;
		this.kanji = kanji;
		this.traducaoEmPortugues = traducaoEmPortugues;
		this.hiraganaDoKanji = hiraganaDoKanji;
		this.possiveisCiladasKanji = possiveisCiladas;
		this.dificuldadeDoKanji = dificuldadeDoKanji;
	}
	
	
	
	public String getJlptAssociado() {
		return jlptAssociado;
	}



	public void setJlptAssociado(String jlptAssociado) {
		this.jlptAssociado = jlptAssociado;
	}



	public String getCategoriaAssociada() {
		return categoriaAssociada;
	}
	public void setCategoriaAssociada(String categoriaAssociada) {
		this.categoriaAssociada = categoriaAssociada;
	}
	public String getKanji() {
		return kanji;
	}
	public void setKanji(String kanji) {
		this.kanji = kanji;
	}
	public String getTraducaoEmPortugues() {
		return traducaoEmPortugues;
	}
	public void setTraducaoEmPortugues(String traducaoEmPortugues) {
		this.traducaoEmPortugues = traducaoEmPortugues;
	}
	public String getHiraganaDoKanji() {
		return hiraganaDoKanji;
	}
	public void setHiraganaDoKanji(String hiraganaDoKanji) {
		this.hiraganaDoKanji = hiraganaDoKanji;
	}
	public LinkedList<String> getPossiveisCiladasKanji() {
		return possiveisCiladasKanji;
	}
	public void setPossiveisCiladasKanji(LinkedList<String> possiveisCiladasKanji) {
		this.possiveisCiladasKanji = possiveisCiladasKanji;
	}
	public int getDificuldadeDoKanji() {
		return dificuldadeDoKanji;
	}
	public void setDificuldadeDoKanji(int dificuldadeDoKanji) {
		this.dificuldadeDoKanji = dificuldadeDoKanji;
	}

	
}
