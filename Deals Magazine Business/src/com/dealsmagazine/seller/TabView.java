package com.dealsmagazine.seller;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class TabView extends TabActivity {

	public final static String KEY_TAB_INDEX = "TAB_INDEX";
	private TextView tv_username;
	private int tab_index = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_view);

		// Get which tab to display
		Bundle extras = getIntent().getExtras();
		try {
			tab_index = extras.getInt(KEY_TAB_INDEX);
		} catch (Exception e) {
		}

		tv_username = (TextView) this.findViewById(R.id.tv_tab_username);
		tv_username.setText(((User) getApplication()).loadusernameFrompreferences());

		Resources res = getResources();
		final TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Create an Intent to launch an Activity for the tab (to be reused)

		// Seller Info Tab
		intent = new Intent().setClass(this, SellerInfoView.class);
		spec = tabHost.newTabSpec("businesses").setIndicator("Business", res.getDrawable(R.drawable.ic_tab_business)).setContent(intent);
		tabHost.addTab(spec);

		// Input Voucher Menu
		intent = new Intent().setClass(this, TabInputVoucherView.class);
		spec = tabHost.newTabSpec("redeem").setIndicator("Redeem", res.getDrawable(R.drawable.ic_tab_redeem)).setContent(intent);
		tabHost.addTab(spec);
		
		// Deals list Tab
		intent = new Intent().setClass(this, DealsListView.class);
		spec = tabHost.newTabSpec("deals").setIndicator("My Deals", res.getDrawable(R.drawable.ic_tab_deals)).setContent(intent);
		tabHost.addTab(spec);

		// Contact Tab
		intent = new Intent().setClass(this, ContactUsView.class);
		spec = tabHost.newTabSpec("contacts").setIndicator("Contact", res.getDrawable(R.drawable.ic_tab_contact)).setContent(intent);
		tabHost.addTab(spec);
		setTabColor(tabHost);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				setTabColor(tabHost);
			}
		});

		// Set display tab
		tabHost.setCurrentTab(tab_index);
	}

	public static void setTabColor(TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E2DDD9")); // unselected
		}
		tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#939393")); // selected
	}
}
