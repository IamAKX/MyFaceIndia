package com.akash.applications.myfaceindia;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;

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
import PostAdapter.PostFetchAdapter;
import PostAdapter.PostFetchModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment implements PopupMenu.OnMenuItemClickListener{

    private static final int SELECT_VIDEO = 599;
    ImageView accessControllImageView, arrowiv, userDP, postVideoButton;
    ImageView camButton, galleryButton;
    String name="",access="1";
    TextView postButton;
    EditText postText;
    private String selectedPath;
    private PostFetchAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<PostFetchModel> data = new ArrayList<>();



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(PreferenceSaver.getUserName(getContext()).equals(null))
            name = PreferenceSaver.getFname(getContext())+" "+PreferenceSaver.getLname(getContext());
        else
            name = PreferenceSaver.getUserName(getContext());


        accessControllImageView = (ImageView) getView().findViewById(R.id.ivMainPageAccessControll);
        arrowiv = (ImageView)getView().findViewById(R.id.hideshowpost);
        postButton = (TextView)getView().findViewById(R.id.post_button);
        postText = (EditText) getView().findViewById(R.id.post_edit);
        final ConnectionDetector cd = new ConnectionDetector(getContext());
        userDP = (ImageView)getView().findViewById(R.id.post_form_user_dp);



        Glide.with(getContext())
                .load(UserConfig.PROFILE_PIC_URL+PreferenceSaver.getImg(getContext()))
                .placeholder(R.drawable.userprofilepic)
                .into(userDP);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet()) {
                    if (!postText.getText().toString().trim().equals(null)) {
                        new PostText(postText.getText().toString().trim()).execute();
                    }
                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");
                postText.setText(null);
            }
        });
        final LinearLayout layout = (LinearLayout)getView().findViewById(R.id.postContentLayout);
        arrowiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility()==View.VISIBLE)
                {
                    layout.setVisibility(View.GONE);
                    arrowiv.setImageResource(android.R.drawable.arrow_up_float);
                }
                else
                {
                    layout.setVisibility(View.VISIBLE);
                    arrowiv.setImageResource(android.R.drawable.arrow_down_float);
                }
            }
        });

        accessControllImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        camButton = (ImageView)getView().findViewById(R.id.postCamButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet()) {
                    Intent intent = new Intent(getActivity(), NewPostCameraActivity.class);
                    intent.putExtra("uname", name);
                    intent.putExtra("acc", access);
                    getActivity().startActivity(intent);

                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");

            }
        });
        galleryButton = (ImageView)getView().findViewById(R.id.postGalleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(getActivity(), NewPostActivity.class);
                    intent.putExtra("uname", name);
                    intent.putExtra("acc", access);
                    getActivity().startActivity(intent);

                } else
                    cd.showSnackBar(getView(), "Check your Internet Connection.");
            }
        });

        postVideoButton = (ImageView) getView().findViewById(R.id.postVideoButton);
        postVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(getActivity(), NewVideoPostActivity.class);
                    intent.putExtra("uname", name);
                    intent.putExtra("acc", access);
                    getActivity().startActivity(intent);

                } else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");
            }
        });

        recyclerView = (RecyclerView) getView().findViewById(R.id.post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostFetchAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        new FetchData().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }



    public void refreshAdapter()
    {
        data.clear();
        adapter = new PostFetchAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        new FetchData().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
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
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publicAccess:
                access="1";
                accessControllImageView.setImageResource(R.drawable.publicbtn);
                return true;
            case R.id.friendAccess:
                access="2";
                accessControllImageView.setImageResource(R.drawable.friends);
                return true;
            case R.id.privateAccess:
                access="3";
                accessControllImageView.setImageResource(R.drawable.privatebtn);
                return true;
            default:
                return false;
        }

    }


    public void getJsonArray() {
        // final ArrayList<PostFetchModel> data = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "postfetch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        JSONArray res;

                        try {
                            obj = new JSONObject(response);
                            res = obj.getJSONArray(UserConfig.ARRAY_NAME);
                            for (int i = 0; i < res.length(); i++) {
                                try {
                                    JSONObject j = res.getJSONObject(i);
                                    PostFetchModel post = new PostFetchModel(
                                            j.getString("uid"),
                                            j.getString("pid"),
                                            j.getString("uprofilepic"),
                                            j.getString("uname"),
                                            j.getString("imgname"),
                                            j.getString("time"),
                                            j.getString("type"),
                                            j.getString("likes")
                                    );
                                    data.add(post);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );

        request.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();

        requestQueue.add(request);

        // return data;
    }


    private class FetchData extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            getJsonArray();
            return null;
        }


    }


    private class PostText extends AsyncTask<Void, Void, Void> {

        String text;

        public PostText(String text) {
            this.text = text;
        }

        @Override
        protected Void doInBackground(Void... params) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "post.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server
                            Log.i("Checking", response + " ");
                            if (response.toString().equals("success")) {
                                Toast.makeText(getContext(), "Posted successfully", Toast.LENGTH_SHORT).show();
                                refreshAdapter();

                            } else
                                Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                            Log.i("Checking", error + " ");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put("uid", PreferenceSaver.getUserID(getContext()));
                    params.put("username", name);
                    params.put("userpost", text);
                    params.put("date", DateDifference.timeNow());
                    params.put("type", "2");
                    params.put("access", access);
                    return params;
                }
            };

            //Adding the string request to the queue
            stringRequest.setShouldCache(false);

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.getCache().clear();

            requestQueue.add(stringRequest);
            return null;
        }

    }
}
