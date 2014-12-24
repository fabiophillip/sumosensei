package dousuario;

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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import br.ufrn.dimap.pairg.sumosensei.MainActivity;

public class TaskAcharUsuarioPorEmail extends AsyncTask<String, String, String>{
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;
	private MainActivity telaInicialDoJogo;
	
	private String email;
	private String senha;

	public TaskAcharUsuarioPorEmail(ProgressDialog loadingDaTela, MainActivity telaInicialDoJogo)
	{
		this.popupDeProgresso = loadingDaTela;
		this.telaInicialDoJogo = telaInicialDoJogo;
	}

	@Override
	protected String doInBackground(String... emailESenha) {
		this.email = emailESenha[0];
		this.senha = emailESenha[1];
		
		
		
		
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuarioporemail.php";//android nao aceita localhost, tem de ser seu IP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            nameValuePairs.add(new BasicNameValuePair("email_usuario", email));
            nameValuePairs.add(new BasicNameValuePair("senha_usuario", senha));
            	
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
		                
		                String nomeDeUsuario = jObject.getString("nome_usuario");
		                String emailUsuario = jObject.getString("email_usuario");
		                String senhaUsuario = jObject.getString("senha_usuario");
		                if(senhaUsuario.compareTo(senha) == 0 && emailUsuario.compareTo(this.email) == 0)
		                {
		                	SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		            		guardaUsername.setEmailJogador(emailUsuario, this.telaInicialDoJogo.getApplicationContext());
		            		guardaUsername.setNomeJogador(nomeDeUsuario, this.telaInicialDoJogo.getApplicationContext());
		            		guardaUsername.setSenhaJogador(senhaUsuario, this.telaInicialDoJogo.getApplicationContext());
		                	this.popupDeProgresso.dismiss();
		            		this.telaInicialDoJogo.trocarParaTelaPrincipal();
		            		
		                }
		                else
		                {
		                	this.popupDeProgresso.dismiss();
		                	this.telaInicialDoJogo.mostrarMensagemErroUsuarioESenhaIncorretos();
		                }
		            } // End Loop
	            }
	            else
                {
	            	this.popupDeProgresso.dismiss();
                	this.telaInicialDoJogo.mostrarMensagemErroUsuarioESenhaIncorretos();
                }
	            
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	        }
	}

}
