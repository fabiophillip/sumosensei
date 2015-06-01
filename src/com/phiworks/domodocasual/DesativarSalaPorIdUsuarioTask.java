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


import android.os.AsyncTask;
import android.util.Log;

public class DesativarSalaPorIdUsuarioTask extends AsyncTask<String, String, Void> 
{
	private InputStream inputStream = null;
	private String result = ""; 

	
	
	@Override
	protected Void doInBackground(String... arg0) 
	{
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/desativarsalaporid_usuario.php";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		String nomeUsuario = arg0[0];
		String idUsuario = pegarIdUsuario(nomeUsuario);
		nameValuePairs.add(new BasicNameValuePair("id_usuario", idUsuario));
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
            System.out.print("teste");

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
		return null;
	}
	
	 protected void onPostExecute(Void v) {
		 
	 }
	 
	 public String pegarIdUsuario(String nomeUsuario)
	 {
		 
		 String  idUsuario = "0";
		//primeiro, pegar o user id
			try
			{
				HttpClient httpClient = new DefaultHttpClient();

				String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuariopornome.php";//android nao aceita localhost, tem de ser seu IP
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		        HttpPost httpPost = new HttpPost(url_select);
		        nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeUsuario));
		        	
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
		        
		        //agora, pegar o id dele...
		        
		        JSONArray jArray = new JSONArray(result);
		        System.out.println("para testes");
		        if(jArray.length() > 0)
		        {
		        	for(int i=0; i < jArray.length(); i++) 
		        	{

		                JSONObject jObject = jArray.getJSONObject(i);
		                
		                idUsuario = String.valueOf(jObject.getInt("id_usuario"));
		        	}
		        }
			}
			catch(ClientProtocolException exc)
			{
				exc.printStackTrace();
			}
			catch(IOException exc)
			{
				exc.printStackTrace();
				
			}
			catch(JSONException exc)
			{
				exc.printStackTrace();
			}
			
			
		 return idUsuario;
	 }
	

}
