package com.example.myapplication2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Form extends AppCompatActivity  {
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mObject,mPhone;
    Button mSubmit;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent data = getIntent();

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.EMail);
        mObject = findViewById(R.id.ObjectName);
        mPhone = findViewById(R.id.Phone);
        mSubmit = findViewById(R.id.submit);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String object=mObject.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Fill is required");
                    return;
                }
                if(TextUtils.isEmpty(object)){
                    mObject.setError("Object Fill is required");
                    return;
                }
                if(TextUtils.isEmpty(object)){
                    mFullName.setError("Name Fill is required");
                    return;
                }
                if(TextUtils.isEmpty(object)){
                    mPhone.setError("Phone Fill is required");
                    return;
                }
                            Map<String,Object> forms = new HashMap<>();
                            forms.put("fName",fullName);
                            forms.put("email",email);
                            forms.put("phone",phone);
                            forms.put("object",object);
                            fStore.collection("forms").add(forms).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Form.this,"Your object added Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Form.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                                }
                            });

            }

        });
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    mPhone.setText(documentSnapshot.getString("phone"));
                    mFullName.setText(documentSnapshot.getString("fName"));
                    mEmail.setText(documentSnapshot.getString("email"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }
}
