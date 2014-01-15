package com.itcorea.coreonmobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.viewpagerindicator.UnderlinePageIndicator;

@SuppressLint("NewApi")
public class CoreonMain extends SherlockFragmentActivity implements ActionBar.TabListener
{
	private ViewPager			pager;
	public MyViewPagerAdapter	viewPagerAdapter;

	public ListView				listviewBillingPayments;	// = new
															// ListView(getApplicationContext());
	ListViewArrayAdapter billingListViewAdaptor;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		pager = (ViewPager) findViewById(R.id.pager);

		//TODO get context
		viewPagerAdapter = new MyViewPagerAdapter(this);
		pager.setAdapter(viewPagerAdapter);
		pager.setOffscreenPageLimit(2);
		pager.setPageMargin(10);

		UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setFades(false);
		indicator.setSelectedColor(0xFFFF6600); // orange color
		indicator.setBackgroundColor(0x00000000);
		indicator.setFadeDelay(1000);
		indicator.setFadeLength(1000);

		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(R.layout.mytitle);
		mainTitle = (TextView) findViewById(R.id.textViewTitle);

		viewPagerAdapter.initializeBillingPayments();
		listviewBillingPayments = viewPagerAdapter.getBillingPaymentsListView();

		billingListViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());
		billingListViewAdaptor.initiatizeStringsValues();
		//billingListViewAdaptor.addValueExtra("billing_payment_tab_menu", "", "", "", "", "", "", "", "", "");
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Account Summary", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payments(2)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black_shadow", "Outstanding Balance", "P 1,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_light_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black", "Available Credit", "P 0.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		
		
		View BillingPaymentsView = getLayoutInflater().inflate(R.layout.listview_header_billing_payment, null);
		listviewBillingPayments.addHeaderView(BillingPaymentsView);
		listviewBillingPayments.setAdapter(billingListViewAdaptor);
		listviewBillingPayments.setDividerHeight(-1);
		
		//openAccountSummary(null);

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

	// maintabs
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

	// billing payments sub tab
	// accountsummary
	// billingrecord
	// billingstatements
	// paymentrecord
	// reportpayment
	// paymentoptions


	// subtabs
	public void openAccountSummary(View v)
	{
		setDafaultAllSubTabs();

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layoutViewSubTabAccountSummaryRel);
		rl.setBackgroundColor(Color.parseColor("#ffae00")); // orange
		TextView tv = (TextView) findViewById(R.id.textViewSubTabAccountSummary);
		tv.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv = (ImageView) findViewById(R.id.imageViewSubTabAccountSummary);
		iv.setImageResource(R.drawable.icon_subtab_accountsummary_selected);
		
		
		
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Account Summary", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payments(2)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black_shadow", "Outstanding Balance", "P 1,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_light_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black", "Available Credit", "P 0.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();

	}

	public void openBillingRecord(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingRecordRel);
		rl2.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv2 = (TextView) findViewById(R.id.textViewSubTabBillingRecord);
		tv2.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv2 = (ImageView) findViewById(R.id.imageViewSubTabBillingRecord);
		iv2.setImageResource(R.drawable.icon_subtab_billingrecord_selected);
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Billing Record", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_record", "1", "October 2013", "November 02, 2013", "P 1,533.33");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_record", "12", "November 2013", "December 02, 2013", "P 2,000.00");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_record", "333", "December 2013", "January 02, 2013", "P 1,533.33");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_main_header_billing_record_total", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openBillingStatements(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingStatementsRel);
		rl3.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv3 = (TextView) findViewById(R.id.textViewSubTabBillingStatements);
		tv3.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv3 = (ImageView) findViewById(R.id.imageViewSubTabBillingStatements);
		iv3.setImageResource(R.drawable.icon_subtab_billingstatements_selected);

		
		
		
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Billing Statements", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_statements", "1", "December 2013", "January 02, 2013", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_statements", "2", "January 2013", "December 02, 2013", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_statements", "3", "November 2013", "October 02, 2013", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openPaymentRecord(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl4 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentRecordRel);
		rl4.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv4 = (TextView) findViewById(R.id.textViewSubTabPaymentRecord);
		tv4.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv4 = (ImageView) findViewById(R.id.imageViewSubTabPaymentRecord);
		iv4.setImageResource(R.drawable.icon_subtab_paymentrecord_selected);
		
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Record", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payments(2)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openReportPayment(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl5 = (RelativeLayout) findViewById(R.id.layoutViewSubTabReportPaymentRel);
		rl5.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv5 = (TextView) findViewById(R.id.textViewSubTabReportPayment);
		tv5.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv5 = (ImageView) findViewById(R.id.imageViewSubTabReportPayment);
		iv5.setImageResource(R.drawable.icon_subtab_reportpayment_selected);
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Report Payment", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payments(2)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openPaymentOptions(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl6 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentOptionsRel);
		rl6.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv6 = (TextView) findViewById(R.id.textViewSubTabPaymentOptions);
		tv6.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv6 = (ImageView) findViewById(R.id.imageViewSubTabPaymentOptions);
		iv6.setImageResource(R.drawable.icon_subtab_paymentoptions_selected);
		
		
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Options", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payments(2)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		billingListViewAdaptor.notifyDataSetChanged();
	}
	
	public void setDafaultAllSubTabs()
	{
		RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.layoutViewSubTabAccountSummaryRel);
		rl1.setBackgroundColor(Color.parseColor("#ffffff"));
		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingRecordRel);
		rl2.setBackgroundColor(Color.parseColor("#ffffff"));
		RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingStatementsRel);
		rl3.setBackgroundColor(Color.parseColor("#ffffff"));
		RelativeLayout rl4 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentRecordRel);
		rl4.setBackgroundColor(Color.parseColor("#ffffff"));
		RelativeLayout rl5 = (RelativeLayout) findViewById(R.id.layoutViewSubTabReportPaymentRel);
		rl5.setBackgroundColor(Color.parseColor("#ffffff"));
		RelativeLayout rl6 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentOptionsRel);
		rl6.setBackgroundColor(Color.parseColor("#ffffff"));
		
		TextView tv1 = (TextView) findViewById(R.id.textViewSubTabAccountSummary);
		tv1.setTextColor(Color.parseColor("#666666"));
		TextView tv2 = (TextView) findViewById(R.id.textViewSubTabBillingRecord);
		tv2.setTextColor(Color.parseColor("#666666"));
		TextView tv3 = (TextView) findViewById(R.id.textViewSubTabBillingStatements);
		tv3.setTextColor(Color.parseColor("#666666"));
		TextView tv4 = (TextView) findViewById(R.id.textViewSubTabPaymentRecord);
		tv4.setTextColor(Color.parseColor("#666666"));
		TextView tv5 = (TextView) findViewById(R.id.textViewSubTabReportPayment);
		tv5.setTextColor(Color.parseColor("#666666"));
		TextView tv6 = (TextView) findViewById(R.id.textViewSubTabPaymentOptions);
		tv6.setTextColor(Color.parseColor("#666666"));
		
		ImageView iv1 = (ImageView) findViewById(R.id.imageViewSubTabAccountSummary);
		iv1.setImageResource(R.drawable.icon_subtab_accountsummary);
		ImageView iv2 = (ImageView) findViewById(R.id.imageViewSubTabBillingRecord);
		iv2.setImageResource(R.drawable.icon_subtab_billingrecord);
		ImageView iv3 = (ImageView) findViewById(R.id.imageViewSubTabBillingStatements);
		iv3.setImageResource(R.drawable.icon_subtab_billingstatements);
		ImageView iv4 = (ImageView) findViewById(R.id.imageViewSubTabPaymentRecord);
		iv4.setImageResource(R.drawable.icon_subtab_paymentrecord);
		ImageView iv5 = (ImageView) findViewById(R.id.imageViewSubTabReportPayment);
		iv5.setImageResource(R.drawable.icon_subtab_reportpayment);
		ImageView iv6 = (ImageView) findViewById(R.id.imageViewSubTabPaymentOptions);
		iv6.setImageResource(R.drawable.icon_subtab_paymentoptions);
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

		Context		context;
		View		view;

		View		viewBillingPayments;
		ListView	listViewMyAccount;
		ListView	listViewBillinPayments;
		ListView	listViewRewardsOffers;

		public MyViewPagerAdapter(Context contextConstructor)
		{
			context = contextConstructor;
		}

		public ListView getMyAccountListView()
		{
			return listViewMyAccount;
		}

		public ListView getBillingPaymentsListView()
		{
			return listViewBillinPayments;
		}

		public ListView getRewardsOffersView()
		{
			return listViewRewardsOffers;
		}

		public void initializeBillingPayments()
		{
			int resId1 = R.layout.tab_billing_payment;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view1 = inflater.inflate(resId1, null);

			ListView billingListView = (ListView) view1.findViewById(R.id.listViewBillingPayment);
			listViewBillinPayments = billingListView;
			viewBillingPayments = view1;
		}

		public int getCount()
		{
			return 3;
		}

		public Object instantiateItem(View collection, int position)
		{
			LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			context = collection.getContext();
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

					ListView profileListView = (ListView) view0.findViewById(R.id.listViewProfileListView);
					ListViewArrayAdapter profileListViewAdaptor = new ListViewArrayAdapter(getApplicationContext(), new ArrayList<String>());

					profileListViewAdaptor.initiatizeStringsValues();
					// type title content image date
					profileListViewAdaptor.addValueExtra("my_account_status", "", "", "", "", accStatus, creditStatus, contractStatus, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValueExtra("my_account_info", "", "", "", "", fullname, phoneNumber, network, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValue("listview_main_header_wshadow", "Subscription Details", "", "", "");
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
					
					((ViewPager) collection).addView(viewBillingPayments, 0);
					return viewBillingPayments;

				case 2:
					resId = R.layout.test;
					View view2 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(view2, 0);

					// SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
					// String fname = prefs.getString("fname", null);
					// String lname = prefs.getString("lname", null);
					// String points = prefs.getString("points", null);

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
	}
}