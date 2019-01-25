package com.example.ssneddon.notetakingapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ssneddon.notetakingapp.R;

public class CustomMentorCursorAdapter extends CursorAdapter {


    TextView textView_Mentor_Name, textView_Mentor_Phone, textView_Mentor_Email;
    LayoutInflater cursorInflater;

    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public CustomMentorCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.all_mentors_single, parent, false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        textView_Mentor_Name = view.findViewById(R.id.textView_Mentor_Name);
        textView_Mentor_Phone = view.findViewById(R.id.textView_Mentor_Phone);
        textView_Mentor_Email = view.findViewById(R.id.textView_Mentor_Email);

        textView_Mentor_Name.setText(cursor.getString(1));
        textView_Mentor_Phone.setText(cursor.getString(2));
        textView_Mentor_Email.setText(cursor.getString(3));
    }
}
