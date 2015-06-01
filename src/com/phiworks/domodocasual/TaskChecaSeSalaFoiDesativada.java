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

import dousuario.SingletonGuardaUsernameUsadoNoLogin;

import br.ufrn.dimap.pairg.sumosensei.android.MainActivity;
import br.ufrn.dimap.pairg.sumosensei.android.TelaModoCasual;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class TaskChecaSeSalaFoiDesativada extends AsyncTask<SalaAbertaModoCasual, String, String>{

	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;
	private TelaModoCasual telaModoCasual;
	private SalaAbertaModoCasual salaVaiEntrar;
	
	public TaskChecaSeSalaFoiDesativada(TelaModoCasual telaCasual, ProgressDialog popupDeProgresso)
	{
		this.telaModoCasual = telaCasual;
		this.popupDeProgresso = popupDeProgresso;
	}
	
	@Override
	protected String doInBackground(SalaAbertaModoCasual... params) 
	{
		SalaAbertaModoCasual salaVaiEntrar = params[0];
		this.salaVaiEntrar = salaVaiEntrar;
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_sala_por_id_casual.php";//android nao aceita localhost, tem de ser seu IP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            String idDaSala = String.valueOf(salaVaiEntrar.getIdDaSala());
            nameValuePairs.add(new BasicNameValuePair("id_da_sala",idDaSala));
            	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //
            // Read the contents of an entity and return it as a String.
            //
            String content = httpEntity.toString();
            System.out.println("oi");

            // Read content & Log
            inputStream = httpEntity.getContent();
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
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) {
		 try {
	            JSONArray jArray = new JSONArray(result);
	            System.out.println("para testes");
	            if(jArray.length() > 0)
	            {
	            	for(int i=0; i < jArray.length(); i++) {

		                JSONObject jObject = jArray.getJSONObject(i);
		                
		                String salaAtiva = jObject.getString("sala_ativa");
		               
		                if(salaAtiva.compareTo("sim") == 0)
		                {
		                	
		                	this.popupDeProgresso.dismiss();
		            		this.telaModoCasual.aposVerificarQueSalaEstahAtiva(salaVaiEntrar);
		            		
		                }
		                else
		                {
		                	this.popupDeProgresso.dismiss();
		                	this.telaModoCasual.mostrarMensagemErroSalaInativa();
		                }
		            } // End Loop
	            }
	            else
                {
	            	this.popupDeProgresso.dismiss();
                	this.telaModoCasual.mostrarMensagemErroSalaInativa();
                }
	            
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	        }
	}

}
