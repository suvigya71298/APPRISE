package com.example.suidhaga2.retrofit;

import com.google.gson.annotations.SerializedName;

public class login_model_data {


    @SerializedName("response")
    private String Response;
    @SerializedName("id")
    private String Id;

    public login_model_data(String response, String id) {
        Response = response;
        Id = id;
    }

    public String getResponse() {
        return Response;
    }

    public String getId() {
        return Id;
    }
}
