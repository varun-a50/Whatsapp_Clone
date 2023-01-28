package com.example.whatsappclone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivityNumberBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class NumberActivity extends AppCompatActivity {

    ActivityNumberBinding binding;

    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.PhoneBox.requestFocus();

        binding.ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.PhoneBox.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), OTP.class);
                    intent.putExtra("PhoneNumber", binding.PhoneBox.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}