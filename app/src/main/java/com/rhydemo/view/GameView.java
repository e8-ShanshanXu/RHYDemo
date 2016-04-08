package com.rhydemo.view;


import java.util.ArrayList;

import com.example.rhydemo.R;
import com.rhydemo.music.Sound;
import com.rhydemo.spirit.ButtonSample;
import com.rhydemo.spirit.Sample;

import android.R.color;
import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Sampler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

public class GameView extends View implements Runnable{
//背景图
Bitmap bitmap;
Bitmap ready;
Bitmap go;
Bitmap key;//按键图片
Bitmap line;//线的整体图
Bitmap[] lineArr=new Bitmap[2];//存储切割后的图片数组
int[] lineX={ 676,847,1020 };//每条线的x坐标
Sample[] lineSample=new Sample[3];

Bitmap frt;//音频
Bitmap[] frtArr=new Bitmap[3];//音频数组
Sample frtSample;

Bitmap button;
Bitmap[] buttonArr=new Bitmap[2];
ArrayList<ButtonSample> list=new ArrayList<ButtonSample>();//存储按钮集合
int bottonX[]={525,525+172,525+172*2,525+172*3};//按钮x坐标
Bitmap acc;//爆炸图片

//游戏状态
final int READY=0;
final int GO=1;
final int PLAY=2;
final int OVER=3;
int state;
int touchA;//zhuang tai 
int touchX,touchY;//zuobiao
Bitmap[] scoreArr=new Bitmap[10];
Bitmap miss;
Bitmap per;
int score;//存储分数的变量
int sco;//存储连续进行碰撞几次
/**是否进行绘制miss per   press*/
boolean isMiss,isPer,isPress;
Bitmap over;//恭喜过关
Bitmap press;//按下的阴影效果
int pressIndex;//记录按下的位置(第几根线右边的位置)
Sound sound;
/**线程的开关*/
public boolean isRun=true;
	public GameView(Context context) {
		super(context);
		sound=new Sound(context);//创建音效对象
		Resources res=getResources();//获取系统资源
		bitmap=BitmapFactory.decodeResource(res, R.drawable.background);
		ready=BitmapFactory.decodeResource(res, R.drawable.ready);
		go=BitmapFactory.decodeResource(res, R.drawable.go);
		key=BitmapFactory.decodeResource(res, R.drawable.key);
		line=BitmapFactory.decodeResource(res, R.drawable.line);
		frt=BitmapFactory.decodeResource(res, R.drawable.frt);
		button=BitmapFactory.decodeResource(res, R.drawable.button);
		acc=BitmapFactory.decodeResource(res, R.drawable.acc);
		miss=BitmapFactory.decodeResource(res, R.drawable.miss);
		per=BitmapFactory.decodeResource(res, R.drawable.perfect);
		over=BitmapFactory.decodeResource(res, R.drawable.over);
		press=BitmapFactory.decodeResource(res, R.drawable.pre);
		for (int i = 0; i < scoreArr.length; i++) {
			scoreArr[i]=BitmapFactory.decodeResource(res, R.drawable.n0+i);
		}
		//线的切割以及绘制
		int imgw=line.getWidth()/2;
		int imgh=line.getHeight();
		for( int i=0;i<lineArr.length;i++){
			lineArr[i]=Bitmap.createBitmap(line, i*imgw, 0, imgw,imgh);
		}
		for( int i=0;i<lineSample.length;i++){
			lineSample[i]=new Sample(lineArr, lineX[i], 5);
		}
		
		//音频切割以及绘制
		imgw=frt.getWidth();
		imgh=frt.getHeight()/3;
		for (int i = 0; i < frtArr.length; i++) {
			frtArr[i]=Bitmap.createBitmap(frt,0,i*imgh,imgw,imgh);
		}
		int x=(517-imgw)/2;
		int y=720-20-imgh;
		frtSample=new  Sample(frtArr,x,y);
		//按钮切割以及绘制
		imgw=button.getWidth()/2;
		imgh=button.getHeight();
		for (int i = 0; i < buttonArr.length; i++) {
			buttonArr[i]=Bitmap.createBitmap(button,i*imgw,0,imgw,imgh);
		}
		
		sound.soundPlay(1);//播放ready go音效
		new Thread(this).start();//开启线程
		postInvalidate();//调用onDraw 方法
		
	}
	//使用画布和画笔进行绘制界面布局
    @Override
    protected void onDraw(Canvas canvas) {
//    	Paint p=new Paint();
//    	p.setColor(Color.RED);
//    	canvas.drawCircle(50, 50, 50, p);
//    	canvas.drawLine(50,50,300,300,p)
    	canvas.drawBitmap(bitmap, 0, 0, null);
    	int x=0;
    	int y=0;
    	switch (state) {
		case READY:
			x=(getWidth()-ready.getWidth())/2;
			y=(getHeight()-ready.getHeight())/2;
			canvas.drawBitmap(ready, x,y,null);
			break;
		case GO:
			x=(getWidth()-go.getWidth())/2;
			y=(getHeight()-go.getHeight())/2;
			canvas.drawBitmap(go, x,y,null);
			break;
		case PLAY:
			canvas.drawBitmap(key, 517,0,null);
			if(isPress){
				canvas.drawBitmap(press, bottonX[pressIndex], 5, null);
			}
			for (int i = 0; i < lineSample.length; i++) {
				lineSample[i].onRent(canvas);
			}
			frtSample.onRent(canvas);//音频绘制
			for (int i = 0; i < list.size(); i++) {
				list.get(i).onRent(canvas);//绘制多个按钮
			}
			String str=score+"";
			drawScore(str, 517, 200, canvas);
			if(isMiss){
				canvas.drawBitmap(miss,(517-miss.getWidth())/2,80,null);
			}
			if(isPer){
				canvas.drawBitmap(per,(517-per.getWidth())/2,80,null);
			}
			break;
		case OVER:
			canvas.drawBitmap(over, 0, 0, null);
			drawScore(score+"", 517, 400, canvas);
		    break;
		}
    }
    public void run(){
    	postInvalidate();
    	try{
    		Thread.sleep(3000);
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	state=GO;
    	postInvalidate();
    	try{
    		Thread.sleep(1000);
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	state=PLAY;
    	while(isRun){
    		onTouch();
    		onUpDate();
    		postInvalidate();
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
    	}
    	
    }
    /**更新数据*/
    public void onUpDate(){
    	for (int i = 0; i < lineSample.length; i++) {
    		lineSample[i].onUpdate();
		}
    	frtSample.onUpdate();
        int index=(int)(Math.random()*10)%4;
    	if(list.size()==0){
    		//3、按钮的初始绘制位置；4、调整按钮下落的速度
    		list.add(new ButtonSample(buttonArr, bottonX[index], -20, 10, acc));
    	}else {
    		int y=list.get(list.size()-1).y;
    		int h=buttonArr[0].getHeight();
    		//y>h*3当前按钮的下落距离大于按钮高度的3倍是，后一个下落
    		if(y>h*3){
    			list.add(new ButtonSample(buttonArr, bottonX[index],-20, 10, acc));
    		}
    		
    	}
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).onUpdate();	
		}
    	for (int i = 0; i < list.size(); i++) {
			if(list.get(i).y>720){
				list.remove(i);
				sco=0;
				isMiss=true;
			}
		}
    	for (int i = 0; i < list.size(); i++) {
			if(list.get(i).state==list.get(i).CLEAN){
				list.remove(i);
			}
		}
    }
    @Override
    	public boolean onTouchEvent(MotionEvent event) {
    		touchA=event.getAction();
    		touchX=(int) event.getX();
    		touchY=(int) event.getY();
    		return true;
    	}
    public void onTouch(){
    	switch (touchA) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:	
			if(touchY>5+lineArr[0].getHeight()){
				for (int i = 0; i <list.size(); i++) {
					if(list.get(i).isKill(touchX,touchY)){
						sound.soundPlay(2);
						sco++;
						if(sco>4){
							isPer=true;
						}
						score+=sco;
						if(score>10){
							state=OVER;
						}
						isMiss=false;
					}
				}
				for (int i = bottonX.length-1; i >=0; i--) {
					if(touchX>bottonX[i]){
						pressIndex=i;
						isPress=true;
						break;
					}
				}
			}else{
				isPress=false;
			}
			break;
		case MotionEvent.ACTION_UP:
			isPer=false;//抬起时per图片消失
			break;
		}
    }
    /**
     * 绘制分数的方法
     * @param score分数
     * @param x x坐标
     * @param y  y坐标
     * @param canvas 画布
     */
    public void drawScore(String score,int x,int y,Canvas canvas){
    	char[] ch=score.toCharArray();
    	int length=ch.length;
    	int imgw=scoreArr[0].getWidth();
    	int x1=(x-length*imgw)/2;
    	for (int i = 0; i < ch.length; i++) {
    		int index=Integer.parseInt(ch[i]+"");
			canvas.drawBitmap(scoreArr[index], x1+i*imgw, y, null);
		}
    }
}
