package com.phiworks.domodocasual;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;
import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.Categoria;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCasual;


public class SolicitaCategoriasAbreSelecaoCategoriasTask extends SolicitaCategoriasDoJogoTask {

	private TelaModoCasual activityTelaCasual;
	public SolicitaCategoriasAbreSelecaoCategoriasTask(
			ProgressDialog loadingDaTela,
			TelaModoCasual activityQueEsperaAteRequestTerminar) {
		super(loadingDaTela, activityQueEsperaAteRequestTerminar);
		this.activityTelaCasual = activityQueEsperaAteRequestTerminar;
		// TODO Auto-generated constructor stub
	}
	
	 protected void onPostExecute(Void v) {
	        //parse JSON data
	        try {
	        	JSONArray jArray = new JSONArray(result);
	            System.out.println("para testes");
	            for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                String jlptAssociado = jObject.getString("jlpt");
	                
	                String categoriaAssociada; 
	                String traducaoEmPortugues; 
	                
	                Resources res = this.activityTelaCasual.getResources();
	                Locale myLocale = res.getConfiguration().locale;
	        		if(myLocale != null)
	        		{
	        			if(myLocale.getLanguage().compareTo("en") == 0)
	        		    {
	        				categoriaAssociada = jObject.getString("nome_categoria_ingles");
	        		    	traducaoEmPortugues = jObject.getString("traducao_ingles");
	        		    }
	        		    else // br
	        		    {
	        		    	categoriaAssociada = jObject.getString("nome_categoria");
	        		    	traducaoEmPortugues = jObject.getString("traducao");
	        		    }
	        			 
	        		}
	        		else
	        		{
	        			categoriaAssociada = jObject.getString("nome_categoria");
        		    	traducaoEmPortugues = jObject.getString("traducao");
	        		}
	                
	                String kanji = jObject.getString("kanji");
	                String hiraganaDoKanji = jObject.getString("hiragana");
	                String dificuldadeDoKanji = jObject.getString("dificuldade");
	                
	                String id_do_kanji = jObject.getString("id");
	                int id_categoria = jObject.getInt("id_categoria");
	                String descricao_categoria = jObject.getString("id");
	                
	                Categoria categoriaNovaArmazenar = new Categoria(id_categoria,categoriaAssociada,descricao_categoria);
	                SingletonArmazenaCategoriasDoJogo.getInstance().armazenarNovaCategoria(categoriaAssociada, categoriaNovaArmazenar);
	                
	                int dificuldadeDoKanjiEmNumero; 
	                try
	                {
	                	dificuldadeDoKanjiEmNumero = Integer.valueOf(dificuldadeDoKanji);
	                }
	                catch(NumberFormatException exc)
	                {
	                	dificuldadeDoKanjiEmNumero = 1;
	                }
	                
	                KanjiTreinar novoKanjiTreinar = new KanjiTreinar(jlptAssociado, categoriaAssociada, kanji, 
	                		traducaoEmPortugues, hiraganaDoKanji, dificuldadeDoKanjiEmNumero, id_do_kanji);
	                //vamos só ver se o kanji tem uma lista de possiveis ciladas...
	                @SuppressWarnings("unchecked")
					Iterator<String> nomesColunasDoJObject = jObject.keys();
	                while(nomesColunasDoJObject.hasNext())
	                {
	                	if(nomesColunasDoJObject.next().compareTo("possiveis ciladas") == 0)
	                	{
	                		String possiveisCiladas = jObject.getString("possiveis ciladas");
	                		String [] ciladasEmArray = possiveisCiladas.split(";");
	                		LinkedList<String> possiveisCiladasLinkedList = new LinkedList<String>();
	                		for(int j = 0; j < ciladasEmArray.length; j++)
	                		{
	                			possiveisCiladasLinkedList.add(ciladasEmArray[j]);
	                		}
	                		novoKanjiTreinar.setPossiveisCiladasKanji(possiveisCiladasLinkedList);
	                	}
	                	
	                }
	                
	                //e, por fim, armazenar esses kanjis na lista de ArmazenaKanjisPorCategoria
	                ArmazenaKanjisPorCategoria.pegarInstancia().adicionarKanjiACategoria(categoriaAssociada, novoKanjiTreinar);
	                
	                

	            } // End Loop
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	        }
	        ArmazenaKanjisPorCategoria armazena = ArmazenaKanjisPorCategoria.pegarInstancia();
	        LinkedList<String> categoriasArmazenadas = armazena.getCategoriasDeKanjiArmazenadas("5");
	        System.out.println("pra testes");
	        this.activityTelaCasual.mostrarPopupPesquisarPorCategorias();
	        this.loadingDaTelaEmEspera.dismiss();
	    } // protect

}
