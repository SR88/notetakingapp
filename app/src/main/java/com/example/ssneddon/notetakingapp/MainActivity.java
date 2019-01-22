package com.example.ssneddon.notetakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.ssneddon.notetakingapp.adapters.CustomTermCursorAdapter;


public class MainActivity extends AppCompatActivity {


    private Cursor cursor;
    ListView listview_terms;
    private static final int REQUEST_CODE_DETAIL_TERM = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate");
        listview_terms = findViewById(R.id.listview_terms);

        buildTermList();

        // click-ables set
        listview_terms.setClickable(true);
        clickGetTermDetails();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (cursor != null){
            if (!cursor.isClosed()){
                cursor.close();
                Log.d("onStop", "Cursor closed");
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.v("onRestart", "Build term list");
        buildTermList();
    }

    public void buildTermList(){
        // Term information is loaded in here

        cursor = getContentResolver().query(
                DBProvider.CONTENT_URI_TERMS,
                DataBaseContract.Term.PROJECTION,
                null,
                null,
                null
        );
        CustomTermCursorAdapter customCursorAdapter = new CustomTermCursorAdapter(
                MainActivity.this,
                cursor,
                0
        );
        listview_terms.setAdapter(customCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.action_addTerm:
                Intent intent = new Intent(this, NewTerm.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickGetTermDetails(){
        listview_terms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailTermView.class);
                Uri uri = Uri.parse(DBProvider.CONTENT_URI_TERMS + "/" + id);
                intent.putExtra(DBProvider.CONTENT_TYPE_TERM, uri);
                startActivityForResult(intent, REQUEST_CODE_DETAIL_TERM);
            }
        });
    }

}
