package docompeticao;

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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufrn.dimap.pairg.sumosensei.ActivityDoJogoComSom;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCompeticao;

import com.phiworks.dapartida.GuardaDadosDaPartida;

import dousuario.SingletonGuardaUsernameUsadoNoLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class TaskMudaDadosDoRanking extends AsyncTask<DadosUsuarioNoRanking, Void, Void> 
{
	private TelaModoCompeticao telaModoCompeticao;
	private String result;
	private ProgressDialog popupDeProgresso;
	public TaskMudaDadosDoRanking(TelaModoCompeticao activityComContexto, ProgressDialog dialog )
	{
		this.telaModoCompeticao = activityComContexto;
		this.popupDeProgresso = dialog;
	}

	@Override
	protected Void doInBackground(DadosUsuarioNoRanking... params) 
	{
		try
		{
			DadosUsuarioNoRanking dadosAtualizadosDoRanking = params[0];
			String derrotasCompeticao = String.valueOf(dadosAtualizadosDoRanking.getDerrotasCompeticao());
			String vitoriasCompeticao = String.valueOf(dadosAtualizadosDoRanking.getVitoriasCompeticao());
			String idUsuario = dadosAtualizadosDoRanking.getIdUsuario();
			String pontuacaoTotal = String.valueOf(dadosAtualizadosDoRanking.getPontuacaoTotal());
			
			
			HttpClient httpClient;
			String url_select;
			ArrayList<NameValuePair> nameValuePairs;
			HttpPost httpPost;
			HttpResponse httpResponse;
			String content;
			HttpEntity entity;
			url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/atualizarusuarionoranking.php";//android nao aceita localhost, tem de ser seu IP
			nameValuePairs = new ArrayList<NameValuePair>();
			
			httpClient = new DefaultHttpClient();

		    httpPost = new HttpPost(url_select);
		    nameValuePairs.add(new BasicNameValuePair("id_usuario", idUsuario));
		    nameValuePairs.add(new BasicNameValuePair("derrotas_competicao", derrotasCompeticao));		   
		    nameValuePairs.add(new BasicNameValuePair("vitorias_competicao", vitoriasCompeticao));
		    nameValuePairs.add(new BasicNameValuePair("pontuacao_total", pontuacaoTotal));
		    	
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
		    httpResponse = httpClient.execute(httpPost);
		    entity = httpResponse.getEntity();
		    
		    //
		    // Read the contents of an entity and return it as a String.
		    //
		    content = EntityUtils.toString(entity);
		    
		    System.out.println("oi");
		    pegarPosicaoJogadorNoRanking();
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
		return null;
		
	}
	
	private void pegarPosicaoJogadorNoRanking()
	{
		SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(this.telaModoCompeticao.getApplicationContext());
		

		
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuarionoranking.php";//android nao aceita localhost, tem de ser seu IP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeJogadorArmazenado));
            	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //
            // Read the contents of an entity and return it as a String.
            //
            String content = httpEntity.toString();
            System.out.println("oi");

            // Read content & Log
            InputStream inputStream = httpEntity.getContent();
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
            this.result = sBuilder.toString();
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
	}
	
	@Override
	protected void onPostExecute(Void v) {
		 try {
	            JSONArray jArray = new JSONArray(result);
	            System.out.println("para testes");
	            if(jArray.length() > 0)
	            {
	            	for(int i=0; i < jArray.length(); i++) {

		                JSONObject jObject = jArray.getJSONObject(i);
		                
		                String idUsuario = jObject.getString("id_usuario");
		                int pontuacao = Integer.valueOf(jObject.getString("pontuacao_total"));
		                int vitoriasCompeticao = Integer.valueOf(jObject.getString("vitorias_competicao"));
		                int derrotasCompeticao = Integer.valueOf(jObject.getString("derrotas_competicao"));
		                int posicaoDoJogadorNoRanking = Integer.valueOf(jObject.getString("position"));
		                
		                DadosUsuarioNoRanking dadosObtidosJogadorNoRanking = new DadosUsuarioNoRanking(idUsuario, pontuacao, vitoriasCompeticao, derrotasCompeticao, posicaoDoJogadorNoRanking);
		                //mandar pro Singleton que guarda esses dados
		                SingletonGuardaDadosUsuarioNoRanking singletonGuardaDadosRanking = SingletonGuardaDadosUsuarioNoRanking.getInstance();
		                singletonGuardaDadosRanking.setDadosSalvosUsuarioNoRanking(dadosObtidosJogadorNoRanking);
		                
		                this.popupDeProgresso.dismiss();
		            	this.telaModoCompeticao.mudarParaTelaFimDeJogoComRanking();
		               
		            } // End Loop
	            }
	            else
                {
	            	this.popupDeProgresso.dismiss();
                }
	            
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	        }
	}
	

}
