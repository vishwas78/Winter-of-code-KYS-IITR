package com.example.vishwasmittal.kysiitrknowyoursurroundingsiitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }//end of onCreate()

    public void goToInstituteDB(View view){
       // Intent i = new Intent(this, )      //set the class's name which serves as Institute's DB
       // startActivity(i);
    }//end of goToInstituteDB()

    public void goToTaskReminder(View view){
        Intent i = new Intent(this, ToDoList.class);      //set the class's name which serves as interface for task reminder
        startActivity(i);
    }//end of goToTaskReminder

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
