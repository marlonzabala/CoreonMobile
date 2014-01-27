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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LogIn extends Activity
{

	int	dev	= 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_log_in);

		ImageView imageLogo = (ImageView) findViewById(R.id.imageViewLogoMain);
		imageLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				dev--;
				if (dev < 0)
				{
					new getRandomAccount().execute();
				}
			}
		});

		// Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
		// check if user is logged in

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean LoggedIn = prefs.getBoolean("LoggedIn", false);

		// for dev quick login
		//LoggedIn = true;

		// for dev
		// EditText ep = (EditText) findViewById(R.id.editMobile);
		// EditText eu = (EditText) findViewById(R.id.editEmail);
		// // eu.setText("ghost@corea.ph");
		// // ep.setText("9178589031");
		// eu.setText("scotlee1004@gmail.com");
		// ep.setText("9178143372");

		// detection of logged in value
		if (LoggedIn == true)
		{
			Intent intent = new Intent(getApplicationContext(), CoreonMain.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(intent);
			finish();
		}
		else
		{
			// Toast.makeText(getApplicationContext(), "Not Logged In", Toast.LENGTH_SHORT).show();
		}
	}

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
				Log.e("log_tag 1", "Error in http connection " + e.toString());
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
				Log.e("log_tag 2", "Error converting result " + e.toString());
			}
		}
		return result;
	}

	private List<String[]> getDataArrayFromJsonString(String... jsonString)
	{
		// Gets the json string and converts it to a list of strings

		List<String[]> rowList;
		rowList = new ArrayList<String[]>();

		if (jsonString[0].equals(""))
			return rowList;

		try
		{
			JSONArray jArray = null;
			jArray = new JSONArray(jsonString[0]);

			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++)
			{
				// count
				json_data = jArray.getJSONObject(i);

				String[] stringContents = new String[jsonString.length];
				for (int j = 1; j < jsonString.length; j++)
				{
					if (jsonString[j].equals("count"))
					{
						stringContents[j - 1] = String.valueOf(jArray.length() - i);
					}
					else
					{
						stringContents[j - 1] = json_data.getString(jsonString[j]);
					}
				}
				rowList.add(stringContents);
			}
		}
		catch (JSONException e)
		{
			Log.e("getDataArrayFromJsonString error", e.toString());
		}

		return rowList;
	}

	private class getRandomAccount extends AsyncTask<String, Void, String>
	{
		List<String[]>	rowList;

		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... params)
		{
			String httpAddress = "http://" + ipAdd + "/coreonmobile_getrandomuser.php";
			Log.e("urlPost 5", httpAddress.toString());

			String jsonString = sendPost(httpAddress);
			rowList = getDataArrayFromJsonString(jsonString, "mobile_no", "email_address");

			return "";
		}

		@Override
		protected void onPostExecute(String result)
		{
			for (int i = 0; i < rowList.size(); i++)
			{
				EditText eu = (EditText) findViewById(R.id.editEmail);
				EditText ep = (EditText) findViewById(R.id.editMobile);
				// eu.setText("ghost@corea.ph");
				// ep.setText("9178589031");
				eu.setText(rowList.get(i)[1].toString());
				ep.setText(rowList.get(i)[0].toString());
			}

		}

		@Override
		protected void onProgressUpdate(Void... values)
		{

		}
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
			new CheckCredentials(getApplicationContext(), LogIn.this).execute(editEmail.getText().toString().trim(), editMobile.getText().toString().trim(),
					"login");
		}
	}

	public void openSignUp(View view)
	{
		final CharSequence[] items = { "511-17-15 (Landline)", "09178530966 (Globe)", "09998879711 (Smart)" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Call Us");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item)
			{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				switch (item)
				{
					case 0:
						callIntent.setData(Uri.parse("tel:5111715"));
						startActivity(callIntent);
						break;
					case 1:
						callIntent.setData(Uri.parse("tel:09178530966"));
						startActivity(callIntent);
						break;
					case 2:
						callIntent.setData(Uri.parse("tel:09998879711"));
						startActivity(callIntent);
						break;
					default:
						break;
				}
			}
		});
		builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// Do nothing.
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

		// (02)511 17 15 (land line)
		// 09178530966 - globe
		// 09998879711 - smart
	}

	String				useremail	= "";
	boolean				logIn		= false;
	boolean				network		= true;
	boolean				timeout		= false;
	int					timeoutsec	= 20000;									// 20 second timeout
	private Context		mContext;
	private Activity	mActivity;
	ProgressDialog		mDialog;
	String				ipAdd		= "125.5.16.155/coreonwallet/coreonmobile"; // "192.168.123.111/android/coreonmobile";

	class CheckCredentials extends AsyncTask<String, Integer, Long>
	{
		// desktop set to static ip 192.168.123.111
		// String ipAdd = "125.5.16.155/coreonwallet";
		// "125.5.16.155/coreonwallet/coreonmobile";

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
				logIn = true;
				return null;
			}
			else
			{
				network = true;
			}

			String httpAddress = "http://" + ipAdd + "/coreonmobile_accountinfo.php?email=" + params[0] + "&mobile=" + params[1] + "";// &request="
																																		// params[2]
			// + "";
			Log.e("address", httpAddress);
			Log.i("urlPost", httpAddress.toString());
			String result = sendPost(httpAddress);

			JSONArray jArray = null;
			String id = null;
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
					id = json_data.getString("main_id");
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
				logIn = true;
			}
			catch (JSONException e1)
			{
				logIn = false;
				Log.e("Exception", e1.toString());
			}
			catch (ParseException e1)
			{
				logIn = false;
				Log.e("Exception", e1.toString());
				e1.printStackTrace();
			}

			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
			SharedPreferences.Editor editor = preferences.edit();
			// Boolean tr = true;

			editor.putString("id", id);
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
}
