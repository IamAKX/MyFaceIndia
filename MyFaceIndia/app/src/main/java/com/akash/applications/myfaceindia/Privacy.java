package com.akash.applications.myfaceindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import HelperPackage.SpinnerData;

public class Privacy extends AppCompatActivity {

    Spinner profileS,messageS,chatStatusS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        getSupportActionBar().setTitle("Privacy");
        profileS = (Spinner)findViewById(R.id.privacyProfile);
        messageS = (Spinner)findViewById(R.id.privacyMessage);
        chatStatusS = (Spinner)findViewById(R.id.privacyChatStatus);

        ArrayAdapter<String> profileA = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, new SpinnerData().getGroupPrivacy());
        profileA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileS.setAdapter(profileA);
        profileS.setOnItemSelectedListener(new ProfileSpinnerClass());

        ArrayAdapter<String> messageA = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, new SpinnerData().getPrivacy());
        messageA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        messageS.setAdapter(messageA);
        messageS.setOnItemSelectedListener(new MessageSpinnerClass());

        ArrayAdapter<String> chatStatusA = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, new SpinnerData().getOnline());
        chatStatusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chatStatusS.setAdapter(chatStatusA);
        chatStatusS.setOnItemSelectedListener(new ChatStatusSpinnerClass());
    }

    private class ProfileSpinnerClass implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class MessageSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class ChatStatusSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu_notification, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch(id)
        {
            case R.id.Save:
                Toast.makeText(getBaseContext(),"Settings are updated ",Toast.LENGTH_LONG).show();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
