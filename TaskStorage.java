package com.example.vishwasmittal.kysiitrknowyoursurroundingsiitr;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskStorage extends SQLiteOpenHelper{

    final static int DB_VERSION = 1;
    final static String DB_NAME = "TaskStorageDB.db";
    final static String TABLE_TASK = "task_info";
    final static String COLUMN_ID = "_id";
    final static String COLUMN_TASK = "_task";

    //constructor
    public TaskStorage(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TASK + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT NOT NULL ); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public void addNewTask(TaskLayout newTask){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, newTask.getTask());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASK, null, values);
        db.close();
    }//end of addNewTask() method

    int getTaskCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TASK, null);
        db.close();
        return c.getCount();
    }

    public void editTask(TaskLayout newTask){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_TASK + " SET " + COLUMN_TASK + " = \'" + newTask.getTask() +
                "\' WHERE " + COLUMN_ID + " = " + newTask.get_id() + ";";
        db.execSQL(query);
        db.close();
    }

    public void deleteTask(final int _id, final Context context){
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setTitle("Warning")
                .setMessage("Task will be deleted permanently")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getReadableDatabase();
                        String query = "DELETE FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + " = " + _id + ";";
                        db.execSQL(query);
                        db.close();

                        //Success Message for deleting
                        final AlertDialog.Builder deleteSuccessDialog = new AlertDialog.Builder(context);
                        deleteSuccessDialog.setTitle("Operation Successful")
                                .setMessage("Task Deleted")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(context, ToDoList.class);
                                        context.startActivity(i);
                                    }
                                })
                                .create()
                                .show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder deleteCancelDialog = new AlertDialog.Builder(context);
                        deleteCancelDialog.setTitle("Operation Cancelled")
                                .setMessage("Task is not Deleted")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                    }
                })
                .create()
                .show();
    }

}
