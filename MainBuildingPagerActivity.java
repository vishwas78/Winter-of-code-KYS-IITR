package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainBuildingPagerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main Building");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle deptKey = new Bundle();
            deptKey.putString("dept_key", "Main_Building");
            if(position == 0){
                AdministrativeDeptInfoFragment deptInfo = new AdministrativeDeptInfoFragment();
                deptInfo.setArguments(deptKey);
                return deptInfo;
            }

            else if (position == 1){
                deptKey.putString("URL", FirebaseAppConfig.firebaseURL + "Main_Building/Departments");
                DepartmentListFragment deptList = new DepartmentListFragment();
                deptList.setArguments(deptKey);
                return deptList;
            }
            else {
                return new AdministrativeFacultyList();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0: return "Main Building Info";
                case 1: return "Departments";
                default: return "Faculty List";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        startActivity(new Intent(getApplicationContext(), MainViewPager.class));
        return  true;
    }
}
