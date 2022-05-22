package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tic_ipg204_project.R;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private Button materialBtn , ownerBtn , outlayBtn , materialServiceBtn , materialNotServiceBtn , filterOutlayByDateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");
        materialBtn = findViewById(R.id.material_btn);
        ownerBtn = findViewById(R.id.owner_btn);
        outlayBtn = findViewById(R.id.outlay_btn);
        materialServiceBtn = findViewById(R.id.material_service_btn);
        materialNotServiceBtn = findViewById(R.id.material_not_service_btn);
        filterOutlayByDateBtn = findViewById(R.id.filter_outlay_by_date_btn);


        materialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MaterialsActivity.class));
            }
        });
        ownerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OwnersActivity.class));
            }
        });
        outlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OutlaysActivity.class));
            }
        });
        materialServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MaterialsServiceActivity.class));
            }
        });
        materialNotServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MaterialsNotServiceActivity.class));
            }
        });
        filterOutlayByDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FilterOutlayByDateActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_owner:
                startActivity(new Intent(MainActivity.this,AddOwnerActivity.class));
                break;
            case R.id.add_material:
                startActivity(new Intent(MainActivity.this,AddMaterialActivity.class));
                break;
            case R.id.add_outlay:
                startActivity(new Intent(MainActivity.this,AddOutlayActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}