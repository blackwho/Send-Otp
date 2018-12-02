package com.example.appjo.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appjo.contactsapp.Adapters.ContactsAdapter;
import com.example.appjo.contactsapp.Adapters.MessageSummaryAdapter;
import com.example.appjo.contactsapp.Fragments.ContactListFragment;
import com.example.appjo.contactsapp.Fragments.MessageSummaryFragment;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnSelectContactListener {
    private ViewPager mViewPager;
    private AppPagerAdapter mAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();

    protected void onResume(){
        super.onResume();
        viewPagerChangeListener();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);
        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        mAdapter = new AppPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        //setting the View Pager to the tab layout
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onContactClick(String first, String last, String phone) {
        Intent intent = new Intent(MainActivity.this, ContactInfoActivity.class);
        intent.putExtra("first", first);
        intent.putExtra("last", last);
        intent.putExtra("phone", phone);
        //This checks if the right intent is available to handle this
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void viewPagerChangeListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v(TAG, "Here");
            }

            @Override
            public void onPageSelected(int position) {
                Log.v(TAG, "Here");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v(TAG, "Here");
            }
        });
    }

    public class AppPagerAdapter extends FragmentPagerAdapter{
        private Context mContext;

        public AppPagerAdapter(Context context, FragmentManager mFragmentManager){
            super(mFragmentManager);
            mContext = context;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:{
                    return ContactListFragment.newInstance();
                }
                case 1:{
                    return MessageSummaryFragment.newInstance();
                }
                default: {
                    return ContactListFragment.newInstance();
                }
            }
        }

        //This determines the number of tab
        @Override
        public int getCount() {
            return 2;
        }

        //This determines title for each tab
        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0: {
                    return mContext.getString(R.string.tab1_contact_list);
                }
                case 1: {
                    return mContext.getString(R.string.tab2_message_summary);
                }
                default: {
                    return null;
                }
            }
        }
    }
}
