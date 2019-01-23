package com.example.ssneddon.notetakingapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DBProvider extends ContentProvider {

    // authority, is the symbolic name of the provider
    private static final String AUTHORTIY = "com.example.ssneddon.notetakingapp.dbprovider"; // todo change name later and in AndroidManifest.XML
    private static final String BASE_PATH = "school"; // entire dataset
    // path that points to a table or a file
    private static final String PATH_TERMS = DBOpenHelper.TABLE_TERM;
    private static final String PATH_MENTORS = DBOpenHelper.TABLE_MENTOR;
    private static final String PATH_COURSES = DBOpenHelper.TABLE_COURSE;
    private static final String PATH_NOTES = DBOpenHelper.TABLE_NOTES;
    private static final String PATH_NOTES_UNBOUND = "notes_unbound";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORTIY + "/" + BASE_PATH);

    // (authority + path) = identifies data in a provider
    public static final Uri CONTENT_URI_TERMS = Uri.parse("content://" + AUTHORTIY + "/" + PATH_TERMS);
    // used to indicate that we are updating or looking an existing term
    public static final String CONTENT_TYPE_TERM = "Term";



    public static final Uri CONTENT_URI_COURSES = Uri.parse("content://" + AUTHORTIY + "/" + PATH_COURSES);

    // for use in passing uri - see onCreate NoteEditorActivity
    public static final String CONTENT_ITEM_TYPE = "CourseNote";
    public static final Uri CONTENT_URI_NOTES = Uri.parse("content://" + AUTHORTIY + "/" + PATH_NOTES);
    public static final Uri CONTENT_URI_NOTES_UNBOUND = Uri.parse("content://" + AUTHORTIY + "/" + PATH_NOTES_UNBOUND);

    // Constant to identify requested operation
    private static final int ALL_RECORDS = 1;     // give me the data
    private static final int ONE_RECORD = 2;  // deals with only a single record
    private static final int TERMS = 3;
    // the optional id part points to an individual row in a table
    private static final int TERMS_ID = 4;
    private static final int MENTORS = 5;
    private static final int MENTORS_ID = 6;
    private static final int COURSES = 7;
    private static final int COURSES_ID = 8;
    private static final int NOTES = 9;
    private static final int NOTES_ID = 10;
    private static final int NOTES_LIST = 11;

    // parses uri pattern matches content uris using wildcards
    // * matches a string of any valid characters of any length
    // # matches string of numeric characters of any length
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH); // is a constant value of -1

    static {
        URI_MATCHER.addURI(AUTHORTIY, BASE_PATH, ALL_RECORDS);
        URI_MATCHER.addURI(AUTHORTIY, BASE_PATH + "/#" , ONE_RECORD);  // /# means looking for particular record
        URI_MATCHER.addURI(AUTHORTIY, PATH_TERMS, TERMS);
        URI_MATCHER.addURI(AUTHORTIY, PATH_TERMS + "/#", TERMS_ID);
        URI_MATCHER.addURI(AUTHORTIY, PATH_MENTORS, MENTORS);
        URI_MATCHER.addURI(AUTHORTIY, PATH_MENTORS + "/#", MENTORS_ID);
        URI_MATCHER.addURI(AUTHORTIY, PATH_COURSES, COURSES);
        URI_MATCHER.addURI(AUTHORTIY, PATH_COURSES + "/#", COURSES_ID);
        URI_MATCHER.addURI(AUTHORTIY, PATH_NOTES, NOTES);
        URI_MATCHER.addURI(AUTHORTIY, PATH_NOTES + "/#", NOTES_ID);
        URI_MATCHER.addURI(AUTHORTIY, PATH_NOTES + "/#", NOTES_LIST);
        URI_MATCHER.addURI(AUTHORTIY, PATH_NOTES_UNBOUND + "/#", NOTES_LIST);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }


    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        // Use sqlitequerybuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        boolean useAuthorityUri = false;

        // Record id
        String id;

        int uriType = URI_MATCHER.match(uri);
        switch(uriType){
            case TERMS:
                queryBuilder.setTables(DBOpenHelper.TABLE_TERM);
                break;
            case TERMS_ID:
                queryBuilder.setTables(DBOpenHelper.TABLE_TERM);
                selection = DBOpenHelper.KEY_TERM + "=" +uri.getLastPathSegment();
                break;

            case COURSES:
                queryBuilder.setTables(DBOpenHelper.TABLE_COURSE);
                break;
            case MENTORS:
                queryBuilder.setTables(DBOpenHelper.TABLE_MENTOR);
                break;
            case MENTORS_ID:
                queryBuilder.setTables(DBOpenHelper.TABLE_MENTOR);
                queryBuilder.appendWhere(DBOpenHelper.KEY_MENTOR + "=" +uri.getLastPathSegment());
                break;

            case NOTES:
                queryBuilder.setTables(DBOpenHelper.TABLE_NOTES);
                break;
            case NOTES_ID:
                queryBuilder.setTables(DBOpenHelper.TABLE_NOTES);
                queryBuilder.appendWhere(DBOpenHelper.KEY_NOTE + "=" + uri.getLastPathSegment());
                break;
            case NOTES_LIST:
                queryBuilder.setTables(DBOpenHelper.TABLE_NOTES);
                String[] args = {String.valueOf(uri.getLastPathSegment())};
                Cursor c = database.rawQuery(
                        "SELECT ALL notes WHERE _id IN (?)", args
                );
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;

            default:
                try {
                    throw new IllegalAccessException("Uknown URI: " + uri);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }

        Cursor c = queryBuilder.query(
                database,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    // what is actually stored in my provider
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        long rowId;
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)){
            case TERMS:
                rowId = db.insertOrThrow(DBOpenHelper.TABLE_TERM,null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(BASE_PATH + "/" + PATH_TERMS + "/" + rowId);

            case NOTES:
                rowId = db.insertOrThrow(DBOpenHelper.TABLE_NOTES, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(BASE_PATH + "/" + PATH_NOTES + "/" + rowId);
        }
        return null;
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int uriType = URI_MATCHER.match(uri);
        int deletionCount = 0;

        switch(uriType){
            case TERMS_ID:
                String id = uri.getLastPathSegment();
                return deletionCount = db.delete(
                        DataBaseContract.Term.TABLE_NAME,
                        selection,
                        selectionArgs
                );
        }

        return deletionCount;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }


}
