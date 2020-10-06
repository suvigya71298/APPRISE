package com.example.suidhaga2.sewing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.suidhaga2.R;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Buyer_model_data;
import com.example.suidhaga2.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SewingBuyerName extends AppCompatActivity {

    Button sweing_submit;
    private api api;
    private String[] data;
    private Intent i;
    private AutoCompleteTextView textView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewing_buyer_name);
        toolbar=findViewById(R.id.actionbarSBN);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        api= ApiClient.getapiclient().create(com.example.suidhaga2.retrofit.api.class);


        textView=findViewById(R.id.sewing_autocomplete);
        //sweing_buyername=findViewById(R.id.sweing_buyername);
        sweing_submit=findViewById(R.id.sweing_submit);
        getJobNumber();

        sweing_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = textView.getText().toString().trim();
                if (a.length() == 0) {
                    textView.requestFocus();
                    textView.setError("FIELD CANNOT BE EMPTY");
                } else {
                    // startActivity(new Intent(SweingBuyerName.this,SweingJobNumber.class));
                    i = new Intent(SewingBuyerName.this, SewingJobNumber.class);
                    // Toast.makeText(SweingBuyerName.this, a, Toast.LENGTH_SHORT).show();
                    i.putExtra("check", 1);
                    i.putExtra("job_no", a);
                    startActivity(i);
                    // intent.putExtra("job_no",a);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        View view=this.getCurrentFocus();
        if(view!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);}
    }

    private void getJobNumber(){


        Call<List<Buyer_model_data>> call=api.get_buyer();
        call.enqueue(new Callback<List<Buyer_model_data>>() {
            @Override
            public void onResponse(Call<List<Buyer_model_data>> call, Response<List<Buyer_model_data>> response) {
                List<Buyer_model_data> buyer=response.body();
                data=new String[buyer.size()];
                for(int i=0;i<buyer.size();i++)
                {
                    data[i]=buyer.get(i).getBuyer();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SewingBuyerName.this,android.R.layout.simple_list_item_1,data);
                textView.setAdapter(arrayAdapter);




                //  Toast.makeText(SweingBuyerName.this, data[1], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Buyer_model_data>> call, Throwable t) {

                Toast.makeText(SewingBuyerName.this, call.toString(), Toast.LENGTH_LONG).show();

            }
        });






    }
}
