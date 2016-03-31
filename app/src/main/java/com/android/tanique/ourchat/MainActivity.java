package com.android.tanique.ourchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Tanique on 8/3/2016.
 */
public class MainActivity extends Activity {

    private Databasehelper dbhelper = null;
    private Cursor c = null;
    private CourseListAdapter mAdapter = null;
    String user,pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            String u = getIntent().getStringExtra(user);
            String p = getIntent().getStringExtra(pass);
            setContentView(R.layout.content_course);


            ListView mlistView = (ListView) findViewById(R.id.listView);

            dbhelper = new Databasehelper(this);

            dbhelper.createDataBase();

            dbhelper.openDataBase();

            c = dbhelper.getCursor(u,p);
            startManagingCursor(c);

            //mAdapter = new Adapter(c);
            mlistView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.e("ERROR", "ERROR IN CODE" + e.toString());
            e.printStackTrace();
        }

    }


    class CourseListAdapter extends CursorAdapter {


        public CourseListAdapter(Cursor c){
            super(MainActivity.this,c);
        }


        @Override
        public View newView(Context context, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.courses_list_fragment, parent,false);
            Courseholder holder = new Courseholder(row);
            row.setTag(holder);
            return (row);
        }



        @Override
        public void bindView(View row, Context context, Cursor c) {
            Courseholder holder = (Courseholder) row.getTag();
            holder.populateFrom(dbhelper);

        }
    }

    class Courseholder{

        private TextView name = null;
        Courseholder(View row){
            name = (TextView)row.findViewById(R.id.no_list);
        }

        void populateFrom (Databasehelper r){
            String u = getIntent().getStringExtra(user);
            name.(r.getCC(u));
        }
    }
}
