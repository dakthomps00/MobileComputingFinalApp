package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasehelper extends SQLiteOpenHelper
{
    public databasehelper(Context mycontext)
    {
        super(mycontext, "sitedb", null, 1);
        // version = has to be 1

    }

    //adds prefilled links
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE sitedb (ID INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT)");

        ContentValues cv = new ContentValues();
        cv.put("item", "Rape, Abuse & Incest National Network>https://www.rainn.org/resources");
        db.insert("sitedb", null, cv);
        ContentValues cv2 = new ContentValues();
        cv2.put("item", "Online Self-Defense Lessons>https://www.verywellfit.com/best-online-self-defense-classes-5075581");
        db.insert("sitedb", null, cv2);
        ContentValues cv3 = new ContentValues();
        cv3.put("item", "Local Self-Defense Classes>https://www.google.com/search?q=self+defense+near+me");
        db.insert("sitedb", null, cv3);
        ContentValues cv4 = new ContentValues();
        cv4.put("item", "Local Police Force>https://www.google.com/search?q=police+station+near+me");
        db.insert("sitedb", null, cv4);
        ContentValues cv5 = new ContentValues();
        cv5.put("item", "National Sexual Violence Resource Center>https://www.nsvrc.org");
        db.insert("sitedb", null, cv5);
        ContentValues cv6 = new ContentValues();
        cv6.put("item", "National Suicide Prevention Line>https://suicidepreventionlifeline.org");
        db.insert("sitedb", null, cv6);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE sitedb");
        onCreate(db);
    }

    //get cursor
    public Cursor getcursor()
    {
        Cursor returncursor;
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        returncursor = db.rawQuery("SELECT * FROM sitedb", null);
        return returncursor;
    }

}

