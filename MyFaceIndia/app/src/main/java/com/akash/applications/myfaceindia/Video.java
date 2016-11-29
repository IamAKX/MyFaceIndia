package com.akash.applications.myfaceindia;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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
import Events.AdapterForEvents;
import Events.EventsModel;
import Events.ValidateLinks;
import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.SimpleDividerItemDecoration;
import HelperPackage.UserConfig;
import PostAdapter.CustomAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Video extends Fragment {

    ArrayList<EventsModel> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    private AdapterForEvents adapter;

    EditText etName,etUrl;
    TextView tv;
    String name,url;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();

        final ConnectionDetector cd = new ConnectionDetector(getContext());
        etName = (EditText)getView().findViewById(R.id.event_video_name);
        etUrl = (EditText)getView().findViewById(R.id.event_video_link);
        tv = (TextView)getView().findViewById(R.id.video_event_post_button);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    name = etName.getText().toString().trim();
                    url = etUrl.getText().toString().trim();
                    if(!name.equals("") && !url.equals(""))
                    {
                        if(ValidateLinks.validateVideo(url.substring(1+url.lastIndexOf('.'))))
                            new UploadEvent().execute();
                        else
                            Toast.makeText(getContext(),"Enter a valid video link",Toast.LENGTH_LONG).show();
                    }
                    etName.setText("");
                    etUrl.setText("");
                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection");

            }
        });
        recyclerView = (RecyclerView) getView().findViewById(R.id.video_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterForEvents(context, dataList);
        recyclerView.setAdapter(adapter);
        new ContactServer().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }


    public void refreshList()
    {
        dataList.clear();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterForEvents(context, dataList);
        recyclerView.setAdapter(adapter);
        new ContactServer().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    private class UploadEvent extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            uploadEvent();
            return null;
        }
    }

    private void uploadEvent() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"eventpost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.trim().equalsIgnoreCase("success"))
                        {
                            Toast.makeText(getContext(),"Event added successfully",Toast.LENGTH_LONG);
                            refreshList();
                        }
                        else
                            Toast.makeText(getContext(),"Failed to add event",Toast.LENGTH_LONG);

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
                params.put("username",PreferenceSaver.getUserName(getContext()));
                params.put("name",name);
                params.put("url",url);
                params.put("type","3");
                params.put("edatetime", DateDifference.timeNow());
                params.put("uimage",PreferenceSaver.getImg(getContext()));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);
    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            fetchEvents();
            return null;
        }
    }

    private void fetchEvents() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"eventfetch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        parseJSON(response);
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

                params.put("uid", PreferenceSaver.getUserID(context));
                params.put("type","3");
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);
    }

    private void parseJSON(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("events");

            if(result==null)
            {
                Toast.makeText(context, "No Event", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i=0;i<result.length();i++)
            {
                JSONObject data = result.getJSONObject(i);
                EventsModel obj = new EventsModel(data.getString("id"),data.getString("uname"),data.getString("name"),data.getString("url"),data.getString("type"),data.getString("edatetime"),data.getString("uimage"));
                dataList.add(obj);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {

        }
    }

}
