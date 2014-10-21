package com.phiworks.domodocasual;

import java.util.ArrayList;
import java.util.LinkedList;

import com.phiworks.sumosenseinew.CategoriaDeKanjiParaListviewSelecionavel;
import com.phiworks.sumosenseinew.LojinhaMaceteKanjiActivity;
import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.TelaModoCasual;
import com.phiworks.sumosenseinew.VerMaceteKanjiActivity;

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
	   
	   LinearLayout layoutDeUmaLinhaDoBuscarSalas = (LinearLayout) convertView.findViewById(R.id.uma_linha_buscar_salas);
	   TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	   ImageView imagemTituloDoJogador = (ImageView) convertView.findViewById(R.id.titulo_do_jogador);
	   if((position & 1) != 0)
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.violet_header);
		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	   }
	   else
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.light_violet_header);
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
	   this.setarIconeNivelDoJogador(holder, salaEscolhidaPraJogar.getNivelDoUsuario());
	   
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
	        LinearLayout layoutDeUmaLinhaDoBuscarSalas = (LinearLayout) convertView.findViewById(R.id.uma_linha_buscar_salas);
	 	   TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	 	   ImageView imagemTituloDoJogador = (ImageView) convertView.findViewById(R.id.titulo_do_jogador);
	 	   if((position & 1) != 0)
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.violet_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	 	   }
	 	   else
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.light_violet_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	 	   }
	 	  
	 	   //holder = new ViewHolderSalasCriadas();
	 	   holder.nomeDeUsuario = (TextView) convertView.findViewById(R.id.username);
	 	   holder.nivelDoUsuario = (ImageView) convertView.findViewById(R.id.titulo_do_jogador);
	 	   
	 	  
	 	   
	 	   //convertView.setTag(holder);
	 	   final SalaAbertaModoCasual salaEscolhidaPraJogar = arrayListSalasAbertas.get(position);
	 	   holder.nomeDeUsuario.setText(salaEscolhidaPraJogar.getNomeDeUsuario());
	 	   this.setarIconeNivelDoJogador(holder, salaEscolhidaPraJogar.getNivelDoUsuario());
	 	  
	 	   
	 	   
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
	
	  
	  private void setarIconeNivelDoJogador(ViewHolderSalasCriadas holder, String nivelDoJogador)
	  {
		  //vai ter varios ifs aqui de acordo com o nivel para mudar a figurinha do nivel do jogador
		  holder.nivelDoUsuario.setImageResource(R.drawable.icone_dan_1);
	  }

}
