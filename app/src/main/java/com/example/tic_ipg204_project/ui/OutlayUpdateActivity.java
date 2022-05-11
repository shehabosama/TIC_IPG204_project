package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.SpinMaterialAdapter;
import com.example.tic_ipg204_project.adapters.SpinOwnerAdapter;
import com.example.tic_ipg204_project.common.model.Material;
import com.example.tic_ipg204_project.common.model.Owner;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OutlayUpdateActivity extends AppCompatActivity {
    Button date_btn , btn_done;
    private Calendar c;
    List<Material> materials;
    List<Owner> owners;
    MyDbAdapter myDbAdapter;
    private EditText editTextPrice , editTextDescription;
    private SpinMaterialAdapter spinMaterialAdapter;
    private SpinOwnerAdapter spinOwnerAdapter;
    private int materialId , ownerId;
    private String yourDateString;
    private String outlayId , outlayPrice , outlayDescription , outlayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlay_update);
        myDbAdapter = new MyDbAdapter(this);
        date_btn = findViewById(R.id.butto_date);
        editTextPrice = findViewById(R.id.edit_price);
        editTextDescription = findViewById(R.id.edit_description);
        btn_done = findViewById(R.id.btn_done);
        c = Calendar.getInstance();
        Intent intent = getIntent();
        if(intent != null){
            outlayId = intent.getStringExtra("OUTLAY_ID");
            outlayPrice = intent.getStringExtra("OUTLAY_PRICE");
            outlayDescription = intent.getStringExtra("OUTLAY_DESCRIPTION");
            outlayDate = intent.getStringExtra("OUTLAY_DATE");
            date_btn.setText(outlayDate);
            editTextDescription.setText(outlayDescription);

            editTextPrice.setText(outlayPrice);
        }
        else{
            Toast.makeText(OutlayUpdateActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        materials = myDbAdapter.getMaterialsData();
        spinMaterialAdapter = new SpinMaterialAdapter(OutlayUpdateActivity.this,
                android.R.layout.simple_spinner_item,
                materials);
        Spinner materialSpinner =  findViewById(R.id.spinner);
        materialSpinner.setAdapter(spinMaterialAdapter);
        materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Material material = (Material) parent.getSelectedItem();
                materialId = material.getMaterialId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        owners = myDbAdapter.getOwnersData();
        spinOwnerAdapter = new SpinOwnerAdapter(OutlayUpdateActivity.this,
                android.R.layout.simple_spinner_item,
                owners);
        Spinner ownerSpinner =  findViewById(R.id.spinnerOwner);
        ownerSpinner.setAdapter(spinOwnerAdapter);
        ownerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Owner owner = (Owner) parent.getSelectedItem();
                ownerId = owner.getOwnerId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        date_btn.setOnClickListener(new View.OnClickListener() {
            public void setReturnDate(int year, int month, int day) {
                datePicked(year, month, day);
            }
            @Override
            public void onClick(View view) {
                Dialog datePickerDialog = new DatePickerDialog(OutlayUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        setReturnDate(((DatePickerDialog) dialog).getDatePicker().getYear(),
                                ((DatePickerDialog) dialog).getDatePicker().getMonth(), ((DatePickerDialog) dialog)
                                        .getDatePicker().getDayOfMonth());
                    }
                });

                datePickerDialog.show();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialId >0 && ownerId >0 &&
                        !TextUtils.isEmpty(editTextPrice.getText().toString()) &&
                        !TextUtils.isEmpty(editTextDescription.getText().toString())&&
                        !TextUtils.isEmpty(date_btn.getText().toString()))  {

                    if ( myDbAdapter.updateOutlay(outlayId,materialId,ownerId,editTextPrice.getText().toString(),date_btn.getText().toString() , editTextDescription.getText().toString()) != -1){
                        Toast.makeText(OutlayUpdateActivity.this, "Outlay Updated Successfully", Toast.LENGTH_SHORT).show();
                        editTextDescription.setText("");
                        editTextPrice.setText("");
                    }else{
                        Toast.makeText(OutlayUpdateActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(OutlayUpdateActivity.this, "please make sure that all field is filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void datePicked(int year, int month, int day) {
        yourDateString = String.valueOf(month) + "/" +String.valueOf(day) + "/" +String.valueOf(year);
        SimpleDateFormat yourDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date yourDate = yourDateFormat.parse(yourDateString);
            SimpleDateFormat formatNowDay = new SimpleDateFormat("dd");
            SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatNowYear = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                formatNowYear = new SimpleDateFormat("yyyy");
            }
            String currentDay = formatNowDay.format(yourDate);
            String currentMonth = formatNowMonth.format(yourDate);
            String currentYear = formatNowYear.format(yourDate);
            Toast.makeText(this, currentDay+" "+ currentMonth+" "+ currentYear, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date_btn.setText(yourDateString);
    }

}