package com.akash.applications.myfaceindia;


import android.content.Intent;
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

import HelperPackage.SpinnerData;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    EditText nameET,emailET,cityET,addressET,workET,educationET,blogET,googleET,facebookET,twitterET,bioET;
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

        nameET = (EditText)getView().findViewById(R.id.profileFullName);
        emailET = (EditText)getView().findViewById(R.id.profileEmail);
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
                    Toast.makeText(getContext(),"Profile is updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(),"All fields are required to update profile",Toast.LENGTH_LONG).show();
                return true;
            case R.id.Cancel:
                 startActivity(new Intent(getContext(),Home.class));
                 getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkAllFields() {
        Log.i("##check"," "+nameET.getText().toString().trim());
        if(nameET.getText().toString().trim().equals(""))
        {

            nameET.setError("Enter your name");
            return false;
        }
        else
        if(emailET.getText().toString().trim().equals(""))
        {
            emailET.setError("Enter your email");
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
}
