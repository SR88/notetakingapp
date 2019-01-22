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

public class CustomCourseCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public CustomCourseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
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
        return cursorInflater.inflate(R.layout.activity_detail_term_view, parent, false);
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
        Log.d("bindView", "Bound view to term details, courses");
        TextView textView_TermDetail_CourseName = view.findViewById(R.id.textView_TermDetail_CourseName);
        TextView textView_TermDetail_CourseStart = view.findViewById(R.id.textView_TermDetail_CourseStart);
        TextView textView_TermDetail_CourseFinish = view.findViewById(R.id.textView_TermDetail_CourseFinish);

        textView_TermDetail_CourseName.setText(cursor.getString(1));
        textView_TermDetail_CourseStart.setText(cursor.getString(2));
        textView_TermDetail_CourseFinish.setText(cursor.getString(3));
    }
}
