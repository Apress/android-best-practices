package com.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteFragment extends Fragment
{
	final static String ARG_POSITION = "position";
	private int currentPosition = -1;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
	{
		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null)
		{
			this.currentPosition = savedInstanceState.getInt(NoteFragment.ARG_POSITION);
		}

		return inflater.inflate(R.layout.notes, container, false);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState)
	{
		super.onSaveInstanceState(outState);

		// Save the current selection in case we need to recreate
		outState.putInt(NoteFragment.ARG_POSITION, this.currentPosition);
	}

	@Override
	public void onStart()
	{
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been applied to the fragment at this point so we can safely call the
		// method below that sets the text.
		final Bundle args = this.getArguments();
		if (args != null)
		{
			// Set note based on argument passed in
			this.updateNoteView(args.getInt(NoteFragment.ARG_POSITION));
		}
		else if (this.currentPosition != -1)
		{
			// Set notes based on saved instance state from onCreateView
			this.updateNoteView(this.currentPosition);
		}
	}

	public void updateNoteView(final int position)
	{
		final TextView note = (TextView) this.getActivity().findViewById(R.id.note);
		note.setText(Content.Notes[position]);
		this.currentPosition = position;
	}
}
