package com.example.suidhaga2.cutting;

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

public class CuttingBuyerName extends AppCompatActivity {
    private Button cutting_submit;
    private com.example.suidhaga2.retrofit.api api;
    private String[] data;
    private String a;
    private Intent i;
    private AutoCompleteTextView textView;
    //private Toolbar toolbar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutting_buyer_name);

     //   toolbar=findViewById(R.id.actionbar2);
        toolbar=findViewById(R.id.actionbar2);

       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(null);
        //setSupportActionBar(toolbar);

        textView=findViewById(R.id.cutting_autocomplete);

        cutting_submit =findViewById(R.id.cutting_submit);

        getJobNumber();
        cutting_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=textView.getText().toString().trim();
                if(a.length()==0)

                {
                    textView.requestFocus();
                    textView.setError("FIELD CANNOT BE EMPTY");
                }
                else {
                    Toast.makeText(CuttingBuyerName.this, a, Toast.LENGTH_SHORT).show();
                    i = new Intent(CuttingBuyerName.this, CuttingJobNumber.class);

                    i.putExtra("job_no", a);
                    startActivity(i);
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
        api= ApiClient.getapiclient().create(api.class);

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
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(CuttingBuyerName.this,android.R.layout.simple_list_item_1,data);
                textView.setAdapter(arrayAdapter);

            }@Override
            public void onFailure(Call<List<Buyer_model_data>> call, Throwable t) {

                Toast.makeText(CuttingBuyerName.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });







    }
}
