package com.example.myapplication2.ScrollView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.myapplication2.Adapters.ReportsAdapter;
        import com.example.myapplication2.ClassObject.Report;
        import com.example.myapplication2.R;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

public class ReportsScrollView extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Report> list;
    ReportsAdapter adapter;
    private  String actReport[]={"Scam","Fake advertise","inappropriate","harassment","other"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        Spinner spinner = findViewById(R.id.spinner6);
        spinner.setVisibility(View.INVISIBLE);
        TextView textView = findViewById(R.id.textView9);
        textView.setVisibility(View.INVISIBLE);
        list = new ArrayList<Report>();
        reference = FirebaseDatabase.getInstance().getReference().child("report");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                loop();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReportsScrollView.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {

            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                //  Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                Toast.makeText(ReportsScrollView.this,"heloo in successfully", Toast.LENGTH_SHORT).show();

            }

        });

    }


    public void loop(){
        for (String object : actReport) {
            reference = FirebaseDatabase.getInstance().getReference("report").child(object);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Report p= dataSnapshot.getValue(Report.class);
                    p.setReportType(FirebaseDatabase.getInstance().getReference("report").child(object).getKey());
                    list.add(p);

                    adapter = new ReportsAdapter(ReportsScrollView.this,list);
                    recyclerView.setAdapter(adapter);
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