package com.example.mabiaat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mabiaat.offlinedata.Commission;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;

import java.text.Normalizer;

public class CommissionDetailsActivity extends AppCompatActivity {

    int id;
    Commission commission;

    TextView mId, mName, mMonth, mYear, mCreatedAt, mNorth, mSouth, mEast, mWest, mLebanon, mCommission;

    Myappdatabas myappdatabas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_details);

        bindViews();

        myappdatabas = Myappdatabas.getDatabase(this);

        Intent sender = getIntent();
        if(sender != null){
            id = sender.getIntExtra("commission_id", -1);
        }

        commission = myappdatabas.commissionsDao().getCommissionReportById(id);

        mId.setText(String.valueOf(commission.getRepresentativeId()));
        Representative representative = myappdatabas.representativesDao().getRepresentativeById(commission.getRepresentativeId());
        mName.setText(representative.getName());
        mMonth.setText(String.valueOf(commission.getMonth()));
        mYear.setText(String.valueOf(commission.getYear()));
        mCreatedAt.setText(commission.getCreatedAt());
        mNorth.setText(String.valueOf(commission.getNorthCommission()) + " S.P");
        mSouth.setText(String.valueOf(commission.getSouthCommission()) + " S.P");
        mEast.setText(String.valueOf(commission.getEastCommission()) + " S.P");
        mWest.setText(String.valueOf(commission.getWestCommission()) + " S.P");
        mLebanon.setText(String.valueOf(commission.getLebanonCommission()) + " S.P");
        mCommission.setText(String.valueOf(commission.getCommission()) + " S.P");

    }

    private void bindViews() {
        mId = findViewById(R.id.rep_id);
        mName = findViewById(R.id.name);
        mMonth = findViewById(R.id.month);
        mYear = findViewById(R.id.year);
        mCreatedAt = findViewById(R.id.created_at);
        mNorth = findViewById(R.id.north);
        mEast = findViewById(R.id.east);
        mSouth = findViewById(R.id.south);
        mWest = findViewById(R.id.west);
        mLebanon = findViewById(R.id.lebanon);
        mCommission = findViewById(R.id.commission);
    }
}