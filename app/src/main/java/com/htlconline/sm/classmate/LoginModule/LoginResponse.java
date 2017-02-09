package com.htlconline.sm.classmate.LoginModule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shikhar Garg on 23-12-2016.
 */
public class LoginResponse {
    @SerializedName("success")
    private String success;
    @SerializedName("error")
    private String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
