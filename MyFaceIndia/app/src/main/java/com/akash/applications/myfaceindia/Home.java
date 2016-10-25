package com.akash.applications.myfaceindia;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int PIC_CROP = 3;
    FragmentManager fm;
    ImageView userProfilePicture;
    private View navHeaderView;
    private Uri profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm=getSupportFragmentManager();

        getSupportActionBar().setTitle("Home");
        fm.beginTransaction()
                .replace(R.id.container,new MainPage())
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        userProfilePicture = (ImageView)navHeaderView.findViewById(R.id.userProfilePicture);
        registerForContextMenu(userProfilePicture);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                break;
            case R.id.Profile:
                fragment = new Profile();
                getSupportActionBar().setTitle("Edit Profile");
                break;
            case R.id.Timeline:
                fragment = new Timeline();
                getSupportActionBar().setTitle("Timeline");
                break;
            case R.id.CreateGroup:
                fragment = new CreateGroup();
                getSupportActionBar().setTitle("Create Group");
                break;
            case R.id.MyGroup:
                fragment = new MyGroup();
                getSupportActionBar().setTitle("My Group");
                break;
            case R.id.AllEvents:
                fragment = new AllEvents();
                getSupportActionBar().setTitle("All Events");
                break;
            case R.id.Music:
                fragment = new Music();
                getSupportActionBar().setTitle("Music");
                break;
            case R.id.Picture:
                fragment = new Picture();
                getSupportActionBar().setTitle("Picture");
                break;
            case R.id.Video:
                fragment = new Video();
                getSupportActionBar().setTitle("Video");
                break;
            case R.id.Shared:
                fragment = new Shared();
                getSupportActionBar().setTitle("Shared");
                break;
            case R.id.Location:
                fragment = new Location();
                getSupportActionBar().setTitle("Location");
                break;
            case R.id.Chat:
                fragment = new Chat();
                getSupportActionBar().setTitle("Chat");
                break;
            case R.id.Friends:
                fragment = new Friends();
                getSupportActionBar().setTitle("Friends");
                break;
            case R.id.Notification:
                fragment = new Notification();
                getSupportActionBar().setTitle("Notification");
                break;
            case R.id.Settings:
                fragment = new Settings();
                getSupportActionBar().setTitle("Settings");
                break;
            case R.id.Logout:
                startActivity(new Intent(getBaseContext(),UserCredential.class));
                finish();
                return true;
            case R.id.About:
                fragment = new About();
                getSupportActionBar().setTitle("About Us");
                break;

            case R.id.Footer:
                break;

        }

        fm.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//Change user profile picture by long pressing on navigation header view
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navheader_change_profileimage,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.takeImage:
                launchCamera();
               // Toast.makeText(getBaseContext(),"Image from camera",Toast.LENGTH_LONG).show();
                return true;
            case R.id.chooseFromGallary:
                Toast.makeText(getBaseContext(),"Image from gallery",Toast.LENGTH_LONG).show();
                launchGallery();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    private void launchCamera() {
        try{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String imgDirectory = Environment.getExternalStorageDirectory() + "/MyFaceIndia/Media/Images/Profile";
            File f = new File(imgDirectory);
            if(!f.exists())
                f.mkdirs();

            File imageFile = new File(imgDirectory+"/IMG_"+System.currentTimeMillis()+".jpg");
            profilePic = Uri.fromFile(imageFile);
            //Works only for TargetAPI <= 23
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

        if (resultCode == RESULT_OK && data!=null) {

            if(requestCode == PICK_FROM_CAMERA)
            {
                // performCrop(data.getData());
                performCrop(profilePic);
            }
            else if(requestCode == PIC_CROP){

                Bundle extras = data.getExtras();
                Bitmap finalPic = extras.getParcelable("data");
                userProfilePicture.setImageBitmap(finalPic);
            }
            else if(requestCode == PICK_FROM_GALLERY)
            {
                Bundle extras = data.getExtras();
                Bitmap finalBitmap = extras.getParcelable("data");
                userProfilePicture.setImageBitmap(finalBitmap);
                userProfilePicture.setBackgroundResource(R.drawable.make_round_corner);
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

    private void launchGallery() {
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
            Toast.makeText(getBaseContext(), "Unable to launch gallery", Toast.LENGTH_LONG).show();
        }

    }


}
