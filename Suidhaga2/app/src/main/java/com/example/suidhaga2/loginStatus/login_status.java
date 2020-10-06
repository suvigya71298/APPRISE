package com.example.suidhaga2.loginStatus;


import android.content.Context;
import android.content.SharedPreferences;


import com.example.suidhaga2.R;

import static android.content.Context.MODE_PRIVATE;

public class login_status {

    private SharedPreferences sharedPreferences;
    private Context context;

    public login_status(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.login_preference),MODE_PRIVATE);
    }
    public void writelogin_status(boolean status)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference),status);
        editor.commit();

    }

    public boolean readlogin_status()
    {
        boolean status=false;
        status=sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference),false);

        return  status;
    }
    public void writeid(String id)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.id_preference),id);
        editor.commit();
    }
    public void writepassword(String password)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.password_prefernce),password);
        editor.commit();
    }

    public String readid()
    {
        String id;
        id=sharedPreferences.getString(context.getResources().getString(R.string.id_preference),"id");
        return id;
    }
    public String readpassword()
    {
        String password;
        password=sharedPreferences.getString(context.getResources().getString(R.string.password_prefernce),"password");
        return password;
    }

}
