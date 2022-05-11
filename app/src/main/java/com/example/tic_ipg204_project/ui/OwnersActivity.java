package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.OwnersAdapter;
import com.example.tic_ipg204_project.common.model.Owner;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class OwnersActivity extends AppCompatActivity implements OwnersAdapter.OwnerInteraction {
    RecyclerView mrecycler;
    OwnersAdapter ownersAdapter;
    List<Owner> owners;
    MyDbAdapter myDbAdapter;
    LinearLayoutManager mlayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners);
        initialization();
    }
    public void initialization(){
        getSupportActionBar().setTitle("Owners");
        myDbAdapter = new MyDbAdapter(this);
        owners = new ArrayList<>();
        owners.addAll(myDbAdapter.getOwnersData());
        mrecycler = findViewById(R.id.recyclerView);
        mlayoutmanager=new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mlayoutmanager);
        ownersAdapter =new OwnersAdapter(OwnersActivity.this,owners,this);
        mrecycler.setAdapter(ownersAdapter);
    }
    @Override
    public void onClickItem(Owner owner , int postion) {
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
                        myDbAdapter.deleteOwner(String.valueOf(owners.get(postion).getOwnerId()));
                        owners.remove(postion);
                        ownersAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        startActivity(new Intent(OwnersActivity.this , OwnerUpdateActivity.class)
                                .putExtra("OWNER_ID",String.valueOf(owner.getOwnerId()))
                                .putExtra("OWNER_NAME" , owner.getOwnerName())
                                .putExtra("OWNER_DESCRIPTION",owner.getDescription()));
                        break;
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initialization();
    }
}