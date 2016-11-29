package com.akash.applications.myfaceindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import HelperPackage.UploadVideo;
import HelperPackage.UserConfig;

public class NewVideoPostActivity extends AppCompatActivity {
    private static final int SELECT_VIDEO = 132;
    String selectedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                new UploadVideoAsync().execute();
            }
        }


    }

    public String getPath(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = this.getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    class UploadVideoAsync extends AsyncTask<Void, Void, String> {

        ProgressDialog uploading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uploading = ProgressDialog.show(NewVideoPostActivity.this, "Uploading File", "Please wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            uploading.dismiss();

            Intent i = new Intent(NewVideoPostActivity.this, Home.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        }

        @Override
        protected String doInBackground(Void... params) {
            UploadVideo u = new UploadVideo();
            String msg = u.uploadVideo(
                    selectedPath,
                    getIntent().getStringExtra("uname"),
                    getIntent().getStringExtra("acc"),
                    NewVideoPostActivity.this);
            Log.i("Response", msg);

            updateVideoData(msg);
            return msg;
        }
    }

    private void updateVideoData(final String msg) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "post.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking", response + " ");
                        if (response.toString().equals("success")) {
                            Toast.makeText(NewVideoPostActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(NewVideoPostActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
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
                params.put("uid", PreferenceSaver.getUserID(NewVideoPostActivity.this));
                params.put("username", getIntent().getStringExtra("uname"));
                params.put("userpost", msg);
                params.put("date", DateDifference.timeNow());
                params.put("type", "3");
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
