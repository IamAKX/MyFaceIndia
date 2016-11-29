package com.akash.applications.myfaceindia;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;

public class ChooseFromGallery extends Activity {

    private static final int PICK_FROM_GALLERY_POST = 56;

    private String encryptedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_from_gallery);
        launchGallery();
    }
    private void launchGallery() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop", "true");

        try {

            intent.putExtra("return-data-post", true);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), PICK_FROM_GALLERY_POST);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Unable to launch gallery", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage(final String encryptedString) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"creategroup.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().trim().equalsIgnoreCase("success"))
                        {
                            Toast.makeText(getBaseContext(),"Group created successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getBaseContext(),"Failed to create group",Toast.LENGTH_LONG).show();

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

                params.put("uid", PreferenceSaver.getUserID(getBaseContext()));
                params.put("name",getIntent().getStringExtra("name"));
                params.put("title",getIntent().getStringExtra("title"));
                params.put("image",encryptedString);
                params.put("privacy", getIntent().getStringExtra("privacy"));
                params.put("control",getIntent().getStringExtra("control"));
                params.put("desc",getIntent().getStringExtra("desc"));
                params.put("gdate",getIntent().getStringExtra("gdate"));


                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLERY_POST) {
            Log.i("Checking", "Gallery");

            Bitmap finalBitmap = null;
            try {
                Bundle extras = data.getExtras();

                finalBitmap = extras.getParcelable("data");
            }
            catch (Exception e)
            {
                try {
                    finalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            encryptedString = ImageFormatChanger.getStringImage(finalBitmap);

            new ContactServer().execute();

            // Toast.makeText(this, encryptedString, Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(getBaseContext(),Home.class));
        finish();
    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            uploadImage(encryptedString);
            return null;
        }
    }
}