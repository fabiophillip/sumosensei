package com.phiworks.dapartida;


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;

public class GuardaDadosDaPartida {
	
	private static GuardaDadosDaPartida guardaPartida;
	private HashMap<String, LinkedList<KanjiTreinar>> kanjisDaPartidaSeparadosPorCategoria;
	private LinkedList<KanjiTreinar> kanjisTreinadosNaPartida;
	private LinkedList<KanjiTreinar> kanjisAcertadosNaPartida;
	private LinkedList<KanjiTreinar> kanjisErradosNaPartida;
	private int posicaoSumozinhoDoJogadorNaArena;
	private int placarDoJogadorNaPartida = 0;
	private LinkedList<String> listaItensUsadosDurantePartida;
	
	//referente aos itens durante a  partida
	private LinkedList<String> itensNoInventarioDoJogador;
	private LinkedList<String> itensIncorporadosPeloJogador;// itens que o jogador incorporou durante a partida
	private static String [] itensDoJogo = {"chikaramizu", "shiko", "tegata", "teppotree"};
	private static String [] itensDoJogoParaQuemQuaseGanha = {"shiko", "teppotree"};
	//private static String [] itensDoJogo = {"chikaramizu", "teppotree"};
	//private static String [] itensDoJogoParaQuemQuaseGanha = {"chikaramizu", "teppotree"};
	
	private int roundDaPartida;
	
	private boolean shikoFoiUsado;
	
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

	



	




	public void adicionarPontosPlacarDoJogadorNaPartida(int pontosAdicionar) {
		this.placarDoJogadorNaPartida = this.placarDoJogadorNaPartida + pontosAdicionar;
	}




	public int getPosicaoSumozinhoDoJogadorNaTela() {
		return posicaoSumozinhoDoJogadorNaArena;
	}


	public void setPosicaoSumozinhoDoJogadorNaTela(
			int posicaoSumozinhoDoJogadorNaTela) {
		this.posicaoSumozinhoDoJogadorNaArena = posicaoSumozinhoDoJogadorNaTela;
		//só há 6 posicoes...
		if(this.posicaoSumozinhoDoJogadorNaArena > 6)
		{
			this.posicaoSumozinhoDoJogadorNaArena = 6;
		}
		if(this.posicaoSumozinhoDoJogadorNaArena < -6)
		{
			this.posicaoSumozinhoDoJogadorNaArena = -6;
		}
		
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
		this.posicaoSumozinhoDoJogadorNaArena = 0;
		this.placarDoJogadorNaPartida = 0;
		this.listaItensUsadosDurantePartida = new LinkedList<String>();
		this.kanjisAcertadosNaPartida = new LinkedList<KanjiTreinar>();
		this.kanjisErradosNaPartida = new LinkedList<KanjiTreinar>();
		this.itensNoInventarioDoJogador = new LinkedList<String>();
		this.itensNoInventarioDoJogador.add("no_item");
		this.itensNoInventarioDoJogador.add("no_item");
		this.itensNoInventarioDoJogador.add("no_item");
		this.itensNoInventarioDoJogador.add("no_item");
		this.itensNoInventarioDoJogador.add("no_item");
		this.itensIncorporadosPeloJogador = new LinkedList<String>();
		this.roundDaPartida = 0;
		this.shikoFoiUsado = false;
		
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
		
		
		//vamos ter de converter o Set<String> de categorias em LinkedList 
		LinkedList<String> linkedListCategoriasTreinadas = new LinkedList<String>();
		Iterator<String> iteraSobreCategorias = categoriasDosKanjisATreinar.iterator();
		while(iteraSobreCategorias.hasNext())
		{
			linkedListCategoriasTreinadas.add(iteraSobreCategorias.next());
		}
		
		if(posicaoDaCategoriaAleatoria < linkedListCategoriasTreinadas.size())
		{
			categoriaDoKanjiATreinar = linkedListCategoriasTreinadas.get(posicaoDaCategoriaAleatoria);
					
		}
		else
		{
			//tratamento de exceção mínimo
			categoriaDoKanjiATreinar = linkedListCategoriasTreinadas.getFirst();
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
	
	/*REFERENTE AOS ITENS DO JOGO*/
	public LinkedList<String> getItensIncorporadosPeloJogador() {
		return itensIncorporadosPeloJogador;
	}
	
	public boolean usuarioTemItemIncorporado(String nomeItem)
	{
		if(itensIncorporadosPeloJogador != null)
		{
			for(int i = 0; i < this.itensIncorporadosPeloJogador.size(); i++)
			{
				String itemIncorporado = itensIncorporadosPeloJogador.get(i);
				if(itemIncorporado.compareTo(nomeItem) == 0)
				{
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public void adicionarItemIncorporado(String novoItemIncorporado)
	{
		itensIncorporadosPeloJogador.add(novoItemIncorporado);
	}
	
	public void removerTodosOsItensIncorporados()
	{
		itensIncorporadosPeloJogador = new LinkedList<String>();
	}
	
	public void removerItemIncorporado(String itemRemover)
	{
		LinkedList<String> novosItensIncorporados = new LinkedList<String>();
		for(int i = 0; i < itensIncorporadosPeloJogador.size(); i++)
		{
			String umItem = itensIncorporadosPeloJogador.get(i);
			if(umItem.compareTo(itemRemover) != 0)
			{
				novosItensIncorporados.add(umItem);
			}
		}
		
		this.itensIncorporadosPeloJogador = novosItensIncorporados;
	}
	
	public LinkedList<String> getItensNoInventarioDoJogador() {
		return itensNoInventarioDoJogador;
	}
	
	public int getQuantosItensNoInventarioDoJogador()
	{
		return this.itensNoInventarioDoJogador.size();
	}
	
	public String adicionarItemAleatorioAoInventario()
	{
		String itemAleatorio;
		Random r = new Random();
		if(posicaoSumozinhoDoJogadorNaArena < 4)
		{
			int indiceItemAleatorio = r.nextInt(itensDoJogo.length);
			itemAleatorio = itensDoJogo[indiceItemAleatorio];
		}
		else
		{
			int indiceItemAleatorio = r.nextInt(itensDoJogoParaQuemQuaseGanha.length);
			itemAleatorio = itensDoJogoParaQuemQuaseGanha[indiceItemAleatorio];
		}
		
		int posicaoSemItem = this.getPrimeiraPosicaoSemItemDoInventario();
		if(posicaoSemItem != -1)
		{
			
			this.itensNoInventarioDoJogador.set(posicaoSemItem, itemAleatorio);
			return itemAleatorio;
		}
		else
		{
			return "no_item";
		}
	}
	
	/**
	 * 
	 * @return a posicao do primeiro "no_item" na linkedlist do inventario do jogador ou -1 para o caso dele já ter todos os itens no inventario.
	 */
	
	public int getPrimeiraPosicaoSemItemDoInventario()
	{
		for(int i = 0; i < this.itensNoInventarioDoJogador.size(); i++)
		{
			String nomeItemNaPosicao = this.itensNoInventarioDoJogador.get(i);
			if(nomeItemNaPosicao.compareTo("no_item") == 0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	
	public String removerUmItemDoInventario(int posicaoDoItem)
	{
		if(posicaoDoItem >= 0 && posicaoDoItem < 5)
		{
			String nomeItem = this.itensNoInventarioDoJogador.get(posicaoDoItem);
			this.itensNoInventarioDoJogador.set(posicaoDoItem,"no_item");
			return nomeItem;
		}
		else
		{
			return "no_item";//sem item nessa posicao...
		}
	}
	public boolean jogadorEstahSemItens()
	{
		for(int i = 0; i < this.itensNoInventarioDoJogador.size(); i++)
		{
			if(this.itensNoInventarioDoJogador.get(i).compareTo("no_item") != 0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean oShikoFoiUsado() {
		return shikoFoiUsado;
	}
	public void setShikoFoiUsado(boolean shikoFoiUsado) {
		this.shikoFoiUsado = shikoFoiUsado;
	}
	
	/*FIM REFERENTE AOS ITENS DO JOGO*/

	public int getRoundDaPartida() {
		return roundDaPartida;
	}
	
	public void incrementarRoundDaPartida()
	{
		roundDaPartida = roundDaPartida + 1;
	}




	
	
	
	

}
