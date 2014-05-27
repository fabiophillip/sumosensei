package armazenamentointerno;

import java.io.Serializable;
import java.util.LinkedList;

import bancodedados.KanjiTreinar;


//LEMBRAR: KANJITREINO TEM DE IMPLEMENTAR SERIALIZABLE TB!!!!
//LEMBRAR: KANJITREINO TEM DE IMPLEMENTAR SERIALIZABLE TB!!!!
//LEMBRAR: KANJITREINO TEM DE IMPLEMENTAR SERIALIZABLE TB!!!!
//LEMBRAR: KANJITREINO TEM DE IMPLEMENTAR SERIALIZABLE TB!!!!
//LEMBRAR: KANJITREINO TEM DE IMPLEMENTAR SERIALIZABLE TB!!!!

public class DadosDePartida implements Serializable {
	
	private LinkedList<String> categoriasTreinadasNaPartida;
	private LinkedList<KanjiTreinar> kanjisTreinadosNaPartida;
	private LinkedList<KanjiTreinar> kanjisErradosNaPartida;
	private int pontuacaoNaPartida;
	private LinkedList<String> itensUsadosNaPartida;
	
	
	public LinkedList<String> getCategoriasTreinadasNaPartida() {
		return categoriasTreinadasNaPartida;
	}
	public void setCategoriasTreinadasNaPartida(
			LinkedList<String> categoriasTreinadasNaPartida) {
		this.categoriasTreinadasNaPartida = categoriasTreinadasNaPartida;
	}
	
	public LinkedList<KanjiTreinar> getKanjisTreinadosNaPartida() {
		return kanjisTreinadosNaPartida;
	}
	public void setKanjisTreinadosNaPartida(
			LinkedList<KanjiTreinar> kanjisTreinadosNaPartida) {
		this.kanjisTreinadosNaPartida = kanjisTreinadosNaPartida;
	}
	public LinkedList<KanjiTreinar> getKanjisErradosNaPartida() {
		return kanjisErradosNaPartida;
	}
	public void setKanjisErradosNaPartida(
			LinkedList<KanjiTreinar> kanjisErradosNaPartida) {
		this.kanjisErradosNaPartida = kanjisErradosNaPartida;
	}
	public int getPontuacaoNaPartida() {
		return pontuacaoNaPartida;
	}
	public void setPontuacaoNaPartida(int pontuacaoNaPartida) {
		this.pontuacaoNaPartida = pontuacaoNaPartida;
	}
	public LinkedList<String> getItensUsadosNaPartida() {
		return itensUsadosNaPartida;
	}
	public void setItensUsadosNaPartida(LinkedList<String> itensUsadosNaPartida) {
		this.itensUsadosNaPartida = itensUsadosNaPartida;
	}
	
}
