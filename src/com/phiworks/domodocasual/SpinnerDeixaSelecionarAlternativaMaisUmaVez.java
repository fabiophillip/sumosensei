package com.phiworks.domodocasual;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SpinnerDeixaSelecionarAlternativaMaisUmaVez extends Spinner 
{
	OnItemSelectedListener listener;

	public SpinnerDeixaSelecionarAlternativaMaisUmaVez(Context context, AttributeSet attrs) 
	{
	    super(context, attrs);
	}

	@Override
	public void setSelection(int position) {
	    super.setSelection(position);
	    if (listener != null)
	        listener.onItemSelected(null, null, position, 0);
	}

	public void setOnItemSelectedEvenIfUnchangedListener(
	        OnItemSelectedListener listener) {
	    this.listener = listener;
	}

}
