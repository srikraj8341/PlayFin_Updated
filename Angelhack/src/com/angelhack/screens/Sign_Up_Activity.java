package com.angelhack.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.angelhack.Constants;
import com.angelhack.R;
import com.angelhack.Utils;

public class Sign_Up_Activity extends BaseActivity {

	Button mSignUp;
	
	
	Boolean onSuccesSignUp = false;
	
	Button mFacebookSignUp;
	
	AlertDialog alertDialog;
	

	private EditText mPwd_et, mverifypwd_et;
	private EditText mEmail_et;
	private String mEmail = "";
	private String mPWD = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_activity);
		// Show the Up button in the action bar.

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ab_logo);

		mPwd_et = (EditText) findViewById(R.id.signup_password);
		mEmail_et = (EditText) findViewById(R.id.signup_email);
		mverifypwd_et = (EditText) findViewById(R.id.verfiy_password);
		mSignUp = (Button) findViewById(R.id.signup);
		mFacebookSignUp = (Button) findViewById(R.id.fb_sign_up);
		mFacebookSignUp
				.setBackgroundResource(R.drawable.facebook_login_button_background);
		mFacebookSignUp.setPadding(8, 8, 8, 8);
		mFacebookSignUp.setTypeface(Typeface.DEFAULT);
		mFacebookSignUp.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

		

		mSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				

				mEmail = mEmail_et.getText().toString().trim();
				mPWD = mPwd_et.getText().toString().trim();

				if (!(mPWD.equals(mverifypwd_et.getText().toString().trim()))) {
					showAlertDialogWithTitle("Password not matching",
							"Warning!!");

				} else if (mEmail.length() > 0 && mPWD.length() > 0) {
					if (Utils.isMailValid(mEmail)) {

						if (mPwd_et.getText().toString().trim().length() >= 6) {

							if (haveNetworkConnection()) {
																//startAscTask();
								Intent i = new Intent(getBaseContext(), HomeActivity.class);
								i.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
								setResult(RESULT_OK);
								finish();
								startActivity(i);


							} else
								setAlertDialog(Constants.NO_INTERNET_MESSAGE,
										"Warning");
						} else
							setAlertDialog(
									"Password should contain atleast six characters.",
									"Warning");

					} else {
						showAlertDialogWithTitle(
								"Enter valid email and password", "Warning!!");

					}

				} else if (mEmail.length() == 0 && mPWD.length() == 0) {
					showAlertDialogWithTitle("Enter email and password",
							"Warning!!");

				} else if ((mEmail.length() > 0 && mPWD.length() == 0)
						|| (mEmail.length() == 0 && mPWD.length() > 0)) {
					if (Utils.isMailValid(mEmail))
						showAlertDialogWithTitle("Enter password", "Warning!!");
					else
						showAlertDialogWithTitle(
								"Enter vaild email and password", "Warning!!");

				} else if (mEmail.length() == 0) {
					showAlertDialogWithTitle("Enter email", "Warning!!");

				} else {
					showAlertDialogWithTitle("Enter password", "Warning!!");

				}
			}
		});

		mFacebookSignUp.setOnClickListener(new View.OnClickListener() {

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

	public boolean checkVaildation() {
		mEmail = mEmail_et.getText().toString().trim();
		mPWD = mPwd_et.getText().toString().trim();

		if (!(mPWD.equals(mverifypwd_et.getText().toString().trim()))) {
			showAlertDialogWithTitle("Password not matching", "Warning!!");
			return false;
		} else if (mEmail.length() > 0 && mPWD.length() > 0) {
			if (Utils.isMailValid(mEmail)) {

				if (haveNetworkConnection()) {
					
					return true;
				} else
					setAlertDialog(Constants.NO_INTERNET_MESSAGE, "Warning");
				return false;

			} else {
				showAlertDialogWithTitle("Enter valid email and password",
						"Warning!!");
				return false;
			}

		} else if (mEmail.length() == 0 && mPWD.length() == 0) {
			showAlertDialogWithTitle("Enter email and password", "Warning!!");
			return false;

		} else if ((mEmail.length() > 0 && mPWD.length() == 0)
				|| (mEmail.length() == 0 && mPWD.length() > 0)) {
			if (Utils.isMailValid(mEmail))
				showAlertDialogWithTitle("Enter password", "Warning!!");
			else
				showAlertDialogWithTitle("Enter vaild email and password",
						"Warning!!");
			return false;
		} else if (mEmail.length() == 0) {
			showAlertDialogWithTitle("Enter email", "Warning!!");
			return false;

		} else {
			showAlertDialogWithTitle("Enter password", "Warning!!");
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dogalize_sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_CANCELED);
			finish();
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

	public boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase(Constants.INTERNET_MODE_WIFI))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase(
					Constants.INTERNET_MODE_MOBILE))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	public void setAlertDialog(String msg, String title) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title)
				.setMessage(msg)
				.setNegativeButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						});
		alertDialog = builder.create();
		alertDialog.show();

	}

	public void showAlertDialogWithTitle(String msg, String title) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title)
				.setMessage(msg)
				.setNegativeButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.cancel();
							}
						});
		alertDialog = builder.create();
		alertDialog.show();

	}

	/*
	 * Starting asynchronous task and also showing progress dialog.
	 */
	

	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes

		outState.putBoolean("onSuccesSignUp", onSuccesSignUp);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		onSuccesSignUp = savedInstanceState.getBoolean("onSuccesSignUp");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}
