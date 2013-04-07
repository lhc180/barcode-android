/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.util;

import android.os.Environment;

public class ExternalStorageUtils {

	/*
	 * Helper Method to Test if external Storage is Available
	 */
	public static boolean isExternalStorageAvailable() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

	/*
	 * Helper Method to Test if external Storage is read only
	 */
	public static boolean isExternalStorageReadOnly() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

}
