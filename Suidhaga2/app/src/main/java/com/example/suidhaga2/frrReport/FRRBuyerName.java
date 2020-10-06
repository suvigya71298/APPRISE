package com.example.suidhaga2.frrReport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suidhaga2.R;
import com.example.suidhaga2.cutting.CuttingBuyerName;
import com.example.suidhaga2.cutting.CuttingJobNumber;
import com.example.suidhaga2.retrofit.ApiClient;
import com.example.suidhaga2.retrofit.Buyer_model_data;
import com.example.suidhaga2.retrofit.api;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRRBuyerName extends AppCompatActivity {
    private Button frr_submit;
    private com.example.suidhaga2.retrofit.api api;
    private String[] data;
    private String a,checkvalid;
    private Intent i;
    private TextView textview_select_buyer;
    private AutoCompleteTextView textView;
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;

    int month,day,year;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frrbuyer_name);
        checkvalid="";
        mDisplayDate=findViewById(R.id.tvDate);
        textview_select_buyer=findViewById(R.id.select_buyer_name);
        textView=findViewById(R.id.frr_autocomplete);
        toolbar=findViewById(R.id.actionbar9);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        textView.setVisibility(View.GONE);
        textview_select_buyer.setVisibility(View.GONE);

        frr_submit =findViewById(R.id.frr_submit);

        frr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FRRBuyerName.this,FRRJobNumber.class));
            }
        });

      //  mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                 year = cal.get(Calendar.YEAR);
                 month = cal.get(Calendar.MONTH);
                 day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FRRBuyerName.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                ((ViewGroup) dialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                String date1 = month + "/" + year;
                mDisplayDate.setText(date1);
                textView.setVisibility(View.VISIBLE);
                textview_select_buyer.setVisibility(View.VISIBLE);
                checkvalid="date set";
                i=new Intent(FRRBuyerName.this, FRRJobNumber.class);
                i.putExtra("month",month);
                i.putExtra("year",year);
                ////////////////////////////////////////////////////////////////
                api= ApiClient.getapiclient().create(com.example.suidhaga2.retrofit.api.class);

                //Toast.makeText(this, month+year, Toast.LENGTH_SHORT).show();
                Call<List<Buyer_model_data>> call=api.get_buyer_frr(month,year);
                call.enqueue(new Callback<List<Buyer_model_data>>() {
                    @Override
                    public void onResponse(Call<List<Buyer_model_data>> call, Response<List<Buyer_model_data>> response) {
                        List<Buyer_model_data> buyer=response.body();
                        data=new String[buyer.size()];
                        for(int i=0;i<buyer.size();i++)
                        {
                            data[i]=buyer.get(i).getBuyer();
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(FRRBuyerName.this,android.R.layout.simple_list_item_1,data);
                        textView.setAdapter(arrayAdapter);

                    }@Override
                    public void onFailure(Call<List<Buyer_model_data>> call, Throwable t) {

                        Toast.makeText(FRRBuyerName.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };



        frr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = textView.getText().toString().trim();
                if (a.length() == 0) {
                    textView.requestFocus();
                    textView.setError("FIELD CANNOT BE EMPTY");

                } else {
                    //   Toast.makeText(FRRBuyerName.this, a, Toast.LENGTH_SHORT).show();
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
}


