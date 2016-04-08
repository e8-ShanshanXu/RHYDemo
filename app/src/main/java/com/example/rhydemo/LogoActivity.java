package com.example.rhydemo;

import android.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends MainActivity {
    ImageView iv_logo1;
    ImageView iv_logo2;

    //当界面出现
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        iv_logo1 = (ImageView) findViewById(R.id.iv_logo1);
        iv_logo2 = (ImageView) findViewById(R.id.iv_logo2);
        //透明度动画类
        AlphaAnimation anima_logo1 = new AlphaAnimation(0.0f, 1.0f);
        anima_logo1.setDuration(3000);//3秒
        anima_logo1.setStartOffset(1000);
        iv_logo1.startAnimation(anima_logo1);//开启动画
        AlphaAnimation anima_logo2 = new AlphaAnimation(1.0f, 0.0f);
        anima_logo2.setDuration(3000);
        //当动画结束后，是否保持结束时不透明样式
        anima_logo2.setFillAfter(true);

        iv_logo2.startAnimation(anima_logo2);
        anima_logo1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // 动画开启之前调用

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // 动画播放中调用

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // 动画结束之后调用
                //信使，从某和activity跳入到另一个activity
                Intent intent = new Intent(LogoActivity.this, MenuActitvity.class);
                startActivity(intent);
                //销毁当前的activity
                finish();
            }
        });
        //当动画结束时调用该方法
    }
    // TODO Auto-generated method stub


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
