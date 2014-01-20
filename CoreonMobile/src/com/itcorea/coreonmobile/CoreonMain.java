package com.itcorea.coreonmobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.viewpagerindicator.UnderlinePageIndicator;

@SuppressLint("NewApi")
public class CoreonMain extends SherlockFragmentActivity implements ActionBar.TabListener
{
	// private MyViewPager pager;
	private ViewPager			pager;
	public MyViewPagerAdapter	viewPagerAdapter;

	public ListView				listviewmMyAccounts;
	public ListView				listviewRewardsOffers;
	public ListView				listviewBillingPayments;

	ListViewArrayAdapter		myAccountListViewAdaptor;
	ListViewArrayAdapter		billingListViewAdaptor;
	ListViewArrayAdapter		rewardsListViewAdaptor;

	String						phoneNumber	= "";

	String						ipAdd		= "192.168.123.111";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		pager = (ViewPager) findViewById(R.id.pager);

		// TODO get context
		viewPagerAdapter = new MyViewPagerAdapter(this);
		pager.setAdapter(viewPagerAdapter);
		pager.setOffscreenPageLimit(2);
		pager.setPageMargin(10);

		UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setFades(false);
		indicator.setSelectedColor(0xFFFF6600); // orange color
		indicator.setBackgroundColor(0x00000000);
		indicator.setFadeDelay(1000);// dont know if still needed
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
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Account Summary", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", "3 Bill(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payment(s)", "", "");
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

		// pager.setPageMargin(-50);
		// openAccountSummary(null);

		// rewards initial view
		listviewRewardsOffers = viewPagerAdapter.getRewardsOffersListView();
		rewardsListViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());
		rewardsListViewAdaptor.initiatizeStringsValues();
		rewardsListViewAdaptor.addValue("listview_main_header_wshadow", "Rewards", "", "", "");
		rewardsListViewAdaptor.addType("listview_line_gray");

		View RewardsOffersView = getLayoutInflater().inflate(R.layout.listview_header_rewards_offers, null);
		listviewRewardsOffers.addHeaderView(RewardsOffersView);
		listviewRewardsOffers.setAdapter(rewardsListViewAdaptor);
		listviewRewardsOffers.setDividerHeight(-1);

		try
		{
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new DecelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
			mScroller.set(pager, scroller);
		}
		catch (Exception e)
		{
		}

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
						mainTitle.setText("My Account");

						break;
					case 1:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments_selected);
						im3.setImageResource(R.drawable.icon_rewards_offers);
						mainTitle.setText("Billing and Payments");

						// openAccountSummary(null);

						break;
					case 2:
						im1.setImageResource(R.drawable.icon_account);
						im2.setImageResource(R.drawable.icon_billing_payments);
						im3.setImageResource(R.drawable.icon_rewards_offers_selected);
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
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", "2 Payment(s)", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Billing Amount", "P 5,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black_shadow", "Outstanding Balance", "P 1,811.77", "", "");
		billingListViewAdaptor.addType("listview_line_light_gray");
		billingListViewAdaptor.addValue("listview_sub_info_large_black", "Available Credit", "P 0.00", "", "");
		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		// billingListViewAdaptor.notifyDataSetChanged();
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

		new getBillingRecord().execute("");

		// billingListViewAdaptor.addValue("listview_billing_record", "1", "October 2013",
		// "November 02, 2013", "P 1,533.33");
		// billingListViewAdaptor.addType("listview_line_gray");
		// billingListViewAdaptor.addValue("listview_billing_record", "12", "November 2013",
		// "December 02, 2013", "P 2,000.00");
		// billingListViewAdaptor.addType("listview_line_gray");
		// billingListViewAdaptor.addValue("listview_billing_record", "333", "December 2013",
		// "January 02, 2013", "P 1,533.33");
		// billingListViewAdaptor.addType("listview_line_gray");
		// billingListViewAdaptor.addValue("listview_main_header_billing_record_total",
		// "Total Billing Amount", "P 4,000.00", "", "");
		// billingListViewAdaptor.addType("listview_line_gray");
		// billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
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
//		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Billing Statements", "", "", "");
//		billingListViewAdaptor.addType("listview_line_gray");
//		billingListViewAdaptor.addValue("listview_billing_statements", "1", "December 2013", "January 02, 2013", "");
//		billingListViewAdaptor.addType("listview_line_gray");
//		billingListViewAdaptor.addValue("listview_billing_statements", "2", "January 2013", "December 02, 2013", "");
//		billingListViewAdaptor.addType("listview_line_gray");
//		billingListViewAdaptor.addValue("listview_billing_statements", "3", "November 2013", "October 02, 2013", "");
//		billingListViewAdaptor.addType("listview_line_gray");
//		billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
//		billingListViewAdaptor.notifyDataSetChanged();
		
		new getBillingStatements().execute("");
		
		//TODO current work
		// http://my.coreonmobile.com/account/layout/billing_download.php?filename=9998863057_MARCH_2013.pdf&mobile_no=9998863057
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
		billingListViewAdaptor.addValueExtra("listview_payment_record", "1", "July 23, 2013", "11/22/2013", "Over the Counter", "BDO", "Makati",
				"65629599666", "P 5,519.02", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValueExtra("listview_payment_record", "2", "July 23, 2013", "11/22/2013", "Over the Counter", "BDO", "Makati",
				"65629599666", "P 5,519.02", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValueExtra("listview_payment_record", "4", "July 22, 2013", "01/22/2013", "Over the Counter", "BDO", "Makati",
				"65629599666", "P 5,519.02", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_main_header_billing_record_total", "Total Payment Amount", "P 4,000.00", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
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
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openPaymentOptions(View v)
	{
		setDafaultAllSubTabs();
		RelativeLayout rl6 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentOptionsRel);
		rl6.setBackgroundColor(Color.parseColor("#ffae00"));
		rl6.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv6 = (TextView) findViewById(R.id.textViewSubTabPaymentOptions);
		tv6.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv6 = (ImageView) findViewById(R.id.imageViewSubTabPaymentOptions);
		iv6.setImageResource(R.drawable.icon_subtab_paymentoptions_selected);

		// hard coded, never small
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Options", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addType("listview_bank_deposit_how_to_info");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "", String.valueOf(R.drawable.icon_payment_option_bank_bdo), "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "001688032543", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "BNORPHMM", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "", String.valueOf(R.drawable.icon_payment_option_bank_unionbank),
				"");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "001-001-011420-8", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "DOLLAR ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "130010019410", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "UBHPHM", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "", String.valueOf(R.drawable.icon_payment_option_bank_citibank),
				"");
		billingListViewAdaptor.addValue("listview_space", "5", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "Jin Su Kim", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "441-07516-261-01", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void setDafaultAllSubTabs()
	{
		// apply plain design on all tabs in billing
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

	public void openRewards(View v)
	{
		RelativeLayout rl6 = (RelativeLayout) findViewById(R.id.layoutViewSubTabRewardsRel);
		rl6.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv6 = (TextView) findViewById(R.id.textViewSubTabRewards);
		tv6.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv6 = (ImageView) findViewById(R.id.imageViewSubTabRewards);
		iv6.setImageResource(R.drawable.icon_subtab_rewards_selected);

		RelativeLayout rl7 = (RelativeLayout) findViewById(R.id.layoutViewSubTabOffersRel);
		rl7.setBackgroundColor(Color.parseColor("#ffffff"));
		TextView tv7 = (TextView) findViewById(R.id.textViewSubTabOffers);
		tv7.setTextColor(Color.parseColor("#666666"));
		ImageView iv7 = (ImageView) findViewById(R.id.imageViewSubTabOffers);
		iv7.setImageResource(R.drawable.icon_subtab_offers);

		rewardsListViewAdaptor.initiatizeStringsValues();
		rewardsListViewAdaptor.addValue("listview_main_header_wshadow", "Rewads", "", "", "");
		rewardsListViewAdaptor.addType("listview_line_gray");
		rewardsListViewAdaptor.addType("listview_rewards_warning");
		rewardsListViewAdaptor.notifyDataSetChanged();
	}

	public void openOffers(View v)
	{
		RelativeLayout rl7 = (RelativeLayout) findViewById(R.id.layoutViewSubTabOffersRel);
		rl7.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv7 = (TextView) findViewById(R.id.textViewSubTabOffers);
		tv7.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv7 = (ImageView) findViewById(R.id.imageViewSubTabOffers);
		iv7.setImageResource(R.drawable.icon_subtab_offers_selected);

		RelativeLayout rl6 = (RelativeLayout) findViewById(R.id.layoutViewSubTabRewardsRel);
		rl6.setBackgroundColor(Color.parseColor("#ffffff"));
		TextView tv6 = (TextView) findViewById(R.id.textViewSubTabRewards);
		tv6.setTextColor(Color.parseColor("#666666"));
		ImageView iv6 = (ImageView) findViewById(R.id.imageViewSubTabRewards);
		iv6.setImageResource(R.drawable.icon_subtab_rewards);

		rewardsListViewAdaptor.initiatizeStringsValues();
		rewardsListViewAdaptor.addValue("listview_main_header_wshadow", "Rewards", "", "", "");

		for (int i = 0; i < 20; i++)
		{
			rewardsListViewAdaptor.addType("listview_line_gray");
			rewardsListViewAdaptor.addValue("listview_offers", " Dong Won Restaurant", "Get 50% payment of Coreon Card", "image",
					"August 25, 2013 at 11:30 pm");
		}

		rewardsListViewAdaptor.addType("listview_line_gray");
		rewardsListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		rewardsListViewAdaptor.notifyDataSetChanged();
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1)
	{

	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft)
	{

	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
	{

	}

	public class MyViewPagerAdapter extends PagerAdapter
	{
		Context		context;
		View		view;

		View		viewMyAccount;
		View		viewBillingPayments;
		View		viewRewardsOffers;
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

		public ListView getRewardsOffersListView()
		{
			return listViewRewardsOffers;
		}

		public void initializeBillingPayments()
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			int resId0 = R.layout.tab_rewards_offers;
			View view0 = inflater.inflate(resId0, null);
			ListView accountsListView = (ListView) view0.findViewById(R.id.listViewRewardsOffers);
			listViewMyAccount = accountsListView;
			viewMyAccount = view0;

			int resId1 = R.layout.tab_billing_payment;
			View view1 = inflater.inflate(resId1, null);
			ListView billingListView = (ListView) view1.findViewById(R.id.listViewBillingPayment);
			listViewBillinPayments = billingListView;
			viewBillingPayments = view1;

			int resId2 = R.layout.tab_rewards_offers;
			View view2 = inflater.inflate(resId2, null);
			ListView rewardsListView = (ListView) view2.findViewById(R.id.listViewRewardsOffers);
			listViewRewardsOffers = rewardsListView;
			viewRewardsOffers = view2;
		}

		public int getCount()
		{
			return 3;
		}

		public String getStringDate(String date)
		{
			date = date.replaceAll(" 00:00:00", "");
			Date dateFormat = null;
			String fullDate = "";
			try
			{
				dateFormat = new SimpleDateFormat("yyyy-d-MM", Locale.ENGLISH).parse(date);
				SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
				fullDate = df.format(dateFormat);
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			return fullDate;
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
					resId = R.layout.tab_my_account;
					View view0 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(view0, 0);

					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
					// String fname = prefs.getString("fname", null);
					// String lname = prefs.getString("lname", null);
					// String points = prefs.getString("points", null);

					// get from net information
					String accStatus = prefs.getString("contract_status", "null");
					String creditStatus = prefs.getString("credit_status", "null");
					String contractStatus = prefs.getString("days_left", "null");

					String fullname = prefs.getString("first_name", "null") + " " + prefs.getString("last_name", "null");
					phoneNumber = prefs.getString("mobile_number", "null");
					String network = prefs.getString("mobile_network", "null");

					String plan = prefs.getString("plan_title", "null");
					String supplementary = prefs.getString("supplementary_service", "null");
					String subscriptionType = prefs.getString("subscription_type", "null");
					String mobileUnit = prefs.getString("mobile_unit", "null");
					String enrollmentFee = prefs.getString("enrollment_fee", "null");
					String contractStart = prefs.getString("date_of_contract_start", "null");
					String contractEnd = prefs.getString("date_of_contract_end", "null");
					String creditLimit = prefs.getString("credit_limit", "null");
					String activationDate = prefs.getString("activation_date", "null");
					String discount = prefs.getString("discount", "null");

					String fName = prefs.getString("first_name", "null");
					String lName = prefs.getString("last_name", "null");
					String email = prefs.getString("primary_email_address", "null");
					String otherEmail = prefs.getString("secondary_email_address", "null");
					String homeAddress = prefs.getString("home_address", "null");
					String zipCode = prefs.getString("zip_code", "null");
					String landlinePh = prefs.getString("ph_tel", "null");
					String landlineKr = prefs.getString("kr_tel", "null");
					String otherMobile = prefs.getString("other_mobile", "null");
					String billingAddress = prefs.getString("billing_address", "null");

					if (mobileUnit.equals(""))
						mobileUnit = "N/A";
					creditLimit = "P " + creditLimit;
					discount = "P " + discount;

					if (contractStatus.equals("1"))
						contractStatus = contractStatus + " DAY";
					else
						contractStatus = contractStatus + " DAYS";

					contractStart = getStringDate(contractStart);
					contractEnd = getStringDate(contractEnd);

					ListView profileListView = (ListView) view0.findViewById(R.id.listViewProfileListView);
					ListViewArrayAdapter profileListViewAdaptor = new ListViewArrayAdapter(getApplicationContext(), new ArrayList<String>());

					profileListViewAdaptor.initiatizeStringsValues();
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

					// resId = R.layout.test;
					// View view1 = inflater.inflate(resId, null);
					// ((ViewPager) collection).addView(view1, 0);
					// return view1;

				case 2:
					// resId = R.layout.tab_rewards_offers;
					// View view2 = inflater.inflate(resId, null);
					((ViewPager) collection).addView(viewRewardsOffers, 0);

					// SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
					// String fname = prefs.getString("fname", null);
					// String lname = prefs.getString("lname", null);
					// String points = prefs.getString("points", null);

					return viewRewardsOffers;
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

	boolean	timeout;
	boolean	network;
	int		timeoutsec	= 5000;

	private String sendPost(String httpAddress)
	{
		timeout = false;
		String result = "";
		StringBuilder sb = null;
		InputStream is = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		// check for network connection
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected()))
		{
			// Toast.makeText(getApplicationContext(), "No internet Conenction",
			// Toast.LENGTH_LONG).show();
			network = false;
			return "";
		}
		else
		{
			network = true;
			timeout = false;
			try
			{
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutsec);
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutsec);
				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost(httpAddress);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
			catch (ConnectTimeoutException e)
			{
				// timeout connection
				timeout = true;
				Log.e("logs1", "Timeout");
				return "";
			}
			catch (Exception e)
			{
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
				sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");

				String line = "0";
				while ((line = reader.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			}
			catch (Exception e)
			{
				Log.e("log_tag", "Error converting result " + e.toString());
			}
		}
		return result;
	}

	private class getBillingRecord extends AsyncTask<String, Void, String>
	{
		List<String[]>	rowList;

		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... params)
		{
			try
			{
				String httpAddress = "http://" + ipAdd + "/android/coreonmobile_billingrecord.php?mobile=" + phoneNumber;
				Log.i("urlPost", httpAddress.toString());
				JSONArray jArray = null;
				JSONObject json_data = null;
				jArray = new JSONArray(sendPost(httpAddress));

				rowList = new ArrayList<String[]>();
				for (int i = 0; i < jArray.length(); i++)
				{
					json_data = jArray.getJSONObject(i);
					rowList.add(new String[] { json_data.getString("billing_month"), json_data.getString("billing_day"),
							json_data.getString("billing_year"), json_data.getString("billing_due_month"), json_data.getString("billing_due_day"),
							json_data.getString("billing_due_year"), json_data.getString("billing_amount") });
				}

				double total = (double) 0.0;// = Float.parseFloat("25");
				NumberFormat anotherFormat = NumberFormat.getNumberInstance(Locale.US);
				DecimalFormat anotherDFormat = (DecimalFormat) anotherFormat;
				anotherDFormat.applyPattern("#.00");
				anotherDFormat.setGroupingUsed(true);
				anotherDFormat.setGroupingSize(3);

				for (int i = 0; i < rowList.size(); i++)
				{

					double amount = Double.parseDouble(rowList.get(i)[6].toString());
					total += amount;
					double roundOffAmount = Math.round(amount * 100.0) / 100.0;
					String stringAmount = anotherDFormat.format(roundOffAmount).toString();

					billingListViewAdaptor.addValue("listview_billing_record", String.valueOf(i + 1),
							rowList.get(i)[0].toString() + " " + rowList.get(i)[2].toString(),
							rowList.get(i)[3].toString() + " " + rowList.get(i)[4].toString() + ", " + rowList.get(i)[5].toString(), "P "
									+ stringAmount);
					billingListViewAdaptor.addType("listview_line_gray");

				}

				double roundOffTotalAmount = Math.round(total * 100.0) / 100.0;
				String stringTotalAmount = anotherDFormat.format(roundOffTotalAmount).toString();

				billingListViewAdaptor
						.addValue("listview_main_header_billing_record_total", "Total Billing Amount", "P " + stringTotalAmount, "", "");
				billingListViewAdaptor.addType("listview_line_gray");
				billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");

			}
			catch (Exception e1)
			{
				Log.e("Exception", "Thread  exception " + e1);
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result)
		{
			billingListViewAdaptor.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{

		}
	}

	private List<String[]> getDataArrayFromJsonString(String jsonString)
	{
		List<String[]> rowList;
		rowList = new ArrayList<String[]>();

		try
		{
			JSONArray jArray = null;
			jArray = new JSONArray(jsonString);

			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++)
			{
				json_data = jArray.getJSONObject(i);
				
				
				
				//get json column names
				//Log.e("json length",String.valueOf(json_data.length()));
				//json_data.names();
				
				rowList.add(new String[] { json_data.getString("0"), json_data.getString("1"),
						json_data.getString("2"), json_data.getString("billing_due_month"), json_data.getString("billing_due_day"),
						json_data.getString("billing_due_year"), json_data.getString("billing_amount") });
				
				
			}

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return rowList;
	}

	private class getBillingStatements extends AsyncTask<String, Void, String>
	{
		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... params)
		{
			List<String[]> rowList;
			
			try
			{
				String httpAddress = "http://" + ipAdd + "/android/coreonmobile_billingstatements.php?mobile=" + phoneNumber;
				Log.e("urlPost billingDownloadUrl", httpAddress.toString());
				
				String jsonString = sendPost(httpAddress);
				rowList = getDataArrayFromJsonString(jsonString);
				
				Log.e("billingDownloadUrl rowlist", rowList.toString());
				
				for (int i = 0; i < rowList.size(); i++)
				{
					//TODO current work
					//billingListViewAdaptor.addValue("listview_billing_statements", "1", "December 2013", "January 02, 2013", "");
					
					String billingMonth = rowList.get(i)[3].toString() + " " + rowList.get(i)[4].toString();
					String billingDueDate = rowList.get(i)[6].toString();
					String billingDownloadUrl = "http://my.coreonmobile.com/account/layout/billing_download.php?filename=" + rowList.get(i)[2].toString() + "&mobile_no="+rowList.get(i)[1].toString();
					
					// http://my.coreonmobile.com/account/layout/billing_download.php?filename=9998863057_MARCH_2013.pdf&mobile_no=9998863057
					
					billingListViewAdaptor.addValue("listview_billing_statements", String.valueOf(i + 1), billingMonth, billingDueDate, billingDownloadUrl);
					billingListViewAdaptor.addType("listview_line_gray");
				}
				billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
			}
			catch (Exception e1)
			{
				Log.e("Exception", "Thread  exception " + e1);
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result)
		{
			billingListViewAdaptor.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{

		}
	}

	public class FixedSpeedScroller extends Scroller
	{
		private int	mDuration	= 500;	// speed of file

		public FixedSpeedScroller(Context context)
		{
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator)
		{
			super(context, interpolator);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel)
		{
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration)
		{
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy)
		{
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
	}
}