package com.imejadevs.employee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Employee.db";
    // User table name
    private static final String TABLE_EXPENSES = "expenses";
    // User Table Columns names
    private static final String COLUMN_EXPENSES_ID = "expense_id";
    private static final String COLUMN_EXPENSES_TOTAL_CASH = "total_cash";
    private static final String COLUMN_EXPENSES_FLAG_VAT = "flag_vat";
    private static final String COLUMN_EXPENSES_DATE_RECEIVED = "date_received";
    private static final String COLUMN_EXPENSES_DATE_ADDED = "date_added";
    private static final String COLUMN_EXPENSES_DATE_PAID = "date_paid";
    private static final String COLUMN_EXPENSES_SUMMARY = "summary";
    private static final String COLUMN_EXPENSES_RECEIPT_PHOTO = "photo";
    private static final String COLUMN_EXPENSES_STATUS = "status";
    private static final String COLUMN_EXPENSES_NAME = "name";
    private static final String COLUMN_EXPENSES_PAYMENT = "pay";
    private static final String COLUMN_EXPENSE_DATE_CLAIMED = "date_claimed";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_EXPENSES + "(" +
                "expense_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "total_cash TEXT," +
                "flag_vat TEXT," +
                "date_received TEXT," +
                "date_added TEXT," +
                "date_paid TEXT," +
                "summary TEXT," +
                "status TEXT," +
                "pay TEXT," +
                "date_claimed TEXT," +
                "photo BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        onCreate(db);
    }

    public boolean addExpese(String flag_name, String total_cash, String claim_summary, boolean flag_claimed,
                             String date_incurred, String date_paid, String date_added, String vat_component,
                             byte[] byteArray, String not_paid,String date_claimed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSES_STATUS, flag_claimed);
        values.put(COLUMN_EXPENSES_NAME, flag_name);
        values.put(COLUMN_EXPENSES_TOTAL_CASH, total_cash);
        values.put(COLUMN_EXPENSES_SUMMARY, claim_summary);
        values.put(COLUMN_EXPENSES_FLAG_VAT, vat_component);
        values.put(COLUMN_EXPENSES_RECEIPT_PHOTO, byteArray);
        values.put(COLUMN_EXPENSES_DATE_PAID, date_paid);
        values.put(COLUMN_EXPENSES_DATE_ADDED, date_added);
        values.put(COLUMN_EXPENSES_DATE_RECEIVED, date_incurred);
        values.put(COLUMN_EXPENSES_PAYMENT, not_paid);
        values.put(COLUMN_EXPENSE_DATE_CLAIMED, date_claimed);
        long check_insert = db.insert(TABLE_EXPENSES, null, values);
        if (check_insert == -1) {
            return false;
        }
        return true;
    }

    public Cursor searchExpenses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);
        return cursor;
    }


    public boolean payExpense(String update, String paid, String s) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSES_DATE_PAID, update);
        contentValues.put(COLUMN_EXPENSES_PAYMENT, paid);
        long bool = database.update(TABLE_EXPENSES, contentValues, "" + COLUMN_EXPENSES_NAME + " =?", new String[]{s});
        if (bool == -1) {
            return false;
        }
        return true;
    }

    public boolean delete(String s) {
        SQLiteDatabase database = this.getWritableDatabase();
        long bool = database.delete(TABLE_EXPENSES,"" + COLUMN_EXPENSES_NAME + " =?",new String[]{s});
        if (bool == -1) {
            return false;
        }
        return true;
    }

    public boolean claimNow(String today, String s, String s1) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_DATE_CLAIMED, today);
        contentValues.put(COLUMN_EXPENSES_STATUS, s);
        long bool = database.update(TABLE_EXPENSES, contentValues, "" + COLUMN_EXPENSES_NAME + " =?", new String[]{s1});
        if (bool == -1) {
            return false;
        }
        return true;
    }
}
