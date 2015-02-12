package cenario;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/*eh um spinner que quando o usuario volta ao mesmo item, ele considera como selecionado*/
public class SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem extends Spinner {

	  public SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem(Context context)
	  { super(context); }

	  public SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem(Context context, AttributeSet attrs)
	  { super(context, attrs); }

	  public SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem(Context context, AttributeSet attrs, int defStyle)
	  { super(context, attrs, defStyle); }

	  @Override public void
	  setSelection(int position, boolean animate)
	  {
	    boolean sameSelected = position == getSelectedItemPosition();
	    super.setSelection(position, animate);
	    if (sameSelected) {
	      // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
	      getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
	    }
	  }

	  @Override public void
	  setSelection(int position)
	  {
	    boolean sameSelected = position == getSelectedItemPosition();
	    super.setSelection(position);
	    if (sameSelected) {
	      // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
	      getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
	    }
	  }
	  
	  
	  
	  
	  
	}