package com.hummingtech.zetrixwebpractical.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.databinding.ActivityLoginBinding;
import com.hummingtech.zetrixwebpractical.dialog.LoadingDialog;
import com.hummingtech.zetrixwebpractical.model.LoginModel;
import com.hummingtech.zetrixwebpractical.model.LoginResponseModel;
import com.hummingtech.zetrixwebpractical.retrofit.ApiClient;
import com.hummingtech.zetrixwebpractical.retrofit.RetrofitCallbacks;
import com.hummingtech.zetrixwebpractical.utils.ToastUtils;
import com.hummingtech.zetrixwebpractical.utils.ValidationUtils;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        init();
        setListener();
    }

    private void init(){

        loadingDialog = new LoadingDialog(this);
    }

    private void setListener(){

        binding.btnLogin.setOnClickListener(view -> {
            checkValidation();
        });
    }

    private void checkValidation(){

        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if(email.isEmpty()){
            ToastUtils.showErrorToast("Please enter email address");
        }
        else if(!ValidationUtils.isValidEmail(email)){
            ToastUtils.showErrorToast("Please enter valid email address");
        }
        else if(password.isEmpty()){
            ToastUtils.showErrorToast("Please enter password");
        }
        else if(password.length()<8){
            ToastUtils.showErrorToast("Password length must be 8 character");
        }
        else if(!ValidationUtils.isValidPassword(password)){
            ToastUtils.showErrorToast("Please enter valid password");
        }
        else {
            LoginModel loginModel = new LoginModel(email, password);
            callLogin(loginModel);
        }
    }

    private void callLogin(LoginModel loginModel){

        loadingDialog.show();


        RetrofitCallbacks<LoginResponseModel> callbacks = new RetrofitCallbacks<LoginResponseModel>(this,loadingDialog) {
            @Override
            public void onSuccess(Response<LoginResponseModel> response) {

                ToastUtils.showSuccessToast("Login successful.");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {

                ToastUtils.showErrorToast(getString(R.string.something_went_wrong));
            }
        };

        ApiClient.getApiInterface().callLogin(loginModel).enqueue(callbacks);
    }
}