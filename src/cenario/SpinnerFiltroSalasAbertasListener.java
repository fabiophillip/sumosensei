package cenario;

import bancodedados.SolicitaKanjisParaTreinoTask;

import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.TelaModoCasual;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerFiltroSalasAbertasListener implements OnItemSelectedListener {
	
	private TelaModoCasual telaModoCasual;
	private String ultimoFiltroAplicado;
	public SpinnerFiltroSalasAbertasListener(TelaModoCasual telaModoCasual)
	{
		this.telaModoCasual = telaModoCasual;
		this.ultimoFiltroAplicado = telaModoCasual.getResources().getString(R.string.filtro_nenhum);
	}
	 
	  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		Toast.makeText(parent.getContext(), 
			"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
			Toast.LENGTH_SHORT).show();
		String opcaoSelecionada = parent.getItemAtPosition(pos).toString();
		String labelFiltroNenhum = telaModoCasual.getResources().getString(R.string.filtro_nenhum);
		String labelFiltroCategoria = telaModoCasual.getResources().getString(R.string.filtro_categoria);
		String labelFiltroRanking = telaModoCasual.getResources().getString(R.string.filtro_ranking);
		String labelFiltroUsername = telaModoCasual.getResources().getString(R.string.filtro_username);
		//if(ultimoFiltroAplicado.compareTo(opcaoSelecionada) != 0) pode ser a mesma opção selecionada sem pro
		//{
			this.ocultarCamposDetalhesDeFiltragem();
			ultimoFiltroAplicado = opcaoSelecionada;
			this.telaModoCasual.pararThreadAtualizaComNovasSalas();
			
			//aplicar novo filtro
			if(opcaoSelecionada.compareTo(labelFiltroCategoria) == 0)
			{
				this.telaModoCasual.solicitarPorKanjisPraTreino(false);
			}
			else if(opcaoSelecionada.compareTo(labelFiltroNenhum) == 0)
			{
				this.telaModoCasual.solicitarBuscarSalasAbertas();
			}
			else if(opcaoSelecionada.compareTo(labelFiltroRanking) == 0)
			{
				this.telaModoCasual.mostrarPopupPesquisarPorRanking();
			}
			else if(opcaoSelecionada.compareTo(labelFiltroUsername) == 0)
			{
				this.telaModoCasual.mostrarPopupPesquisarPorUsername();
			}
		//}
	  }
	  
	  public void ocultarCamposDetalhesDeFiltragem()
	  {
		  this.telaModoCasual.findViewById(R.id.textfield_digite_username_busca).setVisibility(View.INVISIBLE);
		  this.telaModoCasual.findViewById(R.id.spinner_escolha_ranking).setVisibility(View.INVISIBLE);
		  this.telaModoCasual.findViewById(R.id.spinner_escolha_categoria).setVisibility(View.INVISIBLE);
	  }
	  
	 
	  @Override
	  public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	  }
}
