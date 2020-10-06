package com.example.suidhaga2.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


   //public  static  final String BASE_URL="http://clothinglaws.com/suiDhaga/";
    public  static  final String BASE_URL="http://103.255.232.8/Sui_Dhaga/";
    public static Retrofit retrofit;

    public static Retrofit getapiclient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }

}
