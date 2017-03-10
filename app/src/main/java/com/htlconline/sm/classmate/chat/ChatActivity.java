package com.htlconline.sm.classmate.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.htlconline.sm.classmate.R;

public class ChatActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.back);
		setSupportActionBar(toolbar);

		if(getIntent().getExtras()!= null){
			getSupportActionBar().setTitle(getIntent().getExtras().getString("receiverName"));
		}


		openPage();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
	}




	private void openPage(){
		Fragment fragment = new ChatNewFragment();
		if(getIntent().getExtras() != null)
		fragment.setArguments(getIntent().getExtras());
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container_body, fragment);
		fragmentTransaction.attach(fragment);
		fragmentTransaction.commit();
	}
}
