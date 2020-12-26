package com.example.myapplication2.MainPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword,mPhone,mPasswordConfirm;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String userID;
    Spinner _spinner;
    FirebaseUser updateUser;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPasswordConfirm = findViewById(R.id.PasswordConfirm);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.LoginBtn);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn =  findViewById(R.id.createText);

        FBDB=FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("users");



        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();

                String PasswordConfirm=mPasswordConfirm.getText().toString().trim();

                final String phone    = mPhone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be at least 6 characters");
                    return;
                }
                if(!PasswordConfirm.equals(password)){
                    mPasswordConfirm.setError("The passwords should be the same");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Register the user to firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this,"user Created.", Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid();
                            updateUser= fAuth.getCurrentUser();
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("type","user");

                            DBRF.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Your object added Successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else{
                            Toast.makeText(Register.this,"Error ! "+ task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }
}