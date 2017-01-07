package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class DepartmentViewPager extends AppCompatActivity {

    private final static int NO_OF_PAGES = 2;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager deptPager;
    private static String dept_key;      //this is the dept key for which this pageer is all about

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Department Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dept_key = getIntent().getStringExtra("dept_key");

        deptPager = (ViewPager) findViewById(R.id.pager);
        DeptPagerAdapter deptPagerAdapter = new DeptPagerAdapter(getSupportFragmentManager(),dept_key);
        deptPager.setAdapter(deptPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(deptPager);


    }

    @Override
    public void onBackPressed(){
        if(deptPager.getCurrentItem() == 0){
            finish();
            startActivity(new Intent(getApplicationContext(), MainViewPager.class));
        }
        else {
            deptPager.setCurrentItem(deptPager.getCurrentItem() - 1);
        }
    }


    //Adapter class for DepartViewPager
    private class DeptPagerAdapter extends FragmentStatePagerAdapter{

        private String dept_key;

        public DeptPagerAdapter(FragmentManager fm, String dept_id) {
            super(fm);
            this.dept_key = dept_id;
        }

        @Override
        public Fragment getItem(int position) {

            Bundle deptKey1 = new Bundle();
            deptKey1.putString("dept_key", this.dept_key);
            deptKey1.putString("URL", FirebaseAppConfig.firebaseURL + "Departments/" + this.dept_key);
            deptKey1.putString("URL2", FirebaseAppConfig.firebaseURL + "Departments/" + this.dept_key + "/Faculty_List");

            switch (position){
                case 0:
                    DeptInfoFragment deptInfoFragment = new DeptInfoFragment();
                    deptInfoFragment.setArguments(deptKey1);
                    return deptInfoFragment;

                case 1:
//                    FacultyListFragment facultyListFragment = new FacultyListFragment();
//                    facultyListFragment.setArguments(deptKey);
//                    return facultyListFragment;
//                    Bundle deptKey2 = new Bundle();
//                    deptKey2.putString("dept_key", this.dept_key);
//                    deptKey2.putString("URL", FirebaseAppConfig.firebaseURL + "Departments/" + this.dept_key + "/Faculty_List");
                    AdministrativeFacultyList facultyList = new AdministrativeFacultyList();
                    facultyList.setArguments(deptKey1);
                    return facultyList;
            }

            return null;
        }

        @Override
        public int getCount() {
            return NO_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Dept Info";
            else
                return "Dept Faculty";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return  true;
    }
}