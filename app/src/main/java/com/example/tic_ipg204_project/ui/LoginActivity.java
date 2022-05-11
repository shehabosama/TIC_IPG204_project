package com.example.tic_ipg204_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail,editPassword;
    Button createAccountButton , btnLogin;
    MyDbAdapter myDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.et_email);
        editPassword = findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_done);
        createAccountButton = findViewById(R.id.btn_create_account);
        myDbAdapter = new MyDbAdapter(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail=editEmail.getText().toString();
                String strPassword=editPassword.getText().toString();

                if(TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)){
                    Toast.makeText(LoginActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }else{
                    if (myDbAdapter.userIsExist(strEmail,strPassword)) {
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        editEmail.setText("");
                        editPassword.setText("");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "there's no account like this, create a new account first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UserRegistrationActivity.class));
            }
        });

    }
}