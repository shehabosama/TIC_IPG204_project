package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

public class MaterialUpdateActivity extends AppCompatActivity {
    EditText editMaterialName,editMaterialDescription;
    Button createMaterialButton;
    CheckBox isService;
    MyDbAdapter myDbAdapter;
    boolean checkIsService;
    String materialId, materialDescription, materialNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_update);
        editMaterialName = findViewById(R.id.et_material_name);
        editMaterialDescription = findViewById(R.id.et_material_description);
        createMaterialButton =findViewById(R.id.btn_done);
        isService = findViewById(R.id.is_service);
        myDbAdapter = new MyDbAdapter(this);
        Intent intent = getIntent();
        if(intent != null){
            materialId = intent.getStringExtra("MATERIAL_ID");
            materialNam = intent.getStringExtra("MATERIAL_NAME");
            materialDescription = intent.getStringExtra("MATERIAL_DESCRIPTION");
            checkIsService =intent.getBooleanExtra("MATERIAL_IS_SERVICE",false);
            isService.setChecked(checkIsService);
            editMaterialName.setText(materialNam);
            editMaterialDescription.setText(materialDescription);
        }
        else{
            Toast.makeText(MaterialUpdateActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        isService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isService.setChecked(b);
                checkIsService = b;
            }
        });
        createMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=editMaterialName.getText().toString();
                String strDescription=editMaterialDescription.getText().toString();

                if(TextUtils.isEmpty(strName)||TextUtils.isEmpty(strDescription)){
                    Toast.makeText(MaterialUpdateActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }else{
                    if (myDbAdapter.updateMaterial(materialId,strName,strDescription,checkIsService) != -1) {
                        Toast.makeText(MaterialUpdateActivity.this, "Material Created Successfully", Toast.LENGTH_SHORT).show();
                        editMaterialName.setText("");
                        editMaterialDescription.setText("");

                    }else{
                        Toast.makeText(MaterialUpdateActivity.this, "there is something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}