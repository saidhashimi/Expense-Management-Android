package com.example.expensemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName="expense";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME1 = "users";
    private static final String TABLE_NAME2="trips";
    private static final String TABLE_NAME3="expenses";


    private SQLiteDatabase database;
    private String userID;
    private Session session;




    public DatabaseHelper(Context context) {
        super(context, databaseName, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String userQuery = "CREATE TABLE " + TABLE_NAME1 + " ("
                + "user_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_name" + " VARCHAR,"
                + "user_email" + " VARCHAR,"
                + "user_password"  + " VARCHAR)";

        String userQuery2 = "CREATE TABLE " + TABLE_NAME2 + " ("
                + "trip_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "trip_name" + " VARCHAR,"
                + "trip_destination" + " VARCHAR,"
                + "trip_date" + " VARCHAR,"
                + "trip_risk"  + " VARCHAR,"
                + "trip_description" + " VARCHAR,"
                + "trip_duration" + " VARCHAR,"
                + "trip_mode" + " VARCHAR,"
                + "user_id" + " INTEGER, FOREIGN KEY (user_id) REFERENCES "+TABLE_NAME1+" (user_id))";

        String userQuery3 = "CREATE TABLE " + TABLE_NAME3 + " ("
                + "exp_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "exp_type" + " VARCHAR,"
                + "exp_amount" + " VARCHAR,"
                + "exp_date" + " VARCHAR,"
                + "exp_comment"  + " VARCHAR,"
                + "trip_id" + " INTEGER, FOREIGN KEY (trip_id) REFERENCES "+TABLE_NAME2+" (trip_id))";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(userQuery);
        db.execSQL(userQuery2);
        db.execSQL(userQuery3);

    }

    //Insert users
    public void insertUsers(String name, String email, String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data= new ContentValues();

        data.put("user_name",name);
        data.put("user_email",email);
        data.put("user_password",password);





        db.insert(TABLE_NAME1, null, data);

        db.close();

    }

    //Insert new Trip
    public void newTrip(String name, String destination, String date, String risk, String description, String duration, String mode,int userid){




        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data= new ContentValues();

        data.put("trip_name",name);
        data.put("trip_destination",destination);
        data.put("trip_date",date);
        data.put("trip_risk",risk);
        data.put("trip_description",description);
        data.put("trip_duration",duration);
        data.put("trip_mode",mode);
        data.put("user_id",userid);


        db.insert(TABLE_NAME2, null, data);

        db.close();

    }

    //Insert Expense

    public void addExpense(String type, String amount, String date, String comment,int trip_id){




        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data= new ContentValues();

        data.put("exp_type",type);
        data.put("exp_amount",amount);
        data.put("exp_date",date);
        data.put("exp_comment",comment);

        data.put("trip_id",trip_id);


        db.insert(TABLE_NAME3, null, data);

        db.close();

    }
    //Login Process
    public String userLogin(String email,String pass){

        String[] columns = {
                "user_id"
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_email" + " = ?" + " AND " + "user_password" + " = ?";
        String[] selectionArgs = {email, pass};


        Cursor cursor = db.query(TABLE_NAME1, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        int cursorCount = cursor.getCount();

        if (cursor.moveToFirst()){
            userID=cursor.getString(0);
        }
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return userID;
        }
        return "Wrong";

    }

    // Add Expense Coding here

    public Cursor getUserTrips(String user_id){

        String[] columns = {
                "trip_id","trip_name"
        };



        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_id" + " = ?";
        String[] selectionArgs = {user_id};


        Cursor cursor = db.query(TABLE_NAME2, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

       return cursor;
    }

    // Get UserDetails

    public Cursor getUserDetails(String user_id){

        String[] columns = {
                "user_name","user_email","user_password"
        };



        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_id" + " = ?";
        String[] selectionArgs = {user_id};


        Cursor cursor = db.query(TABLE_NAME1, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        return cursor;
    }

    public Cursor getTripDetails(String trip_id){

        String[] columns = {
                "trip_name","trip_destination","trip_date","trip_risk","trip_description","trip_duration","trip_mode"
        };



        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "trip_id" + " = ?";
        String[] selectionArgs = {trip_id};


        Cursor cursor = db.query(TABLE_NAME2, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        return cursor;
    }

    public Cursor getTripExpenses(String trip_id){

        String[] columns = {
                "exp_id","exp_type"
        };



        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "trip_id" + " = ?";
        String[] selectionArgs = {trip_id};


        Cursor cursor = db.query(TABLE_NAME3, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        return cursor;
    }
    public Cursor getExpenseDetails(String exp_id){

        String[] columns = {
                "exp_type","exp_amount","exp_date","exp_comment"
        };



        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "exp_id" + " = ?";
        String[] selectionArgs = {exp_id};


        Cursor cursor = db.query(TABLE_NAME3, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        return cursor;
    }

    public boolean userAvialable(String email){
        String[] columns = {
                "user_id"
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_email" + " = ?" ;
        String[] selectionArgs = {email};


        Cursor cursor = db.query(TABLE_NAME1, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {

            return true;
        }
        return false;
    }

    //Updation Parts

    //User Password Update

    public void updatePassword(String password, String user_id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_password",password);
        String whereClause = "user_id=?";
        String whereArgs[] = {user_id};

        db.update(TABLE_NAME1, contentValues, whereClause, whereArgs);


    }

    public void updateTrip(String name,String destination,String date, String description, String duration, String mode, String trip_id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trip_name",name);
        contentValues.put("trip_destination",destination);
        contentValues.put("trip_date",date);
        contentValues.put("trip_description",description);
        contentValues.put("trip_duration",duration);
        contentValues.put("trip_mode",mode);

        String whereClause = "trip_id=?";
        String whereArgs[] = {trip_id};

        db.update(TABLE_NAME2, contentValues, whereClause, whereArgs);

    }

    public void deleteExpense(String exp_id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME3,"exp_id = ?",new String[] {exp_id});
    }

    public void deleteTripss(String trip_id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME3,"trip_id = ?",new String[] {trip_id});
        db.delete(TABLE_NAME2,"trip_id = ?",new String[] {trip_id});
    }


    public int getTripCount(String user_id){
        String[] columns = {
                "trip_id"
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "user_id" + " = ?" ;
        String[] selectionArgs = {user_id};


        Cursor cursor = db.query(TABLE_NAME2, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        int cursorCount = cursor.getCount();

        return cursorCount;
    }

    public int getTotalExpense(){
        String[] columns = {
                "exp_amount"
        };

        SQLiteDatabase db = this.getReadableDatabase();




        Cursor cursor = db.query(TABLE_NAME3, //Table to query
                columns,                    //columns to return
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        int totalExpense=0;

        while (cursor.moveToNext()){
            int index;
            index = cursor.getColumnIndexOrThrow("exp_amount");
            String amount = cursor.getString(index);
            int amm= Integer.parseInt(amount);

            totalExpense+=amm;


        }

        return totalExpense;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        Log.w(this.getClass().getName(),databaseName+"database upgrade to version"+newVersion+"- old data lost");
        onCreate(db);

    }





}
