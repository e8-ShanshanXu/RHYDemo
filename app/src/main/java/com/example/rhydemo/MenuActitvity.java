package com.example.rhydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuActitvity extends MainActivity implements OnTouchListener{
	ImageView iv_game;
	ImageView iv_about;
	ImageView iv_help;
	ImageView iv_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	music.addMusic(R.raw.trouble, true);
    	setContentView(R.layout.activity_menu);
    	
    	//将该图片添加触摸监听事件
    	iv_game=(ImageView) findViewById(R.id.btn_game);
    	iv_about=(ImageView) findViewById(R.id.btn_about);
        iv_help=(ImageView) findViewById(R.id.btn_help);
        iv_exit=(ImageView) findViewById(R.id.btn_exit);
    	iv_game.setOnTouchListener(this);
      	iv_about.setOnTouchListener(this);
    	iv_help.setOnTouchListener(this);
    	iv_exit.setOnTouchListener(this);
    }
    //触摸图片自动触发该方法
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		switch (v.getId()) {
		case R.id.btn_game:
			if(event.getAction()==MotionEvent.ACTION_DOWN){
		    	iv_game.setBackgroundResource(R.drawable.game01);
		    	return true;
		    }else if(event.getAction()==MotionEvent.ACTION_UP){
		    	iv_game.setBackgroundResource(R.drawable.game00);
		    	Intent intent=new Intent(MenuActitvity.this,GameActivity.class);
		    	startActivity(intent);
		    	return true;
		    }	
			break;
		
		case R.id.btn_about:
			if(event.getAction()==MotionEvent.ACTION_DOWN){
		    	iv_about.setBackgroundResource(R.drawable.about01);
		    	showToast("游戏关于");
		    	return true;
		    }else if(event.getAction()==MotionEvent.ACTION_UP){
		    	iv_about.setBackgroundResource(R.drawable.about00);
		    	
		    	return true;
		    }	
			break;
		
		case R.id.btn_help:
			if(event.getAction()==MotionEvent.ACTION_DOWN){
		    	iv_help.setBackgroundResource(R.drawable.help01);
		    	showToast("帮助按钮");
		    	return true;
		    }else if(event.getAction()==MotionEvent.ACTION_UP){
		    	iv_help.setBackgroundResource(R.drawable.help00);
		    	return true;
		    }	
			break;
		case R.id.btn_exit:
		
			//判断触摸是按下还是弹起
		    if(event.getAction()==MotionEvent.ACTION_DOWN){
		    	iv_exit.setBackgroundResource(R.drawable.exit01);
		    	return true;
		    }else if(event.getAction()==MotionEvent.ACTION_UP){
		    	iv_exit.setBackgroundResource(R.drawable.exit00);
		    	showDialog();
		    	return true;
		    }	
			break;

		
		}
		return false;
	}
	/**
	 * 弹出提示对话
	 */
	public void showToast(String toast){
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 弹出退出主菜单对话框
	 */
	public void showDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.icon1);
		builder.setTitle("警告");
		builder.setMessage("是否退出主菜单");
		builder.setPositiveButton("否", null);
		//第二个null继续游戏不做任何操作
		builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
				
			}
		});
		builder.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK){
			showDialog();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onRestart() {
		
		super.onRestart();
		music.addMusic(R.raw.trouble, true);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(music!=null){
			music.stopMusic();
		}
		
	}
}
