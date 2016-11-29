package com.akash.applications.myfaceindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import Group.GroupPostFetchAdapter;
import Group.GroupPostModel;
import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;

public class GroupPage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final int SELECT_VIDEO = 599;
    static String image, title, date, gId;
    ImageView groupIV;
    TextView _title,_date;
    ImageView accessControllImageView, arrowiv, userDP, postVideoButton;
    ImageView camButton, galleryButton;
    String name = "", access = "1";
    TextView postButton;
    EditText postText;
    private String selectedPath;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GroupPostFetchAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<GroupPostModel> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_group_page);
            gId = getIntent().getStringExtra("gid");
            image = getIntent().getStringExtra("image");
            name = getIntent().getStringExtra("name");
            title = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");

        getSupportActionBar().setTitle(name);
        groupIV = (ImageView)findViewById(R.id.groupImage);
        _title = (TextView)findViewById(R.id.GroupPageTitle);
        _date = (TextView)findViewById(R.id.GroupPageDate);

        Glide.with(getBaseContext()).load(image)
                .placeholder(R.drawable.userdp)
                .into(groupIV);
        _title.setText(title);
        _date.setText("Created on "+date);

        accessControllImageView = (ImageView) findViewById(R.id.ivGroupPageAccessControl);
        arrowiv = (ImageView) findViewById(R.id.gr_hideshowpost);
        postButton = (TextView) findViewById(R.id.gr_post_button);
        postText = (EditText) findViewById(R.id.gr_post_edit);
        final ConnectionDetector cd = new ConnectionDetector(this);
        userDP = (ImageView) findViewById(R.id.gr_post_form_user_dp);
        Glide.with(this)
                .load(UserConfig.PROFILE_PIC_URL + PreferenceSaver.getImg(this))
                .placeholder(R.drawable.userprofilepic)
                .into(userDP);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    if (!postText.getText().toString().trim().equals(null)) {
                        new PostText(postText.getText().toString().trim()).execute();
                    }
                }

                postText.setText(null);
            }
        });
        final LinearLayout layout = (LinearLayout) findViewById(R.id.gr_postContentLayout);
        arrowiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                    arrowiv.setImageResource(android.R.drawable.arrow_up_float);
                } else {
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

        camButton = (ImageView) findViewById(R.id.gr_postCamButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(GroupPage.this, GroupPostCameraActivity.class);
                    intent.putExtra("gid", gId);
                    intent.putExtra("uname", PreferenceSaver.getUserName(GroupPage.this));
                    intent.putExtra("acc", access);
                    intent.putExtra("image", getIntent().getStringExtra("image"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("title",getIntent().getStringExtra("title"));
                    intent.putExtra("date",getIntent().getStringExtra("date"));
                    GroupPage.this.startActivity(intent);
                    finish();
                }


            }
        });
        galleryButton = (ImageView) findViewById(R.id.gr_postGalleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(GroupPage.this, GroupPostGalleryActivity.class);
                    intent.putExtra("gid", gId);
                    intent.putExtra("uname", PreferenceSaver.getUserName(GroupPage.this));
                    intent.putExtra("acc", access);
                    intent.putExtra("image", getIntent().getStringExtra("image"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("title",getIntent().getStringExtra("title"));
                    intent.putExtra("date",getIntent().getStringExtra("date"));

                    GroupPage.this.startActivity(intent);
                    finish();
                }
            }
        });

        postVideoButton = (ImageView) findViewById(R.id.gr_postVideoButton);
        postVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(GroupPage.this, GroupPostVideoActivity.class);
                    intent.putExtra("gid", gId);
                    intent.putExtra("uname", PreferenceSaver.getUserName(GroupPage.this));
                    intent.putExtra("acc", access);
                    intent.putExtra("image", getIntent().getStringExtra("image"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("title",getIntent().getStringExtra("title"));
                    intent.putExtra("date",getIntent().getStringExtra("date"));

                    GroupPage.this.startActivity(intent);
                    finish();
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.group_post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupPostFetchAdapter(GroupPage.this, data, getIntent().getStringExtra("gid"));
        recyclerView.setAdapter(adapter);
        new FetchData().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.gr_swipe_ref);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.themeBackground,R.color.colorPrimary,R.color.themeBackground);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.invalidate();
                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 2000
                );
            }
        });

    }

    public void refreshAdapter() {
        data.clear();
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupPostFetchAdapter(this, data, getIntent().getStringExtra("gid"));
        recyclerView.setAdapter(adapter);
        new FetchData().execute();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    private void showPopup() {
        PopupMenu menu = new PopupMenu(this, accessControllImageView);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.access_control_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(GroupPage.this);
        menu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publicAccess:
                access = "1";
                accessControllImageView.setImageResource(R.drawable.publicbtn);
                return true;
            case R.id.friendAccess:
                access = "2";
                accessControllImageView.setImageResource(R.drawable.friends);
                return true;
            case R.id.privateAccess:
                access = "3";
                accessControllImageView.setImageResource(R.drawable.privatebtn);
                return true;
            default:
                return false;
        }

    }


    public void getJsonArray() {
        // final ArrayList<PostFetchModel> data = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "grouppostfetch.php",
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
                                    GroupPostModel post = new GroupPostModel(
                                            j.getString("uid"),
                                            getIntent().getStringExtra("gid"),
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

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("gid", getIntent().getStringExtra("gid"));
                return params;
            }
        };

        request.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(GroupPage.this);
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "grouppost.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //If we are getting success from server
                            Log.i("Checking", response + " ");
                            if (response.toString().equals("success")) {
                                Toast.makeText(GroupPage.this, "Posted successfully", Toast.LENGTH_SHORT).show();
                                refreshAdapter();

                            }
                            else
                            {
                                Toast.makeText(GroupPage.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                            }


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
                    params.put("gid", getIntent().getStringExtra("gid"));
                    params.put("uid", PreferenceSaver.getUserID(GroupPage.this));
                    params.put("username", PreferenceSaver.getUserName(GroupPage.this));
                    params.put("userpost", text);
                    params.put("date", DateDifference.timeNow());
                    params.put("type", "2");
                    params.put("access", access);
                    return params;
                }
            };

            //Adding the string request to the queue
            stringRequest.setShouldCache(false);

            RequestQueue requestQueue = Volley.newRequestQueue(GroupPage.this);
            requestQueue.getCache().clear();

            requestQueue.add(stringRequest);
            return null;
        }

    }
    
    
}
