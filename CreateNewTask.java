package com.example.vishwasmittal.kysiitrknowyoursurroundingsiitr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewTask extends AppCompatActivity {

    private EditText task;
    private TextView createTaskTitle;
    private Button submitTaskBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        String[] accessMode = getIntent().getStringArrayExtra("Access mode");


        submitTaskBTN = (Button) findViewById(R.id.submitNewTaskBTN);
        task = (EditText) findViewById(R.id.enterTask);
        createTaskTitle = (TextView) findViewById(R.id.createTaskTitle);
        createTaskTitle.setText(accessMode[0]);
        task.setText(accessMode[1]);
        final int task_id = Integer.valueOf(accessMode[2]);

        submitTaskBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  taskString = task.getText().toString();

                if(taskString.trim().length() == 0){
                    Toast.makeText(getApplicationContext(),"Enter the task!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    TaskLayout enteredTask = new TaskLayout(task_id, taskString);
                    TaskStorage db = new TaskStorage(getApplicationContext(), null, null, 1);
                    if (task_id == -1) {
                        db.addNewTask(enteredTask);
                        Toast.makeText(getApplicationContext(), "Your task has been added", Toast.LENGTH_SHORT).show();
                    }//end of else
                    else {
                        db.editTask(enteredTask);
                        Toast.makeText(getApplicationContext(), "Your task has been edited", Toast.LENGTH_SHORT).show();
                    }
                    task.setText("");
                }//end of else
            }
        }); //end of clickListener
    }//end of onCreate();

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),ToDoList.class);
        startActivity(i);
    }
}//end of class
