package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class all_reports extends AppCompatActivity {

    //FirebaseAuth fAuth;
    //FirebaseFirestore fStore;
   // FirebaseUser user;


  //  ArrayList<ReportData> arrayList2 = new ArrayList<>();
  //  MyReportAdapter adapter;
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    //////////
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String UserID;
    String Description;
    String ReportType;
    int count;
    private  String actReport[]={"Scam","Fake advertise","inappropriate","harassment","other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.all_objects);
        setContentView(R.layout.activity_new_show_report);

        listView = findViewById(R.id.listViewReport);
        FBDB= FirebaseDatabase.getInstance();
        DBRF=FBDB.getReference("report");
        count=1;
        listView = findViewById(R.id.listViewForm2);
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2);


for (String reportSubject:actReport) {
    databaseReference = FirebaseDatabase.getInstance().getReference("report").child(reportSubject);
    listView = (ListView) findViewById(R.id.listViewReport);
    arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
    listView.setAdapter(arrayAdapter);
    databaseReference.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            UserID = String.valueOf(dataSnapshot.child("UserID").getValue());
            Description = String.valueOf(dataSnapshot.child("Description").getValue());
            ReportType = reportSubject;

     //       arrayList2.add(new ReportData(count+") UserID: "+ UserID," Report type: "+ReportType ,"Description: "+ Description ));
       //     arrayAdapter.add(count+") Report type: "+ReportType);
     //       arrayAdapter.add("UID is "+UserID );

          //  arrayAdapter.add("DESCRIPTION:  "+Description );
         //   arrayAdapter.notifyDataSetChanged();
          //  adapter = new MyReportAdapter(all_reports.this, arrayList2);
           // listView.setAdapter(adapter);
            arrayAdapter.add(count+"" + ")UserID: "+ UserID+"\n"+" Report type: "+ReportType+"\n"+"Description: "+ Description);
            listView.setAdapter(arrayAdapter);
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



//        listView = findViewById(R.id.listView);
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        FBDB= FirebaseDatabase.getInstance();
//        DBRF=FBDB.getReference("report");
//        DBRF = FBDB.getReference("/report");
//        DBRF.addValueEventListener(changeListener);
//
//        fStore.collection("report")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "blabla data!!!", Toast.LENGTH_LONG).show();
//                            int i=1;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                arrayList.add(new ReportData(i+") UserID: "+ document.get("UserID").toString(),"Report Subject: "+ document.get("Report Subject").toString() ,"Description: "+ document.get("Description").toString() ));
//                                i++;
//
//                            }
//                        } else {
//                            //  Log.d("TAG", "Error getting documents: ", task.getException());
//                            Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
//
//                        }
//                        adapter = new MyReportAdapter(all_reports.this, arrayList);
//                        listView.setAdapter(adapter);
//                    }
//                });

//        arrayList.add(new objectData(12, " Niyaz","65757657657"));
//       // adapter = new MyAdapter(this, arrayList);
//        listView.setAdapter(adapter);
    }
}

