package com.example.ssneddon.notetakingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ssneddon.notetakingapp.adapters.CustomCourseCursorAdapter;

public class DetailTermView extends AppCompatActivity {

    private String termName, termStart, termEnd, termId, termFilter, action;
    private TextView textView_DetailTerm_End, textView_DetailTerm_Start;
    Button btn_DetailTerm_DeleteTerm;
    ListView listView_TermDetails_NewCourse;
    Uri uri;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_term_view);

        btn_DetailTerm_DeleteTerm = findViewById(R.id.btn_DetailTerm_DeleteTerm);
        textView_DetailTerm_End = findViewById(R.id.textView_DetailTerm_End);
        textView_DetailTerm_Start = findViewById(R.id.textView_DetailTerm_Start);
        listView_TermDetails_NewCourse = findViewById(R.id.listView_TermDetails_NewCourse);

        Intent intent = getIntent();

        uri = intent.getParcelableExtra(DBProvider.CONTENT_TYPE_TERM);

        if (uri != null){
            action = Intent.ACTION_EDIT;
            termFilter = DBOpenHelper.KEY_TERM + "=" + uri.getLastPathSegment();

            Cursor c = getContentResolver().query(
                    uri,
                    DataBaseContract.Term.PROJECTION,
                    termFilter,
                    null,
                    null,
                    null
            );

            c.moveToFirst();

            termId = c.getString(c.getColumnIndex(DBOpenHelper.KEY_TERM));
            termName = c.getString(c.getColumnIndex(DBOpenHelper.TERM_NAME));
            termStart = c.getString(c.getColumnIndex(DBOpenHelper.TERM_START));
            termEnd = c.getString(c.getColumnIndex(DBOpenHelper.TERM_END));

            getSupportActionBar().setTitle("Term: " + termName);
            textView_DetailTerm_End.setText(termEnd);
            textView_DetailTerm_Start.setText(termStart);

            c.close();

            setupDeleteButton();

            buildCourseList();

            setupNewCourseFAB();


        }
    }

    private void setupDeleteButton() {
        btn_DetailTerm_DeleteTerm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validateNoAttachedTerms()){
                            int deletedRow = getContentResolver().delete(uri,  DataBaseContract.Term.KEY+ " =? ", new String[] {termId});
                            Intent intent = new Intent("deleteId", uri);
                            setResult(RESULT_OK, intent);

                            finish();
                        } else {
                            createErrorDialog();
                        }
                    }
                }
        );
    }

    private void setupNewCourseFAB(){
        FloatingActionButton fab_TermDetails_NewCourse = findViewById(R.id.fab_TermDetails_NewCourse);
        fab_TermDetails_NewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTermView.this, NewCourse.class);
                startActivity(intent);
            }
        });
    }

    private void createErrorDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DetailTermView.this);
        alertBuilder.setMessage("You cannot delete this term because it has courses attached to it.  Please delete all courses attached to this term before attempting to delete this term.")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setTitle("Deletion of term halted!");
        alertDialog.show();
    }

    private boolean validateNoAttachedTerms(){
        boolean noAttachedCourses = false;

        Cursor c = getContentResolver().query(DBProvider.CONTENT_URI_COURSES, DataBaseContract.Course.PROJECTION, DataBaseContract.Course.TERM_ID + " =? " , new String[] {termFilter}, null);
        if (c.getCount() <= 0){
            noAttachedCourses = true;
        }
        c.close();
        return noAttachedCourses;
    }

    private void buildCourseList(){
        cursor = getContentResolver().query(
                DBProvider.CONTENT_URI_COURSES,
                DataBaseContract.Course.PROJECTION,
                "term_id=?",
                new String[] {termId},
                null
        );

        CustomCourseCursorAdapter customCourseCursorAdapter = new CustomCourseCursorAdapter(
                DetailTermView.this,
                cursor,
                0
        );

        listView_TermDetails_NewCourse.setAdapter(customCourseCursorAdapter);
    }
}
