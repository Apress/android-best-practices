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

        TextView login = (TextView) findViewById(R.id.login);
        login.setText(Unix.getlogin());
    }
}

