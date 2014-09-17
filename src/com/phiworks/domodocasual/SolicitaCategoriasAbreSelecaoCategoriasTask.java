package com.phiworks.domodocasual;

import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;

import com.phiworks.sumosenseinew.TelaModoCasual;

public class SolicitaCategoriasAbreSelecaoCategoriasTask extends SolicitaCategoriasDoJogoTask {

	public SolicitaCategoriasAbreSelecaoCategoriasTask(
			ProgressDialog loadingDaTela,
			TelaModoCasual activityQueEsperaAteRequestTerminar) {
		super(loadingDaTela, activityQueEsperaAteRequestTerminar);
		// TODO Auto-generated constructor stub
	}
	
	 protected void onPostExecute(Void v) {
	        //parse JSON data
	        try {
	            JSONArray jArray = new JSONArray(result);    
	            for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                String jlptAssociado = jObject.getString("jlpt");
	                String categoriaAssociada = jObject.getString("categoria");
	                String kanji = jObject.getString("kanji");
	                String traducaoEmPortugues = jObject.getString("traducao");
	                String hiraganaDoKanji = jObject.getString("hiragana");
	                String dificuldadeDoKanji = jObject.getString("dificuldade");
	                
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
	                		traducaoEmPortugues, hiraganaDoKanji, dificuldadeDoKanjiEmNumero);
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
	        this.activityModoCasual.mostrarPopupPesquisarPorCategorias();
	        this.loadingDaTelaEmEspera.dismiss();
	    } // protect

}
