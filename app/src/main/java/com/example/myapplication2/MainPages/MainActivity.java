package com.example.myapplication2.MainPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2.AddPages.AddForm;
import com.example.myapplication2.AddPages.AddReport;
import com.example.myapplication2.MyPosts;
import com.example.myapplication2.R;
import com.example.myapplication2.ScrollView.FormsScrollView;
import com.example.myapplication2.ScrollView.ReportsScrollView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    String userType;
    FirebaseAuth fAuth;
    FirebaseUser fBase;
    Button inspector;
    Button report;
    /////
    FirebaseDatabase FBDB;
    DatabaseReference DBRF;
    String userAccess ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        userType= fBase.getUid();
        inspector=  findViewById(R.id.inspector);
        report=  findViewById(R.id.report);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userAccess=dataSnapshot.child("type").getValue().toString();
                //Toast.makeText(MainActivity.this,userAccess,Toast.LENGTH_SHORT).show();
                if(userAccess.equals("Inspector"))
                {
                    inspector.setVisibility(View.VISIBLE);
                    report.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,userAccess,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    report.setVisibility(View.VISIBLE);
                    inspector.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,userAccess,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem item=menu.findItem(R.id.search_icon);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this,"Search expanded",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this,"Search collapse",Toast.LENGTH_SHORT).show();
                return true;
            }
        };




        menu.findItem(R.id.search_icon).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_icon).getActionView();
        searchView.setQueryHint("search here...");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.search_icon:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.refresh:
                Toast.makeText(this,"Refresh",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id.Credits:
                Toast.makeText(this,"Credits",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Credits.class));
                return true;

/*            case R.id.Lost:
                Toast.makeText(this,"Lost",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Form.class));
                return true;
            case R.id.Found:
                Toast.makeText(this,"Found",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Form.class));
                return true;*/
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
        //return super.onOptionsItemSelected(item);

/*
        if(item.getItemId()==R.id.settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Settings.class));
            Toast.makeText(this,"yesss",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
 */
    }

    public void allPosts(View view) {
        //startActivity(new Intent(getApplicationContext(), AllPosts.class));
        startActivity(new Intent(getApplicationContext(), FormsScrollView.class));
    }

    public void addform(View view) {
        Intent intent=new Intent(MainActivity.this, AddForm.class);
        intent.putExtra("CALLED","Main");
        startActivity(intent);
    }
    public void settings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));

    }
    public void report(View view) {
        startActivity(new Intent(getApplicationContext(), AddReport.class));
    }
    public void myPosts(View view) {
        startActivity(new Intent(getApplicationContext(), MyPosts.class));
    }
    public void inspector(View view) {
       // startActivity(new Intent(getApplicationContext(), AllReports.class));
        startActivity(new Intent(getApplicationContext(), ReportsScrollView.class));

    }

}