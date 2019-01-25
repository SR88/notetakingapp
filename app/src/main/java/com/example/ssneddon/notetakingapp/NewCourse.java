package com.example.ssneddon.notetakingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ssneddon.notetakingapp.adapters.CustomMentorCursorAdapter;
import com.example.ssneddon.notetakingapp.adapters.CustomNoteCursorAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewCourse extends AppCompatActivity {

    static final int START_DILOG_ID = 0;
    static final int END_DILOG_ID = 1;
    private static final int EDITOR_REQUEST_CODE_NOTE = 1001;
    private static final int EDITOR_REQUEST_CODE_MENTOR = 1002;
    TextView textView_NewCourse_SetStartDate, textView_NewCourse_SetAntFinish;
    EditText editText_NewCourse_CourseName;
    String stringDateStart, stringDateEnd;
    Date dateStart, dateEnd;
    int year_Start, month_Start, day_Start, year_End, month_End, day_End;
    CheckBox checkBox_NewCourse_AlertDueDate;
    Spinner spinner_NewCourse_Status;
    Button btn_NewCourse_NewNote, btn_NewCourse_AddMentor;
    ArrayList<String> arrayListNotes;
    Cursor cursor;
    ListView listView_NewCourse_Notes, listView_NewCourse_Mentors;
    private SQLiteDatabase database;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        getSupportActionBar().setTitle("Create a new course");


        dbOpenHelper = new DBOpenHelper(this);
        database = dbOpenHelper.getWritableDatabase();

        listView_NewCourse_Mentors = findViewById(R.id.listView_NewCourse_Mentors);
        listView_NewCourse_Notes = findViewById(R.id.listView_NewCourse_Notes);
        textView_NewCourse_SetStartDate = findViewById(R.id.textView_NewCourse_SetStartDate);
        textView_NewCourse_SetAntFinish = findViewById(R.id.textView_NewCourse_SetAntFinish);

        arrayListNotes = new ArrayList<>();

        final Calendar cal = Calendar.getInstance();
        year_Start = cal.get(Calendar.YEAR);
        month_Start = cal.get(Calendar.MONTH);
        day_Start = cal.get(Calendar.DAY_OF_MONTH);
        year_End = cal.get(Calendar.YEAR);
        month_End = cal.get(Calendar.MONTH);
        day_End = cal.get(Calendar.DAY_OF_MONTH);

        showDialogDatePickerOnClick();
        setupSpinnerCourseStatus();
        setupNewNote();
        setupNewMentor();

        listView_NewCourse_Notes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });


    }


    private void showDialogDatePickerOnClick() {
        Button btn_NewCourse_SetStartDate = findViewById(R.id.btn_NewCourse_SetStartDate);
        Button btn_NewCourse_SetAntFinish = findViewById(R.id.btn_NewCourse_SetAntFinish);

        btn_NewCourse_SetStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DILOG_ID);
            }
        });

        btn_NewCourse_SetAntFinish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(END_DILOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == START_DILOG_ID) {
            return new DatePickerDialog(this, startDPListener, year_Start, month_Start, day_Start);
        }
        if (id == END_DILOG_ID) {
            return new DatePickerDialog(this, endDPListener, year_End, month_End, day_End);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener startDPListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_Start = year;
            month_Start = month + 1;
            day_Start = dayOfMonth;

            textView_NewCourse_SetStartDate.setText(month_Start + "/" + day_Start + "/" + year_Start);

            stringDateStart = (String) textView_NewCourse_SetStartDate.getText();
        }
    };

    private DatePickerDialog.OnDateSetListener endDPListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_End = year;
            month_End = month + 1;
            day_End = dayOfMonth;

            textView_NewCourse_SetAntFinish.setText(month_Start + "/" + day_Start + "/" + year_Start);

            stringDateEnd = (String) textView_NewCourse_SetAntFinish.getText();
        }
    };

    private void setupSpinnerCourseStatus() {
        spinner_NewCourse_Status = findViewById(R.id.spinner_NewCourse_Status);

        // Create adapter using string array and default spinner layout
        ArrayAdapter<CharSequence> arrayAdapterSpinner = ArrayAdapter.createFromResource(
                this,
                R.array.course_statuses,
                android.R.layout.simple_spinner_dropdown_item
        );

        // specify layout to use when the list of choice appears
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // apply adapter to spinner
        spinner_NewCourse_Status.setAdapter(arrayAdapterSpinner);
    }

    private void setupNewNote() {
        btn_NewCourse_NewNote = findViewById(R.id.btn_NewCourse_NewNote);
        btn_NewCourse_NewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCourse.this, EditorNoteActivity.class);
                startActivityForResult(intent, EDITOR_REQUEST_CODE_NOTE);
            }
        });
    }

    private void setupNewMentor() {
        btn_NewCourse_AddMentor = findViewById(R.id.btn_NewCourse_AddMentor);
        btn_NewCourse_AddMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCourse.this, EditorMentorActivity.class);
                startActivityForResult(intent, EDITOR_REQUEST_CODE_MENTOR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITOR_REQUEST_CODE_NOTE && resultCode == RESULT_OK) {
            String id = data.getStringExtra("row");
            arrayListNotes.add(id);
            buildNotesData();
        }
        if (requestCode == EDITOR_REQUEST_CODE_MENTOR && resultCode == RESULT_OK) {
            String id = data.getStringExtra("row");
            buildMentorData();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void buildMentorData() {
        // todo build mentor listview out of unassigned mentors
        // have to use raw query. unable to figure out how to do this subquery through content provider
        cursor = database.rawQuery(
                "SELECT * from mentor where mentor._id NOT IN (SELECT course.mentor_id from course)",
                null
        );

        CustomMentorCursorAdapter customMentorCursorAdapter = new CustomMentorCursorAdapter(
                NewCourse.this,
                cursor,
                0
        );

        listView_NewCourse_Mentors.setAdapter(customMentorCursorAdapter);
    }

    private void buildNotesData() {
        // have to use raw query. unable to figure out how to do this subquery through content provider
        cursor = database.rawQuery(
                "SELECT * from notes where notes._id NOT IN (SELECT course.note_id from course)",
                null
        );


        // todo change to query all notes that are do not have fk assigned course table


        CustomNoteCursorAdapter customNoteCursorAdapter = new CustomNoteCursorAdapter(
                NewCourse.this,
                cursor,
                0
        );

        listView_NewCourse_Notes.setAdapter(customNoteCursorAdapter);
        // todo on save course save fk course.note_id
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        buildNotesData();
    }


    // todo on cancel delete all notes and mentors without a fk

}
