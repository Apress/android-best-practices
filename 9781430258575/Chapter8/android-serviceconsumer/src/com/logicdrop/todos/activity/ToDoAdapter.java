package com.logicdrop.todos.activity;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.logicdrop.todos.ToDo;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private final Context mContext;
    private final List<ToDo> mValues;

    public ToDoAdapter(Context context, List<ToDo> values) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    /*
     * Creates the row in the ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Generate the row from a layout
        final View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        final TextView textView = (TextView) rowView.findViewById(R.id.text1);

        //Get the data
        final ToDo s = mValues.get(position);

        //Store the Id in the tag
        rowView.setTag(s.getId());

        //Place the data in the view
        textView.setText(s.getTitle());

        return rowView;
    }
}
