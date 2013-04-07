/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.util;

import android.text.Html;
import android.text.Spanned;

public class HtmlUtils {

	/*
	 * Convert Html to String
	 */
	public static String convertHtmltoString(String html) {
		String result = "";

		if (html == null || html.equals("")) {
			result = "";

		} else {
			Spanned html_spanned = Html.fromHtml(html);
			result = html_spanned.toString();

		}
		return result;
	}

}
