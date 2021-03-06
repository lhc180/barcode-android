/*
 * 
 * � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.adapter;

import java.util.ArrayList;

import com.dealsmagazine.cache.VoucherHistoryViewCache;
import com.dealsmagazine.entity.VoucherHistory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*
 *  ListView VoucherHistory Adapter 
 */
public class VoucherHistoryArrayAdapter extends ArrayAdapter<VoucherHistory> {

	private final Activity context;
	private final ArrayList<VoucherHistory> voucherhistorys;
	private int resourceId;

	/*
	 * Constructor
	 * 
	 * @param context - the application content
	 * 
	 * @param resourceId - the ID of the resource/view
	 * 
	 * @param voucherhistorys - the bound ArrayList
	 */
	public VoucherHistoryArrayAdapter(Activity context, int resourceId, ArrayList<VoucherHistory> voucherhistorys, ListView listView) {
		super(context, resourceId, voucherhistorys);
		this.context = context;
		this.voucherhistorys = voucherhistorys;
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
		VoucherHistoryViewCache voucherHistoryViewCache;

		if (rowView == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = vi.inflate(resourceId, null);
			voucherHistoryViewCache = new VoucherHistoryViewCache(rowView);
			rowView.setTag(voucherHistoryViewCache);

		} else {
			voucherHistoryViewCache = (VoucherHistoryViewCache) rowView.getTag();
		}

		VoucherHistory voucherHistory = voucherhistorys.get(position);

		TextView idText = voucherHistoryViewCache.getVoucherHistoryIdTextView();
		idText.setText(voucherHistory.id);

		TextView statusText = voucherHistoryViewCache.getVoucherHistoryDateTextView();
		statusText.setText(voucherHistory.date);

		return rowView;
	}

}
