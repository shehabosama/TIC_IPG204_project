package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.OutlaysAdapter;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class MaterialsServiceActivity extends AppCompatActivity implements OutlaysAdapter.OutlayInteraction {
    RecyclerView mrecycler;
    OutlaysAdapter ownersAdapter;
    List<OutLay> outLays;
    MyDbAdapter myDbAdapter;
    LinearLayoutManager mlayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_service);
        getSupportActionBar().setTitle("Outlays Not Service Materials");
        myDbAdapter = new MyDbAdapter(this);
        outLays = new ArrayList<>();
        outLays.addAll(myDbAdapter.getIsServiceMaterialOutlays(1));
        mrecycler = findViewById(R.id.recyclerView);
        mlayoutmanager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mlayoutmanager);
        ownersAdapter = new OutlaysAdapter(MaterialsServiceActivity.this, outLays, this);
        mrecycler.setAdapter(ownersAdapter);
        getSupportActionBar().setTitle("Outlay of this Service material: "+String.valueOf(calculateOutlays()));
    }

    public double calculateOutlays(){
        double sum = 0;
        for (OutLay outlay:outLays) {
            sum+=outlay.getPrice();
        }
        return sum;
    }

    @Override
    public void onClickItem(OutLay outLay, int position) {

    }
}