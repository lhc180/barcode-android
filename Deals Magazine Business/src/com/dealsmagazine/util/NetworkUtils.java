/*
 * 
 * � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.seller.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * Network
 * 
 */
public class NetworkUtils {

	static String url_null = new String("");

	/*
	 * Check Network Connection
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo == null) {
			return false;
		}
		if (netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/*
	 * Login
	 * 
	 * @ username
	 * 
	 * @ passwprd
	 */
	public static String validateLogin(String username, String password) {
		String result = "";
		String apitoken = Globals.API_TOKEN;

		// Get the Login Url
		String url_valid = Globals.URL_LOGIN_BASE;
		HttpPost httpRequest = new HttpPost(url_valid);

		// Use BasicNameValuePair to store POST data
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("apiToken", apitoken));
		pairs.add(new BasicNameValuePair("sellerEmail", username.trim()));
		pairs.add(new BasicNameValuePair("sellerPassword", password.trim()));

		try {

			// Set Character
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf8");

			// Request HttpRequest
			httpRequest.setEntity(entity);

			// AddHeader
			DefaultHttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, Globals.NETWORK_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Globals.NETWORK_READ_TIMEOUT);
			client.setParams(params);

			// Get HTTPResponse
			HttpResponse response = client.execute(httpRequest);

			// Get return String
			result = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {

		}
		return result;
	}

	/*
	 * Get Voucher Info
	 */
	public static byte[] getVoucherInfo(String username, String password, String voucherCode) {
		byte[] result_byte = null;
		String result_string = "";
		String apitoken = Globals.API_TOKEN;
		String url_getVoucher = Globals.URL_GET_VOUCHER_BASE;
		HttpPost httpRequest = new HttpPost(url_getVoucher);

		// Use BasicNameValuePair to store POST data
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("apiToken", apitoken));
		pairs.add(new BasicNameValuePair("sellerEmail", username.trim()));
		pairs.add(new BasicNameValuePair("sellerPassword", password.trim()));
		pairs.add(new BasicNameValuePair("voucherCode", voucherCode.trim()));

		try {

			// Set Character
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf8");

			// Request HttpRequest
			httpRequest.setEntity(entity);

			// AddHeader
			DefaultHttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, Globals.NETWORK_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Globals.NETWORK_READ_TIMEOUT);
			client.setParams(params);

			// Get HTTPResponse
			HttpResponse response = client.execute(httpRequest);

			// Get return String
			result_string = EntityUtils.toString(response.getEntity());
			result_byte = FileUtils.convertStringToByteArray(result_string);
		} catch (Exception e) {
		}
		return result_byte;
	}

	/*
	 * Redeem Voucher #
	 */
	public static byte[] redeemVoucher(String username, String password, String voucherCode) {
		byte[] result_byte = null;
		String result_string = "";
		String apitoken = Globals.API_TOKEN;
		String url_redeemVoucher = Globals.URL_REDEEM_VOUCHER_BASE;
		HttpPost httpRequest = new HttpPost(url_redeemVoucher);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("apiToken", apitoken));
		pairs.add(new BasicNameValuePair("sellerEmail", username.trim()));
		pairs.add(new BasicNameValuePair("sellerPassword", password.trim()));
		pairs.add(new BasicNameValuePair("voucherCode", voucherCode.trim()));

		try {

			// Set Character
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf8");

			// Request HttpRequest
			httpRequest.setEntity(entity);

			// AddHeader
			DefaultHttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, Globals.NETWORK_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Globals.NETWORK_READ_TIMEOUT);
			client.setParams(params);

			// Get HTTPResponse
			HttpResponse response = client.execute(httpRequest);

			// Get return String
			result_string = EntityUtils.toString(response.getEntity());
			result_byte = FileUtils.convertStringToByteArray(result_string);
		} catch (Exception e) {
		}
		return result_byte;
	}

	/*
	 * Edit Voucher #
	 */
	public static byte[] editVoucher(String username, String password, String voucherCode, String note, String totalSpend, String redeemAddress) {
		byte[] result_byte = null;
		String result_string = "";
		String apitoken = Globals.API_TOKEN;
		String url_editVoucher = Globals.URL_EDIT_VOUCHER_BASE;
		HttpPost httpRequest = new HttpPost(url_editVoucher);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("apiToken", apitoken));
		pairs.add(new BasicNameValuePair("sellerEmail", username.trim()));
		pairs.add(new BasicNameValuePair("sellerPassword", password.trim()));
		pairs.add(new BasicNameValuePair("voucherCode", voucherCode.trim()));
		pairs.add(new BasicNameValuePair("voucherNotes", note.trim()));
		pairs.add(new BasicNameValuePair("voucherTotalSpent", totalSpend.trim()));
		pairs.add(new BasicNameValuePair("voucherSellerAddressIDRedeemed", redeemAddress.trim()));

		try {

			// Set Character
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf8");

			// Request HttpRequest
			httpRequest.setEntity(entity);

			// AddHeader
			DefaultHttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, Globals.NETWORK_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Globals.NETWORK_READ_TIMEOUT);
			client.setParams(params);

			// Get HTTPResponse
			HttpResponse response = client.execute(httpRequest);

			// Get return String
			result_string = EntityUtils.toString(response.getEntity());
			result_byte = FileUtils.convertStringToByteArray(result_string);
		} catch (Exception e) {
		}
		return result_byte;
	}

	/*
	 * Get Quick Status
	 */
	public static String getQuickStats(String username, String password, String sellerId) {
		String result = "";
		String apitoken = Globals.API_TOKEN;

		// Get the Login Url
		String url_getQuickStats = Globals.URL_GET_QUICK_STATS;
		HttpPost httpRequest = new HttpPost(url_getQuickStats);

		// Use BasicNameValuePair to store POST data
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("apiToken", apitoken));
		pairs.add(new BasicNameValuePair("sellerEmail", username.trim()));
		pairs.add(new BasicNameValuePair("sellerPassword", password.trim()));
		pairs.add(new BasicNameValuePair("sellerId", sellerId.trim()));

		try {

			// Set Character
			HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf8");

			// Request HttpRequest
			httpRequest.setEntity(entity);

			// AddHeader
			DefaultHttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, Globals.NETWORK_CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, Globals.NETWORK_READ_TIMEOUT);
			client.setParams(params);

			// Get HTTPResponse
			HttpResponse response = client.execute(httpRequest);

			// Get return String
			result = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
		}
		return result;
	}

	/*
	 * Get Bitmap
	 */
	public static Bitmap returnBitMap(String url) {

		if (!url.equals(url_null)) {
			URL myFileUrl = null;
			Bitmap bitmap = null;
			try {
				myFileUrl = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		} else {
			Bitmap bitmap = BitmapFactory.decodeResource(null, R.drawable.icon);
			return bitmap;
		}
	}

}
