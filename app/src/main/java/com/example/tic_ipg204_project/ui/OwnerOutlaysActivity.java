package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class OwnerOutlaysActivity extends AppCompatActivity {
    private int ownerId;
    private List<OutLay> outLays;
    private MyDbAdapter myDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_outlays);
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



    }
    public double calculateOutlays(){
        int sum = 0;
        for (OutLay outlay:outLays) {
            sum+=outlay.getPrice();
        }
        return sum;
    }
}