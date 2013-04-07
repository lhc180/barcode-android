/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.util.Eula;
import com.dealsmagazine.util.NetworkUtils;
import com.dealsmagazine.seller.R;

public class DealsMagazineBusinessActivity extends Activity {

	private static final int MENU_LOGOUT = 1;
	private static final int MENU_LOGIN = 2;
	final int MENU_CANCEL = 3;

	private ImageButton imgbtn_scanvouchers;
	private ImageButton imgbtn_mybusiness;
	private Button btn_need_help;
	private TextView tv_mainmenu;

	final String _nullLoginStatus = new String("");
	Context mContext = this;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Eula.show(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn_need_help = (Button) this.findViewById(R.id.btn_need_help);
		imgbtn_scanvouchers = (ImageButton) this.findViewById(R.id.imgbtn_scanvouchers);
		imgbtn_mybusiness = (ImageButton) this.findViewById(R.id.imgbtn_mybusiness);
		tv_mainmenu = (TextView) this.findViewById(R.id.text_mainmenu);
		tv_mainmenu.setVisibility(View.GONE);

		if (!NetworkUtils.isNetworkAvailable(this)) {
			tv_mainmenu.setText("Please connect to Internet for using redeem function.");
			tv_mainmenu.setVisibility(View.VISIBLE);
		}
		
		btn_need_help.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.deals_magazine_url)));
				startActivity(i);
			}
		});

		imgbtn_scanvouchers.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				if (isUserLogIn()) {
					Intent i = new Intent(DealsMagazineBusinessActivity.this, TabView.class);
					i.putExtra(TabView.KEY_TAB_INDEX, 1);
					startActivityForResult(i, Globals.INPUT_VOUCHER_VIEW);
					finish();
				} else {
					Intent i = new Intent(DealsMagazineBusinessActivity.this, LoginView.class);
					i.putExtra(LoginView.KEY_LOGIN_VIEW_SELECTOR, Globals.INPUT_VOUCHER_VIEW);
					startActivityForResult(i, Globals.LOGIN_VIEW);
					finish();
				}
			}
		});

		imgbtn_mybusiness.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				if (isUserLogIn()) {
					Intent i = new Intent(DealsMagazineBusinessActivity.this, TabView.class);
					startActivityForResult(i, Globals.SELLER_INFO_VIEW);
					finish();
				} else {
					Intent i = new Intent(DealsMagazineBusinessActivity.this, LoginView.class);
					i.putExtra(LoginView.KEY_LOGIN_VIEW_SELECTOR, Globals.SELLER_INFO_VIEW);
					startActivityForResult(i, Globals.LOGIN_VIEW);
					finish();
				}
			}
		});

	}

	public boolean isUserLogIn() {
		if (!((User) getApplication()).loaduserIdFromPreferences().equals(_nullLoginStatus) && !((User) getApplication()).loadusernameFrompreferences().equals(_nullLoginStatus) && !((User) getApplication()).loadpasswordFrompreferences().equals(_nullLoginStatus)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Options Menus
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isUserLogIn()) {
			menu.add(0, MENU_LOGOUT, 0, "Logout").setIcon(android.R.drawable.ic_lock_power_off);
		} else {
			menu.add(0, MENU_LOGIN, 0, "Login").setIcon(android.R.drawable.ic_menu_add);
		}
		menu.add(0, MENU_CANCEL, 0, "Cancel").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case MENU_LOGIN:
			Intent ic = new Intent(DealsMagazineBusinessActivity.this, LoginView.class);
			ic.putExtra(LoginView.KEY_LOGIN_VIEW_SELECTOR, Globals.WHERE_YOU_SHOP_BUSINESS_ACTIVITY);
			startActivityForResult(ic, Globals.LOGOUT_VIEW);
			finish();
			break;

		case MENU_LOGOUT:
			Intent ia = new Intent(DealsMagazineBusinessActivity.this, LogoutView.class);
			ia.putExtra(LogoutView.KEY_LOGOUT_SELECTOR, Globals.WHERE_YOU_SHOP_BUSINESS_ACTIVITY);
			startActivityForResult(ia, Globals.LOGOUT_VIEW);
			finish();
			break;

		case MENU_CANCEL:
			break;

		default:
			break;
		}
		return true;
	}

}