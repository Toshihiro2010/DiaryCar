package com.stecon.patipan_on.diarycar.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stecon.patipan_on.diarycar.common_class.MyAppConfig;
import com.stecon.patipan_on.diarycar.database.DatabaseStatusToServer;

/**
 * Created by patipan_on on 4/11/2018.
 */

public class StatusCheckServer {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private MyOnListener myOnListener = null;

    public StatusCheckServer(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.myDbHelper = new MyDbHelper(context);
        this.sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    public void checkInsert(){
        String strSql = "SELECT * FROM " +
                DatabaseStatusToServer.TABLE_NAME +
                " WHERE " +
                DatabaseStatusToServer.COL_STATUS + " = " + 0;
        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);

        String user = sharedPreferences.getString(MyAppConfig.employee_id, "");

        if (cursor.getCount() > 0) {

        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseStatusToServer.COL_STATUS, 0);
            contentValues.put(DatabaseStatusToServer.COL_CREATE_BY, user);
            contentValues.put(DatabaseStatusToServer.COL_UPDATE_BY, user);
            sqLiteDatabase.insert(DatabaseStatusToServer.TABLE_NAME, null, contentValues);
        }

        if (myOnListener != null) {
            myOnListener.onInsertListener();
        }
    }

    public void checkUpdate(){
        String strSql = "SELECT * FROM " +
                DatabaseStatusToServer.TABLE_NAME +
                " WHERE " +
                DatabaseStatusToServer.COL_STATUS + " = " + 2;
        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        String user = sharedPreferences.getString(MyAppConfig.employee_id, "");

        if (cursor.getCount() > 0) {

        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseStatusToServer.COL_STATUS, 2);
            contentValues.put(DatabaseStatusToServer.COL_CREATE_BY, user);
            contentValues.put(DatabaseStatusToServer.COL_UPDATE_BY, user);
            sqLiteDatabase.insert(DatabaseStatusToServer.TABLE_NAME, null, contentValues);
        }

        if (myOnListener != null) {
            myOnListener.onUpdateListener();
        }
    }

    public interface MyOnListener{
        public void onInsertListener();

        public void onUpdateListener();
    }

    public void setOnMyListener(MyOnListener onMySetListener) {
        myOnListener = onMySetListener;
    }
}
