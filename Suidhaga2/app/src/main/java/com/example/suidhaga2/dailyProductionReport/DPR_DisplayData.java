package com.example.suidhaga2.dailyProductionReport;


import android.app.ProgressDialog;
import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.suidhaga2.R;
import com.example.suidhaga2.cutting.Cutting_data;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Report_model_data;
import com.example.suidhaga2.retrofit.Sewing_model_data;
import com.example.suidhaga2.retrofit.api;
import com.example.suidhaga2.sewing.Sewing_data;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DPR_DisplayData extends AppCompatActivity

{

    private static final int PERMISSION_STORAGE_CODE = 0;
    private ListView sewing,cutting,finishing,packing,mandatory_fields;
    private com.example.suidhaga2.retrofit.api api;
    private String mTitle2[]={"Order Qty","Fabric","Colour","Buyer PO","Production Qty"};
    private String mTitle[]={"Total Issued","Total Received","Balance","Last Updated"};
    private String mDescription_mandatory[]=new String[5];
    private String mDescription_s[]=new String[4] ;
    private String mDescription_c[]=new String[4] ;
    private String mDescription_f[]=new String[4] ;
    private String mDescription_p[]=new String[4] ;
    private String Job_no;
    DPR_DisplayData.MyAdapter adapter,adapter2;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView textView,textView1,textView2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dpr__display_data);
        progressDialog=new ProgressDialog(DPR_DisplayData.this);
        progressDialog.setMessage("Loading All Contents");
        progressDialog.setCancelable(false);

        sewing=findViewById(R.id.dpr_sewing_data_list);
        cutting=findViewById(R.id.dpr_cutting_data_list);
        finishing=findViewById(R.id.dpr_finishing_data_list);
        packing=findViewById(R.id.dpr_packing_data_list);
        mandatory_fields=findViewById(R.id.dpr_mandatory_data_list);
        imageView=findViewById(R.id.dprimage);
        textView=findViewById(R.id.style5);
        textView1=findViewById(R.id.dprbuyername);
        textView2=findViewById(R.id.jobno);

        toolbar=findViewById(R.id.actionbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle  extras=getIntent().getExtras();
        Job_no= extras.getString("trial");

        getdatafor_cutting();

        getdatafor_sewing();

        getdatafor_finishing();

        getdatafor_packing();
    }

    private void getdatafor_packing()
    {
        progressDialog.show();
        api= ApiClient.getapiclient().create(api.class);
        Call<Report_model_data> call=api.packing_data(Job_no);

        call.enqueue(new Callback<Report_model_data>() {
            @Override
            public void onResponse(Call<Report_model_data> call, Response<Report_model_data> response) {

             String jobno=response.body().getJob_no();
               String buyername=response.body().getBuyer();
                String stylenumber=response.body().getStyle();
                mDescription_mandatory[4]=response.body().getProduction_qty();
                mDescription_mandatory[1]=response.body().getFabric();
                mDescription_mandatory[2]=response.body().getColor();
                mDescription_mandatory[3]=response.body().getBuyer_po();
                mDescription_mandatory[0]=response.body().getOrder_qty();
                mDescription_p[0]=response.body().getTotal_issue();
                mDescription_p[1]=response.body().getTotal_received();
                mDescription_p[2]=response.body().getBalance();
                mDescription_p[3]=response.body().getDate();
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




                adapter = new DPR_DisplayData.MyAdapter(DPR_DisplayData.this, mTitle, mDescription_p);
                textView.setText(stylenumber);
                textView1.setText(buyername);
                textView2.setText(jobno);
                packing.setAdapter(adapter);
                ListUtils.setDynamicHeight(packing);
                adapter2 = new DPR_DisplayData.MyAdapter(DPR_DisplayData.this, mTitle2, mDescription_mandatory);
                mandatory_fields.setAdapter(adapter2);
                ListUtils.setDynamicHeight(mandatory_fields);

                String url="http://103.255.232.8/image_sui_dhaga/"+jobno+".png";

                Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                      //  Toast.makeText(DPR_DisplayData.this, "Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(DPR_DisplayData.this, e.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });






            }

            @Override
            public void onFailure(Call<Report_model_data> call, Throwable t) {
                        progressDialog.dismiss();
                Toast.makeText(DPR_DisplayData.this, call.toString(), Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void getdatafor_finishing()
    {


        api= ApiClient.getapiclient().create(api.class);
        Call<Report_model_data> call=api.finishing_data(Job_no);

        call.enqueue(new Callback<Report_model_data>() {
            @Override
            public void onResponse(Call<Report_model_data> call, Response<Report_model_data> response) {
                mDescription_f[0]=response.body().getTotal_issue();
                mDescription_f[1]=response.body().getTotal_received();
                mDescription_f[2]=response.body().getBalance();
                mDescription_f[3]=response.body().getDate();
                adapter = new DPR_DisplayData.MyAdapter(DPR_DisplayData.this, mTitle, mDescription_f);
                finishing.setAdapter(adapter);
                ListUtils.setDynamicHeight(finishing) ;
            }

            @Override
            public void onFailure(Call<Report_model_data> call, Throwable t) {
            }
        });

    }

    private void getdatafor_cutting()
    {
           api= ApiClient.getapiclient().create(api.class);
            Call<Report_model_data> call=api.cutting_data(Job_no);
            call.enqueue(new Callback<Report_model_data>() {
                @Override
                public void onResponse(Call<Report_model_data> call, Response<Report_model_data> response) {
                    mDescription_c[0]=response.body().getTotal_issue();
                    mDescription_c[1]=response.body().getTotal_received();
                    mDescription_c[2]=response.body().getBalance();
                    mDescription_c[3]=response.body().getDate();

                    adapter = new DPR_DisplayData.MyAdapter(DPR_DisplayData.this, mTitle, mDescription_c);
                    cutting.setAdapter(adapter);
                    ListUtils.setDynamicHeight(cutting);
                }
                @Override
                public void onFailure(Call<Report_model_data> call, Throwable t) {
                }
            });}

    private void getdatafor_sewing()
    {
        api= ApiClient.getapiclient().create(com.example.suidhaga2.retrofit.api.class);
        Call<Sewing_model_data> call=api.sewing_data(Job_no);

        call.enqueue(new Callback<Sewing_model_data>() {
            @Override
            public void onResponse(Call<Sewing_model_data> call, Response<Sewing_model_data> response) {
                mDescription_s[0]=response.body().getTotal_issue();
                mDescription_s[1]=response.body().getTotal_received();
                mDescription_s[2]=response.body().getBalance();
                mDescription_s[3]=response.body().getDate();
                 adapter = new DPR_DisplayData.MyAdapter(DPR_DisplayData.this, mTitle, mDescription_s);
                sewing.setAdapter(adapter);
                ListUtils.setDynamicHeight(sewing);
            }

            @Override
            public void onFailure(Call<Sewing_model_data> call, Throwable t) {

            }

        });

    }
    class MyAdapter extends ArrayAdapter<String> {

        private Sewing_data data=new Sewing_data();
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

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
               // Toast.makeText(DPR_DisplayData.this, "NULL HAI", Toast.LENGTH_SHORT).show();
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }



}

