package com.example.vishwasmittal.practice2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class ToDoList extends AppCompatActivity {

    private String[] stringTaskList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_to_do_list);

        TaskStorage helper = new TaskStorage(getApplicationContext(), null, null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM task_info;", null);
        int count = helper.getCount();
        ListAdapter taskAdapter;           //list adapter

        //creating and initializing task object
        TaskLayout[] taskObjectList = new TaskLayout[count];
        for(int i=0; i<count; ++i){
            taskObjectList[i] = new TaskLayout();
        }

        //retrieving the list of task from DB
        if(c.moveToFirst()){
            Log.e("class ToDoList","if statement");
            stringTaskList = new String[count];
            int i=0;
            while(i < count){
                Log.e("class ToDoList","putting the tasks in array");
                taskObjectList[i].setTask(c.getString(c.getColumnIndex("_task")));
                stringTaskList[i] = c.getString(c.getColumnIndex("_task"));
                taskObjectList[i].set_id(c.getInt(c.getColumnIndex("_id")));
                c.moveToNext();
                i++;
            }
            taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, stringTaskList);
        }
        else{
          Log.e("class ToDoList","else statement all caught up...");
            String noTask[] = {"You are all caught up..."};
            taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, noTask);
        }

        //displaying tasks in the listView
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(taskAdapter);
//-------------------------------------------------------------------------------------------------- Problematic part for popup menu
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
                            case R.id.popup_edit:         //eirte a function to edit the task.
                                break;
                        }//end of switch

                        return true;
                    }
                }); //end of menuItemClickListener()
            }
        });//end of list ItemClickListener()
//--------------------------------------------------------------------------------------------------
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
                Intent i = new Intent(this,CreateNewTask.class);
                startActivity(i);
        }
        return true;
    }//end of onOptionsItemSelected()

    @Override
    protected void onResume() {
        super.onResume();
    }
}

