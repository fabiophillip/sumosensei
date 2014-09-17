package com.phiworks.domodocasual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

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

import com.phiworks.sumosenseinew.ActivityQueEsperaAtePegarOsKanjis;
import com.phiworks.sumosenseinew.TelaModoCasual;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class BuscaSalasModoCasualTask extends AsyncTask<String, String, Void>
{
	private InputStream inputStream = null;
	private String result = ""; 
	private ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	private TelaModoCasual activityQueEsperaAtePegarAsSalas;

	public BuscaSalasModoCasualTask(ProgressDialog loadingDaTela, TelaModoCasual activityQueEsperaAteRequestTerminar)
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
				String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarsalasmodocasualjson.php";
				//String url_select = "http://192.168.0.110/amit/pegarjlptjson.php";
		       
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
	                String email_do_criador = jObject.getString("usernamequemcriousala");
	                String dan_do_criador = jObject.getString("titulodojogador");
	                
	                SalaAbertaModoCasual novaSalaModoCasual = new SalaAbertaModoCasual();
	                novaSalaModoCasual.setIdDaSala(id_sala);
	                String [] categoriasSeparadasArray;
	                if(categorias_juntas.contains(",") == true)
	                {
	                	categoriasSeparadasArray = categorias_juntas.split(",");
	                }
	                else
	                {
	                	categoriasSeparadasArray = new String [1];
	                	categoriasSeparadasArray[0] = categorias_juntas;
	                }
	                LinkedList<String> categoriasSeparadasLinkedlist = new LinkedList<String>();
	                for(int j = 0; j < categoriasSeparadasArray.length; j++)
	                {
	                	String umaCategoria = categoriasSeparadasArray[j];
	                	categoriasSeparadasLinkedlist.add(umaCategoria);
	                	
	                }
	                novaSalaModoCasual.setCategoriasSelecionadas(categoriasSeparadasLinkedlist);
	                novaSalaModoCasual.setNivelDoUsuario(dan_do_criador);
	                //encurtar username
	                if(email_do_criador.length() > 13)
	        	 	{
	        	 		email_do_criador = email_do_criador.substring(0, 12);
	        	 	}
	                novaSalaModoCasual.setNomeDeUsuario(email_do_criador);
	                
	                salasModoCasual.add(novaSalaModoCasual);

	            } // End Loop
	            
	            //vamos reverter a ordem das salas, ordenadas do mais recente...
	            ArrayList<SalaAbertaModoCasual> salasModoCasualRevertido;
	            /*for(int y = salasModoCasual.size() - 1; y >= 0; y--)
	            {
	            	SalaAbertaModoCasual umaSalaAbertaDaLista = salasModoCasual.get(y);
	            	salasModoCasualRevertido.add(umaSalaAbertaDaLista);
	            }*/
	            Collections.reverse(salasModoCasual);
	            salasModoCasualRevertido = salasModoCasual;
	            ArrayList<SalaAbertaModoCasual> antigasSalasCarregadasModoCasual = this.activityQueEsperaAtePegarAsSalas.getSalasCarregadasModoCasual();
	           
	            boolean acheiSalasDiferentes = false;
	            boolean novasSalasForamAdicionadas = false;
	            if(antigasSalasCarregadasModoCasual != null && antigasSalasCarregadasModoCasual.size() == salasModoCasualRevertido.size())
	            {
	            	
	            	//as arrayLists tem o mesmo tamanho. Então são iguais? Vamos ver...
	            	for(int u = 0; u < antigasSalasCarregadasModoCasual.size(); u++)
	            	{
	            		SalaAbertaModoCasual umaSalaAntigaCarregada = antigasSalasCarregadasModoCasual.get(u);
	            		SalaAbertaModoCasual salaAtualCarregada = salasModoCasualRevertido.get(u);
	            		if(umaSalaAntigaCarregada.getIdDaSala() != salaAtualCarregada.getIdDaSala())
	            		{
	            			novasSalasForamAdicionadas = true;
	            			acheiSalasDiferentes = true;
	            			break;
	            		}
	            	}
	            }
	            else
	            {
	            	acheiSalasDiferentes = true;
	            	if(antigasSalasCarregadasModoCasual != null && antigasSalasCarregadasModoCasual.size() < salasModoCasualRevertido.size())
	            	{
	            		novasSalasForamAdicionadas = true;
	            	}
	            	
	            }
	            if(acheiSalasDiferentes == true)
	            {
	            	
        			
	            	this.activityQueEsperaAtePegarAsSalas.mostrarListaComSalasAposCarregar(salasModoCasualRevertido, novasSalasForamAdicionadas);
	            }
	            
	            if(loadingDaTelaEmEspera!= null && loadingDaTelaEmEspera.isShowing())
   			 	{
   				 	this.loadingDaTelaEmEspera.dismiss();	 
   			 	}
	            //this.loadingDaTelaEmEspera.dismiss();
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	            if(loadingDaTelaEmEspera!= null && loadingDaTelaEmEspera.isShowing())
   			 	{
   				 	this.loadingDaTelaEmEspera.dismiss();	 
   			 	}
	        }

	    }
}
