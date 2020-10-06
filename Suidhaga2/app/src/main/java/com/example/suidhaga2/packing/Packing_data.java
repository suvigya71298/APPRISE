package com.example.suidhaga2.packing;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.R;
import com.example.suidhaga2.cutting.Cutting_data;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Report_model_data;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.sewing.Sewing_data;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Packing_data extends AppCompatActivity {
    private ListView packing_datalist;
    private com.example.suidhaga2.retrofit.api api;
    private String mTitle[]={"Order Qty","Fabric","Colour","Buyer PO","Production Qty","Total Issued","Total Received","Balance","Last Updated"};
    private String mDescription[]=new String[12] ;
    private String Job_no;
    private ImageView imgview2;
    private TextView textView,textView1,textview2;
    private Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_packing_data);
        packing_datalist =(ListView)findViewById(R.id.packing_data_list);
        imgview2=findViewById(R.id.packimage);
        progressDialog=new ProgressDialog(Packing_data.this);
        progressDialog.setMessage("Loading All Contents");
        progressDialog.setCancelable(false);
        getalldata();
        textView=findViewById(R.id.styleno3);
        textView1=findViewById(R.id.packingbuyername);
        textview2=findViewById(R.id.jobnopacking);
        toolbar=findViewById(R.id.actionbarPckData);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void getalldata()
    {
        progressDialog.show();
        Bundle  extras=getIntent().getExtras();
        if(extras!=null) {
            Job_no= extras.getString("trial");
          //  Toast.makeText(this, Job_no, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }



        api= ApiClient.getapiclient().create(api.class);
        Call<Report_model_data> call=api.packing_data(Job_no);





        call.enqueue(new Callback<Report_model_data>() {
            @Override
            public void onResponse(Call<Report_model_data> call, Response<Report_model_data> response) {

                String jobnumber=response.body().getJob_no();
                String buyername=response.body().getBuyer();
                String stylenumber=response.body().getStyle();
                mDescription[4]=response.body().getProduction_qty();
                mDescription[1]=response.body().getFabric();
                mDescription[2]=response.body().getColor();
                mDescription[3]=response.body().getBuyer_po();
                mDescription[0]=response.body().getOrder_qty();
                mDescription[5]=response.body().getTotal_issue();
                mDescription[6]=response.body().getTotal_received();
                mDescription[7]=response.body().getBalance();
                mDescription[8]=response.body().getDate();

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


                Packing_data.MyAdapter adapter = new Packing_data.MyAdapter(Packing_data.this, mTitle, mDescription);
                textView.setText(stylenumber);
                textView1.setText(buyername);
                textview2.setText(jobnumber);
                packing_datalist.setAdapter(adapter);

                String url="http://103.255.232.8/image_sui_dhaga/"+jobnumber+".png";

                Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imgview2, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //  Toast.makeText(Cutting_data.this, "Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Packing_data.this, e.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });




            }

            @Override
            public void onFailure(Call<Report_model_data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Packing_data.this, call.toString(), Toast.LENGTH_SHORT).show();
            }


        });

    }
    class MyAdapter extends ArrayAdapter<String> {

        private Packing_data data=new Packing_data();
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
