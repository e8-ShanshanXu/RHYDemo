package com.example.rhydemo;

import com.rhydemo.view.GameView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class GameActivity extends MainActivity {
    GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        music.addMusic(R.raw.wanner, true);
        super.onCreate(savedInstanceState);
        gv = new GameView(this);
        setContentView(gv);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon1);
        builder.setTitle("警告");
        builder.setMessage("是否退出游戏?");
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //游戏结束后将资源清空gameview 释放掉
        if (gv != null) {
            gv.isRun = false;
        }
        gv = null;
        super.onDestroy();
    }
}
