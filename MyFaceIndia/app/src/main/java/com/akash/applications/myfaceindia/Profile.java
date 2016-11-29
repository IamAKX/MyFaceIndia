package com.akash.applications.myfaceindia;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import HelperPackage.SpinnerData;
import HelperPackage.UserConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    EditText firstnameET,lastnameET,cityET,addressET,workET,educationET,blogET,googleET,facebookET,twitterET,bioET;
    Spinner dayS,monthS,yearS,genderS,intrestS,countryS;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firstnameET = (EditText)getView().findViewById(R.id.profileFirstName);
        lastnameET = (EditText)getView().findViewById(R.id.profileLastName);
        cityET = (EditText)getView().findViewById(R.id.profileCity);
        addressET = (EditText)getView().findViewById(R.id.profileAddress);
        workET = (EditText)getView().findViewById(R.id.profileWork);
        educationET = (EditText)getView().findViewById(R.id.profileEducation);
        blogET = (EditText)getView().findViewById(R.id.profileWebsite);
        googleET = (EditText)getView().findViewById(R.id.profileGoogle);
        facebookET = (EditText)getView().findViewById(R.id.profileFacebook);
        twitterET = (EditText)getView().findViewById(R.id.profileTwitter);
        bioET = (EditText)getView().findViewById(R.id.profileBio);

        dayS = (Spinner)getView().findViewById(R.id.dobDay);
        monthS = (Spinner)getView().findViewById(R.id.dobMonth);
        yearS = (Spinner)getView().findViewById(R.id.dobYear);
        genderS = (Spinner)getView().findViewById(R.id.Gender);
        intrestS = (Spinner)getView().findViewById(R.id.Intrest);
        countryS = (Spinner)getView().findViewById(R.id.Country);

        ArrayAdapter<String> dayA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getDay());
        dayA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayS.setAdapter(dayA);
        dayS.setOnItemSelectedListener(new DaySpinnerClass());

        ArrayAdapter<String> monthA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getMonth());
        monthA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthS.setAdapter(monthA);
        monthS.setOnItemSelectedListener(new MonthSpinnerClass());

        ArrayAdapter<String> yearA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getYear());
        yearA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearS.setAdapter(yearA);
        yearS.setOnItemSelectedListener(new YearSpinnerClass());

        ArrayAdapter<String> genderA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getGender());
        genderA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderS.setAdapter(genderA);
        genderS.setOnItemSelectedListener(new GenderSpinnerClass());

        ArrayAdapter<String> intrestA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getIntrest());
        intrestA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intrestS.setAdapter(intrestA);
        intrestS.setOnItemSelectedListener(new IntrestSpinnerClass());

        ArrayAdapter<String> countryA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getCountry());
        countryA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryS.setAdapter(countryA);
        countryS.setOnItemSelectedListener(new CountrySpinnerClass());


        initializeData();
    }

    private void initializeData() {
        if(PreferenceSaver.getSaveState(getContext()))
        {
            firstnameET.setText(PreferenceSaver.getFname(getContext()));
            lastnameET.setText(PreferenceSaver.getLname(getContext()));
            dayS.setSelection(PreferenceSaver.getDay(getContext()));
            monthS.setSelection(PreferenceSaver.getMonth(getContext()));
            yearS.setSelection(PreferenceSaver.getYear(getContext()));
            genderS.setSelection(PreferenceSaver.getGender(getContext()));
            intrestS.setSelection(PreferenceSaver.getIntrest(getContext()));
            countryS.setSelection(PreferenceSaver.getCountry(getContext()));
            cityET.setText(PreferenceSaver.getCity(getContext()));
            addressET.setText(PreferenceSaver.getAddress(getContext()));

            workET.setText(PreferenceSaver.getWork(getContext()));
            educationET.setText(PreferenceSaver.getSchool(getContext()));
            blogET.setText(PreferenceSaver.getWeb(getContext()));
            googleET.setText(PreferenceSaver.getGoogle(getContext()));
            facebookET.setText(PreferenceSaver.getFacebook(getContext()));
            twitterET.setText(PreferenceSaver.getTwitter(getContext()));
            bioET.setText(PreferenceSaver.getBio(getContext()));

        }
    }

    private class DaySpinnerClass implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class MonthSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class YearSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class GenderSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class IntrestSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class CountrySpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.save_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch(id)
        {
            case R.id.Save:
                if(checkAllFields())
                {
                    new ContactServer().execute();

                }
                
                return true;
            case R.id.Cancel:
                 startActivity(new Intent(getContext(),Home.class));
                 getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkAllFields() {
        Log.i("##check"," "+firstnameET.getText().toString().trim());
        if(firstnameET.getText().toString().trim().equals(""))
        {

            firstnameET.setError("Enter your first name");
            return false;
        }
        else
        if(lastnameET.getText().toString().trim().equals(""))
        {
            lastnameET.setError("Enter your last name");
            return false;
        }
        else
        if(cityET.getText().toString().trim().equals(""))
        {
            cityET.setError("Enter your city name");
            return false;
        }
        else
        if(addressET.getText().toString().trim().equals(""))
        {
            addressET.setError("Enter your address");
            return false;
        }


        if(dayS.getSelectedItem().toString().trim().equalsIgnoreCase("Day")||monthS.getSelectedItem().toString().trim().equalsIgnoreCase("Month")||yearS.getSelectedItem().toString().trim().equalsIgnoreCase("Year"))
        {
            Toast.makeText(getContext(),"Select a valid date",Toast.LENGTH_LONG).show();
            return false;
        }
        else
            if(genderS.getSelectedItem().toString().trim().equalsIgnoreCase("No Gender"))
            {
                Toast.makeText(getContext(),"Select your gender",Toast.LENGTH_LONG).show();
                return false;
            }

        return true;
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            updateProfile();
            return null;
        }

    }

    private void updateProfile() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"editprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().trim().equalsIgnoreCase("success"))
                        {
                            PreferenceSaver.saveProfile(getContext(),true,firstnameET.getText().toString().trim(),lastnameET.getText().toString().trim(),dayS.getSelectedItemPosition(),monthS.getSelectedItemPosition(),yearS.getSelectedItemPosition(),genderS.getSelectedItemPosition(),intrestS.getSelectedItemPosition(),countryS.getSelectedItemPosition(),cityET.getText().toString().trim(),addressET.getText().toString().trim());
                            PreferenceSaver.saveProfileOtherDetails(getContext(),workET.getText().toString().trim(),educationET.getText().toString().trim(),blogET.getText().toString().trim(),googleET.getText().toString().trim(),facebookET.getText().toString().trim(),twitterET.getText().toString().trim(),bioET.getText().toString().trim());
                            Toast.makeText(getContext(),"Profile is updated",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getContext(),"Failed to update profile",Toast.LENGTH_LONG).show();

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

                params.put("uid", PreferenceSaver.getUserID(getContext()));

                params.put("fname",firstnameET.getText().toString().trim());
                params.put("lname",lastnameET.getText().toString().trim());
                params.put("date",yearS.getSelectedItem().toString().trim()+"-"+ DateDifference.monthNumber(monthS.getSelectedItem().toString().trim())+"-"+dayS.getSelectedItem().toString().trim());
                params.put("gender", String.valueOf(UserConfig.getGenderNumber(genderS.getSelectedItem().toString().trim())));
                params.put("intrest", String.valueOf(UserConfig.getIntrestNumber(intrestS.getSelectedItem().toString().trim())));
                params.put("country",countryS.getSelectedItem().toString().trim());
                params.put("city",cityET.getText().toString().trim());
                params.put("address",addressET.getText().toString().trim());
                params.put("workplace",workET.getText().toString().trim());
                params.put("education",educationET.getText().toString().trim());
                params.put("website",blogET.getText().toString().trim());
                params.put("glink",googleET.getText().toString().trim());
                params.put("fblink",facebookET.getText().toString().trim());
                params.put("tlink",twitterET.getText().toString().trim());
                params.put("bio",bioET.getText().toString().trim());
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
