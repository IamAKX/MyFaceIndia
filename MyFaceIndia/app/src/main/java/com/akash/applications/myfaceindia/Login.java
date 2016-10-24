package com.akash.applications.myfaceindia;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


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

}
