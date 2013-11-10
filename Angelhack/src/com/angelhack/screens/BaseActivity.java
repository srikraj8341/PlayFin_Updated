package com.angelhack.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.angelhack.R;

public class BaseActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		overridePendingTransition(R.anim.slide_in_right,
//				R.anim.slide_out_left);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

}
