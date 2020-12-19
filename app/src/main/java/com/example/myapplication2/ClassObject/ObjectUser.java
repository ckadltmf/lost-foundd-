package com.example.myapplication2.ClassObject;

import android.annotation.SuppressLint;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.io.Serializable;

public class ObjectUser implements Serializable {
     String UserKey;
     String email;
     String fullName;
     String phone;
     String UserType;

    public ObjectUser(){}

    public ObjectUser(String UserID){
        this.UserKey=UserID;
/*        FirebaseDatabase.getInstance().getReference("users/").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone = (dataSnapshot.child("phone").getValue().toString());
                    fullName = (dataSnapshot.child("fName").getValue().toString());
                    email = (dataSnapshot.child("email").getValue().toString());
                    UserType = dataSnapshot.child("type").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

/*    public ObjectUser(String UserID,String email,String fName,String phone,String UserType){
        this.UserKey= UserID;
        this.email=email;
        this.fullName=fName;
        this.phone=phone;
        this.UserType=UserType;
    }*/

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return UserType;
    }
    public String getUserKey() {
        return UserKey;
    }
}
