/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.util;

import java.io.File;

import android.content.Context;

public class VoucherHistoryPageUtils {

	/*
	 * Delete external private file
	 * 
	 * @param filename - the filename to delete
	 */
	public static void deleteExternalStoragePrivateFile(Context context, String filename) {
		File file = new File(context.getExternalFilesDir("voucherhistory"), filename);
		if (file != null) {
			file.delete();
		}
	}

}
