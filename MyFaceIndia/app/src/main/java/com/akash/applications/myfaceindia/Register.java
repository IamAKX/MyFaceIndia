package com.akash.applications.myfaceindia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.akash.applications.myfaceindia.Models.CaptchaResponse;
import com.akash.applications.myfaceindia.Models.RegisterResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    private static final String BASE_URL = "url-of-the-registration-api";
    private Unbinder knifeInst;
    @BindView(R.id.regUserName)
    private EditText userNameEditText;
    @BindView(R.id.regEmail)
    private EditText userEmailEditText;
    @BindView(R.id.regPassword)
    private EditText passwordEditText;
    @BindView(R.id.regCaptcha)
    private EditText captchaEditText;
    @BindView(R.id.reg_button)
    private Button registerUserButton;
    @BindView(R.id.captcha_imageview)
    private ImageView captchaImageView;
    private ApiInterface apiService;

    public Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        knifeInst = ButterKnife.bind(this, rootView);
        apiService = this.getInterfaceService();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        knifeInst.unbind();
    }

    private ApiInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    private void registerUser() {

        Call<RegisterResponse> mService = apiService.registerUser(
                userNameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                userEmailEditText.getText().toString(),
                captchaEditText.getText().toString() //May or may not be used depending upon the captcha mechanism.
        );

        mService.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                //We are logged in disable progress bars and goto Home.class
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Can't Register! Please retry", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private boolean validateInput() {

        if (userNameEditText.getText().toString().trim().isEmpty()) {
            userNameEditText.setError("Enter username or email");
            return false;
        } else if (passwordEditText.getText().toString().isEmpty()) {
            passwordEditText.setError("Enter password");
            return false;
        } else if (userEmailEditText.getText().toString().isEmpty()) {
            userEmailEditText.setError("Enter Email");
            return false;
        } else if (captchaEditText.getText().toString().equalsIgnoreCase("Captcha-string")) {
            captchaEditText.setError("Wrong Capthca!");
            return false;
        }
        return true;
    }

    private void setCaptcha() {
        Call<CaptchaResponse> mService = apiService.getCaptcha();
        mService.enqueue(new Callback<CaptchaResponse>() {
            @Override
            public void onResponse(Call<CaptchaResponse> call, Response<CaptchaResponse> response) {
                // Uri captchaImgUri = response.body()  //get the url for captcha
                /*Glide.with(getActivity())
                        .fromUri()
                        .load("uri")
                        .into(captchaImageView);*/
            }

            @Override
            public void onFailure(Call<CaptchaResponse> call, Throwable t) {
                //Make a toast.
            }
        });
    }
}
