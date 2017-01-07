package com.example.vishwasmittal.woc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class AdminDepartmentViewPager extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private String URL, key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Department Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adminDeptPagerAdapter pagerAdapter = new adminDeptPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        URL = getIntent().getStringExtra("URL");
        key = getIntent().getStringExtra("dept_key");

    }


    private class adminDeptPagerAdapter extends FragmentStatePagerAdapter{

        public adminDeptPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle deptkey1 = new Bundle();
            deptkey1.putString("URL", URL);
            deptkey1.putString("dept_key", key);
            Log.d("ADMIN", "The key is: " + key);
            deptkey1.putString("URL2", FirebaseAppConfig.firebaseURL + "Main_Building/Departments/" + key + "/Faculty_Info");

            switch (position){
                case 0: AdministrativeDeptInfoFragment deptInfoFragment = new AdministrativeDeptInfoFragment();
                    deptInfoFragment.setArguments(deptkey1);
                    return deptInfoFragment;

                default:
                    AdministrativeFacultyList facultyList = new AdministrativeFacultyList();
                    facultyList.setArguments(deptkey1);
                    return facultyList;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "DEPT INFO";
                default: return "DEPT FACULTY";
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
