package com.phiworks.dapartida;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;

public class GuardaDadosDaPartida {
	
	private static GuardaDadosDaPartida guardaPartida;
	private HashMap<String, LinkedList<KanjiTreinar>> kanjisDaPartidaSeparadosPorCategoria;
	private LinkedList<KanjiTreinar> kanjisTreinadosNaPartida;
	private LinkedList<KanjiTreinar> kanjisQueDeixouDeAcertarNaPartida;
	private LinkedList<KanjiTreinar> kanjisAcertadosNaPartida;
	private LinkedList<KanjiTreinar> kanjisErradosNaPartida;
	private int posicaoSumozinhoDoJogadorNaArena;
	private int placarDoJogadorNaPartida = 0;
	private LinkedList<String> listaItensUsadosDurantePartida;
	
	private GuardaDadosDaPartida()
	{
		this.kanjisDaPartidaSeparadosPorCategoria = new HashMap<String, LinkedList<KanjiTreinar>>();
	}
	
	
	
	
	public int getPlacarDoJogadorNaPartida() {
		return placarDoJogadorNaPartida;
	}


	public LinkedList<String> getListaItensUsadosDurantePartida() {
		return listaItensUsadosDurantePartida;
	}

	



	public LinkedList<KanjiTreinar> getKanjisQueDeixouDeAcertarNaPartida() {
		return kanjisQueDeixouDeAcertarNaPartida;
	}




	public void adicionarPontosPlacarDoJogadorNaPartida(int pontosAdicionar) {
		this.placarDoJogadorNaPartida = this.placarDoJogadorNaPartida + pontosAdicionar;
	}




	public int getPosicaoSumozinhoDoJogadorNaTela() {
		return posicaoSumozinhoDoJogadorNaArena;
	}


	public void setPosicaoSumozinhoDoJogadorNaTela(
			int posicaoSumozinhoDoJogadorNaTela) {
		this.posicaoSumozinhoDoJogadorNaArena = posicaoSumozinhoDoJogadorNaTela;
	}


	public static GuardaDadosDaPartida getInstance()
	{
		if(guardaPartida == null)
		{
			guardaPartida = new GuardaDadosDaPartida();
		}
		
		return guardaPartida;
	}
	
	public void limparDadosPartida()
	{
		//limpa o hashmap de categorias selecionadas para a partida
		this.kanjisDaPartidaSeparadosPorCategoria = new HashMap<String, LinkedList<KanjiTreinar>>();
		//limpa os kanjis selecionados para treino na partida
		this.kanjisTreinadosNaPartida = new LinkedList<KanjiTreinar>();
		this.kanjisQueDeixouDeAcertarNaPartida = new LinkedList<KanjiTreinar>();
		this.posicaoSumozinhoDoJogadorNaArena = 0;
		this.placarDoJogadorNaPartida = 0;
		this.listaItensUsadosDurantePartida = new LinkedList<String>();
		this.kanjisAcertadosNaPartida = new LinkedList<KanjiTreinar>();
		this.kanjisErradosNaPartida = new LinkedList<KanjiTreinar>();
		
	}
	
	public void setCategoriasSelecionadasPraPartida(LinkedList<String> categoriasPartida)
	{
		
		for(int i = 0; i < categoriasPartida.size(); i++)
		{
			String umaCategoriaDaPartida = categoriasPartida.get(i);
			LinkedList<KanjiTreinar> kanjisDaCategoria = ArmazenaKanjisPorCategoria.pegarInstancia().getListaKanjisTreinar(umaCategoriaDaPartida);
			this.kanjisDaPartidaSeparadosPorCategoria.put(umaCategoriaDaPartida, kanjisDaCategoria);
		}
	}
	
	/**
	 * assumindo que as categorias do treino já foram setadas, ele irá pegar um kanji qualquer para treinar.
	 * @return null se nao conseguimos achar uma categoria aleatoria do kanji a treinar ou um kanji aleatorio em caso de sucesso
	 */
	public KanjiTreinar getUmKanjiAleatorioParaTreinar()
	{
		Set<String> categoriasDosKanjisATreinar = this.kanjisDaPartidaSeparadosPorCategoria.keySet();
		String categoriaDoKanjiATreinar = "";
		int percorredorCategoria = 0;
		int posicaoDaCategoriaAleatoria = new Random().nextInt(categoriasDosKanjisATreinar.size());
		for(String umaCategoria : categoriasDosKanjisATreinar)
		{
			if(percorredorCategoria == posicaoDaCategoriaAleatoria)
			{
				categoriaDoKanjiATreinar = umaCategoria;
			}
		}
		
		if(categoriaDoKanjiATreinar != null && categoriaDoKanjiATreinar.length() > 0)
		{
			LinkedList<KanjiTreinar> kanjisPraTreinar = this.kanjisDaPartidaSeparadosPorCategoria.get(categoriaDoKanjiATreinar);
			Collections.shuffle(kanjisPraTreinar);
			Collections.shuffle(kanjisPraTreinar);
			KanjiTreinar kanjiAleatorioParaTreinar = kanjisPraTreinar.getFirst();
			return kanjiAleatorioParaTreinar;
		}
		else
		{
			return null;
		}
	}
	
	public void adicionarKanjiAoTreinoDaPartida(KanjiTreinar kanjiEscolhido)
	{
		this.kanjisTreinadosNaPartida.add(kanjiEscolhido);
	}
	public void adicionarKanjiAosKanjisQueDeixouDeAcertar(KanjiTreinar kanjiDeixouDeAcertar)
	{
		this.kanjisQueDeixouDeAcertarNaPartida.add(kanjiDeixouDeAcertar);
	}
	public void adicionarItemAListaDeItensUsados(String nomeItemUsado)
	{
		this.listaItensUsadosDurantePartida.add(nomeItemUsado);
	}
	
	/**
	 * @param kanjiRespostaCorreta o kanji correto que responde a pergunta da partida
	 * @return uma lista com outros 2 kanjis similares a resposta correta para servir de pegadinha
	 */
	public LinkedList<String> getHiraganasAleatoriosSimilares(KanjiTreinar kanjiRespostaCorreta)
	{
		LinkedList<String> kanjisAleatoriosSimilares = new LinkedList<String>();
		String categoriaDoKanjiRespostaCorreta = kanjiRespostaCorreta.getCategoriaAssociada();
		LinkedList<KanjiTreinar> kanjisDaMesmaCategoria = this.kanjisDaPartidaSeparadosPorCategoria.get(categoriaDoKanjiRespostaCorreta);
		int tamanhoDoHiraganaCorreto = kanjiRespostaCorreta.getHiraganaDoKanji().length();
		Collections.shuffle(kanjisDaMesmaCategoria);
		Collections.shuffle(kanjisDaMesmaCategoria);
		
		for(int i = 0; i < kanjisDaMesmaCategoria.size(); i++)
		{
			KanjiTreinar umKanjiMesmaCategoria = kanjisDaMesmaCategoria.get(i);
			String hiraganaDeUmKanjiMesmaCategoria = umKanjiMesmaCategoria.getHiraganaDoKanji();
			String hiraganaKanjiRespostaCorreta = kanjiRespostaCorreta.getHiraganaDoKanji();
			if(hiraganaDeUmKanjiMesmaCategoria.length() == tamanhoDoHiraganaCorreto &&
					hiraganaKanjiRespostaCorreta.compareTo(hiraganaDeUmKanjiMesmaCategoria) != 0)
			{
				//o hiragana tem mesmo tamanho e mesma categoria? eh uma possivel cilada
				kanjisAleatoriosSimilares.add(umKanjiMesmaCategoria.getHiraganaDoKanji());
				if(kanjisAleatoriosSimilares.size() == 2)
				{
					//soh precisamos de dois kanjis aleatorios para servir de outras alternativas...
					break;
				}
			}
		}
		
		//ainda nao temos 2 alternativas aleatorias? vamos pegar o resto dos kanjis de mesma categoria...
		while(kanjisAleatoriosSimilares.size() < 2)
		{
			Collections.shuffle(kanjisDaMesmaCategoria);
			kanjisAleatoriosSimilares.add(kanjisDaMesmaCategoria.getFirst().getHiraganaDoKanji());
		}
		
		return kanjisAleatoriosSimilares;
		
		
	}
	
	public LinkedList<KanjiTreinar> getKanjisTreinadosNaPartida()
	{
		return this.kanjisTreinadosNaPartida;
	}
	
	
	public KanjiTreinar encontrarKanjiTreinadoNaPartida(String categoriaDoKanji, String textoKanji)
	{
		LinkedList<KanjiTreinar> kanjisDaCategoria = this.kanjisDaPartidaSeparadosPorCategoria.get(categoriaDoKanji);
		for(int i = 0; i < kanjisDaCategoria.size(); i++)
		{
			KanjiTreinar umKanjiDaCategoria = kanjisDaCategoria.get(i);
			if(umKanjiDaCategoria.getKanji().compareTo(textoKanji) == 0)
			{
				return umKanjiDaCategoria;
			}
		}
		
		return null;
	}
	
	public LinkedList<String> getCategoriasTreinadasNaPartida()
	{
		LinkedList<String> categoriasTreinadasNaPartida = new LinkedList<String>();
		Set<String> categorias = this.kanjisDaPartidaSeparadosPorCategoria.keySet();
		for(String umaCategoria : categorias)
		{
			categoriasTreinadasNaPartida.add(umaCategoria);
		}
		
		return categoriasTreinadasNaPartida;
		
	}
	
	public void adicionarKanjiAcertadoNaPartida(KanjiTreinar kanjiAcertado)
	{
		this.kanjisAcertadosNaPartida.add(kanjiAcertado);
	}




	public LinkedList<KanjiTreinar> getKanjisAcertadosNaPartida() {
		return kanjisAcertadosNaPartida;
	}
	
	public void adicionarKanjiErradoNaPartida(KanjiTreinar kanjiErrado)
	{
		this.kanjisErradosNaPartida.add(kanjiErrado);
	}




	public LinkedList<KanjiTreinar> getKanjisErradosNaPartida() {
		return kanjisErradosNaPartida;
	}
	
	
	
	
	

}
