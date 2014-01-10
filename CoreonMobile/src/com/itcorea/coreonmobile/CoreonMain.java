package com.itcorea.coreonmobile;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.viewpagerindicator.UnderlinePageIndicator;

@SuppressLint("NewApi")
public class CoreonMain extends SherlockFragmentActivity implements ActionBar.TabListener
{
	private ViewPager		pager;
	private MyPagerAdapter	adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setFades(false);
		indicator.setSelectedColor(0xFFFF6600); // orange color
		indicator.setBackgroundColor(0x00000000);
		indicator.setFadeDelay(1000);
		indicator.setFadeLength(1000);

		// // set defaults for logo & home up
		// // ab.setDisplayHomeAsUpEnabled(true);
		// actionBar.setDisplayUseLogoEnabled(false);
		// actionBar.setIcon(R.drawable.login_logo);
		// actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.title_bg));
		//
		// View customNav = LayoutInflater.from(this).inflate(R.layout.mytitle, null);
		// getSupportActionBar().setCustomView(customNav);
		// getSupportActionBar().setDisplayShowCustomEnabled(true);

		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.mytitle);
		mainTitle = (TextView) findViewById(R.id.textViewTitle);

		// TODO
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position)
			{
				// on changing the page
				// make respected tab selected
				// actionBar.setSelectedNavigationItem(position);

				ImageView im1 = (ImageView) findViewById(R.id.imageViewTabMyAccount);
				ImageView im2 = (ImageView) findViewById(R.id.imageViewTabBillingPayment);
				ImageView im3 = (ImageView) findViewById(R.id.imageViewTabRewardsOffers);

				switch (position)
				{

					case 0:
						im1.setImageResource(R.drawable.icon_account_selected);
						im2.setImageResource(R.drawable.icon_billing_payments);
						im3.setImageResource(R.drawable.icon_rewards_offers);

						// setTitle("My Account");
						// actionBar.setTitle("My Account");
						mainTitle.setText("My Account");

						break;
					case 1:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments_selected);
						im3.setImageResource(R.drawable.icon_rewards_offers);

						// actionBar.setTitle("Billing and Payments");
						mainTitle.setText("Billing and Payments");

						break;
					case 2:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments);
						im3.setImageResource(R.drawable.icon_rewards_offers_selected);

						// actionBar.setTitle("Rewards and Offers");
						mainTitle.setText("Rewards and Offers");

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

	TextView	mainTitle;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void openMyAccount(View v)
	{
		pager.setCurrentItem(0);
	}

	public void openBillingPayment(View v)
	{
		pager.setCurrentItem(1);
	}

	public void openRewardsOffers(View v)
	{
		pager.setCurrentItem(2);
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft)
	{
		// tab.setIcon(R.drawable.icon_account_selected);
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

		// private final int[] ICONS = { R.drawable.icon_account, R.drawable.icon_billing_payments,
		// R.drawable.icon_rewards_offers };

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
	}
}