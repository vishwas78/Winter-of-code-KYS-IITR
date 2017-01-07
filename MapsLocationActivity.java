package com.example.vishwasmittal.woc;

//TODO: Receive these data from another class i.e. main class for this part

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsLocationActivity extends FragmentActivity implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener {

    //Sample Hardcoded data
    String[] places = {"Main Building", "Dept. of Architecture and Planning", "Dept. of Electrical Engineering",
            "Dept. of Computer Science and Engineering", "Department of Management Studies",
            "Mahatma Gandhi IIT Central Library"};
    private LatLng[] coordinates;

    private Firebase mapref;
    private ArrayList[] list;
    Firebase coordinatesref;

    private GoogleMap mMap;
    private Spinner placesSpinner;
    private ArrayAdapter placesAdapter;
    private String dept_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapref = new Firebase(FirebaseAppConfig.firebaseURL + "Departments");
        dept_key = getIntent().getStringExtra("dept_key");
        list = new ArrayList[3];
        list[0] = new ArrayList();
        list[1] = new ArrayList();
        list[2] = new ArrayList();

        placesSpinner = (Spinner) findViewById(R.id.placesSpinner);
        placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list[0]);
        placesSpinner.setAdapter(placesAdapter);
       // placesSpinner.setSelection(dept_id);
       // placesSpinner.setOnItemSelectedListener(this);


        mapref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(list[0].isEmpty()){
                    list[0].add("Main Building");
                    list[1].add("29.865");
                    list[2].add("77.89657");
                }

                list[0].add(dataSnapshot.getKey().replace('_', ' '));
                coordinatesref = mapref.child(dataSnapshot.getKey()).child("Coordinates");         //Coordinates child
                Firebase latitude = coordinatesref.child("Latitude");
                Firebase longitude = coordinatesref.child("Longitude");

                latitude.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list[1].add(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                longitude.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list[2].add(dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                placesAdapter.notifyDataSetChanged();

                if(list[0].contains(dept_key.replace('_', ' '))){
                    placesSpinner.setSelection(list[0].indexOf(dept_key.replace('_', ' ')));
                    placesSpinner.setOnItemSelectedListener(MapsLocationActivity.this);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Error Loading Information", Toast.LENGTH_SHORT).show();
                System.out.print(firebaseError);
            }
        });




       /* //initializing the  coordinates list...
        coordinates = new LatLng[6];
        coordinates[0] = new LatLng(29.865, 77.89657);
        coordinates[1] = new LatLng(29.8634284,77.8997386);
        coordinates[2] = new LatLng(29.8636884,77.8973005);
        coordinates[3] = new LatLng(29.8633245,77.8956495);
        coordinates[4] = new LatLng(29.8647926,77.8947435);
        coordinates[5] = new LatLng(29.8652788,77.8946457);*/




    }//end of onCreate()

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*LatLng IITR = new LatLng(29.865, 77.89657);
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IITR, 16));
        mMap.addMarker(new MarkerOptions().position(IITR).title("IITR"));*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LatLng buildingPos = new LatLng(Double.parseDouble(list[1].get(position).toString()), Double.parseDouble(list[2].get(position).toString()));

        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(buildingPos, 17));
        mMap.addMarker(new MarkerOptions().position(buildingPos).title(list[0].get(position).toString()));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        LatLng IITR = new LatLng(29.865, 77.89657);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IITR, 16));
        mMap.addMarker(new MarkerOptions().position(IITR).title("IITR"));
    }
}