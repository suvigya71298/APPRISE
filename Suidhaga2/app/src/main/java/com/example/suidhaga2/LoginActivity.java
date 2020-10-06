package com.example.suidhaga2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.suidhaga2.loginStatus.login_status;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.retrofit.login_model_data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText userID;
    private EditText password;
   // ProgressDialog progressDialog;
    private String id;
    private String pass;
    TextInputLayout user;
    Button login;
    private com.example.suidhaga2.retrofit.api api;
    com.example.suidhaga2.loginStatus.login_status login_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);
        login_status=new login_status(getApplicationContext());
        //TextEditTExt Layout findViewBy ID..........
        user=(TextInputLayout)findViewById(R.id.userlayout);
        userID=findViewById(R.id.username);
        password=findViewById(R.id.password2);
       // progressDialog=new ProgressDialog(LoginActivity.this);
       // progressDialog.setTitle("Logging in");
       // progressDialog.setMessage("Please Wait...");


        login=findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_details();
                performlogin(id,pass);





            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        View view=this.getCurrentFocus();
        if(view!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private  void login_details()
    {

        //validations for editText
        id=userID.getText().toString().trim();
        pass=password.getText().toString().trim();

        if(id.isEmpty())
        {
            userID.setError("Enter UserID");
            userID.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }


    }
    private void performlogin(final String id, final String pass)
    {
       // progressDialog.show();
        api= ApiClient.getapiclient().create(api.class);
        Call<login_model_data> call=api.performlogin(id,pass);
        call.enqueue(new Callback<login_model_data>() {
            @Override
            public void onResponse(Call<login_model_data> call , Response<login_model_data> response) {

                String result=response.body().getResponse();
                if(result.equals("ok"))
                {
                    //progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,PinLock.class));
                    login_status.writelogin_status(true);
                    login_status.writeid(id);
                    login_status.writepassword(pass);
                    finish();
                }
                if(result.equals("failed"))
                {
                    Toast.makeText(LoginActivity.this,"user does not exist",Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<login_model_data> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "No Response form the server", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
