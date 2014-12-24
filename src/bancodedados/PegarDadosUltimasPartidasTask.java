package bancodedados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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

import br.ufrn.dimap.pairg.sumosensei.DadosPartidasAnteriores;



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
	protected Void doInBackground(String... nomeJogador) 
	{
		//antigo: http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarlogjogadorjson.php
		String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarlogjogadorjsoncompeticao.php";//android nao aceita localhost, tem de ser seu IP
	       
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

	        try {
	            // Set up HTTP post

	            // HttpClient is more then less deprecated. Need to change to URLConnection
	            HttpClient httpClient = new DefaultHttpClient();

	            HttpPost httpPost = new HttpPost(url_select);
	            
	            String nomeDojogador = nomeJogador[0];
	            param.add(new BasicNameValuePair("username", nomeDojogador));
	            
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
		                
		                String usernameJogador = jObject.getString("usuario_da_partida");
		            	String data = jObject.getString("data");
		            	String categoria = jObject.getString("categorias_da_partida"); //pode ser mais de uma categoria separadas por ; Ex: "cotidiano 1; verbos;"
		            	int pontuacao = Integer.valueOf(jObject.getString("pontuacao"));
		            	String palavrasTodasJuntas = jObject.getString("palavras");
		            	String categoriasPalavrasTodasJuntas = jObject.getString("categorias_das_palavras");
		            	String treinadaAcertadaErradaTodasJuntas = jObject.getString("treinadaerradaouacertadas_das_palavras");
		            	String nomeAdversario = jObject.getString("nome_adversario");
		            	String voceGanhouOuPerdeu = jObject.getString("voceganhououperdeu");
		                
		            	DadosPartidaParaOLog dadosLog = new DadosPartidaParaOLog();
		            	dadosLog.setCategoria(categoria);
		            	dadosLog.setData(data);
		            	dadosLog.setUsernameJogador(usernameJogador);
		            	dadosLog.setUsernameAdversario(nomeAdversario);
		            	dadosLog.setPontuacao(pontuacao);
		            	dadosLog.setVoceGanhouOuPerdeu(voceGanhouOuPerdeu);
		            	
		            	HashMap<String, LinkedList<KanjiTreinar>> palavrasAcertadasErradasJogadas = extrairKanjisTreinar(palavrasTodasJuntas, categoriasPalavrasTodasJuntas, treinadaAcertadaErradaTodasJuntas );
		            	LinkedList<KanjiTreinar> palavrasAcertadas = palavrasAcertadasErradasJogadas.get("acertadas");
		            	LinkedList<KanjiTreinar> palavrasErradas = palavrasAcertadasErradasJogadas.get("erradas");
		            	LinkedList<KanjiTreinar> palavrasJogadas = palavrasAcertadasErradasJogadas.get("jogadas");
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
	private HashMap<String, LinkedList<KanjiTreinar>> extrairKanjisTreinar(String palavrasTodasJuntas, String categoriasPalavrasJuntas, String acertadaErradaTreinadaJuntas)
	{
		HashMap<String, LinkedList<KanjiTreinar>> kanjisTreinadosErradosAcertados = new HashMap<String, LinkedList<KanjiTreinar>>();
		LinkedList<KanjiTreinar> kanjisAcertados = new LinkedList<KanjiTreinar>();
		LinkedList<KanjiTreinar> kanjisErrados = new LinkedList<KanjiTreinar>();
		LinkedList<KanjiTreinar> kanjisJogados = new LinkedList<KanjiTreinar>();
		
		String [] palavrasTreinadasSeparadas = palavrasTodasJuntas.split(",");
		String [] categoriasPalavrasSeparadas = categoriasPalavrasJuntas.split(",");
		String [] listaPalavraAcertadaErradaOuTreinada = acertadaErradaTreinadaJuntas.split(",");
		
		for(int i = 0; i < palavrasTreinadasSeparadas.length; i++)
		{
			String umaPalavraTreinada = palavrasTreinadasSeparadas[i];
			String categoriaDaUmaPalavraTreinada = categoriasPalavrasSeparadas[i];
			String acertouTreinouErrouEssaPalavra = listaPalavraAcertadaErradaOuTreinada[i];
			if(categoriaDaUmaPalavraTreinada.startsWith(" "))
			{
				categoriaDaUmaPalavraTreinada = categoriaDaUmaPalavraTreinada.substring(1);
			}
			if(umaPalavraTreinada.startsWith(" "))
			{
				umaPalavraTreinada = umaPalavraTreinada.substring(1);
			}
			if(acertouTreinouErrouEssaPalavra.startsWith(" "))
			{
				acertouTreinouErrouEssaPalavra = acertouTreinouErrouEssaPalavra.substring(1);
			}
			KanjiTreinar kanjiTreinar = ArmazenaKanjisPorCategoria.pegarInstancia().acharKanji(categoriaDaUmaPalavraTreinada, umaPalavraTreinada);
			//falta adicionar a uma das listas...
			if(acertouTreinouErrouEssaPalavra.compareTo("treinada") == 0)
			{
				kanjisJogados.add(kanjiTreinar);
			}
			else if(acertouTreinouErrouEssaPalavra.compareTo("acertada") == 0)
			{
				kanjisAcertados.add(kanjiTreinar);
			}
			else
			{
				kanjisErrados.add(kanjiTreinar);
			}
		}
		
		kanjisTreinadosErradosAcertados.put("acertadas", kanjisAcertados);
		kanjisTreinadosErradosAcertados.put("erradas", kanjisErrados);
		kanjisTreinadosErradosAcertados.put("jogadas", kanjisJogados);
		
		return kanjisTreinadosErradosAcertados;
	}

}
