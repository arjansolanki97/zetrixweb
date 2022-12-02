package com.hummingtech.zetrixwebpractical.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;

import com.hummingtech.zetrixwebpractical.R;
import com.hummingtech.zetrixwebpractical.databinding.DialogLoaderBinding;


public class LoadingDialog {
    Context context;
    private AppCompatDialog dialog;
    DialogLoaderBinding binding;

    public LoadingDialog(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        dialog = new AppCompatDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loader,
                null, false);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public LoadingDialog show() {
        if (!dialog.isShowing())
            dialog.show();
        return this;
    }

    public LoadingDialog dismiss() {
        try {
            if (dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

}
