package com.example.ssneddon.notetakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.ssneddon.notetakingapp.adapters.CustomCursorAdapter;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    DBOpenHelper dbOpenHelper;
    ListView listview_terms;
//    CustomCursorAdapter customCursorAdapter;
    private static final int REQUEST_CODE_DETAIL_TERM = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Term information is loaded in here
        dbOpenHelper = new DBOpenHelper(this);
        listview_terms = findViewById(R.id.listview_terms);




        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(
                MainActivity.this,
                null,
                0);



        listview_terms.setAdapter(customCursorAdapter);




        // This refreshes the list of terms
        LoaderManager supportLoaderManager = getSupportLoaderManager();
        supportLoaderManager.initLoader(1, null, this);

        listview_terms.setClickable(true);
        clickGetTermDetails();

//        ContentValues values = new ContentValues();
//        values.put(DBOpenHelper.TERM_NAME, "First Term");
//        getContentResolver().insert(DBProvider.CONTENT_URI, values);
//        getContentResolver().insert(DBProvider.CONTENT_URI_TERMS,values);
//
//        Log.d("MainActivity", "Inserted Term");
//
//        Toast.makeText(MainActivity.this, dbOpenHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();

        dbOpenHelper.close();
        Toast.makeText(MainActivity.this, dbOpenHelper.getDatabaseName() + " closed.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume(){
        super.onResume();
        customCursorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRestart() {
        super.onRestart();

        Cursor c = getContentResolver().query(DBProvider.CONTENT_URI_TERMS, DataBaseContract.Term.PROJECTION, null, null, null);

        customCursorAdapter = new CustomCursorAdapter(MainActivity.this, c, 0);
        customCursorAdapter = (CustomCursorAdapter) listview_terms.getAdapter();
        customCursorAdapter.changeCursor(c);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_DETAIL_TERM == (resultCode == RESULT_OK) && (data != null))){
            int id = data.getIntExtra("deletedId", 0);

            customCursorAdapter.notifyDataSetChanged();

        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, DBProvider.CONTENT_URI_TERMS, DataBaseContract.Term.PROJECTION, null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        customCursorAdapter.notifyDataSetChanged();
        listview_terms.setAdapter(customCursorAdapter);
        customCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        customCursorAdapter.swapCursor(null);
    }
}
