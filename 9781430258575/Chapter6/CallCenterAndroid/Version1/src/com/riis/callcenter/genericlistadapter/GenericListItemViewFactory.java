package com.riis.callcenter.genericlistadapter;

import android.content.Context;
import android.view.View;

public interface GenericListItemViewFactory {
	abstract public View generateListItemViewFromListItem(Context context, GenericListItem listItem, View.OnClickListener clickListener);
}
