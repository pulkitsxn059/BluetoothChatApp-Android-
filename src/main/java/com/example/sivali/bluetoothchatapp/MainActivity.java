package com.example.sivali.bluetoothchatapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    Animation animation;
    ImageView iv;
    BluetoothAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_animation);
        iv=(ImageView)findViewById(R.id.imageView);
        iv.setAnimation(animation);
        animation.setAnimationListener(this);
        adapter=BluetoothAdapter.getDefaultAdapter();
        new MyThread().start();
        }
    public class MyThread extends Thread
    {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            }
            catch(Exception e)
            {}
            Intent intn=new Intent(MainActivity.this,MainPage.class);
            startActivity(intn);

        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(!adapter.isEnabled())
        {
            adapter.enable();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

