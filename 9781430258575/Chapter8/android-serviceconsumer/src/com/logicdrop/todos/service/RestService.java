package com.logicdrop.todos.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logicdrop.todos.ToDo;
import com.logicdrop.todos.ToDoId;
import com.logicdrop.todos.activity.TodoActivity;
import com.logicdrop.todos.data.ToDoProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RestService extends IntentService {

    public static final String SERVICE_NAME ="REST-TODO";

    public static final String LIST_ACTION = "todo-list";
    public static final String ADD_ACTION = "todo-add";
    public static final String DELETE_ACTION = "todo-remove";

    public RestService() {
        super("RestService");
    }

    /**
     * Handles the intents sent to the service
     * @param intent The Intent to process
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (LIST_ACTION.equals(intent.getAction())) {
            final String email = intent.getStringExtra("email");
            listToDos(email);
        }
        else if (ADD_ACTION.equals(intent.getAction())) {
            final ToDo item = intent.getParcelableExtra("todo");
            addToDo(item);
        }
        else if (DELETE_ACTION.equals(intent.getAction())) {
            final long id = intent.getLongExtra("id", -1);
            final int position = intent.getIntExtra("position", -1);

            removeToDo(id, position);
        }
    }

    /**
     * List the ToDos stored in the web service for a user
     * @param email Email address of user
     */
    private void listToDos(String email) {
        Log.d(TodoActivity.APP_TAG, "Service - List ToDo called");

        final WebHelper http = new WebHelper();
        int result = -1;
        ArrayList<ToDo> results = null;
        final String url = "http://androidbestpractices.appspot.com/api/todo/list/" + email;
        try {
            final WebResult webResult = http.executeHTTP(url, "GET", null);
            if(webResult.getHttpCode() == 200) {
                //Convert the json string result to Java objects
                final Gson parser = new Gson();
                results = parser.fromJson(webResult.getHttpBody(), new TypeToken<ArrayList<ToDo>>(){}.getType());
                result = Activity.RESULT_OK;
            }
        } catch (IOException e) {
            Log.d(TodoActivity.APP_TAG, "Exception calling list service", e);
        }

        persistToDos(results);

        //Send the todos back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("result", result);
        sendBack.putExtra("function", LIST_ACTION);

        if(results != null){
            sendBack.putParcelableArrayListExtra("data", results);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);
    }

    /**
     * Save data to a local database
     * @param toDos A list of ToDos
     */
    private void persistToDos(List<ToDo> toDos) {
        Log.d(TodoActivity.APP_TAG, "Service - persistToDos called");

        final ToDoProvider provider = new ToDoProvider(this);

        provider.deleteAll();

        for(ToDo item : toDos) {
            provider.addTask(item.getId(), item.getTitle());
        }

    }

    /**
     * Add a single ToDo via the web service
     * @param item A ToDo object
     */
    private void addToDo(ToDo item) {
        Log.d(TodoActivity.APP_TAG, "Service - Add ToDo called");

        final WebHelper http = new WebHelper();
        int result = -1;
        ToDoId newId = null;

        final String url = "http://androidbestpractices.appspot.com/api/todo/";
        final Gson parser = new Gson();

        try {
            final String body = parser.toJson(item, ToDo.class);
            final WebResult webResult = http.executeHTTP(url, "PUT", body);
            if(webResult.getHttpCode() == 200) {
                result = Activity.RESULT_OK;

                //Convert the string result to Java objects

                newId = parser.fromJson(webResult.getHttpBody(), ToDoId.class);
                item.setId(newId.getId());
            }

        } catch (IOException e) {
            Log.d(TodoActivity.APP_TAG, "Exception calling add service", e);
        }

        //Send the result back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("function", ADD_ACTION);
        sendBack.putExtra("result", result);
        if(newId != null){
            sendBack.putExtra("data", item);
        }

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);
    }

    /**
     * Remove a ToDo via the web service
     * @param id The key of the item to remove
     * @param position The position of the item in the array to remove
     */
    private void removeToDo(long id, int position) {
        Log.d(TodoActivity.APP_TAG, "Service - Remove ToDo called");

        final WebHelper http = new WebHelper();
        int result = -1;
        final String url = "http://androidbestpractices.appspot.com/api/todo/" + id;
        try {
            final WebResult webResult = http.executeHTTP(url, "DELETE", null);
            if(webResult.getHttpCode() == 204) {
                result = Activity.RESULT_OK;
            }
        } catch (IOException e) {
            Log.d(TodoActivity.APP_TAG, "Exception calling delete service", e);
        }

        //Send the data back
        final Intent sendBack = new Intent(SERVICE_NAME);
        sendBack.putExtra("result", result);
        sendBack.putExtra("function", DELETE_ACTION);
        sendBack.putExtra("position", position);

        //Keep the intent local to the application
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBack);
    }
}
