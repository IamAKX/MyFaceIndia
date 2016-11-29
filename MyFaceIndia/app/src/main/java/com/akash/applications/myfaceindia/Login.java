package com.akash.applications.myfaceindia;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import Connection.ConnectionDetector;
import HelperPackage.PreferenceSaver;
import HelperPackage.UserConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    Button login;
    public Login() {
        // Required empty public constructor
    }

    EditText userNameET,passwordET;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userNameET = (EditText)getView().findViewById(R.id.loginUserName);
        passwordET = (EditText)getView().findViewById(R.id.loginPassword);
        login = (Button)getView().findViewById(R.id.btnLogin);

        final ConnectionDetector cd = new ConnectionDetector(getContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(cd.isConnectingToInternet()) {
                if (validateInput()) {
                    new ContactServer().execute();
                }
            }
            else
                cd.showSnackBar(getView(),"Check your Internet Connection.");
            }
        });
    }

    private boolean validateInput() {
        login.setText("VALIDATING...");
        if(userNameET.getText().toString().trim().equals(""))
        {
            login.setText("LOGIN");
            userNameET.setError("Enter username or email");
            return false;
        }
        else if(passwordET.getText().toString().trim().equals(""))
        {
            login.setText("LOGIN");
            passwordET.setError("Enter password");
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            sendLoginRequest();
            return null;
        }

    }

    private void sendLoginRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().substring(0,7).trim().equalsIgnoreCase("success"))
                        {

                            userNameET.setText("");
                            passwordET.setText("");
                            login.setText("LOGIN");
                            parseJSON(response.toString().substring(7).trim());
                        }
                        else if(response.toString().substring(0,7).trim().equalsIgnoreCase("failure"))
                        {
                            userNameET.setText("");
                            passwordET.setText("");
                            userNameET.setError("Invaild username or password");
                            login.setText("LOGIN");
                        }
                        else
                        {
                            userNameET.setText("");
                            passwordET.setText("");
                            Toast.makeText(getContext(),"Sorry we are facing server problem, try againg after sometime.",Toast.LENGTH_LONG).show();
                            login.setText("LOGIN");
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

                params.put("email",userNameET.getText().toString().trim());
                params.put("password",passwordET.getText().toString().trim());
                return params;
            }
        };

        //Adding the string request to the queue
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();

        requestQueue.add(stringRequest);

    }

    private void parseJSON(String reply) {

        String a=null,b = null,c = null,d=null;
        String e=null,f=null,g = null,h = null,i = null,j=null,k=null,l=null,m=null,n=null,o=null,p=null,q=null,r=null,s=null;
        try {
            JSONObject jsonObject = new JSONObject(reply);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject data = result.getJSONObject(0);
            a = data.getString("username");
            b = data.getString("email");
            d = data.getString("img");
            c = data.getString("id");

            e = data.getString("fname");
            f = data.getString("lname");
            g = data.getString("dob");
            h = data.getString("gender");
            i = data.getString("interests");
            j = data.getString("country");
            k = data.getString("city");
            l = data.getString("address");
            m = data.getString("work");
            n = data.getString("school");
            o = data.getString("website");
            p = data.getString("google");
            q = data.getString("facebook");
            r = data.getString("twitter");
            s = data.getString("bio");


        } catch (JSONException err) {
            err.printStackTrace();
        }

        int year = Integer.parseInt(g.substring(0,g.indexOf('-')))-1985+1;
        int month = Integer.parseInt(g.substring(1+g.indexOf('-'),g.lastIndexOf('-')));
        int day = Integer.parseInt(g.substring(1+g.lastIndexOf('-')));

        Log.i("CheckingPref123",day+" "+month+" "+year+" "+Integer.parseInt(h)+" "+Integer.parseInt(i)+" "+UserConfig.countryPosition(j));
        PreferenceSaver.saveDetail(getContext(),a,b,d,c,true);
        PreferenceSaver.saveProfile(getContext(),true,e,f,day,month,year,Integer.parseInt(h),Integer.parseInt(i),UserConfig.countryPosition(j),k,l);
        PreferenceSaver.saveProfileOtherDetails(getContext(),m,n,o,p,q,r,s);
        startActivity(new Intent(getContext(),Home.class));
        getActivity().finish();

    }
}
