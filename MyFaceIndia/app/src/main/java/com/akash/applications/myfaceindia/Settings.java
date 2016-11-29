package com.akash.applications.myfaceindia;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements PopupMenu.OnMenuItemClickListener{


    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int PIC_CROP = 3 ;
    private Uri profilePic;
    String encryptedString="";
    TextView notificationTV,privacyTV,passwordTV;//,imageTV;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notificationTV = (TextView)getView().findViewById(R.id.settingNotification);
        privacyTV = (TextView)getView().findViewById(R.id.settingPrivacy);
        passwordTV = (TextView)getView().findViewById(R.id.settingPassword);
//        imageTV = (TextView)getView().findViewById(R.id.settingProfileImage);

        notificationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NotificationSetting.class));
            }
        });

        privacyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Privacy.class));
            }
        });

        passwordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Password.class));
            }
        });

  /*      imageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });*/
    }

    /*private void showPopup() {
        PopupMenu menu = new PopupMenu(getActivity(), imageTV);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.navheader_change_profileimage, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }

*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.takeImage:
                launchCamera();
                return true;
            case R.id.chooseFromGallary:
                launchGallary();
                return true;
            default:
                return false;
        }

    }

    private void launchGallary() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);

        try {

            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), PICK_FROM_GALLERY);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(),"Unable to launch gallary",Toast.LENGTH_LONG).show();
        }

    }


    private void launchCamera() {
        try{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String imgDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFaceIndia/Media/Images/Profile";
            File f = new File(imgDirectory);
            if(!f.exists())
                f.mkdirs();

            File imageFile = new File(imgDirectory+"/IMG_"+System.currentTimeMillis()+".jpg");
            profilePic = Uri.fromFile(imageFile); // convert path to Uri
            Log.i("Cropping",profilePic.getPath()+" ");
            Log.i("Cropping uri",profilePic+" ");
            takePictureIntent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, profilePic );
            startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);

        }

        catch (ActivityNotFoundException err)
        {
            Toast.makeText(getContext(),"Camera Not available or Give the permission of the camera from the Settings manually",Toast.LENGTH_LONG).show();
        }

    }

    private void performCrop(Uri data) {

        try{

            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(data, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 80);
            cropIntent.putExtra("outputY", 80);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch (ActivityNotFoundException err)
        {
            Log.i("Cropping not",data+" ");
            Toast.makeText(getContext(),"Failed to crop image",Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            if(requestCode == PICK_FROM_CAMERA)
            {
                Log.i("Checking","Camera");
                performCrop(profilePic);
                //performCrop(data.getData());
            }
            else if(requestCode == PIC_CROP){

                Bundle extras = data.getExtras();
                Bitmap finalPic = extras.getParcelable("data");
                Log.i("Checking","cropping");
                encryptedString = ImageFormatChanger.getStringImage(finalPic);
                new ContactServer().execute();
            }
            else if(requestCode == PICK_FROM_GALLERY)
            {
                Log.i("Checking","Gallery");
                Bundle extras = data.getExtras();
                Bitmap finalBitmap = extras.getParcelable("data");

                encryptedString = ImageFormatChanger.getStringImage(finalBitmap);
                new ContactServer().execute();
            }
        }

    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            uploadImage(encryptedString);
            return null;
        }
    }

    private void uploadImage(final String encryptedString) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"updateimage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().substring(0,7).equals("success"))
                        {
                            PreferenceSaver.saveImg(getContext(),response.substring(7));
                            Toast.makeText(getContext(), "Profile Picture updated successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Log.i("Checking",error+" ");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("uid",PreferenceSaver.getUserID(getContext()));
                params.put("userpost",encryptedString);
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
