package com.example.suidhaga2.sewing;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.suidhaga2.R;
import com.example.suidhaga2.cutting.Cutting_data;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Sewing_model_data;
import com.example.suidhaga2.retrofit.api;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sewing_data extends AppCompatActivity {

    private ListView sweing_data_list;
    private com.example.suidhaga2.retrofit.api api;
    private String mTitle[]={"Order Qty","Fabric","Colour","Buyer PO","Production Qty","Total Issued","Total Received","Balance","Last Updated"};
    private String mDescription[]=new String[9] ;
    private String Job_no;
    private ImageView imgview;
    private TextView textview,textview1,textview2;
    private Toolbar toolbar1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sewing_data);

        toolbar1=findViewById(R.id.actionbar1);

        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imgview=findViewById(R.id.sewingimage);
        sweing_data_list=(ListView)findViewById(R.id.sweing_data_list);
        textview=findViewById(R.id.styleno1);
        textview1=findViewById(R.id.sewing_buyername);
        textview2=findViewById(R.id.jobnosewing);
        progressDialog=new ProgressDialog(Sewing_data.this);
        progressDialog.setMessage("Loading All Contents");
        progressDialog.setCancelable(false);

        getalldata();

    }
    void getalldata()
    {
        progressDialog.show();
        Bundle  extras=getIntent().getExtras();
        if(extras!=null) {
            Job_no= extras.getString("trial");
           // Toast.makeText(this, Job_no, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }




        api= ApiClient.getapiclient().create(api.class);
        Call<Sewing_model_data> call=api.sewing_data(Job_no);

        call.enqueue(new Callback<Sewing_model_data>() {
            @Override
            public void onResponse(Call<Sewing_model_data> call, Response<Sewing_model_data> response) {

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

                MyAdapter adapter = new MyAdapter(Sewing_data.this, mTitle, mDescription);
                textview.setText(stylenumber);
                textview1.setText(buyername);
                textview2.setText(jobnumber);
                sweing_data_list.setAdapter(adapter);

                String url = "http://103.255.232.8/image_sui_dhaga/" + jobnumber + ".png";
              Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imgview, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //  Toast.makeText(Finishing_data.this, "Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Sewing_data.this, e.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }

                });


            }
            @Override
            public void onFailure(Call<Sewing_model_data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Sewing_data.this, call.toString(), Toast.LENGTH_SHORT).show();
            }




        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        private   Sewing_data data=new Sewing_data();
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
