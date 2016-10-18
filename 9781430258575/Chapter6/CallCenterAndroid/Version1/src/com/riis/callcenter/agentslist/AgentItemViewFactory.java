package com.riis.callcenter.agentslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.riis.callcenter.R;
import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.genericlistadapter.GenericListItemViewFactory;

public class AgentItemViewFactory implements GenericListItemViewFactory {

	@Override
	public View generateListItemViewFromListItem(Context context, GenericListItem agentItem, OnClickListener clickListener) {
		LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View agentItemView = inflator.inflate(R.layout.agent_list_item, null);
		
		TextView nameView = (TextView) agentItemView.findViewById(R.id.agentName);
		Button statusButton = (Button) agentItemView.findViewById(R.id.agentStatusButton);
		
		nameView.setText(((AgentListItem)agentItem).name);
		
		Drawable statusIcon = context.getResources().getDrawable(((AgentListItem)agentItem).status.getDrawableId());
		statusIcon.setBounds(0, 0, statusIcon.getIntrinsicWidth(), statusIcon.getIntrinsicHeight());
		
		statusButton.setCompoundDrawables(statusIcon, null, null, null);
		statusButton.setText(((AgentListItem)agentItem).status.getStatusText());
		statusButton.setTextColor(((AgentListItem)agentItem).status.getTextColor());
		
		statusButton.setOnClickListener(clickListener);

		return agentItemView;
	}
}
