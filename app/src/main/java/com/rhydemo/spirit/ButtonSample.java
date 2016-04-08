package com.rhydemo.spirit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class ButtonSample extends Sample{
    int speed;
    Bitmap acc;
    public final int LEVEL=0;//下落
    public final int OVER=1;//碰撞
    public final int CLEAN=2;//消失
    public int state;
    //创建碰撞区域
    RectF r=new RectF();
    int flush=1;//shua xin cishu
	public ButtonSample(Bitmap[] bitmap, int x, int y,int speed,Bitmap acc) {
		super(bitmap, x, y);
		this.speed=speed;
		this.acc=acc;
	}
   @Override
    public void onUpdate() {
	   switch (state) {
	case LEVEL:
		y +=speed;
		//更新碰撞区域
		r.left=x;
		r.right=x+w;
		r.top=y;
		r.bottom=y+h;
		break;
	case OVER:
		flush++;
		//数字越大停留时间越长
		if(flush>4){
			state=CLEAN;
		}
		
	   break;
	}
	   super.onUpdate();  
	    
    }
  
   @Override
   public void onRent(Canvas canvas) {
 	switch (state) {
	case LEVEL:
		super.onRent(canvas);
		break;
	case OVER:
		int iy=y+h-acc.getHeight();
		canvas.drawBitmap(acc, x,iy,null);
		super.onRent(canvas);
		break;
	}
	   
   }
   //pengzhuang fangfa
   public boolean isKill(int touchX,int touchY){
	   boolean is=false;
	   if(state==LEVEL){
		   if(r.contains(touchX,touchY)){
			   is=true;
			   state=OVER;
		   }
	   }
	   return is;
   }
}
