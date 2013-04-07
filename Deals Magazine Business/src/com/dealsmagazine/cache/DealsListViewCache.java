/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.cache;

import com.dealsmagazine.seller.R;

import android.view.View;
import android.widget.TextView;

public class DealsListViewCache {

	private View v_baseview;
	private TextView tv_status;
	private TextView tv_market;
	private TextView tv_deal_headline;

	public DealsListViewCache(View v_baseview) {
		this.v_baseview = v_baseview;
	}

	public TextView getStatusTextView() {
		if (tv_status == null) {
			tv_status = (TextView) v_baseview.findViewById(R.id.rowtext_status);
		}
		return tv_status;
	}

	public TextView getDealHeadlineTextView() {
		if (tv_deal_headline == null) {
			tv_deal_headline = (TextView) v_baseview.findViewById(R.id.rowtext_deal_headline);
		}
		return tv_deal_headline;
	}

	public TextView getMarketTextView() {
		if (tv_market == null) {
			tv_market = (TextView) v_baseview.findViewById(R.id.rowtext_market);
		}
		return tv_market;
	}

}
