package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

public class OwnerUpdateActivity extends AppCompatActivity {
    EditText editOwnerName, editOwnerDescription;
    Button createOwnerButton;
    MyDbAdapter myDbAdapter;
    String ownerId,ownerName,ownerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_update);
        getSupportActionBar().setTitle("Update Owner");
        Intent intent = getIntent();

        editOwnerName = findViewById(R.id.et_owner_name);
        editOwnerDescription = findViewById(R.id.et_owner_description);
        createOwnerButton =findViewById(R.id.btn_done);
        myDbAdapter = new MyDbAdapter(this);

        if(intent != null){
            ownerId = intent.getStringExtra("OWNER_ID");
            ownerName = intent.getStringExtra("OWNER_NAME");
            ownerDescription = intent.getStringExtra("OWNER_DESCRIPTION");
            editOwnerName.setText(ownerName);
            editOwnerDescription.setText(ownerDescription);
        }
        else{
            Toast.makeText(OwnerUpdateActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        createOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=editOwnerName.getText().toString();
                String strDescription=editOwnerDescription.getText().toString();

                if(TextUtils.isEmpty(strName)||TextUtils.isEmpty(strDescription)){
                    Toast.makeText(OwnerUpdateActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }else{
                    if (myDbAdapter.updateOwner(ownerId,strName,strDescription) != -1) {
                        Toast.makeText(OwnerUpdateActivity.this, "Owner Added Successfully", Toast.LENGTH_SHORT).show();
                        editOwnerName.setText("");
                        editOwnerDescription.setText("");
                    }else{
                        Toast.makeText(OwnerUpdateActivity.this, "there is something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}