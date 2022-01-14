package com.example.mabiaat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mabiaat.offlinedata.Commission;
import com.example.mabiaat.offlinedata.Constants;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Sales;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddSalesReportActivity extends AppCompatActivity {


    Spinner mMonthChooser;
    EditText mYearEt;
    TextInputEditText mNorthEt;
    TextInputEditText mSouthEt;
    TextInputEditText mEastEt;
    TextInputEditText mWestEt;
    TextInputEditText mLebanonEt;
    Button mSaveBtn;

    Myappdatabas myappdatabas;

    int repId;
    String area;

    String[] monthArray;
    int chosenMonth = -1;

    Calendar cal;
    String createdAt = null;

    //if alarm type is set to true
    MediaPlayer m1 = null;
    int warningSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_report);

        warningSound = R.raw.alert;

        myappdatabas = Myappdatabas.getDatabase(this);
        cal = Calendar.getInstance();
        createdAt = "" + cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH);

        Intent sender = getIntent();
        if(sender != null){
            repId = sender.getIntExtra("rep_id", -1);
            area = sender.getStringExtra("area");
        }

        bindViews();

        mMonthChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthArray = getResources().getStringArray(R.array.months);
                chosenMonth = Integer.parseInt(monthArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddSalesReportActivity.this, "month is required value please set a month", Toast.LENGTH_SHORT).show();
            }
        });

        mSaveBtn.setOnClickListener(v -> {
            mSaveBtn.setEnabled(false);
            if (validateUserInput()) {
                addSalesReport();
            }
        });


    }


    private void bindViews() {
        mMonthChooser = findViewById(R.id.month_chooser);
        mYearEt = findViewById(R.id.year);
        mNorthEt = findViewById(R.id.north);
        mSouthEt = findViewById(R.id.south);
        mEastEt = findViewById(R.id.east);
        mWestEt = findViewById(R.id.west);
        mLebanonEt = findViewById(R.id.lebanon);
        mSaveBtn = findViewById(R.id.save);
    }

    private boolean validateUserInput() {

        //first getting the values
        final String yearString = mYearEt.getText().toString();
        int year;
        final String north = mNorthEt.getText().toString();
        final String south = mSouthEt.getText().toString();
        final String east = mEastEt.getText().toString();
        final String west = mWestEt.getText().toString();
        final String lebanon = mLebanonEt.getText().toString();
        double value;

        //checking if id is empty
        if (TextUtils.isEmpty(yearString)) {
            Toast.makeText(this, "year field is required!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            year = Integer.parseInt(yearString);
            if(year < 1980 || year > 2021){
                Toast.makeText(this, "please choose a valid year!", Toast.LENGTH_SHORT).show();
                mSaveBtn.setEnabled(true);
                return false;
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "year must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }


        if (TextUtils.isEmpty(north)) {
            Toast.makeText(this, "put at least zero value !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            value = Double.parseDouble(north);
        }catch (NumberFormatException e){
            Toast.makeText(this, "sales must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if (TextUtils.isEmpty(south)) {
            Toast.makeText(this, "put at least zero value !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            value = Double.parseDouble(south);
        }catch (NumberFormatException e){
            Toast.makeText(this, "sales must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if (TextUtils.isEmpty(east)) {
            Toast.makeText(this, "put at least zero value !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            value = Double.parseDouble(east);
        }catch (NumberFormatException e){
            Toast.makeText(this, "sales must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if (TextUtils.isEmpty(west)) {
            Toast.makeText(this, "put at least zero value !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            value = Double.parseDouble(west);
        }catch (NumberFormatException e){
            Toast.makeText(this, "sales must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if (TextUtils.isEmpty(lebanon)) {
            Toast.makeText(this, "put at least zero value !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            value = Double.parseDouble(lebanon);
        }catch (NumberFormatException e){
            Toast.makeText(this, "sales must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if(chosenMonth == -1){
            Toast.makeText(this, "please choose month first !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }


        return true;

    }

    private void addSalesReport() {

        int year = Integer.parseInt(mYearEt.getText().toString());

        Sales sales = new Sales();
        sales.setRepresentativeId(repId);
        sales.setMonth(chosenMonth);
        sales.setYear(year);
        sales.setNorth(Double.parseDouble(mNorthEt.getText().toString()));
        sales.setSouth(Double.parseDouble(mSouthEt.getText().toString()));
        sales.setEast(Double.parseDouble(mEastEt.getText().toString()));
        sales.setWest(Double.parseDouble(mWestEt.getText().toString()));
        sales.setLebanon(Double.parseDouble(mLebanonEt.getText().toString()));
        sales.setCreatedAt(createdAt);

        boolean existed = myappdatabas.salesDao().isSalesReportExisted(repId, year, chosenMonth);

        if(existed){

            playSound();

            LayoutInflater factory = LayoutInflater.from(this);
            final View view = factory.inflate(R.layout.confirm_replace_dialog, null);
            final AlertDialog confirmDialog = new AlertDialog.Builder(this).create();
            confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            confirmDialog.setView(view);

            TextView yes = view.findViewById(R.id.yes);
            TextView no = view.findViewById(R.id.no);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Sales sales1 = myappdatabas.salesDao().getSalesReportByEmpYearMonth(repId, year, chosenMonth);
                    sales1.setNorth(Double.parseDouble(mNorthEt.getText().toString()));
                    sales1.setSouth(Double.parseDouble(mSouthEt.getText().toString()));
                    sales1.setEast(Double.parseDouble(mEastEt.getText().toString()));
                    sales1.setWest(Double.parseDouble(mWestEt.getText().toString()));
                    sales1.setLebanon(Double.parseDouble(mLebanonEt.getText().toString()));
                    myappdatabas.salesDao().updateSalesReport(sales1);

                    //update also the commission table
                    calculateNUpdateCommission(sales1);
                    confirmDialog.dismiss();
                    finish();

                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.dismiss();
                    Toast.makeText(AddSalesReportActivity.this, "change the year and/or month for this report", Toast.LENGTH_SHORT).show();
                    mSaveBtn.setEnabled(true);
                }
            });
            confirmDialog.show();

        }else {
            myappdatabas.salesDao().addSalesReport(sales);
            //calculate and save the commission
            calculateNSubmitCommission(sales);
            finish();
        }



    }

    private void calculateNSubmitCommission(Sales sales) {

        double[] commissionValues = calculateCommission(sales.getNorth(), sales.getSouth(), sales.getEast(), sales.getWest(), sales.getLebanon());
        Commission commission = new Commission();
        commission.setRepresentativeId(repId);
        commission.setMonth(sales.getMonth());
        commission.setYear(sales.getYear());
        commission.setNorthCommission(commissionValues[0]);
        commission.setSouthCommission(commissionValues[1]);
        commission.setEastCommission(commissionValues[2]);
        commission.setWestCommission(commissionValues[3]);
        commission.setLebanonCommission(commissionValues[4]);
        commission.setCommission(commissionValues[5]);
        commission.setCreatedAt(createdAt);
        myappdatabas.commissionsDao().addCommissionReport(commission);

    }

    private void calculateNUpdateCommission(Sales sales) {

        double[] commissionValues = calculateCommission(sales.getNorth(), sales.getSouth(), sales.getEast(), sales.getWest(), sales.getLebanon());
        Commission commission = myappdatabas.commissionsDao().getCommissionForEmpNDate(repId, sales.getYear(), sales.getMonth());
        commission.setNorthCommission(commissionValues[0]);
        commission.setSouthCommission(commissionValues[1]);
        commission.setEastCommission(commissionValues[2]);
        commission.setWestCommission(commissionValues[3]);
        commission.setLebanonCommission(commissionValues[4]);
        commission.setCommission(commissionValues[5]);
        myappdatabas.commissionsDao().updateCommission(commission);

    }

    private double[] calculateCommission(double north, double south, double east, double west, double lebanon){
        final String mainArea = area;
        double commission = 0;
        double northCommission = 0, southCommission = 0, eastCommission = 0, westCommission = 0, lebanonCommission = 0;

        switch (mainArea){
            case Constants.NORTH_AREA:
                northCommission = calculateForSingleArea(north, true);
                southCommission = calculateForSingleArea(south, false);
                eastCommission = calculateForSingleArea(east, false);
                westCommission = calculateForSingleArea(west, false);
                lebanonCommission = calculateForSingleArea(lebanon, false);
                commission += northCommission + southCommission + eastCommission + westCommission + lebanonCommission;
                break;
            case Constants.SOUTH_AREA:
                southCommission = calculateForSingleArea(south, true);
                northCommission = calculateForSingleArea(north, false);
                eastCommission = calculateForSingleArea(east, false);
                westCommission = calculateForSingleArea(west, false);
                lebanonCommission = calculateForSingleArea(lebanon, false);
                commission += northCommission + southCommission + eastCommission + westCommission + lebanonCommission;
                break;
            case Constants.EAST_AREA:
                eastCommission = calculateForSingleArea(east, true);
                northCommission = calculateForSingleArea(north, false);
                southCommission = calculateForSingleArea(south, false);
                westCommission = calculateForSingleArea(west, false);
                lebanonCommission = calculateForSingleArea(lebanon, false);
                commission += northCommission + southCommission + eastCommission + westCommission + lebanonCommission;
                break;
            case Constants.WEST_AREA:
                westCommission = calculateForSingleArea(west, true);
                northCommission = calculateForSingleArea(north, false);
                southCommission = calculateForSingleArea(south, false);
                eastCommission = calculateForSingleArea(east, false);
                lebanonCommission = calculateForSingleArea(lebanon, false);
                commission += northCommission + southCommission + eastCommission + westCommission + lebanonCommission;
                break;
            case Constants.LEBANON:
                lebanonCommission = calculateForSingleArea(lebanon, true);
                northCommission = calculateForSingleArea(north, false);
                southCommission = calculateForSingleArea(south, false);
                westCommission = calculateForSingleArea(west, false);
                eastCommission = calculateForSingleArea(east, false);
                commission += northCommission + southCommission + eastCommission + westCommission + lebanonCommission;
                break;
        }

        return new double[]{northCommission, southCommission, eastCommission, westCommission, lebanonCommission, commission};
    }
    private double calculateForSingleArea(double amount, boolean main){

        if(amount == 0 )
            return 0;

        double commission = 0;
        double rest = amount;
        if(main){
            if(rest >= 10000000){
                double temp = 10000000;
                while (rest >= 10000000){
                    commission += (0.05 * temp);
                    rest = rest - 10000000;
                }
                if(rest > 0){
                    commission += (0.07 * rest);
                }
            }else {
                commission += rest * 0.07;
            }
        }else {
            commission += 0.03 * rest;
        }
        return commission;
    }

    public void playSound() {
        // This play function
        // takes five parameter
        // leftVolume, rightVolume,
        // priority, loop and rate.
        if(m1 == null) {
            m1 = MediaPlayer.create(this, warningSound);
            m1.setVolume(1.0f, 1.0f);
            m1.setLooping(false);
            m1.start();

        }else
            m1.start();
    }
}