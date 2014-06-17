package cenario;

import com.phiworks.sumosenseinew.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds;

    public ImageAdapter(Integer [] idsIMagens, Context c) {
    	this.mImageIds = idsIMagens;
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.TelaInicialMultiplayer);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.TelaInicialMultiplayer_android_galleryItemBackground, 0);
        attr.recycle();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageResource(mImageIds[position]);
        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }
}
