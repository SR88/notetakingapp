package com.example.ssneddon.notetakingapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditorMentorActivity extends AppCompatActivity {

    EditText editText_MentorEditor_Name, editText_MentorEditor_Email, editText_MentorEditor_Phone;
    Button button_MentorEditor_Save;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mentor);
        setTitle("Edit mentor");

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(DBProvider.CONTENT_ITEM_TYPE); // todo incoming uri

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle("New mentor for course");
        }

        setupGUIElements();

        setupSaveButton();
    }

    private void setupSaveButton() {
        button_MentorEditor_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    // todo if incoming uri is not null update record
    private void insertMentor(){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.MENTOR_NAME, String.valueOf(editText_MentorEditor_Name.getText()));
        values.put(DBOpenHelper.MENTOR_PHONE, String.valueOf(editText_MentorEditor_Phone.getText()));
        values.put(DBOpenHelper.MENTOR_EMAIL, String.valueOf(editText_MentorEditor_Email.getText()));
        Uri mentorUri = getContentResolver().insert(DBProvider.CONTENT_URI_MENTORS, values);

        // pass back results and what row was inserted
        Intent intent = new Intent();
        intent.putExtra("row", mentorUri.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void validate(){
        ArrayList<String> errors = new ArrayList<>();
        if (editText_MentorEditor_Name.getText().length() < 1){
            errors.add("You must have a name for your mentor before saving it.");
        }
        if (editText_MentorEditor_Phone.getText().length() == 0 && editText_MentorEditor_Email.getText().length() == 0){
            errors.add("You must have a phone number or email address associated with a mentor.");
        }

        if (errors.size() > 0){
            createErrorDialog(errors);
        } else {
            // todo or update
            insertMentor();
        }
    }

    private void createErrorDialog(ArrayList<String> errors) {
        String errorsText = "";

        for (String s : errors){
            errorsText += s + "\n";
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditorMentorActivity.this);
        alertBuilder.setMessage(errorsText)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.setTitle("Error(s) detected!");
        alertDialog.show();
    }

    private void setupGUIElements(){
        editText_MentorEditor_Name = findViewById(R.id.editText_MentorEditor_Name);
        editText_MentorEditor_Email = findViewById(R.id.editText_MentorEditor_Email);
        editText_MentorEditor_Phone = findViewById(R.id.editText_MentorEditor_Phone);
        button_MentorEditor_Save = findViewById(R.id.button_MentorEditor_Save);
    }
}
