package br.ufrn.dimap.pairg.sumosensei;

import java.util.ArrayList;
import java.util.LinkedList;

import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.domodocasual.AssociaCategoriaComIcone;

import doteppo.AdapterListViewKanjisErrados;
import doteppo.AdapterListaKanjisPraMemorizar;
import doteppo.ArmazenaMostrarDicaTreinamento;
import doteppo.ArmazenaMostrarRegrasTreinamento;
import doteppo.DadosDeKanjiMemorizar;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import br.ufrn.dimap.pairg.sumosensei.app.R;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cenario.SimpleGestureFilter;
import cenario.SimpleGestureFilter.SimpleGestureListener;

public class VerPalavrasTreinadasTeppoActivity extends ActivityDoJogoComSom implements SimpleGestureListener{
	
	private int indiceCategoriaAtual;//indice da categoria das palavras sendo apresentadas na lista atualmente
	private LinkedList<String> categoriasEscolhidasPraTreinar;
	private boolean mostrarDicasTeppoNovamente;
	private LinkedList<ImageView> botoesCategoriasTreinadas;
	private SimpleGestureFilter detector;//detecta swipe

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_palavras_treinadas_teppo);
		
		mostrarDicasTeppoNovamente = true;
		
		//mudar a fonte dos textos...
		
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	    TextView textoMostrarKanjisMemorizar = (TextView)findViewById(R.id.texto_mostrar_kanjis_memorizar);
	    textoMostrarKanjisMemorizar.setTypeface(tfBrPraTexto);
	    String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	    TextView tituloDaTela = (TextView) findViewById(R.id.textoTituloPalavrasTreinadas);
	    tituloDaTela.setTypeface(tf);
		
		//pegar as categorias escolhidas para jogar...
	    this.setarCategoriasTreinadasNoTeppoActivity();
		 GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		 categoriasEscolhidasPraTreinar = guardaDadosDaPartida.getCategoriasTreinadasNaPartida();
		 //agora, pegar os kanjis da primeira categoria...
		 this.indiceCategoriaAtual = 0;//primeira categoria
		 String umaCategoria = categoriasEscolhidasPraTreinar.get(0);
					
		 
		 ArmazenaKanjisPorCategoria sabeKanjisDasCategorias = ArmazenaKanjisPorCategoria.pegarInstancia();
		 LinkedList<KanjiTreinar> kanjisDaCategoria = sabeKanjisDasCategorias.getListaKanjisTreinar(umaCategoria);
		 //agora, transformar essa lista de kanjis em array list de DadosKanjiMemorizar pra botar na ListView
		 ArrayList<DadosDeKanjiMemorizar> dadosDeKanjiMemorizar = new ArrayList<DadosDeKanjiMemorizar>();
		 for(int k = 0; k < kanjisDaCategoria.size(); k++)
		 {
			 KanjiTreinar umKanjiPraTreinar = kanjisDaCategoria.get(k);
			 String kanjiMemorizar = umKanjiPraTreinar.getKanji();
			 String hiraganaMemorizar = umKanjiPraTreinar.getHiraganaDoKanji();
			 String traducaoMemorizar = umKanjiPraTreinar.getTraducaoEmPortugues();
			 DadosDeKanjiMemorizar umKanjiMemorizar = new DadosDeKanjiMemorizar(kanjiMemorizar, hiraganaMemorizar, traducaoMemorizar);
			 dadosDeKanjiMemorizar.add(umKanjiMemorizar);
		 }
		 //falta agora associar essa lista ao adapter para o listview
		 AdapterListaKanjisPraMemorizar adapterKanjisMemorizar = new AdapterListaKanjisPraMemorizar
					(this, R.layout.item_palavra_pra_treinar, dadosDeKanjiMemorizar);
		 
		 ListView listaKanjisMemorizar = (ListView) findViewById(R.id.listaPalavrasTreinadas);
		 listaKanjisMemorizar.setAdapter(adapterKanjisMemorizar);
		 
		// Detect touched area 
         detector = new SimpleGestureFilter(this,this);
		 
		 
	}
	
	public void setarCategoriasTreinadasNoTeppoActivity()
	{
		this.botoesCategoriasTreinadas = new LinkedList<ImageView>();
		LinearLayout layoutBotoesCategorias = (LinearLayout) findViewById(R.id.botoesCategorias);
		LinkedList<String> categoriasSelecionadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
		Integer [] indicesImagensCategoriasTreinadas = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadasPequenoProTeppo(categoriasSelecionadas); 
		for(int i = 0; i < indicesImagensCategoriasTreinadas.length; i++)
		{
			int umIndiceImagemCategoriaSelecionada = indicesImagensCategoriasTreinadas[i];
			ImageView imageViewCategoria = new ImageView(this);
			imageViewCategoria.setId(i);
			imageViewCategoria.setImageResource(umIndiceImagemCategoriaSelecionada);
			LayoutParams params = new LayoutParams(
			        LayoutParams.WRAP_CONTENT,      
			        LayoutParams.WRAP_CONTENT
			);
			if(umIndiceImagemCategoriaSelecionada > 0)
			{
				Resources r = getResources();
				int px = (int) TypedValue.applyDimension(
				        TypedValue.COMPLEX_UNIT_DIP,
				        5, 
				        r.getDisplayMetrics()
				);
				params.setMargins(px, 0, 0, 0);
			}
			imageViewCategoria.setLayoutParams(params);
			imageViewCategoria.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//MUDAR KANJIS DE ACORDO COM A SELECAO
					int indiceDaCategoria = v.getId();
					mudarListaPalavrasTreinadasDeAcordoComIndice(indiceDaCategoria);
					
				}

				
			});
			layoutBotoesCategorias.addView(imageViewCategoria);
			//deixar transparente o botao, exceto se ele for o primeiro
			if(i > 0)
			{
				AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
				alpha.setDuration(0); // Make animation instant
				alpha.setFillAfter(true); // Tell it to persist after the animation ends
				// And then on your imageview
				imageViewCategoria.startAnimation(alpha);
			}
			
			this.botoesCategoriasTreinadas.add(imageViewCategoria);
		}
		
	}
	
	public void mudarListaPalavrasTreinadasDeAcordoComIndice(int indiceDaCategoria) 
	{
		//primeiro, deixar os botoes das categorias tudo invisivel, exceto o selecionado
		for(int i = 0; i < botoesCategoriasTreinadas.size(); i++)
		{
			ImageView umBotaoCategoriaTreinada = botoesCategoriasTreinadas.get(i);
			if(i != indiceDaCategoria)
			{
				AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
				alpha.setDuration(0); // Make animation instant
				alpha.setFillAfter(true); // Tell it to persist after the animation ends
				// And then on your imageview
				umBotaoCategoriaTreinada.startAnimation(alpha);
			}
			else
			{
				//deixar o botao claro
				AlphaAnimation alpha = new AlphaAnimation(1.0F, 1.0F); // change values as you want
				alpha.setDuration(0); // Make animation instant
				alpha.setFillAfter(true); // Tell it to persist after the animation ends
				// And then on your imageview
				umBotaoCategoriaTreinada.startAnimation(alpha);
				
			}
		}
		
		LinkedList<String> categoriasSelecionadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
		String nomeCategoriaSeguinte = categoriasSelecionadas.get(indiceDaCategoria);
		
		
		ArmazenaKanjisPorCategoria sabeKanjisDasCategorias = ArmazenaKanjisPorCategoria.pegarInstancia();
		 LinkedList<KanjiTreinar> kanjisDaCategoria = sabeKanjisDasCategorias.getListaKanjisTreinar(nomeCategoriaSeguinte);
		 //agora, transformar essa lista de kanjis em array list de DadosKanjiMemorizar pra botar na ListView
		 ArrayList<DadosDeKanjiMemorizar> dadosDeKanjiMemorizar = new ArrayList<DadosDeKanjiMemorizar>();
		 for(int k = 0; k < kanjisDaCategoria.size(); k++)
		 {
			 KanjiTreinar umKanjiPraTreinar = kanjisDaCategoria.get(k);
			 String kanjiMemorizar = umKanjiPraTreinar.getKanji();
			 String hiraganaMemorizar = umKanjiPraTreinar.getHiraganaDoKanji();
			 String traducaoMemorizar = umKanjiPraTreinar.getTraducaoEmPortugues();
			 DadosDeKanjiMemorizar umKanjiMemorizar = new DadosDeKanjiMemorizar(kanjiMemorizar, hiraganaMemorizar, traducaoMemorizar);
			 dadosDeKanjiMemorizar.add(umKanjiMemorizar);
		 }
		 //falta agora setar a nova lista para o existente adapter do listview...
		 
		 ListView listaKanjisMemorizar = (ListView) findViewById(R.id.listaPalavrasTreinadas);
		 ListAdapter adapterDaListView = listaKanjisMemorizar.getAdapter();
		 if(adapterDaListView instanceof AdapterListaKanjisPraMemorizar)
		 {
			 AdapterListaKanjisPraMemorizar adapterListaKanjisMemorizar = (AdapterListaKanjisPraMemorizar) adapterDaListView;
			 adapterListaKanjisMemorizar.setListItems(dadosDeKanjiMemorizar);
		 }
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void iniciarActivityJogo(View view)
	{
		mudarMusicaDeFundo(R.raw.headstart);
		 Intent iniciaTelaTeppo = new Intent(VerPalavrasTreinadasTeppoActivity.this, TreinoTeppo.class);
		  startActivity(iniciaTelaTeppo);
		  finish();
	}
	
	public void mudarValorMostrarKanjisMemorizar(View view)
	{
		Button checkboxMostrarRegrasNovamente = (Button)findViewById(R.id.checkbox_mostrar_kanjis_memorizar);
		if(this.mostrarDicasTeppoNovamente == false)
		{
			this.mostrarDicasTeppoNovamente = true;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarDicasTeppoNovamente = false;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		ArmazenaMostrarDicaTreinamento guardaConfiguracoes = ArmazenaMostrarDicaTreinamento.getInstance();
		guardaConfiguracoes.alterarMostrarDicaDoTreinamento(getApplicationContext(), mostrarDicasTeppoNovamente);
	}
	
	/**
	 * REFERENTE AO SWIPE
	 */
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
    @Override
     public void onSwipe(int direction) 
    {
      String str = "";
      
      switch (direction) {
      
      case SimpleGestureFilter.SWIPE_RIGHT : 
    	  //str = "Swipe Right";
    	  //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    	  this.indiceCategoriaAtual = indiceCategoriaAtual - 1;
    	  if(indiceCategoriaAtual < 0)
    	  {
    		  int indiceUltimaCategoria = this.categoriasEscolhidasPraTreinar.size() - 1;
    		  indiceCategoriaAtual = indiceUltimaCategoria;
    	  }
    	  this.mudarListaPalavrasTreinadasDeAcordoComIndice(indiceCategoriaAtual);
    	  break;
      case SimpleGestureFilter.SWIPE_LEFT :  
    	  //str = "Swipe Left";
    	  //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    	  this.indiceCategoriaAtual = indiceCategoriaAtual + 1;
    	  int indiceUltimaCategoria = this.categoriasEscolhidasPraTreinar.size() - 1;
    	  if(indiceCategoriaAtual > indiceUltimaCategoria)
    	  {
    		  indiceCategoriaAtual = 0;
    	  }
    	  this.mudarListaPalavrasTreinadasDeAcordoComIndice(indiceCategoriaAtual);
          break;
      
      
      }
     }
      
     @Override
     public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
     }
     
     /**
 	 * FIM REFERENTE AO SWIPE
 	 */
}
