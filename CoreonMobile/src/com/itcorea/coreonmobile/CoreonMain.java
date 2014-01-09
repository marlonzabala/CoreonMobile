package com.itcorea.coreonmobile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.viewpagerindicator.UnderlinePageIndicator;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CoreonMain extends FragmentActivity implements ActionBar.TabListener
{

	// private ViewPager viewPager;
	// private TabsPagerAdapter mAdapter;
	// private ActionBar actionBar;
	// // Tab titles
	// private String[] tabs = { "My Account", "Billing & Payments", "Rewards & Offers" };
	//
	//

	private PagerSlidingTabStrip	tabs;
	private ViewPager				pager;
	private MyPagerAdapter			adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.test);

		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		// tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		// tabs.setViewPager(pager);
		// tabs.setTabBackground(R.drawable.icon_billing_payments_selected);

		UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setFades(false);
		indicator.setSelectedColor(0xFFFF6600); // orange color
		indicator.setBackgroundColor(0x00000000);
		indicator.setFadeDelay(1000);
		indicator.setFadeLength(1000);
		
//		View title = getWindow().findViewById(android.R.id.title);
//		//View titleBar = (View) title.getParent();
//		title.setBackgroundColor(Color.RED);

		// TODO
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position)
			{
				// on changing the page
				// make respected tab selected
				// actionBar.setSelectedNavigationItem(position);
				
				ImageView im1 = (ImageView)findViewById(R.id.imageViewTabMyAccount);
				ImageView im2 = (ImageView)findViewById(R.id.imageViewTabBillingPayment);
				ImageView im3 = (ImageView)findViewById(R.id.imageViewTabRewardsOffers);
				
				switch (position)
				{
					case 0:
						im1.setImageResource(R.drawable.icon_account_selected);
						im2.setImageResource(R.drawable.icon_billing_payments);
						im3.setImageResource(R.drawable.icon_rewards_offers);
						
						setTitle("My Account");
						
						break;
					case 1:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments_selected);
						im3.setImageResource(R.drawable.icon_rewards_offers);
						
						
						setTitle("Billing and Payments");

						break;
					case 2:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments);
						im3.setImageResource(R.drawable.icon_rewards_offers_selected);
						
						setTitle("Rewards and Offers");
						
						
						break;
					default:
						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft)
	{
		tab.setIcon(R.drawable.icon_account_selected);
		// TODO Auto-generated method stub
		// pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

	public class MyPagerAdapter extends FragmentPagerAdapter// implements IconTabProvider
	{

		private final String[]	TITLES	= { "My Account", "Billing & Payments", "Rewards & Offers" };
		private final int[]		ICONS	= { R.drawable.icon_account, R.drawable.icon_billing_payments, R.drawable.icon_rewards_offers };

		public MyPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return TITLES[position];
		}

		@Override
		public int getCount()
		{
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position)
		{
			return SuperAwesomeCardFragment.newInstance(position);
		}

		// @Override
		// public int getPageIconResId(int position)
		// {
		// return ICONS[position];
		// }

	}

}