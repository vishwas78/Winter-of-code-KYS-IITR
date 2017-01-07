package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class DeptInfoFragment extends Fragment {

    //Sample Hardcoded Data
    private String[] deptlist = {"Main Building", "Dept. of Architecture and Planning", "Dept. of Electrical Engineering",
            "Dept. of Computer Science and Engineering", "Department of Management Studies",
            "Mahatma Gandhi IIT Central Library"};
    private String samplePhoneNo1 = "0123456789";
    private String samplePhoneNo2 = "9876543210";
    private String sampleWebsite = "www.iitr.ac.in";


    private Firebase ref;
    private ArrayList[] list;
    private static String key;
    private ImageView mapIcon;
    private TextView name, phoneNo1, phoneNo2, website;
    private LinearLayout phoneNo1Layout, phoneNo2Layout, websiteLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dept_info_fragment, container, false);

        key = getArguments().getString("dept_key");
        Log.e("dept Info", " the key is" + key);
        //ref = new Firebase(FirebaseAppConfig.firebaseURL + "Departments/" + key);
        ref = new Firebase(getArguments().getString("URL"));
        list = new ArrayList[2];
        list[0] = new ArrayList();
        list[1] = new ArrayList();


        name = (TextView) rootView.findViewById(R.id.departmentNameTextView);
        phoneNo1 = (TextView) rootView.findViewById(R.id.phoneNoTextView1);
        phoneNo2 = (TextView) rootView.findViewById(R.id.phoneNoTextView2);
        website = (TextView) rootView.findViewById(R.id.webTextView);
        mapIcon = (ImageView) rootView.findViewById(R.id.mapIcon);
        phoneNo1Layout = (LinearLayout) rootView.findViewById(R.id.phoneNo1Layout);
        phoneNo2Layout = (LinearLayout) rootView.findViewById(R.id.phoneNo2Layout);
        websiteLayout = (LinearLayout) rootView.findViewById(R.id.websiteLayout);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("dept Info", "inside child listener");
                list[0].add(dataSnapshot.getKey());
                list[1].add(dataSnapshot.getValue());
                setUIControls();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getContext(), "The list has been modified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainViewPager.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(getContext(), "The list has been modified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainViewPager.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(), "Error Loading Information", Toast.LENGTH_SHORT).show();
                System.out.print(firebaseError);
            }
        });


        //id of the department received from parent pager class and used to retrieve the data from DB

       /* name.setText(deptlist[id]);
        phoneNo1.setText(samplePhoneNo1);
        phoneNo2.setText(samplePhoneNo2);
        website.setText(sampleWebsite);
*/

        //TODO: Write methods to handle thee click events on these Views.
        phoneNo1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                  //to start the dialer with phone No. 1
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo1.getText().toString()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        phoneNo2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                  //to start the dialer with phone No. 2
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo2.getText().toString()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        websiteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                   //launch dept. website
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(website.getText().toString())));
            }
        });

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                     //launch map showing the map location
                Intent i = new Intent(getContext(), MapsLocationActivity.class);
                i.putExtra("dept_key", key);
                startActivity(i);
            }
        });


        return rootView;
    }

    private void setUIControls() {
        name.setText(key.replace('_', ' '));

        int pos = 0;

        pos = list[0].indexOf("Phone No 1");
        if (pos >= 0) phoneNo1.setText(list[1].get(pos).toString());

        pos = list[0].indexOf("Phone No 2");
        if (pos >= 0) phoneNo2.setText(list[1].get(pos).toString());

        pos = list[0].indexOf("Website");
        if (pos >= 0) website.setText(list[1].get(pos).toString());


    }


}
