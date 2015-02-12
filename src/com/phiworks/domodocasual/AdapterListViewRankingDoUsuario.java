package com.phiworks.domodocasual;

import java.util.ArrayList;


import br.ufrn.dimap.pairg.sumosensei.app.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterListViewRankingDoUsuario extends ArrayAdapter<RankingEImagem>{
	private ArrayList<RankingEImagem> rankingsEImagensDaLista;
	private final Typeface typeFaceFonteTexto;
	private final Activity telaTemEscolhaRanking;
	private int layoutUsadoParaTextoEImagem;//possível valor = R.layout.list_item_icone_e_texto
	private final boolean iconesDevemEstarMeioTransparentesNoComeco;
	
	private String rankingAtualmenteSelecionado;
	private int idRankingAtualmenteSelecionado;

	public AdapterListViewRankingDoUsuario(Activity telaTemEscolhaRanking, int layoutRankingUsuario, ArrayList<RankingEImagem> rankingsEImagens, Typeface typeFaceFonteTexto, boolean iconesDevemEstarMeioTransparentesNoComeco) 
	{
		super(telaTemEscolhaRanking, layoutRankingUsuario, rankingsEImagens);
		this.rankingsEImagensDaLista = rankingsEImagens;
		this.telaTemEscolhaRanking = telaTemEscolhaRanking;
		this.typeFaceFonteTexto = typeFaceFonteTexto;
		this.iconesDevemEstarMeioTransparentesNoComeco = iconesDevemEstarMeioTransparentesNoComeco;
		this.layoutUsadoParaTextoEImagem = R.layout.item_lista_ranking_usuario;
		this.rankingAtualmenteSelecionado = "";
		this.idRankingAtualmenteSelecionado = -1;
		
	}
	
	
	
	

	public ArrayList<RankingEImagem> getRankingsEImagensDaLista() {
		return rankingsEImagensDaLista;
	}





	public void setRankingsEImagensDaLista(
			ArrayList<RankingEImagem> rankingsEImagensDaLista) {
		this.rankingsEImagensDaLista = rankingsEImagensDaLista;
	}


	public void setRankingAtualmenteSelecionado(String rankingAtualmenteSelecionado) {
		this.rankingAtualmenteSelecionado = rankingAtualmenteSelecionado;
	}

	public String getRankingAtualmenteSelecionado() {
		return rankingAtualmenteSelecionado;
	}
	
	
	



	public int getIdRankingAtualmenteSelecionado() {
		return idRankingAtualmenteSelecionado;
	}



	public void setIdRankingAtualmenteSelecionado(int idRankingAtualmenteSelecionado) {
		this.idRankingAtualmenteSelecionado = idRankingAtualmenteSelecionado;
	}



	public int getLayoutUsadoParaTextoEImagem() {
		return layoutUsadoParaTextoEImagem;
	}

	public void setLayoutUsadoParaTextoEImagem(int layoutUsadoParaTextoEImagem) {
		this.layoutUsadoParaTextoEImagem = layoutUsadoParaTextoEImagem;
	}
	
	
	private class ViewHolderRankingUsuario {
		   TextView textoRanking;
		   ImageView figuraRanking;
		  }
	
	

	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		ViewHolderRankingUsuario holder = null;
		
		if(view == null)
		{
			LayoutInflater vi = (LayoutInflater)telaTemEscolhaRanking.getSystemService(
				     Context.LAYOUT_INFLATER_SERVICE);
				   view = vi.inflate(R.layout.item_lista_ranking_usuario, null);
				   
				   holder = new ViewHolderRankingUsuario();
				   holder.textoRanking = (TextView) view.findViewById(R.id.texto_label_ranking_usuario_filtro);
				   holder.figuraRanking = (ImageView) view.findViewById(R.id.icone_ranking_usuario_filtro);
		
				   view.setTag(holder);
				   final RankingEImagem salaEscolhidaPraJogar = this.rankingsEImagensDaLista.get(position);
				   holder.textoRanking.setText(salaEscolhidaPraJogar.getNomeDaPosicaoNoRanking());
				   holder.figuraRanking.setImageResource(salaEscolhidaPraJogar.getIdImagemPosicaoNoRanking());
		
				   
				   TextView txtTitle = (TextView) view.findViewById(R.id.texto_label_ranking_usuario_filtro);
					
					//mudar fonte do texto
					txtTitle.setTypeface(typeFaceFonteTexto);
					
					String umTituloRanking = salaEscolhidaPraJogar.getNomeDaPosicaoNoRanking();
					if(umTituloRanking.compareTo(rankingAtualmenteSelecionado) != 0)
					{
						view.setBackgroundColor(Color.TRANSPARENT);
					}
					else
					{
						view.setBackgroundColor(Color.parseColor("#5db1ff"));
					}
		}
		else
		{
			holder = (ViewHolderRankingUsuario) view.getTag();
			
			holder.textoRanking = (TextView) view.findViewById(R.id.texto_label_ranking_usuario_filtro);
			   holder.figuraRanking = (ImageView) view.findViewById(R.id.icone_ranking_usuario_filtro);
	
			   view.setTag(holder);
			   final RankingEImagem umRankingDaTelaDeBusca = this.rankingsEImagensDaLista.get(position);
			   holder.textoRanking.setText(umRankingDaTelaDeBusca.getNomeDaPosicaoNoRanking());
			   holder.figuraRanking.setImageResource(umRankingDaTelaDeBusca.getIdImagemPosicaoNoRanking());
	
			   
			   TextView txtTitle = (TextView) view.findViewById(R.id.texto_label_ranking_usuario_filtro);
				
				//mudar fonte do texto
				txtTitle.setTypeface(typeFaceFonteTexto);
				
				String umTituloRanking = umRankingDaTelaDeBusca.getNomeDaPosicaoNoRanking();
				if(umTituloRanking.compareTo(rankingAtualmenteSelecionado) != 0)
				{
					view.setBackgroundColor(Color.TRANSPARENT);
				}
				else
				{
					view.setBackgroundColor(Color.parseColor("#5db1ff"));
				}
			
		}
		
		
		
		
		
		/*if(this.iconesDevemEstarMeioTransparentesNoComeco == true)
		{
			txtTitle.setTextColor(Color.argb(130, 0, 0, 0));
			imageView.setAlpha(130);
		}*/
		
		
		return view;
	}

}

