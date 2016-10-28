package com.akash.applications.myfaceindia;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import Connection.ConnectionDetector;
import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import PostAdapter.AdapterForTimeline;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline extends Fragment {


    ImageView accessControllImageView;
    EditText statusET;
    TextView buttonPost;
    String mystatus="";
    int access = 1;
    ListView lv;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accessControllImageView = (ImageView) getView().findViewById(R.id.ivTimelineAccessControll);

        statusET = (EditText)getView().findViewById(R.id.timelineEditText);
        buttonPost = (TextView)getView().findViewById(R.id.timelineEditText);

        lv = (ListView)getView().findViewById(R.id.timeline_listview);
        swipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.timeline_swipe_refresh_layout);

        fillListView();

        final ConnectionDetector cd = new ConnectionDetector(getContext());

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    if(!statusET.getText().toString().equals(""))
                    {
                        mystatus=statusET.getText().toString().trim();
                        new ContactServer().execute();
                        statusET.setText("");
                    }
                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillListView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.themeBackground,R.color.colorPrimary,R.color.themeBackground);

        registerForContextMenu(accessControllImageView);
    }

    private void fillListView() {
        lv.setAdapter(new AdapterForTimeline(getActivity().getBaseContext()));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.i("viewTesting","button"+v.toString()+"        "+v.getId());
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.access_control_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.publicAccess:
                access=1;
                accessControllImageView.setImageResource(R.drawable.publicbtn);
                return true;
            case R.id.friendAccess:
                access=2;
                accessControllImageView.setImageResource(R.drawable.friends);
                return true;
            case R.id.privateAccess:
                access=3;
                accessControllImageView.setImageResource(R.drawable.privatebtn);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public Timeline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            postMyStatus();
            return null;
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
                            fillListView();
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
}
