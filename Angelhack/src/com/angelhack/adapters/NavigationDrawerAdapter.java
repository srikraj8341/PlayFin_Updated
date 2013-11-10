package com.angelhack.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelhack.R;

public class NavigationDrawerAdapter extends BaseAdapter {

	Context context;
	int pos = 0;

	public NavigationDrawerAdapter(Context c) {
		this.context = c;
	}

	private int mNavigationImages[] = { R.drawable.ic_home,
			R.drawable.ic_account, R.drawable.ic_friends,
			R.drawable.ic_action_search };

	private String[] TextValue = { "Profile", "Initiate", "My Games", "Search" };

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.drawer_list_item, parent,
				false);
		ImageView img = (ImageView) rowView.findViewById(R.id.navigation_img);
		img.setImageResource(mNavigationImages[position]);
		TextView text1 = (TextView) rowView.findViewById(R.id.navigation_tv);
		text1.setTextColor(Color.WHITE);
		text1.setText(TextValue[position]);
		if (pos == position) {
			rowView.setBackgroundColor(context.getResources().getColor(
					R.color.navigationDrawerBackground));
		} else {
			rowView.setBackgroundColor(Color.TRANSPARENT);
		}
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refresh(int position) {
		pos = position;

	}
}
