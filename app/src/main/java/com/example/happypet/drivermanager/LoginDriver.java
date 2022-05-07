package com.example.happypet.drivermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.happypet.R;
import com.example.happypet.RegisterVetActivity;
import com.example.happypet.VetLoginActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class LoginDriver extends AppCompatActivity {

    private TextView driverRegisterBtn;

    private TextInputEditText driverLoginEmail,driverLoginPassword;

    private TextView driverForgetPassword;

    private Button driverLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_driver);

        driverRegisterBtn = findViewById(R.id.driverRegisterBtn);
        driverLoginBtn = findViewById(R.id.driverLoginBtn);
        driverLoginEmail = findViewById(R.id.driverLoginEmail);
        driverLoginPassword = findViewById(R.id.driverPassword);


        driverRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginDriver.this, RegisterDriverActivity.class);
                startActivity(intent);
            }
        });

        driverLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final String email = driverLoginEmail.getText().toString().trim();
                final String password = driverLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    driverLoginEmail.setError("Email cannot be empty!");
//                    driverLoginPassword.setError("Password cannot be empty!");
                    return;
                }
            }
        });

    }
}