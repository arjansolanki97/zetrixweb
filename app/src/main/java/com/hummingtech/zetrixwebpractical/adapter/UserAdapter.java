package com.hummingtech.zetrixwebpractical.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.databinding.RowCategoryBinding;
import com.hummingtech.zetrixwebpractical.databinding.RowGridUserBinding;
import com.hummingtech.zetrixwebpractical.databinding.RowListUserBinding;
import com.hummingtech.zetrixwebpractical.enums.ViewType;
import com.hummingtech.zetrixwebpractical.model.CategoryModel;
import com.hummingtech.zetrixwebpractical.model.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<UserModel> userList;
    private ViewType userViewType = ViewType.LIST;

    public UserAdapter(Activity activity, List<UserModel> userList, ViewType userViewType) {
        this.activity = activity;
        this.userList = userList;
        this.userViewType = userViewType;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(userViewType==ViewType.GRID) {
            RowGridUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_grid_user, parent, false);
            return new GridViewHolder(binding);
        }
        else {
            RowListUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_list_user, parent, false);
            return new ListViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        UserModel userModel = userList.get(position);

        if(userViewType==ViewType.GRID) {
            ((GridViewHolder) holder).bind(userModel);
        }
        else {
            ((ListViewHolder) holder).bind(userModel);
        }
    }

    /*public void setUserViewType(ViewType viewType){
        this.userViewType = viewType;
        notifyDataSetChanged();
    }*/


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        RowListUserBinding binding;

        public ListViewHolder(@NonNull RowListUserBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(UserModel userModel){

            binding.tvName.setText(userModel.getFirstName() + " " + userModel.getLastName());
            binding.tvEmail.setText(userModel.getEmail());

            Glide.with(activity)
                    .load(userModel.getAvatar())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .circleCrop()
                    .into(binding.ivThumb);
        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        RowGridUserBinding binding;

        public GridViewHolder(@NonNull RowGridUserBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(UserModel userModel){

            binding.tvName.setText(userModel.getFirstName() + " " + userModel.getLastName());
            binding.tvEmail.setText(userModel.getEmail());

            Glide.with(activity)
                    .load(userModel.getAvatar())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .circleCrop()
                    .into(binding.ivThumb);
        }
    }
}
