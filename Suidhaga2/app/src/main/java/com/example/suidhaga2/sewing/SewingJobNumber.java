package com.example.suidhaga2.sewing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.R;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Job_data;
import com.example.suidhaga2.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewingJobNumber extends AppCompatActivity {

    private com.example.suidhaga2.retrofit.api api;
    private  String buyer;
    private String b;
    private TextView tv1;
    Toolbar toolbar;
    String TAG="Msg";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewing_job_number);


        toolbar=findViewById(R.id.actionbarSBN1);
        //toolbar=findViewById(R.id.actionbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=findViewById(R.id.sewing_listview);
        tv1=findViewById(R.id.sewing_jobnotv1);
        Bundle  extras=getIntent().getExtras();
        if(extras!=null) {
            b = extras.getString("job_no");
            //Toast.makeText(this, b, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }


        api= ApiClient.getapiclient().create(api.class);

        Call<List<Job_data>> call=api.get_job(b);
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
                        Intent intent=new Intent(SewingJobNumber.this,Sewing_data.class);
                        intent.putExtra("trial",trial);
                        startActivity(intent);
                        finish();

                    }
                });

             /*   for(int i=0; i<jobNoList.size();i++)
                {

                    job1[i]=jobNoList.get(i).getJob();
                    Job_data jobNo= new Job_data("This is Job No"+job1[i]);


                    jobNoList.add(jobNo);
                }
                adapter=new SweingJobNumberAdapter(jobNoList,SweingJobNumber.this);
                recyclerView.setAdapter(adapter);*/

            }
            @Override
            public void onFailure(Call<List<Job_data>> call, Throwable t) {
                Toast.makeText(SewingJobNumber.this, "No response from the server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
