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

import org.json.JSONArray;
import org.json.JSONObject;

import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.util.FileUtils;
import com.dealsmagazine.util.NetworkUtils;
import com.dealsmagazine.seller.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class VoucherEditView extends Activity {

	public final static String KEY_VOUCHER_EDIT_CODE = "VOUCHER_EDIT_CODE";
	public final static String KEY_VOUCHER_TOTAL_SPEND = "VOUCHER_TOTAL_SPEND";
	public final static String KEY_VOUCHER_NOTE = "VOUCHER_NOTE";
	public final static String KEY_VOUCHER_ADDRESS = "VOUCHER_ADDRESS";
	public final static String KEY_VOUCHER_ADDRESS_ID = "VOUCHER_ASSRESS_ID";

	static private Context appContext;

	private ProgressDialog mypDialog;
	private EditText et_voucher_note;
	private EditText et_voucher_total_spend;
	private Button btn_voucher_edit_back;
	private Button btn_voucher_edit_save;
	private Spinner spinner_redeem_address;
	private TextView tv_voucher_edit_title;

	private String sVoucherEditCode = "";
	private String sVoucherEditNote = "";
	private String sVoucherSpend = "";
	private String sEditResult = "";

	private String redeemed_address = "";
	private int redeemed_address_id = 0;

	int selected_address_position = 0;
	boolean isGetAddressPosition = false;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voucher_edit_view);

		mypDialog = new ProgressDialog(this);

		try {
			Bundle extrasBundle = getIntent().getExtras();
			sVoucherEditCode = extrasBundle.getString(KEY_VOUCHER_EDIT_CODE);

		} catch (Exception e) {
			sVoucherEditCode = "";
		}

		try {
			Bundle extrasBundle = getIntent().getExtras();
			sVoucherSpend = extrasBundle.getString(KEY_VOUCHER_TOTAL_SPEND);

		} catch (Exception e) {
			sVoucherSpend = "";
		}

		try {
			Bundle extrasBundle = getIntent().getExtras();
			sVoucherEditNote = extrasBundle.getString(KEY_VOUCHER_NOTE);

		} catch (Exception e) {
			sVoucherEditNote = "";
		}

		try {
			Bundle extrasBundle = getIntent().getExtras();
			redeemed_address = extrasBundle.getString(KEY_VOUCHER_ADDRESS);

		} catch (Exception e) {
		}

		try {
			Bundle extrasBundle = getIntent().getExtras();
			redeemed_address_id = extrasBundle.getInt(KEY_VOUCHER_ADDRESS_ID);

		} catch (Exception e) {
		}

		spinner_redeem_address = (Spinner) this.findViewById(R.id.spinner_redeem_address);

		tv_voucher_edit_title = (TextView) this.findViewById(R.id.voucher_note_edit);
		tv_voucher_edit_title.setText("VOUCHER #: " + sVoucherEditCode);

		et_voucher_note = (EditText) this.findViewById(R.id.et_voucher_note);
		et_voucher_note.setText(sVoucherEditNote);

		et_voucher_total_spend = (EditText) this.findViewById(R.id.et_voucher_total_spend);
		et_voucher_total_spend.setInputType(InputType.TYPE_CLASS_PHONE);
		et_voucher_total_spend.setText(sVoucherSpend);

		btn_voucher_edit_save = (Button) this.findViewById(R.id.btn_voucher_edit_save);
		btn_voucher_edit_save.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				refreshEditData();

			}
		});

		btn_voucher_edit_back = (Button) this.findViewById(R.id.btn_voucher_edit_back);
		btn_voucher_edit_back.setOnClickListener(new OnClickListener() {
			public void onClick(View _v) {
				Intent i = new Intent(VoucherEditView.this, VoucherInfoView.class);
				i.putExtra(VoucherInfoView.KEY_VOUCHER_CODE, sVoucherEditCode);
				startActivityForResult(i, Globals.VOUCHER_INFO_VIEW);
				finish();
			}
		});

		// Get the Address Spinner Data
		if (redeemed_address_id == 0) {
			refreshRedeemAddressSpinner();
		} else {
			refreshRedeemAddressSpinnerWithID();
		}

		spinner_redeem_address.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (redeemed_address_id == 0) {
					getAddressIdByPosition(position);
				} else {
					getAddressIdByPositionWithID(position);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void refreshEditData() {
		// Get Edit Info
		sVoucherEditNote = et_voucher_note.getText().toString().trim();
		sVoucherSpend = et_voucher_total_spend.getText().toString().trim();

		new editVoucherInfoViewTask().execute();
	}

	/*
	 * JSON Parser, Refresh Address Spinner
	 * 
	 * When There is no address assigned
	 */
	public void refreshRedeemAddressSpinner() {

		final ArrayAdapter<String> address_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		address_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if (!NetworkUtils.isNetworkAvailable(this)) {

		} else {
			final byte[] buffer;

			buffer = readInternalStoragePrivate("login_data");
			final int position = ((User) getApplication()).loadbusinessPositonFromPreferences();

			if (buffer != null) {
				new Thread() {
					@Override
					public void run() {
						try {
							String string_redeem_address_spinner = new String(buffer);
							final JSONObject json_object_redeem_address = new JSONObject(string_redeem_address_spinner);
							final JSONObject json_object_redeem_data = json_object_redeem_address.getJSONObject("Data");
							JSONArray json_array_redeem_address_data = json_object_redeem_data.getJSONArray("MySellers");
							final JSONObject json_array_redeem_address_data_p = json_array_redeem_address_data.getJSONObject(position);
							JSONArray json_array_redeem_address = json_array_redeem_address_data_p.getJSONArray("Addresses");
							int countAddress = json_array_redeem_address.length();

							// Set default value for spinner, position 0
							address_adapter.add("No Redeemed Address Assigned");

							// Refresh Spinner Adapter
							for (int i = 0; i < countAddress; i++) {
								JSONObject oAddress = json_array_redeem_address.getJSONObject(i);
								String address1 = oAddress.getString("Address1");
								String address2 = oAddress.getString("Address2");
								String city = oAddress.getString("City");
								String state = oAddress.getString("State");
								String zip = oAddress.getString("Zip");
								address_adapter.add(address1 + " " + address2 + ", " + city + ", " + state + " " + zip);
							}

							VoucherEditView.this.runOnUiThread(new Runnable() {
								public void run() {
									spinner_redeem_address.setAdapter(address_adapter);
								}
							});

						} catch (Exception e) {

						}
					}
				}.start();
			}
		}
	}

	/*
	 * Get the Address ID
	 */
	private void getAddressIdByPosition(final int position_redeem) {

		if (position_redeem == 0) {
			((User) getApplication()).setredeemAddress(0);
			((User) getApplication()).saveredeemAddressToPreferences();

		} else {

			final int position_business = ((User) getApplication()).loadbusinessPositonFromPreferences();

			if (!NetworkUtils.isNetworkAvailable(this)) {

			} else {
				final byte[] buffer;
				buffer = readInternalStoragePrivate(Globals.LOGIN_CACHE);
				if (buffer != null) {
					new Thread() {
						@Override
						public void run() {
							try {

								String string_redeem_address = new String(buffer);
								final JSONObject json_object_address = new JSONObject(string_redeem_address);
								final JSONObject json_object_address_data = json_object_address.getJSONObject("Data");
								final JSONArray json_array_address = json_object_address_data.getJSONArray("MySellers");
								final JSONObject json_object_address_a = json_array_address.getJSONObject(position_business);
								final JSONArray json_array_redeem_address = json_object_address_a.getJSONArray("Addresses");

								// Spinner Array Start from 1, Not 0
								final JSONObject oRedeemAddress = json_array_redeem_address.getJSONObject(position_redeem - 1);
								final int addressId = oRedeemAddress.getInt("SellerAddressID");

								((User) getApplication()).setredeemAddress(addressId);
								((User) getApplication()).saveredeemAddressToPreferences();

							} catch (Exception e) {

							}
						}
					}.start();
				}
			}
		}
	}

	/*
	 * When Address have assigned, set the selected value to default;
	 */
	public void refreshRedeemAddressSpinnerWithID() {

		final ArrayAdapter<String> address_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		address_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if (!NetworkUtils.isNetworkAvailable(this)) {

		} else {
			final byte[] buffer;

			buffer = readInternalStoragePrivate("login_data");
			final int position = ((User) getApplication()).loadbusinessPositonFromPreferences();

			if (buffer != null) {
				new Thread() {
					@Override
					public void run() {
						try {
							String string_redeem_address_spinner = new String(buffer);
							final JSONObject json_object_redeem_address = new JSONObject(string_redeem_address_spinner);
							final JSONObject json_object_redeem_data = json_object_redeem_address.getJSONObject("Data");
							JSONArray json_array_redeem_address_data = json_object_redeem_data.getJSONArray("MySellers");
							final JSONObject json_array_redeem_address_data_p = json_array_redeem_address_data.getJSONObject(position);
							JSONArray json_array_redeem_address = json_array_redeem_address_data_p.getJSONArray("Addresses");

							int countAddress = json_array_redeem_address.length();

							// Set default value for spinner, position 0

							address_adapter.add(redeemed_address);

							address_adapter.add("No Redeemed Address Assigned");

							// Refresh Spinner Adapter
							for (int i = 0; i < countAddress; i++) {
								JSONObject oAddress = json_array_redeem_address.getJSONObject(i);
								String address1 = oAddress.getString("Address1");
								String address2 = oAddress.getString("Address2");
								String city = oAddress.getString("City");
								String state = oAddress.getString("State");
								String zip = oAddress.getString("Zip");

								int seller_address_id_temp = oAddress.getInt("SellerAddressID");

								if (seller_address_id_temp != redeemed_address_id) {

									if (!isGetAddressPosition) {
										selected_address_position++;
									}

									address_adapter.add(address1 + " " + address2 + ", " + city + ", " + state + " " + zip);

								} else if (seller_address_id_temp == redeemed_address_id) {
									isGetAddressPosition = true;
								}

								// address_adapter.add(address1 + " " + address2 + ", " + city + ", " + state + " " + zip);
							}

							VoucherEditView.this.runOnUiThread(new Runnable() {
								public void run() {
									spinner_redeem_address.setAdapter(address_adapter);
								}
							});

						} catch (Exception e) {

						}
					}
				}.start();
			}
		}
	}

	/*
	 * Get the Assigned or Other Address ID
	 */
	private void getAddressIdByPositionWithID(final int position_redeem) {

		if (position_redeem == 0) {
			((User) getApplication()).setredeemAddress(redeemed_address_id);
			((User) getApplication()).saveredeemAddressToPreferences();

		} else if (position_redeem == 1) {
			((User) getApplication()).setredeemAddress(0);
			((User) getApplication()).saveredeemAddressToPreferences();
		}

		else {

			final int position_business = ((User) getApplication()).loadbusinessPositonFromPreferences();

			if (!NetworkUtils.isNetworkAvailable(this)) {

			} else {
				final byte[] buffer;
				buffer = readInternalStoragePrivate(Globals.LOGIN_CACHE);
				if (buffer != null) {
					new Thread() {
						@Override
						public void run() {
							try {

								String string_redeem_address = new String(buffer);
								final JSONObject json_object_address = new JSONObject(string_redeem_address);
								final JSONObject json_object_address_data = json_object_address.getJSONObject("Data");
								final JSONArray json_array_address = json_object_address_data.getJSONArray("MySellers");
								final JSONObject json_object_address_a = json_array_address.getJSONObject(position_business);
								final JSONArray json_array_redeem_address = json_object_address_a.getJSONArray("Addresses");

								int addressId = 0;

								// Spinner Array Start from 1, Not 0

								if (position_redeem > 1 && position_redeem < selected_address_position + 2) {

									final JSONObject oRedeemAddress = json_array_redeem_address.getJSONObject(position_redeem - 2);
									addressId = oRedeemAddress.getInt("SellerAddressID");

								} else if (position_redeem == selected_address_position + 2) {
									final JSONObject oRedeemAddress = json_array_redeem_address.getJSONObject(selected_address_position);
									addressId = oRedeemAddress.getInt("SellerAddressID");
								} else if (position_redeem > selected_address_position + 2) {
									final JSONObject oRedeemAddress = json_array_redeem_address.getJSONObject(position_redeem - 3);
									addressId = oRedeemAddress.getInt("SellerAddressID");
								}

								((User) getApplication()).setredeemAddress(addressId);
								((User) getApplication()).saveredeemAddressToPreferences();

							} catch (Exception e) {

							}
						}
					}.start();
				}
			}
		}
	}

	private void showMessageBox(String message) {
		new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(VoucherEditView.this, VoucherInfoView.class);
				mypDialog.dismiss();
				i.putExtra(VoucherInfoView.KEY_VOUCHER_CODE, sVoucherEditCode);
				startActivityForResult(i, Globals.VOUCHER_INFO_VIEW);
				finish();
			}
		}).setMessage(message).show();
	}

	private void showMessageBox_error(String message) {
		new AlertDialog.Builder(this).setPositiveButton("Back", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mypDialog.dismiss();
			}
		}).setMessage(message).show();
	}

	/*
	 * JSON Parser
	 */
	public boolean isEditVoucherSuccess(byte[] getVoucherResult_byte) {

		boolean boolean_getVoucherResult = true;
		String jsonResultString = FileUtils.convertByteArrayToString(getVoucherResult_byte);
		try {
			final JSONObject json_object_validate = new JSONObject(jsonResultString);
			boolean_getVoucherResult = json_object_validate.getBoolean("Success");
			if (boolean_getVoucherResult) {
				String Message = json_object_validate.getString("Message");
				sEditResult = Message;

			} else {
				String errorMessage = json_object_validate.getString("Message");
				sEditResult = errorMessage;
				// api_message = json_validate.getString("ApiVersion");
			}
		} catch (Exception e) {

		}
		return boolean_getVoucherResult;
	}

	/*
	 * Read Voucher from Cache
	 */
	public byte[] readInternalStoragePrivate(String filename) {
		int len = 1024 * 128;
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

		} catch (Exception e) {

		}
		return buffer;
	}

	/*
	 * *********************Edit Voucher Task*******************
	 */
	class editVoucherInfoViewTask extends AsyncTask<Void, Void, byte[]> {
		public byte[] doInBackground(Void... params) {
			byte[] buffer_c = null;
			String username = ((User) getApplication()).loadusernameFrompreferences();
			String password = ((User) getApplication()).loadpasswordFrompreferences();
			String redeemaddress = Integer.toString(((User) getApplication()).loadredeemAddressFromPreferences());
			try {
				buffer_c = NetworkUtils.editVoucher(username, password, sVoucherEditCode, sVoucherEditNote, sVoucherSpend, redeemaddress);

			} catch (Exception e) {
				showMessageBox(getString(R.string.net_time_out));
			}
			return buffer_c;
		}

		@Override
		public void onPreExecute() {

			mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mypDialog.setTitle(getString(R.string.app_name));
			mypDialog.setMessage("Saving...");
			mypDialog.show();
		}

		@Override
		public void onPostExecute(byte[] buffer_b) {

			if (buffer_b != null) {
				try {
					if (isEditVoucherSuccess(buffer_b)) {
						showMessageBox(sEditResult);

					} else {
						showMessageBox_error(sEditResult);
					}

				} catch (Exception e) {
					showMessageBox(getString(R.string.net_time_out));
				}

			} else {
				showMessageBox(getString(R.string.net_time_out));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.setClass(VoucherEditView.this, VoucherInfoView.class);
		i.putExtra(VoucherInfoView.KEY_VOUCHER_CODE, sVoucherEditCode);
		startActivity(i);
		finish();
	}
}
