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

import com.example.myapplication2.ClassObject.ObjectForm;
import com.example.myapplication2.ClassObject.ObjectReport;
import com.example.myapplication2.ClassObject.ObjectUser;
import com.example.myapplication2.MainPages.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.ScrollView.ReportsScrollView;
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
    ObjectUser ReportingUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_activity);
        Intent intent=getIntent();
        fAuth=FirebaseAuth.getInstance();
        mName=findViewById(R.id.viewreportname);
        mEmail=findViewById(R.id.viewreportmail);
        mPhone=findViewById(R.id.viewreportphone);
        mDescription=findViewById(R.id.viewreportdescription);
        mViewReportImage=findViewById(R.id.reportImageView);
        mDeleteButton=findViewById(R.id.viewreportdeletebutton);
        mSubject=findViewById(R.id.viewreportsubjet);
        storageReference = FirebaseStorage.getInstance().getReference("report");
        ObjectReport report=(ObjectReport) intent.getSerializableExtra("ObjectReport");
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        userType= fBase.getUid();
        Log.d("report"+report.getGeneratedKey()+"/ReportIMG.jpg", " DEBUGGGGGGG");
        StorageReference profileRef = storageReference.child(report.getGeneratedKey()+"/ReportIMG.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mViewReportImage);
            }
        });
        mDescription.setText(report.getDescription());
        mSubject.setText(report.getReportType());
        useridDetails(report.getUserID());
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("report/").child(report.getReportType()).child(report.getGeneratedKey()).removeValue();
                FirebaseStorage.getInstance().getReference("report/"+report.getGeneratedKey()+"/ReportIMG.jpg").delete();
                startActivity(new Intent(getApplicationContext(), ReportsScrollView.class));
                return;
            }
        });
    }

    private void useridDetails(String userid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ReportingUser=new ObjectUser(dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("fName").getValue().toString(),
                            dataSnapshot.child("phone").getValue().toString(),
                            dataSnapshot.child("type").getValue().toString());
                    mName.setText(ReportingUser.getFullName());
                    mEmail.setText(ReportingUser.getEmail());
                    mPhone.setText(ReportingUser.getPhone());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
