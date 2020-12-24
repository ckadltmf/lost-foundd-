package com.example.myapplication2.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication2.Adapters.FormsAdapter;
import com.example.myapplication2.ClassObject.ObjectForm;
import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPostsScrollView extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<ObjectForm> list;
    FormsAdapter adapter;
    Spinner Happened_spinner;
    private  String actObject[]={"Mobile","Jewel","Clothing","Pet","Electronics","Car","Bike","Bag","Glasses","jewel"};
    private  String happend[]={"Lost","Found"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        Happened_spinner = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> HappendAdapter= ArrayAdapter.createFromResource(this, R.array.Whathappened, R.layout.support_simple_spinner_dropdown_item);
        Happened_spinner.setAdapter(HappendAdapter);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<ObjectForm>();
        reference = FirebaseDatabase.getInstance().getReference().child("forms").child("Lost");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //loop("Lost");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyPostsScrollView.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {

            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                //  Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                Toast.makeText(MyPostsScrollView.this,"heloo in successfully", Toast.LENGTH_SHORT).show();

            }

        });

        Happened_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                list = new ArrayList<ObjectForm>();
                if(Happened_spinner.getSelectedItem().equals("Lost")){
                    //  list.clear();
                    loop("Lost");
                }
                else if(Happened_spinner.getSelectedItem().equals("Found")){
                    //   list.clear();
                    loop("Found");
                }
                else{
                    //  list.clear();
                    loop("Lost");
                    loop("Found");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });


    }


    public void loop(String Look){
        // list.clear();
        for (String object : actObject) {
            reference = FirebaseDatabase.getInstance().getReference("forms").child(Look).child(object);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ObjectForm p= dataSnapshot.getValue(ObjectForm.class);
                    FirebaseUser FB_currUser= FirebaseAuth.getInstance().getCurrentUser();
                    String DB_UserID=String.valueOf(dataSnapshot.child("UserID").getValue());
                    if(FB_currUser.getUid().equals(DB_UserID)) {
                        p.setCategory(FirebaseDatabase.getInstance().getReference("forms").child(Look).child(object).getKey());
                        p.setHappend(FirebaseDatabase.getInstance().getReference("forms").child(Look).getKey());
                        p.setGeneratedKey(dataSnapshot.getKey());
                        list.add(p);

                        adapter = new FormsAdapter(MyPostsScrollView.this, list);
                        recyclerView.setAdapter(adapter);
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

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



}