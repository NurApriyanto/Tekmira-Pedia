package org.alfredo.tekmirapedia.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "tekmirapedia.db";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_KAMUS = "CREATE TABLE " + DbContract.FavoriteKamus.TABLE_KAMUS + " (" +
                DbContract.FavoriteKamus._ID + " INTEGER NOT NULL," +
                DbContract.FavoriteKamus.COLUMN_INDO + " TEXT NOT NULL, " +
                DbContract.FavoriteKamus.COLUMN_INGGRIS + " TEXT NOT NULL, " +
                DbContract.FavoriteKamus.COLUMN_URAIAN + " TEXT NOT NULL" + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_KAMUS);

        final String SQL_CREATE_TABLE_ENSIKLOPEDIA = "CREATE TABLE " + DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA + " (" +
                DbContract.FavoriteEnsiklopedia._ID + " INTEGER NOT NULL," +
                DbContract.FavoriteEnsiklopedia.COLUMN_INDO + " TEXT NOT NULL, " +
                DbContract.FavoriteEnsiklopedia.COLUMN_INGGRIS + " TEXT NOT NULL, " +
                DbContract.FavoriteEnsiklopedia.COLUMN_URAIAN + " TEXT NOT NULL" + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ENSIKLOPEDIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteKamus.TABLE_KAMUS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA);
        onCreate(sqLiteDatabase);
    }
}
