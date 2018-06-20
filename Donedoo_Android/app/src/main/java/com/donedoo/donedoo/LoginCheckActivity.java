package com.donedoo.donedoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.donedoo.model.Data;

public class LoginCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        login();

    }

    public void login() {

        final ProgressDialog dialog = ProgressDialog.show(LoginCheckActivity.this, "","Loading..Wait.." , true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if(Data.getCheck().equals("wait")) {
                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                check();
                dialog.dismiss();


            }

        }, 2000);
    }

    public  void check(){
        if(Data.getCheck().equals("exist")){
            Intent intent = new Intent(this, DoActivity.class);
            startActivity(intent);
        }else{
          //  final MediaPlayer mp4 = MediaPlayer.create(this, R.raw.stairs);
           // mp4.start();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
    }
}
