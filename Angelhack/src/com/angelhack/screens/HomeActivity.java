package com.angelhack.screens;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.angelhack.Preferences;
import com.angelhack.R;
import com.angelhack.adapters.NavigationDrawerAdapter;
import com.angelhack.fragment.InitiateFragment;
import com.angelhack.fragment.MyGamesFragment;
import com.angelhack.fragment.ProfileDetails_Fragment;
import com.angelhack.fragment.Search_Fragment;

public class HomeActivity extends ActionBarActivity implements
		OnNavigationListener {

	public DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationDrawerAdapter adapter;
	LayoutInflater inflater;
	ViewGroup mProfileHeader;
	boolean isAppFirstTime = false;
	Preferences mPreference;
	public int pos = 0;
	int clickCount = 0;
	public boolean drawerOpen = false;
	public boolean isUserAccountEnabled = false;
	ProfileDetails_Fragment mProfile_Fragment;
	InitiateFragment mUser_profile_fragment;
	MyGamesFragment mMyGamesFragment;
	Search_Fragment mDogalize_Maps_Fragment;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDogalizeTitles;

	Fragment fragment = null;

	String mCheckin_desc = "", mCheckin_place = "";
	boolean isPost = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mPreference = new Preferences(this, getPackageName());
		setContentView(R.layout.home_activity);
		mTitle = mDrawerTitle = getTitle();
		mDogalizeTitles = getResources().getStringArray(R.array.dogalize_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		inflater = getLayoutInflater();
		// Add header news title

		mProfileHeader = (ViewGroup) inflater.inflate(R.layout.profile_header,
				mDrawerList, false);
		mDrawerList.addHeaderView(mProfileHeader, null, false);

		adapter = new NavigationDrawerAdapter(HomeActivity.this);

		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, mDogalizeTitles));
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ab_logo);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_navigation_drawer, /*
										 * nav drawer image to replace 'Up'
										 * caret
										 */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			public void onDrawerClosed(View view) {

				if (pos == 5) {
					
				} else if (pos == 2) {

					setTitle(mDogalizeTitles[pos]);
					supportInvalidateOptionsMenu();

				} else if (pos == 1) {
					supportInvalidateOptionsMenu();

					setTitle(mDogalizeTitles[pos]);

				} else {
					setTitle(mDogalizeTitles[pos]);
					getSupportActionBar().setNavigationMode(0);
					supportInvalidateOptionsMenu();
				}
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setNavigationMode(0);
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
		isAppFirstTime = mPreference.getValueFromPreference(
				getString(R.string.key_isAppFirstTime), true);
		if (isAppFirstTime) {
			mDrawerLayout.openDrawer(Gravity.START);
		}
		//adapter.refresh(3);
		//selectItem(3);
		supportInvalidateOptionsMenu();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.home, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			pos = position - 1;
			adapter.refresh(position - 1);
			selectItem(position - 1);
		}
	}

	private void selectItem(final int position) {

		final Bundle args = new Bundle();
		final FragmentManager fragmentManager = getSupportFragmentManager();

		// update the main content by replacing fragments
		switch (position) {
		case 0:
			// if (mDogalize_Home_Fragment == null)
			//mMyGamesFragment = new MyGamesFragment();
			//fragment = mMyGamesFragment;
			mProfile_Fragment = new ProfileDetails_Fragment();
			fragment = mProfile_Fragment;
			break;
		case 1:
			// if (mUser_profile_fragment == null)
			mUser_profile_fragment = new InitiateFragment();
			fragment = mUser_profile_fragment;

			// startActivity(new Intent(HomeActivity.this,
			// UserProfileActivity.class));
			break;
		case 2:
			// if (mDogalizeFriendsFragment == null)
			//mProfile_Fragment = new ProfileDetails_Fragment();
			//fragment = mProfile_Fragment;
			mMyGamesFragment = new MyGamesFragment();
			fragment = mMyGamesFragment;

			break;
		case 3:
			// if (mDogalize_Friends_Request_Fragment == null)
			mDogalize_Maps_Fragment = new Search_Fragment();
			fragment = mDogalize_Maps_Fragment;
			break;
		default:
			break;
		}
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				args.putInt("PUT", position);

				if (position == 5) {
					if (clickCount == 0) {
						fragmentManager
								.beginTransaction()
								.replace(R.id.content_frame,
										mDogalize_Maps_Fragment).commit();
						clickCount += 1;
					} else {
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
						clickCount = 0;
					}

				} else if (position == 2) {
					fragmentManager
							.beginTransaction()
							.replace(R.id.content_frame,
									mMyGamesFragment).commit();
				}

				else {
					getSupportActionBar().setDisplayShowTitleEnabled(true);
					fragment.setArguments(args);
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();

				}

				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(position, true);

				mDrawerLayout.closeDrawer(mDrawerList);

			}
		});
		mDrawerToggle.onDrawerStateChanged(3);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		mPreference.saveValueIntoPreference(
				getString(R.string.key_isAppFirstTime), false);
		// if(mSimpleFacebook.isLogin())
		// mSimpleFacebook.logout(mOnLogoutListener);
		super.onDestroy();

	}

	
	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		getSupportActionBar().setNavigationMode(0);
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case android.R.id.home:
			getSupportActionBar().setNavigationMode(0);
			if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
				mDrawerLayout.closeDrawer(Gravity.START);
				getSupportActionBar().show();
			}

			else {
				mDrawerLayout.openDrawer(Gravity.START);
			}

			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		menu.clear();
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

		if (drawerOpen) {
			getSupportActionBar().setDisplayShowTitleEnabled(true);
			getSupportActionBar().setTitle(mDrawerTitle);
			inflater.inflate(R.menu.on_drawer_open_menu, menu);
		} else if (!drawerOpen) {
			menu.clear();
			// getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_solid_dogalize));
			switch (pos) {
			case 3:
				getSupportActionBar().setDisplayShowTitleEnabled(true);
				inflater.inflate(R.menu.dogalize_friends, menu);
				// inflater.inflate(R.menu.dogalize_friends, menu);
				MenuItem searchItem = menu.findItem(R.id.action_search);

				SearchView mSearchView = (SearchView) MenuItemCompat
						.getActionView(searchItem);
				if (mDogalize_Maps_Fragment != null)
					mDogalize_Maps_Fragment.setupSearchView(mSearchView);
				break;

			}
		}

		return super.onPrepareOptionsMenu(menu);
	}

	// Facebook Listeners

}
