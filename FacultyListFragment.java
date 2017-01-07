package com.example.vishwasmittal.woc;

 //TODO: add a please wait progress bar while the list loads
//TODO: add a query for department to serve the need for departmentViewPager's faculty list


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class FacultyListFragment extends Fragment {

    //Sample Hardcoded Data
   // private String[] listFaculty = {"Ajay Wasan", "Bhanu Prakash Vellanki", "Durga Toshniwal",
     //       "R. Balasubramaniam", "Rahul K. Gairola", "Ram Manohar Singh"};

    private Firebase facultyListRef;
    private ArrayList facultyList;
    String deptKeyStr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.contact_list_fragment_layout, container, false);



        //This is the the id for dept if null then return all the dept. Use it with the database.

        Bundle deptKey = getArguments();           //unused at the moment

        if(deptKey != null) {
            deptKeyStr = deptKey.getString("dept_key");
            Log.e("Faculty list, if", "value of dept key str is: " + deptKeyStr);
            facultyListRef = new Firebase(FirebaseAppConfig.firebaseURL + deptKeyStr + "/" + "Faculty_List");
            Log.e("Faculty list, if", "reference URL is: " + FirebaseAppConfig.firebaseURL + "Departments/" + deptKeyStr + "/" + "Faculty_List");
        }
        else {
            facultyListRef = new Firebase(FirebaseAppConfig.firebaseURL + "Faculty");
        }

        Log.e("Faculty list, if", "outside if else...");
        facultyList = new ArrayList();
        ListView contatList = (ListView) rootView.findViewById(R.id.contactListView);
        final contactListAdapter listAdapter = new contactListAdapter(getActivity(), facultyList);
        contatList.setAdapter(listAdapter);
        Log.e("Faculty list, if", "List adapter set");

        facultyListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Faculty list", "inside child listener");
                facultyList.add(dataSnapshot.getKey());
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getContext(), "The list has been modified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainViewPager.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                facultyList.remove(dataSnapshot.getKey());
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "The list has been updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Log.e("Faculty list, if", "surpassed child event listener");


        contatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListClass Intent", "key: " + facultyList.get(position).toString());
                Intent i = new Intent(getContext(), FacultyInfo.class);
                i.putExtra("_key", facultyList.get(position).toString());
                startActivity(i);
            }
        });

        return rootView;
    }


    //Adapter class for listView to support custom list...................
    public class contactListAdapter extends ArrayAdapter<String>{

        public contactListAdapter(Activity context, ArrayList contactList) {
            super(context, R.layout.contact_list_row_layout, contactList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_list_row_layout, parent, false);

            TextView contactName = (TextView) convertView.findViewById(R.id.contactNameTextView);
            contactName.setText(facultyList.get(position).toString().replace('_', ' '));

            return convertView;
        }
    }

}

