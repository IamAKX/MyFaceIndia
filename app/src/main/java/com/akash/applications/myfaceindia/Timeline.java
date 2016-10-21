package com.akash.applications.myfaceindia;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.UserSharedPref.SharedPref;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Timeline extends Fragment {
    EditText postEditText;
    ImageView btnSend;
    ArrayList<PostItemText> items;
    public Timeline() {
        // Required empty public constructor
    }

    AdapterForText myAdapter;
    ListView listView;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postEditText = (EditText)getView().findViewById(R.id.etPost);
        btnSend = (ImageView)getView().findViewById(R.id.btnSend);


        listView = (ListView)getView().findViewById(R.id.listview);
        setPostList();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostText();
                myAdapter=null;
                setPostList();
            }
        });
    }

    private ArrayList<PostItemText> generateData() {
        items = new ArrayList<PostItemText>();
        items.clear();
        updateStatusList();

        return items;
    }

    private void setPostList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter = new AdapterForText(getContext(),generateData());
                listView.setAdapter(myAdapter);
            }
        });

    }

    private void updateStatusList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SharedPref.SERVER_URL+"statusfetch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("##Rply"," "+response);
                        if(!response.equalsIgnoreCase("fail")){

                          String w="",a,b;
                            int i=0;
                            while(i<response.length()) {
                                if(response.charAt(i)!='*') {
                                    w+=response.charAt(i);

                                }
                                else {
                                    a=w.substring(0,w.indexOf('#'));
                                    b=w.substring(w.indexOf('#')+1);
                                    items.add(new PostItemText(b,a));
                                    w="";
                                }
                                i++;
                            }
                        }
                        else
                        {
                            items.add(new PostItemText("No Status","2016-10-19 19:06:25"));
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

                params.put("uid",new SharedPref().getUserId(getContext()));



                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    private void sendPostText() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SharedPref.SERVER_URL+"statuspost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("##Rply"," "+response);
                        if(response.equalsIgnoreCase("success")){

                            Toast.makeText(getActivity(), "Post successfully!!", Toast.LENGTH_LONG).show();
                            postEditText.setText("");
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(getActivity(), "Post failed", Toast.LENGTH_LONG).show();
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

                params.put("uid",new SharedPref().getUserId(getContext()));
                params.put("status",postEditText.getText().toString().trim());
                params.put("date",getDateTime());
                params.put("type","text");


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return  formattedDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

}
