package dousuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.android.CadastroActivity;
import br.ufrn.dimap.pairg.sumosensei.android.MainActivity;

import com.phiworks.dapartida.GuardaDadosDaPartida;

public class TaskInserirUsuarioNoBd extends AsyncTask<String, String, String>{
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;
	private CadastroActivity telaCadastroDoJogo;
	private String nomeDeUsuario;
	private String emailDeUsuario;

	public TaskInserirUsuarioNoBd(ProgressDialog loadingDaTela, CadastroActivity telaInicialDoJogo)
	{
		this.popupDeProgresso = loadingDaTela;
		this.telaCadastroDoJogo = telaInicialDoJogo;
	}

	@Override
	protected String doInBackground(String... nomeUsuarioEEmailESenha) {
		try
		{
			this.nomeDeUsuario = nomeUsuarioEEmailESenha[0];
			this.emailDeUsuario = nomeUsuarioEEmailESenha[1];
			String senha = nomeUsuarioEEmailESenha[2];
			
			//primeiro, vamos ver se o usuario não já existe...
			
			HttpClient httpClient = new DefaultHttpClient();

			String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuarioporemailouusername.php";//android nao aceita localhost, tem de ser seu IP
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        HttpPost httpPost = new HttpPost(url_select);
	        nameValuePairs.add(new BasicNameValuePair("email_usuario", emailDeUsuario));
	        nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeDeUsuario));
	        	
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
	        
	        //agora, ver se já existe um usuário com esse nome...
	        
	        JSONArray jArray = new JSONArray(result);
            System.out.println("para testes");
            if(jArray.length() > 0)
            {
            	for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                String nomeDeUsuario = jObject.getString("nome_usuario");
	                String emailDeUsuario = jObject.getString("email_usuario");
	                if(nomeDeUsuario.compareTo(this.nomeDeUsuario) == 0)
	                {
	                	return "username_jah_existe";
	                }
	                else if(emailDeUsuario.compareTo(this.emailDeUsuario) == 0)
	                {
	                	return "email_jah_existe";
	                }
	                else
	                {
	                	//depois, se não teve um usuário já cadastrado, vamos inserir o usuário no BD
	        			
	        			url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirusuarionobd.php";//android nao aceita localhost, tem de ser seu IP
	        			nameValuePairs = new ArrayList<NameValuePair>();
	        			
	        			httpClient = new DefaultHttpClient();

	                    httpPost = new HttpPost(url_select);
	                    nameValuePairs.add(new BasicNameValuePair("nome_usuario", this.nomeDeUsuario));
	                    nameValuePairs.add(new BasicNameValuePair("email_usuario", this.emailDeUsuario));
	                    nameValuePairs.add(new BasicNameValuePair("senha_usuario", senha));
	                    	
	                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	                    httpResponse = httpClient.execute(httpPost);
	                    HttpEntity entity = httpResponse.getEntity();
	                    
	                    //
	                    // Read the contents of an entity and return it as a String.
	                    //
	                    content = EntityUtils.toString(entity);
	                    
	                    //por fim, inserir o novo usuario no ranking global do competição
	        			inserirJogadorNoRankingGlobal(emailDeUsuario, senha);
	                    
	                    
	                }
	               
	            } // End Loop
            }
            else
            {
            	//depois, se não teve um usuário já cadastrado, vamos inserir o usuário no BD
    			
            	url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirusuarionobd.php";//android nao aceita localhost, tem de ser seu IP
    			nameValuePairs = new ArrayList<NameValuePair>();
    			
    			httpClient = new DefaultHttpClient();

                httpPost = new HttpPost(url_select);
                nameValuePairs.add(new BasicNameValuePair("nome_usuario", this.nomeDeUsuario));
                nameValuePairs.add(new BasicNameValuePair("email_usuario", this.emailDeUsuario));
                nameValuePairs.add(new BasicNameValuePair("senha_usuario", senha));
                	
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                //
                // Read the contents of an entity and return it as a String.
                //
                content = EntityUtils.toString(entity);
                System.out.println("oi");
              //por fim, inserir o novo usuario no ranking global do competição
    			inserirJogadorNoRankingGlobal(emailDeUsuario, senha);
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
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private void inserirJogadorNoRankingGlobal(String email, String senha)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException, JSONException {
		HttpClient httpClient;
		String url_select;
		ArrayList<NameValuePair> nameValuePairs;
		HttpPost httpPost;
		HttpResponse httpResponse;
		String content;
		HttpEntity entity;
		String idJogador = pegarIdDeUmJogador(email, senha);
		if(idJogador != "")
		{
			url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirnovousuarionoranking.php";//android nao aceita localhost, tem de ser seu IP
			nameValuePairs = new ArrayList<NameValuePair>();
			
			httpClient = new DefaultHttpClient();

		    httpPost = new HttpPost(url_select);
		    nameValuePairs.add(new BasicNameValuePair("id_usuario", idJogador));
		    	
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
		    httpResponse = httpClient.execute(httpPost);
		    entity = httpResponse.getEntity();
		    
		    //
		    // Read the contents of an entity and return it as a String.
		    //
		    content = EntityUtils.toString(entity);
		    
		    System.out.println("oi");
		}
	}
	
	@Override
	protected void onPostExecute(String v) {
		if(v.compareTo("username_jah_existe") != 0 && v.compareTo("email_jah_existe") != 0)
		{
			this.popupDeProgresso.dismiss();
			this.telaCadastroDoJogo.trocarParaTelaLogin();
		}
		else
		{
			if(v.compareTo("username_jah_existe") == 0)
			{
				this.popupDeProgresso.dismiss();
				this.telaCadastroDoJogo.mostrarMensagemErroUsuarioJahExiste();
			}
			else
			{
				this.popupDeProgresso.dismiss();
				this.telaCadastroDoJogo.mostrarMensagemErroEmailJahExiste();
			}
			
		}
	}
	
	private String pegarIdDeUmJogador(String emailJogador, String senhaJogador)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException, JSONException {
		String userId = "";
		//primeiro, pegar o user id
		
		HttpClient httpClient = new DefaultHttpClient();

		//antigo: http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidanolog.php
		String url_select;
		ArrayList<NameValuePair> nameValuePairs;
		url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuarioporemail.php";//android nao aceita localhost, tem de ser seu IP
		nameValuePairs = new ArrayList<NameValuePair>();
		HttpPost httpPost = new HttpPost(url_select);
		nameValuePairs.add(new BasicNameValuePair("email_usuario", emailJogador));
		nameValuePairs.add(new BasicNameValuePair("senha_usuario", senhaJogador));
		
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
			for(int i=0; i < jArray.length(); i++) {

		        JSONObject jObject = jArray.getJSONObject(i);
		        
		        userId = String.valueOf(jObject.getInt("id_usuario"));
		        
		        //agora, pegar o id do adversario dele tb...
		        
			}
		}
		return userId;
	}
	

}
