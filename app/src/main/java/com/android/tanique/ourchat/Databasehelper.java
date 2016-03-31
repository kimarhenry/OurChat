package com.android.tanique.ourchat;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by Tanique on 7/3/2016.
 */
public class Databasehelper extends SQLiteOpenHelper{
    Context context;
    private final String DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).toString();
    private static final String DATABASE_NAME = "Database.db";
    private static final int SCHEMA_VERSION= 1;
    private static final String TABLE_NAME = "STUDENT";
    private static final String COLUMN_ID = "_id";
    private static final String USER_CC = "COURSE_CODE";
    private static  final String USER_PASS = "PASS";
    SQLiteDatabase db;
    SQLiteDatabase q ;


    public SQLiteDatabase dbSqlite;
    private final Context mContext;

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean DBExists(){
        SQLiteDatabase db = null;

        try{
            String databasepath = DATABASE_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(databasepath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(1);
        }catch (SQLiteException e){
            Log.e("DatabaseHelper", "database not found");
        }
        if (db != null){
            db.close();
        }
        return db != null ? true: false;

    }

    private void copyDBFromResource (){
        InputStream IS ;
        OutputStream OS;
        String dbFilePath = DATABASE_PATH + DATABASE_NAME;

        try{
            IS = mContext.getAssets().open(DATABASE_NAME);
            OS = new FileOutputStream(dbFilePath);

            byte [] buffer = new  byte[1024];
            int length;
            while ((length = IS.read(buffer)) > 0){
                OS.write(buffer, 0,length);
            }
            OS.flush();
            OS.close();
            IS.close();
        } catch (IOException e){
            throw new Error("Problem copying database from resourse file.");
        }
    }

    private void createDB(){
        boolean dbExist = DBExists();

        if (!dbExist){
            this.getReadableDatabase();
            copyDBFromResource();
        }
    }

    public void createDataBase (){
        createDB();
    }

    public void openDataBase()throws SQLiteException{
        String mpath = DATABASE_PATH + DATABASE_NAME;
        dbSqlite = SQLiteDatabase.openDatabase(mpath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public synchronized void close(){
        if (dbSqlite != null){
            dbSqlite.close();
        }
        super.close();
    }

    public Cursor getCursor(String id, String pass){
       /* SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);


        String [] asColumnsToReturn = new String[] { USER_CC};

        Cursor mCursor = qb.query(dbSqlite, asColumnsToReturn, null, null, null, null, null);

        return mCursor;*/
        Cursor c = q.rawQuery("SELECT COURSE_CODE FROM STUDENT WHERE _id=? and PASS =?", new String[]{id,pass});
        return c;

    }

    public boolean Login(String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT _id, PASS FROM STUDENT WHERE _id=? AND PASS=?", new String[]{username, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    public Cursor getCC (String id){

        Cursor cursor = q.rawQuery("SELECT COURSE_CODE FROM STUDENT AS A JOIN STUDENT_COURSES AS B JOIN COURSE AS C\n" +
                "WHERE A._id = B._id AND B.COURSE_ID =  C.COURSE_ID AND B._id = ? ", new String[]{id});

        return cursor;
    }



}












