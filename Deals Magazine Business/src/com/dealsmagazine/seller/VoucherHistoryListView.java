/*
 * 
 * � 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.seller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.dealsmagazine.adapter.VoucherHistoryArrayAdapter;
import com.dealsmagazine.entity.VoucherHistory;
import com.dealsmagazine.globals.Globals;
import com.dealsmagazine.util.FileUtils;
import com.dealsmagazine.seller.R;
/*
 * Copyright (C) 2011  | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */


public class VoucherHistoryListView extends ListActivity {

	final int MENU_CLEAR = 1;
	final ArrayList<VoucherHistory> voucherhistorys = new ArrayList<VoucherHistory>();

	private int count = 0;
	private VoucherHistoryArrayAdapter voucherHistoryArrayAdapter;
	private ListView lv_voucherhistory;
	private ImageButton imgbtn_voucher_history_list_back;
	private ImageButton imgbtn_voucher_history_list_refresh;
	private AlertDialog.Builder ad_clear_history_confirm;
	private TextView tv_voucher_record_count;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voucher_history_list_view);
		
		lv_voucherhistory = (ListView) findViewById(android.R.id.list);
		tv_voucher_record_count = (TextView) this.findViewById(R.id.tv_voucher_record_count);
		tv_voucher_record_count.setVisibility(View.GONE);
		imgbtn_voucher_history_list_back = (ImageButton) this.findViewById(R.id.imgbtn_voucher_history_list_back);
		imgbtn_voucher_history_list_refresh = (ImageButton) this.findViewById(R.id.imgbtn_voucher_history_list_refresh);

		imgbtn_voucher_history_list_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent ia = new Intent(VoucherHistoryListView.this, TabView.class);
				ia.putExtra(TabView.KEY_TAB_INDEX, 1);
				startActivityForResult(ia, Globals.INPUT_VOUCHER_VIEW);
				finish();
			}
		});

		imgbtn_voucher_history_list_refresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				voucherHistoryArrayAdapter.clear();
				refreshVoucherHistory();
				voucherHistoryArrayAdapter.notifyDataSetChanged();
			}
		});

		voucherHistoryArrayAdapter = new VoucherHistoryArrayAdapter(this, R.layout.voucherhistory_item, voucherhistorys, lv_voucherhistory);
		lv_voucherhistory.setAdapter(voucherHistoryArrayAdapter);

		refreshVoucherHistory();

		voucherHistoryArrayAdapter.notifyDataSetChanged();
		lv_voucherhistory.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
				VoucherHistory select_voucherHistory = (VoucherHistory) _av.getItemAtPosition(_index);
				showVoucherHistoryPage(select_voucherHistory);
			}
		});

		ad_clear_history_confirm = new AlertDialog.Builder(this);
		ad_clear_history_confirm.setTitle("Delete Confirm");
		ad_clear_history_confirm.setMessage("Delete all of the voucher redeem historys PERMANENTLY, are you sure you want to delete?");
		ad_clear_history_confirm.setCancelable(true);
		ad_clear_history_confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				deleteHistoryRecord();
				Intent i = new Intent(VoucherHistoryListView.this, VoucherHistoryListView.class);
				startActivityForResult(i, Globals.VOUCHER_HISTORY_VIEW);
				finish();
			}
		});

		ad_clear_history_confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

	}

	private void showVoucherHistoryPage(VoucherHistory voucherHistory) {
		Intent i = new Intent(this, VoucherHistoryPageView.class);
		i.putExtra(VoucherHistoryPageView.KEY_VOUCHER_HISTORY_PAGE_CODE, voucherHistory.getId());
		startActivityForResult(i, Globals.VOUCHER_HISTORY_PAGE_VIEW);
		finish();
	}

	private void deleteHistoryRecord() {
		FileUtils.deleteExternalStorageAll(this, "voucherhistory");
	}

	private void refreshVoucherHistory() {

		try {
			File dir = new File(this.getExternalFilesDir("voucherhistory").toString());
			File[] files = dir.listFiles();

			VoucherHistory voucherhistory;

			// Redeem Record Number
			count = files.length;

			if (count == 1) {
				tv_voucher_record_count.setText("YOU HAVE " + Integer.toString(count) + " RECORD");
				tv_voucher_record_count.setVisibility(View.VISIBLE);

			} else if (count > 1) {
				tv_voucher_record_count.setText("YOU HAVE " + Integer.toString(count) + " RECORDS");
				tv_voucher_record_count.setVisibility(View.VISIBLE);
			}

			for (File f : files) {
				String voucher_name = f.getName();
				Date lastModDate = new Date(f.lastModified());

				String voucher_date;

				try {
					voucher_date = df.format(lastModDate);
				} catch (Exception e) {
					voucher_date = lastModDate.toString();
				}

				voucherhistory = new VoucherHistory();
				voucherhistory.id = voucher_name;
				voucherhistory.date = voucher_date;
				voucherhistorys.add(voucherhistory);
			}

			VoucherHistoryListView.this.runOnUiThread(new Runnable() {
				public void run() {
					voucherHistoryArrayAdapter.notifyDataSetChanged();
				}
			});

		} catch (Exception e) {
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, MENU_CLEAR, 0, "Clear History").setIcon(android.R.drawable.ic_menu_delete);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case MENU_CLEAR:
			ad_clear_history_confirm.create().show();
			break;

		default:
			break;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		
			Intent intent = new Intent();
			intent.setClass(VoucherHistoryListView.this, TabView.class);
			intent.putExtra(TabView.KEY_TAB_INDEX, 1);
			startActivity(intent);
			finish();
		
	}
}
