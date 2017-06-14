package com.droidapp.ivanelv.mansy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.MessagePattern;

import com.droidapp.ivanelv.mansy.data.ParticipantContract.ParticipantEntry;

/**
 * Created by ivanelv on 13/06/2017.
 */

public class ParticipantDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Participant.db";
    public static final int DATABASE_VERSION = 1;

    public ParticipantDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_CREATE_TABLE =
                "CREATE TABLE " + ParticipantEntry.TABLE_NAME +
                        "(" +
                        ParticipantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ParticipantEntry.NAME + " TEXT NOT NULL," +
                        ParticipantEntry.EMAIL + " TEXT NOT NULL," +
                        ParticipantEntry.FINAL_PROJECT_LINK + " TEXT NOT NULL" +
                        ")";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String SQL_DELETE_TABLE =
            "DELETE TABLE IF EXISTS " + ParticipantEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
