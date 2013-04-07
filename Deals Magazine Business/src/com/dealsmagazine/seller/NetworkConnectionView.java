/*
 * 
 4* � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.seller.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;

/*
 * Network Connection View
 * 
 * @ Network error
 * 
 * @ Network Setting
 * 
 */
public class NetworkConnectionView extends Activity {

	private ImageButton imgbtn_retry;
	private ImageButton imgbtn_quit;
	private ImageButton imgbtn_networkset;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_connection_view);

		imgbtn_retry = (ImageButton) this.findViewById(R.id.btnRetry);
		imgbtn_quit = (ImageButton) this.findViewById(R.id.btnQuit);
		imgbtn_networkset = (ImageButton) this.findViewById(R.id.btn_networkset);

		/*
		 * Determine the caller that started this activity and the passed error message
		 */

		// Wire-up click events
		imgbtn_retry.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				retry();
				finish();
			}
		});

		imgbtn_quit.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				finish();
				Intent i = new Intent(NetworkConnectionView.this, DealsMagazineBusinessActivity.class);
				startActivityForResult(i, Globals.WHERE_YOU_SHOP_BUSINESS_ACTIVITY);
			}
		});

		// Go to Network Setting MENU
		imgbtn_networkset.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				Intent mIntent = new Intent();
				ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
				mIntent.setComponent(comp);
				mIntent.setAction("android.intent.action.VIEW");
				startActivityForResult(mIntent, 0);
			}
		});
	}

	// Refresh Activity
	private void retry() {
		startActivity(new Intent(this, LoginView.class));
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(NetworkConnectionView.this, DealsMagazineBusinessActivity.class);
		startActivity(intent);
		finish();
	}

}
