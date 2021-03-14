package com.example.assignment2android;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//I have created DatabaseHelper class which extends from SQLiteOpenHelper which is main class for handling SQLiteDatabse
public class DatabaseHelper extends SQLiteOpenHelper
{
    //Declare the name of database
    public static final String DatabaseName = "StudentDB.db";
    public static final String TableName = "StudentInformationTable";
    public static final String ID = "ID";
    public static final String FN = "FirstName";
    public static final String LN = "LastName";
    public static final String Mark = "Marks";
    public static final String Courses = "Course";
    public static final String Credit = "Credits";

//Basic consrctor and methods

    public DatabaseHelper(@Nullable Context context)
    //When we call this constrcctor database will create
    {
        super(context, DatabaseName, null,1);
    }


    @Override
   //we will create table whenever onCreate method will call

    public void onCreate(SQLiteDatabase db) {
        //It executes query you pass in this method
   db.execSQL("create table " + TableName +" (ID Integer primary key autoincrement,FirstName text,LastName text,Marks text,Course text,Credits text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 db.execSQL("drop table " + TableName);
 onCreate(db);
    }

    public boolean InsertData (String FirstName, String LastName, String Marks,String Course,String Credits) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FN, FirstName);
        contentValues.put(LN, LastName);
        contentValues.put(Mark, Marks);
        contentValues.put(Courses, Course);
        contentValues.put(Credit, Credits);


        //it will take these argument
        db.insert(TableName, null, contentValues);
        return true;

    }
    // We have used cursor to give read write acess to return method.
    public Cursor getAlldata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Ans for storing information
        Cursor Ans = db.rawQuery("select * from "+TableName,null);
        return Ans;
    }

    public boolean updateData(String id,String firstname,String lastname,String marks,String Course,String Credits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(FN, firstname);
        contentValues.put(LN, lastname);
        contentValues.put(Mark, marks);
        contentValues.put(Courses, Course);
        contentValues.put(Credit, Credits);
        db.update(TableName,contentValues,"ID=?",new String[] {id});
        return true;
    }
    public Integer DeleteInformation(String id){
        SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(TableName,"ID=?",new String[] {id});

    }
    // select data using id
    public Cursor showTableDataByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TableName +" WHERE ID ="+ id, null);
        return data;
    }
    // select data using course code
    public Cursor showTableDataByCode(String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TableName +" WHERE Courses = ?", new String[] {course});
        return data;
    }
}



