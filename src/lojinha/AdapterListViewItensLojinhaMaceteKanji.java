package lojinha;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phiworks.sumosenseinew.LojinhaMaceteKanjiActivity;
import com.phiworks.sumosenseinew.MainActivity;
import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.VerMaceteKanjiActivity;

public class AdapterListViewItensLojinhaMaceteKanji extends ArrayAdapter<MaceteKanjiParaListviewSelecionavel> {
	
	private ArrayList<MaceteKanjiParaListviewSelecionavel> arrayListMacetesDeKanjiLojinha;
	private Context contextoAplicacao;
	private LojinhaMaceteKanjiActivity telaLojinhaMaceteKanji;
	
	public AdapterListViewItensLojinhaMaceteKanji(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<MaceteKanjiParaListviewSelecionavel> maceteKanjiPraLojaArrayList, LojinhaMaceteKanjiActivity telaLojinhaMaceteKanji) {
		   super(contextoAplicacao, textViewResourceId, maceteKanjiPraLojaArrayList);
		   this.arrayListMacetesDeKanjiLojinha = new ArrayList<MaceteKanjiParaListviewSelecionavel>();
		   this.arrayListMacetesDeKanjiLojinha.addAll(maceteKanjiPraLojaArrayList);
		   this.contextoAplicacao = contextoAplicacao;
		   this.telaLojinhaMaceteKanji = telaLojinhaMaceteKanji;
		  }

	public ArrayList<MaceteKanjiParaListviewSelecionavel> getArrayListMacetesDeKanjiLojinha() {
		return arrayListMacetesDeKanjiLojinha;
	}

	public void setArrayListMacetesDeKanjiLojinha(
			ArrayList<MaceteKanjiParaListviewSelecionavel> arrayListMacetesDeKanjiLojinha) {
		this.arrayListMacetesDeKanjiLojinha = arrayListMacetesDeKanjiLojinha;
	}
	
	
	private class ViewHolder {
		   TextView name;
		   Button botaoComprar;
		  }
	
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolder holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if (convertView == null) {
	   LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.lojinha_list_item, null);
	  
	   holder = new ViewHolder();
	   holder.name = (TextView) convertView.findViewById(R.id.labelItemLojinha);
	   holder.botaoComprar = (Button) convertView.findViewById(R.id.botaoComprarLojinha);
	   
	   convertView.setTag(holder);
	   MaceteKanjiParaListviewSelecionavel maceteKanjiDaLojinha = arrayListMacetesDeKanjiLojinha.get(position); 
	   if(maceteKanjiDaLojinha.isJahComprado() == true)
	   {
		   String textoBotao = contextoAplicacao.getResources().getText(R.string.botao_visualizar_macete).toString();
		   holder.botaoComprar.setText(textoBotao);
	   }
	   
	    holder.botaoComprar.setOnClickListener( new View.OnClickListener() { 
	     public void onClick(View v) {
	    	 Button botaoComprar = (Button) v ; 
	    	 String textoBotao = botaoComprar.getText().toString();
	    	 String textoBotaoComprar = contextoAplicacao.getResources().getText(R.string.botao_comprar_item_loja).toString();
	    	 
	    	 if(textoBotao.compareTo(textoBotaoComprar) == 0)
	    	 {
	    		 //o botao tem label "comprar, eh pq o usuario ainda nao comprou"
	    		 DAOAcessaDinheiroDoJogador acessaDinheiroJogador = ConcreteDAOAcessaDinheiroDoJogador.getInstance();
		    	 int creditoQuePossui = acessaDinheiroJogador.getCreditoQuePossui(contextoAplicacao);
		    	 if(creditoQuePossui >= 1500)
		    	 {
		    		 //tem dinheiro para comprar macetes
		    		 acessaDinheiroJogador.reduzirCredito(1500, contextoAplicacao);
		    		 String textoBotaoMaceteComprado = contextoAplicacao.getResources().getText(R.string.botao_visualizar_macete).toString();
		    		 botaoComprar.setText(textoBotaoMaceteComprado);
		    		 DAOAcessaComprasMaceteKanji acessaComprasMaceteKanji = ConcreteDAOAcessaComprasMaceteKanji.getInstance();
		    		 MaceteKanjiParaListviewSelecionavel maceteKanjiDaLojinha = (MaceteKanjiParaListviewSelecionavel) botaoComprar.getTag(); 
		    		 acessaComprasMaceteKanji.inserirMaceteKanjiComprado(maceteKanjiDaLojinha.getLabelKanji(), contextoAplicacao);
		    		 TextView textviewDinheiro = (TextView) telaLojinhaMaceteKanji.findViewById(R.id.label_dinheiro);
		    		 String textoAntigoDinheiro = textviewDinheiro.getText().toString();
		    		 String [] textoAntigoDinheiroDividido = textoAntigoDinheiro.split(":");
		    		 String labelDinheiro = textoAntigoDinheiroDividido[0];
		    		 String dinheiroComMoeda = textoAntigoDinheiroDividido[1];
		    		 String moedaDoJogo = contextoAplicacao.getResources().getText(R.string.moeda_do_jogo).toString();
		    		 String dinheiroSemMoeda = dinheiroComMoeda.replace(moedaDoJogo, "");
		    		 int dinheiroJogador = Integer.valueOf(dinheiroSemMoeda);
		    		 dinheiroJogador = dinheiroJogador - 1500;
		    		 
		    		 textviewDinheiro.setText(labelDinheiro + ":" + dinheiroJogador + moedaDoJogo);
		    	 }
		    	 else
		    	 {
		    		 String textoAlerta = contextoAplicacao.getResources().getText(R.string.credito_insuficiente).toString();
		    		 Toast t = Toast.makeText(contextoAplicacao, textoAlerta, Toast.LENGTH_LONG);
		    		 t.show();
		    	 }
	    	 }
	    	 else
	    	 {
	    		 //usuario jah comprou e quer visualizar o macete...
	    		 /*Toast t = Toast.makeText(contextoAplicacao, "visualizar macete", Toast.LENGTH_LONG);
	    		 t.show();*/
	    		 MaceteKanjiParaListviewSelecionavel maceteKanjiDaLojinha = (MaceteKanjiParaListviewSelecionavel) botaoComprar.getTag();
	    		 Intent iniciaActivityVerMacete = new Intent(telaLojinhaMaceteKanji, VerMaceteKanjiActivity.class);
	    		 iniciaActivityVerMacete.putExtra("urlDoMacete", maceteKanjiDaLojinha.getNomeUrlDoMaceteKanji());
	    		 telaLojinhaMaceteKanji.startActivity(iniciaActivityVerMacete);
	    	 }
	    	 
	    	 
	     
	      
	     
	    	  
	     } 
	    }); 
	   }
	   else {
	    holder = (ViewHolder) convertView.getTag();
	   }
	  
	   MaceteKanjiParaListviewSelecionavel categoriaDeKanji = arrayListMacetesDeKanjiLojinha.get(position);
	   holder.name.setText(categoriaDeKanji.getLabelKanji());
	   holder.botaoComprar.setTag(categoriaDeKanji);
	  
	   return convertView;
	  
	  }
	

}
