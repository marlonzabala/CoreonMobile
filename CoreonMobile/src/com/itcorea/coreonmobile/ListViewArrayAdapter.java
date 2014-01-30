package com.itcorea.coreonmobile;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ListViewArrayAdapter extends ArrayAdapter<String>
{
	private Context						context;

	private String						ipAdd				= "125.5.16.155/coreonwallet";	// "192.168.123.111";
	public boolean						billingInitialized	= false;
	LayoutInflater						inflater;
	String								tag					= "null";

	public transient ArrayList<String>	_type				= new ArrayList<String>();
	public transient ArrayList<String>	_title				= new ArrayList<String>();
	public transient ArrayList<String>	_content			= new ArrayList<String>();
	public transient ArrayList<String>	_image				= new ArrayList<String>();
	public transient ArrayList<String>	_date				= new ArrayList<String>();
	public transient ArrayList<String>	_extra1				= new ArrayList<String>();
	public transient ArrayList<String>	_extra2				= new ArrayList<String>();
	public transient ArrayList<String>	_extra3				= new ArrayList<String>();
	public transient ArrayList<String>	_extra4				= new ArrayList<String>();
	public transient ArrayList<String>	_extra5				= new ArrayList<String>();

	View								lineGray;

	public ListViewArrayAdapter(Context context, ArrayList<String> values)
	{
		super(context, 0, values);
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// lineGray = inflater.inflate(R.layout.listview_line_gray, null);
	}

	public int getViewTypeCount()
	{
		return 30;
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
		super.clear();

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

		this.add("");
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
	
	public void removeLast()
	{
		this.removeValue(this.getCount()-1);
	}

	@Override
	public int getCount()
	{
		return this._title.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView;
		String type = _type.get(position).toString();

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

			TextView textName = (TextView) rowView.findViewById(R.id.textViewProfileName);
			TextView textNumber = (TextView) rowView.findViewById(R.id.textViewProfileMobileNumber);
			TextView textNetwork = (TextView) rowView.findViewById(R.id.textViewProfileNetwork);
			ImageView imageProfile = (ImageView) rowView.findViewById(R.id.imageViewProfilePicture);
			textName.setText(_extra1.get(position).toString());
			textNumber.setText(_extra2.get(position).toString());
			textNetwork.setText(_extra3.get(position).toString());
			UrlImageViewHelper.setUrlDrawable(imageProfile, _image.get(position).toString(), R.drawable.my_account_picture);

			// Log.e("Image URL", _image.get(position).toString());
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

			TextView textAccountStatus = (TextView) rowView.findViewById(R.id.textViewTabAccountStatus);
			TextView textCreditStatus = (TextView) rowView.findViewById(R.id.textViewTabCreditStatus);
			TextView textContractStatus = (TextView) rowView.findViewById(R.id.textViewTabContractStatus);
			textAccountStatus.setText(_extra1.get(position).toString());
			textCreditStatus.setText(_extra2.get(position).toString());
			textContractStatus.setText(_extra3.get(position).toString());

			if (_extra2.get(position).toString().equals("GOOD"))
			{
				textCreditStatus.setTextColor(context.getResources().getColor(R.color.lightGreen));
			}
			else if (_extra2.get(position).toString().equals("WARNING"))
			{
				textCreditStatus.setTextColor(context.getResources().getColor(R.color.lightYellow));
			}
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
		else if (type.equals(tag = "listview_space"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_space, parent, false);
			}
			rowView.setTag(tag);

			if (!_title.get(position).toString().equals(""))
			{
				TextView textSpace = (TextView) rowView.findViewById(R.id.textViewblank);
				int spaceHeight = Integer.parseInt(_title.get(position).toString());
				textSpace.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, spaceHeight));
			}
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
			textBillingCount.setText("  " + _title.get(position).toString() + "  ");
			textBillingMonth.setText(_content.get(position).toString());
			textBillingDueDate.setText(_image.get(position).toString());
			textBillingAmount.setText(_date.get(position).toString());
		}
		else if (type.equals(tag = "listview_payment_record"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_payment_record, parent, false);
			}
			rowView.setTag(tag);

			TextView textCount = (TextView) rowView.findViewById(R.id.textViewRecordCount);
			TextView textPaymentDate = (TextView) rowView.findViewById(R.id.textViewPaymentDate);
			TextView textPostedDate = (TextView) rowView.findViewById(R.id.textViewPostedDate);
			TextView textPaymetnMode = (TextView) rowView.findViewById(R.id.textViewPaymentMode);
			TextView textBankCard = (TextView) rowView.findViewById(R.id.textViewBankCard);
			TextView textBranch = (TextView) rowView.findViewById(R.id.textViewBranch);
			TextView textReferenceNumber = (TextView) rowView.findViewById(R.id.textViewReferenceNumber);
			TextView textPaymentAmount = (TextView) rowView.findViewById(R.id.textViewPaymentAmount);
			textCount.setText("  " + _title.get(position).toString() + "  ");
			textPaymentDate.setText(_content.get(position).toString());
			textPostedDate.setText(_image.get(position).toString());
			textPaymetnMode.setText(_date.get(position).toString());
			textBankCard.setText(_extra1.get(position).toString());
			textBranch.setText(_extra2.get(position).toString());
			textReferenceNumber.setText(_extra3.get(position).toString());
			textPaymentAmount.setText(_extra4.get(position).toString());
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
			textBillingCount.setText("  " + _title.get(position).toString() + "  ");
			textBillingMonth.setText(_content.get(position).toString());
			textBillingDueDate.setText(_image.get(position).toString());

			// TODO test

			RelativeLayout clickLayout = (RelativeLayout) rowView.findViewById(R.id.layoutBillingStatement);
			clickLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0)
				{

					String url = _date.get(position).toString();

					if (!url.startsWith("http://") && !url.startsWith("https://"))
						url = "http://" + url;

					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(browserIntent);
				}
			});
		}
		else if (type.equals(tag = "listview_bank_deposit_sub_info"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_sub_info, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInformation);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
		}
		else if (type.equals(tag = "listview_bank_deposit_sub_header"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_sub_header, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			textTitle.setText(_title.get(position).toString());
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
		else if (type.equals(tag = "listview_bank_deposit_image_header"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_image_header, parent, false);
			}
			rowView.setTag(tag);

			ImageView imageBank = (ImageView) rowView.findViewById(R.id.imageViewBankImage);
			imageBank.setImageResource(Integer.parseInt(_image.get(position).toString()));
			
			
			ImageView imageLocation = (ImageView) rowView.findViewById(R.id.imageViewLocation);
			imageLocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0)
				{
					if(_date.get(position).toString().equals(""))
					{
						float latitude = 14.5613973f;
						float longitude = 121.028455f;
						String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
						context.startActivity(intent);
					}
					else
					{
						String url = _date.get(position).toString();
						if (!url.startsWith("http://") && !url.startsWith("https://"))
							url = "http://" + url;

						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(browserIntent);
					}					
				}
			});
		}
		else if (type.equals(tag = "listview_bank_deposit_how_to_info"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_how_to_info, parent, false);
			}
			rowView.setTag(tag);

			TextView textInfo = (TextView) rowView.findViewById(R.id.TextViewInfo2);
			textInfo.setText(Html
					.fromHtml("After payment, send us a scan copy of the validated payment slip at: <font color='#ff9600'>cs@coreonmobile.com</font>"));
		}
		else if (type.equals(tag = "listview_bank_deposit_how_to_info_branch_payments"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_how_to_info_branch_payments, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_bank_deposit_sub_info_text"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_bank_deposit_sub_info_text, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			textTitle.setText(Html.fromHtml(_title.get(position).toString()));
		}
		else if (type.equals(tag = "listview_rewards_warning"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_rewards_warning, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_offers"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_offers, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			TextView textInfo = (TextView) rowView.findViewById(R.id.textViewInfo);
			ImageView image = (ImageView) rowView.findViewById(R.id.imageViewPicture);
			TextView textDate = (TextView) rowView.findViewById(R.id.textViewDate);
			textTitle.setText(_title.get(position).toString());
			textInfo.setText(_content.get(position).toString());
			textDate.setText(_date.get(position).toString());
			// change picture
			image.setImageResource(R.drawable.offer_0);
		}
		else if (type.equals(tag = "listview_drawer_menu"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_drawer_menu, parent, false);
			}
			rowView.setTag(tag);

			TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
			ImageView image = (ImageView) rowView.findViewById(R.id.imageViewIcon);
			textTitle.setText(_title.get(position).toString());
			image.setImageResource(Integer.parseInt(_image.get(position).toString()));
		}
		else if (type.equals(tag = "listview_drawer_info"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_drawer_info, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_report_payment"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_report_payment, parent, false);
			}
			rowView.setTag(tag);
		}
		else if (type.equals(tag = "listview_loading"))
		{
			if ((convertView != null && convertView.getTag().equals(tag)))
			{
				rowView = convertView;
			}
			else
			{
				rowView = inflater.inflate(R.layout.listview_loading, parent, false);
			}
			rowView.setTag(tag);
		}
		else
		{
			// TODO end of layouts
			View v = new View(context);
			v.setTag("error");
			Log.e("Error", "no layout");
			return v;
		}

		return rowView;
	}

	static class ViewHolder
	{
		TextView	title, Content;
	}
}