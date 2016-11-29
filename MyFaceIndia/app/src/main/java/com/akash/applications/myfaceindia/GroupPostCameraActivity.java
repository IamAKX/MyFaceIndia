package com.akash.applications.myfaceindia;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;

public class GroupPostCameraActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA_POST = 65;
    private static final int PIC_CROP = 75;

    private String encryptedString;
    private Uri profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_camera);

        launchCamera();
    }

    private void launchCamera() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String imgDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFaceIndia/Media/Images";
            File f = new File(imgDirectory);
            if (!f.exists())
                f.mkdirs();

            File imageFile = new File(imgDirectory + "/IMG_" + System.currentTimeMillis() + ".jpg");
            profilePic = Uri.fromFile(imageFile); // convert path to Uri
            Log.i("Cropping", profilePic.getPath() + " ");
            Log.i("Cropping uri", profilePic + " ");
            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, profilePic);
            startActivityForResult(takePictureIntent, PICK_FROM_CAMERA_POST);

        } catch (ActivityNotFoundException err) {
            Toast.makeText(getBaseContext(), "Camera Not available or Give the permission of the camera from the Settings manually", Toast.LENGTH_LONG).show();
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Log.i("Checking", "Ok restlut");
            if (requestCode == PICK_FROM_CAMERA_POST) {
                Log.i("Checking", "camera");
                performCrop(profilePic);
                //performCrop(data.getData());
            } else if (requestCode == PIC_CROP) {

                Log.i("Checking", "crop");
                Bundle extras = data.getExtras();
                Bitmap finalPic = extras.getParcelable("data");
                //userProfilePicture.setImageBitmap(finalPic);

                encryptedString = ImageFormatChanger.getStringImage(finalPic);
                Toast.makeText(GroupPostCameraActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
                new ContactServer().execute();

            }

        } else
            finish();


    }

    private void performCrop(Uri data) {

        try {

            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(data, "image/*");
            //retrieve data on return
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);

            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException err) {
            Log.i("Cropping not", data + " ");
            Toast.makeText(getBaseContext(), "Failed to crop image", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage(final String encryptedString) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL + "grouppost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking", response + " ");
                        if (response.toString().equals("success")) {
                            Toast.makeText(GroupPostCameraActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(GroupPostCameraActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
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
                params.put("gid", getIntent().getStringExtra("gid"));
                params.put("uid", PreferenceSaver.getUserID(GroupPostCameraActivity.this));
                params.put("username", getIntent().getStringExtra("uname"));
                params.put("userpost", encryptedString);
                params.put("date", DateDifference.timeNow());
                params.put("type", "1");
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

    private class ContactServer extends AsyncTask<Void, Void, Void> {

        ProgressDialog uploading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uploading = ProgressDialog.show(GroupPostCameraActivity.this, "Uploading File", "Please wait...", false, false);
        }

        @Override
        protected Void doInBackground(Void... params) {

            uploadImage(encryptedString);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            uploading.dismiss();
            Intent i = new Intent(GroupPostCameraActivity.this, GroupPage.class);
           // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("gid", getIntent().getStringExtra("gid"));
            i.putExtra("image", getIntent().getStringExtra("image"));
            i.putExtra("name",getIntent().getStringExtra("name"));
            i.putExtra("title",getIntent().getStringExtra("title"));
            i.putExtra("date",getIntent().getStringExtra("date"));
            startActivity(i);
            finish();
        }
    }
}

