package com.example.ssneddon.notetakingapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataBaseContract {


    // authority of db provider
    public static final String AUTHORITY =
            "com.example.ssneddon.notetakingapp.dbprovider";

    // content uri for top-level dbprovider authority
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // a selection clause for ID based queries
    public static final String SELECTION_ID_BASED = BaseColumns._ID + " = ? ";



    /*
        Define term table
     */
    public static final class Term implements BaseColumns {
        // Define table name
        public static final String TABLE_NAME = "term";

        // Define table column
        public static final String KEY = "_id";
        public static final String NAME = "term_Name" ;
        public static final String START = "term_Start" ;
        public static final String END = "term_End" ;

        // Define projection for term table
        public static final String[] PROJECTION = new String[]{
                Term.KEY,
                Term.NAME,
                Term.START,
                Term.END
        };
    } // End term table

    public static final class Mentor implements BaseColumns{
        // Define table name
        public static final String TABLE_NAME = "mentor";

        // Define table column
        public static final String KEY = "_id";
        public static final String NAME = "mentor_name" ;
        public static final String EMAIL = "mentor_email" ;
        public static final String PHONE = "mentor_phone" ;

        // Define projection for term table
        public static final String[] PROJECTION = new String[]{
                Mentor.KEY,
                Mentor.NAME,
                Mentor.EMAIL,
                Mentor.PHONE
        };
    } // End mentor table

    public static final class Course implements BaseColumns{
        public static final String TABLE_NAME = "course";

        // Course attributes
        public static final String KEY = "_id";
        public static final String NAME = "course_Name" ;
        public static final String START = "course_Start" ;
        public static final String END = "course_End" ;
        public static final String TERM_ID = "term_id";

        public static final String[] PROJECTION = new String[]{
                Course.KEY,
                Course.NAME,
                Course.START,
                Course.END,
                Course.TERM_ID
        };
    }

    public static final class Note implements BaseColumns{
        public static final String TABLE_NAME = "notes";

        public static final String KEY = "_id";
        public static final String NOTE = "notes_Note";
        public static final String COURSE_ID = "course_id";

        public static final String[] PROJECTION = new String []{
                Note.KEY,
                Note.NOTE,
                Note.COURSE_ID
        };
    }

}
