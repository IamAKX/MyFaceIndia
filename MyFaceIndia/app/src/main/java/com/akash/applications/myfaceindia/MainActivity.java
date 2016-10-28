package com.akash.applications.myfaceindia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import HelperPackage.PreferenceSaver;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashScreen);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_in);

        relativeLayout.setAnimation(anim);
        Thread background = new Thread() {

            public void run() {
                try {
                    sleep(3000);
                    launchNext();
                } catch (Exception e) {
                }
            }

        };

        background.start();

    }


    private void launchNext() {
        if(PreferenceSaver.getLogin(getBaseContext()))
            startActivity(new Intent(getBaseContext(),Home.class));
        else
            startActivity(new Intent(getBaseContext(),UserCredential.class));
        finish();
    }
}
