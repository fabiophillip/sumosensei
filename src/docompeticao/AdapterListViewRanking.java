package docompeticao;

import java.util.ArrayList;

import br.ufrn.dimap.pairg.sumosensei.android.R;



import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterListViewRanking extends ArrayAdapter<DadosDeRanking> 
{
	private ArrayList<DadosDeRanking> arrayListDadosRanking;
	private Context contextoAplicacao;
	private Typeface tfBr;
	
	public AdapterListViewRanking(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<DadosDeRanking> dadosRank) 
	{
		super(contextoAplicacao, textViewResourceId, dadosRank);
		this.arrayListDadosRanking = dadosRank;
		this.contextoAplicacao = contextoAplicacao;
		
		String fontpath = "fonts/gilles_comic_br.ttf";
	    tfBr = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontpath);
	   
	}

	
	
	
	public ArrayList<DadosDeRanking> getArrayListDadosRanking() {
		return arrayListDadosRanking;
	}

	public void setArrayListDadosRanking(
			ArrayList<DadosDeRanking> arrayListDadosRanking) {
		this.arrayListDadosRanking = arrayListDadosRanking;
	}




	private class ViewHolderPosicaoRanking {
		   TextView posicaoNoRanking;
		   TextView usernameRanking;
		   TextView tituloRanking;
		   TextView vitoriasRanking;
		   TextView derrotasRanking;
		  }
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderPosicaoRanking viewholderPosicaoRanking = null;
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
				     Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item_posicao_no_ranking, null);
			
			viewholderPosicaoRanking = new ViewHolderPosicaoRanking();
			
		}
		else
		{
			viewholderPosicaoRanking = (ViewHolderPosicaoRanking) convertView.getTag();
		}
		viewholderPosicaoRanking.posicaoNoRanking = (TextView) convertView.findViewById(R.id.texto_posicao_rank_competicao);
		viewholderPosicaoRanking.usernameRanking = (TextView) convertView.findViewById(R.id.texto_username_rank_competicao);
		viewholderPosicaoRanking.tituloRanking = (TextView) convertView.findViewById(R.id.texto_titulo_rank_competicao);
		viewholderPosicaoRanking.vitoriasRanking = (TextView) convertView.findViewById(R.id.texto_vitorias_rank_competicao);
		viewholderPosicaoRanking.derrotasRanking = (TextView) convertView.findViewById(R.id.texto_derrotas_rank_competicao);
		
		
		
		convertView.setTag(viewholderPosicaoRanking);
		
		DadosDeRanking dadosDoRanking = arrayListDadosRanking.get(position);
		viewholderPosicaoRanking.posicaoNoRanking.setText(dadosDoRanking.getPosicaoNoRanking());
		String usernameTexto = dadosDoRanking.getNomeUsuario();
		viewholderPosicaoRanking.usernameRanking.setText(usernameTexto);
		viewholderPosicaoRanking.tituloRanking.setText(dadosDoRanking.getTituloUsuario());
		viewholderPosicaoRanking.vitoriasRanking.setText(String.valueOf(dadosDoRanking.getQuantidadeDeVitorias()));
		viewholderPosicaoRanking.derrotasRanking.setText(String.valueOf(dadosDoRanking.getQuantidadeDeDerrotas()));
		
		viewholderPosicaoRanking.posicaoNoRanking.setTypeface(tfBr);
		viewholderPosicaoRanking.usernameRanking.setTypeface(tfBr);
		viewholderPosicaoRanking.tituloRanking.setTypeface(tfBr);
		viewholderPosicaoRanking.vitoriasRanking.setTypeface(tfBr);
		viewholderPosicaoRanking.derrotasRanking.setTypeface(tfBr);
		
		
		
		
		return convertView;
		
	
	}
	
	public void setListItems(ArrayList<DadosDeRanking> newList) {
	     this.clear();
	     this.addAll(newList); 
	     notifyDataSetChanged();
	}
	

}
