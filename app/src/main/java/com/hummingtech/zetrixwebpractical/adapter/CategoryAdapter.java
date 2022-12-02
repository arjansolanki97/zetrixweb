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
import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.databinding.RowCategoryBinding;
import com.hummingtech.zetrixwebpractical.enums.ViewType;
import com.hummingtech.zetrixwebpractical.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Activity activity;
    private List<CategoryModel> categoryList;
    private ViewType viewType = ViewType.LIST;

    public CategoryAdapter(Activity activity, List<CategoryModel> categoryList) {
        this.activity = activity;
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_category,parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel categoryModel = categoryList.get(position);
        holder.bind(categoryModel);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RowCategoryBinding binding;

        public ViewHolder(@NonNull RowCategoryBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(CategoryModel categoryModel){

            binding.tvName.setText(categoryModel.getName());

            int color = Color.parseColor(categoryModel.getColor());

            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(color);

            Glide.with(activity)
                    .load(gradientDrawable)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .circleCrop()
                    .into(binding.ivThumb);
        }
    }
}
