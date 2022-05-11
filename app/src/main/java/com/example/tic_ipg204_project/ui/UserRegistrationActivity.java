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

public class UserRegistrationActivity extends AppCompatActivity {
    EditText editEmail,editPassword,editConfirmPassword;
    Button createAccountButton;
    MyDbAdapter myDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        editEmail = findViewById(R.id.et_email);
        editPassword = findViewById(R.id.et_password);
        editConfirmPassword = findViewById(R.id.et_conf_password);
        createAccountButton =findViewById(R.id.btn_done);
        myDbAdapter = new MyDbAdapter(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail=editEmail.getText().toString();
                String strPassword=editPassword.getText().toString();
                String strConfirmPassword=editConfirmPassword.getText().toString();
                if(TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)||TextUtils.isEmpty(strConfirmPassword)){
                    Toast.makeText(UserRegistrationActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }else if(!strPassword.equals(strConfirmPassword)) {
                    Toast.makeText(UserRegistrationActivity.this, "Please make sure that password is match with confirm password", Toast.LENGTH_SHORT).show();
                }else{
                    if (myDbAdapter.insertUserData(strEmail,strPassword) != -1) {
                        Toast.makeText(UserRegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        editEmail.setText("");
                        editPassword.setText("");
                        editConfirmPassword.setText("");
                    }else{
                        Toast.makeText(UserRegistrationActivity.this, "there is something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}