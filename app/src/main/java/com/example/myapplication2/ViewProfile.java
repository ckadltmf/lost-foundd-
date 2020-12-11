package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewProfile extends AppCompatActivity {
    TextView mName,mEmail,mPhone;
    ImageView mViewProfileImage;
    Button mInspectorButton;
    FirebaseUser fBase;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_activity);
        Intent intent=getIntent();
        fAuth=FirebaseAuth.getInstance();
        mName=findViewById(R.id.viewprofilename);
        mEmail=findViewById(R.id.viewprofileemail);
        mPhone=findViewById(R.id.viewprofilephone);
        mViewProfileImage=findViewById(R.id.ViewProfileImage);
        mInspectorButton=findViewById(R.id.RemovalOptions);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String RegisteredUserID = currentUser.getUid();
        // storageReference = FirebaseStorage.getInstance().getReference().child("users").child(RegisteredUserID);
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        String userType= fBase.getDisplayName();
        assert userType != null;
        if(userType.equals("Inspector")){
            String UserID=intent.getStringExtra("USERID");

        }
        else{
            mInspectorButton.setVisibility(View.GONE);
        }
    }
}
