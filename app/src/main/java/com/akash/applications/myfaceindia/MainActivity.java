package com.akash.applications.myfaceindia;

import com.akash.applications.myfaceindia.UserSharedPref.SharedPref;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView)findViewById(R.id.textView2);
        launchNext();

    }

    private void launchNext() {
        //new UserInfoManager().saveDetail(getActivity(), inputName.getText().toString(), inputEmail.getText().toString());
        if(!new SharedPref().isExist(getBaseContext())||(new SharedPref().getEmail(getBaseContext()).equals(null) || new SharedPref().getName(getBaseContext()).equals(null)))
        {

            msg="Signing Up...";
            runThread(1);

        }
        else
        {
            msg="Please Sign In or Sign Up";
            runThread(2);

        }

    }
    private void runThread(final int x) {

        new Thread() {
            public void run() {

                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                t.setText(msg);

                            }
                        });
                        Thread.sleep(3000);
                        if(x==1)
                            startActivity(new Intent(getBaseContext(), LoginRegister.class));
                        else
                            startActivity(new Intent(getBaseContext(),Home.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }.start();

    }
}
