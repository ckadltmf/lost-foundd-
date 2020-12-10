package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyPosts extends AppCompatActivity {

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

    List<String> GENERATED_KEYS_PATH;
    List<String> GENERATED_KEYS_LIST;
    //FirebaseAuth fAuth;
    Spinner Happened_spinner;

    private  String actObject[]={"Mobile","Jewel","Clothing","Pet","Electronics","Car","Bike","Bag","Glasses","jewel"};
    private  String happend[]={"Lost","Found"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        //fAuth=FirebaseAuth.getInstance();
        GENERATED_KEYS_PATH = new LinkedList<>();
        GENERATED_KEYS_LIST = new LinkedList<>();
        listView = findViewById(R.id.listviewform);
        FBDB= FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("forms");
        count=1;
        arrayList2.clear();
        //userID= fAuth.getCurrentUser().getUid().toString();
        Happened_spinner = findViewById(R.id.spinner7);
        ArrayAdapter<CharSequence> HappendAdapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(HappendAdapter);
       // loop("Lost");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(GENERATEDKSYLIST.toString(),"TAG");
                //Log.d(String.valueOf((int)id),"TAG");
/*                Object o = prestListView.getItemAtPosition(position);
                prestationEco str = (prestationEco)o; //As you are using Default String Adapter*/
                AlertDialog.Builder alert = new AlertDialog.Builder(MyPosts.this);
                alert.setTitle("Choose Action");
                alert.setMessage("Please choose your wanted Action:");
                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FBDB.getReference(GENERATED_KEYS_PATH.get(position)+"/"+GENERATED_KEYS_LIST.get(position)).removeValue();
                        FirebaseStorage.getInstance().getReference("forms/"+GENERATED_KEYS_LIST.get(position)+"/ObjectIMG.jpg").delete();
                        count=1;
                        arrayList2.clear();
                        loop("Lost");
                    }
                });
                alert.show();
            }
        });
        Happened_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                count=1;
                arrayList2.clear();
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
            listView = (ListView) findViewById(R.id.listviewform);
            arrayAdapter = new ArrayAdapter<String>(MyPosts.this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            databaseReference.addChildEventListener(new ChildEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    ObjectTitle = String.valueOf(dataSnapshot.child("Object Title").getValue());
                    Description = String.valueOf(dataSnapshot.child("description").getValue());
                    String DB_UserID=String.valueOf(dataSnapshot.child("UserID").getValue());
                    //String y=String.valueOf(dataSnapshot.getKey());
                    ObjectType = object;
                    FirebaseUser FB_currUser=FirebaseAuth.getInstance().getCurrentUser();

                    if(FB_currUser.getUid().equals(DB_UserID)) {
                        //GENERATEDKSYLIST.add(dataSnapshot.getKey());
                        GENERATED_KEYS_PATH.add(FirebaseDatabase.getInstance().getReference("forms").child(Look).child(object).getPath().toString());
                        GENERATED_KEYS_LIST.add(dataSnapshot.getKey());
                        //here add if statement when applied to my posts
                        arrayList2.add(new objectData(count + ") Object Title: " + ObjectTitle, " Object type: " + ObjectType, "Description: " + Description));
                        adapter = new MyAdapter(MyPosts.this, arrayList2);
                        listView.setAdapter(adapter);
                        count++;
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //Log.d(dataSnapshot.getValue().toString(),"TAG");
                    //Log.d(dataSnapshot.getKey(),"TAG");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
