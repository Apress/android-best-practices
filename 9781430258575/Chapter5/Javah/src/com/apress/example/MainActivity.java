/**
 * Android Best Practices - Apress Publishing.
 */

package com.apress.example;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            // Using the native method as an ordinary Java method
            String value = nativeMethod(1, this);
        } catch (IOException ex) {
        }
    }

    /**
     * Native method that is implemented using C/C++.
     *
     * @param index integer value.
     * @param activity activity instance.
     * @return string value.
     * @throws IOException
     */
    private static native String nativeMethod(int index, Activity activity) 
            throws IOException;
}

