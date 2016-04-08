package com.rhydemo.music;

import java.util.HashMap;

import com.example.rhydemo.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	/**音效池*/
    SoundPool pool;
    HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
    public Sound(Context context){
//    	第一个参数最高存储多少个音效
//    	第二个音效类型;
//    	第三个备用参数，现在无用
    	pool =new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    	map.put(1, pool.load(context,R.raw.ready, 1));
    	map.put(2, pool.load(context,R.raw.pa, 1));
    }
    public void soundPlay(int key){
    	//1、音效ID  2、左声道   3、右声道  4、级别   5、是否循环播放   6、播放速度(0是不循环)
    	pool.play(map.get(key), 1.0f, 1.0f, 0, 0, 1);
    }
}
