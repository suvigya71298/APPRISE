package com.example.suidhaga2.packing;

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

public class PackingJobNumber extends AppCompatActivity {
    private com.example.suidhaga2.retrofit.api api;
    private String buyer;
    private String b;
    private Toolbar toolbar;
    private TextView tv1;

    String TAG="Msg";
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_job_number);
        listView=findViewById(R.id.packing_listview);
        tv1=findViewById(R.id.packing_jobnotv1);
        toolbar=findViewById(R.id.actionbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        Intent intent=new Intent(PackingJobNumber.this,Packing_data.class);
                        intent.putExtra("trial",trial);
                        startActivity(intent);

                    }
                });

            }
            @Override
            public void onFailure(Call<List<Job_data>> call, Throwable t) {
                Toast.makeText(PackingJobNumber.this, "Retrofit Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
