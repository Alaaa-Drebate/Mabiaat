package com.example.mabiaat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mabiaat.adapters.CommissionsAdapter;
import com.example.mabiaat.adapters.SalesAdapter;
import com.example.mabiaat.offlinedata.Commission;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Sales;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SearchSalesActivity extends AppCompatActivity {

    TextInputEditText mSearchEt;
    RecyclerView mSalesList;

    Spinner mMonthChooser;
    Spinner mYearChooser;

    boolean onSearch = false;

    int chosenMonth = -1;
    int chosenYear = -1;

    SalesAdapter mAdapter;
    ArrayList<Sales> sales;

    Myappdatabas myappdatabas;

    String query = null;
    ArrayList<Integer> years;
    ArrayList<Integer> months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sales);

        myappdatabas = Myappdatabas.getDatabase(this);

        bindViews();

        sales = (ArrayList<Sales>) myappdatabas.salesDao().getAllSalesReports();

        mAdapter = new SalesAdapter(this, sales);

        mSalesList.setAdapter(mAdapter);

        setUpYearChooser();
        setUpMonthChooser();

        mYearChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenYear = years.get(position);
                if(chosenYear > -1)
                    updateList("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mMonthChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenMonth = months.get(position);
                if(chosenMonth > -1)
                    updateList("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSearchEt.setOnEditorActionListener((v, actionId, event) -> {
            if (event == null) {
                query = mSearchEt.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    // Capture soft enters in a singleLine EditText that is the last EditText.
                    if(query.isEmpty()){
                        Toast.makeText(this, "enter a search query first!", Toast.LENGTH_SHORT).show();
                    }else {
                        updateList(query);
                    }
                }
                else if (actionId==EditorInfo.IME_ACTION_NEXT)
                {
                    // Capture soft enters in other singleLine EditTexts
                    if(query.isEmpty()){
                        Toast.makeText(this, "enter a search query first!", Toast.LENGTH_SHORT).show();
                    }else {
                        updateList(query);
                    }
                }
                else return false;  // Let system handle all other null KeyEvents
            } else
                return false;

            return false;
        });
    }

    private void updateList(String query) {
        onSearch = true;
        if(query.isEmpty()){
            if(chosenMonth > -1 && chosenYear == -1){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesForMonth(chosenMonth);
            }else if(chosenYear > -1 && chosenMonth ==-1){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesForYear(chosenYear);
            }else if((chosenYear > -1) && (chosenMonth > -1)){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesReportByYearMonth(chosenYear, chosenMonth);
            }
        }else {
            if(chosenMonth > -1 && chosenYear == -1){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesByMonthEmpName(query, chosenMonth);
            }else if(chosenYear > -1 && chosenMonth ==-1){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesByYearEmpName(query, chosenYear);
            }else if((chosenYear > -1) && (chosenMonth > -1)){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesByMonthYearEmpName(query, chosenMonth, chosenYear);
            }else if(chosenYear == -1 && chosenMonth == -1){
                sales = (ArrayList<Sales>) myappdatabas.salesDao().getSalesReportByEmpName(query);
            }
        }
        mAdapter = new SalesAdapter(this, sales);
        mSalesList.setAdapter(mAdapter);

    }

    private void setUpYearChooser() {

        years = new ArrayList<>();
        years.add(-1);
        for(int i = 1980; i <= 2021; i++){
            years.add(i);
        }
        // Creating adapter for spinner
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, years);
        // Drop down layout style - list view with radio button
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mYearChooser.setAdapter(yearsAdapter);

        onSearch = false;
    }

    private void setUpMonthChooser() {
        months = new ArrayList<>();
        months.add(-1);
        for(int i = 1; i <= 12; i++){
            months.add(i);
        }
        // Creating adapter for spinner
        ArrayAdapter<Integer> monthsAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, months);
        // Drop down layout style - list view with radio button
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mMonthChooser.setAdapter(monthsAdapter);

        onSearch = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sales = (ArrayList<Sales>) myappdatabas.salesDao().getAllSalesReports();
        mAdapter = new SalesAdapter(this, sales);
        mSalesList.setAdapter(mAdapter);
    }

    private void bindViews() {
        mSearchEt = findViewById(R.id.search);
        mSalesList = findViewById(R.id.sales_list);
        mMonthChooser = findViewById(R.id.month_chooser);
        mYearChooser = findViewById(R.id.year_chooser);
    }

    @Override
    public void onBackPressed() {
        if(onSearch){
            onSearch = false;
            setUpMonthChooser();
            setUpYearChooser();
            sales = (ArrayList<Sales>) myappdatabas.salesDao().getAllSalesReports();
            mAdapter = new SalesAdapter(this, sales);
            mSalesList.setAdapter(mAdapter);
            mSearchEt.setText("");
        }else {
            finish();
        }

    }


}