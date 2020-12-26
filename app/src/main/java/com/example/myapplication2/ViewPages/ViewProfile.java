package com.example.myapplication2.ViewPages;

import android.content.Intent;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewProfile extends AppCompatActivity {
    TextView mName,mEmail,mPhone,namehint,phonehint,mailhint,NoUsersFound;
    ImageView mViewProfileImage;
    Button mInspectorButton;
    ObjectUser CurrUser;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_activity);
        FirebaseAuth fAuth=FirebaseAuth.getInstance();
        namehint=findViewById(R.id.textView11);
        mailhint=findViewById(R.id.textView12);
        phonehint=findViewById(R.id.textView13);
        mName=findViewById(R.id.viewprofilename);
        mEmail=findViewById(R.id.viewprofileemail);
        mPhone=findViewById(R.id.viewprofilephone);
        mViewProfileImage=findViewById(R.id.ViewProfileImage);
        mInspectorButton=findViewById(R.id.RemovalOptions);
        NoUsersFound=findViewById(R.id.NoUsersFound);
        NoUsersFound.setVisibility(View.GONE);
        Intent intent=getIntent();
        String Called=intent.getStringExtra("CALLED");
        if(Called.equals("Search")){
            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String EmailField=intent.getStringExtra("FreeSearch");
                    for (DataSnapshot childes : dataSnapshot.getChildren()) {
                        if(childes.child("email").getValue().equals(EmailField)){
                            UserID=childes.getKey();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(UserID==null){
                namehint.setVisibility(View.GONE);
                phonehint.setVisibility(View.GONE);
                mailhint.setVisibility(View.GONE);
                mInspectorButton.setVisibility(View.GONE);
                NoUsersFound.setVisibility(View.VISIBLE);
                return;
            }
        }
        else {
            UserID = intent.getStringExtra("UserObject");
        }

        FirebaseStorage.getInstance().getReference().child("users/" + UserID + "/profile.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(mViewProfileImage);
                }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrUser=new ObjectUser(
                        dataSnapshot.child(UserID).child("email").getValue().toString(),
                        dataSnapshot.child(UserID).child("fName").getValue().toString(),
                        dataSnapshot.child(UserID).child("phone").getValue().toString(),
                        dataSnapshot.child(UserID).child("type").getValue().toString());

                mPhone.setText(CurrUser.getPhone());
                mName.setText(CurrUser.getFullName());
                mEmail.setText(CurrUser.getEmail());
                if(!dataSnapshot.child(fAuth.getCurrentUser().getUid()).child("type").getValue().toString().equals("Inspector")){
                    mInspectorButton.setVisibility(View.GONE);
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
