package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {
    String userType;
    FirebaseAuth fAuth;
    FirebaseUser fBase;
    Button inspector;
    Button report;
    MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        fBase = fAuth.getCurrentUser();
        assert fBase != null;
        userType= fBase.getDisplayName();
        inspector=  findViewById(R.id.inspector);
        report=  findViewById(R.id.report);

//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Search: ");
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        searchView= (MaterialSearchView)findViewById(R.id.searchView);

        if(userType.equals("Inspector"))
        {
            inspector.setVisibility(View.VISIBLE);
            report.setVisibility(View.INVISIBLE);
        }
        else
        {
            report.setVisibility(View.VISIBLE);
            inspector.setVisibility(View.INVISIBLE);
        }





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem item=menu.findItem(R.id.search_icon);
        searchView.setMenuItem(item);
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

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.inspectorOptions);
        if(userType.equals("Inspector"))
        {
            register.setVisible(false);
        }
        else
        {
            register.setVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.search_icon:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Settings.class));
                return true;
            case R.id.refresh:
                Toast.makeText(this,"Refresh",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id.inspectorOptions:
                Toast.makeText(this,"Refresh",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),allObjects.class));
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
                startActivity(new Intent(getApplicationContext(),Login.class));
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
        startActivity(new Intent(getApplicationContext(),allObjects.class));
    }

    public void addform(View view) {
        startActivity(new Intent(getApplicationContext(),Form.class));
    }
    public void settings(View view) {
        startActivity(new Intent(getApplicationContext(),Settings.class));
    }
    public void report(View view) {
        startActivity(new Intent(getApplicationContext(),Report.class));
    }
    public void myPosts(View view) {
        startActivity(new Intent(getApplicationContext(),MyPosts.class));
    }
    public void inspector(View view) {
        startActivity(new Intent(getApplicationContext(),all_reports.class));
    }

}