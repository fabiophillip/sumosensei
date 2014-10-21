package com.phiworks.domodocasual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.phiworks.sumosenseinew.ActivityQueEsperaAtePegarOsKanjis;
import com.phiworks.sumosenseinew.TelaModoCasual;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class BuscaSalasModoCasualComArgumentoTask extends AsyncTask<String, String, Void>
{
	private InputStream inputStream = null;
	private String result = ""; 
	private ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	private TelaModoCasual activityQueEsperaAtePegarAsSalas;

	public BuscaSalasModoCasualComArgumentoTask(ProgressDialog loadingDaTela, TelaModoCasual activityQueEsperaAteRequestTerminar)
	{
		this.loadingDaTelaEmEspera = loadingDaTela;
		this.activityQueEsperaAtePegarAsSalas = activityQueEsperaAteRequestTerminar;
	}

	@Override
	protected Void doInBackground(String... arg0) 
	{
		//LCC: http://10.9.99.239/amit/pegarjlptjson.php
				//sala de aula: http://10.5.26.127/amit/pegarjlptjson.php

		       //String url_select = "http://app.karutakanji.pairg.dimap.ufrn.br/pegarjlptjson.php";//android nao aceita localhost, tem de ser seu IP
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(arg0[0].compareTo("username") == 0)
		{
			//iremos pesquisar por user
			url_select = url_select + "filtrarsalapornomejogador.php";
			String username = arg0[1];
			nameValuePairs.add(new BasicNameValuePair("username", username));
		}
		else if(arg0[0].compareTo("nivel_adversario") == 0)
		{
			//iremos pesquisar por email
			url_select = url_select + "filtrarsalaportitulojogador.php";
			String nivelDoJogador = arg0[1];
			nameValuePairs.add(new BasicNameValuePair("niveldojogador", nivelDoJogador));
		}
		else if(arg0[0].compareTo("categorias") == 0)
		{
			//iremos pesquisar por email
			url_select = url_select + "pegasalasporcategoria.php";
			String categorias = arg0[1];
			nameValuePairs.add(new BasicNameValuePair("categoriasselecionadas", categorias));
		}
		
		//String url_select = "http://192.168.0.110/amit/pegarjlptjson.php";

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
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
            if(result.contains("%%%%%%%%%%%%%%%%%%%%%%%%%"))
            {
            	result = result.split("%%%%%%%%%%%%%%%%%%%%%%%%%")[2];
            }
            System.out.println("teste");

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        
		return null;		
	}
	
	 protected void onPostExecute(Void v) {
	        //parse JSON data
	        try {
	            JSONArray jArray = new JSONArray(result); 
	            ArrayList<SalaAbertaModoCasual> salasModoCasual = new ArrayList<SalaAbertaModoCasual>();
	            for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                int id_sala = jObject.getInt("id_da_sala");
	                String categorias_juntas = jObject.getString("categorias_juntas");
	                String username_do_criador = jObject.getString("usernamequemcriousala");
	                String dan_do_criador = jObject.getString("titulodojogador");
	                
	                SalaAbertaModoCasual novaSalaModoCasual = new SalaAbertaModoCasual();
	                novaSalaModoCasual.setIdDaSala(id_sala);
	                String [] categoriasSeparadas = categorias_juntas.split(",");
	                LinkedList<String> categoriasDaSala = new LinkedList<String>();
	                for(int j = 0; j < categoriasSeparadas.length; j++)
	                {
	                	String umaCategoria = categoriasSeparadas[j];
	                	categoriasDaSala.add(umaCategoria);
	                }
	                
	                //encurtar username
	                if(username_do_criador.length() > 13)
	        	 	{
	        	 		username_do_criador = username_do_criador.substring(0, 12);
	        	 	}
	                novaSalaModoCasual.setCategoriasSelecionadas(categoriasDaSala);
	                novaSalaModoCasual.setNivelDoUsuario(dan_do_criador);
	                novaSalaModoCasual.setNomeDeUsuario(username_do_criador);
	                
	                salasModoCasual.add(novaSalaModoCasual);

	            } // End Loop
	            
	            this.activityQueEsperaAtePegarAsSalas.mostrarListaComSalasAposCarregar(salasModoCasual, false);
	            this.loadingDaTelaEmEspera.dismiss();
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	            this.loadingDaTelaEmEspera.dismiss();
	        }

	    }


}
