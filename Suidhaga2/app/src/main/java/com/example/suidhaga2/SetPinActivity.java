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

public class SetPinActivity extends AppCompatActivity {

    TextInputEditText userid,userpass;
    Button verify;

    ImageView user;
    String uid,upass;

    boolean check;
    login_status status;
    private com.example.suidhaga2.retrofit.api api;;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_pin_activity);
        status=new login_status(getApplicationContext());
        userid=(TextInputEditText)findViewById(R.id.setpin_username);
        userpass=(TextInputEditText)findViewById(R.id.setpin_password);
        verify=findViewById(R.id.setpin_login_btn);
        api= ApiClient.getapiclient().create(api.class);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                check=verify();

                if(!check)
                {

                    Toast.makeText(SetPinActivity.this,"Invalid ID or Password",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(SetPinActivity.this, "Verfied", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SetPinActivity.this,UpdatePinActivity.class));
                    status.writeid(uid);
                    status.writelogin_status(true);
                    status.writepassword(upass);
                    finish();



                }




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

    private void login(){
        //validations for editText
        uid=userid.getText().toString().trim();
        upass=userpass.getText().toString().trim();

        if(uid.isEmpty())
        {
            userid.setError("Enter UserID");
            userid.requestFocus();
            return;
        }
        if(upass.isEmpty())
        {
            userpass.setError("Enter Password");
            userpass.requestFocus();
            return;
        }
    }
    private boolean verify(){
        boolean statusverify;
        if((status.readid()).equals(uid)) {


            if ((status.readpassword()).equals(upass)) {
                statusverify=true;

            }
            else statusverify=false;
        }
        else statusverify=false;
        //here to verify user

        return statusverify;
    }




}
