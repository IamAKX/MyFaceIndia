package com.akash.applications.myfaceindia;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import Connection.ConnectionDetector;
import HelperPackage.DateDifference;
import HelperPackage.PreferenceSaver;
import HelperPackage.SpinnerData;
import HelperPackage.UserConfig;
import ImageEncoderDecoder.ImageFormatChanger;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroup extends Fragment {

    private static final int PICK_FROM_GALLERY = 99;
    Spinner privacyS,postS;
    EditText name,title,desc;
    String _name,_title,_desc;

    int privacy=0,control=1;
    private Button btnCreateGroup;

    public CreateGroup() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        privacyS = (Spinner)getView().findViewById(R.id.GroupPrivacy);
        postS = (Spinner)getView().findViewById(R.id.GroupPost);
        name = (EditText)getView().findViewById(R.id.GroupName);
        title = (EditText)getView().findViewById(R.id.GroupTitle);
        desc = (EditText)getView().findViewById(R.id.GroupDescription);

        btnCreateGroup = (Button)getView().findViewById(R.id.btnCreateGroup);

        ArrayAdapter<String> privacyA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getGroupPrivacy());
        privacyA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacyS.setAdapter(privacyA);
        privacyS.setOnItemSelectedListener(new PrivacySpinnerClass());

        ArrayAdapter<String> postA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new SpinnerData().getPost());
        postA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postS.setAdapter(postA);
        postS.setOnItemSelectedListener(new PostSpinnerClass());

        final ConnectionDetector cd = new ConnectionDetector(getContext());
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _name=name.getText().toString().trim();
                _title = title.getText().toString().trim();
                _desc = desc.getText().toString().trim();

                if(_name.equals("")||_title.equals("")||_desc.equals(""))
                    Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                else {
                    if(cd.isConnectingToInternet())
                    {
                        String s = DateDifference.timeNow();
                        Intent intent = new Intent(getActivity(), ChooseFromGallery.class);
                        intent.putExtra("name",_name);
                        intent.putExtra("title",_title);
                        intent.putExtra("privacy", String.valueOf(privacy));
                        intent.putExtra("control",String.valueOf(control));
                        intent.putExtra("desc",_desc);
                        intent.putExtra("gdate",s.substring(0,s.indexOf(' ')));
                        getActivity().startActivity(intent);
                    }
                    else
                        cd.showSnackBar(getView(),"Check your Internet Connection.");

                }
            }
        });
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
            privacy = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class PostSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            control = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}
