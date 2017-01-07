package com.example.vishwasmittal.woc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class AdministrativeFacultyList extends Fragment {

    //TODO: add column for faculty Designation

    private Firebase ref;
    private ArrayList facultyList;
    private String URL;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.contact_list_fragment_layout, container, false);

        Bundle deptKey = getArguments();
        URL = new String();

        if (deptKey != null) {
            String key = deptKey.getString("dept_key");
            URL = deptKey.getString("URL2");
    //        if(!URL.matches(null)){
            Log.d("ADMIN", "URL: " + URL);
//            URL = deptKey.getString(FirebaseAppConfig.firebaseURL + URL);
            ref = new Firebase(URL);
//        }
        //    else Log.d("ADMIN", "No URL");
        } else {
            ref = new Firebase(FirebaseAppConfig.firebaseURL + "Main_Building/Faculty_Info");
        }

        facultyList = new ArrayList();
        listView = (ListView) rootView.findViewById(R.id.contactListView);
        final customListAdapter listAdapter = new customListAdapter(getActivity(), facultyList);
        listView.setAdapter(listAdapter);

        ref.addChildEventListener(new ChildEventListener() {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), FacultyInfo.class);
                i.putExtra("_key", facultyList.get(position).toString());
                startActivity(i);
            }
        });


        return rootView;
    }


    public class customListAdapter extends ArrayAdapter<String> {

        public customListAdapter(Activity context, ArrayList contactList) {
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
