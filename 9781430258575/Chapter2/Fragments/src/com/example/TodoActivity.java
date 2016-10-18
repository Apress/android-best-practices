package com.example;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class TodoActivity extends FragmentActivity implements TaskFragment.OnTaskSelectedListener
{
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.main);

		// Check whether the activity is using the layout version with
		// the fragment_container FrameLayout. If so, we must add the first
		// fragment
		if (this.findViewById(R.id.fragment_container) != null)
		{
			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null)
			{
				return;
			}

			final TaskFragment taskFrag = new TaskFragment();

			// In case this activity was started with special instructions from
			// an Intent,
			// pass the Intent's extras to the fragment as arguments
			taskFrag.setArguments(this.getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, taskFrag).commit();
		}
	}

	/**
	 * User selected a task
	 */
	@Override
	public void onTaskSelected(final int position)
	{
		// Capture the title fragment from the activity layout
		final NoteFragment noteFrag = (NoteFragment) this.getSupportFragmentManager()
				.findFragmentById(R.id.note_fragment);

		if (noteFrag != null)
		{
			// If note frag is available, we're in two-pane layout...
			noteFrag.updateNoteView(position);
		}
		else
		{
			// If the frag is not available, we're in the one-pane layout
			// Create fragment and give it an argument for the selected task
			final NoteFragment swapFrag = new NoteFragment();
			final Bundle args = new Bundle();
			args.putInt(NoteFragment.ARG_POSITION, position);
			swapFrag.setArguments(args);
			final FragmentTransaction fragTx = this.getSupportFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view
			// and add the transaction to the back stack so the user can
			// navigate back
			fragTx.replace(R.id.fragment_container, swapFrag);
			fragTx.addToBackStack(null);

			// Commit the transaction
			fragTx.commit();
		}
	}
}
