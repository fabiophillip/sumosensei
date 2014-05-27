package armazenamentointerno;

import java.util.LinkedList;

import android.content.Context;
import bancodedados.KanjiTreinar;

public interface DAOAcessaHistoricoDePartidas 
{
	/**
	 * CHAMAR QUANDO QUISER ARMAZENAR DADOS DE UMA PARTIDA
	 * @param categoriasTreinadasNaPartida
	 * @param kanjisTreinadosNaPartida
	 * @param kanjisErradosNaPartida
	 * @param pontuacaoNaPartida
	 * @param itensUsadosNaPartida
	 * @param context geralmente é getApplicationContext() se vc estah em uma Activity
	 */
	public void inserirDadosDeNovaPartida(LinkedList<String> categoriasTreinadasNaPartida, 
			LinkedList<KanjiTreinar> kanjisTreinadosNaPartida, LinkedList<KanjiTreinar> kanjisErradosNaPartida,
			int pontuacaoNaPartida, LinkedList<String> itensUsadosNaPartida, Context context);
	
	public LinkedList<DadosDePartida> getDadosDePartidasAnteriores(Context context); 

}
