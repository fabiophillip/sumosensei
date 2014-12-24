package docompeticao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
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
import bancodedados.DadosPartidaParaOLog;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCompeticao;

public class EnviarDadosDaPartidaParaLogCompeticaoTask extends AsyncTask<DadosPartidaParaOLog, String, String> {
	private String result = "";
	private InputStream inputStream = null;
	private TelaModoCompeticao telaCompeticao;
	private ProgressDialog dialogSumirAposCompleto;
	
	public EnviarDadosDaPartidaParaLogCompeticaoTask(TelaModoCompeticao telaCompeticao, ProgressDialog caixaDialogo)
	{
		this.telaCompeticao = telaCompeticao;
		this.dialogSumirAposCompleto = caixaDialogo;
	}
	
	@Override
	protected String doInBackground(DadosPartidaParaOLog... dadosPartida) 
	{
		DadosPartidaParaOLog umDadosPartida = dadosPartida[0];
		String idsCategoriasSeparadosPorVirgula = umDadosPartida.getCategoria();
		String usernameJogador = umDadosPartida.getUsernameJogador();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String data = dateFormat.format(date); 
		String pontuacao = String.valueOf(umDadosPartida.getPontuacao());
		
		LinkedList<KanjiTreinar> palavrasAcertadas = umDadosPartida.getPalavrasAcertadas();
		LinkedList<KanjiTreinar> palavrasErradas = umDadosPartida.getPalavrasErradas();
		LinkedList<KanjiTreinar> palavrasJogadas = umDadosPartida.getPalavrasJogadas();
		//tem que juntar tudo isso num string de ids categorias, kanjis e se acertou, errou...
		String idsCategoriasTodasPalavras = "";
		String idsKanjisTodasPalavras = "";
		String acertouErrouJogou = "";
		idsCategoriasTodasPalavras = this.pegarIdsCategoriasTreinadasSeparadasPorVirgula(palavrasJogadas);
		idsKanjisTodasPalavras = this.transformarLinkedListKanjisEmString(palavrasJogadas);
		acertouErrouJogou = this.criarStringAcertouErrouJogouComKanjis(palavrasJogadas, "treinada");
		
		String stringSomarCategoriasPalavrasAcertadas = this.pegarIdsCategoriasTreinadasSeparadasPorVirgula(palavrasAcertadas);
		if(stringSomarCategoriasPalavrasAcertadas.length() > 0)
		{
			idsCategoriasTodasPalavras = idsCategoriasTodasPalavras + "," + stringSomarCategoriasPalavrasAcertadas;
			idsKanjisTodasPalavras = idsKanjisTodasPalavras + "," + this.transformarLinkedListKanjisEmString(palavrasAcertadas);
			acertouErrouJogou = acertouErrouJogou + "," + this.criarStringAcertouErrouJogouComKanjis(palavrasAcertadas, "acertada");
		}
		String stringSomarCategoriasPalavrasErradas = this.pegarIdsCategoriasTreinadasSeparadasPorVirgula(palavrasErradas);
		if(stringSomarCategoriasPalavrasErradas.length() > 0)
		{
			idsCategoriasTodasPalavras = idsCategoriasTodasPalavras + "," + this.pegarIdsCategoriasTreinadasSeparadasPorVirgula(palavrasErradas);
			idsKanjisTodasPalavras = idsKanjisTodasPalavras + "," + this.transformarLinkedListKanjisEmString(palavrasErradas);
			acertouErrouJogou = acertouErrouJogou + "," + this.criarStringAcertouErrouJogouComKanjis(palavrasErradas, "errada");
			
		}
		
		
		
		
		
		
		String usernameAdversario = umDadosPartida.getUsernameAdversario();
		String voceganhououperdeu = umDadosPartida.getVoceGanhouOuPerdeu();
		
		
		
		try
		{
			ArrayList<NameValuePair> nameValuePairs;
			//pegar o id do jogador
			String idJogador = pegarIdDeUmJogador(usernameJogador);
			//agora, pegar o id do jogador adversario
			String idAdversario = pegarIdDeUmJogador(usernameAdversario);
			
			//agora, inserir no registro as categorias treinadas na partida...
			
			HttpClient httpClient = new DefaultHttpClient();

			String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidanologcompeticao.php";//android nao aceita localhost, tem de ser seu IP
            HttpPost httpPost = new HttpPost(url_select);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id_usuario", idJogador));
            nameValuePairs.add(new BasicNameValuePair("data", data));
            nameValuePairs.add(new BasicNameValuePair("pontuacao", pontuacao));
            nameValuePairs.add(new BasicNameValuePair("id_adversario", idAdversario));
            nameValuePairs.add(new BasicNameValuePair("voceganhououperdeu", voceganhououperdeu));
            	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost); 
            
            String idPartidaAcabouDeInserir = pegarIdUltimaPartidaInserida(usernameJogador);
            
            //agora, inserir as categorias treinadas na partida...
            inserirCategoriasRegistroModoCasual(
					idsCategoriasSeparadosPorVirgula, 
					idPartidaAcabouDeInserir);
            
            inserirPalavrasTreinadasNoBD(idsCategoriasTodasPalavras,
					idsKanjisTodasPalavras, acertouErrouJogou,
					idPartidaAcabouDeInserir);
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



	private void inserirPalavrasTreinadasNoBD(
			String idsCategoriasTodasPalavras, String idsKanjisTodasPalavras,
			String acertouErrouJogou, String idPartidaAcabouDeInserir)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		//por fim, inserir as palavras treinadas na partida...
		HttpClient httpClientInserirPalavrasTreinadas = new DefaultHttpClient();
		String url_selectInserirPalavrasTreinadas = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserir_palavras_treinadas_competicao.php";//android nao aceita localhost, tem de ser seu IP
		HttpPost httpPostInserirPalavrasTreinadas = new HttpPost(url_selectInserirPalavrasTreinadas);
		ArrayList<NameValuePair> nameValuePairsInserirPalavrasTreinadas = new ArrayList<NameValuePair>();
		nameValuePairsInserirPalavrasTreinadas.add(new BasicNameValuePair("id_partida", idPartidaAcabouDeInserir));
		nameValuePairsInserirPalavrasTreinadas.add(new BasicNameValuePair("categorias", idsCategoriasTodasPalavras));
		nameValuePairsInserirPalavrasTreinadas.add(new BasicNameValuePair("id_palavras",idsKanjisTodasPalavras));
		nameValuePairsInserirPalavrasTreinadas.add(new BasicNameValuePair("treinadaerradaouacertadas", acertouErrouJogou ));
		
		
		
		
		httpPostInserirPalavrasTreinadas.setEntity(new UrlEncodedFormEntity(nameValuePairsInserirPalavrasTreinadas,"UTF-8"));
		httpPostInserirPalavrasTreinadas.setEntity(new UrlEncodedFormEntity(nameValuePairsInserirPalavrasTreinadas,"UTF-8"));
		HttpResponse httpResponseInserirPalavrasTreinadas = httpClientInserirPalavrasTreinadas.execute(httpPostInserirPalavrasTreinadas);
		HttpEntity entity = httpResponseInserirPalavrasTreinadas.getEntity();
		String htmlResponse = EntityUtils.toString(entity);
		System.out.println("teste");
	}



	private void inserirCategoriasRegistroModoCasual(
			String idsCategoriasSeparadosPorVirgula,
			String idPartidaAcabouDeInserir)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		HttpClient httpClientInserirCategoria = new DefaultHttpClient();

		String url_selectInserirCategoria = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserir_categorias_registro_partida_competicao.php";//android nao aceita localhost, tem de ser seu IP
		HttpPost httpPostInserirCategoria = new HttpPost(url_selectInserirCategoria);
		ArrayList<NameValuePair> nameValuePairsInserirCategoria = new ArrayList<NameValuePair>();
		nameValuePairsInserirCategoria.add(new BasicNameValuePair("id_partida", idPartidaAcabouDeInserir));
		nameValuePairsInserirCategoria.add(new BasicNameValuePair("categorias", idsCategoriasSeparadosPorVirgula));
			
		httpPostInserirCategoria.setEntity(new UrlEncodedFormEntity(nameValuePairsInserirCategoria,"UTF-8"));
		HttpResponse httpResponseInserirCategoria = httpClientInserirCategoria.execute(httpPostInserirCategoria);
		HttpEntity entity = httpResponseInserirCategoria.getEntity();
		String htmlResponse = EntityUtils.toString(entity);
	}

	

	private String pegarIdDeUmJogador(String usernameJogador)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException, JSONException {
		String userId = "";
		//primeiro, pegar o user id
		
		HttpClient httpClient = new DefaultHttpClient();

		//antigo: http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidanolog.php
		String url_select;
		ArrayList<NameValuePair> nameValuePairs;
		url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuariopornome.php";//android nao aceita localhost, tem de ser seu IP
		nameValuePairs = new ArrayList<NameValuePair>();
		HttpPost httpPost = new HttpPost(url_select);
		nameValuePairs.add(new BasicNameValuePair("nome_usuario", usernameJogador));
			
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
	
	private String pegarIdUltimaPartidaInserida(String usernameJogador)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		String idPartidaDoTreinamento = "-1";
		//em seguida, pegar o id da nova partida que acabou de ser criada.
		String url_pegar_id = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_id_partida_competicao.php";
		ArrayList<NameValuePair> nomeEEmailQuemCriouSala = new ArrayList<NameValuePair>();
		HttpClient httpClientPegarIdSala = new DefaultHttpClient();
		HttpPost httpPostPegarIdSala = new HttpPost(url_pegar_id);
		nomeEEmailQuemCriouSala.add(new BasicNameValuePair("nome_usuario", usernameJogador));
		httpPostPegarIdSala.setEntity(new UrlEncodedFormEntity(nomeEEmailQuemCriouSala,"UTF-8"));
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
		    JSONArray jArray = new JSONArray(result);
	        for(int i=0; i < jArray.length(); i++) {

	            JSONObject jObject = jArray.getJSONObject(i);
	            
	            idPartidaDoTreinamento = jObject.getString("ultima_partida");
	           
	        }

		} catch (Exception e) {
		    Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
		}
		
		return idPartidaDoTreinamento;
	}
	
	/*o banco de dados nao pode armazenar linkedlist, por isso transformaremos uma linkedlist de kanjis em string*/
	private String transformarLinkedListKanjisEmString(LinkedList<KanjiTreinar> listaKanjis)
	{
		String listaEmString = "";
		for(int i = 0; i < listaKanjis.size(); i++)
		{
			KanjiTreinar umaPalavra = listaKanjis.get(i);
			listaEmString = listaEmString + umaPalavra.getIdKanji();
			if(i < listaKanjis.size() - 1)
			{
				listaEmString = listaEmString + ",";
			}
		}
		
		return listaEmString;
	}
	
	private String pegarIdsCategoriasTreinadasSeparadasPorVirgula(LinkedList<KanjiTreinar> listaKanjis)
	{
		String listaEmString = "";
		for(int i = 0; i < listaKanjis.size(); i++)
		{
			KanjiTreinar umaPalavra = listaKanjis.get(i);
			String categoriaUmaPalavra = umaPalavra.getCategoriaAssociada();
			int idCategoriaUmaPalavra = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdDaCategoria(categoriaUmaPalavra);
			listaEmString = listaEmString + idCategoriaUmaPalavra;
			if(i < listaKanjis.size() - 1)
			{
				listaEmString = listaEmString + ",";
			}
		}
		
		return listaEmString;
	}
	
	private String criarStringAcertouErrouJogouComKanjis(LinkedList<KanjiTreinar> listaKanjis, String acertouErrouJogou)
	{
		String acertouErrouJogouTodosKanjis = "";
		for(int i = 0; i < listaKanjis.size(); i++)
		{
			
			acertouErrouJogouTodosKanjis = acertouErrouJogouTodosKanjis + acertouErrouJogou;
			if(i < listaKanjis.size() - 1)
			{
				acertouErrouJogouTodosKanjis = acertouErrouJogouTodosKanjis + ",";
			}
		}
		
		return acertouErrouJogouTodosKanjis;
		
	}
	
	@Override
	public void onPostExecute(String result)
	{
		if(this.dialogSumirAposCompleto != null)
		{
			this.dialogSumirAposCompleto.dismiss();
		}
		
		this.telaCompeticao.atualizarVitoriasDerrotasDoUsuario();
	}

}
