package com.example.vishwasmittal.woc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainViewPager extends AppCompatActivity {

    final static int NO_OF_PAGES = 2;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Around You..");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pager = (ViewPager) findViewById(R.id.pager);
        SlidingScreenPagerAdapter pagerAdapter = new SlidingScreenPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed(){
        if(pager.getCurrentItem() == 0){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    //Adapter class for viewPager
    private class SlidingScreenPagerAdapter extends FragmentStatePagerAdapter {

        public SlidingScreenPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NO_OF_PAGES;
        }         //No. of pages in view pager, to be modified to dynamic nature

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: DepartmentListFragment pagerDisplay = new DepartmentListFragment();
                    return pagerDisplay;
                default:// Bundle toFacultyList = new Bundle();
                   // toFacultyList.putInt("Dept_id", 0);
                    FacultyListFragment pagerDisplay2 = new FacultyListFragment();
                   // pagerDisplay2.setArguments(toFacultyList);
                    return pagerDisplay2;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Department List";
            else
                return "Faculty List";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        return  true;
    }
}
