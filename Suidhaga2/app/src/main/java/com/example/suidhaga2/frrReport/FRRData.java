package com.example.suidhaga2.frrReport;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.R;
import com.example.suidhaga2.cutting.Cutting_data;
import com.example.suidhaga2.finishing.Finishing_data;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Frr_data_model_data;
import com.example.suidhaga2.retrofit.Report_model_data;
import com.example.suidhaga2.retrofit.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRRData extends AppCompatActivity {

    private ListView frr_datalist;
    private com.example.suidhaga2.retrofit.api api;
    private String mTitle[]={"Order Qty","Fabric","Colour","Buyer PO","Production Qty","Cutting ord Avg","Actual Prod Avg",
            "Actual Cutting","fabric Issued","Fabric Returned","Fabric Consumed","Balance","Util %"};
    private String mDescription[]=new String[13] ;
    private String Job_no;
    private int month,year;
    private TextView textView,textView1,textview2;
    private Toolbar toolbar;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frrdata);
        frr_datalist =(ListView)findViewById(R.id.frr_listview);

        progressDialog=new ProgressDialog(FRRData.this);
        progressDialog.setMessage("Loading All Contents");
        progressDialog.setCancelable(false);

        getalldata();
        textView=findViewById(R.id.styleno4);
        textView1=findViewById(R.id.frrbuyername);
        textview2=findViewById(R.id.jobnofrr);
        toolbar=findViewById(R.id.actionbar10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    void getalldata()
    {
        progressDialog.show();
        Bundle  extras=getIntent().getExtras();
        if(extras!=null) {
            Job_no= extras.getString("trial");
            month=extras.getInt("month");
            year=extras.getInt("year");
            Toast.makeText(this, Job_no, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }


        api= ApiClient.getapiclient().create(com.example.suidhaga2.retrofit.api.class);
        Call<Frr_data_model_data> call=api.frr_data(Job_no,month,year);

        call.enqueue(new Callback<Frr_data_model_data>() {
            @Override
            public void onResponse(Call<Frr_data_model_data> call, Response<Frr_data_model_data> response) {

                String jobnumber=response.body().getJob_no();
                String buyername=response.body().getBuyer();
                String stylenumber=response.body().getStyle();
                mDescription[4]=response.body().getProduction_qty();
                mDescription[1]=response.body().getFabric();
                mDescription[2]=response.body().getColor();
                mDescription[3]=response.body().getBuyer_po();
                mDescription[0]=response.body().getOrder_qty();

                mDescription[5]=response.body().getCutting_ord_avg();
                mDescription[6]=response.body().getActual_prod_avg();
                mDescription[7]=response.body().getActual_cutting();
                mDescription[8]=response.body().getFabric_issued();
                mDescription[9]=response.body().getFabric_returned();
                mDescription[10]=response.body().getFabric_consumed();
                mDescription[11]=response.body().getBalance();
                mDescription[12]=response.body().getUtil();

                String[] words =buyername.split("\\s+");
                int len=words.length;
                if(len>2)
                {
                    String new_buyer="";
                    for(int count=0;count<len;count++)
                    {   if(words[count].length()==1){
                        new_buyer=new_buyer+words[count]+" ";
                    }

                    else if (count!=(len)/2)
                    {
                        new_buyer=new_buyer+words[count]+" ";
                    }
                    else{
                        new_buyer=new_buyer+"\n"+words[count]+" ";
                    }
                    }buyername=new_buyer;
                }


                FRRData.MyAdapter adapter = new FRRData.MyAdapter(FRRData.this, mTitle, mDescription);
                textView.setText(stylenumber);
                textView1.setText(buyername);
                textview2.setText(jobnumber);
                frr_datalist.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Frr_data_model_data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FRRData.this, call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    class MyAdapter extends ArrayAdapter<String> {

        private Finishing_data data=new Finishing_data();
        private Context context;
        private String rTitle[];
        private String rDescription[];

        MyAdapter (Context c, String title[], String description[]) {
            super(c,R.layout.cutting_data_cardview,R.id.heading,title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.cutting_data_cardview, parent, false);

            TextView myTitle = view.findViewById(R.id.heading);
            TextView myDescription = view.findViewById(R.id.data);

            // now set our resources on views

            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return view;
        }
    }
}
