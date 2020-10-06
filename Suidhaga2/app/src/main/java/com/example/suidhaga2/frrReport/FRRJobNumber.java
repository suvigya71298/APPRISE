package com.example.suidhaga2.frrReport;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.PinLock;
import com.example.suidhaga2.R;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Download_report;
import com.example.suidhaga2.retrofit.Job_data;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.retrofit.Download_report;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRRJobNumber extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE =0 ;
    private com.example.suidhaga2.retrofit.api api,api2;
    private String buyer;
    private String b;
    private TextView tv1;
    String TAG = "Msg";
    private ListView listView;
    private  int month,year;
    private Toolbar toolbar;
    private ImageView img;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frrjob_number);
        listView = findViewById(R.id.frr_listview);
        tv1 = findViewById(R.id.frr_jobnotv1);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        progressDialog=new ProgressDialog(FRRJobNumber.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        // img=findViewById(R.id.download);

        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        Snackbar.make(v,"Permission Denied",Snackbar.LENGTH_LONG).show();
                        String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {

                        startDownloading();
                    }

                }
                else{


                    startDownloading();
                }

            }
        });
*/

        Bundle  extras=getIntent().getExtras();
        if(extras!=null) {
            b = extras.getString("job_no");
            month=extras.getInt("month");
            year=extras.getInt("year");
            //Toast.makeText(this, b, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
        api= ApiClient.getapiclient().create(api.class);

        Call<List<Job_data>> call=api.get_job_frr(b,month,year);
        call.enqueue(new Callback<List<Job_data>>() {
            @Override
            public void onResponse(Call<List<Job_data>> call, Response<List<Job_data>> response) {
                List<Job_data> jobNoList=response.body();
                // List<Job_data> jobNoList=response.body();
                final String[] job1=new String[jobNoList.size()];
                for (int i=0;i<jobNoList.size();i++)
                {
                    job1[i]=jobNoList.get(i).getJob_no();

                }
                // Toast.makeText(SweingJobNumber.this, job1[0], Toast.LENGTH_SHORT).show();
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,job1));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String trial=job1[position];
                        // Toast.makeText(SweingJobNumber.this, trial, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(FRRJobNumber.this, FRRData.class);
                        intent.putExtra("trial",trial);
                        intent.putExtra("month",month);
                        intent.putExtra("year",year);
                        startActivity(intent);

                    }
                });

            }
            @Override
            public void onFailure(Call<List<Job_data>> call, Throwable t) {
                Toast.makeText(FRRJobNumber.this, "Retrofit Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startDownloading() {

        // String url=editText.getText().toString().trim();
        final String url="http://103.255.232.8/Sui_Dhaga/pdfs/FRR_Report.pdf";
        api2=ApiClient.getapiclient().create(api.class);
        //Download Request
        Call<Download_report> call2=api2.get_download_report(month,year);
        progressDialog.show();
        call2.enqueue(new Callback<Download_report>() {
            @Override
            public void onResponse(Call<Download_report> call, Response<Download_report> response) {
              String report_download=response.body().getDownload_report();
              if (report_download.equalsIgnoreCase("yes")){
                  DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));

                  //Allow types of network to download the file
                  request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                  request.setTitle("ReconciliationReport.pdf");
                  request.setDescription("Downloading");
                  request.allowScanningByMediaScanner();
                  request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                  request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ReconciliationReport.pdf");
                  Toast.makeText(FRRJobNumber.this,"Downloading Pdf",Toast.LENGTH_LONG).show();

                  //get download service
                  DownloadManager manager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                  manager.enqueue(request);
                  progressDialog.dismiss();
              }

            }

            @Override
            public void onFailure(Call<Download_report> call, Throwable t) {
                Toast.makeText(FRRJobNumber.this, "Download Failed", Toast.LENGTH_SHORT).show();
            }
        });




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:
            {
                if(grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    startDownloading();
                }
                else {
                    Snackbar.make(findViewById(R.id.dpr_display_data),"Permission Denied",Snackbar.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dowload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.report:
              //  Toast.makeText(FRRJobNumber.this,"Hello",Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        Snackbar.make(findViewById(R.id.ll2),"Permission Denied",Snackbar.LENGTH_LONG).show();
                        String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {

                        startDownloading();
                    }
                }
                else{
                    startDownloading();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
