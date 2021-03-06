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

import Group.AdapterForGroup;
import Group.GroupModel;
import HelperPackage.PreferenceSaver;
import HelperPackage.SimpleDividerItemDecoration;
import HelperPackage.UserConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroup extends Fragment {

    ArrayList<GroupModel> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    private AdapterForGroup adapter;
    public MyGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
        recyclerView = (RecyclerView) getView().findViewById(R.id.my_group_recycler);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterForGroup(context, dataList);
        recyclerView.setAdapter(adapter);
        new ContactServer().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    private void getGroupJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"fetchgroup.php",
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
            JSONArray result = jsonObject.getJSONArray("groups");

            if(result==null)
            {
                Toast.makeText(context, "No Group", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i=0;i<result.length();i++)
            {
                JSONObject data = result.getJSONObject(i);
                GroupModel obj = new GroupModel(data.getString("id"),data.getString("name"),data.getString("title"),data.getString("image"),data.getString("privacy"),data.getString("control"),data.getString("description"),data.getString("gdate"));
                dataList.add(obj);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {

        }
    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getGroupJSON();
            return null;
        }


    }
}
