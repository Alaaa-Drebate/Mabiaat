package com.example.mabiaat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mabiaat.adapters.RepresentativesAdapter;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OurRepresentativesActivity extends AppCompatActivity {

    Toolbar toolbar;

    RepresentativesAdapter mAdapter;
    ArrayList<Representative> representatives;
    RecyclerView mList;

    FloatingActionButton addRepBtn;

    Myappdatabas myappdatabas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_representatives);

        myappdatabas = Myappdatabas.getDatabase(this);

        mList = findViewById(R.id.reps_list);
        addRepBtn = findViewById(R.id.add_rep);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        addRepBtn.setOnClickListener(v -> {
            Intent add = new Intent(this, AddRepresentativeActivity.class);
            startActivity(add);
        });

        representatives = (ArrayList<Representative>) myappdatabas.representativesDao().getAllRepresentatives();

        mAdapter = new RepresentativesAdapter(this, representatives);
        mList.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        representatives = (ArrayList<Representative>) myappdatabas.representativesDao().getAllRepresentatives();

        mAdapter = new RepresentativesAdapter(this, representatives);
        mList.setAdapter(mAdapter);
    }
}