package com.itcorea.coreonmobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.viewpagerindicator.UnderlinePageIndicator;

@SuppressLint("NewApi")
public class CoreonMain extends SherlockFragmentActivity implements ActionBar.TabListener
{
	private ViewPager			pager;
	public MyViewPagerAdapter	viewPagerAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		pager = (ViewPager) findViewById(R.id.pager);
		// adapter = new MyPagerAdapter(getSupportFragmentManager());
		// pager.setAdapter(adapter);

		viewPagerAdapter = new MyViewPagerAdapter();
		pager.setAdapter(viewPagerAdapter);

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
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
	}

	public class MyViewPagerAdapter extends PagerAdapter
	{
		// TODO ViewPagerAdaptor
		//
		//
		//
		
		Context	con;
		View	view;

		public int getCount()
		{
			return 3;
		}

		public Object instantiateItem(View collection, int position)
		{
			LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			con = collection.getContext();
			view = collection;

			int resId = 0;
			switch (position)
			{
				case 0:
					// menu drawer
					resId = R.layout.tab_my_account;
					View view0 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(view0, 0);
					
					
					
					
					
					String accStatus = "ACTIVE";
					String creditStatus = "BAD";
					String contractStatus = "123 DAYS";
					
					String fullname = "Sheryl Lagman";
					String phoneNumber = "09123456789";
					String network = "Globe";
					
					String plan = "Plan 350 Unli Call to Smart or TNT";
					String supplementary = "Roaming Service";
					String subscriptionType = "Sim Card Only";
					String mobileUnit = "N/A";
					String enrollmentFee = "Installment";
					String contractStart = "November 29, 2013";
					String contractEnd = "November 28, 2015";
					String creditLimit = "P 1000";
					String activationDate = "November 29, 2013";
					String discount = "P 0.00";
					
					String fName = "Sheryl";
					String lName = "Lagman";
					String email = "email@yahoo.com";
					String otherEmail = "";
					String homeAddress = "11 Manalite Sta Cruz A.C";
					String zipCode = "1820";
					String landlinePh = "027856925";
					String landlineKr = "814665979";
					String otherMobile = "091848545656";
					String billingAddress = "Same as Home Address";
					
					
					
					
					ListView profileListView = (ListView)view0.findViewById(R.id.listViewProfileListView);
					ListViewArrayAdapter profileListViewAdaptor = new ListViewArrayAdapter(getApplicationContext(),new ArrayList<String>());
					
					profileListViewAdaptor.initiatizeStringsValues();
					//type title content image date
					profileListViewAdaptor.addValueExtra("my_account_status", "", "", "", "", accStatus, creditStatus, contractStatus, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValueExtra("my_account_info", "", "", "", "", fullname, phoneNumber, network, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_main_header_wshadow", "Subscription Details", "", "","");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Plan", plan, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Supplementary", supplementary, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Subscription Type", subscriptionType, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Mobile Unit", mobileUnit, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Enrollment Fee", enrollmentFee, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Contract Start", contractStart, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Contract End", contractEnd, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Credit Limit", creditLimit, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Activation Date", activationDate, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Discount", discount, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_main_header", "Personal Details", activationDate, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "First Name", fName, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Last Name", lName, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Email Address", email, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Other Email Address", otherEmail, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Home Address", homeAddress, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Zip Code", zipCode, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Landline (Ph)", landlinePh, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Landline (Kr)", landlineKr, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Other Mobile", otherMobile, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_sub_info", "Billing Address", billingAddress, "", "");
					
					
					profileListView.setAdapter(profileListViewAdaptor);
					profileListView.setDividerHeight(-1);
					
					
					return view0;
					
				case 1:
					// main home container
					resId = R.layout.my_account_info;
					View view1 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(view1, 0);

					// SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
					// String fname = prefs.getString("fname", null);
					// String lname = prefs.getString("lname", null);
					// String points = prefs.getString("points", null);
					return view1;

				case 2:
					resId = R.layout.my_account_status;
					View view2 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(view2, 0);
					return view2;

			}

			return resId;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == ((View) arg1);
		}

		@Override
		public Parcelable saveState()
		{
			return null;
		}
	}
}