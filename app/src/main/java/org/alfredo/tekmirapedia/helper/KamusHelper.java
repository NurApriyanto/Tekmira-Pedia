package org.alfredo.tekmirapedia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.alfredo.tekmirapedia.model.Kamus;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class KamusHelper {
    private static DbHelper dbHelper;
    private static KamusHelper INSTANCE;
    private static SQLiteDatabase db;

    private KamusHelper(Context context){
        dbHelper = new DbHelper(context);
    }

    public static KamusHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new KamusHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        if (db.isOpen()){
            db.close();
        }
    }

    public ArrayList<Kamus> getFavoriteKamus(){
        ArrayList<Kamus> KamusArrayList = new ArrayList<>();
        Cursor cursor = db.query(DbContract.FavoriteKamus.TABLE_KAMUS,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            do {
                Kamus Kamus = new Kamus();
                Kamus.setIdKamus(cursor.getInt(cursor.getColumnIndex(_ID)));
                Kamus.setIndo(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteKamus.COLUMN_INDO)));
                Kamus.setInggris(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteKamus.COLUMN_INGGRIS)));
                Kamus.setUraian(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteKamus.COLUMN_URAIAN)));

                KamusArrayList.add(Kamus);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return KamusArrayList;
    }

    public boolean isFavoriteKamus(int KamusId){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + DbContract.FavoriteKamus.TABLE_KAMUS + " WHERE " + _ID + " = '" + KamusId + "'";
            cursor = database.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return isFavorite;
    }

    public void addFavoriteKamus(Kamus Kamus){
        ContentValues values = new ContentValues();
        values.put(DbContract.FavoriteKamus._ID, Kamus.getIdKamus());
        values.put(DbContract.FavoriteKamus.COLUMN_INDO, Kamus.getIndo());
        values.put(DbContract.FavoriteKamus.COLUMN_INGGRIS, Kamus.getInggris());
        values.put(DbContract.FavoriteKamus.COLUMN_URAIAN, Kamus.getUraian());

        db.insert(DbContract.FavoriteKamus.TABLE_KAMUS, null, values);
    }

    public void deleteKamus(int KamusId){
        db.delete(DbContract.FavoriteKamus.TABLE_KAMUS, _ID + " = '" + KamusId + "'", null);
    }
}
