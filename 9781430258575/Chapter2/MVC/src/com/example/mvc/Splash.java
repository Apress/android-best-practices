package com.example.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class Splash extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        // Load all the necessary data here at startup because it is
        // a bit faster than loading in other methods

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openStartingPoint = new Intent("com.example.mvc.TodoActivity");
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }
}