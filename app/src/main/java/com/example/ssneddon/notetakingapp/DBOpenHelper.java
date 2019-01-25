package com.example.ssneddon.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ssneddon.notetakingapp.entity.Mentor;
import com.example.ssneddon.notetakingapp.entity.Term;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.example.ssneddon.notetakingapp.Consts.*;

public class DBOpenHelper extends SQLiteOpenHelper {

    Consts c;

    // Constant for DB name and version
    public static final String DATABASE_NAME = "school.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_TERM = DataBaseContract.Term.TABLE_NAME;
    public static final String TABLE_COURSE = "course";
    public static final String TABLE_STATUS = "status";
    public static final String TABLE_COURSE_STATUS = "course_status";
    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_MENTOR = DataBaseContract.Mentor.TABLE_NAME;
    public static final String TABLE_COURSE_MENTOR = "course_mentor";
    public static final String TABLE_EXAM = "exam";
    public static final String TABLE_EXAMTYPE = "examtype";
    public static final String TABLE_NOTIFIACTION = "notification";
    public static final String TABLE_NOTIFICATIONTYPE = "notificationtype";

    // Keys for tables
    public static final String KEY_EXAMTYPE = "_id";
    public static final String KEY_NOTIFICATION = "_id";
    public static final String KEY_NOTIFICATIONTYPE = "_id";
    public static final String KEY_EXAM = "_id";
    public static final String KEY_MENTOR = DataBaseContract.Mentor.KEY;
    public static final String KEY_NOTE = "_id";
    public static final String KEY_STATUS = "_id";
    public static final String KEY_COURSE_STATUS = "_id";
    public static final String KEY_COURSE = "_id";
    public static final String KEY_COURSE_MENTOR = "_id";
    public static final String KEY_TERM = DataBaseContract.Term.KEY;

    // Term attributes
    public static final String TERM_NAME = "term_Name" ;
    public static final String TERM_START = "term_Start" ;
    public static final String TERM_END = "term_End" ;

    // Course attributes
    public static final String COURSE_NAME = DataBaseContract.Course.NAME ;
    public static final String COURSE_START = DataBaseContract.Course.START ;
    public static final String COURSE_END = DataBaseContract.Course.END ;

    // Status attributes
    public static final String STATUS_STATUS = "status_Status" ;

    // Notes attributes
    public static final String NOTES_NOTE = "notes_Note" ;

    // Mentor attributes
    public static final String MENTOR_NAME = DataBaseContract.Mentor.NAME;
    public static final String MENTOR_EMAIL = DataBaseContract.Mentor.EMAIL;
    public static final String MENTOR_PHONE = DataBaseContract.Mentor.PHONE;

    // Exam attributes
    public static final String EXAM_CODE = "exam_Code" ;
    public static final String EXAM_DUE_DATE = "exam_Due_Date" ;

    // ExamType attributes
    public static final String EXAMTYPE_TYPE = "examType_Type" ;

    // Notification attirbutes
    public static final String NOTIFICATION_DATE = "notification_Date" ;

    // NotificationType attributes
    public static final String NOTIFICATIONTYPE_TYPE = "notificationType_Type" ;
    public static final String NOTIFICATIONTYPE_MESSAGE = "notificationType_Message" ;


    private static final String createTableTerm =
            CMD_CREATE_TABLE_INE + TABLE_TERM + LBR
            + KEY_TERM + TYPE_PK_IA + COMA
            + TERM_NAME + TYPE_TEXT + COMA
            + TERM_START + TYPE_TEXT_DCTS + COMA
            + TERM_END + TYPE_TEXT_DCTS
            + RBR + SEMICOL;


    private static final String createTableMentor =
            CMD_CREATE_TABLE_INE + TABLE_MENTOR + LBR
            + KEY_MENTOR + TYPE_PK_IA + COMA
            + MENTOR_NAME + TYPE_TEXT + COMA
            + MENTOR_EMAIL + TYPE_TEXT + COMA
            + MENTOR_PHONE + TYPE_TEXT
            + RBR + SEMICOL;

    private static final String createCourseTable = "CREATE TABLE " + TABLE_COURSE + " ("
            + KEY_COURSE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COURSE_NAME + " TEXT, "
            + COURSE_START + " TEXT DEFAULT CURRENT_TIMESTAMP, "
            + COURSE_END + " TEXT default CURRENT_TIMESTAMP, "
            + TABLE_TERM+ KEY_TERM + " INTEGER, "
            + DataBaseContract.Course.NOTE_ID + TYPE_INT + COMA
            + DataBaseContract.Course.MENTOR_ID + TYPE_INT + COMA
            + DataBaseContract.Course.EXAM_ID + TYPE_INT + COMA
            + " FOREIGN KEY("+ TABLE_TERM+KEY_TERM + ") REFERENCES " + TABLE_TERM + "(_id)"
            + FK + LBR + DataBaseContract.Course.NOTE_ID + RBR + REFS + TABLE_NOTES + "(_id)"
            + FK + LBR + DataBaseContract.Course.MENTOR_ID + RBR + REFS + TABLE_MENTOR + "(_id)"
            + FK + LBR + DataBaseContract.Course.EXAM_ID + RBR + REFS + TABLE_EXAM + "(_id)"
            + ")";

    private static final String createExamTable = "CREATE TABLE " + TABLE_EXAM + " ("
            + KEY_EXAM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXAM_CODE + " TEXT, "
            + EXAM_DUE_DATE + " TEXT default CURRENT_TIMESTAMP)";

    private static final String createNotesTable = "CREATE TABLE " + TABLE_NOTES + " ("
            + KEY_NOTE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTES_NOTE + " TEXT"
            + ")";



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create all tables
        db.execSQL(createTableTerm);
        db.execSQL(createTableMentor);
        db.execSQL(createCourseTable);
//        db.execSQL(createExamTypeTable);
        db.execSQL(createExamTable);
//        db.execSQL(createStatusTable);
        db.execSQL(createNotesTable);
//        db.execSQL(createCourseStatusTable);
//        db.execSQL(createCourseMentorTable);
//        db.execSQL(createNotificationTypeTable);
//        db.execSQL(createNotificationTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop all tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_STATUS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_MENTOR);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONTYPE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFIACTION);

        // create new tables after dropping old
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); // ensure that foreign key constraints are always on
    }


    /*
        General usage methods
     */

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        );

        Date date = new Date();

        return dateFormat.format(date);
    }

    /*
        Term methods
     */

    public long createTerm(Term term){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_NAME, term.getTermName());
        values.put(TERM_START, term.getDateStart());
        values.put(TERM_END, term.getDateEnd());

        // insert row
        long termId = db.insert(TABLE_TERM, null, values);

        return termId;
    }

    public Term getTermById(long termId){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TERM + " WHERE " + KEY_TERM + " = " + termId;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Term term = new Term();
        term.setKey(c.getColumnIndex(KEY_TERM));
        term.setTermName(c.getString(c.getColumnIndex(TERM_NAME)));
        term.setDateStart(c.getString(c.getColumnIndex(TERM_START)));
        term.setDateEnd(c.getString(c.getColumnIndex(TERM_END)));

        return term; // can return null
    }

    public List<Term> getAllTerms(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Term> terms = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TERM;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            do{
                Term term = new Term();
                term.setKey(c.getColumnIndex(KEY_TERM));
                term.setTermName(c.getString(c.getColumnIndex(TERM_NAME)));
                term.setDateStart(c.getString(c.getColumnIndex(TERM_START)));
                term.setDateEnd(c.getString(c.getColumnIndex(TERM_END)));

                terms.add(term);
            } while (c.moveToNext());
        }

        return terms;
    }

    public int updateTerm(Term term){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_NAME, term.getTermName());
        values.put(TERM_START, term.getDateStart());
        values.put(TERM_END, term.getDateEnd());

        return db.update(TABLE_TERM, values, KEY_TERM + "=?", new String[]{String.valueOf(term.getKey())});
    }

    public void deleteTerm(long termId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERM, KEY_TERM + " = ?", new String[]{(String.valueOf(termId))});
    }

    /*
        Mentor Methods
     */

    public void deleteMentor(long mentorId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTOR, KEY_MENTOR + " = ?", new String[]{(String.valueOf(mentorId))});
    }

    public int updateMentor(Mentor mentor){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MENTOR_NAME, mentor.getName());
        values.put(MENTOR_EMAIL, mentor.getEmail());
        values.put(MENTOR_PHONE, mentor.getPhone());

        return db.update(TABLE_MENTOR, values, KEY_MENTOR + "=?", new String[]{String.valueOf(mentor.getKey())});
    }

    public List<Mentor> getAllMentors(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Mentor> mentors = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MENTOR;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            do{
                Mentor mentor = new Mentor();
                mentor.setKey(c.getColumnIndex(KEY_MENTOR));
                mentor.setName(c.getString(c.getColumnIndex(MENTOR_NAME)));
                mentor.setPhone(c.getString(c.getColumnIndex(MENTOR_PHONE)));
                mentor.setEmail(c.getString(c.getColumnIndex(MENTOR_EMAIL)));

                mentors.add(mentor);
            } while (c.moveToNext());
        }

        return mentors;
    }

    public long createMentor(Mentor mentor){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MENTOR_NAME, mentor.getName());
        values.put(MENTOR_EMAIL, mentor.getEmail());
        values.put(MENTOR_PHONE, mentor.getPhone());

        // insert row
        long mentorId = db.insert(TABLE_MENTOR, null, values);

        return mentorId;
    }

    public Mentor getMentorById(long mentorId){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MENTOR + " WHERE " + KEY_MENTOR + " = " + mentorId;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }

        Mentor mentor = new Mentor();
        mentor.setKey(c.getColumnIndex(KEY_MENTOR));
        mentor.setName(c.getString(c.getColumnIndex(MENTOR_NAME)));
        mentor.setEmail(c.getString(c.getColumnIndex(MENTOR_EMAIL)));
        mentor.setPhone(c.getString(c.getColumnIndex(MENTOR_PHONE)));

        return mentor;
    }
}
