/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

import android.app.Application;
import android.content.SharedPreferences;

public class User extends Application {

	public static final String KEY_CURRENT_USER = "CURRENT_USER";
	public static final String KEY_BUSINESS_NAME = "BUSINESS_NAME";
	public static final String KEY_BUSINESS_LIST = "BUSINESS_LIST";
	public static final String KEY_SELLER_ID = "SELLER_ID";
	public static final String KEY_REDEEM_ADDRESS = "REDEEM_ADDRESS";

	private String userId = "";
	private String sellerId = "";
	private String username = "";
	private String password = "";
	private String businessName = "";
	private int businessPosition = 0;
	private int redeem_address = 0;

	public void setuserId(String userId) {
		this.userId = userId.trim();
	}

	public void setusername(String username) {
		this.username = username.trim();
	}

	public void setpassword(String password) {
		this.password = password.trim();
	}

	public void setbusinessName(String businessName) {
		this.businessName = businessName.trim();
	}

	public void setbusinessPosition(int businessPosition) {
		this.businessPosition = businessPosition;
	}

	public void setredeemAddress(int redeem_address) {
		this.redeem_address = redeem_address;
	}

	public void setsellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getuserId() {
		return userId;
	}

	public String getusername() {
		return username;
	}

	public String getpassword() {
		return password;
	}

	public String getbusinessName() {
		return businessName;
	}

	public int getbusinessPosition() {
		return businessPosition;
	}

	public int getredeemAddress() {
		return redeem_address;
	}

	public String getsellerId() {
		return sellerId;
	}

	public void logout() {
		userId = "";
		sellerId = "";
		username = "";
		password = "";
		businessName = "";
		businessPosition = 0;
		redeem_address = 0;
		saveToPreferences();
		saveBusinessNameToPreferences();
		saveBusinessPositionToPreferences();
		saveredeemAddressToPreferences();
		saveSellerIdToPreferences();
		
	}

	public String loaduserIdFromPreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_CURRENT_USER, 0);
		userId = getting.getString("userId", "");
		return userId;
	}

	public String loadusernameFrompreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_CURRENT_USER, 0);
		username = getting.getString("username", "");
		return username;
	}

	public String loadpasswordFrompreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_CURRENT_USER, 0);
		password = getting.getString("password", "");
		return password;
	}

	public String loadbusinessNameFrompreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_BUSINESS_NAME, 0);
		businessName = getting.getString("businessName", "");
		return businessName;
	}

	public int loadbusinessPositonFromPreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_BUSINESS_LIST, 0);
		businessPosition = getting.getInt("businessPosition", 0);
		return businessPosition;
	}

	public int loadredeemAddressFromPreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_REDEEM_ADDRESS, 0);
		redeem_address = getting.getInt("redeemAddress", 0);
		return redeem_address;
	}

	public String loadSellerIdFromPreferences() {
		SharedPreferences getting = getSharedPreferences(KEY_SELLER_ID, 0);
		sellerId = getting.getString("sellerId", "");
		return sellerId;
	}

	public void saveToPreferences() {
		SharedPreferences setting = getSharedPreferences(KEY_CURRENT_USER, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString("userId", userId);
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	public void saveBusinessNameToPreferences() {
		SharedPreferences setting = getSharedPreferences(KEY_BUSINESS_NAME, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString("businessName", businessName);
		editor.commit();

	}

	public void saveBusinessPositionToPreferences() {
		SharedPreferences setting = getSharedPreferences(KEY_BUSINESS_LIST, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putInt("businessPosition", businessPosition);
		editor.commit();
	}

	public void saveredeemAddressToPreferences() {
		SharedPreferences setting = getSharedPreferences(KEY_REDEEM_ADDRESS, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putInt("redeemAddress", redeem_address);
		editor.commit();
	}

	public void saveSellerIdToPreferences() {
		SharedPreferences setting = getSharedPreferences(KEY_SELLER_ID, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString("sellerId", sellerId);
		editor.commit();
	}

}
