package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.adapters.MaterialsAdapter;
import com.example.tic_ipg204_project.adapters.OwnersAdapter;
import com.example.tic_ipg204_project.common.model.Material;
import com.example.tic_ipg204_project.common.model.Owner;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class MaterialsActivity extends AppCompatActivity implements MaterialsAdapter.MaterialInteraction {
    RecyclerView mrecycler;
    MaterialsAdapter ownersAdapter;
    List<Material> materials;
    MyDbAdapter myDbAdapter;
    LinearLayoutManager mlayoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);

    }

    public void initialization() {
        getSupportActionBar().setTitle("Owners");
        myDbAdapter = new MyDbAdapter(this);
        materials = new ArrayList<>();
        materials.addAll(myDbAdapter.getMaterialsData());
        mrecycler = findViewById(R.id.recyclerView);
        mlayoutmanager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mlayoutmanager);
        ownersAdapter = new MaterialsAdapter(MaterialsActivity.this, materials, this);
        mrecycler.setAdapter(ownersAdapter);
    }

    @Override
    public void onClickItem(Material material, int postion) {
        final CharSequence option[] = new CharSequence[]{
                "Delete", "Update"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        myDbAdapter.deleteMaterial(String.valueOf(materials.get(postion).getMaterialId()));
                        materials.remove(postion);
                        ownersAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        startActivity(new Intent(MaterialsActivity.this, MaterialUpdateActivity.class)
                                .putExtra("MATERIAL_ID", String.valueOf(material.getMaterialId()))
                                .putExtra("MATERIAL_NAME", material.getMaterialName())
                                .putExtra("MATERIAL_DESCRIPTION", material.getDescription()).putExtra("MATERIAL_IS_SERVICE",material.isService()));
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