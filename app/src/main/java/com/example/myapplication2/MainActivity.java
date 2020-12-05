package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

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

    public void addform(View view) {
        //FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Form.class));
        finish();
    }

}