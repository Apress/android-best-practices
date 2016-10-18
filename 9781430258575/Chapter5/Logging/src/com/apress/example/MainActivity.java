/**
 * Android Best Practices - Apress Publishing.
 */

package com.apress.example;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * Logs messages to adb log from native code.
     */
    public static native void logMessages();

    static {
        System.loadLibrary("logger");
    }
}

