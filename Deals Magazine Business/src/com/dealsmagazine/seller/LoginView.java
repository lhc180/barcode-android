/*
 * 
 * � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.util.FileUtils;
import com.dealsmagazine.util.LoginViewUtils;
import com.dealsmagazine.util.NetworkUtils;
import com.dealsmagazine.seller.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class LoginView extends Activity {

	static private Context appContext;
	public final static String KEY_LOGIN_VIEW_SELECTOR = "LOGINVIEW_SELECTOR";
	private final String SHARE_LOGIN_TAG = "MAP_SHARE_LOGIN_TAG";
	private final String SHARE_LOGIN_USERNAME = "MAP_LOGIN_USERNAME";
	private final String SHARE_LOGIN_PASSWORD = "MAP_LOGIN_PASSWORD";
	private final Handler mHandler = new Handler();

	private EditText edittext_username;
	private EditText edittext_password;
	private ProgressDialog my_pdialog;
	private ImageButton imgbtn_login;
	private Button btn_login_help;
	private Button btn_login_about;
	private CheckBox view_rememberMe;

	private int selector = 0;
	private String sUserId = "";
	private String sFirstName = "";
	private String sLastName = "";
	private String sErrorMessage = "Network error, please try again.";
	private boolean isLoginResultValid = false;
	private boolean save_password = false;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.login_view);
		appContext = this;
		my_pdialog = new ProgressDialog(this);
		my_pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		my_pdialog.setTitle(getString(R.string.app_name));

		SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(appContext);
		String autosave_password_prefs = appContext.getString(R.string.prefs_autosave_password);

		// Settings
		try {
			save_password = sprefs.getBoolean(autosave_password_prefs, false);
		} catch (ClassCastException e) {
			save_password = false;
		}

		// Network Connection
		if (!NetworkUtils.isNetworkAvailable(this)) {
			Intent i = new Intent(LoginView.this, NetworkConnectionView.class);
			startActivity(i);
			finish();
		}

		// View Selector
		try {
			Bundle extras = getIntent().getExtras();
			selector = extras.getInt(KEY_LOGIN_VIEW_SELECTOR);

		} catch (Exception e) {
			selector = 0;
		}

		// Initial Login Views
		initialLoginView();

		// Save User information in SharedPreferences
		isRememberUser(save_password);

		// Setup Button Click Listener
		setLoginListener();
	}

	/*
	 * Initial Views
	 */
	private void initialLoginView() {

		edittext_username = (EditText) this.findViewById(R.id.edittext_username);
		edittext_password = (EditText) this.findViewById(R.id.edittext_password);

		imgbtn_login = (ImageButton) this.findViewById(R.id.imgbtn_login);
		btn_login_help = (Button) this.findViewById(R.id.btn_login_help);
		btn_login_about = (Button) this.findViewById(R.id.btn_login_about);

		btn_login_help.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginView.this, ContactUsView.class);
				i.putExtra(ContactUsView.KEY_CONTACT_US_SELECTOR, Globals.LOGIN_VIEW);
				startActivityForResult(i, Globals.LOGIN_VIEW);
				finish();
			}
		});

		btn_login_about.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginView.this, AboutView.class);
				startActivityForResult(i, Globals.LOGIN_VIEW);
				finish();
			}
		});

		// Hidden CheckBox
		view_rememberMe = (CheckBox) this.findViewById(R.id.loginRememberMeCheckBox);
		view_rememberMe.setVisibility(View.INVISIBLE);
	}

	/*
	 * Get the UserName and Password
	 */
	private void isRememberUser(boolean isRememberMe) {
		SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);

		String shareuserName = share.getString(SHARE_LOGIN_USERNAME, "");
		String sharepassword = share.getString(SHARE_LOGIN_PASSWORD, "");

		Log.d(this.toString(), "userName=" + shareuserName + " password=" + sharepassword);

		if (!"".equals(shareuserName)) {
			edittext_username.setText(shareuserName);
		}
		if (!"".equals(sharepassword)) {
			edittext_password.setText("");
			view_rememberMe.setChecked(true);
		}
		if (!isRememberMe) {
			edittext_password.setText("");
		}
		// If store password, let sign in button get focus
		if (edittext_password.getText().toString().length() > 0) {
		}
		share = null;
	}

	/*
	 * Set Listener
	 */
	private void setLoginListener() {

		imgbtn_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// If UserName or PassWord is empty
				if (edittext_username.getText().toString().trim().length() == 0 || edittext_password.getText().toString().trim().length() == 0) {
					showMessageBox(getString(R.string.enter_login));
					imgbtn_login.setVisibility(View.VISIBLE);
					return;

				} else {
					new VaildLoginTask().execute();
				}
			}
		});

		// Remember UserName
		view_rememberMe.setOnCheckedChangeListener(rememberMeListener);
	}

	/*
	 * CheckBox Listener
	 */
	private OnCheckedChangeListener rememberMeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (view_rememberMe.isChecked()) {
				Toast.makeText(LoginView.this, "Name Saved", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/*
	 * Save UserName and password to SharedPreferences
	 */
	private void saveSharePreferences(boolean saveUserName, boolean savePassword) {
		SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);
		if (saveUserName) {
			Log.d(this.toString(), "saveUserName=" + edittext_username.getText().toString());
			share.edit().putString(SHARE_LOGIN_USERNAME, edittext_username.getText().toString()).commit();
		}
		if (savePassword) {
			share.edit().putString(SHARE_LOGIN_PASSWORD, edittext_password.getText().toString()).commit();
		}
		share = null;
	}

	/*
	 * Alert
	 */
	private void showMessageBox(String message) {
		new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
	}

	/*
	 * Clear the SharedPreferences
	 */
	private void clearSharePassword() {
		SharedPreferences share = getSharedPreferences(SHARE_LOGIN_TAG, 0);
		share.edit().putString(SHARE_LOGIN_PASSWORD, "").commit();
		share = null;
	}

	/*
	 * Save Login result
	 */
	final Runnable mUpdateLoginResults = new Runnable() {
		public void run() {

			imgbtn_login.setVisibility(View.INVISIBLE);
			if (!isLoginResultValid) {
				((User) getApplication()).logout();
			} else {
				String username = edittext_username.getText().toString().trim();
				String password = edittext_password.getText().toString().trim();

				((User) getApplication()).setuserId(sUserId);
				((User) getApplication()).setusername(username);
				((User) getApplication()).setpassword(password);
				((User) getApplication()).setbusinessName(sFirstName + sLastName);
				((User) getApplication()).saveToPreferences();
			}
			// Re-enable the Fields and Button
			edittext_username.setEnabled(true);
			edittext_password.setEnabled(true);
			imgbtn_login.setEnabled(true);
		}
	};

	/*
	 * Cache the login result
	 */
	private void saveLoginResult(String loginResult) {

		byte[] loginresult = FileUtils.convertStringToByteArray(loginResult);
		LoginViewUtils.writeInternalStoragePrivate(this, Globals.LOGIN_CACHE, loginresult);
	}

	/*
	 * ValidateLogin JSON Parser
	 */
	public boolean isUserValidate(String loginresult) {
		boolean boolean_loginResult = false;
		String jsonResultString = loginresult;
		try {
			final JSONObject json_object_validate = new JSONObject(jsonResultString);
			boolean_loginResult = json_object_validate.getBoolean("Success");
			if (boolean_loginResult) {
				JSONObject json_object_validate_data = json_object_validate.getJSONObject("Data");
				sUserId = json_object_validate_data.getString("UserID");
			} else {
				sErrorMessage = json_object_validate.getString("Message");
				// api_message = json_validate.getString("ApiVersion");
			}
		} catch (Exception e) {
		}
		return boolean_loginResult;
	}

	/*
	 * Validate Task
	 */
	class VaildLoginTask extends AsyncTask<Void, Void, String> {
		public String doInBackground(Void... params) {
			String loginResult = "";
			String username = edittext_username.getText().toString().trim();
			String password = edittext_password.getText().toString().trim();

			try {
				loginResult = NetworkUtils.validateLogin(username, password);
				saveLoginResult(loginResult);
			} catch (Exception e) {
				showMessageBox(getString(R.string.net_time_out));
			}
			return loginResult;
		}

		@Override
		public void onPreExecute() {
			my_pdialog.setMessage("validating...");
			my_pdialog.show();
		}

		@Override
		public void onPostExecute(String loginResult) {
			my_pdialog.dismiss();
			isLoginResultValid = isUserValidate(loginResult);
			if (isLoginResultValid) {
				new UpdateUserDataTask().execute();
			} else {
				if (!view_rememberMe.isChecked()) {
					clearSharePassword();
				}
				imgbtn_login.setVisibility(View.VISIBLE);
				showMessageBox(sErrorMessage);
				edittext_username.setEnabled(true);
				edittext_password.setEnabled(true);
				edittext_password.setText("");
				imgbtn_login.setEnabled(true);
			}
		}
	}

	/*
	 * Load User Data Task
	 */
	class UpdateUserDataTask extends AsyncTask<Void, Void, String> {
		public String doInBackground(Void... params) {
			String tempUserData = "";
			return tempUserData;
		}

		@Override
		public void onPreExecute() {
			my_pdialog.setMessage("Loading...");
			my_pdialog.show();
		}

		@Override
		public void onPostExecute(String tempUserData) {
			my_pdialog.dismiss();
			if (save_password) {
				saveSharePreferences(true, true);
			} else {
				saveSharePreferences(true, false);
			}
			mHandler.post(mUpdateLoginResults);
			// Start Select Activity
			if (selector == Globals.INPUT_VOUCHER_VIEW) {
				selector = 0;
				Intent intent = new Intent();
				intent.setClass(LoginView.this, TabView.class);
				intent.putExtra(TabView.KEY_TAB_INDEX, 1);
				startActivity(intent);
				LoginView.this.finish();
			} else if (selector == Globals.SELLER_INFO_VIEW) {
				selector = 0;
				Intent intent = new Intent();
				intent.setClass(LoginView.this, TabView.class);
				startActivity(intent);
				LoginView.this.finish();
			} else if (selector == Globals.WHERE_YOU_SHOP_BUSINESS_ACTIVITY) {
				selector = 0;
				Intent intent = new Intent();
				intent.setClass(LoginView.this, DealsMagazineBusinessActivity.class);
				startActivity(intent);
				LoginView.this.finish();
			} else {
				selector = 0;
				Intent intent = new Intent();
				intent.setClass(LoginView.this, DealsMagazineBusinessActivity.class);
				startActivity(intent);
				LoginView.this.finish();
			}
		}
	}

	/*
	 * Cache Reader
	 */
	public byte[] readInternalStoragePrivate(String filename) {
		int len = 1024;
		byte[] buffer = new byte[len];
		try {
			FileInputStream fis = openFileInput(filename);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int nrb = fis.read(buffer, 0, len);
			while (nrb != -1) {
				baos.write(buffer, 0, nrb);
				nrb = fis.read(buffer, 0, len);
			}
			buffer = baos.toByteArray();
			fis.close();
		} catch (FileNotFoundException e) {
			Log.d(appContext.getString(R.string.app_name) + ".readInternalStorage()", "FileNotFoundException: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(appContext.getString(R.string.app_name) + ".readInternalStorage()", "IOException: " + e);
			e.printStackTrace();
		}
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(LoginView.this, DealsMagazineBusinessActivity.class);
		startActivity(intent);
		finish();
	}

}
