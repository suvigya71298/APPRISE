package com.example.suidhaga2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.loginStatus.login_status;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.retrofit.pinmodeldata;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinLock extends AppCompatActivity {
    Pinview pinview;

    String pin,id;
    TextView changepin;
    private com.example.suidhaga2.retrofit.api api;
    private com.example.suidhaga2.loginStatus.login_status login_status;
    String response1;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pin_lock);
        progressDialog=new ProgressDialog(PinLock.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        pinview = (Pinview) findViewById(R.id.pinview);
        api= ApiClient.getapiclient().create(api.class);
        changepin=(TextView)findViewById(R.id.changepin);
        login_status=new login_status(getApplicationContext());
        verifypin();
        



        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinLock.this,SetPinActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        View view=this.getCurrentFocus();
        if(view!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);}
    }

    private void verifypin(){

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(final Pinview pinview, boolean b) {
                pin=pinview.getValue();
                id=login_status.readid();





                Call<pinmodeldata> call=api.getpin(pin,id);
                progressDialog.show();
                call.enqueue(new Callback<pinmodeldata>() {
                    @Override
                    public void onResponse(Call<pinmodeldata> call, Response<pinmodeldata> response) {
                        response1=response.body().getResponse();
                        if (pin.isEmpty()){
                            Toast.makeText(PinLock.this, "Please Enter the Pin.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            if (response1.equals("true")) {

                                startActivity(new Intent(PinLock.this, DashBoard.class));

                                finish();
                            } else
                                Toast.makeText(PinLock.this, "Invalid Pin !!!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<pinmodeldata> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PinLock.this, "No Response from the server", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

}