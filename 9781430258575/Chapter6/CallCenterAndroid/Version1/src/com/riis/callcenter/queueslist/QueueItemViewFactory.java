package com.riis.callcenter.queueslist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.riis.callcenter.R;
import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.genericlistadapter.GenericListItemViewFactory;
import com.riis.callcenter.utils.TimeFormatter;

public class QueueItemViewFactory implements GenericListItemViewFactory {
	
	@Override
	public View generateListItemViewFromListItem(Context context, GenericListItem queueItem, OnClickListener clickListener) {
		LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View queueItemView = inflator.inflate(R.layout.queue_list_item, null);
		
		ImageView statusView = (ImageView) queueItemView.findViewById(R.id.activeIndicator);
		TextView nameView = (TextView) queueItemView.findViewById(R.id.queueTitle);
		TextView callsInQueueView = (TextView) queueItemView.findViewById(R.id.callsInQueueNumber);
		
		nameView.setText(((QueueListItem)queueItem).name);
		
		int queueStatusIndex = 0;
		
		int callsInQueue = ((QueueListItem)queueItem).callsInQueue;
		callsInQueueView.setText(Integer.toString(callsInQueue));
		if(callsInQueue > 2) {
			callsInQueueView.setTextColor(Color.RED);
			queueStatusIndex++;
		}
		
		if(queueStatusIndex == 1) {
			statusView.setImageResource(android.R.drawable.presence_away);
		} else if(queueStatusIndex == 2) {
			statusView.setImageResource(android.R.drawable.presence_busy);
		}
		
		queueItemView.setOnClickListener(clickListener);
		
		return queueItemView;
	}
}
