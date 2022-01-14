package com.example.mabiaat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mabiaat.offlinedata.Constants;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class UpdateRepresentativeData extends AppCompatActivity {

    Myappdatabas myappdatabas;

    ImageView mAddImageBtn;
    TextInputEditText mIdET;
    TextInputEditText mNameET;
    RadioGroup mMainAreaChooser;
    Button mSaveBtn;

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;

    int id;
    String name;
    String chosenArea;

    Representative representative;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_representative_data);

        myappdatabas = Myappdatabas.getDatabase(this);

        Intent sender = getIntent();
        if(sender != null){
            id = sender.getIntExtra("id", -1);
            name = sender.getStringExtra("name");
            chosenArea = sender.getStringExtra("area");
            imageUri = Uri.parse(sender.getStringExtra("image"));
            representative = new Representative();
            representative.setId(id);
            representative.setImageURL(imageUri.toString());
            representative.setMainArea(chosenArea);
            representative.setName(name);
        }

        bindViews();

        updateUi();

        mMainAreaChooser.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.north:
                    chosenArea = Constants.NORTH_AREA;
                    break;
                case R.id.south:
                    chosenArea = Constants.SOUTH_AREA;
                    break;
                case R.id.east:
                    chosenArea = Constants.EAST_AREA;
                    break;
                case R.id.west:
                    chosenArea = Constants.WEST_AREA;
                    break;
                case R.id.lebanon:
                    chosenArea = Constants.LEBANON;
                    break;

            }
        });

        mAddImageBtn.setOnClickListener(v -> {
            openFileChooser();
        });


        mSaveBtn.setOnClickListener(v -> {
            mSaveBtn.setEnabled(false);
            if(validateUserInput()){
                storeNewRepresentative();
            }
        });


    }



    private void bindViews() {
        mAddImageBtn = findViewById(R.id.image);
        mIdET = findViewById(R.id.rep_id);
        mNameET = findViewById(R.id.name);
        mMainAreaChooser = findViewById(R.id.rg_chooser);
        mSaveBtn = findViewById(R.id.save);
    }

    private void updateUi() {

        mIdET.setText(String.valueOf(id));
        mIdET.setEnabled(false);
        mNameET.setText(String.valueOf(name));
        switch (chosenArea){
            case Constants.NORTH_AREA:
                mMainAreaChooser.check(R.id.north);
                break;
            case Constants.SOUTH_AREA:
                mMainAreaChooser.check(R.id.south);
                break;
            case Constants.EAST_AREA:
                mMainAreaChooser.check(R.id.east);
                break;
            case Constants.WEST_AREA:
                mMainAreaChooser.check(R.id.west);
                break;
            case Constants.LEBANON:
                mMainAreaChooser.check(R.id.lebanon);
                break;
        }

        Glide.with(this)
                .load(imageUri)
                .into(mAddImageBtn);
    }


    private boolean validateUserInput() {

        //first getting the values
        final String name = mNameET.getText().toString();

        //checking if name is empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name field is required!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if(chosenArea == null){
            Toast.makeText(this, "main area is required !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if(imageUri == null){
            Toast.makeText(this, "profile image is required", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }


        return true;

    }

    private boolean existed(int id) {
        List<Integer> ids = myappdatabas.representativesDao().getIds();
        for (int i : ids){
            if(i == id)
                return true;
        }
        return false;
    }

    //..................Methods for File Chooser.................
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .into(mAddImageBtn);

            Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    //..............................................................................

    private void storeNewRepresentative() {
        String name = mNameET.getText().toString();

        representative.setName(name);
        representative.setMainArea(chosenArea);
        representative.setImageURL(imageUri.toString());

        myappdatabas.representativesDao().addRepresentative(representative);
        Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();

        mSaveBtn.setEnabled(true);

        finish();
    }
}