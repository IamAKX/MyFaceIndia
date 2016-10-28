package com.akash.applications.myfaceindia;


import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

import Connection.ConnectionDetector;
import HelperPackage.UserConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassword extends Fragment {


    public ForgetPassword() {
        // Required empty public constructor
    }

    EditText username;
    Button buttonForget;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        username = (EditText)getView().findViewById(R.id.forgetpasswordUserName);
        buttonForget = (Button)getView().findViewById(R.id.btnForgetPassword);
        final ConnectionDetector cd = new ConnectionDetector(getContext());
        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    if(username.getText().toString().equals(""))
                        username.setError("Enter username or email you want to recover");
                    else
                    {
                        buttonForget.setText("Verifying");
                        new ContactServer().execute();
                    }
                }
                else
                    cd.showSnackBar(getView(),"Check your Internet Connection.");
            }
        });
    }

    private class ContactServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            sendRecoveryRequest();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);

            switch (flag)
            {
                case 1:
                    progressDialog.setMessage("Your password is sent to your email inbox. Do you want to check it now");

                    progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            username.setText("");
                            progressDialog.dismiss();
                        }
                    });
                    progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            username.setText("");
                            progressDialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                            getActivity().startActivity(intent);
                        }
                    });
                    progressDialog.show();
                    buttonForget.setText("RECOVER");
                    break;
                case 2:
                    progressDialog.setMessage("No user registered with this email.");

                    progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            username.setText("");
                            progressDialog.dismiss();
                        }
                    });
                    progressDialog.show();
                    buttonForget.setText("RECOVER");
                    break;
                case 3:

                    progressDialog.setMessage("We are face server error. Try again after some time.");

                    progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            username.setText("");
                            progressDialog.dismiss();
                        }
                    });
                    progressDialog.show();
                    buttonForget.setText("RECOVER");
                    break;

            }
        }
    }

    int flag;
    private void sendRecoveryRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserConfig.SERVER_URL+"forgetpassword.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.i("Checking",response+" ");
                        if(response.toString().substring(0,7).trim().equals("success"))
                            flag=1;
                        else
                            if(response.toString().substring(0,7).trim().equals("failure"))
                                flag=2;
                            else
                                flag=3;

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

                params.put("email",username.getText().toString().trim());

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
