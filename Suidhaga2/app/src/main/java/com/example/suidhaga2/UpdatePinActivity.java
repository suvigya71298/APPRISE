package com.example.suidhaga2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.suidhaga2.loginStatus.login_status;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.retrofit.changepin;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdatePinActivity extends AppCompatActivity {
    Button confirm;
    ImageView user;
    Pinview pin1,pin2;
    String p1,p2;
    private com.example.suidhaga2.loginStatus.login_status login_status;
    private com.example.suidhaga2.retrofit.api api;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_update_pin);
        pin1=findViewById(R.id.newpinview);
        pin2=findViewById(R.id.confirm_pinview);
        confirm=findViewById(R.id.confirm);
        login_status=new login_status(getApplicationContext());
        api= ApiClient.getapiclient().create(api.class);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1=pin1.getValue();
                p2=pin2.getValue();
                confirmpin();
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

    private void confirmpin()
    {
        if(p1.isEmpty()){
            Toast.makeText(this, "Set new Pin is empty", Toast.LENGTH_SHORT).show();
        }
        else if(p2.isEmpty()){
            Toast.makeText(this, "Confirm Pin is empty", Toast.LENGTH_SHORT).show();
        }


        else if(p1.equals(p2)==true)
        {
            Call<changepin> call=api.changepin(login_status.readid(),p1);
            call.enqueue(new Callback<changepin>() {
                @Override
                public void onResponse(Call<changepin> call, Response<changepin> response) {

                    String result;
                    result=response.body().getResponsechange();
                    if(result.equals("true"))
                    {
                        Toast.makeText(UpdatePinActivity.this, "Pin Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdatePinActivity.this,PinLock.class));
                        finish();
                    }
                    else

                        Toast.makeText(UpdatePinActivity.this, response.body().getResponsechange(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<changepin> call, Throwable t) {
                    Toast.makeText(UpdatePinActivity.this, "No Response from the server", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            Toast.makeText(UpdatePinActivity.this,"pin does not match",Toast.LENGTH_SHORT).show();


        }

    }
}
