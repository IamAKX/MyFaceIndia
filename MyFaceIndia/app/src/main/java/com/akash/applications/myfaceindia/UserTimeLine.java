package com.akash.applications.myfaceindia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.UserConfig;
import PostAdapter.AdapterForTimeline;
import PostAdapter.TimelineModel;

public class UserTimeLine extends AppCompatActivity {

    ImageView iv;
    String uid,uimg;
    RecyclerView recyclerView;
    TextView tv;
    SwipeRefreshLayout swipeRefreshLayout;
    AdapterForTimeline adapterForTimeline;
    ArrayList<TimelineModel> statusData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_time_line);
        uid = getIntent().getStringExtra("uID");
        uimg = getIntent().getStringExtra("uImage");
        getSupportActionBar().setTitle(getIntent().getStringExtra("uName"));
        iv = (ImageView)findViewById(R.id.userTimelineUserImage);
        recyclerView = (RecyclerView) findViewById(R.id.user_timeline_recycler);
        tv = (TextView)findViewById(R.id.textViewPromptNoStatus);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.user_timeline_swipe_refresh_layout);
        tv.setVisibility(View.GONE);
        adapterForTimeline = new AdapterForTimeline(UserTimeLine.this, statusData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterForTimeline);
        new ContactServer().execute();
        adapterForTimeline.notifyDataSetChanged();
        recyclerView.invalidate();
        Glide.with(getBaseContext()).load(uimg)
                .placeholder(R.drawable.userdp)
                .into(iv);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.themeBackground,R.color.colorPrimary,R.color.themeBackground);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshing();
            }
        });
    }

    private void startRefreshing() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterForTimeline.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

        },2000);
    }

    private void getStatusJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "statusfetch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking", response + " ");
                        if (!response.toString().equals("failure")) {

                            parseJSON(response);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put("uid", uid);
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(UserTimeLine.this);
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);
    }

    private void parseJSON(String response) {

        Log.i("Checking", " fullJSON " + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("statusarray");
            Log.i("Checking", " JSONResult " + result + "\n" + result.length());
            if (result == null) {
                Toast.makeText(UserTimeLine.this, "No status", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < result.length(); i++) {
                JSONObject data = result.getJSONObject(i);
                String _a = data.getString("status");
                String _b = data.getString("stime");
                /*statuslist.add(new String(_a));
                datelist.add(new String(_b));*/
                statusData.add(new TimelineModel(_b, _a));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* for (int i = 0; i < statuslist.size(); i++) {
            list.add(new TimelineModel(datelist.get(i), statuslist.get(i)));
        }*/

    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getStatusJSON();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterForTimeline.notifyDataSetChanged();
        }
    }
}
