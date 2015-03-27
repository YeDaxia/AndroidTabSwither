package com.darcye.tabswitcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.darcye.R;


public class SimpleTabActivity extends FragmentActivity {

	private final static String TAG = "SimpleTabActivity";
	
	private RadioGroup mTabGroup;
	
	private ViewPager mViewPager;
	private Fragment[] mFragments;
	private TabView mTab3;
	private TabView mTab4;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabGroup = (RadioGroup) findViewById(R.id.tab_group);
        mTab3 = (TabView) findViewById(R.id.tab_3);
        mTab4 = (TabView) findViewById(R.id.tab_4);
        
        mTab3.setHasTip(true);
        mTab4.setTipText("99");
        
        mFragments = new Fragment[5];
        
        mFragments[0] = SimpleFragment.newFragment("Hello, This is Tab 1");
        mFragments[1] = SimpleFragment.newFragment("Hello, This is Tab 2");
        mFragments[2] = SimpleFragment.newFragment("Hello, This is Tab 3");
        mFragments[3] = SimpleFragment.newFragment("Hello, This is Tab 4");
        mFragments[4] = SimpleFragment.newFragment("Hello, This is Tab 5");
        
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.length;
			}
			
			@Override
			public Fragment getItem(int position) {
				return mFragments[position];
			}
		});
        
        mTabGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.i(TAG, "onCheckedChanged");
				switch(checkedId){
				case R.id.tab_0:
					mViewPager.setCurrentItem(0);
					break;
				case R.id.tab_1:
					mViewPager.setCurrentItem(1);
					break;
				case R.id.tab_2:
					mViewPager.setCurrentItem(2);
					break;
				case R.id.tab_3:
					mViewPager.setCurrentItem(3);
					break;
				case R.id.tab_4:
					mViewPager.setCurrentItem(4);
					break;
				}
			}
		});
        
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				((RadioButton)mTabGroup.getChildAt(position)).setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
    }
}
