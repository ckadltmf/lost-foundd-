package com.example.myapplication2.ClassObject;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {

    private String email;
    private String fullName;
    private String phone;
    private String UserType;

    public User(){}

    public User(String GeneratedKey){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(GeneratedKey);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email=dataSnapshot.child("email").getValue().toString();
                fullName=dataSnapshot.child("fName").getValue().toString();
                phone=dataSnapshot.child("phone").getValue().toString();
                UserType=dataSnapshot.child("type").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
}
