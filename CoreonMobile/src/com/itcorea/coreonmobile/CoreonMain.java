//Developer name: Marlon N. Zabala
//IT.Corea Programmer
//Start of Development - Jan 16, 2014
//Version Control: https://github.com/marlonzabala/CoreonMobile

package com.itcorea.coreonmobile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.viewpagerindicator.UnderlinePageIndicator;

@SuppressLint("NewApi")
public class CoreonMain extends SherlockFragmentActivity implements OnDateSetListener// implements
// ActionBar.TabListener
{
	View footerView;
	private ViewPager pager;
	public MyViewPagerAdapter viewPagerAdapter;
	
	//test

	public ListView listViewDrawer;
	public ListView listviewmMyAccounts;
	public ListView listviewRewardsOffers;
	public ListView listviewBillingPayments;

	ListViewArrayAdapter myAccountListViewAdaptor;
	ListViewArrayAdapter billingListViewAdaptor;
	ListViewArrayAdapter rewardsListViewAdaptor;
	ListViewArrayAdapter drawerlistViewAdaptor;

	RadioButton onlinePayment;
	RadioButton overTheCounter;

	String phoneNumber = "";
	String ipAdd = "125.5.16.155/coreonwallet/coreonmobile";
	String agency_code = "";
	// "192.168.123.111/android/coreonmobile";

	TextView mainTitle;

	ImageView im1;
	ImageView im2;
	ImageView im3;

	// "125.5.16.155/coreonwallet/coreonmobile";
	// String ipAdd = "125.5.16.155/coreonwallet";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		im1 = (ImageView) findViewById(R.id.imageViewTabMyAccount);
		im2 = (ImageView) findViewById(R.id.imageViewTabBillingPayment);
		im3 = (ImageView) findViewById(R.id.imageViewTabRewardsOffers);
		UnderlinePageIndicator indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		pager = (ViewPager) findViewById(R.id.pager);

		viewPagerAdapter = new MyViewPagerAdapter(this);
		pager.setAdapter(viewPagerAdapter);
		pager.setOffscreenPageLimit(2);
		pager.setPageMargin(10);

		indicator.setViewPager(pager);
		indicator.setFades(false);
		indicator.setSelectedColor(0xFFFF6600); // orange color
		indicator.setBackgroundColor(0x00000000);
		indicator.setFadeDelay(1000);// dont know if still needed
		indicator.setFadeLength(1000);

		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				// actionBar.setSelectedNavigationItem(position);

				switch (position) {
					case 0:
						openMyAccount(null);
						break;
					case 1:
						openBillingPayment(null);
						break;
					case 2:
						openRewardsOffers(null);
						break;
					default:
						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		actionBar.setCustomView(R.layout.layout_title);
		mainTitle = (TextView) findViewById(R.id.textViewTitle);

		// mainTitle = new TextView(this);

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.title_bg));

		actionBar.setHomeButtonEnabled(true);
		// actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setIcon(R.drawable.icon_account);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.icon_menu, R.string.hello_world,
				R.string.hello_world) {

			public void onDrawerClosed(View view) {
				mainTitle.setText(mainTitleText);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				mainTitleText = (String) mainTitle.getText();
				mainTitle.setText("Settings");
				getSupportActionBar().setTitle("test");
				supportInvalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.setDrawerIndicatorEnabled(false);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		viewPagerAdapter.initializeBillingPayments();
		listviewBillingPayments = viewPagerAdapter.getBillingPaymentsListView();
		// tempListViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());

		//
		// 502-5025
		// 09266503660
		// .196
		//

		mainTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// mDrawerLayout.openDrawer(R.id.drawer_layout);
				// toggleDrawer(null);
			}
		});

		billingListViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());

		View BillingPaymentsView = getLayoutInflater().inflate(R.layout.listview_header_billing_payment, null);
		listviewBillingPayments.addHeaderView(BillingPaymentsView);
		listviewBillingPayments.setDividerHeight(-1);

		footerView = getLayoutInflater().inflate(R.layout.listview_report_payment, null);

		// rewards initial view
		listviewRewardsOffers = viewPagerAdapter.getRewardsOffersListView();
		rewardsListViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());
		rewardsListViewAdaptor.initiatizeStringsValues();

		View RewardsOffersView = getLayoutInflater().inflate(R.layout.listview_header_rewards_offers, null);
		listviewRewardsOffers.addHeaderView(RewardsOffersView);
		listviewRewardsOffers.setAdapter(rewardsListViewAdaptor);
		listviewRewardsOffers.setDividerHeight(-1);
		listviewBillingPayments.setAdapter(billingListViewAdaptor);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new DecelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
			mScroller.set(pager, scroller);
		} catch (Exception e) {
		}

		if (savedInstanceState != null) {
			selectedTab = Integer.valueOf(savedInstanceState.getString("maintab"));
			selectedBillingPaymentsTab = Integer.valueOf(savedInstanceState.getString("billingpaymentab"));
			selectedRewardsOffersTab = Integer.valueOf(savedInstanceState.getString("rewardsoffersTab"));
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		phoneNumber = prefs.getString("mobile_number", "null");
		setSelectedTab();

		listViewDrawer = (ListView) findViewById(R.id.listview_drawer);

		// listViewDrawer.setAdapter(adapter);

		drawerlistViewAdaptor = new ListViewArrayAdapter(this, new ArrayList<String>());
		drawerlistViewAdaptor.initiatizeStringsValues();
		// drawerlistViewAdaptor.addValue("listview_drawer_menu", "Edit / Update My Account", "",
		// String.valueOf(R.drawable.icon_drawer_edit), "");
		drawerlistViewAdaptor.addType("listview_line_gray");
		drawerlistViewAdaptor.addValue("listview_drawer_menu", "Logout", "",
				String.valueOf(R.drawable.icon_drawer_logout), "");
		drawerlistViewAdaptor.addType("listview_line_gray");
		drawerlistViewAdaptor.addType("listview_drawer_info");
		drawerlistViewAdaptor.addType("listview_line_gray");

		listViewDrawer.setAdapter(drawerlistViewAdaptor);
		listViewDrawer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				switch (position) {
					case 1:

						// go to edit my account
						// Toast.makeText(getApplicationContext(), "Tester", Toast.LENGTH_SHORT).show();

						Logout();
						break;
					case 2:

						break;

					default:
						break;
				}
			};
		});

		onlinePayment = (RadioButton) footerView.findViewById(R.id.radioOnlinePayment);
		overTheCounter = (RadioButton) footerView.findViewById(R.id.radioOverTheCounter);

		textMonth = (TextView) footerView.findViewById(R.id.textViewMonthValue);
		textDay = (TextView) footerView.findViewById(R.id.textViewDayValue);
		textYear = (TextView) footerView.findViewById(R.id.textViewYearValue);

		Calendar c = Calendar.getInstance();

		// set current date as default for report payment
		textMonth.setText(String.valueOf(c.get(Calendar.MONTH) + 1));
		textDay.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
		textYear.setText(String.valueOf(c.get(Calendar.YEAR)));

		stringDateOfPayment = String.valueOf(c.get(Calendar.MONTH) + 1) + "/"
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(c.get(Calendar.YEAR));
	}

	public void clickOnlinePayment(View v) {
		onlinePayment.setChecked(true);
		overTheCounter.setChecked(false);

		// Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
		return;
	}

	public void clickOverTheCounter(View v) {
		onlinePayment.setChecked(false);
		overTheCounter.setChecked(true);
		// Toast.makeText(getApplicationContext(), "tester 2", Toast.LENGTH_SHORT).show();
		return;
	}

	String mainTitleText = "My Account";

	public void toggleDrawer(View v) {
		// TODO

		mDrawerLayout.openDrawer(R.id.drawer_layout);
	}

	public void Logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(CoreonMain.this);
		builder.setMessage("Logout your account?").setTitle("Coreon Mobile");

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				logoutAccount();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog do nothing
			}
		});

		// build the dialog then show
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void logoutAccount() {
		// User clicked OK button

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();

		// set value of Logged In to false to invoke log in on screen on startup
		editor.putBoolean("LoggedIn", false);
		editor.commit();

		// finish this activity
		Intent newIntent = new Intent(CoreonMain.this, LogIn.class);
		startActivity(newIntent);
		// ((Activity) CoreonMain.this).finish();

		this.finish();
		return;
	}

	ActionBarDrawerToggle mDrawerToggle;
	DrawerLayout mDrawerLayout;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// maintabs
	public void openMyAccount(View v) {
		selectedTab = 0;
		im1.setImageResource(R.drawable.icon_account_selected);
		im2.setImageResource(R.drawable.icon_billing_payments);
		im3.setImageResource(R.drawable.icon_rewards_offers);
		mainTitle.setText("My Account");
		pager.setCurrentItem(0);
	}

	public void openBillingPayment(View v) {
		selectedTab = 1;
		im1.setImageResource(R.drawable.icon_account);
		im2.setImageResource(R.drawable.icon_billing_payments_selected);
		im3.setImageResource(R.drawable.icon_rewards_offers);
		mainTitle.setText("Billing and Payments");
		pager.setCurrentItem(1);
	}

	public void openRewardsOffers(View v) {
		selectedTab = 2;
		im1.setImageResource(R.drawable.icon_account);
		im2.setImageResource(R.drawable.icon_billing_payments);
		im3.setImageResource(R.drawable.icon_rewards_offers_selected);
		mainTitle.setText("Rewards and Offers");
		pager.setCurrentItem(2);
		// mDrawerLayout.openDrawer(R.id.drawer_layout);
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putString("maintab", String.valueOf(selectedTab));
		bundle.putString("billingpaymentab", String.valueOf(selectedBillingPaymentsTab));
		bundle.putString("rewardsoffersTab", String.valueOf(selectedRewardsOffersTab));
	}

	int selectedTab = 0;
	int selectedBillingPaymentsTab = 0;
	int selectedRewardsOffersTab = 0;

	public void setSelectedTab() {
		switch (selectedTab) {
			case 0:
				openMyAccount(null);
				break;
			case 1:
				openBillingPayment(null);
				break;
			case 2:
				openRewardsOffers(null);
				break;
			default:
				openMyAccount(null);
				break;
		}
	}

	public void setSelectedBillingPaymentsTab() {
		switch (selectedBillingPaymentsTab) {
			case 0:
				openAccountSummary(null);
				break;
			case 1:
				openBillingRecord(null);
				break;
			case 2:
				openBillingStatements(null);
				break;
			case 3:
				openPaymentRecord(null);
				break;
			case 4:
				openReportPayment(null);
				break;
			case 5:
				openPaymentOptions(null);
				break;
			default:
				openAccountSummary(null);
				break;
		}
	}

	public void setSelectedRewardsOffersTab() {
		switch (selectedRewardsOffersTab) {
			case 0:
				openRewards(null);
				break;
			case 1:
				openOffers(null);
				break;
			default:
				openRewards(null);
				break;
		}
	}

	public void openAccountSummary(View v) {
		selectedBillingPaymentsTab = 0;
		setDafaultAllSubTabs();

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layoutViewSubTabAccountSummaryRel);
		rl.setBackgroundColor(Color.parseColor("#ffae00")); // orange
		TextView tv = (TextView) findViewById(R.id.textViewSubTabAccountSummary);
		tv.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv = (ImageView) findViewById(R.id.imageViewSubTabAccountSummary);
		iv.setImageResource(R.drawable.icon_subtab_accountsummary_selected);

		new getAccountSummary().execute("");
	}

	public void openBillingRecord(View v) {
		selectedBillingPaymentsTab = 1;
		setDafaultAllSubTabs();

		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingRecordRel);
		rl2.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv2 = (TextView) findViewById(R.id.textViewSubTabBillingRecord);
		tv2.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv2 = (ImageView) findViewById(R.id.imageViewSubTabBillingRecord);
		iv2.setImageResource(R.drawable.icon_subtab_billingrecord_selected);

		new getBillingRecord().execute("");
	}

	public void openBillingStatements(View v) {
		// android.os.Debug.startMethodTracing("coreon");
		selectedBillingPaymentsTab = 2;
		setDafaultAllSubTabs();

		RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.layoutViewSubTabBillingStatementsRel);
		rl3.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv3 = (TextView) findViewById(R.id.textViewSubTabBillingStatements);
		tv3.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv3 = (ImageView) findViewById(R.id.imageViewSubTabBillingStatements);
		iv3.setImageResource(R.drawable.icon_subtab_billingstatements_selected);

		new getBillingStatements().execute();
	}

	public void openPaymentRecord(View v) {
		selectedBillingPaymentsTab = 3;
		setDafaultAllSubTabs();

		RelativeLayout rl4 = (RelativeLayout) findViewById(R.id.layoutViewSubTabPaymentRecordRel);
		rl4.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv4 = (TextView) findViewById(R.id.textViewSubTabPaymentRecord);
		tv4.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv4 = (ImageView) findViewById(R.id.imageViewSubTabPaymentRecord);
		iv4.setImageResource(R.drawable.icon_subtab_paymentrecord_selected);

		new getPaymentRecord().execute("");
	}

	public void openReportPayment(View v) {
		selectedBillingPaymentsTab = 4;
		setDafaultAllSubTabs();

		RelativeLayout rl5 = (RelativeLayout) findViewById(R.id.layoutViewSubTabReportPaymentRel);
		rl5.setBackgroundColor(Color.parseColor("#ffae00"));
		TextView tv5 = (TextView) findViewById(R.id.textViewSubTabReportPayment);
		tv5.setTextColor(Color.parseColor("#ffffff"));
		ImageView iv5 = (ImageView) findViewById(R.id.imageViewSubTabReportPayment);
		iv5.setImageResource(R.drawable.icon_subtab_reportpayment_selected);

		// new getPaymentOptions().execute();
		billingListViewAdaptor.initiatizeStringsValues();
		billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Report", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		// billingListViewAdaptor.addValue("listview_report_payment", "", "", "", "");

		listviewBillingPayments.addFooterView(footerView);
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void openPaymentOptions(View v) {
		selectedBillingPaymentsTab = 5;
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
		billingListViewAdaptor.addType("listview_bank_deposit_how_to_info_branch_payments");
		billingListViewAdaptor.addType("listview_line_gray");

		billingListViewAdaptor
				.addValue(
						"listview_bank_deposit_image_header",
						"14.5613973",
						"121.028455",
						String.valueOf(R.drawable.icon_payment_option_itcorea),
						"https://maps.google.com.ph/maps?q=14.561549,121.028169&ie=UTF-8&hq=&hnear=0x3397c900b1eb26c9:0x3d5a7c4a5ee60bf2,14.561549,121.028169&gl=ph&ei=8r7pUs61BqaWiQe0s4CIAg&ved=0CCQQ8gEwAA");

		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "IT.COREA INC. MAKATI MAIN OFFICE", "", "",
				"");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text", "UNIT 506 Executive Building Center",
				"", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text",
				"369 Sen Gil Puyat Ave. corner Makati Ave.", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text", "Makati City 1209", "", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text", "Telephone No: +63 2 511 1715", "", "",
				"");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text",
				"Mobile No (Globe) 0917 8530966 • (Smart) 0999 8879711", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text",
				"Email: <font color='#ff9600'>cs@coreonmobile.com</font>", "", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");

		billingListViewAdaptor
				.addValue(
						"listview_bank_deposit_image_header",
						"",
						"",
						String.valueOf(R.drawable.icon_payment_option_coreon_gate),
						"https://maps.google.com.ph/maps?ie=UTF-8&q=Station+168+Adriatico+Internet+Center&fb=1&gl=ph&hq=Station+168+Adriatico+Internet+Center,+1774+2nd+Flr+HRC+Building+M.Adriatico+Street+Malate,+Manila+City+1000,+Philippines");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "COREON GATE MALATE BRANCH", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text",
				"1774 2nd Flr HRC Building M. Adriatico Street", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text", "Malate, Manila City 1000", "", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text", "Telephone No: +63 2 521 6933", "", "",
				"");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info_text",
				"Email: <font color='#ff9600'>info@coreongate.ph</font>", "", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");

		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addType("listview_bank_deposit_how_to_info");
		billingListViewAdaptor.addType("listview_line_gray");

		billingListViewAdaptor
				.addValue("listview_bank_deposit_image_header", "", "",
						String.valueOf(R.drawable.icon_payment_option_bank_bdo),
						"https://www.bdo.com.ph/branches-atms-locator");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "001688032543", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "BNORPHMM", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "",
				String.valueOf(R.drawable.icon_payment_option_bank_unionbank),
				"http://www.unionbankph.com/index.php?option=com_content&id=1043&Itemid=505");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "00-001-011420-8", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_header", "DOLLAR ACCOUNT", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "IT.Corea Inc.", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "130010019410", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "UBHPHM", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "",
				String.valueOf(R.drawable.icon_payment_option_bank_citibank), "http://www.findmyciti.com/ph/");
		billingListViewAdaptor.addValue("listview_space", "5", "", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "Jin Su Kim", "", "");
		billingListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number", "441-07516-261-01", "", "");
		billingListViewAdaptor.addValue("listview_space", "30", "", "", "");
		billingListViewAdaptor.addType("listview_line_gray");
		billingListViewAdaptor.notifyDataSetChanged();
	}

	public void setDafaultAllSubTabs() {
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

		listviewBillingPayments.removeFooterView(footerView);
	}

	public void openRewards(View v) {
		selectedRewardsOffersTab = 0;
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

	public void openOffers(View v) {
		selectedRewardsOffersTab = 1;
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
		rewardsListViewAdaptor.addType("listview_line_gray");
		rewardsListViewAdaptor.addType("listview_rewards_warning");

		// for (int i = 0; i < 20; i++)
		// {
		// rewardsListViewAdaptor.addType("listview_line_gray");
		// rewardsListViewAdaptor.addValue("listview_offers", " Dong Won Restaurant",
		// "Get 50% payment of Coreon Card", "image",
		// "August 25, 2013 at 11:30 pm");
		// }
		//
		// rewardsListViewAdaptor.addType("listview_line_gray");
		// rewardsListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
		rewardsListViewAdaptor.notifyDataSetChanged();
	}

	// @Override
	// public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1)
	// {
	//
	// }
	//
	// @Override
	// public void onTabSelected(Tab tab, android.app.FragmentTransaction ft)
	// {
	//
	// }
	//
	// @Override
	// public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
	// {
	//
	// }

	public String getStringDate(String date) {
		if (date.equals("0000-00-00 00:00:00"))
			return "";
		date = date.replaceAll(" 00:00:00", "");
		Date dateFormat = null;
		String fullDate = "";
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
			SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
			fullDate = df.format(dateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fullDate;
	}

	public String capitalizeFirst(String text) {
		text = text.toLowerCase();
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		Context context;
		View view;

		View viewMyAccount;
		View viewBillingPayments;
		View viewRewardsOffers;
		ListView listViewMyAccount;
		ListView listViewBillinPayments;
		ListView listViewRewardsOffers;

		public MyViewPagerAdapter(Context contextConstructor) {
			context = contextConstructor;
		}

		public ListView getMyAccountListView() {
			return listViewMyAccount;
		}

		public ListView getBillingPaymentsListView() {
			return listViewBillinPayments;
		}

		public ListView getRewardsOffersListView() {
			return listViewRewardsOffers;
		}

		public void initializeBillingPayments() {
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

		public int getCount() {
			return 3;
		}

		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			context = collection.getContext();
			view = collection;

			int resId = 0;
			switch (position) {
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

					String fullname = prefs.getString("first_name", "null") + " "
							+ prefs.getString("last_name", "null");
					// phoneNumber = prefs.getString("mobile_number", "null");
					String network = prefs.getString("mobile_network", "null");
					String userId = prefs.getString("id", "null");
					String imageUrl = "http://my.coreonmobile.com/files/" + network.toLowerCase() + "/" + userId
							+ "-0.jpg";

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

					agency_code = prefs.getString("agency_code", "null");

					network = capitalizeFirst(network);

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
					ListViewArrayAdapter profileListViewAdaptor = new ListViewArrayAdapter(getApplicationContext(),
							new ArrayList<String>());

					profileListViewAdaptor.initiatizeStringsValues();
					profileListViewAdaptor.addValueExtra("my_account_status", "", "", "", "", accStatus.toUpperCase(),
							creditStatus.toUpperCase(), contractStatus, "", "");
					profileListViewAdaptor.addType("listview_line_gray");
					profileListViewAdaptor.addValueExtra("my_account_info", "", "", imageUrl, "", fullname,
							phoneNumber, network, "", "");
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

					// retain tab position
					// openAccountSummary(null);
					setSelectedBillingPaymentsTab();
					return viewBillingPayments;

				case 2:

					// SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
					// String fname = prefs.getString("fname", null);
					// String lname = prefs.getString("lname", null);
					// String points = prefs.getString("points", null);

					((ViewPager) collection).addView(viewRewardsOffers, 0);
					setSelectedRewardsOffersTab();
					return viewRewardsOffers;
			}
			return resId;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}
	}

	boolean timeout;
	boolean network;
	int timeoutsec = 5000;

	private void showConenctionStatus() {
		if (!network) {
			Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
		} else if (timeout) {
			Toast.makeText(getApplicationContext(), "Connection timeout, server might be down", Toast.LENGTH_SHORT)
					.show();
		}
		return;
	}

	private String sendPost(String httpAddress) {
		Log.e("Log sendpost", httpAddress);

		timeout = false;
		String result = "";
		StringBuilder sb = null;
		InputStream is = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		// check for network connection
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
			network = false;
			return "";
		} else {
			network = true;
			timeout = false;
			try {
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutsec);
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutsec);
				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost(httpAddress);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			} catch (ConnectTimeoutException e) {
				// timeout connection
				timeout = true;
				Log.e("logs1", "Timeout");
				return "";
			} catch (Exception e) {
				Log.e("log_tag 1", "Error in http connection " + e.toString());
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
				sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");

				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag 2", "Error converting result " + e.toString());
			}
		}
		return result;
	}

	private String getStringAmount(String stringValue) {
		if (stringValue.equals(""))
			return "P 0.00";
		Double value = Double.valueOf(stringValue);
		NumberFormat anotherFormat = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat anotherDFormat = (DecimalFormat) anotherFormat;
		anotherDFormat.applyPattern("#.00");
		anotherDFormat.setGroupingUsed(true);
		anotherDFormat.setGroupingSize(3);

		double roundOffAmount = Math.round(value * 100.0) / 100.0;
		if (roundOffAmount <= 0)
			return "P 0.00";
		String stringAmount = "P " + anotherDFormat.format(roundOffAmount).toString();

		return stringAmount;
	}

	private List<String[]> getDataArrayFromJsonString(String... jsonString) {
		// Gets the json string and converts it to a list of strings

		List<String[]> rowList;
		rowList = new ArrayList<String[]>();

		if (jsonString[0].equals(""))
			return rowList;

		try {
			JSONArray jArray = null;
			jArray = new JSONArray(jsonString[0]);

			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++) {
				// count
				json_data = jArray.getJSONObject(i);

				String[] stringContents = new String[jsonString.length];
				for (int j = 1; j < jsonString.length; j++) {
					if (jsonString[j].equals("count")) {
						stringContents[j - 1] = String.valueOf(jArray.length() - i);
					} else {
						stringContents[j - 1] = json_data.getString(jsonString[j]);
					}
				}
				rowList.add(stringContents);
			}
		} catch (JSONException e) {
			Log.e("getDataArrayFromJsonString error", e.toString());
		}

		return rowList;
	}

	private class getBillingRecord extends AsyncTask<String, Void, String> {
		List<String[]> rowList;

		@Override
		protected void onPreExecute() {
			billingListViewAdaptor.initiatizeStringsValues();
			billingListViewAdaptor.addValue("listview_main_header_wshadow", "Billing Record", "", "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addType("listview_loading");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				String httpAddress = "http://" + ipAdd + "/coreonmobile_billingrecord.php?mobile=" + phoneNumber;
				Log.i("urlPost", httpAddress.toString());

				String jsonString = sendPost(httpAddress);
				rowList = getDataArrayFromJsonString(jsonString, "billing_month", "billing_day", "billing_year",
						"billing_due_month", "billing_due_day", "billing_due_year", "billing_amount", "count");
			} catch (Exception e1) {
				Log.e("Exception 1", "Thread  exception " + e1);
			}

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			billingListViewAdaptor.removeLast();
			showConenctionStatus();

			double total = (double) 0.0;

			for (int i = 0; i < rowList.size(); i++) {

				double amount = Double.parseDouble(rowList.get(i)[6].toString());
				total += amount;
				String stringAmount = getStringAmount(rowList.get(i)[6].toString());

				billingListViewAdaptor.addValue("listview_billing_record", rowList.get(i)[7].toString(),
						rowList.get(i)[0].toString() + " " + rowList.get(i)[2].toString(), rowList.get(i)[3].toString()
								+ " " + rowList.get(i)[4].toString() + ", " + rowList.get(i)[5].toString(),
						stringAmount);
				billingListViewAdaptor.addType("listview_line_gray");
			}
			String stringTotalAmount = getStringAmount(String.valueOf(total));

			billingListViewAdaptor.addValue("listview_main_header_billing_record_total", "Total Billing Amount",
					stringTotalAmount, "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
			billingListViewAdaptor.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	// private class getPaymentOptions extends AsyncTask<String, Void, String>
	// {
	//
	// @Override
	// protected void onPreExecute()
	// {
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... params)
	// {
	// tempListViewAdaptor.initiatizeStringsValues();
	// tempListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Options", "", "", "");
	// tempListViewAdaptor.addType("listview_line_gray");
	// tempListViewAdaptor.addType("listview_bank_deposit_how_to_info");
	// tempListViewAdaptor.addType("listview_line_gray");
	// tempListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "",
	// String.valueOf(R.drawable.icon_payment_option_bank_bdo), "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name",
	// "IT.Corea Inc.", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number",
	// "001688032543", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "BNORPHMM", "",
	// "");
	// tempListViewAdaptor.addValue("listview_space", "30", "", "", "");
	// tempListViewAdaptor.addType("listview_line_gray");
	// tempListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "",
	// String.valueOf(R.drawable.icon_payment_option_bank_unionbank),
	// "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_header", "PESO ACCOUNT", "", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name",
	// "IT.Corea Inc.", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number",
	// "001-001-011420-8", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_header", "DOLLAR ACCOUNT", "", "",
	// "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name",
	// "IT.Corea Inc.", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number",
	// "130010019410", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Swift Code", "UBHPHM", "",
	// "");
	// tempListViewAdaptor.addValue("listview_space", "30", "", "", "");
	// tempListViewAdaptor.addType("listview_line_gray");
	// tempListViewAdaptor.addValue("listview_bank_deposit_image_header", "", "",
	// String.valueOf(R.drawable.icon_payment_option_bank_citibank),
	// "");
	// tempListViewAdaptor.addValue("listview_space", "5", "", "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Name", "Jin Su Kim",
	// "", "");
	// tempListViewAdaptor.addValue("listview_bank_deposit_sub_info", "Account Number",
	// "441-07516-261-01", "", "");
	// tempListViewAdaptor.addValue("listview_space", "30", "", "", "");
	// tempListViewAdaptor.addType("listview_line_gray");
	//
	// return "";
	// }
	//
	// @Override
	// protected void onPostExecute(String result)
	// {
	// // billingListViewAdaptor.initiatizeStringsValues();
	// // billingListViewAdaptor = tempListViewAdaptor;
	// // billingListViewAdaptor.notifyDataSetChanged();
	// }
	//
	// @Override
	// protected void onProgressUpdate(Void... values)
	// {
	//
	// }
	// }

	private class getAccountSummary extends AsyncTask<String, Void, String> {
		List<String[]> rowList;

		@Override
		protected void onPreExecute() {
			billingListViewAdaptor.clear();
			billingListViewAdaptor.initiatizeStringsValues();
			billingListViewAdaptor.addValue("listview_main_header_wshadow", "Account Summary", "", "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addType("listview_loading");
		}

		@Override
		protected String doInBackground(String... params) {

			String httpAddress = "http://" + ipAdd + "/coreonmobile_accountsummary.php?mobile=" + phoneNumber;
			Log.e("urlPost billingDownloadUrl 1", httpAddress.toString());

			String jsonString = sendPost(httpAddress);
			rowList = getDataArrayFromJsonString(jsonString, "totalBills", "totalBillingPayments",
					"totalBillingAmount", "totalPaymentAmount", "outstandingBalance", "availableCredit");

			Log.e("billingDownloadUrl rowlist 2", String.valueOf(rowList.size()));

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			billingListViewAdaptor.removeLast();
			showConenctionStatus();
			for (int i = 0; i < rowList.size(); i++) {
				String totalbillingAmount = getStringAmount(rowList.get(i)[2].toString());
				String totalPaymentAmount = getStringAmount(rowList.get(i)[3].toString());
				String outstandingBalance = getStringAmount(rowList.get(i)[4].toString());
				String availableCredit = getStringAmount(rowList.get(i)[5].toString());

				billingListViewAdaptor.addValue("listview_sub_info", "Total Bills", rowList.get(i)[0].toString()
						+ " Bill(s)", "", "");
				billingListViewAdaptor.addType("listview_line_gray");
				billingListViewAdaptor.addValue("listview_sub_info", "Total Payments", rowList.get(i)[1].toString()
						+ " Payment(s)", "", "");
				billingListViewAdaptor.addType("listview_line_gray");
				billingListViewAdaptor
						.addValue("listview_sub_info", "Total Billing Amount", totalbillingAmount, "", "");
				billingListViewAdaptor.addType("listview_line_gray");
				billingListViewAdaptor
						.addValue("listview_sub_info", "Total Payment Amount", totalPaymentAmount, "", "");
				billingListViewAdaptor.addType("listview_line_gray");
				billingListViewAdaptor.addValue("listview_sub_info_large_black_shadow", "Outstanding Balance",
						outstandingBalance, "", "");
				billingListViewAdaptor.addType("listview_line_light_gray");
				billingListViewAdaptor.addValue("listview_sub_info_large_black", "Available Credit", availableCredit,
						"", "");
			}
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
			billingListViewAdaptor.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	private class getBillingStatements extends AsyncTask<String, Void, String> {
		List<String[]> rowList;

		@Override
		protected void onPreExecute() {
			billingListViewAdaptor.initiatizeStringsValues();
			billingListViewAdaptor.addValue("listview_main_header_wshadow", "Billing Statements", "", "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addType("listview_loading");
		}

		@Override
		protected String doInBackground(String... params) {

			String httpAddress = "http://" + ipAdd + "/coreonmobile_billingstatements.php?mobile=" + phoneNumber;
			Log.e("urlPost billingDownloadUrl", httpAddress.toString());

			String jsonString = sendPost(httpAddress);
			rowList = getDataArrayFromJsonString(jsonString, "file_id", "mobile_no", "file_name", "file_month",
					"file_year", "billing_date", "due_date", "count");

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			billingListViewAdaptor.removeLast();
			showConenctionStatus();
			for (int i = 0; i < rowList.size(); i++) {
				String billingMonth = capitalizeFirst(rowList.get(i)[3].toString()) + " "
						+ rowList.get(i)[4].toString();
				String billingDueDate = rowList.get(i)[6].toString();
				billingDueDate = getStringDate(billingDueDate);
				String billingDownloadUrl = "http://my.coreonmobile.com/account/layout/billing_download.php?filename="
						+ rowList.get(i)[2].toString() + "&mobile_no=" + rowList.get(i)[1].toString();

				billingListViewAdaptor.addValue("listview_billing_statements", rowList.get(i)[7].toString(),
						billingMonth, billingDueDate, billingDownloadUrl);
				billingListViewAdaptor.addType("listview_line_gray");
			}
			billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
			billingListViewAdaptor.notifyDataSetChanged();
			// android.os.Debug.stopMethodTracing();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	private class getPaymentRecord extends AsyncTask<String, Void, String> {
		List<String[]> rowList;

		@Override
		protected void onPreExecute() {
			billingListViewAdaptor.initiatizeStringsValues();
			billingListViewAdaptor.addValue("listview_main_header_wshadow", "Payment Record", "", "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addType("listview_loading");
		}

		@Override
		protected String doInBackground(String... params) {
			String httpAddress = "http://" + ipAdd + "/coreonmobile_paymentrecord.php?mobile=" + phoneNumber;
			Log.e("urlPost billingDownloadUrl", httpAddress.toString());

			String jsonString = sendPost(httpAddress);
			rowList = getDataArrayFromJsonString(jsonString, "payment_id", "payment_date", "posted_date",
					"mode_of_payment", "bank_card_name", "bank_branch", "reference_no", "payment_amount", "count");

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			billingListViewAdaptor.removeLast();
			showConenctionStatus();
			double total = (double) 0.0;

			for (int i = 0; i < rowList.size(); i++) {
				double amount = Double.parseDouble(rowList.get(i)[7].toString());
				total += amount;

				String paymentDate = getStringDate(rowList.get(i)[1].toString());
				String postedDate = getStringDate(rowList.get(i)[2].toString());
				String stringAmount = getStringAmount(rowList.get(i)[7].toString());

				billingListViewAdaptor.addValueExtra("listview_payment_record", rowList.get(i)[8].toString(),
						paymentDate, postedDate, rowList.get(i)[3].toString(), rowList.get(i)[4].toString(),
						rowList.get(i)[5].toString(), rowList.get(i)[6].toString(), stringAmount, "");
				billingListViewAdaptor.addType("listview_line_gray");
			}

			String stringTotalAmount = getStringAmount(String.valueOf(total));

			billingListViewAdaptor.addValue("listview_main_header_billing_record_total", "Total Payment Amount",
					stringTotalAmount, "", "");
			billingListViewAdaptor.addType("listview_line_gray");
			billingListViewAdaptor.addValue("listview_ad", "ads", "", "", "");
			billingListViewAdaptor.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	// Upload file
	private int serverResponseCode = 0;
	private String upLoadServerUri = "";
	private String imagepath = "";

	public void selectImageForUpload(View v) {
		Toast.makeText(getApplicationContext(), "Select image for upload", Toast.LENGTH_SHORT).show();

		// changed intent action for kitkat bug
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 1);

		return;
	}

	String fileName;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Uri selectedImageUri = data.getData();
			imagepath = getRealPathFromURI(selectedImageUri);

			String timestamp = "" + System.currentTimeMillis() / 1000;
			fileName = timestamp + "_" + phoneNumber + ".jpg";

			try {
				Bitmap b = BitmapFactory.decodeFile(imagepath);

				String imagePath = getFilesDir().getAbsolutePath() + "/" + fileName;
				FileOutputStream out = new FileOutputStream(imagePath);
				b.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.close();

				imagepath = imagePath;
			} catch (IOException e) {
				e.printStackTrace();
			}

			Toast.makeText(getApplicationContext(), "Selected Image for upload", Toast.LENGTH_SHORT).show();
		}
	}

	TextView textMonth;
	TextView textDay;
	TextView textYear;

	public void openDateOfPayment(View v) {

		int year = Integer.parseInt(textYear.getText().toString()); // year
		int month = Integer.parseInt(textMonth.getText().toString()) - 1; // month
		int day = Integer.parseInt(textDay.getText().toString()); // day

		Bundle b = new Bundle();
		b.putInt(DatePickerDialogFragment.YEAR, year);
		b.putInt(DatePickerDialogFragment.MONTH, month);
		b.putInt(DatePickerDialogFragment.DATE, day);
		DialogFragment picker = new DatePickerDialogFragment();
		picker.setArguments(b);
		picker.show(getSupportFragmentManager(), "frag_date_picker");

	}

	public void sendReport(View v) {
		// TODO workkkk
		// fileName
		// imagePath

		CheckBox chkJan = (CheckBox) footerView.findViewById(R.id.checkBoxJan);
		CheckBox chkFeb = (CheckBox) footerView.findViewById(R.id.checkBoxFeb);
		CheckBox chkMar = (CheckBox) footerView.findViewById(R.id.checkBoxMar);
		CheckBox chkApr = (CheckBox) footerView.findViewById(R.id.checkBoxApr);
		CheckBox chkMay = (CheckBox) footerView.findViewById(R.id.checkBoxMay);
		CheckBox chkJun = (CheckBox) footerView.findViewById(R.id.checkBoxJun);
		CheckBox chkJul = (CheckBox) footerView.findViewById(R.id.checkBoxJul);
		CheckBox chkAug = (CheckBox) footerView.findViewById(R.id.checkBoxAug);
		CheckBox chkSep = (CheckBox) footerView.findViewById(R.id.checkBoxSep);
		CheckBox chkOct = (CheckBox) footerView.findViewById(R.id.checkBoxOct);
		CheckBox chkNov = (CheckBox) footerView.findViewById(R.id.checkBoxNov);
		CheckBox chkDec = (CheckBox) footerView.findViewById(R.id.checkBoxDec);

		String month = "";

		if (chkJan.isChecked()) {
			month = month + "January,";
		}
		if (chkFeb.isChecked()) {
			month = month + "February,";
		}
		if (chkMar.isChecked()) {
			month = month + "March,";
		}
		if (chkApr.isChecked()) {
			month = month + "April,";
		}
		if (chkMay.isChecked()) {
			month = month + "May,";
		}
		if (chkJun.isChecked()) {
			month = month + "June,";
		}
		if (chkJul.isChecked()) {
			month = month + "July,";
		}
		if (chkAug.isChecked()) {
			month = month + "August,";
		}
		if (chkSep.isChecked()) {
			month = month + "September,";
		}
		if (chkOct.isChecked()) {
			month = month + "October,";
		}
		if (chkNov.isChecked()) {
			month = month + "November,";
		}
		if (chkDec.isChecked()) {
			month = month + "December,";
		}

		month = removeLastChar(month);

		EditText textAmount = (EditText) footerView.findViewById(R.id.textViewAmount);
		EditText textBankName = (EditText) footerView.findViewById(R.id.textViewBankName);
		EditText textBranchName = (EditText) footerView.findViewById(R.id.textViewBranchName);
		EditText textRemarks = (EditText) footerView.findViewById(R.id.textViewRemarks);

		String amount = textAmount.getText().toString();
		String bankName = textBankName.getText().toString();
		String branchName = textBranchName.getText().toString();
		String remarks = textRemarks.getText().toString();

		String modePayment = "";

		if (onlinePayment.isChecked()) {
			modePayment = "Online Payment";
		}
		if (overTheCounter.isChecked()) {
			modePayment = "Over the Counter";
		}

		if (month.equals("")) {
			Toast.makeText(getApplicationContext(), "Please select month of payment", Toast.LENGTH_SHORT).show();
			return;
		} else if (stringDateOfPayment.equals("")) {
			Toast.makeText(getApplicationContext(), "Please select date of payment", Toast.LENGTH_SHORT).show();
			return;
		} else if (amount.equals("")) {
			Toast.makeText(getApplicationContext(), "Please enter payment amount", Toast.LENGTH_SHORT).show();
			return;
		} else if (modePayment.equals("")) {
			Toast.makeText(getApplicationContext(), "Please select mode of payment", Toast.LENGTH_SHORT).show();
			return;
		} else if (bankName.equals("")) {
			Toast.makeText(getApplicationContext(), "Please enter bank name", Toast.LENGTH_SHORT).show();
			return;
		} else if (branchName.equals("")) {
			Toast.makeText(getApplicationContext(), "Please enter branch name", Toast.LENGTH_SHORT).show();
			return;
		}

		if (modePayment.equals("Over the Counter") && imagepath.equals("")) {
			Toast.makeText(getApplicationContext(), "Please select an image for upload", Toast.LENGTH_SHORT).show();
			return;
		}

		String sendText = month + " " + amount + " " + modePayment + " " + bankName + " " + branchName + " " + remarks;
		Toast.makeText(getApplicationContext(), sendText, Toast.LENGTH_SHORT).show();

		// TODO work
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String fName = prefs.getString("first_name", "null");
		String lName = prefs.getString("last_name", "null");
		String email = prefs.getString("primary_email_address", "null");

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		String report_year = String.valueOf(year);
		
		//2014-02-05 11:33:07
		
		String sendDate = report_year 
		+ "-" + String.valueOf(c.get(Calendar.MONTH))
		+ "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
		+ " " + String.valueOf(c.get(Calendar.HOUR_OF_DAY))
		+ ":" + String.valueOf(c.get(Calendar.MINUTE))
		+ ":" + String.valueOf(c.get(Calendar.SECOND));
		

		String getSendText = "?fname=" + fName + "&lname=" + lName + "&mobile=" + phoneNumber + "&email_address="
				+ email + "&report_month=" + month + "&report_year=" + report_year + "&mode_of_payment=" + modePayment
				+ "&bank_name=" + bankName + "&branch_name=" + branchName + "&date_of_payment=" + stringDateOfPayment
				+ "&amount=" + amount + "&deposit_slip=" + fileName + "&send_date=" + sendDate + "&remarks=" + remarks
				+ "&agency_code=" + agency_code + "";

		getSendText = getSendText.replace(" ", "%20");

		new sendReportPayment().execute(getSendText);
		return;
	}

	public String removeLastChar(String str) {
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// updating of birthdate;m send post

		String monthString = "";
		if (monthOfYear < 10) {
			monthString = "0" + String.valueOf(monthOfYear + 1);
		} else {
			monthString = String.valueOf(monthOfYear + 1);
		}

		String dayString = "";
		if (dayOfMonth < 10) {
			dayString = "0" + String.valueOf(dayOfMonth);
		} else {
			dayString = String.valueOf(dayOfMonth);
		}

		stringDateOfPayment = monthString + "/" + dayString + "/" + String.valueOf(year);

		textMonth.setText(monthString);
		textDay.setText(dayString);
		textYear.setText(String.valueOf(year));

		// Toast.makeText(getApplicationContext(), stringDateOfPayment, Toast.LENGTH_SHORT).show();
	}

	String stringDateOfPayment = "";

	private String getRealPathFromURI(Uri contentURI) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);

		if (cursor == null) {
			return contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(filePathColumn[0]);
			String path = cursor.getString(idx);
			Log.e("Path value", path);
			cursor.close();

			return path;
		}
	}

	private class sendReportPayment extends AsyncTask<String, Void, String> {
		ProgressDialog pd = new ProgressDialog(CoreonMain.this);

		@Override
		protected void onPreExecute() {
			pd.setMessage("Sending Report");
			pd.show();
			pd.setCanceledOnTouchOutside(false);
			pd.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
					return arg2.getKeyCode() == KeyEvent.KEYCODE_BACK;
				}
			});
		}

		@Override
		protected String doInBackground(String... params) {
			upLoadServerUri = "http://" + ipAdd + "/coreonmobile_uploadimage.php";
			uploadFile(imagepath);
			sendPost("http://" + ipAdd + "/coreonmobile_reportpayment.php" + params[0]);
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			pd.hide();
			pd.dismiss();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	public int uploadFile(String sourceFileUri) {
		if (sourceFileUri == null) {
			return 0;
		}

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			// dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + imagepath);

			runOnUiThread(new Runnable() {
				public void run() {
					// messageText.setText("Source File not exist :" + imagepath);
				}
			});

			return 0;
		} else {
			try {
				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
						+ lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();
				Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

				Log.i("uploadFile tester", "HTTP " + conn.toString());

				if (serverResponseCode == 200) {
					runOnUiThread(new Runnable() {
						public void run() {
							String msg = "Image upload completed";
							// messageText.setText(msg);
							Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				// dialog.dismiss();
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						Log.e("error", "error");
						// messageText.setText("MalformedURLException Exception : check script url.");
						// Toast.makeText(MainActivity.this, "MalformedURLException",
						// Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				// dialog.dismiss();
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						// messageText.setText("Got Exception : see logcat ");
						Toast.makeText(getApplicationContext(), "Got Exception : see logcat ", Toast.LENGTH_SHORT)
								.show();
					}
				});
				Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
			}
			// dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}

	public class FixedSpeedScroller extends Scroller {
		private int mDuration = 500; // speed of file

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
	}
}