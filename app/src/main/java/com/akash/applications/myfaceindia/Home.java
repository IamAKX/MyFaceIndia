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
import android.widget.TextView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.UserSharedPref.SharedPref;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    Home homeObject;
    private View navHeaderView;
    private TextView tvEmail,tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm=getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        homeObject = this;

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

/*        navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        tvName = (TextView)navHeaderView.findViewById(R.id.myName);
        tvEmail = (TextView)navHeaderView.findViewById(R.id.myEmail);
        tvName.setText("Welcome "+new SharedPref().getName(getBaseContext()));
        tvEmail.setText(new SharedPref().getEmail(getBaseContext()));*/
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
        Fragment myFragment = null;
        switch(id)
        {
            case R.id.Timeline:
                myFragment = new Timeline();
                getSupportActionBar().setTitle("Timeline");
                break;
            case R.id.Home:
                myFragment = new HomeFragment();
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.Notification:
            case R.id.Chats:
            case R.id.Friends:
            case R.id.CreateGroup:
            case R.id.Broadcast:
            case R.id.Share:
            case R.id.About:
            case R.id.Footer:
                myFragment = new Friends();
                getSupportActionBar().setTitle("Under Development");
                break;
            /*case R.id.Logout:
                new SharedPref().deletePref(getBaseContext());
                Toast.makeText(getBaseContext(),"Successfully logged out!!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
                break;*/
            default:
                myFragment = new HomeFragment();
                getSupportActionBar().setTitle("Home");
                break;
        }

        fm.beginTransaction()
                .replace(R.id.container,myFragment)
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
