package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewPost extends AppCompatActivity {
    TextView mObjectTitle,mLostFound,mCategory,mPlace,mDate,mDescription,mStatus;
    ImageView ViewPostImage;
    StorageReference storageReference;
    String PostPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Intent intent = getIntent();
        mObjectTitle=findViewById(R.id.viewpostobject);
        mLostFound=findViewById(R.id.viewpostlostfound);
        mCategory=findViewById(R.id.viewpostcategory);
        mPlace=findViewById(R.id.viewpostplace);
        mDate=findViewById(R.id.viewpostdate);
        mDescription=findViewById(R.id.viewpostdescription);
        mStatus=findViewById(R.id.viewpoststatus);
        ViewPostImage=findViewById(R.id.PostimageView);
        PostPath=intent.getStringExtra("POST");
        storageReference = FirebaseStorage.getInstance().getReference();
        Log.d("forms/"+PostPath.substring(PostPath.lastIndexOf('/'))+"/ReportIMG.jpg", "HERRRRRRRRRRRRRR");
        StorageReference profileRef = storageReference.child("forms"+PostPath.substring(PostPath.lastIndexOf('/'))+"/ObjectIMG.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ViewPostImage);
            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(PostPath);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mDescription.setText(dataSnapshot.child("description").getValue().toString());
                    mDate.setText(dataSnapshot.child("date").getValue().toString());
                    mObjectTitle.setText(dataSnapshot.child("Object Title").getValue().toString());
                    mPlace.setText(dataSnapshot.child("place").getValue().toString());
                    mCategory.setText(databaseReference.getParent().getKey());
                    mLostFound.setText(databaseReference.getParent().getParent().getKey());
                    mStatus.setText(dataSnapshot.child("status").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
