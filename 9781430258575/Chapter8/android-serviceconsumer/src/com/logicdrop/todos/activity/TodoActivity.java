package com.logicdrop.todos.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.logicdrop.todos.R;
import com.logicdrop.todos.ToDo;
import com.logicdrop.todos.data.ToDoProvider;
import com.logicdrop.todos.service.RestService;
import com.logicdrop.todos.service.WebHelper;

import java.util.ArrayList;

public class TodoActivity extends Activity {
    public static final String APP_TAG = "com.logicdrop.todos";

    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;
    private ArrayList<ToDo> mData;

    private final OnClickListener handleNewTaskEvent = new OnClickListener() {
        @Override
        public void onClick(final View view) {
        Log.d(APP_TAG, "Add task click received");

        String[] accounts = getAccountNames();

        ToDo newItem = new ToDo();
        newItem.setEmail(accounts[0]);
        newItem.setTitle(TodoActivity.this
                .etNewTask
                .getText().toString());


        Intent intent = new Intent(TodoActivity.this, RestService.class);
        intent.setAction(RestService.ADD_ACTION);
        intent.putExtra("todo", newItem);
        startService(intent);
        }
    };

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        this.setContentView(R.layout.main);

        this.taskView = (ListView) this.findViewById(R.id.tasklist);
        this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
        this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
        this.btNewTask.setOnClickListener(this.handleNewTaskEvent);

        this.renderToDos();
    }

    private void renderToDos() {
        if (mData == null) {
            if(WebHelper.isOnline(this)) {
            String[] accounts = getAccountNames();
            Intent intent = new Intent(TodoActivity.this, RestService.class);
            intent.setAction(RestService.LIST_ACTION);
            intent.putExtra("email", accounts[0]);
            startService(intent);
            }
            else {
                Toast.makeText(TodoActivity.this, "No Network Connectivity.", Toast.LENGTH_LONG).show();
            }
        } else {
            BindToDoList();
        }
    }

    /*
     * Unhook the BroadcastManager that is listening for service returns before rotation
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onNotice);
    }

    /*
     * Hookup the BroadcastManager to listen to service returns
     */
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(RestService.SERVICE_NAME);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, filter);

        //Check for records stored locally if service returned while activity was not in the foreground
        mData = findPersistedRecords();
        if(!mData.isEmpty()) {
            BindToDoList();
        }
    }

    /**
     * Find any objects in the database
     * @return An ArrayList of ToDo objects
     */
    protected ArrayList<ToDo> findPersistedRecords() {
        final ToDoProvider provider = new ToDoProvider(this);

        ArrayList<ToDo> result = provider.findAll();

        return result;
    }

    /**
     * Helper method to put the list of persons into the ListView
     */
    private void BindToDoList() {
        final ToDoAdapter adapter = new ToDoAdapter(TodoActivity.this, mData);
        taskView.setAdapter(adapter);

        this.taskView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            Log.d(TodoActivity.APP_TAG, String.format("item with id: %d and position: %d", id, position));

            final TextView v = (TextView) view;

            final long todoId = (Long) v.getTag();

            final Intent intent = new Intent(TodoActivity.this, RestService.class);
            intent.setAction(RestService.DELETE_ACTION);
            intent.putExtra("id", todoId);
            intent.putExtra("position", position);
            startService(intent);

            //Remove from the local database
            final ToDoProvider provider = new ToDoProvider(parent.getContext());
            provider.deleteTask(todoId);
            }
        });
    }

    /**
     * The listener that responds to intents sent back from the service
     */
    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
        Log.d(APP_TAG, "onNotice called");

        final int serviceResult = intent.getIntExtra("result", -1);
        final String action = intent.getStringExtra("function");

        if (serviceResult == RESULT_OK) {

            if (action.equalsIgnoreCase(RestService.LIST_ACTION)) {
                mData = intent.getParcelableArrayListExtra("data");
            } else if (action.equals(RestService.ADD_ACTION)) {

                final ToDo newItem = intent.getParcelableExtra("data");
                mData.add(newItem);
                etNewTask.setText("");

            } else if (action.equals(RestService.DELETE_ACTION)) {
                final int position = intent.getIntExtra("position", -1);
                if (position > -1) {
                    mData.remove(position);
                }
            }

            BindToDoList();

        } else {
            Toast.makeText(TodoActivity.this, "Rest call failed.", Toast.LENGTH_LONG).show();
        }
        }
    };

    /**
     * Get the list of account names from the device
     * @return An array of account names
     */
    private String[] getAccountNames() {
        try {
            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
            String[] names = new String[accounts.length];
            for (int i = 0; i < names.length; i++) {
                names[i] = accounts[i].name;
            }
            return names;
        } catch (Exception ex) {
            Log.d(APP_TAG, "Account error", ex);
            return null;
        }
    }
}
