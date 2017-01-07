package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("WoC");
        setSupportActionBar(toolbar);
    }

    public void goToInstituteDB(View view){
         Intent i = new Intent(this, MainViewPager.class);      //set the class's name which serves as Institute's DB
         startActivity(i);
    }//end of goToInstituteDB()

    public void goToTaskReminder(View view){
        Intent i = new Intent(this, ToDoList.class);
        startActivity(i);
    }//end of goToTaskReminder

    public void creditsPressed (View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sdslabs.co")));
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }



}
