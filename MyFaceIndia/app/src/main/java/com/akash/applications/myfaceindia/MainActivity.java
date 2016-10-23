package com.akash.applications.myfaceindia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
       Thread background = new Thread() {

            public void run() {
                try {
                    sleep(2500);
                    launchNext();
                } catch (Exception e) {
                }
            }

        };

        background.start();

    }


    private void launchNext() {

        startActivity(new Intent(getBaseContext(),UserCredential.class));
        finish();
    }
}
