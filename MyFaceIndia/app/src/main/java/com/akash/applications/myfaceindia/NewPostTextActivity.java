package com.akash.applications.myfaceindia;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
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

import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;

public class NewPostTextActivity extends AppCompatActivity {
    Bitmap textImage;
    private String encryptedString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_text);


        encryptedString = getIntent().getStringExtra("postText");
        Toast.makeText(getBaseContext(), "Uploading...", Toast.LENGTH_SHORT).show();
        new ContactServer().execute();

    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            uploadImage(encryptedString);
            return null;
        }
    }

    private void uploadImage(final String encryptedString) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "post.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking", response + " ");
                        if (response.toString().equals("success")) {
                            Toast.makeText(NewPostTextActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(NewPostTextActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                        finish();

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
                params.put("uid", PreferenceSaver.getUserID(NewPostTextActivity.this));
                params.put("username", getIntent().getStringExtra("uname"));
                params.put("userpost", encryptedString);
                params.put("date", DateDifference.timeNow());
                params.put("type", "2");
                params.put("access", getIntent().getStringExtra("acc"));
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);

    }

}
