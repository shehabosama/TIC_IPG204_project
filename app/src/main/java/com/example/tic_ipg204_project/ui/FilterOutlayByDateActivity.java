package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.OutlaysAdapter;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.model.Owner;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterOutlayByDateActivity extends AppCompatActivity  {

    private Spinner spinnerMonths,spinnerYear;
    private RadioGroup radioGroup;
    private RadioButton radioMonth,radioYear;
    private Button searchBtn;
    private MyDbAdapter myDbAdapter;
    private int month=0,year=0;
    private List<OutLay> outLays;
    private RecyclerView mrecycler;
    private LinearLayoutManager mlayoutmanager;
    private OutlaysAdapter outlaysAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_outlay_by_date);
        spinnerMonths = findViewById(R.id.spinnerMonths);
        spinnerYear = findViewById(R.id.spinner_years);
        radioMonth = findViewById(R.id.search_by_month);
        radioYear = findViewById(R.id.search_by_year);
        myDbAdapter = new MyDbAdapter(this);
        spinnerYear.setEnabled(false);
        spinnerMonths.setEnabled(false);
        radioGroup = findViewById(R.id.radio_group);
        searchBtn = findViewById(R.id.search_btn);
        getMonths();
        getYears();
        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (Integer) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               year = (Integer) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {
                        switch (checkedId){
                            case R.id.search_by_month:
                                radioMonth.setVisibility(View.VISIBLE);
                                spinnerYear.setEnabled(false);
                                spinnerMonths.setEnabled(true);
                                break;
                            case R.id.search_by_year:
                                radioYear.setVisibility(View.VISIBLE);
                                spinnerYear.setEnabled(true);
                                spinnerMonths.setEnabled(false);
                                break;
                        }
                    }
                });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(FilterOutlayByDateActivity.this,
                            "No answer has been selected",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else if(selectedId == R.id.search_by_month) {

                    outLays = new ArrayList<>();
                    outLays.addAll( myDbAdapter.getOutlaysByMonth(month));
                    mrecycler = findViewById(R.id.recyclerView);
                    mlayoutmanager=new LinearLayoutManager(FilterOutlayByDateActivity.this);
                    mrecycler.setLayoutManager(mlayoutmanager);
                    outlaysAdapter =new OutlaysAdapter(FilterOutlayByDateActivity.this, outLays, new OutlaysAdapter.OutlayInteraction() {
                        @Override
                        public void onClickItem(OutLay outLay, int position) {

                        }
                    });
                    mrecycler.setAdapter(outlaysAdapter);
                    outlaysAdapter.notifyDataSetChanged();
                }   else if(selectedId == R.id.search_by_year){
                    outLays = new ArrayList<>();
                    outLays.addAll( myDbAdapter.getOutlaysByYear(year));
                    mrecycler = findViewById(R.id.recyclerView);
                    mlayoutmanager=new LinearLayoutManager(FilterOutlayByDateActivity.this);
                    mrecycler.setLayoutManager(mlayoutmanager);
                    outlaysAdapter =new OutlaysAdapter(FilterOutlayByDateActivity.this, outLays, new OutlaysAdapter.OutlayInteraction() {
                        @Override
                        public void onClickItem(OutLay outLay, int position) {

                        }
                    });
                    mrecycler.setAdapter(outlaysAdapter);
                    outlaysAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    public void getYears(){
        List<Integer> years = new ArrayList<>();
        for (int i=1990 ; i<= Calendar.getInstance().get(Calendar.YEAR); i++){
            years.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, years);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(dataAdapter);
    }
    public void getMonths(){
        List<Integer> months = new ArrayList<>();
        for (int i=1 ; i<= 12; i++){
            months.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, months);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerMonths.setAdapter(dataAdapter);
    }

}