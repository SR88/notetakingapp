package com.example.ssneddon.notetakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditorNoteActivity extends AppCompatActivity {

    private String action;
    private EditText editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editor = findViewById(R.id.editText_Note);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(DBProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle("New Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    private void finishTyping(){
        String newText = editor.getText().toString().trim();

        switch (action) {
            case Intent.ACTION_INSERT:
                if (newText.length() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    insertNote(newText);
                }
        }

        finish();
    }

    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTES_NOTE, noteText);
        Uri noteUri = getContentResolver().insert(DBProvider.CONTENT_URI_NOTES, values);
        Log.d("EditorNoteActivity", "Inserted Note " + noteUri.getLastPathSegment());
        Log.d("EditorNoteActivity", "Inserted Note Uri = " + noteUri.toString());

        // pass back result and row that was inserted
        Intent intent = new Intent();
        intent.putExtra("row", noteUri.toString());
        setResult(RESULT_OK, intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_Note_Save:
                finishTyping();
                return true;

            case R.id.action_Note_Cancel:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
