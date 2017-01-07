package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdministrativeDeptInfoFragment extends Fragment {

    private Firebase ref;
    private String key;

    private TextView importance, phoneNo1, phoneNo2, website;
    private ImageView mapIcon;
    private LinearLayout phoneNo1Layout, phoneNo2Layout, websiteLayout, mainLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.administrative_dept_info, container, false);

//        mainLayout = (LinearLayout) rootView.findViewById(R.id.main_layout);
        key = getArguments().getString("dept_key");
        if (key.equals("Main_Building")) {
//            mainLayout.setPadding(0,0,0,100);
            mapIcon = (ImageView) rootView.findViewById(R.id.mapIcon);
            mapIcon.setImageResource(R.drawable.maps_icon);
            mapIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                     //launch map showing the map location
                    Intent i = new Intent(getContext(), MapsLocationActivity.class);
                    i.putExtra("dept_key", "Main_Building");
                    startActivity(i);
                }
            });

            ref = new Firebase(FirebaseAppConfig.firebaseURL + "Main_Building/Info");
        } else {
           // mainLayout.setPadding(0,0,0,30);
            mapIcon = (ImageView) rootView.findViewById(R.id.mapIcon);
            mapIcon.setEnabled(false);

            ref = new Firebase(FirebaseAppConfig.firebaseURL + "Main_Building/Departments/" + key);
        }


        ref.child("Importance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                importance = (TextView) rootView.findViewById(R.id.importanceTextView);
                if (dataSnapshot.getValue() != null)
                    importance.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref.child("Phone No 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phoneNo1 = (TextView) rootView.findViewById(R.id.phoneNoTextView1);
                if (dataSnapshot.getValue() != null)
                    phoneNo1.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref.child("Phone No 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phoneNo2 = (TextView) rootView.findViewById(R.id.phoneNoTextView2);
                phoneNo2.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref.child("Website").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                website = (TextView) rootView.findViewById(R.id.webTextView);
                website.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        phoneNo1Layout = (LinearLayout) rootView.findViewById(R.id.phoneNo1Layout);
        phoneNo2Layout = (LinearLayout) rootView.findViewById(R.id.phoneNo2Layout);
        websiteLayout = (LinearLayout) rootView.findViewById(R.id.websiteLayout);

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


        return rootView;
    }
}