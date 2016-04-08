package com.rhydemo.music;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
   MediaPlayer player;
   Context context;
   public Music(Context context){
	   this.context=context;
   }
   /**
    * 加载音乐的方法
    * @param resid音乐的ID
    * @param isLoop控制音乐是否循环播放
    */
   public void addMusic(int resid,boolean isLoop){
	   stopMusic();
	   //加载音乐
	   player=MediaPlayer.create(context, resid);
	   try {
		player.prepare();
	} catch (IllegalStateException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	   //设置是否循环播放
	player.setLooping(isLoop) ;  
   }
   /**
    * 播放音乐
    */
   public void playMusic(){
	   if(player!=null){
		   //音乐没有播放的情况
		   if(!player.isPlaying()){
			   player.start();
		   }
	   }
   }
   //音乐暂停
   public void pauseMusic(){
	   if(player!=null){
		   if(player.isPlaying()){
			   player.pause();
		   }
	   }
   }
   /**
    * 停止音乐
    */
   public void stopMusic(){
	   if(player!=null){
		   //正在播放
		   if(player.isPlaying()){
			   player.stop();
			   player.reset();//播放器重置
		   }
	   }
	   player=null;
   }
}
