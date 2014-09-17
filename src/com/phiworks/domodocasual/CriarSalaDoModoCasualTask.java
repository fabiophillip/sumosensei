package com.phiworks.domodocasual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

import com.phiworks.dapartida.ActivityPartidaMultiplayer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class CriarSalaDoModoCasualTask extends AsyncTask<DadosDaSalaModoCasual, String, String>
{
	private ActivityPartidaMultiplayer activityDoMultiplayer;
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;

	public CriarSalaDoModoCasualTask(ProgressDialog loadingDaTela, ActivityPartidaMultiplayer telaDoMultiplayer)
	{
		this.activityDoMultiplayer = telaDoMultiplayer;
		this.popupDeProgresso = loadingDaTela;
	}

	@Override
	protected String doInBackground(DadosDaSalaModoCasual... params) {
		DadosDaSalaModoCasual dadosDaSala = params[0];
		String emailQuemCriouSala = dadosDaSala.getUsernameQuemCriouSala();
		String categoriasSeparadasPorVirgula = dadosDaSala.getCategoriasSelecionadasEmString();
		String tituloDoJogador = dadosDaSala.getTituloDoJogador();
		
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserir_nova_sala.php";//android nao aceita localhost, tem de ser seu IP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            nameValuePairs.add(new BasicNameValuePair("idsalasmodocasual", null));
            nameValuePairs.add(new BasicNameValuePair("usernamequemcriousala", emailQuemCriouSala));
            nameValuePairs.add(new BasicNameValuePair("categoriasselecionadas", categoriasSeparadasPorVirgula));
            nameValuePairs.add(new BasicNameValuePair("titulodojogador", tituloDoJogador));
            	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost); 
            
            
            //em seguida, pegar o id da nova partida que acabou de ser criada.
            String url_pegar_id = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_id_nova_sala_criada.php";
            ArrayList<NameValuePair> nomeEEmailQuemCriouSala = new ArrayList<NameValuePair>();
            HttpClient httpClientPegarIdSala = new DefaultHttpClient();
            HttpPost httpPostPegarIdSala = new HttpPost(url_pegar_id);
            nomeEEmailQuemCriouSala.add(new BasicNameValuePair("usernamequemcriousala", emailQuemCriouSala));
            httpPostPegarIdSala.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpRespostaIdSala = httpClientPegarIdSala.execute(httpPostPegarIdSala);
            
            HttpEntity httpEntity = httpRespostaIdSala.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
            
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
            
            //por fim, inserir as categorias da sala numa tabela diferente
            String url_inserir_categorias = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserir_categorias_de_sala.php";
            ArrayList<NameValuePair> idSalaECategorias = new ArrayList<NameValuePair>();
            HttpClient httpClientinserirCategorias = new DefaultHttpClient();
            HttpPost httpPostInserirCategorias = new HttpPost(url_inserir_categorias);
            
            try
    		{
    			JSONArray jArray = new JSONArray(result);
    			String idSalaModoCasual = "-1";
    	        for(int i=0; i < jArray.length(); i++) {

    	            JSONObject jObject = jArray.getJSONObject(i);
    	            
    	            idSalaModoCasual = jObject.getString("id_da_sala");
    	           
    	        }
    	        if(idSalaModoCasual.compareTo("-1") != 0)
    	        {
    	        	 idSalaECategorias.add(new BasicNameValuePair("categoriasselecionadas", categoriasSeparadasPorVirgula));
    	             idSalaECategorias.add(new BasicNameValuePair("idsala", idSalaModoCasual));
    	             
    	             httpPostInserirCategorias.setEntity(new UrlEncodedFormEntity(idSalaECategorias,"UTF-8"));
    	             HttpResponse httpRespostaInserirCategoria = httpClientinserirCategorias.execute(httpPostInserirCategorias);
    	             System.out.println("oi");
    	        }
    	        
    		}
    		catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
            
           
            
		}
		catch (UnsupportedEncodingException e1) {
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
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) {
		this.popupDeProgresso.dismiss();
		try
		{
			JSONArray jArray = new JSONArray(result);
			int idSalaModoCasual = -1;
	        for(int i=0; i < jArray.length(); i++) {

	            JSONObject jObject = jArray.getJSONObject(i);
	            
	            String idSalaModoCasualEmString = jObject.getString("id_da_sala");
	            idSalaModoCasual = Integer.valueOf(idSalaModoCasualEmString);
	        }
	        if(idSalaModoCasual != -1)
	        {
	        	this.activityDoMultiplayer.setarIdDaSala(idSalaModoCasual);
	        	this.activityDoMultiplayer.criarSalaQuickMatch(idSalaModoCasual);
	        }
	        
		}
		catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
		
		
	}
	
}
