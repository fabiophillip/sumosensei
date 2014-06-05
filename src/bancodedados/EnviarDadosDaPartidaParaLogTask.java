package bancodedados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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



import android.os.AsyncTask;
import android.util.Log;

public class EnviarDadosDaPartidaParaLogTask extends AsyncTask<DadosPartidaParaOLog, String, String> 
{
	
	@Override
	protected String doInBackground(DadosPartidaParaOLog... dadosPartida) 
	{
		DadosPartidaParaOLog umDadosPartida = dadosPartida[0];
		String categoria = umDadosPartida.getCategoria();
		String email = umDadosPartida.getEmail();
		String data = umDadosPartida.getData();
		String pontuacao = String.valueOf(umDadosPartida.getPontuacao());
		
		LinkedList<KanjiTreinar> palavrasAcertadas = umDadosPartida.getPalavrasAcertadas();
		String palavrasacertadas = 
				this.transformarLinkedListKanjisEmString(palavrasAcertadas);
		
		LinkedList<KanjiTreinar> palavrasErradas = umDadosPartida.getPalavrasErradas();
		String palavraserradas = 
				this.transformarLinkedListKanjisEmString(palavrasErradas);
		
		LinkedList<KanjiTreinar> palavrasJogadas = umDadosPartida.getPalavrasJogadas();
		String palavrasjogadas = 
				this.transformarLinkedListKanjisEmString(palavrasJogadas);
		String jogoassociado = umDadosPartida.getJogoAssociado();
		
		String emailadversario = umDadosPartida.geteMailAdversario();
		String voceganhououperdeu = umDadosPartida.getVoceGanhouOuPerdeu();
		
		//antigo: http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidanolog.php
		String url_select = "http://192.168.0.104/amit/inserirpartidanolog.php";//android nao aceita localhost, tem de ser seu IP
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("data", data));
            nameValuePairs.add(new BasicNameValuePair("categoria", categoria));
            nameValuePairs.add(new BasicNameValuePair("pontuacao", pontuacao));
            nameValuePairs.add(new BasicNameValuePair("palavrasacertadas", palavrasacertadas));
            nameValuePairs.add(new BasicNameValuePair("palavraserradas", palavraserradas));
            nameValuePairs.add(new BasicNameValuePair("palavrasjogadas", palavrasjogadas));
            nameValuePairs.add(new BasicNameValuePair("jogoassociado", jogoassociado));
            nameValuePairs.add(new BasicNameValuePair("emailadversario", emailadversario));
            nameValuePairs.add(new BasicNameValuePair("voceganhououperdeu", voceganhououperdeu));
            	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost); 
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
	
	/*o banco de dados nao pode armazenar linkedlist, por isso transformaremos uma linkedlist de kanjis em string*/
	private String transformarLinkedListKanjisEmString(LinkedList<KanjiTreinar> listaKanjis)
	{
		String listaEmString = "";
		for(int i = 0; i < listaKanjis.size(); i++)
		{
			KanjiTreinar umaPalavra = listaKanjis.get(i);
			listaEmString = listaEmString + umaPalavra.getKanji() + "|" + umaPalavra.getCategoriaAssociada() + ";";
		}
		
		return listaEmString;
	}
	

}
