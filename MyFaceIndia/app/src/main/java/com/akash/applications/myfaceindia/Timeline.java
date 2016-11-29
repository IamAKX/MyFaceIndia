package com.akash.applications.myfaceindia;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Connection.ConnectionDetector;
import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import PostAdapter.AdapterForTimeline;
import PostAdapter.TimelineModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline extends Fragment implements PopupMenu.OnMenuItemClickListener{


    ImageView accessControllImageView;
    EditText statusET;
    TextView buttonPost;
    String mystatus="";
    int access = 1;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<TimelineModel> statusData = new ArrayList<>();
    AdapterForTimeline adapterForTimeline;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accessControllImageView = (ImageView) getView().findViewById(R.id.ivTimelineAccessControll);
        statusET = (EditText)getView().findViewById(R.id.timelineEditText);
        buttonPost = (TextView)getView().findViewById(R.id.timelineEditText);

        recyclerView = (RecyclerView) getView().findViewById(R.id.timeline_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.timeline_swipe_refresh_layout);
        adapterForTimeline = new AdapterForTimeline(getActivity(), statusData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterForTimeline);
        new ContactServerFetch().execute();
        adapterForTimeline.notifyDataSetChanged();
        recyclerView.invalidate();

        final ConnectionDetector cd = new ConnectionDetector(getContext());

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    if(!statusET.getText().toString().equals(""))
                    {
                        mystatus=statusET.getText().toString().trim();
                        new ContactServerPost().execute();
                        statusET.setText("");
                    }
                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.themeBackground,R.color.colorPrimary,R.color.themeBackground);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshing();

            }
        });


        accessControllImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
    }

    private void startRefreshing() {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterForTimeline.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

        }, 2000);
    }

    private void showPopup() {
        PopupMenu menu = new PopupMenu(getActivity(), accessControllImageView);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.access_control_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publicAccess:
                accessControllImageView.setImageResource(R.drawable.publicbtn);
                return true;
            case R.id.friendAccess:
                accessControllImageView.setImageResource(R.drawable.friends);
                return true;
            case R.id.privateAccess:
                accessControllImageView.setImageResource(R.drawable.privatebtn);
                return true;
            default:
                return false;
        }
    }

    private void postMyStatus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"statuspost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().equals("success"))
                        {
                            Toast.makeText(getContext(), "Status added to timeline", Toast.LENGTH_SHORT).show();
                            adapterForTimeline.notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(getContext(), "Failed to update status", Toast.LENGTH_SHORT).show();
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

                params.put("uid", PreferenceSaver.getUserID(getContext()));
                params.put("status",mystatus);
                params.put("date", DateDifference.timeNow());
                params.put("access", String.valueOf(access));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);

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

                params.put("uid", PreferenceSaver.getUserID(getActivity()));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                Toast.makeText(getActivity(), "No status", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < result.length(); i++) {
                JSONObject data = result.getJSONObject(i);
                String _a = data.getString("status");
                String _b = data.getString("stime");
                statusData.add(new TimelineModel(_b, _a));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class ContactServerPost extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            postMyStatus();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterForTimeline.notifyDataSetChanged();
        }
    }

    private class ContactServerFetch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getStatusJSON();
            return null;
        }


    }
}
