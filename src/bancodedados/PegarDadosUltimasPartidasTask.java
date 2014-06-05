package bancodedados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.phiworks.sumosenseinew.DadosPartidasAnteriores;



import android.os.AsyncTask;
import android.util.Log;

public class PegarDadosUltimasPartidasTask extends AsyncTask<String, String, Void>  
{
	private InputStream inputStream;
	private String result;
	private DadosPartidasAnteriores telaDadosPartidasAnteriores;
	
	public PegarDadosUltimasPartidasTask(DadosPartidasAnteriores telaDadosPartidasAnteriores)
	{
		this.telaDadosPartidasAnteriores = telaDadosPartidasAnteriores;
	}
	
	@Override
	protected Void doInBackground(String... emailJogador) 
	{
		//antigo: http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarlogjogadorjson.php
		String url_select = "http://192.168.0.104/amit/pegarlogjogadorjson.php";//android nao aceita localhost, tem de ser seu IP
	       
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

	        try {
	            // Set up HTTP post

	            // HttpClient is more then less deprecated. Need to change to URLConnection
	            HttpClient httpClient = new DefaultHttpClient();

	            HttpPost httpPost = new HttpPost(url_select);
	            
	            String emailjogador = emailJogador[0];
	            param.add(new BasicNameValuePair("emailjogador", emailjogador));
	            
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
		
	protected void onPostExecute(Void v) 
	{
		LinkedList<DadosPartidaParaOLog> dadosPartidasParaLog = new LinkedList<DadosPartidaParaOLog>();
		        //parse JSON data
		        try {
		            JSONArray jArray = new JSONArray(result);    
		            for(int i=0; i < jArray.length(); i++) {

		                JSONObject jObject = jArray.getJSONObject(i);
		                
		                String email = jObject.getString("email");
		            	String data = jObject.getString("data");
		            	String categoria = jObject.getString("categoria"); //pode ser mais de uma categoria separadas por ; Ex: "cotidiano 1; verbos;"
		            	int pontuacao = Integer.valueOf(jObject.getString("pontuacao"));
		            	String palavrasAcertadasString = jObject.getString("palavrasacertadas");
		            	String palavrasErradasString = jObject.getString("palavraserradas");
		            	String palavrasJogadasString = jObject.getString("palavrasjogadas");
		            	String jogoAssociado = jObject.getString("jogoassociado"); //se eh o karuta kanji ou sumo sensei
		            	String eMailAdversario = jObject.getString("emailadversario");
		            	String voceGanhouOuPerdeu = jObject.getString("voceganhououperdeu");
		                
		            	DadosPartidaParaOLog dadosLog = new DadosPartidaParaOLog();
		            	dadosLog.setCategoria(categoria);
		            	dadosLog.setData(data);
		            	dadosLog.setEmail(email);
		            	dadosLog.seteMailAdversario(eMailAdversario);
		            	dadosLog.setJogoAssociado(jogoAssociado);
		            	dadosLog.setPontuacao(pontuacao);
		            	dadosLog.setVoceGanhouOuPerdeu(voceGanhouOuPerdeu);
		            	
		            	LinkedList<KanjiTreinar> palavrasAcertadas = extrairKanjisTreinar(palavrasAcertadasString);
		            	LinkedList<KanjiTreinar> palavrasErradas = extrairKanjisTreinar(palavrasErradasString);
		            	LinkedList<KanjiTreinar> palavrasJogadas = extrairKanjisTreinar(palavrasJogadasString);
		            	dadosLog.setPalavrasAcertadas(palavrasAcertadas);
		            	dadosLog.setPalavrasErradas(palavrasErradas);
		            	dadosLog.setPalavrasJogadas(palavrasJogadas);
		            	
		            	dadosPartidasParaLog.add(dadosLog);

		            } // End Loop
		            
		            this.telaDadosPartidasAnteriores.atualizarListViewComAsUltimasPartidas(dadosPartidasParaLog);
		           
		        } catch (JSONException e) {
		            Log.e("JSONException", "Error: " + e.toString());
		        }
		        
	}

	/*pega a string do bd e transforma em montes de kanjis como era antes de enviar ao bd. Ex: au|verbos;kau|verbos...*/
	private LinkedList<KanjiTreinar> extrairKanjisTreinar(String kanjisTreinarEmString)
	{
		if(kanjisTreinarEmString.contains(";") == false)
		{
			//nao ha kanjis nessa string, vamos retornar linkedlist vazia
			return new LinkedList<KanjiTreinar>();
		}
		else
		{
			LinkedList<KanjiTreinar> kanjisTreinar = new LinkedList<KanjiTreinar>();
			String[] kanjisECategoriasComBarra = kanjisTreinarEmString.split(";");
			for(int i = 0; i < kanjisECategoriasComBarra.length; i++)
			{
				String umKanjiECategoria = kanjisECategoriasComBarra[i];
				String[] kanjiECategoria = umKanjiECategoria.split("\\|");
				String kanji = kanjiECategoria[0];
				String categoria = kanjiECategoria[1];
				
				KanjiTreinar kanjiTreinar = ArmazenaKanjisPorCategoria.pegarInstancia().acharKanji(categoria, kanji);
				kanjisTreinar.add(kanjiTreinar);
			}
			
			return kanjisTreinar;
		}
	}

}
