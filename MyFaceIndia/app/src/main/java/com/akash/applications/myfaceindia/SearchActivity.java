package com.akash.applications.myfaceindia;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

import HelperPackage.UserConfig;
import Search.SearchAdapter;
import Search.SearchResModel;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private ArrayList<SearchResModel> data = new ArrayList<>();
    private SearchAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
        Log.i("query", query);
        getSupportActionBar().setTitle("Search results");
        adapter = new SearchAdapter(this, data);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "searchuser.php",
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

                        params.put("uname", query);
                        return params;
                    }
                };

                //Adding the string request to the queue
                stringRequest.setShouldCache(false);

                RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
                requestQueue.getCache().clear();

                requestQueue.add(stringRequest);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();

        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    private void parseJSON(String response) {

        Log.i("Checking", " fullJSON " + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("userarray");
            Log.i("Checking", " JSONResult " + result + "\n" + result.length());
            for (int i = 0; i < result.length(); i++) {
                JSONObject j = result.getJSONObject(i);
                data.add(new SearchResModel(
                        j.getString("uid"),
                        j.getString("pname"),
                        j.getString("uimg")
                ));
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
