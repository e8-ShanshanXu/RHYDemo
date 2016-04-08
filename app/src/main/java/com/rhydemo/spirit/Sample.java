package com.rhydemo.spirit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sample extends Spirit{

	public Sample(Bitmap[] bitmap, int x, int y) {
		super(bitmap, x, y);
		
	}

	@Override
	public void onUpdate() {
		if(time==0){
			time=System.currentTimeMillis();
			//获取距1970年1月1日0时0分0秒的毫秒值
		}else if(System.currentTimeMillis()-time>200){
			index++;
			time=0;
		}
		if(index>=bitmap.length){
			index=0;
		}
	}

	@Override
	public void onRent(Canvas canvas) {
		canvas.drawBitmap(bitmap[index], x, y, null);
		
	}

}
