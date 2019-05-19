package com.example.examinapp.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.examinapp.models.UserModel;

public class ExamInAppDBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CSB_EXAMINAPP";

    // Alarms table name
    private static final String TABLE_USER = "Users";

    // Alarms Table Columns names
    private static final String TABLE_USER_COLUMN_ID = "Id";
    private static final String TABLE_USER_COLUMN_NAME = "Name";
    private static final String TABLE_USER_COLUMN_USERNAME = "Username";
    private static final String TABLE_USER_COLUMN_SESSIONID = "SessionId";
    private static final String TABLE_USER_COLUMN_USERTYPE = "UserType";

    public ExamInAppDBHandler(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE `" + TABLE_USER + "` ( " +
                "`" + TABLE_USER_COLUMN_ID + "` INTEGER NOT NULL PRIMARY KEY, " +
                "`" + TABLE_USER_COLUMN_NAME + "` TEXT NOT NULL, " +
                "`" + TABLE_USER_COLUMN_USERNAME + "` TEXT NOT NULL, " +
                "`" + TABLE_USER_COLUMN_SESSIONID + "` TEXT NOT NULL, " +
                "`" + TABLE_USER_COLUMN_USERTYPE + "` INTEGER NOT NULL" +
                ");";

        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Get logged in user
    public UserModel getLoggedInUserModel() {
        UserModel userModel = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                new String[]{ TABLE_USER_COLUMN_ID, TABLE_USER_COLUMN_NAME, TABLE_USER_COLUMN_USERNAME, TABLE_USER_COLUMN_SESSIONID, TABLE_USER_COLUMN_USERTYPE },
                null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userModel = new UserModel();

            userModel.setId(Integer.parseInt(cursor.getString(0)));
            userModel.setName(cursor.getString(1));
            userModel.setUsername(cursor.getString(2));
            userModel.setSessionId(cursor.getString(3));
            userModel.setUserType(Integer.parseInt(cursor.getString(4)));

            cursor.close();
        }

        db.close();
        return userModel;
    }

    // Add new User
    public void newLoggedInUserModel(UserModel user) {
        deleteLoggedInUserModels();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_USER_COLUMN_ID, user.getId());
        values.put(TABLE_USER_COLUMN_NAME, user.getName());
        values.put(TABLE_USER_COLUMN_USERNAME, user.getUsername());
        values.put(TABLE_USER_COLUMN_SESSIONID, user.getSessionId());
        values.put(TABLE_USER_COLUMN_USERTYPE, user.getUserType());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Delete current users
    public void deleteLoggedInUserModels() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
