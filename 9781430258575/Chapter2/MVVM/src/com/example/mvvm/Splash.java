package com.example.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class Splash extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        // TIP: Optimized the onSomething() classes, especially onCreate() (DONE)
        // Load all the necessary data here at startup because it is
        // a bit faster than loading in other methods

        super.onCreate(savedInstanceState);

        // TIP: View - inflate the views a minimum number of times (DONE)
        // inflating views are expensive
        /*for (int i=0; i<10000; i++)
            setContentView(R.layout.splash);*/

        // TIP: Splashscreen optional  (DONE)
        setContentView(R.layout.splash);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openStartingPoint = new Intent("com.example.mvvm.TodoActivity");
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }
}