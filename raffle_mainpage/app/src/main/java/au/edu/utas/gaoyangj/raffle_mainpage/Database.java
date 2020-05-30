package au.edu.utas.gaoyangj.raffle_mainpage;

/**
 * Created by dahoo on 02-May-20.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.PropertyPermission;

public class Database
{
    //This is the tag that is used for the Logcat output (useful for filtering output)
    private static final String TAG = "RaffleDatabase";

    //The name of the database
    private static final String DATABASE_NAME = "RaffleDB";

    //The version of the database. Increment this whenever you change the /structure/ of the database
    private static final int   DATABASE_VERSION   = 2;

    //The connection to the database itself
    private SQLiteDatabase mDb;

    //The helper which we will use to open a connection to the database
    private DatabaseHelper mDbHelper;

    //The application context in which this code is run
    private final Context mCtx;

    // Constructor
    public Database(Context ctx) { this.mCtx = ctx; }

    public SQLiteDatabase open()
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return mDb;
    }

    /**
     * DatabaseHelper class.
     *
     * Database helper class to manage connections with the database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        //This constructor creates the database.
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //This code is called only the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // Note the use of super, which calls the constructor of the SQLiteOpenHelper class,
            // which does the actual work of opening the database.
            Log.d(TAG, "DatabaseHelper onCreate");

            //--- ADD YOUR CREATE TABLE FUNCTIONS HERE ---
            db.execSQL(RaffleTable.CREATE_STATEMENT);
            db.execSQL(users_table.CREATE_STATEMENT);

        }

        //This code is called if the version number changes
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.d(TAG, "DatabaseHelper onUpgrade");

            //--- ADD YOUR DROP TABLE FUNCTIONS HERE (migration logic not required for this unit)---
            db.execSQL("DROP TABLE IF EXISTS " + RaffleTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + users_table.TABLE_NAME);
            onCreate(db);
        }
    }

}
