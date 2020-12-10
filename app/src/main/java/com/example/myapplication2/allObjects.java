package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class allObjects extends AppCompatActivity {
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    FirebaseUser user;

 //   ListView listView;
 //   ArrayList<objectData> arrayList = new ArrayList<>();
 //   MyAdapter adapter;
///////
    ArrayList<objectData> arrayList2 = new ArrayList<>();
    MyAdapter adapter;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String ObjectTitle;
    String Description;
    String ObjectType;
    int count;
    Spinner Happened_spinner;

    private  String actObject[]={"Mobile","Jewel","Clothing","Pet","Electronics","Car","Bike","Bag","Glasses","jewel"};
    private  String happend[]={"Lost","Found"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_show_form);


        listView = findViewById(R.id.listViewForm);
        FBDB= FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("forms");
        count=1;
        Happened_spinner = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> HappendAdapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(HappendAdapter);
        loop("Lost");


        Happened_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
               if(Happened_spinner.getSelectedItem().equals("Lost")){
                   loop("Lost");
               }
               else if(Happened_spinner.getSelectedItem().equals("Found")){
                   loop("Found");
                }
               else{
                   loop("Lost");
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });

//        listView = findViewById(R.id.listView);
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        fStore.collection("forms")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "blabla data!!!", Toast.LENGTH_LONG).show();
//                            int i=1;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                arrayList.add(new objectData(i+") item: "+ document.get("Category").toString(),"Category: "+ document.get("Category").toString() ,"Description: "+ document.get("Object Title").toString() ));
//                                 i++;
//
//                            }
//                        } else {
//                          //  Log.d("TAG", "Error getting documents: ", task.getException());
//                          Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
//
//                        }
//                        adapter = new MyAdapter(allObjects.this, arrayList);
//                        listView.setAdapter(adapter);
//                    }
//                });

//        arrayList.add(new objectData(12, " Niyaz","65757657657"));
//       // adapter = new MyAdapter(this, arrayList);
//        listView.setAdapter(adapter);
    }

    public void loop(String Look){
        for (String object : actObject) {
            databaseReference = FirebaseDatabase.getInstance().getReference("forms").child(Look).child(object);
            listView = (ListView) findViewById(R.id.listViewForm);
            arrayAdapter = new ArrayAdapter<String>(allObjects.this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    ObjectTitle = String.valueOf(dataSnapshot.child("Object Title").getValue());
                    Description = String.valueOf(dataSnapshot.child("description").getValue());
                    ObjectType = object;

                    arrayList2.add(new objectData(count + ") Object Title: " + ObjectTitle, " Object type: " + ObjectType, "Description: " + Description));
                    adapter = new MyAdapter(allObjects.this, arrayList2);
                    listView.setAdapter(adapter);
                    count++;
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}


