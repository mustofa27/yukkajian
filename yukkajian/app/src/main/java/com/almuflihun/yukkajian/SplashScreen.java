package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import model.UserModel;
import utility.LocationGetter;

public class SplashScreen extends AppCompatActivity {
    Intent intent;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        userModel = new UserModel(this);
        if (userModel.GetUser() == null) {
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                    }

                }
            };
            timerThread.start();
        } else {
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            };
            timerThread.start();
        }
        LocationGetter.requestSingleUpdate(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
