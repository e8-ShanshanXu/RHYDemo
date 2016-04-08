package com.rhydemo.spirit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Spirit {
    public  Bitmap[] bitmap;
    public int x;
    public int y;
    public int w,h;//按钮的宽和高
    public long time;
    public int index;
	public Spirit(Bitmap[] bitmap, int x, int y) {
		
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		w=bitmap[0].getWidth();
		h=bitmap[0].getHeight();
	}
    public abstract void onUpdate();
    public abstract void onRent(Canvas canvas);
}
