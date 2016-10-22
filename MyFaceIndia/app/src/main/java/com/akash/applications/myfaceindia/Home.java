package com.akash.applications.myfaceindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
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
}
