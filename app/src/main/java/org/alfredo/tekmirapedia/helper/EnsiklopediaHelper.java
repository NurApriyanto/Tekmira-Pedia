package org.alfredo.tekmirapedia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.alfredo.tekmirapedia.model.Ensiklopedia;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class EnsiklopediaHelper {
    private static DbHelper dbHelper;
    private static EnsiklopediaHelper INSTANCE;
    private static SQLiteDatabase db;

    private EnsiklopediaHelper(Context context){
        dbHelper = new DbHelper(context);
    }

    public static EnsiklopediaHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new EnsiklopediaHelper(context);
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

    public ArrayList<Ensiklopedia> getFavoriteEnsiklopedia(){
        ArrayList<Ensiklopedia> EnsiklopediaArrayList = new ArrayList<>();
        Cursor cursor = db.query(DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA,
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
                Ensiklopedia Ensiklopedia = new Ensiklopedia();
                Ensiklopedia.setIdEnsiklopedia(cursor.getInt(cursor.getColumnIndex(_ID)));
                Ensiklopedia.setIstilahIndo(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteEnsiklopedia.COLUMN_INDO)));
                Ensiklopedia.setIstilahInggris(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteEnsiklopedia.COLUMN_INGGRIS)));
                Ensiklopedia.setUraian(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteEnsiklopedia.COLUMN_URAIAN)));

                EnsiklopediaArrayList.add(Ensiklopedia);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return EnsiklopediaArrayList;
    }

    public boolean isFavoriteEnsiklopedia(int ensiklopediaId){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA + " WHERE " + _ID + " = '" + ensiklopediaId + "'";
            cursor = database.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return isFavorite;
    }

    public void addFavoriteEnsiklopedia(Ensiklopedia ensiklopedia){
        ContentValues values = new ContentValues();
        values.put(DbContract.FavoriteEnsiklopedia._ID, ensiklopedia.getIdEnsiklopedia());
        values.put(DbContract.FavoriteEnsiklopedia.COLUMN_INDO, ensiklopedia.getIstilahIndo());
        values.put(DbContract.FavoriteEnsiklopedia.COLUMN_INGGRIS, ensiklopedia.getIstilahInggris());
        values.put(DbContract.FavoriteEnsiklopedia.COLUMN_URAIAN, ensiklopedia.getUraian());

        db.insert(DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA, null, values);
    }

    public void deleteEnsiklopedia(int ensiklopediaId){
        db.delete(DbContract.FavoriteEnsiklopedia.TABLE_ENSIKLOPEDIA, _ID + " = '" + ensiklopediaId + "'", null);
    }

}
