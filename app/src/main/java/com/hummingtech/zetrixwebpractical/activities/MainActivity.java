package com.hummingtech.zetrixwebpractical.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.adapter.CategoryAdapter;
import com.hummingtech.zetrixwebpractical.adapter.UserAdapter;
import com.hummingtech.zetrixwebpractical.databinding.ActivityMainBinding;
import com.hummingtech.zetrixwebpractical.dialog.LoadingDialog;
import com.hummingtech.zetrixwebpractical.enums.ViewType;
import com.hummingtech.zetrixwebpractical.model.CategoryModel;
import com.hummingtech.zetrixwebpractical.model.ResponseModel;
import com.hummingtech.zetrixwebpractical.model.UserModel;
import com.hummingtech.zetrixwebpractical.retrofit.ApiClient;
import com.hummingtech.zetrixwebpractical.retrofit.RetrofitCallbacks;
import com.hummingtech.zetrixwebpractical.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoadingDialog loadingDialog;

    private CategoryAdapter categoryAdapter;
    private UserAdapter userAdapter;

    private List<CategoryModel> categoryList = new ArrayList<>();
    private List<UserModel> userList = new ArrayList<>();

    private ViewType viewType = ViewType.LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
        setListener();
        setAdapter();
        getCategoryList();
        getUserList();
    }

    private void init(){
        loadingDialog = new LoadingDialog(this);
    }

    private void setListener(){

        binding.ivViewType.setOnClickListener(view -> {

            RecyclerView.LayoutManager layoutManager;

            if(viewType==ViewType.LIST){
                binding.ivViewType.setImageResource(R.drawable.ic_list);
                viewType = ViewType.GRID;
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            }
            else {
                binding.ivViewType.setImageResource(R.drawable.ic_grid);
                viewType = ViewType.LIST;
                layoutManager = new LinearLayoutManager(this);
            }

            binding.rvUser.setLayoutManager(layoutManager);
            userAdapter = new UserAdapter(this, userList, viewType);
            binding.rvUser.setAdapter(userAdapter);

        });
    }

    private void setAdapter(){

        categoryAdapter = new CategoryAdapter(this, categoryList);
        binding.rvCategory.setAdapter(categoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvUser.setLayoutManager(linearLayoutManager);

        userAdapter = new UserAdapter(this, userList, ViewType.LIST);
        binding.rvUser.setAdapter(userAdapter);
    }

    private void getCategoryList(){

        loadingDialog.show();

        RetrofitCallbacks<ResponseModel<CategoryModel>> callbacks = new RetrofitCallbacks<ResponseModel<CategoryModel>>(this, loadingDialog) {
            @Override
            public void onSuccess(Response<ResponseModel<CategoryModel>> response) {

                ToastUtils.showSuccessToast("Color list successful.");

                List<CategoryModel> list = response.body().getData();

                if(list!=null && !list.isEmpty()){

                    categoryList.clear();
                    categoryList.addAll(list);

                    for(CategoryModel colorModel : categoryList){
                        Log.e("TAG", "onSuccess: "+colorModel.getColor() + " : "+ colorModel.getName() );
                    }

                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(@NonNull Call<ResponseModel<CategoryModel>> call, @NonNull Throwable t) {
                ToastUtils.showErrorToast(getString(R.string.something_went_wrong));

            }
        };

        ApiClient.getApiInterface().callCategory(20).enqueue(callbacks);
    }

    private void getUserList(){

        loadingDialog.show();

        RetrofitCallbacks<ResponseModel<UserModel>> callbacks = new RetrofitCallbacks<ResponseModel<UserModel>>(this, loadingDialog) {
            @Override
            public void onSuccess(Response<ResponseModel<UserModel>> response) {

                ToastUtils.showSuccessToast("Color list successful.");

                List<UserModel> list = response.body().getData();

                if(list!=null && !list.isEmpty()){

                    userList.clear();
                    userList.addAll(list);

                    binding.tvUserCount.setText(userList.size() + " user found");

                    for(UserModel userModel : userList){
                        Log.e("TAG", "onSuccess: "+userModel.getEmail() );
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(@NonNull Call<ResponseModel<UserModel>> call, @NonNull Throwable t) {
                ToastUtils.showErrorToast(getString(R.string.something_went_wrong));

            }
        };

        ApiClient.getApiInterface().callUser(20).enqueue(callbacks);
    }

}