package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper extends SQLiteOpenHelper {

    private static final String COL_PASSWORD = "password";
    private static final String COL_FULLNAME = "fullname";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COL_ID = "id";
    private static final String DB_NAME = "users.db";
    private static final String COL_EMAIL = "email";
    private static final String COL_USERNAME = "username";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT NOT NULL, " +
                COL_USERNAME + " TEXT NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_FULLNAME + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_NAME
        );
        onCreate(sqLiteDatabase);
    }

    public boolean addToTable(String email, String username, String password, String fullname) {
        if (!hozzadAD(email, username, fullname)) {
            return false;
        }
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_FULLNAME, fullname);
        long result = database.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean hozzadAD(String email, String username, String fullname) {
        if (!isEmail(email) || !isFullname(fullname)) {
            return false;
        }
        else if (felhasznaloNevELL(username) || emailELLenoriz(email)) {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkUserByEmail(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?", new String[] {email, password},
                null, null, null);
        return cursor.getCount() > 0;
    }
    public boolean nevKereso(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[] {username, password},
                 null, null, null);
        return cursor.getCount() > 0;
    }
    public boolean emailELLenoriz(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_EMAIL + " = ?", new String[] {email},
                null, null, null);
        return cursor.getCount() > 0;
    }
    public boolean felhasznaloNevELL(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_USERNAME + " = ?", new String[] {username},
                null, null, null);
        return cursor.getCount() > 0;
    }
    public Cursor getTableElementByEmail(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?", new String[] {email, password},
                null, null, null);
    }
    public Cursor getTableElementById(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_ID + " = ?", new String[] {String.valueOf(id)},
                null, null, null);
    }
    public Cursor getTableElementByUsername(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        return  database.query(TABLE_NAME, new String[] {COL_ID, COL_EMAIL, COL_USERNAME, COL_PASSWORD, COL_FULLNAME},
                COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[] {username, password},
                null, null, null);
    }
    public Cursor getTableElementByUsernameOrEmail(String usernameOrEmail, String password) {
        if (isEmail(usernameOrEmail)) {
            return getTableElementByEmail(usernameOrEmail, password);
        }
        return getTableElementByUsername(usernameOrEmail, password);
    }
    private boolean isEmail(String email) {
        Pattern regexPattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$");
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }
    private boolean isFullname(String fullname) {
        return fullname.split(" ").length > 1;
    }
}
