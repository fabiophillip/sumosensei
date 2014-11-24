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
import br.ufrn.dimap.pairg.sumosensei.MainActivity;

import com.phiworks.dapartida.GuardaDadosDaPartida;

public class TaskInserirUsuarioNoBd extends AsyncTask<String, String, String>{
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;
	private MainActivity telaInicialDoJogo;
	private String nomeDeUsuario;

	public TaskInserirUsuarioNoBd(ProgressDialog loadingDaTela, MainActivity telaInicialDoJogo)
	{
		this.popupDeProgresso = loadingDaTela;
		this.telaInicialDoJogo = telaInicialDoJogo;
	}

	@Override
	protected String doInBackground(String... nomeUsuarioEEmail) {
		try
		{
			this.nomeDeUsuario = nomeUsuarioEEmail[0];
			String email = null;
			if(nomeUsuarioEEmail.length > 1)
			{
				email = nomeUsuarioEEmail[1];
			}
			
			//primeiro, vamos ver se o usuario não já existe...
			
			HttpClient httpClient = new DefaultHttpClient();

			String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuariopornome.php";//android nao aceita localhost, tem de ser seu IP
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        HttpPost httpPost = new HttpPost(url_select);
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
	                if(nomeDeUsuario.compareTo(this.nomeDeUsuario) == 0)
	                {
	                	return "username_jah_existe";
	                }
	                else
	                {
	                	//depois, se não teve um usuário já cadastrado, vamos inserir o usuário no BD
	        			
	        			url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirusuarionobd.php";//android nao aceita localhost, tem de ser seu IP
	        			nameValuePairs = new ArrayList<NameValuePair>();
	        			
	        			httpClient = new DefaultHttpClient();

	                    httpPost = new HttpPost(url_select);
	                    nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeDeUsuario));
	                    nameValuePairs.add(new BasicNameValuePair("email_usuario", email));
	                    	
	                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	                    httpResponse = httpClient.execute(httpPost);
	                    HttpEntity entity = httpResponse.getEntity();
	                    
	                    //
	                    // Read the contents of an entity and return it as a String.
	                    //
	                    content = EntityUtils.toString(entity);
	                    System.out.println("oi");
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
                nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeDeUsuario));
                nameValuePairs.add(new BasicNameValuePair("email_usuario", email));
                	
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                
                //
                // Read the contents of an entity and return it as a String.
                //
                content = EntityUtils.toString(entity);
                System.out.println("oi");
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
	
	@Override
	protected void onPostExecute(String v) {
		if(v.compareTo("username_jah_existe") != 0)
		{
			this.popupDeProgresso.dismiss();
			this.telaInicialDoJogo.trocarParaTelaPrincipal();
		}
		else
		{
			this.popupDeProgresso.dismiss();
			this.telaInicialDoJogo.mostrarMensagemErroUsuarioJahExiste();
		}
	}
	

}
