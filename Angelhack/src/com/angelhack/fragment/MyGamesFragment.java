package com.angelhack.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.angelhack.R;

public class MyGamesFragment extends Fragment {

	public static final String ARG_DOGALIZE_NUMBER = "dogalize_number";
	private CustomAdapter adapter;

	ArrayList<HashMap<String, Object>> searchResults;
	private int mNavigationImages[] = { R.drawable.andelica,
			R.drawable.chanelneuman, R.drawable.charliemaggy,
			R.drawable.edmondolee, R.drawable.filippo,
			R.drawable.laurettagreece, R.drawable.nicolasqueber };

	// ArrayList that will hold the original Data
	ArrayList<HashMap<String, Object>> originalValues;
	LayoutInflater mInflater;

	public MyGamesFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.search_friends, container,
				false);
		// int i = getArguments().getInt(ARG_DOGALIZE_NUMBER);
		// String mNavigationLabel =
		// getResources().getStringArray(R.array.dogalize_array)[i];
		// getActivity().setTitle(mNavigationLabel);

		// adapter = new FriendsAdapter(getActivity(), mTitlesList);
		mInflater = getActivity().getLayoutInflater();
		originalValues = new ArrayList<HashMap<String, Object>>();

		// temporary HashMap for populating the
		// Items in the ListView
		HashMap<String, Object> temp;

		// total number of rows in the ListView
		int noOfPlaces = mTitles.length;

		// now populate the ArrayList players
		for (int i = 0; i < noOfPlaces; i++) {
			temp = new HashMap<String, Object>();

			temp.put("name", mTitles[i]);
			temp.put("photo", mNavigationImages[i]);

			// add the row to the ArrayList
			originalValues.add(temp);
		}
		// searchResults=OriginalValues initially
		searchResults = new ArrayList<HashMap<String, Object>>(originalValues);

		adapter = new CustomAdapter(getActivity(), R.layout.friends_layout,
				searchResults);

		ListView mList = (ListView) rootView
				.findViewById(R.id.dogalize_friends);
		mList.setAdapter(adapter);

		return rootView;
	}

	private String[] mTitles = { "Badminton @ PGBA on 10 Nov, 8AM",
			"Badminton @ PGBA on 10 Nov, 8AM",
			"Tennis @ ISB on 11 Nov, 7AM",
			"Badminton @ Infy on 11 Nov, 6PM",
			"Badminton @ IIITH on 10 Nov, 7AM",
			"Cricket @ Uppal on 11 Nov, 3PM",
			"Cricket @ PGBA on 10 Nov, 8AM" };

	public void setupSearchView(SearchView mSearchView) {

		mSearchView
				.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

					@Override
					public boolean onQueryTextSubmit(String query) {

						return true;
					}

					@Override
					public boolean onQueryTextChange(String newText) {
						String searchString = newText;
						int textLength = searchString.length();
						searchResults.clear();

						for (int i = 0; i < originalValues.size(); i++) {
							String playerName = originalValues.get(i)
									.get("name").toString();
							if (textLength <= playerName.length()) {
								// compare the String in EditText with Names in
								// the ArrayList
								if (searchString.equalsIgnoreCase(playerName
										.substring(0, textLength)))
									searchResults.add(originalValues.get(i));
							}
						}

						adapter.notifyDataSetChanged();
						return true;
					}
				});
	}

	// define your custom adapter
	private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {

		public CustomAdapter(Context context, int textViewResourceId,
				ArrayList<HashMap<String, Object>> Strings) {

			// let android do the initializing :)
			super(context, textViewResourceId, Strings);
		}

		// class for caching the views in a row
		private class ViewHolder {
			ImageView photo;
			TextView name;
		}

		ViewHolder viewHolder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.friends_layout,
						parent, false);
				viewHolder = new ViewHolder();

				// cache the views
				viewHolder.photo = (ImageView) convertView
						.findViewById(R.id.profile_friends_image);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.profile_friends_name_tv);

				// link the cached views to the convertview
				convertView.setTag(viewHolder);

			} else
				viewHolder = (ViewHolder) convertView.getTag();

			int photoId = (Integer) searchResults.get(position).get("photo");
			viewHolder.photo.setVisibility(View.GONE);
			// set the data to be displayed
			viewHolder.photo.setImageDrawable(getResources().getDrawable(
					photoId));
			viewHolder.name.setText(searchResults.get(position).get("name")
					.toString());
			// return the view to be displayed
			return convertView;
		}

	}
}
