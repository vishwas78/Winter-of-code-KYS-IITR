package com.example.vishwasmittal.kysiitrknowyoursurroundingsiitr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ToDoList extends AppCompatActivity {

    private String[] stringTaskList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        TaskStorage helper = new TaskStorage(getApplicationContext(), null, null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM task_info;", null);
        int count = c.getCount();
        ListAdapter taskAdapter;           //list adapter

        //creating and initializing task object
        final TaskLayout[] taskObjectList = new TaskLayout[count];
        for(int i=0; i<count; ++i){
            taskObjectList[i] = new TaskLayout();
        }

        //retrieving the list of task from DB
        if(c.moveToFirst()){
            stringTaskList = new String[count];
            int i=0;
            while(i < count){
                taskObjectList[i].setTask(c.getString(c.getColumnIndex("_task")));
                taskObjectList[i].set_id(c.getInt(c.getColumnIndex("_id")));
                stringTaskList[i] = c.getString(c.getColumnIndex("_task"));
                c.moveToNext();
                i++;
            }
            taskAdapter = new ArrayAdapter<String>(this, R.layout.task_list_row_layout, R.id.taskString, stringTaskList);


            //displaying tasks in the listView
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(taskAdapter);
/*-------------------------------------------------------------------------------------------------- Problematic part for popup menu
        //On Clicking the items a menu will pop up
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("popup menu", "declaring popup menu");
                PopupMenu popupMenu = new PopupMenu(ToDoList.this, view.findFocus());
                Log.e("popup menu", "inflating the menu layout");
                popupMenu.getMenuInflater().inflate(R.menu.todo_list_popup_menu, popupMenu.getMenu());
                Log.e("popup menu", "showing the menu");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.popup_delete:       //write a function to delete the task and make use of item id.
                                break;
                            case R.id.popup_edit:         //write a function to edit the task.
                                break;
                        }//end of switch

                        return true;
                    }
                }); //end of menuItemClickListener()
            }
        });//end of list ItemClickListener()
//------------------------------------------------------------------------------------------------*/

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String[] taskChooseOptions = {"Edit", "Delete"};

                    AlertDialog.Builder taskSelectDialogue = new AlertDialog.Builder(ToDoList.this);
                    taskSelectDialogue.setTitle("Choose an option")
                            .setItems(taskChooseOptions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TaskStorage ts = new TaskStorage(getApplicationContext(),null,null,1);
                                    switch(which){
                                        case 0:  String[] intentInformation = {"EDIT TASK", taskObjectList[position].getTask(),
                                                String.valueOf(taskObjectList[position].get_id())};
                                            Intent i = new Intent (getApplicationContext(), CreateNewTask.class);
                                            i.putExtra("Access mode", intentInformation);
                                            startActivity(i);
                                            break;
                                        case 1:  ts.deleteTask(taskObjectList[position].get_id(), ToDoList.this);
                                            break;
                                    }
                                }
                            })//end of dialog item click listener
                            .create()
                            .show();
                }
            });//end of itemClickListener()
        }
        else{
            String noTask[] = {"You are all caught up..."};
            taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, noTask);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(taskAdapter);
        }//end of else statement
    }//end of onCreate()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_todo_screen_menu , menu);
        return true;
    }//end of onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_task_option:
                String[] intentInformation = {"ADD NEW TASK", "", "-1"};
                Intent i = new Intent(this,CreateNewTask.class);
                i.putExtra("Access mode", intentInformation);
                startActivity(i);
        }
        return true;
    }//end of onOptionsItemSelected()

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }
}//end of class

