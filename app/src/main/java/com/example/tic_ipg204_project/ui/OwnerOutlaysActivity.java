package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.OutlaysAdapter;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class OwnerOutlaysActivity extends AppCompatActivity implements OutlaysAdapter.OutlayInteraction {
    private int ownerId;
    private List<OutLay> outLays;
    private MyDbAdapter myDbAdapter;
    RecyclerView mrecycler;
    OutlaysAdapter outlaysAdapter;
    LinearLayoutManager mlayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_outlays);
        getSupportActionBar().setTitle("Owners");
        Intent intent = getIntent();
        if(intent != null){
            ownerId = intent.getIntExtra("OWNER_ID",0);
        }
        else{
            Toast.makeText(OwnerOutlaysActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        outLays = new ArrayList<>();
        myDbAdapter = new MyDbAdapter(this);
        outLays.addAll(myDbAdapter.getOwnerOutlays(ownerId));

        mrecycler = findViewById(R.id.recyclerView);
        mlayoutmanager=new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mlayoutmanager);
        outlaysAdapter =new OutlaysAdapter(OwnerOutlaysActivity.this, outLays,this);
        mrecycler.setAdapter(outlaysAdapter);
        getSupportActionBar().setTitle("The outlay of this owner is "+String.valueOf(calculateOutlays()));

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