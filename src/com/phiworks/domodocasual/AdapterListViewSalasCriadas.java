package com.phiworks.domodocasual;

import java.util.ArrayList;
import java.util.LinkedList;

import br.ufrn.dimap.pairg.sumosensei.CategoriaDeKanjiParaListviewSelecionavel;
import br.ufrn.dimap.pairg.sumosensei.LojinhaMaceteKanjiActivity;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCasual;
import br.ufrn.dimap.pairg.sumosensei.VerMaceteKanjiActivity;

import br.ufrn.dimap.pairg.sumosensei.app.R;

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

public class AdapterListViewSalasCriadas extends ArrayAdapter<SalaAbertaModoCasual> 
{
	private ArrayList<SalaAbertaModoCasual> arrayListSalasAbertas;
	private Context contextoAplicacao;
	private TelaModoCasual telaDoModoCasual;
	
	public AdapterListViewSalasCriadas(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<SalaAbertaModoCasual> salasAbertas, TelaModoCasual telaDoModoCasual) 
	{
		super(contextoAplicacao, textViewResourceId, salasAbertas);
		this.arrayListSalasAbertas = salasAbertas;
		this.contextoAplicacao = contextoAplicacao;
		this.telaDoModoCasual = telaDoModoCasual;
	}

	public ArrayList<SalaAbertaModoCasual> getArrayListSalasAbertas() {
		return arrayListSalasAbertas;
	}

	public void setArrayListSalasAbertas(
			ArrayList<SalaAbertaModoCasual> arrayListSalasAbertas) {
		this.arrayListSalasAbertas = arrayListSalasAbertas;
	}
	
	
	private class ViewHolderSalasCriadas {
		   TextView nomeDeUsuario;
		   TextView textViewQuantasCategorias;
		   ImageView nivelDoUsuario;
		   ImageView imageViewsCategorias;
		   ImageView imageViewPortaEntrarSala;
		  }
	 @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolderSalasCriadas holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if (convertView == null) {
		   
	   LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.item_lista_sala, null);
	   
	   TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	   if((position & 1) != 0)
	   {
		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.violet_header);
		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	   }
	   else
	   {
		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.light_violet_header);
		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	   }
	  
	   holder = new ViewHolderSalasCriadas();
	   holder.nomeDeUsuario = (TextView) convertView.findViewById(R.id.username);
	   holder.nivelDoUsuario = (ImageView) convertView.findViewById(R.id.titulo_do_jogador);
	   
	  
	   
	   convertView.setTag(holder);
	   final SalaAbertaModoCasual salaEscolhidaPraJogar = arrayListSalasAbertas.get(position);
	   holder.nomeDeUsuario.setText(salaEscolhidaPraJogar.getNomeDeUsuario());
	   //holder.nivelDoUsuario.setText(salaEscolhidaPraJogar.getDanDoCriador());
	   this.setarIconeEToastDescritivoNivelDoJogador(holder, salaEscolhidaPraJogar.getNivelDoUsuario());
	   
	   
	   LinkedList<String> categoriasDaSala = salaEscolhidaPraJogar.getCategoriasSelecionadas();
	   
	   final String[] arrayCategorias = new String[categoriasDaSala.size()];
	   for(int p = 0; p < categoriasDaSala.size(); p++ )
	   {
		   arrayCategorias[p] = categoriasDaSala.get(p);
	   }
	   
	   
	   
	   holder.imageViewsCategorias = (ImageView) convertView.findViewById(R.id.campo_categorias);
	   holder.imageViewsCategorias.setImageResource(R.drawable.icone_abre_popup_categorias_sala_casual);
	   holder.imageViewPortaEntrarSala = (ImageView) convertView.findViewById(R.id.icone_entrar_sala_casual);
	   holder.imageViewPortaEntrarSala.setImageResource(R.drawable.icone_entrar_sala_casual);
	   
	   
	   holder.imageViewPortaEntrarSala.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            telaDoModoCasual.entrarNaSala(salaEscolhidaPraJogar);
	        }
	    });
	   
	   holder.imageViewsCategorias.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            telaDoModoCasual.abrirPopupMostrarCategoriasDeUmaSala(arrayCategorias);
	        }
	    });
	   holder.textViewQuantasCategorias = (TextView) convertView.findViewById(R.id.quantas_categorias_tem_a_sala);
	   holder.textViewQuantasCategorias.setText(String.valueOf(arrayCategorias.length));
	   
	   }
	   else 
	   {
		   //ANDREWS ADICIONOU
	        holder = (ViewHolderSalasCriadas) convertView.getTag();
	        TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	 	   if((position & 1) != 0)
	 	   {
	 		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.violet_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	 	   }
	 	   else
	 	   {
	 		   //layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.light_violet_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	 	   }
	 	  
	 	   //holder = new ViewHolderSalasCriadas();
	 	   holder.nomeDeUsuario = (TextView) convertView.findViewById(R.id.username);
	 	   holder.nivelDoUsuario = (ImageView) convertView.findViewById(R.id.titulo_do_jogador);
	 	   
	 	  
	 	   
	 	   //convertView.setTag(holder);
	 	   final SalaAbertaModoCasual salaEscolhidaPraJogar = arrayListSalasAbertas.get(position);
	 	   holder.nomeDeUsuario.setText(salaEscolhidaPraJogar.getNomeDeUsuario());
	 	   this.setarIconeEToastDescritivoNivelDoJogador(holder, salaEscolhidaPraJogar.getNivelDoUsuario());
	 	  
	 	   
	 	   
	 	  LinkedList<String> categoriasDaSala = salaEscolhidaPraJogar.getCategoriasSelecionadas();
		   
		   final String[] arrayCategorias = new String[categoriasDaSala.size()];
		   for(int p = 0; p < categoriasDaSala.size(); p++ )
		   {
			   arrayCategorias[p] = categoriasDaSala.get(p);
		   }
		   
	 	   
	 	  holder.imageViewsCategorias = (ImageView) convertView.findViewById(R.id.campo_categorias);
		  holder.imageViewsCategorias.setImageResource(R.drawable.icone_abre_popup_categorias_sala_casual);
		  holder.imageViewPortaEntrarSala = (ImageView) convertView.findViewById(R.id.icone_entrar_sala_casual);
		  holder.imageViewPortaEntrarSala.setImageResource(R.drawable.icone_entrar_sala_casual);
	 	  
		  holder.imageViewPortaEntrarSala.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            telaDoModoCasual.entrarNaSala(salaEscolhidaPraJogar);
		        }
		    });
		   
		   holder.imageViewsCategorias.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            telaDoModoCasual.abrirPopupMostrarCategoriasDeUmaSala(arrayCategorias);
		        }
		    });
		   holder.textViewQuantasCategorias = (TextView) convertView.findViewById(R.id.quantas_categorias_tem_a_sala);
		   holder.textViewQuantasCategorias.setText(String.valueOf(arrayCategorias.length));
	    }
	   
	  
	   
	  
	   return convertView;
	  
	  }
	
	  
	  private void setarIconeEToastDescritivoNivelDoJogador(final ViewHolderSalasCriadas holder, final String nivelDoJogador)
	  {
		  if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_1)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_sensei);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_2)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_treme_terra);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_3)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_muralha);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_4)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_parede);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_5)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_eu_sei);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_6)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_cimento);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_7)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_acho_que_sei);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_8)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_fraquinho);
		  }
		  else if(nivelDoJogador.compareToIgnoreCase(contextoAplicacao.getResources().getString(R.string.sumo_ranking_9)) == 0)
		  {
			  holder.nivelDoUsuario.setImageResource(R.drawable.titulo_sumo_nao_sei);
		  }
		  //vai ter varios ifs aqui de acordo com o nivel para mudar a figurinha do nivel do jogador
		  
		//após setar o icone do nivel do jogador, vamos agora adicionar um toast quando o user clica no imageView
		  holder.nivelDoUsuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast popupExplicaNivelDoUsuarioDaImagem = Toast.makeText(contextoAplicacao, nivelDoJogador, Toast.LENGTH_SHORT);
				int [] localizacoesFiguraNivelJogador = new int [2];
				localizacoesFiguraNivelJogador[0] = 0;
				localizacoesFiguraNivelJogador[1] = 0;
				holder.nivelDoUsuario.getLocationOnScreen(localizacoesFiguraNivelJogador);
				  popupExplicaNivelDoUsuarioDaImagem.setGravity(Gravity.TOP |Gravity.LEFT | Gravity.CENTER_HORIZONTAL, localizacoesFiguraNivelJogador[0], localizacoesFiguraNivelJogador[1]);
				  popupExplicaNivelDoUsuarioDaImagem.show();
			}
		});
		  
		   
		  
	  }

}
