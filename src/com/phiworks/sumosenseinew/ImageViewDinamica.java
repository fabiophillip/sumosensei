package com.phiworks.sumosenseinew;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class ImageViewDinamica extends ImageView 
{

private float mPosX;
private float mPosY;
private float mScaleFactor = 1.f;
private Bitmap imageToDraw = BitmapFactory.decodeResource(getResources(), R.drawable.sumo_background);



/**
 * Return a new image view.
 */

public ImageViewDinamica (Context context) {
	super (context);
}
public ImageViewDinamica (Context context, AttributeSet attrs) {
	super (context, attrs);
}
public ImageViewDinamica (Context context, AttributeSet attrs, int style) {
	super (context, attrs, style);
}

public Bitmap getImageToDraw() {
	return imageToDraw;
}
public void setImageToDraw(Bitmap imageToDraw) {
	this.imageToDraw = imageToDraw;
}



public float getmPosX() {
	return mPosX;
}
public float getmPosY() {
	return mPosY;
}
/**
 * Draw the image on the canvas, after translating the canvas position by mPosX and mPosY.
 * 
 * @param canvas Canvas
 * @return void
 */

public void onDraw(Canvas canvas) 
{
    canvas.drawBitmap(imageToDraw, mPosX, mPosY, null);
    super.onDraw(canvas);
}

/**
 * Take the current x and y values used to translate the canvas and change them by dx and dy.
 * Also calls invalidate so the view will be redrawn.
 * 
 * @param dx float change in x position
 * @param dy float change in y
 * @return void
 */

public void changePos (float dx, float dy)
{
    mPosX += dx;
    mPosY += dy;
    this.invalidate ();
} // end changePos

/**
 * Sets the x and y values used to translate the canvas.
 * Also calls invalidate so the view will be redrawn.
 * 
 * @param x float
 * @param y float
 * @return void
 */

public void setPos (float x, float y)
{
    mPosX = x;
    mPosY = y;
    this.invalidate ();
} // end setPos





} // end class

