package com.akash.applications.myfaceindia;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.applications.myfaceindia.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    //TODO Repalce with actual api url.
    private static final String BASE_URL = "login-url-of-api";
    Button login;
    private EditText userNameET, passwordET;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userNameET = (EditText)getView().findViewById(R.id.loginUserName);
        passwordET = (EditText)getView().findViewById(R.id.loginPassword);
        login = (Button)getView().findViewById(R.id.btnLogin);
        if (!isNetworkAvailable()) {
            //TODO change button behaviour.
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    startActivity(new Intent(getContext(),Home.class));
                    getActivity().finish();
                }
            }
        });
    }

    private boolean validateInput() {

        if(userNameET.getText().toString().trim().equals(""))
        {
            userNameET.setError("Enter username or email");
            return false;
        }
        else if(passwordET.getText().toString().trim().equals(""))
        {
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

    private ApiInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    private void loginUser() {
        ApiInterface apiService = this.getInterfaceService();
        Call<LoginResponse> mService = apiService.authUser(
                userNameET.getText().toString(),
                passwordET.getText().toString()
        );

        mService.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //We are logged in disable progress bars and goto Home.class
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Can't login! Please retry", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
