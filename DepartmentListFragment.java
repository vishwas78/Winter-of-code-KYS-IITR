package com.example.vishwasmittal.woc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class DepartmentListFragment extends Fragment {

    //Sample Hardcoded Data
   /* private String[] deptlist = {"Main Building", "Dept. of Architecture and Planning", "Dept. of Electrical Engineering",
            "Dept. of Computer Science and Engineering", "Department of Management Studies",
            "Mahatma Gandhi IIT Central Library"};
*/
    Firebase deptRef;
    private ArrayList deptList, secondaryList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.department_list_fragment, container, false);


        final Bundle URL = getArguments();
        if(URL == null)
        deptRef = new Firebase(FirebaseAppConfig.firebaseURL + "Departments");
        else{
            deptRef = new Firebase(URL.getString("URL"));
        }


        deptList = new ArrayList();
        secondaryList = new ArrayList();

        ListView deptListView = (ListView) rootView.findViewById(R.id.deptListView);
        final ArrayAdapter deptListAdapter = new ArrayAdapter<String>(getContext(), R.layout.dept_list_row_layout, R.id.deptNameTextView, secondaryList);
        deptListView.setAdapter(deptListAdapter);

        deptRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (secondaryList.isEmpty() && URL == null){
                    secondaryList.add("Main Building");
                }

                deptList.add(dataSnapshot.getKey());
                secondaryList.add(dataSnapshot.getKey().replace('_', ' '));
                deptListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getContext(), "The list has been modified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainViewPager.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                deptList.remove(dataSnapshot.getKey());
                deptListAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "The list has been updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        deptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (URL == null) {
                    if (position != 0) {
                        Intent i = new Intent(getContext(), DepartmentViewPager.class);
                        i.putExtra("dept_key", deptList.get(position - 1).toString());
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getContext(), MainBuildingPagerActivity.class);
                        startActivity(i);
                    }
                }
                else{
                    Intent i = new Intent(getContext(), AdminDepartmentViewPager.class);
                    i.putExtra("dept_key", deptList.get(position).toString());
                    i.putExtra("URL", URL.getString("URL") + deptList.get(position).toString());
                    startActivity(i);
                }
            }
        });



        return rootView;
    }
}
