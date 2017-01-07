package com.example.vishwasmittal.woc;

//TODO: add methods to handle the images
//TODO: add method to go to resprctive department if department textView is clicked


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class FacultyInfo extends AppCompatActivity {

    private Firebase infoRef;
    private ArrayList[] list;
    public String FACEBOOK_PAGE_ID = "";              //for opening profile in app
    private String facebookURL = "https://www.facebook.com/";     //if needed used for opening profile in browser
    private String LINKEDIN_PAGE_ID = "";

    private Toolbar toolbar;
    private String key;         //use this id to retrieve the faculty data from db;
    private TextView facultyName, designation, areasOfInterest, homePhoneNo, officePhoneNo, email, department;
    private ImageView imageView1, imageView2;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Faculty Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        key = getIntent().getStringExtra("_key");

        imageView1 = (ImageView) findViewById(R.id.image_view_1);
        imageView2 = (ImageView) findViewById(R.id.image_view_2);

        infoRef = new Firebase(FirebaseAppConfig.firebaseURL + "Faculty/" + key);
        Log.e("Faculty URL", FirebaseAppConfig.firebaseURL + "Faculty/" + key);
        list = new ArrayList[2];
        list[0] = new ArrayList();
        list[1] = new ArrayList();

        Log.e("Faculty Info Class", "adding childEventListener");
        infoRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list[0].add(dataSnapshot.getKey());
                list[1].add(dataSnapshot.getValue().toString());
                Log.e("child added", "Key Added: " + list[0].get(i).toString());
                if (i >= 5) referenceUIControls();
                ++i;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), "The information has been updated", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainViewPager.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "The information has been updated", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainViewPager.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Log.e("Faculty Info Class", "child event listener added");


    }//end of onCreate


    private void referenceUIControls() {
        //To check if any if the social profile is unavailable and set the links/images like-wise
        if (list[0].contains("Facebook") && list[0].contains("LinkedIn")) {
            Log.e("Faculty info class", "both are present");
            imageView1.setImageResource(R.drawable.facebook_icon);
            imageView2.setImageResource(R.drawable.linkedin_icon);

            int pos = 0;
            pos = list[0].indexOf("Facebook");
            FACEBOOK_PAGE_ID = list[1].get(pos).toString();
            pos = list[0].indexOf("LinkedIn");
            LINKEDIN_PAGE_ID = list[1].get(pos).toString();

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    facebook();
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linkedIn();
                }
            });
        } else if (list[0].contains("Facebook")) {
            Log.e("Faculty info class", "Facebook Present");
            imageView1.setImageResource(R.drawable.facebook_icon);

            int pos = 0;
            pos = list[0].indexOf("Facebook");
            FACEBOOK_PAGE_ID = list[1].get(pos).toString();

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    facebook();
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } else if (list[0].contains("LinkedIn")) {
            Log.e("Faculty info class", "LinkesIn present");
            imageView1.setImageResource(R.drawable.linkedin_icon);

            int pos = 0;
            pos = list[0].indexOf("LinkedIn");
            Log.e("before error statement", "" + pos);
            LINKEDIN_PAGE_ID = list[1].get(pos).toString();

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linkedIn();
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } else {
            Log.e("Faculty info class", "none present");
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }


        Log.e("Faculty info class", "referencing UI controls");
        facultyName = (TextView) findViewById(R.id.facultyNameTextView);
        designation = (TextView) findViewById(R.id.designationTextView);
        areasOfInterest = (TextView) findViewById(R.id.areasOfInterestTextView);
        homePhoneNo = (TextView) findViewById(R.id.homePhoneNoTextView);
        officePhoneNo = (TextView) findViewById(R.id.officePhoneNoTextView);
        email = (TextView) findViewById(R.id.emailTextView);
        department = (TextView) findViewById(R.id.departmentTextView);

        facultyName.setText(infoRef.getKey().replace('_', ' '));

        int pos = 0;
        pos = list[0].indexOf("Areas Of Interest");
        if (pos >= 0) areasOfInterest.setText(list[1].get(pos).toString());
        Log.e("Faculty info class", "Areas Of Interest pos: " + pos);

        pos = list[0].indexOf("Home Phone No");
        if (pos >= 0) homePhoneNo.setText(list[1].get(pos).toString());
        Log.e("Faculty info class", "Home phone No pos: " + pos);

        pos = list[0].indexOf("Office Phone No");
        if (pos >= 0) officePhoneNo.setText(list[1].get(pos).toString());
        Log.e("Faculty info class", "office phone no pos: " + pos);

        pos = list[0].indexOf("Email");
        if (pos >= 0) email.setText(list[1].get(pos).toString());
        Log.e("Faculty info class", "emai pos: " + pos);

        pos = list[0].indexOf("Designation");
        if (pos >= 0) designation.setText(list[1].get(pos).toString().replace('_', ' '));
        Log.e("Faculty info class", "designation pos: " + pos);

        pos = list[0].indexOf("Department");
        if (pos >= 0) department.setText(list[1].get(pos).toString().replace('_', ' '));
        Log.e("Faculty info class", "designation pos: " + pos);
    }


    public void facebook() {
        String facebookUrl = new String();
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                facebookUrl = "fb://facewebmodal/f?href=" + facebookURL + FACEBOOK_PAGE_ID;
            } else { //older versions of fb app
                facebookUrl = "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            facebookUrl = facebookURL + FACEBOOK_PAGE_ID; //normal web url
        }

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(i);
    }

    public void linkedIn() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINKEDIN_PAGE_ID));
        startActivity(i);
    }

    public void sendEmail(View view) {
        try {              //to open the emain in gmail
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setType("plain/text");
            i.setData(Uri.parse(email.getText().toString()));
            i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            startActivity(i);
        } catch (Exception e) {             //to download gmail if not available
            try {   //to google play app to download
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.gm&hl=en")));
            } catch (Exception e1) {  //open web play store
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +
                        "com.google.android.gm&hl=en")));
            }
        }
    }

    public void dialHomePhoneNo(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + homePhoneNo.getText().toString()));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void dialOfficePhoneNo(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + officePhoneNo.getText().toString()));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
