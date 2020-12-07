package com.example.myapplication2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity  {
    public static final String TAG = "TAG";
    EditText mObject;
    Button mSubmit;
    FirebaseFirestore fStore;
    Spinner Happened_spinner, Category_spinner;
    //FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //Intent data = getIntent();
        Happened_spinner = /*(Spinner)*/findViewById(R.id.spinner1);
        Category_spinner = /*(Spinner)*/findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this, R.array.Category, R.layout.support_simple_spinner_dropdown_item);
        Category_spinner.setAdapter(adapter2);
        mObject = findViewById(R.id.ObjectName);
        mSubmit = findViewById(R.id.submit);
        fStore = FirebaseFirestore.getInstance();
        //fAuth = FirebaseAuth.getInstance();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String happened= Happened_spinner.getSelectedItem().toString();
                String category= Category_spinner.getSelectedItem().toString();
                String object=mObject.getText().toString().trim();
                //String fullName = mFullName.getText().toString();
                //final String phone = mPhone.getText().toString();
                //String email=mEmail.getText().toString().trim();

/*                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Fill is required");
                    return;
                }

                if(TextUtils.isEmpty(object)){
                    mFullName.setError("Name Fill is required");
                    return;
                }
                if(TextUtils.isEmpty(object)){
                    mPhone.setError("Phone Fill is required");
                    return;
                }
                */
                if(TextUtils.isEmpty(object)){
                    mObject.setError("Object Fill is required");
                    return;
                }
                if(happened.equals("Choose Lost or Found")){
                    ((TextView)Happened_spinner.getSelectedView()).setError("");
                    //Happened_spinner.setError("Please choose Lost *or* Found");
                    return;
                }
                if(category.equals("Choose Category")){
                    ((TextView)Category_spinner.getSelectedView()).setError("");
                    //Category_spinner.setError("Category choosing is required");
                    return;
                }
                            Map<String,Object> forms = new HashMap<>();
                            //forms.put("fName",fullName);
                            //forms.put("email",email);
                            //forms.put("phone",phone);
                            forms.put("object",object);
                            fStore.collection("forms").add(forms).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Form.this,"Your object added Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Form.this,"Failure in adding content, please try again!",Toast.LENGTH_SHORT).show();
                                }
                            });

            }

        });
/*        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    mPhone.setText(documentSnapshot.getString("phone"));
                    mFullName.setText(documentSnapshot.getString("fName"));
                    mEmail.setText(documentSnapshot.getString("email"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });*/
    }
}
