package com.example.myapplication2.ViewPages;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.ClassObject.ObjectUser;
import com.example.myapplication2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewProfile extends AppCompatActivity {
    TextView mName,mEmail,mPhone;
    ImageView mViewProfileImage;
    Button mInspectorButton;
    FirebaseUser fBase;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userType,userAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_activity);
        ObjectUser user= (ObjectUser) getIntent().getSerializableExtra("UserObject");
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mName=findViewById(R.id.viewprofilename);
        mEmail=findViewById(R.id.viewprofileemail);
        mPhone=findViewById(R.id.viewprofilephone);
        mViewProfileImage=findViewById(R.id.ViewProfileImage);
        mInspectorButton=findViewById(R.id.RemovalOptions);
        storageReference = FirebaseStorage.getInstance().getReference();
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        userType= fBase.getUid();
            StorageReference profileRef = storageReference.child("users/" + user.getUserKey() + "/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(mViewProfileImage);
                }
            });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/").child(user.getUserKey());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mPhone.setText(dataSnapshot.child("phone").getValue().toString());
                    mName.setText(dataSnapshot.child("fName").getValue().toString());
                    mEmail.setText(dataSnapshot.child("email").getValue().toString());
                    userAccess=dataSnapshot.child("type").getValue().toString();
                    if(!userAccess.equals("Inspector")){
                        mInspectorButton.setVisibility(View.GONE);
                    }

                }else {
                    //Log.d("tag", "onEvent: Document: "+UserID+" do not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/*        Log.d(user.getPhone()," DEBUGG");
        Log.d(user.getEmail()," DEBUGG");
        Log.d(user.getFullName()," DEBUGG");*/
    }
}
