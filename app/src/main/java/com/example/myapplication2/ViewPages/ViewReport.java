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

public class ViewReport extends AppCompatActivity {
    TextView mName,mEmail,mPhone,mDescription,mSubject;
    ImageView mViewReportImage;
    Button mDeleteButton;
    FirebaseUser fBase;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    String userType,userAccess;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_activity);
        Intent intent=getIntent();
        path=intent.getStringExtra("REPORTPATH");
        fAuth=FirebaseAuth.getInstance();
        mName=findViewById(R.id.viewreportname);
        mEmail=findViewById(R.id.viewreportmail);
        mPhone=findViewById(R.id.viewreportphone);
        mDescription=findViewById(R.id.viewreportdescription);
        mViewReportImage=findViewById(R.id.reportImageView);
        mDeleteButton=findViewById(R.id.viewreportdeletebutton);
        mSubject=findViewById(R.id.viewreportsubjet);
        storageReference = FirebaseStorage.getInstance().getReference();
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        userType= fBase.getUid();
        Log.d("HERRRR", "report/"+path+"/ReportIMG.jpg");
        StorageReference profileRef = storageReference.child("report"+path.substring(path.lastIndexOf('/'))+"/ReportIMG.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mViewReportImage);
            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("report").child(path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mDescription.setText(dataSnapshot.child("Description").getValue().toString());
                    mSubject.setText(databaseReference.getParent().getKey());
                    useridDetails(dataSnapshot.child("UserID").getValue().toString());

                }else {
                    Log.d("tag", "onEvent: Document: "+path+" do not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("report").child(path).removeValue();
                FirebaseStorage.getInstance().getReference("report/"+path.substring(path.lastIndexOf('/'))+"/ReportIMG.jpg").delete();
            }
        });
    }

    private void useridDetails(String userid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("HERRRR ", path);
                    mName.setText(dataSnapshot.child("fName").getValue().toString());
                    mEmail.setText(dataSnapshot.child("email").getValue().toString());
                    mPhone.setText(dataSnapshot.child("phone").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
