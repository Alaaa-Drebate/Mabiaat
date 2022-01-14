package com.example.mabiaat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mabiaat.adapters.SalesAdapter;
import com.example.mabiaat.offlinedata.Constants;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Sales;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RepresentativeDetailsActivity extends AppCompatActivity {

    ImageView mImage;
    TextView mIdTv;
    TextView mNameTv;
    TextView mAreaTv;
    Button mAddReportBtn;
    RecyclerView mSalesList;

    ArrayList<Sales> sales;
    SalesAdapter mSalesAdapter;


    Uri imageUri;
    int id;
    String name;
    String chosenArea;

    Myappdatabas myappdatabas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_details);

        myappdatabas = Myappdatabas.getDatabase(this);

        bindViews();

        Intent sender = getIntent();
        if(sender != null){
            imageUri = Uri.parse(sender.getStringExtra("image"));
            id = sender.getIntExtra("id", -1);
            name = sender.getStringExtra("name");
            chosenArea = sender.getStringExtra("area");
        }

        updateUi();

        mAddReportBtn.setOnClickListener(v -> {
            Intent salesReport = new Intent(this, AddSalesReportActivity.class);
            salesReport.putExtra("rep_id", id);
            salesReport.putExtra("area", chosenArea);
            startActivity(salesReport);
        });

        sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesForEmp(id);

        mSalesAdapter = new SalesAdapter(this, sales);
        mSalesList.setAdapter(mSalesAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesForEmp(id);

        mSalesAdapter = new SalesAdapter(this, sales);
        mSalesList.setAdapter(mSalesAdapter);
    }

    private void updateUi() {

        mIdTv.setText(String.valueOf(id));
        mNameTv.setText(name);
        mAreaTv.setText(chosenArea);

        Glide.with(this)
                .load(imageUri)
                .into(mImage);
    }

    private void bindViews() {
        mImage = findViewById(R.id.image);
        mIdTv = findViewById(R.id.rep_id);
        mNameTv = findViewById(R.id.name);
        mAreaTv = findViewById(R.id.area);
        mAddReportBtn = findViewById(R.id.add_report);
        mSalesList = findViewById(R.id.sales_list);
    }
}