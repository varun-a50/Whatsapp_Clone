package com.example.whatsappclone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivityOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    ActivityOtpBinding binding;
    FirebaseAuth Auth;

    String verificationId;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Auth = FirebaseAuth.getInstance();

        String PhoneNumber = "+91" + getIntent().getStringExtra("PhoneNumber");
        binding.Number.setText("Verify " + PhoneNumber);

        Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar())).hide();

        binding.otp.requestFocus();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(Auth)
                        .setPhoneNumber(PhoneNumber)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                final String code = phoneAuthCredential.getSmsCode();

                                if (code != null) {

                                    binding.otp.setText(code);


                                    verifyCode(code);

                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();


                            }
                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);

                                verificationId = s;
                            }
                        })		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);




        binding.Continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(binding.otp.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(binding.otp.getText().toString());
                }
            }
        });
    }




    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        Auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    Intent i = new Intent(getApplicationContext(), SetupProfileActivity.class);
                    startActivity(i);
                    finishAffinity();
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}