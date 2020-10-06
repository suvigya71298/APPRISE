package com.example.suidhaga2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.suidhaga2.cutting.CuttingBuyerName;
import com.example.suidhaga2.dailyProductionReport.DPR_BuyerName;
import com.example.suidhaga2.finishing.FinishingBuyerName;
import com.example.suidhaga2.frrReport.FRRBuyerName;
import com.example.suidhaga2.loginStatus.login_status;
import com.example.suidhaga2.packing.PackingBuyerName;

import com.example.suidhaga2.sewing.SewingBuyerName;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoard extends AppCompatActivity {
    ImageView Signout;
    login_status status;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    private com.example.suidhaga2.retrofit.api api;
    String response1;
    String url="http://103.255.232.8/Sui_Dhaga/APPRISE VERSIONS/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api= ApiClient.getapiclient().create(api.class);





        setContentView(R.layout.dashboard_activity);
        //Signout=findViewById(R.id.signout);
        progressDialog=new ProgressDialog(DashBoard.this);

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        toolbar=findViewById(R.id.include);
        setSupportActionBar(toolbar);
        /*if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }*/

        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait...");

        status=new login_status(getApplicationContext()) ;
        /*Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status.writelogin_status(false);
                startActivity(new Intent(DashBoard.this,LoginActivity.class));
                finish();

            }
        });*/
    }



    public void nextpage(View view){
          startActivity(new Intent(DashBoard.this,FRRBuyerName.class));

      }
    public void Redirect(View view){
        switch (view.getId())
        {
            case R.id.reconciliation:
               startActivity(new Intent(DashBoard.this, FRRBuyerName.class));
                break;

            case R.id.dailyreport:
                   startActivity(new Intent(DashBoard.this, DPR_BuyerName.class));
                break;

            case R.id.sewing:

                progressDialog.show();
                startActivity(new Intent(DashBoard.this, SewingBuyerName.class));
                progressDialog.dismiss();
                break;

            case R.id.cutting:
                startActivity(new Intent(DashBoard.this, CuttingBuyerName.class));
                progressDialog.dismiss();
                break;

            case R.id.packing:
                startActivity(new Intent(DashBoard.this, PackingBuyerName.class));
                progressDialog.dismiss();
                break;
            case R.id.finishing:
                startActivity(new Intent(DashBoard.this, FinishingBuyerName.class));
                progressDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.logout:
                status.writelogin_status(false);
                startActivity(new Intent(DashBoard.this,LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
