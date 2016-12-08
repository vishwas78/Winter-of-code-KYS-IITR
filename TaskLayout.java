package com.example.vishwasmittal.kysiitrknowyoursurroundingsiitr;

public class TaskLayout{

    private int _id;
    private String _task;

    public TaskLayout(){
        _id = 0;
        _task = "";
    }

    public TaskLayout(String _task) {
        this._task = _task;
    }

    public TaskLayout(int _id, String task) {
        this._id = _id;
        this._task = task;
    }


    //setters and getters
    public String getTask() {
        return _task;
    }

    public int get_id() {
        return _id;
    }

    public void setTask(String task) {
        this._task = task;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}//end of class
