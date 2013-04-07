/*
 * 
 * � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.globals;

public class Globals {

	/*
	 * Timer
	 */
	public static int SPLASH_TIME = 1000;
	public static int NETWORK_CONNECT_TIMEOUT = 60000;
	public static int NETWORK_READ_TIMEOUT = 60000;

	/*
	 * Seller Info Cache File
	 */
	public static String LOGIN_CACHE = "login_data";

	// STAGE API KEY
	// public static String API_TOKEN = "D2D745A8519C11E18BB270E64724019B";

	/*
	 * STAGE API
	 */
	public static String URL_LOGIN_BASE = "";
	public static String URL_GET_QUICK_STATS = "";
	public static String URL_GET_VOUCHER_BASE = "";
	public static String URL_EDIT_VOUCHER_BASE = "";
	public static String URL_REDEEM_VOUCHER_BASE = "";

	/*
	 * PRODUCTION API
	 */

	public static String API_TOKEN = "";

	

	/*
	 * startActivityForResult final
	 */
	public final static int WHERE_YOU_SHOP_BUSINESS_ACTIVITY = 1;
	public final static int DEALS_LIST_VIEW = 2;
	public final static int INPUT_VOUCHER_VIEW = 3;
	public final static int LOGIN_VIEW = 4;
	public final static int LOGOUT_VIEW = 5;
	public final static int NETWORK_CONNECTION_VIEW = 6;
	public final static int SELLER_INFO_VIEW = 7;
	public final static int SPLASH_SCREEN = 8;
	public final static int VOUCHER_EDIT_VIEW = 9;
	public final static int VOUCHER_HISTORY_VIEW = 10;
	public final static int VOUCHER_INFO_VIEW = 11;
	public final static int CAPTURE_ACTIVITY = 12;
	public final static int CONTACT_US_VIEW = 13;
	public final static int APP_PREFERENCE_ACTIVITY = 14;
	public final static int ABOUT_VIEW = 15;
	public final static int VOUCHER_HISTORY_PAGE_VIEW = 16;

}