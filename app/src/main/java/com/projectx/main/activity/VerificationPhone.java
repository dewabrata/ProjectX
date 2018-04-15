package com.projectx.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.projectx.main.R;
import com.projectx.main.utils.Tools;


public class VerificationPhone extends AppCompatActivity {

    TextInputEditText txtPhone;
    AppCompatButton btnContinue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_phone);
        Tools.setSystemBarColor(this, R.color.grey_20);
        txtPhone = (TextInputEditText)findViewById(R.id.txtPhone);
        btnContinue = (AppCompatButton)findViewById(R.id.btnNextVerification);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtPhone.getText().toString().equalsIgnoreCase("")){
                    setPhone();
                }else{
                    Toast.makeText(getApplicationContext(), "Please Input Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void setPhone(){

        Intent intent  = new Intent(getApplicationContext(), VerificationHeader.class);
        intent.putExtra("phone",txtPhone.getText().toString());
        startActivity(intent);

    }
}
