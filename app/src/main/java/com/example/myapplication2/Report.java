package com.example.myapplication2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity {
    Button mSubmit;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    Spinner mSubject_spinner;
    TextView imageuploadtext;
    EditText mDescription;
    ImageView ObjectImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String DR;
    Uri imageUri;
    StorageReference fileRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FBDB=FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("report");
        setContentView(R.layout.activity_report);
        mSubject_spinner = findViewById(R.id.subject_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.reportsubject, R.layout.support_simple_spinner_dropdown_item);
        mSubject_spinner.setAdapter(adapter);
        mDescription = findViewById(R.id.full_description);
        ObjectImage = findViewById(R.id.ReportImageView);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mSubmit = findViewById(R.id.reportSubmit);
        imageuploadtext=findViewById(R.id.textView8);
        storageReference = FirebaseStorage.getInstance().getReference();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject_spinner=mSubject_spinner.getSelectedItem().toString();
                String description=mDescription.getText().toString().trim();
                String userID=fAuth.getCurrentUser().getUid();
                if(TextUtils.isEmpty(description)){
                    mDescription.setError("Place Fill is required");
                    return;
                }
                if(subject_spinner.equals("Choose Report Subject")){
                    ((TextView)mSubject_spinner.getSelectedView()).setError("");
                    return;
                }
                Map<String,Object> forms = new HashMap<>();
                forms.put("UserID",userID);
                //forms.put("Report Subject",subject_spinner);
                forms.put("Description",description);
                String x= DBRF.child(subject_spinner).push().getKey()+"";

                DBRF.child(subject_spinner).child(x).setValue(forms).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Report.this,"Your report added Successfully",Toast.LENGTH_SHORT).show();
                        if(ObjectImage!=null) {
                            Log.d(x,"TAG");
                            fileRef = storageReference.child("report/"+x+"/ReportIMG.jpg");
                            uploadImageToFirebase(imageUri);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            return;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Report.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                    }
                });
                /* fStore.collection("report").document("unchecked").collection(subject_spinner).add(forms).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Report.this,"Your Report added Successfully",Toast.LENGTH_SHORT).show();
                        if(ObjectImage!=null) {
                            DR=documentReference.getId();
                            uploadImageToFirebase(imageUri);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Report.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
        ObjectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                imageuploadtext.setHint("Image Uploaded!");
            }
        }

    }
    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        //final StorageReference fileRef = storageReference.child("report/"+DR+"/ReportIMG.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Picasso.get().load(uri).into(ObjectImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
