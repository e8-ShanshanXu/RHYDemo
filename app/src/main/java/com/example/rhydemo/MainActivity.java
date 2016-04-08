package com.example.rhydemo;

import com.rhydemo.music.Music;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public static Music music;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉通知栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if(music==null){
			music=new Music(this);
		}
	}
   @Override
   protected void onStart() {
	
	   super.onStart();
	   if(music!=null){
		   music.playMusic();
	   }
   }
	@Override
	protected void onPause() {
		
		super.onPause();
		music.pauseMusic();
	}

}
