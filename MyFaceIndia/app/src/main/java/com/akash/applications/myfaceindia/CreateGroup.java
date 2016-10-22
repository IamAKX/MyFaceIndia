package com.akash.applications.myfaceindia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import HelperPackage.SpinnerData;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroup extends Fragment {

    Spinner privacyS,postS;

    public CreateGroup() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        privacyS = (Spinner)getView().findViewById(R.id.GroupPrivacy);
        postS = (Spinner)getView().findViewById(R.id.GroupPost);

        ArrayAdapter<String> privacyA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getGroupPrivacy());
        privacyA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacyS.setAdapter(privacyA);
        privacyS.setOnItemSelectedListener(new PrivacySpinnerClass());

        ArrayAdapter<String> postA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getPost());
        postA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postS.setAdapter(postA);
        postS.setOnItemSelectedListener(new PostSpinnerClass());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    private class PrivacySpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class PostSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
