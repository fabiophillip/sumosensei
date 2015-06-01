package com.phiworks.domodocasual;
import java.util.ArrayList;
import java.util.LinkedList;

import br.ufrn.dimap.pairg.sumosensei.android.TelaModoCasual;
import br.ufrn.dimap.pairg.sumosensei.android.R;


import lojinha.ConcreteDAOAcessaComprasMaceteKanji;
import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaComprasMaceteKanji;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.MaceteKanjiParaListviewSelecionavel;
import android.R.array;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterListViewChatCasual extends ArrayAdapter<String> 
{
	private ArrayList<String> arrayListMensagensChat;
	private ArrayList<String> posicoesDoBalaoDeCadaMensagemChat; //direita,esquerda,direita,esquerda...
	private Context contextoAplicacao;
	
	public AdapterListViewChatCasual(Context contextoAplicacao, int textViewResourceId,
			ArrayList<String> arrayListMensagensChat, ArrayList<String> posicoesDoBalaoDeCadaMensagemChat) 
	{
		super(contextoAplicacao, textViewResourceId, arrayListMensagensChat);
		this.arrayListMensagensChat = new ArrayList<String>();
		this.arrayListMensagensChat.addAll(arrayListMensagensChat);
		this.posicoesDoBalaoDeCadaMensagemChat = new ArrayList<String>();
		this.posicoesDoBalaoDeCadaMensagemChat.addAll(posicoesDoBalaoDeCadaMensagemChat);
		
		this.contextoAplicacao = contextoAplicacao;
	}

	public ArrayList<String> getArrayListMensagensChat() {
		return arrayListMensagensChat;
	}

	public void setArrayListMensagensChat(
			ArrayList<String> arrayListMensagensChat) {
		this.arrayListMensagensChat = arrayListMensagensChat;
	}
	
	
	private class ViewHolderMensagensChat {
		   TextView mensagemChatCasual;
		  }
	
	
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolderMensagensChat holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if (convertView == null) {
		   
	   LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.item_chat_casual, null);
	   
	   //vamos mudar a posicao do balao de chat para esquerda ou direita
	   TextView layoutDeUmaLinhaDoBuscarSalas = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
       RelativeLayout bg = (RelativeLayout) convertView.findViewById(R.id.uma_linha_chat_casual);
	   String qualPosicaoDoBalaoEssaLinhaDoChatDeveFicar = posicoesDoBalaoDeCadaMensagemChat.get(position);
	   if(qualPosicaoDoBalaoEssaLinhaDoChatDeveFicar.compareTo("esquerda") == 0)
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.balao_chat_casual_esquerda_menor);
 		   layoutDeUmaLinhaDoBuscarSalas.setGravity(Gravity.LEFT);
 		   bg.setGravity(Gravity.LEFT);
	   }
	   else
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.balao_chat_casual_direita_menor);
	 		  layoutDeUmaLinhaDoBuscarSalas.setGravity(Gravity.RIGHT);

	            /*android:layout_width="wrap_content"
	                    android:layout_height="wrap_content"
	                    android:layout_toRightOf="@id/avatar"
	                    android:paddingLeft="4dip"*/
	            

	            bg.setGravity(Gravity.RIGHT);
	   }
	   
	   TextView mensagemChatCasual = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
	   this.mudarFonteTextViewMensagemChat(mensagemChatCasual);
	   mensagemChatCasual.setText(arrayListMensagensChat.get(position));
	   
	   /*COISAS NECESSARIAS PARA FAZER AS LINHAS DA LISTVIEW FICAREM VERMELHAS OU NORMAIS
	   if((position & 1) != 0)
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.red_header);
		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	   }
	   else
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.white_header);
		   textoUsername.setTextColor(Color.parseColor("#000000"));
		   //imagemTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	   }*/
	  
	   holder = new ViewHolderMensagensChat();
	   holder.mensagemChatCasual = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
	   
	   convertView.setTag(holder);
	   }
	   else 
	   {
		   //ANDREWS ADICIONOU
	        holder = (ViewHolderMensagensChat) convertView.getTag();
	       
	      //vamos mudar a posicao do balao de chat para esquerda ou direita
	        TextView layoutDeUmaLinhaDoBuscarSalas = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
	        RelativeLayout bg = (RelativeLayout) convertView.findViewById(R.id.uma_linha_chat_casual);
	 	   String qualPosicaoDoBalaoEssaLinhaDoChatDeveFicar = posicoesDoBalaoDeCadaMensagemChat.get(position);
	 	   if(qualPosicaoDoBalaoEssaLinhaDoChatDeveFicar.compareTo("esquerda") == 0)
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.balao_chat_casual_esquerda_menor);
	 		   layoutDeUmaLinhaDoBuscarSalas.setGravity(Gravity.LEFT);
	 		   bg.setGravity(Gravity.LEFT);
	 	   }
	 	   else
	 	   {
	 		  layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.balao_chat_casual_direita_menor);
	 		  layoutDeUmaLinhaDoBuscarSalas.setGravity(Gravity.RIGHT);
	          bg.setGravity(Gravity.RIGHT);
	 	   }
	 	   
	       TextView mensagemChatCasual = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
	 	   this.mudarFonteTextViewMensagemChat(mensagemChatCasual);
	 	   mensagemChatCasual.setText(arrayListMensagensChat.get(position));
	 	   
	 	  /*COISAS NECESSARIAS PARA FAZER AS LINHAS DA LISTVIEW FICAREM VERMELHAS OU NORMAIS
	 	   if((position & 1) != 0)
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.red_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	 	   }
	 	   else
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.white_header);
	 		   textoUsername.setTextColor(Color.parseColor("#000000"));
	 		   //textoTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	 	   }*/
	 	  
	 	   //holder = new ViewHolderSalasCriadas();
	 	   holder.mensagemChatCasual = (TextView) convertView.findViewById(R.id.mensagemChatCasual);
	 	  
	 	   
	 	   //convertView.setTag(holder);
	 	   
	    }
	   
	  
	   return convertView;
	  
	  }
	  
	  
	  
	  private void mudarFonteTextViewMensagemChat(TextView textView)
	  {
		  String fontPath = "fonts/gilles_comic_br.ttf";
		  Typeface tf = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontPath);
		  textView.setTypeface(tf);
	  }
	  

}
