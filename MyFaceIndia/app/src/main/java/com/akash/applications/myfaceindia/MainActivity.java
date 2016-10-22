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
        TextView tv = (TextView)findViewById(R.id.myFaceIndia);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_in);
        launchNext();
        tv.setAnimation(anim);
    }


    private void launchNext() {
        try {
            Thread.sleep(3000);
            startActivity(new Intent(getBaseContext(),UserCredential.class));
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
