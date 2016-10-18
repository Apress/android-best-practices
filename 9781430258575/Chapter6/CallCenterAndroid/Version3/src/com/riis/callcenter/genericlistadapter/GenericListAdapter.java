package com.riis.callcenter.genericlistadapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GenericListAdapter extends BaseAdapter {
	ArrayList<GenericListItem> items;
	ArrayList<View.OnClickListener> itemClickListeners;
	
	GenericListItemViewFactory viewFactory;
	
	public GenericListAdapter(GenericListItemViewFactory viewFactory) {
		items = new ArrayList<GenericListItem>();
		itemClickListeners = new ArrayList<View.OnClickListener>();
		
		this.viewFactory = viewFactory;
	}
	
	public void addItem(GenericListItem item, View.OnClickListener itemClickListener) {
		items.add(item);
		itemClickListeners.add(itemClickListener);
		notifyDataSetChanged();
	}
	
	public void setDataSetWithClickListenerForAll(ArrayList<GenericListItem> newItems, View.OnClickListener itemClickListener) {
		items.clear();
		itemClickListeners.clear();
		
		items = newItems;
		
		for(int i = 0; i < items.size(); i++) {
			itemClickListeners.add(itemClickListener);
		}
		
		notifyDataSetChanged();
	}
	
	public void removeItem(int position) {
		if(positionIsValid(position)) {
			items.remove(position);
			itemClickListeners.remove(position);
			notifyDataSetChanged();
		}
	}
	
	private boolean positionIsValid(int position) {
		return (position >= 0 && position < items.size());
	}
	
	public void forceRefresh() {
		notifyDataSetChanged();
	}
	
	public GenericListItem getItemAssociateWithClickListener(View.OnClickListener itemClickListener) {
		int clickListenerIndex = itemClickListeners.indexOf(itemClickListener);
		
		if(clickListenerIndex >= 0) {
			return items.get(clickListenerIndex);
		} else {
			return null;
		}
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return viewFactory.generateListItemViewFromListItem(parent.getContext(), items.get(position), itemClickListeners.get(position));
	}
}
