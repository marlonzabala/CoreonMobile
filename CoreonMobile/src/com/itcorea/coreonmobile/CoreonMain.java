package com.itcorea.coreonmobile;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
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
		billingListViewAdaptor.addValue("listview_billing_record", "1", "October 2013", "November 02, 2013", "P 1,533.33");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_record", "12", "November 2013", "December 02, 2013", "P 2,000.00");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_billing_record", "333", "December 2013", "January 02, 2013", "P 1,533.33");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_main_header_billing_record_total", "Total Billing Amount", "P 4,000.00", "", "");
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

		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Options", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		// info
		billingListViewAdaptor.addType("listview_bank_deposit_how_to_info");
		billingListViewAdaptor.addType("listview_line_gray");
		// BDO
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "", String.valueOf(R.drawable.icon_payment_option_bank_bdo), "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "001688032543", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "BNORPHMM", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		// UnionBank
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
		// Citibank
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
		// TODO
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

					// get from net information
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

					// SQL to get all information

					// SELECT * , CONCAT(COALESCE(smart.issued_no, ''),COALESCE(globe.issued_no,
					// '')) as Numberssss
					// FROM billing_accounts
					// LEFT JOIN smart ON billing_accounts.mobile_no=smart.issued_no
					// LEFT JOIN globe ON billing_accounts.mobile_no=globe.issued_no

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

	public class FixedSpeedScroller extends Scroller
	{
		private int	mDuration	= 500;

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