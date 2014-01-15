package com.itcorea.coreonmobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewArrayAdapter extends ArrayAdapter<String>
{
	private Context						context;

	private String						ipAdd		= "125.5.16.155/coreonwallet";	// "192.168.123.111";
	public boolean billingInitialized = false;

	public transient ArrayList<String>	_type		= new ArrayList<String>();
	public transient ArrayList<String>	_title		= new ArrayList<String>();
	public transient ArrayList<String>	_content	= new ArrayList<String>();
	public transient ArrayList<String>	_image		= new ArrayList<String>();
	public transient ArrayList<String>	_date		= new ArrayList<String>();
	public transient ArrayList<String>	_extra1		= new ArrayList<String>();
	public transient ArrayList<String>	_extra2		= new ArrayList<String>();
	public transient ArrayList<String>	_extra3		= new ArrayList<String>();
	public transient ArrayList<String>	_extra4		= new ArrayList<String>();
	public transient ArrayList<String>	_extra5		= new ArrayList<String>();

	public ListViewArrayAdapter(Context context, ArrayList<String> values)
	{
		super(context, 0, values);
		this.context = context;
	}

	public void initiatizeStringsValues()
	{
		_title = new ArrayList<String>();
		_content = new ArrayList<String>();
		_date = new ArrayList<String>();
		_image = new ArrayList<String>();
		_type = new ArrayList<String>();
		_extra1 = new ArrayList<String>();
		_extra2 = new ArrayList<String>();
		_extra3 = new ArrayList<String>();
		_extra4 = new ArrayList<String>();
		_extra5 = new ArrayList<String>();

		this.clear();
	}

	public void removeValue(int position)
	{
		if (position == 0)
		{
			Log.e("conract", "one or more of data are 0");
		}

		_title.remove(position);
		_content.remove(position);
		_date.remove(position);
		_image.remove(position);
		_type.remove(position);
		_extra1.remove(position);
		_extra2.remove(position);
		_extra3.remove(position);
		_extra4.remove(position);
		_extra5.remove(position);
	}

	public void addValue(String type, String title, String content, String image, String date)
	{
		// if (title == null || content == null || date == null || extra == null || type == null)
		// {
		// Log.e("conract", "one or more of data are null");
		// }

		_title.add(title);
		_content.add(content);
		_date.add(date);
		_image.add(image);
		_type.add(type);
		_extra1.add("");
		_extra2.add("");
		_extra3.add("");
		_extra4.add("");
		_extra5.add("");

		this.add(title);
	}

	public void addValueExtra(String type, String title, String content, String image, String date, String extra1, String extra2, String extra3,
			String extra4, String extra5)
	{
		// if (title == null || content == null || date == null || extra == null || type == null)
		// {
		// Log.e("conract", "one or more of data are null");
		// }

		_title.add(title);
		_content.add(content);
		_date.add(date);
		_image.add(image);
		_type.add(type);
		_extra1.add(extra1);
		_extra2.add(extra2);
		_extra3.add(extra3);
		_extra4.add(extra4);
		_extra5.add(extra5);

		this.add(title);
	}

	public void addType(String type)
	{
		_type.add(type);

		_title.add("");
		_content.add("");
		_date.add("");
		_image.add("");
		_extra1.add("");
		_extra2.add("");
		_extra3.add("");
		_extra4.add("");
		_extra5.add("");

		this.add("");
	}

	@Override
	public int getCount()
	{
		return this._title.size();
	}

	@SuppressLint("InlinedApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView;
		String type = _type.get(position).toString();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String tag = "null";

		if (type.equals(tag = "my_account_info"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.my_account_info, parent, false);
			}

			// TextView textName = (TextView) rowView.findViewById(R.id.textViewName);
			// TextView textHi = (TextView) rowView.findViewById(R.id.textViewHi);

			// Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
			// textName.setTypeface(typeFace);
			// textHi.setTypeface(typeFace);
			// textName.setText(_content.get(position).toString());

			TextView textName = (TextView) rowView.findViewById(R.id.textViewProfileName);
			TextView textNumber = (TextView) rowView.findViewById(R.id.textViewProfileMobileNumber);
			TextView textNetwork = (TextView) rowView.findViewById(R.id.textViewProfileNetwork);
			textName.setText(_extra1.get(position).toString());
			textNumber.setText(_extra2.get(position).toString());
			textNetwork.setText(_extra3.get(position).toString());

			rowView.setTag(tag);
		}
		else if (type.equals(tag = "my_account_status"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.my_account_status, parent, false);
			}
			rowView.setTag(tag);

			TextView textAccount = (TextView) rowView.findViewById(R.id.textViewTabMyAccountContent);
			TextView textPayment = (TextView) rowView.findViewById(R.id.textViewTabBillingPaymentContent);
			TextView textOffers = (TextView) rowView.findViewById(R.id.textViewTabRewardsOffersContent);
			textAccount.setText(_extra1.get(position).toString());
			textPayment.setText(_extra2.get(position).toString());
			textOffers.setText(_extra3.get(position).toString());
		}
		else if (type.equals(tag = "listview_main_header_wshadow"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_main_header_wshadow, parent, false);
			}
			rowView.setTag(tag);
			
			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			textTitle.setText(_title.get(position).toString());
		}
		else if (type.equals(tag = "listview_main_header"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_main_header, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			textTitle.setText(_title.get(position).toString());
		}
		else if (type.equals(tag = "listview_sub_info"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_sub_info, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInformation);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
		}
		else if (type.equals(tag = "listview_ad"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_ad, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_line_gray"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_line_gray, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_line_light_gray"))
		{
			if (convertView != null && convertView.getTag().equals(tag))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_line_light_gray, parent, false);
				
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "billing_payment_tab_menu"))
		{
			//TODO billings
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_header_billing_payment, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_sub_info_large_black"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_sub_info_large_black, parent, false);
			}
			rowView.setTag(tag);
			
			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInformation);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
		}
		else if (type.equals(tag = "listview_sub_info_large_black_shadow"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_sub_info_large_black_shadow, parent, false);
			}
			rowView.setTag(tag);
			
			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInformation);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
		}
		else if (type.equals(tag = "advertisment"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_sub_info, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_billing_record"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_billing_record, parent, false);
			}
			rowView.setTag(tag);
			
			
			TextView textBillingCount = (TextView) rowView.findViewById(R.id.textViewBillingRecordCount);
			TextView textBillingMonth = (TextView) rowView.findViewById(R.id.textViewBillingMonth);
			TextView textBillingDueDate = (TextView) rowView.findViewById(R.id.textViewBillingDueDate);
			TextView textBillingAmount = (TextView) rowView.findViewById(R.id.textViewBillingAmount);
			textBillingCount.setText("  "+_title.get(position).toString()+"  ");
			textBillingMonth.setText(_content.get(position).toString());
			textBillingDueDate.setText(_image.get(position).toString());
			textBillingAmount.setText(_date.get(position).toString());
		}
		else if (type.equals(tag = "listview_billing_statements"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_billing_statements, parent, false);
			}
			rowView.setTag(tag);
			
			
			TextView textBillingCount = (TextView) rowView.findViewById(R.id.textViewBillingRecordCount);
			TextView textBillingMonth = (TextView) rowView.findViewById(R.id.textViewBillingMonth);
			TextView textBillingDueDate = (TextView) rowView.findViewById(R.id.textViewBillingDueDate);
			textBillingCount.setText("  "+_title.get(position).toString()+"  ");
			textBillingMonth.setText(_content.get(position).toString());
			textBillingDueDate.setText(_image.get(position).toString());
		}
		else if (type.equals(tag = "listview_main_header_billing_record_total"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_main_header_billing_record_total, parent, false);
			}
			rowView.setTag(tag);
			
			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInformation);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
		}
		else
		{
			// TODO end of layouts
			View v = new View(context);
			v.setTag("error");
			return v;
		}

		return rowView;
	}
}