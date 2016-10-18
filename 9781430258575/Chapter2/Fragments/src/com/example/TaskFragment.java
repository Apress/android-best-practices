package com.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskFragment extends ListFragment
{
	public interface OnTaskSelectedListener
	{
		public void onTaskSelected(int position);
	}

	private OnTaskSelectedListener callback;

	@Override
	public void onAttach(final Activity activity)
	{
		super.onAttach(activity);
		this.callback = (OnTaskSelectedListener) activity;
	}

	@Override
	@SuppressLint("InlinedApi")
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// We need to use a different list item layout for devices older than
		// Honeycomb
		final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
				android.R.layout.simple_list_item_activated_1 :
				android.R.layout.simple_list_item_1;

		// Create an array adapter for the list view, using the content
		this.setListAdapter(new ArrayAdapter<String>(this.getActivity(), layout, Content.Tasks));
	}

	@Override
	public void onListItemClick(final ListView l, final View v, final int position, final long id)
	{
		// Notify the parent activity of selected item
		this.callback.onTaskSelected(position);

		// Set the item as checked to be highlighted when in two-pane layout
		this.getListView().setItemChecked(position, true);
	}

	@Override
	public void onStart()
	{
		super.onStart();

		// When in two-pane layout, set the listview to highlight the selected
		// list item
		if (this.getFragmentManager().findFragmentById(R.id.note_fragment) != null)
		{
			this.getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		}
	}
}
