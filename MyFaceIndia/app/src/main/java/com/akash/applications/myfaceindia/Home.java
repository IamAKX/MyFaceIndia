package com.akash.applications.myfaceindia;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int PIC_CROP = 3;
    private static final int PERMISSION_REQ_CAMERA = 99 ;
    private static final int PERMISSION_REQ_STORAGE_WRITE = 88 ;
    private static final int PERMISSION_REQ_STORAGE_REA = 77 ;
    FragmentManager fm;
    ImageView userProfilePicture;
    TextView userProfileName;
    String encryptedString = "";
    private View navHeaderView;
    private Uri profilePic;
    private Activity myactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm=getSupportFragmentManager();
        myactivity = this;
        getSupportActionBar().setTitle("Home");
        fm.beginTransaction()
                .replace(R.id.container,new MainPage())
                .commit();

        requestPermission();
        requestStoragePermission();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        userProfilePicture = (ImageView)navHeaderView.findViewById(R.id.userProfilePicture);
        userProfileName = (TextView)navHeaderView.findViewById(R.id.userName);

        userProfileName.setText("Hello "+ PreferenceSaver.getUserName(getBaseContext())+" !");

        registerForContextMenu(userProfilePicture);
        //if(!PreferenceSaver.isPicExist(getBaseContext()) || PreferenceSaver.getProfilePic(getBaseContext()))
            updateProfileImage();
        if(!PreferenceSaver.isExist(getBaseContext()) || !PreferenceSaver.getSaveState(getBaseContext()))
            showProfilePrompt();

        Log.i("CheckingPref",PreferenceSaver.getDay(getBaseContext())+" "+PreferenceSaver.getMonth(getBaseContext())+" "+PreferenceSaver.getYear(getBaseContext())+" "+PreferenceSaver.getCountry(getBaseContext())+" ");
    }


    public void updateProfileImage() {
        Log.i("Checking","Updating dp");
        Glide.with(Home.this)
            .load(UserConfig.PROFILE_PIC_URL+PreferenceSaver.getImg(getBaseContext()))
            .placeholder(R.drawable.userprofilepic)
            .into(userProfilePicture);
    }

    private void showProfilePrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle("Profile Setup");
        builder.setMessage("It seem you have not edited your profile yet. Please provide us with your details so that we can give you appropriate suggestion.");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showProfileFragment();
            }
        });
        builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void showProfileFragment() {

        Fragment fragment = new Profile();
        getSupportActionBar().setTitle("Edit Profile");
        fm.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if(id == R.id.app_bar_search){
            startActivity(new Intent(this,SearchActivity.class));
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment) {
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        switch(id)
        {
            case R.id.HomePage:
                fragment = new MainPage();
                getSupportActionBar().setTitle("Home");
                changeFragment(fragment);
                break;
            case R.id.Profile:
                fragment = new Profile();
                getSupportActionBar().setTitle("Edit Profile");
                changeFragment(fragment);
                break;
            case R.id.Timeline:
                fragment = new Timeline();
                getSupportActionBar().setTitle("Timeline");
                changeFragment(fragment);
                break;
            case R.id.CreateGroup:
                fragment = new CreateGroup();
                getSupportActionBar().setTitle("Create Group");
                changeFragment(fragment);
                break;
            case R.id.MyGroup:
                fragment = new MyGroup();
                getSupportActionBar().setTitle("My Group");
                changeFragment(fragment);
                break;
            case R.id.AllEvents:
                fragment = new AllEvents();
                getSupportActionBar().setTitle("All Events");
                changeFragment(fragment);
                break;
            case R.id.Music:
                fragment = new Music();
                getSupportActionBar().setTitle("Music");
                changeFragment(fragment);
                break;
            case R.id.Picture:
                fragment = new Picture();
                getSupportActionBar().setTitle("Picture");
                changeFragment(fragment);
                break;
            case R.id.Video:
                fragment = new Video();
                getSupportActionBar().setTitle("Video");
                changeFragment(fragment);
                break;
           /* case R.id.Shared:
                fragment = new Shared();
                getSupportActionBar().setTitle("Shared");
                changeFragment(fragment);
                break;*/
            case R.id.Location:
                fragment = new Location();
                getSupportActionBar().setTitle("Location");
                changeFragment(fragment);
                break;
            case R.id.Chat:
                fragment = new Chat();
                getSupportActionBar().setTitle("Chat");
                changeFragment(fragment);
                break;
            case R.id.Friends:
                fragment = new Friends();
                getSupportActionBar().setTitle("Friends");
                changeFragment(fragment);
                break;
            case R.id.Notification:
                fragment = new Notification();
                getSupportActionBar().setTitle("Notification");
                changeFragment(fragment);
                break;
            case R.id.Settings:
                fragment = new Settings();
                getSupportActionBar().setTitle("Settings");
                changeFragment(fragment);
                break;
            case R.id.Logout:
                PreferenceSaver.saveDetail(getBaseContext(),"User","email@domain.com","default.jpg","0",false);
                PreferenceSaver.deleteUserProfile(getBaseContext());
                startActivity(new Intent(getBaseContext(),UserCredential.class));
                finish();
                return true;
            case R.id.About:
                fragment = new About();
                getSupportActionBar().setTitle("About Us");
                changeFragment(fragment);
                break;
            case R.id.ShareApp:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Download MyFaceIndia app");
                //TODO : Replace with actual url when published.
                i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.akash.applications.myfaceindia");
                startActivity(Intent.createChooser(i, "Share via.."));
            case R.id.Footer:
                break;

        }

        /*fm.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Change user profile picture by long pressing on navigation header view
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        Log.i("viewTesting","userdp"+v.toString()+"        "+v.getId());
        menuInflater.inflate(R.menu.navheader_change_profileimage,menu);
    }

    @Override
    protected void onPause() {
        unregisterForContextMenu(userProfilePicture);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerForContextMenu(userProfilePicture);
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.takeImage:
                launchCamera();
                return true;
            case R.id.chooseFromGallary:
                launchGallary();
                return true;

            default:
                return super.onContextItemSelected(item);
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
            Toast.makeText(getBaseContext(),"Camera Not available or Give the permission of the camera from the Settings manually",Toast.LENGTH_LONG).show();
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK ) {

            if(requestCode == PICK_FROM_CAMERA)
            {
                performCrop(profilePic);
                //performCrop(data.getData());
            }
            else if(requestCode == PIC_CROP){

                Bundle extras = data.getExtras();
                Bitmap finalPic = extras.getParcelable("data");
                //userProfilePicture.setImageBitmap(finalPic);
                encryptedString = ImageFormatChanger.getStringImage(finalPic);
                new ContactServer().execute();
            }
            else if(requestCode == PICK_FROM_GALLERY)
            {
                Bitmap finalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.userprofilepic);
               try {
                   Bundle extras = data.getExtras();
                   finalBitmap = extras.getParcelable("data");
                 //  userProfilePicture.setImageBitmap(finalBitmap);
                   encryptedString = ImageFormatChanger.getStringImage(finalBitmap);
                   new ContactServer().execute();
                   
               }
               catch (Exception e)
               {

                        performCrop(data.getData());
                       
                       


               }

            }
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
            Toast.makeText(getBaseContext(),"Failed to crop image",Toast.LENGTH_LONG).show();
        }

    }
      private void launchGallary() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);


        try {

            intent.putExtra("return-data", true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), PICK_FROM_GALLERY);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getBaseContext(),"Unable to launch gallary",Toast.LENGTH_LONG).show();
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
                            PreferenceSaver.saveImg(getBaseContext(),response.substring(7));
                            updateProfileImage();
                            Toast.makeText(getBaseContext(), "Profile Picture updated successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getBaseContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
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
                params.put("uid",PreferenceSaver.getUserID(getBaseContext()));
                params.put("userpost",encryptedString);
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);

    }

    private class ContactServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            uploadImage(encryptedString);
            return null;
        }
    }



    //Request for permission

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(myactivity, Manifest.permission.CAMERA))
            Toast.makeText(getBaseContext(), "Allow access to camera", Toast.LENGTH_SHORT).show();
        else
            ActivityCompat.requestPermissions(myactivity,new String[]{Manifest.permission.CAMERA},PERMISSION_REQ_CAMERA);

        if(ActivityCompat.shouldShowRequestPermissionRationale(myactivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Toast.makeText(getBaseContext(), "Allow access to write storage", Toast.LENGTH_SHORT).show();
        else
            ActivityCompat.requestPermissions(myactivity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ_STORAGE_WRITE);

        if(ActivityCompat.shouldShowRequestPermissionRationale(myactivity, Manifest.permission.READ_EXTERNAL_STORAGE))
            Toast.makeText(getBaseContext(), "Allow access to read storage", Toast.LENGTH_SHORT).show();
        else
            ActivityCompat.requestPermissions(myactivity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQ_STORAGE_REA);
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {

            }
            else
            {
                ActivityCompat.requestPermissions(myactivity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ_STORAGE_WRITE);
            }
        }

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch(requestCode){
//            case PERMISSION_REQ_CAMERA:
//
//        }
//    }
}
