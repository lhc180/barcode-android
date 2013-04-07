/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dealsmagazine.cache.DealsListViewCache;
import com.dealsmagazine.entity.Deal;

/*
 * ListView Deals Adapter
 */
public class DealsListArrayAdapter extends ArrayAdapter<Deal> {

	private final Activity context;
	private final ArrayList<Deal> deals;
	private int resourceId;

	String _dealOpen = new String("Open");
	String _dealApproved = new String("Approved");

	/*
	 * Constructor
	 * 
	 * @param context - the application content
	 * 
	 * @param resourceId - the ID of the resource/view
	 * 
	 * @param deals - the bound ArrayList
	 */
	public DealsListArrayAdapter(Activity context, int resourceId, ArrayList<Deal> deals, ListView listView) {
		super(context, resourceId, deals);
		this.context = context;
		this.deals = deals;
		this.resourceId = resourceId;
	}

	/*
	 * Updates the view
	 * 
	 * @param position - the ArrayList position to update
	 * 
	 * @param convertView - the view to update/inflate if needed
	 * 
	 * @param parent - the groups parent view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		DealsListViewCache dealsListViewCache;

		if (rowView == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = vi.inflate(resourceId, null);
			dealsListViewCache = new DealsListViewCache(rowView);
			rowView.setTag(dealsListViewCache);

		} else {
			dealsListViewCache = (DealsListViewCache) rowView.getTag();
		}

		Deal deal = deals.get(position);

		TextView statusText = dealsListViewCache.getStatusTextView();

		if (deal.status.equals(_dealOpen)) {
			statusText.setText("OPEN");
			statusText.setTextColor(0xff2FA5D0); // blue

		} else if (deal.status.equals(_dealApproved)) {
			statusText.setText("APPROVED");
			statusText.setTextColor(0xff5ABA21); // Green
		} else {
			statusText.setText("CLOSED");
			statusText.setTextColor(0xffFA1919); // Red
		}

		TextView marketText = dealsListViewCache.getMarketTextView();
		marketText.setText(deal.market);

		TextView headlineText = dealsListViewCache.getDealHeadlineTextView();
		headlineText.setText(deal.headline);

		return rowView;
	}

}
