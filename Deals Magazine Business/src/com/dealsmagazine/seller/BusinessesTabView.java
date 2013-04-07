package com.dealsmagazine.seller;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class BusinessesTabView extends TabActivity {

	private TextView tv_username;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.businesses_tab_view);

		tv_username = (TextView) this.findViewById(R.id.tv_tab_username);
		tv_username.setText(((User) getApplication()).loadusernameFrompreferences());

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Create an Intent to launch an Activity for the tab (to be reused)

		// Seller Info Tab
		intent = new Intent().setClass(this, SellerInfoView.class);
		spec = tabHost.newTabSpec("businesses").setIndicator("My Businesses", res.getDrawable(R.drawable.account)).setContent(intent);
		tabHost.addTab(spec);

		// Deals list Tab
		intent = new Intent().setClass(this, DealsListView.class);
		spec = tabHost.newTabSpec("deals").setIndicator("My Deals", res.getDrawable(R.drawable.campaign)).setContent(intent);
		tabHost.addTab(spec);

		// Contact Tab
		intent = new Intent().setClass(this, ContactUsView.class);
		spec = tabHost.newTabSpec("contacts").setIndicator("Contact Us", res.getDrawable(R.drawable.contactus)).setContent(intent);
		tabHost.addTab(spec);
	}
}
