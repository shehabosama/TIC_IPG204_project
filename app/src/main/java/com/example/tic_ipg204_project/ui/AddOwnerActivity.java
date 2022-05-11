package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

public class AddOwnerActivity extends AppCompatActivity {
    EditText editOwnerName, editOwnerDescription;
    Button createOwnerButton;
    MyDbAdapter myDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);
        editOwnerName = findViewById(R.id.et_owner_name);
        editOwnerDescription = findViewById(R.id.et_owner_description);
        createOwnerButton =findViewById(R.id.btn_done);
        createOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=editOwnerName.getText().toString();
                String strDescription=editOwnerDescription.getText().toString();

                if(TextUtils.isEmpty(strName)||TextUtils.isEmpty(strDescription)){
                    Toast.makeText(AddOwnerActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }else{
                    if (myDbAdapter.insertOwnerData(strName,strDescription) != -1) {
                        Toast.makeText(AddOwnerActivity.this, "Owner Added Successfully", Toast.LENGTH_SHORT).show();
                        editOwnerName.setText("");
                        editOwnerDescription.setText("");

                    }else{
                        Toast.makeText(AddOwnerActivity.this, "there is something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}