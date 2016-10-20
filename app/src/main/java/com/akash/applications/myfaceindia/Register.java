package com.akash.applications.myfaceindia;

import com.akash.applications.myfaceindia.UserSharedPref.SharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    EditText Name, UserName,Email,Password,Phone;
    Button btn;
    String nme,uname,email,passwrd,phn;
    public Register() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Name = (EditText)getView().findViewById(R.id.etName);
        UserName = (EditText)getView().findViewById(R.id.etUserName);
        Email = (EditText)getView().findViewById(R.id.etEmail);
        Password = (EditText)getView().findViewById(R.id.etPassword);
        Phone = (EditText)getView().findViewById(R.id.etPhone);

        btn = (Button)getView().findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        nme = Name.getText().toString().trim();
        uname = UserName.getText().toString().trim();
        email = Email.getText().toString().trim();
        passwrd = Password.getText().toString().trim();
        phn = Phone.getText().toString().trim();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SharedPref.SERVER_URL+"register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        Log.i("##Rply"," "+response);
                        if(response.substring(0,7).equalsIgnoreCase("success")){
                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_LONG).show();
                            String a=null,b = null,c = null;
                            String serverReply = response.substring(7);
                            try {
                                JSONObject jsonObject = new JSONObject(serverReply);
                                JSONArray result = jsonObject.getJSONArray("result");
                                JSONObject data = result.getJSONObject(0);
                                a = data.getString("name");
                                b = data.getString("email");
                                c = data.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            new SharedPref().saveUserid(getContext(),c);
                            new SharedPref().saveDetail(getContext(),a,b);
                            startActivity(new Intent(getContext(),Home.class));
                            getActivity().finish();
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
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
                params.put("name",nme);
                params.put("username",uname);
                params.put("email",email);
                params.put("password",passwrd);
                params.put("phone",phn);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);


    }

}
