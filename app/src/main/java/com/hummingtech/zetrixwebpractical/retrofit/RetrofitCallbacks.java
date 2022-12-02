package com.hummingtech.zetrixwebpractical.retrofit;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.dialog.LoadingDialog;
import com.hummingtech.zetrixwebpractical.model.ResponseModel;
import com.hummingtech.zetrixwebpractical.utils.AppConstants;
import com.hummingtech.zetrixwebpractical.utils.ToastUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallbacks<T> implements Callback<T> {

    private final Activity activity;
    private final LoadingDialog loadingDialog;
    private String TAG = "RetrofitCallbacks";
    private boolean isLoadingHide = true;

    protected RetrofitCallbacks(Activity activity, LoadingDialog loadingDialog, boolean isLoadingHide) {
        this.activity = activity;
        this.loadingDialog = loadingDialog;
        this.isLoadingHide = isLoadingHide;
    }

    protected RetrofitCallbacks(Activity activity, LoadingDialog loadingDialog) {
        this.activity = activity;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

        if (isLoadingHide)
            loadingDialog.dismiss();

        if (response.code() == AppConstants.RESPONSE_CODE_VALIDATION_ERROR) {
            Gson gson = new GsonBuilder().create();
            ResponseModel responseModel = new ResponseModel();
            try {
                responseModel = gson.fromJson(response.errorBody().string(), ResponseModel.class);

                if(responseModel.getError()!=null && !responseModel.getError().isEmpty())
                    ToastUtils.showErrorToast(responseModel.getError());
                else
                    ToastUtils.showErrorToast(activity.getString(R.string.something_went_wrong));

            } catch (IOException e) { // handle failure at error parse }
                ToastUtils.showErrorToast(activity.getString(R.string.something_went_wrong));
            }
        } else if (response.code() == AppConstants.RESPONSE_CODE_SERVER_ERROR) {
            ToastUtils.showErrorToast(activity.getString(R.string.internal_server_error));
        } else if (response.code() == AppConstants.RESPONSE_CODE_URL_NOT_FOUND) {
            ToastUtils.showErrorToast(activity.getString(R.string.url_not_found));
        }
        /*else if (response.code() == ResponseModel.RESPONSE_CODE_UNAUTHORIZED) {

            new FirebaseAuthentication(activity).logout();
        }*/
        else if (response.code() >= 200 && response.code() <= 300) {
            onSuccess(response);
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFail(call, t);
        loadingDialog.dismiss();

        try {
            if (t.getMessage().contains("Unable to resolve host")) {
                if (activity != null) {
                    ToastUtils.showErrorToast(activity.getString(R.string.please_check_your_internet_connection));
                }
            } else {
                if (activity != null && !t.getMessage().equals("Socket closed"))
                    ToastUtils.showErrorToast("" + t.getMessage());

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract void onSuccess(Response<T> response);

    public abstract void onFail(@NonNull Call<T> call, @NonNull Throwable t);

}
