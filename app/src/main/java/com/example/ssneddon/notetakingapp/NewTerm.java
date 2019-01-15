package com.example.ssneddon.notetakingapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewTerm extends AppCompatActivity {

    Button btn_StartDate, btn_EndDate, btn_NewTermSave;
    int year_Start, month_Start, day_Start, year_End, month_End, day_End;
    EditText editText_NewTermName;
    static final int START_DILOG_ID = 0;
    static final int END_DILOG_ID = 1;
    TextView textView_EndDate, textView_StartDate;
    String stringDateStart, stringDateEnd;
    Date dateStart, dateEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        editText_NewTermName = findViewById(R.id.editText_NewTermName);
        textView_StartDate = findViewById(R.id.textView_StartDate);
        textView_EndDate = findViewById(R.id.textView_EndDate);
        btn_NewTermSave = findViewById(R.id.btn_NewTermSave);

        final Calendar cal = Calendar.getInstance();
        year_Start = cal.get(Calendar.YEAR);
        month_Start = cal.get(Calendar.MONTH);
        day_Start = cal.get(Calendar.DAY_OF_MONTH);
        year_End = cal.get(Calendar.YEAR);
        month_End = cal.get(Calendar.MONTH);
        day_End = cal.get(Calendar.DAY_OF_MONTH);


        startDateShowDialogOnClick();
        setupValidation();
    }

    // sets up both on click listeners for both start and end dates
    public void startDateShowDialogOnClick(){
        btn_StartDate = findViewById(R.id.btn_StartDate);
        btn_EndDate = findViewById(R.id.btn_EndDate);

        btn_StartDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(START_DILOG_ID);
                    }
                }
        );

        btn_EndDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(END_DILOG_ID);
                    }
                }
        );
    }

    public void setupValidation(){
        btn_NewTermSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validate();
                    }
                }
        );
    }


    // sets up date pickers for each button
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == START_DILOG_ID){
            return new DatePickerDialog(this, startDPListener, year_Start, month_Start, day_Start);
        }
        if (id == END_DILOG_ID){
            return new DatePickerDialog(this, endDPListener, year_End, month_End, day_End);
        }
            return null;
    }

    // sets the selected dates and text fields for start
    private DatePickerDialog.OnDateSetListener startDPListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_Start = year;
            month_Start = month + 1;
            day_Start = dayOfMonth;

            Toast.makeText(NewTerm.this, "Term Start: " + year_Start + "/" + month_Start + "/" + day_Start, Toast.LENGTH_SHORT).show();

            textView_StartDate.setText(year_Start + "/" + month_Start + "/" + day_Start);

            stringDateStart = (String) textView_StartDate.getText();
        }
    };

    // sets the selected dates and text fields for end
    private DatePickerDialog.OnDateSetListener endDPListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_End = year;
            month_End = month + 1;
            day_End = dayOfMonth;

            Toast.makeText(NewTerm.this, "Term End: " + year_End + "/" + month_End + "/" + day_End, Toast.LENGTH_SHORT).show();

            textView_EndDate.setText(year_End + "/" + month_End + "/" + day_End);

            stringDateEnd = (String) textView_EndDate.getText();
        }
    };

    public void validate(){
        ArrayList<String> errors = new ArrayList<>();

        // make sure term has a name
        if (editText_NewTermName.getText().length() < 1){
            errors.add("Your new term must have a name.");
        }

        // make sure date start not null
        if (stringDateStart == null){
            errors.add("You must have a start date for your term.");
        }

        // make sure date end not null
        if (stringDateEnd == null) {
            errors.add("You must have an end date for your term.");
        }

        // if both dates are not null check to make sure it is a logic period of time
        if (stringDateEnd != null && stringDateStart != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            try {
                dateStart = formatter.parse(stringDateStart);
                dateEnd = formatter.parse(stringDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // actual check to see if start is before end
            if (dateStart.after(dateEnd)){
                errors.add("Your term's start date cannot be after the end date.");
            }
        }


        // generate errors
        if (errors.size() > 0){
            createErrorDialog(errors);
        } else {
            saveNewTerm();
        }
    }

    private void saveNewTerm() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TERM_NAME, String.valueOf(editText_NewTermName.getText()));
        values.put(DBOpenHelper.TERM_START, dateStart.toString());
        values.put(DBOpenHelper.TERM_END, dateEnd.toString());
        getContentResolver().insert(DBProvider.CONTENT_URI_TERMS, values);

        finish();
        // todo maybe take user to course info
    }

    private void createErrorDialog(ArrayList<String> errors) {
        String errorsText = "";

        for (String s : errors){
            errorsText += s + "\n";
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewTerm.this);
        alertBuilder.setMessage(errorsText)
                .setCancelable(false)
                .setPositiveButton("I'm a dingus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setTitle("Error(s) detected!");
        alertDialog.show();
    }


}
