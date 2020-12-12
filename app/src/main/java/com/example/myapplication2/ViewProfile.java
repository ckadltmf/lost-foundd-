package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class ViewProfile extends AppCompatActivity {
    TextView mName,mEmail,mPhone;
    ImageView mViewProfileImage;
    Button mInspectorButton;
    FirebaseUser fBase;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_activity);
        Intent intent=getIntent();
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        String UserID=intent.getStringExtra("USERID");
        mName=findViewById(R.id.viewprofilename);
        mEmail=findViewById(R.id.viewprofileemail);
        mPhone=findViewById(R.id.viewprofilephone);
        mViewProfileImage=findViewById(R.id.ViewProfileImage);
        mInspectorButton=findViewById(R.id.RemovalOptions);
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mViewProfileImage);
            }
        });
        DocumentReference documentReference = fStore.collection("users").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    mPhone.setText(documentSnapshot.getString("phone"));
                    mName.setText(documentSnapshot.getString("fName"));
                    mEmail.setText(documentSnapshot.getString("email"));

                }else {
                    Log.d("tag", "onEvent: Document: "+UserID+" do not exists");
                }
            }
        });
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String RegisteredUserID = currentUser.getUid();
        // storageReference = FirebaseStorage.getInstance().getReference().child("users").child(RegisteredUserID);
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        String userType= fBase.getDisplayName();
        assert userType != null;
        if(!userType.equals("Inspector")){
            mInspectorButton.setVisibility(View.GONE);
        }
    }
}
