package com.phiworks.domodocasual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.Categoria;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.android.ActivityMultiplayerQueEsperaAtePegarOsKanjis;
import br.ufrn.dimap.pairg.sumosensei.android.ActivityQueEsperaAtePegarOsKanjis;
import br.ufrn.dimap.pairg.sumosensei.android.TelaModoCasual;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Instrumentation.ActivityMonitor;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

public class SolicitaCategoriasDoJogoTask extends AsyncTask<String, String, Void>{
	private InputStream inputStream = null;
	protected String result = ""; 
	protected ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	protected Activity activityModoCasual;
	
	public SolicitaCategoriasDoJogoTask(ProgressDialog loadingDaTela, Activity activityQueEsperaAteRequestTerminar)
	{
		this.loadingDaTelaEmEspera = loadingDaTela;
		this.activityModoCasual = activityQueEsperaAteRequestTerminar;
		ArmazenaKanjisPorCategoria.pegarInstancia().limparHashMap();
		SingletonArmazenaCategoriasDoJogo.getInstance().limparListas();
	}
	
	@Override
    protected Void doInBackground(String... params) {
		//antigo:"http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarjlptjson.php";
       String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarjlptjsonnew.php";//android nao aceita localhost, tem de ser seu IP
       
       
       ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
            	if(line.startsWith("<meta") == false)//pula linha de metadados
            	{
            		 sBuilder.append(line + "\n");
            	}
               
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        
        if(this.activityModoCasual instanceof ActivityMultiplayerQueEsperaAtePegarOsKanjis)
        {
        	//se trata da activity do multiplayer
        	ActivityMultiplayerQueEsperaAtePegarOsKanjis activityMultiplayer = (ActivityMultiplayerQueEsperaAtePegarOsKanjis) this.activityModoCasual;
        	if(activityMultiplayer.jogadorEhHost() == true)
        	{
        		 while(activityMultiplayer.oGuestTerminouDeCarregarListaDeCategorias() == false)
        	        {
        	        	//espera...
        	        	try {
        					Thread.sleep(1000);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        	        }
        	}
        }
       
        
		return null;
    } // protected Void doInBackground(String... params)
	
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
	                Resources res = this.activityModoCasual.getResources();
	                Locale myLocale = res.getConfiguration().locale;
	        		if(myLocale != null)
	        		{
	        			if(myLocale.getLanguage().compareTo("en") == 0)
	        		    {
	        				categoriaAssociada = jObject.getString("nome_categoria_ingles");
			                traducaoEmPortugues = jObject.getString("traducao_ingles");
	        		    }
	        			else if(myLocale.getLanguage().compareTo("ja") == 0)
	        		    {
	        				categoriaAssociada = jObject.getString("nome_categoria_japones");
			                traducaoEmPortugues = jObject.getString("traducao_ingles");
	        		    }
	        		    else if(myLocale.getLanguage().compareTo("pt") == 0)// br
	        		    {
	        		    	categoriaAssociada = jObject.getString("nome_categoria");
			                traducaoEmPortugues = jObject.getString("traducao");
	        		    }
	        		    else
	        		    {
	        				categoriaAssociada = jObject.getString("nome_categoria_ingles");
			                traducaoEmPortugues = jObject.getString("traducao_ingles");
	        		    }
	        			 
	        		}
	        		else
	        		{
	        			categoriaAssociada = jObject.getString("nome_categoria_ingles");
		                traducaoEmPortugues = jObject.getString("traducao_ingles");
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
	                //vamos s� ver se o kanji tem uma lista de possiveis ciladas...
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
	        if(this.loadingDaTelaEmEspera != null)
	        {
	        	this.loadingDaTelaEmEspera.dismiss();
	        }
	        
	    } // protected void onPostExecute(Void v)

}
