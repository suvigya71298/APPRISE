package com.example.suidhaga2.finishing;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
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
import com.example.suidhaga2.retrofit.Report_model_data;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.sewing.Sewing_data;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Finishing_data extends AppCompatActivity {
    private ListView finishing_datalist;
    private com.example.suidhaga2.retrofit.api api;
    private String mTitle[]={"Order Qty","Fabric","Colour","Buyer PO","Production Qty","Total Issued","Total Received","Balance","Last Updated"};
    private String mDescription[]=new String[9] ;
    private String Job_no;
    private String url;
    private ImageView imgview1;
    private TextView textView,textView1,textview2;
    private Toolbar toolbar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finishing_data);
        finishing_datalist =(ListView)findViewById(R.id.finishing_data_list);
        progressDialog=new ProgressDialog(Finishing_data.this);
        progressDialog.setMessage("Loading All Contents");
        progressDialog.setCancelable(false);
        getalldata();
        imgview1=findViewById(R.id.finishimage);
        textView=findViewById(R.id.styleno2);
        textView1=findViewById(R.id.finishing_buyername);
        textview2=findViewById(R.id.jobnofinishing);

        toolbar=findViewById(R.id.actionbar6);
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
        Call<Report_model_data> call=api.finishing_data(Job_no);

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

                Finishing_data.MyAdapter adapter = new Finishing_data.MyAdapter(Finishing_data.this, mTitle, mDescription);
                textView.setText(stylenumber);
                textView1.setText(buyername);
                textview2.setText(jobnumber);
                finishing_datalist.setAdapter(adapter);


                url="http://103.255.232.8/image_sui_dhaga/"+jobnumber+".png";

                Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imgview1, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                      //  Toast.makeText(Finishing_data.this, "Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Finishing_data.this, e.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<Report_model_data> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Finishing_data.this, call.toString(), Toast.LENGTH_SHORT).show();
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
