package com.example.suidhaga2.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api {


    @GET("log_in.php")
    Call<login_model_data> performlogin(@Query("id") String id, @Query("password") String password);

    @GET("getpin.php")
    Call<pinmodeldata> getpin(@Query("pin") String pin ,@Query("id") String id);

    @GET("pinupdate.php")
    Call<changepin> changepin(@Query("id") String id,@Query("pin") String pin);

    @GET("sewing_fetch.php")
    Call<Sewing_model_data> sewing_data(@Query("Job_no")String Job_no);

    @GET("getbuyer.php")
    Call<List<Buyer_model_data>> get_buyer();

    @GET("getjob.php")
    Call<List<Job_data>> get_job(@Query("buyer") String buyer);

    @GET("packing_fetch.php")
    Call<Report_model_data> packing_data(@Query("Job_no")String Job_no);

    @GET("cutting_fetch.php")
    Call<Report_model_data> cutting_data(@Query("Job_no")String Job_no);

    @GET("finishing_fetch.php")
    Call<Report_model_data> finishing_data(@Query("Job_no")String Job_no);

    @GET("getbuyer_frr.php")
    Call<List<Buyer_model_data>> get_buyer_frr(@Query("month") int month,@Query("year") int year);


    @GET("getjob_frr.php")
    Call<List<Job_data>> get_job_frr(@Query("buyer") String buyer,@Query("month") int month,@Query("year") int year);


    @GET("frr_fetch.php")
    Call<Frr_data_model_data> frr_data(@Query("Job_no")String Job_no,@Query("month") int month,@Query("year") int year);
    @GET("frr.php")
    Call<Download_report>get_download_report(@Query("month")int month,@Query("year")int year);
    @GET("dpr.php")
    Call<Download_report_dpr>get_dpr_report();



}
