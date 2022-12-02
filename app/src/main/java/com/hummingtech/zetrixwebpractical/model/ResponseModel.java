package com.hummingtech.zetrixwebpractical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel<T> {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("data")
    @Expose
    private List<T> data = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
