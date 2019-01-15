package com.example.ssneddon.notetakingapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ssneddon.notetakingapp.R;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflator;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflator = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d("newView", "New View created");
        // R.layout.all_terms_single is the custom xml for each row
        return cursorInflator.inflate(R.layout.all_terms_single, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("bindView", "View is bound");
        TextView textTermName = view.findViewById(R.id.textTermName);
        TextView textTermStart = view.findViewById(R.id.textTermStart);
        TextView textTermEnd = view.findViewById(R.id.textTermEnd);

        textTermName.setText(cursor.getString(1));
        textTermStart.setText(cursor.getString(2));
        textTermEnd.setText(cursor.getString(3));
    }



}
