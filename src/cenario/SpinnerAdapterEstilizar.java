package cenario;

import java.util.List;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapterEstilizar extends ArrayAdapter<String> {
int recurso;
Typeface tf;

public SpinnerAdapterEstilizar(Context _context, int _resource,
        List<String> _items) {

    super(_context, _resource, _items);
    recurso=_resource;
    tf=Typeface.createFromAsset(_context.getAssets(),"fonts/gilles_comic_br.ttf");
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
    TextView view = (TextView) super.getView(position, convertView, parent);
    view.setTypeface(tf);
    return view;
    }

@Override
public View getDropDownView(int position, View convertView, ViewGroup parent) {
    TextView view = (TextView) super.getDropDownView(position, convertView, parent);
    view.setTypeface(tf);
    return view;
}

}
