package com.akash.applications.myfaceindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Connection.ConnectionDetector;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;

public class NotificationSetting extends AppCompatActivity {
    int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0;
    Switch likeNoti,commentNoti,messageNoti,chatNoti,friendNoti,groupNoti,notiSound,emailComment,emailLike,emailFriendship,emailGroupInvite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        getSupportActionBar().setTitle("Notification Setting");

        likeNoti = (Switch)findViewById(R.id.switchLike);
        likeNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveLikeNotification(getBaseContext(),isChecked);
                if(isChecked)
                    a=1;
                else
                    a=0;
            }
        });

        commentNoti = (Switch)findViewById(R.id.switchComment);
        commentNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveCommentNotification(getBaseContext(),isChecked);
                if(isChecked)
                    b=1;
                else
                    b=0;
            }
        });

        messageNoti = (Switch)findViewById(R.id.switchMessage);
        messageNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveMessageNotification(getBaseContext(),isChecked);
                if(isChecked)
                    c=1;
                else
                    c=0;
            }
        });

        chatNoti = (Switch)findViewById(R.id.switchChat);
        chatNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveChatNotification(getBaseContext(),isChecked);
                if(isChecked)
                    d=1;
                else
                    d=0;
            }
        });

        friendNoti = (Switch)findViewById(R.id.switchFriend);
        friendNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveFriendNotification(getBaseContext(),isChecked);
                if(isChecked)
                    e=1;
                else
                    e=0;
            }
        });
        groupNoti = (Switch)findViewById(R.id.switchGroup);
        groupNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveGroupNotification(getBaseContext(),isChecked);
                if(isChecked)
                    f=1;
                else
                    f=0;
            }
        });
        notiSound = (Switch)findViewById(R.id.switchSound);
        notiSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveNotificationSound(getBaseContext(),isChecked);
                if(isChecked)
                    g=1;
                else
                    g=0;
            }
        });
        emailComment = (Switch)findViewById(R.id.switchComment);
        emailComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveEmailComment(getBaseContext(),isChecked);
                if(isChecked)
                    h=1;
                else
                    h=0;
            }
        });
        emailLike = (Switch)findViewById(R.id.switchEmailOnLike);
        emailLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveEmailLike(getBaseContext(),isChecked);
                if(isChecked)
                    i=1;
                else
                    i=0;
            }
        });
        emailFriendship = (Switch)findViewById(R.id.switchEmailOnFriendship);
        emailFriendship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveEmailFriendship(getBaseContext(),isChecked);
                if(isChecked)
                    j=1;
                else
                    j=0;
            }
        });
        emailGroupInvite = (Switch)findViewById(R.id.switchEmailOnGroupInvite);
        emailGroupInvite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceSaver.saveEmailGroupInvite(getBaseContext(),isChecked);
                if(isChecked)
                    k=1;
                else
                    k=0;
            }
        });

        initializeSettings();
    }

    private void initializeSettings() {
        if(PreferenceSaver.getLikeNotification(getBaseContext()))
        {
            a=1;
            likeNoti.setChecked(true);
        }
        if(PreferenceSaver.getCommentNotification(getBaseContext()))
        {
            b=1;
            commentNoti.setChecked(true);
        }
        if(PreferenceSaver.getMessageNotification(getBaseContext()))
        {
            c=1;
            messageNoti.setChecked(true);
        }
        if(PreferenceSaver.getChatNotification(getBaseContext()))
        {
            d=1;
            chatNoti.setChecked(true);
        }
        if(PreferenceSaver.getFriendNotification(getBaseContext()))
        {
            e=1;
            friendNoti.setChecked(true);
        }
        if(PreferenceSaver.getGroupNotification(getBaseContext()))
        {
            f=1;
            groupNoti.setChecked(true);
        }
        if(PreferenceSaver.getNotificationSound(getBaseContext()))
        {
            g=1;
            notiSound.setChecked(true);
        }
        if(PreferenceSaver.getEmailComment(getBaseContext()))
        {
            h=1;
            emailComment.setChecked(true);
        }
        if(PreferenceSaver.getEmailLike(getBaseContext()))
        {
            i=1;
            emailLike.setChecked(true);
        }
        if(PreferenceSaver.getEmailFriendship(getBaseContext()))
        {
            j=1;
            emailFriendship.setChecked(true);
        }
        if(PreferenceSaver.getEmailGroupInvite(getBaseContext()))
        {
            k=1;
            emailGroupInvite.setChecked(true);
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
        ConnectionDetector cd = new ConnectionDetector(getBaseContext());
        switch(id)
        {
            case R.id.Save:
                if(cd.isConnectingToInternet()) {
                        new ContactServer().execute();
                }
                else
                    Toast.makeText(getBaseContext(),"Check your Internet connection",Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            sendSetting();
            return null;
        }

    }

    private void sendSetting() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"notificationsetting.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().trim().equalsIgnoreCase("success"))
                        {
                            Toast.makeText(getBaseContext(),"Settings are saved successfully.",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Failed to save your settings on server.",Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("uid", PreferenceSaver.getUserID(getBaseContext()));
                params.put("likenotification", String.valueOf(a));
                params.put("commentnotification", String.valueOf(b));
                params.put("messagenotification", String.valueOf(c));
                params.put("chatnotification", String.valueOf(d));
                params.put("friendnotification", String.valueOf(e));
                params.put("groupnotification", String.valueOf(f));
                params.put("notificationsound", String.valueOf(g));
                params.put("emailcomment", String.valueOf(h));
                params.put("emaillike", String.valueOf(i));
                params.put("emailfriendship", String.valueOf(j));
                params.put("emailgroupinvite", String.valueOf(k));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);

    }

}
