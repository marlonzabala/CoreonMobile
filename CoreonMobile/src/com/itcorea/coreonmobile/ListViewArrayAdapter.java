package com.itcorea.coreonmobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ListViewArrayAdapter extends ArrayAdapter<String>
{
	private transient static Context	context;

	private transient final String		ipAdd				= "125.5.16.155/coreonwallet";	// "192.168.123.111";

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

	public ListViewArrayAdapter()
	{
		super(context, 0);
	}

	@SuppressWarnings("static-access")
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

	public void addStrings(String type, String title, String content, String image, String date, String extra1, String extra2, String extra3, String extra4, String extra5)
	{
//		if (title == null || content == null || date == null || extra == null || type == null)
//		{
//			Log.e("conract", "one or more of data are null");
//		}

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

//			TextView textName = (TextView) rowView.findViewById(R.id.textViewName);
//			TextView textHi = (TextView) rowView.findViewById(R.id.textViewHi);

//			Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
//			textName.setTypeface(typeFace);
//			textHi.setTypeface(typeFace);
//			textName.setText(_content.get(position).toString());

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
		else
		{
			//TODO end of layouts
			View v = new View(context);
			v.setTag("error");
			return v;
		}

		return rowView;
	}
}