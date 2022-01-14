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

public class AddRepresentativeActivity extends AppCompatActivity {

    Myappdatabas myappdatabas;

    ImageView mAddImageBtn;
    TextInputEditText mIdET;
    TextInputEditText mNameET;
    RadioGroup mMainAreaChooser;
    Button mSaveBtn;

    String chosenArea = null;

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_representative);

        myappdatabas = Myappdatabas.getDatabase(this);

        bindViews();

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



    private boolean validateUserInput() {

        //first getting the values
        final String idString = mIdET.getText().toString();
        int id = -1;
        final String name = mNameET.getText().toString();

        //checking if id is empty
        if (TextUtils.isEmpty(idString)) {
            Toast.makeText(this, "id field is required!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        try {
            id = Integer.parseInt(idString);
        }catch (NumberFormatException e){
            Toast.makeText(this, "id must be a valid number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        if(existed(id)){
            Toast.makeText(this, "this id is already existed, please try a new one", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

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
        int id = Integer.parseInt(mIdET.getText().toString());
        String name = mNameET.getText().toString();

        Representative representative = new Representative();
        representative.setId(id);
        representative.setName(name);
        representative.setMainArea(chosenArea);
        representative.setImageURL(imageUri.toString());

        myappdatabas.representativesDao().addRepresentative(representative);
        Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();

        mSaveBtn.setEnabled(true);

        finish();
    }
}