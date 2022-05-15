package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.OutlaysAdapter;
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class OutlaysActivity extends AppCompatActivity implements OutlaysAdapter.OutlayInteraction {
    RecyclerView mrecycler;
    OutlaysAdapter outlaysAdapter;
    List<OutLay> outLays;
    MyDbAdapter myDbAdapter;
    LinearLayoutManager mlayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlays);
    }

    public void initialization(){
        getSupportActionBar().setTitle("Outlays");
        myDbAdapter = new MyDbAdapter(this);
        outLays = new ArrayList<>();
        outLays.addAll(myDbAdapter.getOutlayData());
        mrecycler = findViewById(R.id.recyclerView);
        mlayoutmanager=new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mlayoutmanager);
        outlaysAdapter =new OutlaysAdapter(OutlaysActivity.this, outLays,this);
        mrecycler.setAdapter(outlaysAdapter);
        getSupportActionBar().setTitle("The sum of outlays:"+String.valueOf(calculateOutlays()));
    }
    @Override
    public void onClickItem(OutLay outLay , int postion) {
        final CharSequence option[]=new CharSequence[]{
                "Delete","Update"
        };
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        myDbAdapter.deleteOutlay(String.valueOf(outLays.get(postion).getId()));
                        outLays.remove(postion);
                        outlaysAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        startActivity(new Intent(OutlaysActivity.this , OutlayUpdateActivity.class)
                                .putExtra("OUTLAY_ID",String.valueOf(outLay.getId()))
                                .putExtra("OUTLAY_PRICE" , String.valueOf(outLay.getPrice()))
                                .putExtra("OUTLAY_DESCRIPTION",outLay.getDescription())
                                .putExtra("OUTLAY_DATE",outLay.getDate()));
                        break;
                }
            }
        });
        builder.show();
    }
    public double calculateOutlays(){
        double sum = 0;
        for (OutLay outlay:outLays) {
            sum+=outlay.getPrice();
        }
        return sum;
    }
    @Override
    protected void onStart() {
        super.onStart();
        initialization();
    }
}