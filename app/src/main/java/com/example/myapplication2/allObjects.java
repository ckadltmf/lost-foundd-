package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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

public class allObjects extends AppCompatActivity {
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    FirebaseUser user;

 //   ListView listView;
 //   ArrayList<objectData> arrayList = new ArrayList<>();
 //   MyAdapter adapter;
///////
  //  ArrayList<objectData> arrayList2 = new ArrayList<>();
   // MyAdapter adapter;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String ObjectTitle;
    String Description;
    String ObjectType;
    //String UserID;
    int count;
    FirebaseAuth fAuth;
    FirebaseUser fBase;
    List<String> GENERATED_KEYS_PATH;
    List<String> GENERATED_KEYS_LIST;
    List<String> GENERATED_KEYS_USERID;
    Spinner Happened_spinner;

    private  String actObject[]={"Mobile","Jewel","Clothing","Pet","Electronics","Car","Bike","Bag","Glasses","jewel"};
    private  String happend[]={"Lost","Found"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_new_show_form);
        GENERATED_KEYS_PATH = new LinkedList<>();
        GENERATED_KEYS_LIST = new LinkedList<>();
        GENERATED_KEYS_USERID=new LinkedList<>();
        listView = findViewById(R.id.listViewForm2);
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2);

        //listView.setAdapter(arrayAdapter);
        FBDB= FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("forms");
        count=1;
        Happened_spinner = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> HappendAdapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(HappendAdapter);
        //loop("Lost");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(GENERATEDKSYLIST.toString(),"TAG");
                //Log.d(String.valueOf((int)id),"TAG");
/*                Object o = prestListView.getItemAtPosition(position);
                prestationEco str = (prestationEco)o; //As you are using Default String Adapter*/
                AlertDialog.Builder alert = new AlertDialog.Builder(allObjects.this);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currentUser != null;
                String RegisteredUserID = currentUser.getUid();
                // storageReference = FirebaseStorage.getInstance().getReference().child("users").child(RegisteredUserID);
                fBase = fAuth.getCurrentUser();
                assert fBase != null;
                String userType= fBase.getDisplayName();
                assert userType != null;
                if(userType.equals("Inspector")){
                    alert.setTitle("Choose Action");
                    alert.setMessage("Please choose your wanted Action:");
                    alert.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(allObjects.this,ViewProfile.class);
                            intent.putExtra("USERID",GENERATED_KEYS_USERID.get(position));
                            startActivity(intent);
                            //intent.putExtra("PATH",DBRF.get((GENERATED_KEYS_PATH.get(position))).child("UserID").toString());
                            //(getApplicationContext(),ViewProfile.class)
                            //startActivity(new Intent(getApplicationContext(),ViewProfile.class,GENERATED_KEYS_PATH.toString()));
                        }
                    });
                    alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FBDB.getReference(GENERATED_KEYS_PATH.get(position) + "/" + GENERATED_KEYS_LIST.get(position)).removeValue();
                            FirebaseStorage.getInstance().getReference("forms/" + GENERATED_KEYS_LIST.get(position) + "/ObjectIMG.jpg").delete();
                            GENERATED_KEYS_PATH = new LinkedList<>();
                            GENERATED_KEYS_LIST = new LinkedList<>();
                            GENERATED_KEYS_USERID=new LinkedList<>();
                            count = 1;
                           // arrayList2.clear();
                            arrayAdapter.clear();
                            loop("Lost");
                        }
                    });
                }
                else{
                    //Log.d(,"TAG");
                    Intent intent=new Intent(allObjects.this,ViewProfile.class);
                    intent.putExtra("USERID",GENERATED_KEYS_USERID.get(position));
                    startActivity(intent);
                    //TODO: finish View profile intent by Clicking on Form
                }
                alert.show();
            }
        });

        Happened_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                count=1;
              //  arrayList2.clear();
                arrayAdapter.clear();
               if(Happened_spinner.getSelectedItem().equals("Lost")){
                   GENERATED_KEYS_PATH = new LinkedList<>();
                   GENERATED_KEYS_LIST = new LinkedList<>();
                   GENERATED_KEYS_USERID=new LinkedList<>();
                   loop("Lost");
               }
               else if(Happened_spinner.getSelectedItem().equals("Found")){
                   GENERATED_KEYS_PATH = new LinkedList<>();
                   GENERATED_KEYS_LIST = new LinkedList<>();
                   GENERATED_KEYS_USERID=new LinkedList<>();
                   loop("Found");
                }
               else{
                   GENERATED_KEYS_PATH = new LinkedList<>();
                   GENERATED_KEYS_LIST = new LinkedList<>();
                   GENERATED_KEYS_USERID=new LinkedList<>();
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
            listView = (ListView) findViewById(R.id.listViewForm2);
            arrayAdapter = new ArrayAdapter<String>(allObjects.this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            databaseReference.addChildEventListener(new ChildEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    ObjectTitle = String.valueOf(dataSnapshot.child("Object Title").getValue());
                    Description = String.valueOf(dataSnapshot.child("description").getValue());
                    //UserID= String.valueOf(dataSnapshot.child("UserID").getValue());
                    //Log.d(UserID," HERE IS");
                    ObjectType = object;
                    //here add if statement when applied to my posts
                    GENERATED_KEYS_PATH.add(FirebaseDatabase.getInstance().getReference("forms").child(Look).child(object).getPath().toString());
                    GENERATED_KEYS_LIST.add(dataSnapshot.getKey());
                  //  arrayList2.add(new objectData(count + ") Object Title: " + ObjectTitle, " Object type: " + ObjectType, "Description: " + Description));
                   // adapter = new MyAdapter(allObjects.this, arrayList2);
                    arrayAdapter.add(count+"" + ")Object Title: " + ObjectTitle+"\n"+" Object type: " + ObjectType+"\n"+"Description: " + Description);
                  //  listView.setAdapter(adapter);
                    listView.setAdapter(arrayAdapter);
                    GENERATED_KEYS_USERID.add(dataSnapshot.child("UserID").getValue().toString());
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


