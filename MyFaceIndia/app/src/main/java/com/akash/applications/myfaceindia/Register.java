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
public class Register extends Fragment{

    EditText uName,uPassword,uEmail;
    String _uName,_uPassword,_uEmail;
    Button registerButton;
    View _view;
    public Register() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _view = getView();
        uName = (EditText)_view.findViewById(R.id.regUserName);
        uPassword = (EditText)_view.findViewById(R.id.regPassword);
        uEmail = (EditText)_view.findViewById(R.id.regEmail);
        registerButton = (Button)_view.findViewById(R.id.btnRegister);

        final ConnectionDetector cd = new ConnectionDetector(getContext());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    _uName = uName.getText().toString().trim();
                    _uPassword = uPassword.getText().toString().trim();
                    _uEmail = uEmail.getText().toString().trim();

                    registerButton.setText("Validating...");
                    validateInputs();
                }
                else
                    cd.showSnackBar(_view,"Check your Internet Connection.");

            }
        });
    }

    private void validateInputs() {
        if(uName.getText().toString().equals(""))
        {
            uName.setError("Enter valid username");
            return;
        }
        else
        if(uEmail.getText().toString().equals(""))
        {
            uEmail.setError("Enter valid email");
            return;
        }

        if(checkPassword())
        {
            new ContactServer().execute();
        }

    }




    private boolean checkPassword() {
        if(_uPassword.length()<8 || _uPassword.length()>16) {
            uPassword.setText("");
            uPassword.setError("Password should be of 8-16 character");
            registerButton.setText("REGISTER");
            return false;
        }
        return true;
    }




    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            checkEmailUsername();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(flag==0)
                sendLoginRequest();
        }
    }

    private void sendLoginRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",_uName+"  "+_uEmail);
                        Log.i("Checking",response+" ");
                        if(response.toString().substring(0,7).trim().equalsIgnoreCase("success"))
                        {

                           // Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                            uName.setText("");
                            uPassword.setText("");
                            uEmail.setText("");
                            registerButton.setText("DONE");

                            parseJSON(response.toString().substring(7).trim());
                        }
                        else
                        {
                            uName.setText("");
                            uPassword.setText("");
                            uEmail.setText("");
                            registerButton.setText("REGISTER");
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

                params.put("username",_uName);
                params.put("email",_uEmail);
                params.put("password",_uPassword);
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
        try {
            JSONObject jsonObject = new JSONObject(reply);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject data = result.getJSONObject(0);
            a = data.getString("username");
            b = data.getString("email");
            d = data.getString("img");
            c = data.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        PreferenceSaver.saveDetail(getContext(),a,b,d,c,true);

        startActivity(new Intent(getContext(),Home.class));
        getActivity().finish();
    }

    int flag;
    private void checkEmailUsername() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"checkUserEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().trim().equals("1")){

                            uEmail.setError("This email is already registered");
                            uName.setError("Use different user name");
                            registerButton.setText("REGISTER");
                            flag=1;
                        }else{
                            flag=0;

                        }
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

                params.put("email",_uEmail);
                params.put("username",_uName);
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
