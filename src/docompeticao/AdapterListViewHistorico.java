package docompeticao;

import java.util.ArrayList;
import java.util.LinkedList;

import com.phiworks.domodocasual.AssociaCategoriaComIcone;
import com.phiworks.domodocasual.SalaAbertaModoCasual;


import bancodedados.DadosPartidaParaOLog;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import bancodedados.SingletonArmazenaCategoriasDoJogo;

import br.ufrn.dimap.pairg.sumosensei.android.CategoriaDeKanjiParaListviewSelecionavel;
import br.ufrn.dimap.pairg.sumosensei.android.DadosPartidasAnteriores;
import br.ufrn.dimap.pairg.sumosensei.android.LojinhaMaceteKanjiActivity;
import br.ufrn.dimap.pairg.sumosensei.android.TelaModoCasual;
import br.ufrn.dimap.pairg.sumosensei.android.TelaModoCompeticao;
import br.ufrn.dimap.pairg.sumosensei.android.VerMaceteKanjiActivity;
import br.ufrn.dimap.pairg.sumosensei.android.R;

import lojinha.ConcreteDAOAcessaComprasMaceteKanji;
import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaComprasMaceteKanji;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.MaceteKanjiParaListviewSelecionavel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterListViewHistorico extends ArrayAdapter<DadosPartidaParaOLog> 
{
	private LinkedList<DadosPartidaParaOLog> arrayListHistorico;
	private Context contextoAplicacao;
	private DadosPartidasAnteriores telaHistorico;
	
	public AdapterListViewHistorico(Context contextoAplicacao, int textViewResourceId,
		    LinkedList<DadosPartidaParaOLog> historicos, DadosPartidasAnteriores telaHistorico) 
	{
		super(contextoAplicacao, textViewResourceId, historicos);
		this.arrayListHistorico = historicos;
		this.contextoAplicacao = contextoAplicacao;
		this.telaHistorico = telaHistorico;
	}

	
	
	
	




	public LinkedList<DadosPartidaParaOLog> getArrayListHistorico() {
		return arrayListHistorico;
	}
	public void setArrayListHistorico(
			LinkedList<DadosPartidaParaOLog> arrayListHistorico) {
		this.arrayListHistorico = arrayListHistorico;
	}









	private class ViewHolderHistorico {
		   TextView nomeAdversarioPartida;
		   TextView dataPartida;
		   ImageView [] imageViewsCategorias = new ImageView[8];
		  }
	 @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolderHistorico holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if(convertView == null)
		{
		   LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
				     Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.item_partida_do_historico, null);
			
		   holder = new ViewHolderHistorico();
			
		}
		else
		{
			holder = (ViewHolderHistorico) convertView.getTag();
		}
		   
	   
	   TextView textoAdversario = (TextView) convertView.findViewById(R.id.nominho_do_oponente);
	   if((position & 1) != 0)
	   {
		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.violet_header);
		   textoAdversario.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	   }
	   else
	   {
		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.light_violet_header);
		   textoAdversario.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	   }
	  
	   holder.nomeAdversarioPartida = (TextView) convertView.findViewById(R.id.nominho_do_oponente);
	   holder.dataPartida = (TextView) convertView.findViewById(R.id.data_da_partida);
	   
	   ImageView imagemCategoria1 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico1);
	   holder.imageViewsCategorias[0] = imagemCategoria1;
	   ImageView imagemCategoria2 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico2);
	   holder.imageViewsCategorias[1] = imagemCategoria2;
	   ImageView imagemCategoria3 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico3);
	   holder.imageViewsCategorias[2] = imagemCategoria3;
	   ImageView imagemCategoria4 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico4);
	   holder.imageViewsCategorias[3] = imagemCategoria4;
	   ImageView imagemCategoria5 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico5);
	   holder.imageViewsCategorias[4] = imagemCategoria5;
	   ImageView imagemCategoria6 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico6);
	   holder.imageViewsCategorias[5] = imagemCategoria6;
	   ImageView imagemCategoria7 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico7);
	   holder.imageViewsCategorias[6] = imagemCategoria7;
	   ImageView imagemCategoria8 = (ImageView) convertView.findViewById(R.id.icone_categoria_historico8);
	   holder.imageViewsCategorias[7] = imagemCategoria8;
	  
	   
	   convertView.setTag(holder);
	   DadosPartidaParaOLog dadosDaPartida = this.arrayListHistorico.get(position);
	   holder.nomeAdversarioPartida.setText(dadosDaPartida.getUsernameAdversario());
	   String dataString = dadosDaPartida.getData();
	   String [] dataStringSeparada = dataString.split(":");
	   String textoPraLabelData = "";
	   for(int l = 0; l < dataStringSeparada.length - 1; l++)
	   {
		   if(l != 0)
		   {
			   textoPraLabelData = textoPraLabelData + ":" + dataStringSeparada[l];
		   }
		   else
		   {
			   textoPraLabelData = textoPraLabelData + dataStringSeparada[l];
		   }
	   }
	   holder.dataPartida.setText(textoPraLabelData);
	   String categoriasComVirgulas = dadosDaPartida.getCategoria();
	   String [] categoriasSemVirgulas = categoriasComVirgulas.split(",");
	   LinkedList<String> categoriasLinkedList = new LinkedList<String>();
	   for(int k = 0; k < categoriasSemVirgulas.length; k++)
	   {
		   String umaCategoria = categoriasSemVirgulas[k];
		   categoriasLinkedList.add(umaCategoria);
	   }
	   LinkedList<Integer> idsCategoriasSelecionadas = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdsCategorias(categoriasLinkedList);
	   Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadasPraPratida(idsCategoriasSelecionadas, this.telaHistorico.getApplicationContext());
	   for(int i = 0; i < indicesIconesCategoriasDoJogo.length; i++)
	   {
		   int imageResourceIconeCategoria = indicesIconesCategoriasDoJogo[i];
		   ImageView umaImagemCategoria = holder.imageViewsCategorias[i];
		   umaImagemCategoria.setImageResource(imageResourceIconeCategoria);
	   }
	   
	   return convertView;
	   
	   
	   
	  }
	 
	 
	  
}
	
	  
	
