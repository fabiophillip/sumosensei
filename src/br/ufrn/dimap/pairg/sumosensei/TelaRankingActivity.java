package br.ufrn.dimap.pairg.sumosensei;

import java.util.ArrayList;
import java.util.LinkedList;

import docompeticao.AdapterListViewRanking;
import docompeticao.DadosDeRanking;
import docompeticao.DadosUsuarioNoRanking;
import docompeticao.SingletonGuardaDadosUsuarioNoRanking;
import docompeticao.TaskMinhaPosicaoNoRanking;
import docompeticao.TaskPegaRanking;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;

import br.ufrn.dimap.pairg.sumosensei.app.R;
import br.ufrn.dimap.pairg.sumosensei.app.R.layout;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TelaRankingActivity extends ActivityDoJogoComSom {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_ranking);
		ListView listviewRanking = (ListView) findViewById(R.id.listviewRanking);
		View header = getLayoutInflater().inflate(R.layout.header_list_item_posicao_no_ranking, null);
		listviewRanking.addHeaderView(header);
		ProgressDialog loadingGuardandoDadosTreino =  ProgressDialog.show(this, getResources().getString(R.string.carregando_ranking), getResources().getString(R.string.por_favor_aguarde));
		TaskPegaRanking taskPegarRanking = new TaskPegaRanking(loadingGuardandoDadosTreino, this);
		taskPegarRanking.execute("sohMelhores");
	}

	

	public void voltarParaTelaCompeticao(View v)
	{
		finish();//a activity do competicao não morreu, entã basta fechar essa guia.
	}
	
	public void mostrarRankingAposCarregar(ArrayList<DadosDeRanking> dadosCarregadosRanking)
	{
		ArrayList<DadosDeRanking> dadosCarregadDadosDeRankingsSemNulo = new ArrayList<DadosDeRanking>();
		for(int i = 0;i < dadosCarregadosRanking.size(); i++)
		{
			DadosDeRanking umDadoDeRanking = dadosCarregadosRanking.get(i);
			if(umDadoDeRanking != null && umDadoDeRanking.getNomeUsuario() != null)
			{
				dadosCarregadDadosDeRankingsSemNulo.add(umDadoDeRanking);
			}
			
		}
		ListView listviewRanking = (ListView) findViewById(R.id.listviewRanking);
		AdapterListViewRanking adapterPraRanking = null;
		if(listviewRanking.getAdapter() == null)
		{
			adapterPraRanking = new AdapterListViewRanking(getApplicationContext(), R.layout.list_item_posicao_no_ranking, dadosCarregadDadosDeRankingsSemNulo);
			listviewRanking.setAdapter(adapterPraRanking);
		}
		else
		{
			ListAdapter adapterDoRanking = listviewRanking.getAdapter();
			if(adapterDoRanking instanceof AdapterListViewRanking)
			{
				adapterPraRanking =  (AdapterListViewRanking)adapterDoRanking;
				adapterPraRanking.setListItems(dadosCarregadDadosDeRankingsSemNulo);
			}
		}
	}
	
	public void verMinhaPosicaoNoRanking(View v)
	{
		ProgressDialog loadingGuardandoDadosTreino =  ProgressDialog.show(this, getResources().getString(R.string.carregando_ranking), getResources().getString(R.string.por_favor_aguarde));
		TaskMinhaPosicaoNoRanking taskChecaMinaPosicaoNoRanking = new TaskMinhaPosicaoNoRanking(loadingGuardandoDadosTreino, this);
		SingletonGuardaUsernameUsadoNoLogin guardaUsername = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		taskChecaMinaPosicaoNoRanking.execute(guardaUsername.getNomeJogador(getApplicationContext()));
	}
	
	public void carregarListaNaPosicaoDoJogador(DadosUsuarioNoRanking dadosDoUsuario, ArrayList<DadosDeRanking> dadosDeRankingsBaixados)
	{
		this.mostrarRankingAposCarregar(dadosDeRankingsBaixados);
		ListView listviewSalas = (ListView) findViewById(R.id.listviewRanking);
		int indiceJogadorNoRanking = dadosDoUsuario.getPosicaoJogadorNoRanking();
		int indiceObjetoDeCima = indiceJogadorNoRanking;
		/*int visiblePercent = getVisiblePercent(listviewSalas.getChildAt(indiceJogadorNoRanking)); //pega o primeiro item VISIVEL da listview
		if(visiblePercent != 100)
		{
			//se o primeiro item visivel da listview nao estah 100% visivel, o primeiro item visivel deveria ser o proximo
			//antes tava dando bug por exemplo: O item mais acima atualmente visivel era soh 50% visivel.
			indiceObjetoDeCima = indiceObjetoDeCima - 1;
			if(indiceObjetoDeCima)
		}*/
		listviewSalas.setSelection(indiceObjetoDeCima);
	}
	
	public static int getVisiblePercent(View v) {
	    if (v != null && v.isShown()) {
	        Rect r = new Rect();
	        v.getGlobalVisibleRect(r);
	        double sVisible = r.width() * r.height();
	        double sTotal = v.getWidth() * v.getHeight();
	        return (int) (100 * sVisible / sTotal);
	    } else {
	        return -1;
	    }
	}
	
	
	

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
}
