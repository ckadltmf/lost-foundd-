package com.example.myapplication2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity  {
    public static final String TAG = "TAG";
    EditText mObject, mDescription,mPlace;
    Button mSubmit;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    Spinner Happened_spinner, Category_spinner;
    ImageView ObjectImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    Uri imageUri;
    String x,y;
    StorageReference fileRef;
    TextView mDate, imageuploadtext;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent intent=getIntent();
        x=intent.getStringExtra("PATH");
        y=intent.getStringExtra("KEY");
        FBDB=FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("forms");
        storageReference = FirebaseStorage.getInstance().getReference();
        Happened_spinner = findViewById(R.id.spinner1);
        Category_spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this, R.array.Category, R.layout.support_simple_spinner_dropdown_item);
        Category_spinner.setAdapter(adapter2);
        mObject = findViewById(R.id.ObjectName);
        mPlace=findViewById(R.id.place);
        mDescription=findViewById(R.id.description);
        mSubmit = findViewById(R.id.submit);
        ObjectImage = findViewById(R.id.ObjectImageView);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        imageuploadtext=findViewById(R.id.textView7);
        mDate = (TextView) findViewById(R.id.date);
        mDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Form.this, mDateSetListener, year,month,day);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDate.setText(date);
            }
        };
        if(x!=null){
            //Log.d("x is:", x);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(x);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mObject.setText(String.valueOf(dataSnapshot.child("Object Title").getValue()));
                    mDate.setText(String.valueOf(dataSnapshot.child("date").getValue()));
                    mDescription.setText(String.valueOf(dataSnapshot.child("description").getValue()));
                    mPlace.setText(String.valueOf(dataSnapshot.child("place").getValue()));
                    int lostfound=adapter.getPosition(String.valueOf(databaseReference.getParent().getParent().getKey()));
                    int categ=adapter2.getPosition(String.valueOf(databaseReference.getParent().getKey()));
/*                    Log.d(String.valueOf(databaseReference.getParent().getParent()), String.valueOf(databaseReference.getParent()));
                    Log.d("Happened_spinner: " + lostfound,"category_spinner: " + categ);*/
                    Happened_spinner.setSelection(lostfound);
                    Category_spinner.setSelection(categ);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String object = mObject.getText().toString().trim();
                String happened = Happened_spinner.getSelectedItem().toString();
                String category = Category_spinner.getSelectedItem().toString();
                String userID = fAuth.getCurrentUser().getUid();
                String place = mPlace.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                String date = mDate.getText().toString().trim();
                if (TextUtils.isEmpty(object)) {
                    mObject.setError("Object Title is required");
                    return;
                }
                if (TextUtils.isEmpty(place)) {
                    mPlace.setError("Place Fill is required");
                    return;
                }
                if (happened.equals("Choose Lost or Found")) {
                    ((TextView) Happened_spinner.getSelectedView()).setError("");
                    return;
                }
                if (category.equals("Choose Category")) {
                    ((TextView) Category_spinner.getSelectedView()).setError("");
                    return;
                }
                if(x==null) {
                    x = DBRF.child(happened).child(category).push().getKey() + "";
                }
                else{
                    x=y;
                }
                Map<String, Object> forms = new HashMap<>();
                forms.put("UserID", userID);
                forms.put("Object Title", object);
                //forms.put("Lost or Found",happened);
                //forms.put("Category",category);
                forms.put("place", place);
                forms.put("description", description);
                forms.put("date", date);
                //forms.put("Generated Key",x);
                //DBRF.child(happened).child(category).push().setValue(forms);
                //fStore.collection("forms").document(happened).collection(category).add(forms).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                    DBRF.child(happened).child(category).child(x).setValue(forms).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Form.this, "Your object added Successfully", Toast.LENGTH_SHORT).show();
                            if (ObjectImage != null) {
                                fileRef = storageReference.child("forms/" + x + "/ObjectIMG.jpg");
                                uploadImageToFirebase(imageUri);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                return;

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Form.this, "Failure in adding content, please try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
/*                    DBRF.child(x).setValue(forms).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Form.this, "Your object updated Successfully", Toast.LENGTH_SHORT).show();
                            if (ObjectImage != null) {
                                fileRef = storageReference.child("forms/" + x + "/ObjectIMG.jpg");
                                uploadImageToFirebase(imageUri);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                return;

                            }
                        }
                    });*/

/*                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Form.this,"Your object added Successfully",Toast.LENGTH_SHORT).show();
                                    if(ObjectImage!=null) {
                                        fileRef = storageReference.child("forms/"+documentReference.getId()+"/ObjectIMG.jpg");
                                        uploadImageToFirebase(imageUri);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Form.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                                }
                            });*/
            }

        });
        ObjectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
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
        //final StorageReference fileRef = storageReference.child("forms/"+DR+"/ObjectIMG.jpg");
        if(imageUri!=null) {
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
}
