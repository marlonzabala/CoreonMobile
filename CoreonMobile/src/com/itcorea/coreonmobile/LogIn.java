package com.itcorea.coreonmobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_log_in);

		// Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
		// TODO remove preferences in android filesystem beacause of duplicate names
		// check if user is logged in

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean LoggedIn = prefs.getBoolean("LoggedIn", false);

		// for dev
		// test for errors
		// LoggedIn = true;

		// for dev
		EditText ep = (EditText) findViewById(R.id.editMobile);
		EditText eu = (EditText) findViewById(R.id.editEmail);
		// ep.setText("12312");
		// eu.setText("kit.datuin@gmail.com");
		ep.setText("9998863057");
		eu.setText("krkz1203@nate.com");

		// if (LoggedIn == true)
		// {
		// Intent intent = new Intent(getApplicationContext(), CoreonMain.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// getApplicationContext().startActivity(intent);
		// finish();
		// }
		// else
		// {
		// //Toast.makeText(getApplicationContext(), "Not Logged In", Toast.LENGTH_SHORT).show();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	public void CheckLogIn(View view)
	{
		// get text values
		EditText editEmail = (EditText) findViewById(R.id.editEmail);
		EditText editMobile = (EditText) findViewById(R.id.editMobile);

		// check if incomplete
		if (editEmail.getText().toString().equals("") || editMobile.getText().toString().equals(""))
		{
			Toast.makeText(getBaseContext(), "Please complete the fields", Toast.LENGTH_SHORT).show();
		}
		else
		{
			// Check login credentials then proceed
			// execute in asynchronous task
			new CheckCredentials(getApplicationContext(), LogIn.this).execute(editEmail.getText().toString(), editMobile.getText().toString(),
					"login");
		}
	}

	public void openSignUp(View view)
	{
		new AlertDialog.Builder(LogIn.this).setTitle("Call us? 0912345689").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:0912345689"));
				startActivity(callIntent);

			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Do nothing.
			}
		}).show();
	}
}

class CheckCredentials extends AsyncTask<String, Integer, Long>
{

	String				useremail	= "";
	boolean				logIn		= false;
	boolean				network		= true;
	boolean				timeout		= false;
	private Context		mContext;
	private Activity	mActivity;
	ProgressDialog		mDialog;

	// desktop set to static ip 192.168.123.111

	// live
	// String ipAdd = "125.5.16.155/coreonwallet";

	// test
	String				ipAdd		= "192.168.123.111/android";

	public CheckCredentials(Context context, Activity activity)
	{
		mContext = context;
		mActivity = activity;
	}

	private boolean isNetworkAvailable()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private String sendPost(String httpAddress)
	{
		String result = "";
		StringBuilder sb = null;
		InputStream is = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		try
		{
			int timeoutsec = 20000; // 20 second timeout
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
			return "";
		}
		catch (Exception e)
		{
			// Log.e("log_tag", "Error in http connection " + e.toString());
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
			// Log.e("log_tag", "Error converting result " + e.toString());
		}
		return result;
	}

	@Override
	protected Long doInBackground(String... params)
	{
		timeout = false;

		if (!isNetworkAvailable())
		{
			network = false;
			return null;
		}
		else
		{
			network = true;
		}

		String httpAddress = "http://" + ipAdd + "/accountinfo_coreonmobile.php?email=" + params[0] + "&mobile=" + params[1] + "";// &request="
																																	// +
																																	// params[2]
		// + "";

		Log.e("address", httpAddress);
		Log.i("urlPost", httpAddress.toString());
		String result = sendPost(httpAddress);

		JSONArray jArray = null;
		String first_name = null;
		String last_name = null;
		String contract_status = null;
		String credit_status = null;
		String mobile_number = null;
		String mobile_network = null;
		String plan_title = null;
		String supplementary_service = null;
		String subscription_type = null;
		String days_left = null;
		String mobile_unit = null;
		String enrollment_fee = null;
		String date_of_contract_start = null;
		String date_of_contract_end = null;
		String credit_limit = null;
		String activation_month = null;
		String activation_day = null;
		String activation_year = null;
		String discount = null;
		String primary_email_address = null;
		String secondary_email_address = null;
		String h_address_line1 = null;
		String h_address_line2 = null;
		String h_address_line3 = null;
		String h_zip_code = null;
		String ph_tel_areaCode = null;
		String ph_tel_3digit = null;
		String ph_tel_4digit = null;
		String kr_tel_areaCode = null;
		String kr_tel_3digit = null;
		String kr_tel_4digit = null;
		String mobile_code = null;
		String mobile_3digit = null;
		String mobile_4digit = null;
		String billing_address = null;

		try
		{
			jArray = new JSONArray(result);
			JSONObject json_data = null;

			List<String[]> rowList = new ArrayList<String[]>();

			rowList.add(new String[] { "title", "content", "image", "date", "url" });
			rowList.add(new String[] { "title", "content", "image", "date", "url" });
			rowList.add(new String[] { "title", "content", "image", "date", "url" });

			for (int i = 0; i < jArray.length(); i++)
			{
				json_data = jArray.getJSONObject(i);
				first_name = json_data.getString("first_name");
				last_name = json_data.getString("last_name");
				contract_status = json_data.getString("contract_status");
				credit_status = json_data.getString("credit_status");
				mobile_number = json_data.getString("mobile_number");
				plan_title = json_data.getString("plan_title");
				mobile_network = json_data.getString("mobile_network");
				days_left = json_data.getString("days_left");
				supplementary_service = json_data.getString("supplementary_service");
				subscription_type = json_data.getString("subscription_type");
				mobile_unit = json_data.getString("mobile_unit");
				enrollment_fee = json_data.getString("enrollment_fee");
				date_of_contract_start = json_data.getString("date_of_contract_start");
				date_of_contract_end = json_data.getString("date_of_contract_end");
				credit_limit = json_data.getString("credit_limit");
				activation_month = json_data.getString("activation_month");
				activation_day = json_data.getString("activation_day");
				activation_year = json_data.getString("activation_year");
				discount = json_data.getString("discount");
				primary_email_address = json_data.getString("primary_email_address");
				secondary_email_address = json_data.getString("secondary_email_address");
				h_address_line1 = json_data.getString("h_address_line1");
				h_address_line2 = json_data.getString("h_address_line2");
				h_address_line3 = json_data.getString("h_address_line3");
				h_zip_code = json_data.getString("h_zip_code");
				ph_tel_areaCode = json_data.getString("ph_tel_areaCode");
				ph_tel_3digit = json_data.getString("ph_tel_3digit");
				ph_tel_4digit = json_data.getString("ph_tel_4digit");
				kr_tel_areaCode = json_data.getString("kr_tel_areaCode");
				kr_tel_3digit = json_data.getString("kr_tel_3digit");
				kr_tel_4digit = json_data.getString("kr_tel_4digit");
				mobile_code = json_data.getString("mobile_code");
				mobile_3digit = json_data.getString("mobile_3digit");
				mobile_4digit = json_data.getString("mobile_4digit");
				billing_address = json_data.getString("billing_address");
			}
		}
		catch (JSONException e1)
		{
			useremail = "No data found";
			Log.e("Exception", e1.toString());
		}
		catch (ParseException e1)
		{
			Log.e("Exception", e1.toString());
			e1.printStackTrace();
		}

		if (useremail.equals("No data found"))
		{
			logIn = false;
		}
		else
		{
			logIn = true;
		}

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		SharedPreferences.Editor editor = preferences.edit();
		// Boolean tr = true;

		editor.putString("first_name", first_name);
		editor.putString("last_name", last_name);
		editor.putString("contract_status", contract_status);
		editor.putString("credit_status", credit_status);
		editor.putString("mobile_number", mobile_number);
		editor.putString("mobile_network", mobile_network);
		editor.putString("days_left", days_left);
		editor.putString("plan_title", plan_title);
		editor.putString("supplementary_service", supplementary_service);
		editor.putString("subscription_type", subscription_type);
		editor.putString("mobile_unit", mobile_unit);
		editor.putString("enrollment_fee", enrollment_fee);
		editor.putString("date_of_contract_start", date_of_contract_start);
		editor.putString("date_of_contract_end", date_of_contract_end);
		editor.putString("credit_limit", credit_limit);
		editor.putString("activation_date", activation_month + " " + activation_day + ", " + activation_year);
		editor.putString("discount", discount);
		editor.putString("primary_email_address", primary_email_address);
		editor.putString("secondary_email_address", secondary_email_address);
		editor.putString("home_address", h_address_line1 + " " + h_address_line2 + " " + h_address_line3);
		editor.putString("zip_code", h_zip_code);
		editor.putString("ph_tel", ph_tel_areaCode + ph_tel_3digit + ph_tel_4digit);
		editor.putString("kr_tel", kr_tel_areaCode + kr_tel_3digit + kr_tel_4digit);
		editor.putString("other_mobile", mobile_code + mobile_3digit + mobile_4digit);
		editor.putString("billing_address", billing_address);

		editor.commit();

		return null;
	}

	protected void onProgressUpdate(Integer... progress)
	{
		// setProgressPercent(progress[0]);
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		mDialog = new ProgressDialog(mActivity);
		mDialog.setMessage("Loggin in..");
		mDialog.show();
	}

	protected void onPostExecute(Long result)
	{
		// remove progress dialog
		mDialog.dismiss();

		if (logIn)
		{
			// Toast.makeText(mContext, useremail, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(mContext, CoreonMain.class);
			// Intent intent = new Intent(mContext, LogIn.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
		else
		{
			if (network)
			{
				if (timeout)
				{
					Toast.makeText(mContext, "Network time out, server maybe down", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(mContext, "Wrong password or username", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(mContext, "No internet connection", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
