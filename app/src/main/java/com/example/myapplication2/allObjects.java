package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class allObjects extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    ListView listView;
    ArrayList<objectData> arrayList = new ArrayList<>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_objects);


        listView = findViewById(R.id.listView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("forms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "blabla data!!!", Toast.LENGTH_LONG).show();
                            int i=1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                arrayList.add(new objectData(i+") item: "+ document.get("Category").toString(),"Category: "+ document.get("Category").toString() ,"Description: "+ document.get("Object Title").toString() ));
                                 i++;

                            }
                        } else {
                          //  Log.d("TAG", "Error getting documents: ", task.getException());
                          Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();

                        }
                        adapter = new MyAdapter(allObjects.this, arrayList);
                        listView.setAdapter(adapter);
                    }
                });

//        arrayList.add(new objectData(12, " Niyaz","65757657657"));
//       // adapter = new MyAdapter(this, arrayList);
//        listView.setAdapter(adapter);
    }
}


