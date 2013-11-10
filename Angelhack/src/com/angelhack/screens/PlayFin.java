package com.angelhack.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.angelhack.Preferences;
import com.angelhack.R;

public class PlayFin extends Activity {

	// private boolean isOnScreen = true;
	Preferences mPreferences;
	boolean mConfigChanged = false;
	boolean isAppFirstTime = false;
	LinearLayout mGetStarted;
	LinearLayout mSignin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPreferences = new Preferences(this, getPackageName());
		setContentView(R.layout.playfin_activity);
		// Show the Up button in the action bar.

		mGetStarted = (LinearLayout) findViewById(R.id.getstarted);
		mSignin = (LinearLayout) findViewById(R.id.signin);
		isAppFirstTime = mPreferences.getValueFromPreference(
				getString(R.string.key_isAppFirstTime), true);
		// getSupportActionBar().hide();
		initViews();

	}

	public void initViews() {
		mGetStarted.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(PlayFin.this,
						Sign_Up_Activity.class), 0);
				// overridePendingTransition(R.anim.slide_in_right,
				// R.anim.slide_out_left);

			}
		});
		mSignin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (isAppFirstTime) {
				// startActivity(new Intent(Dogalize.this,
				// DogalizeLoginActivity.class));
				// } else {
				// startActivityForResult(new Intent(Dogalize.this,
				// HomeActivity.class),0);
				//
				// }
				startActivityForResult(new Intent(PlayFin.this,
						LoginActivity.class), 0);
				// overridePendingTransition(R.anim.slide_in_right,
				// R.anim.slide_out_left);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == RESULT_OK)
		{
			finish();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}