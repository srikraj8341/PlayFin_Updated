package com.angelhack.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.angelhack.Constants;
import com.angelhack.R;
import com.angelhack.Utils;

public class LoginActivity extends ActionBarActivity {

	private EditText mPwd_et;
	private EditText mEmail_et;
	Button mLogin_btn;
	private String mEmail = "";
	private String mPWD = "";
	
	Button mFbSigin;
	Boolean onSuccesLogin = false;
	AlertDialog alertDialog;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		mContext = this;
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ab_logo);

		initViews();
		initClickListeners();

	}

	public void initViews() {

		mPwd_et = (EditText) findViewById(R.id.login_password);
		mEmail_et = (EditText) findViewById(R.id.login_email);
		mLogin_btn = (Button) findViewById(R.id.login);
		mFbSigin = (Button) findViewById(R.id.fblogin);
		mFbSigin.setBackgroundResource(R.drawable.facebook_login_button_background);
		mFbSigin.setPadding(8, 8, 8, 8);
		mFbSigin.setTypeface(Typeface.DEFAULT);
		mFbSigin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

		// mEmail_et.setText("chandu1307@live.com");
		// mPwd_et.setText("123456");

	}

	/*
	 * Used to initialize clicklisteners to all the clickable views in this
	 * screen
	 */
	private void initClickListeners() {
		mLogin_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				mEmail = mEmail_et.getText().toString().trim();
				mPWD = mPwd_et.getText().toString().trim();
				if (mEmail.length() > 0 && mPWD.length() > 0) {
					if (Utils.isMailValid(mEmail)) {

						if (Utils
								.haveNetworkConnection(LoginActivity.this)) {
						
							Intent i = new Intent(getBaseContext(),
									HomeActivity.class);
							i.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
							setResult(RESULT_OK);
							finish();
							startActivity(i);

						} else
							Utils.showAlertDialogWithTitle(
									Constants.NO_INTERNET_MESSAGE, "Warning",
									mContext);

					} else {
						Utils.showAlertDialogWithTitle(
								"Enter valid email and password", "Warning!!",
								mContext);
					}

				} else if (mEmail.length() == 0 && mPWD.length() == 0) {
					Utils.showAlertDialogWithTitle("Enter email and password",
							"Warning!!", mContext);

				} else if ((mEmail.length() > 0 && mPWD.length() == 0)
						|| (mEmail.length() == 0 && mPWD.length() > 0)) {
					if (Utils.isMailValid(mEmail))
						Utils.showAlertDialogWithTitle("Enter password",
								"Warning!!", mContext);
					else
						Utils.showAlertDialogWithTitle(
								"Enter vaild email and password", "Warning!!",
								mContext);
				} else if (mEmail.length() == 0) {
					Utils.showAlertDialogWithTitle("Enter email", "Warning!!",
							mContext);

				} else {
					Utils.showAlertDialogWithTitle("Enter password",
							"Warning!!", mContext);
				}

			}
		});

		mFbSigin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), HomeActivity.class);
				i.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
				setResult(RESULT_OK);
				finish();
				startActivity(i);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dogalize_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			setResult(RESULT_CANCELED);
			finish();
			// startActivity(new
			// Intent(DogalizeLoginActivity.this,Dogalize.class));
			// overridePendingTransition(R.anim.slide_in_left,
			// R.anim.slide_out_right);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		finish();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}
