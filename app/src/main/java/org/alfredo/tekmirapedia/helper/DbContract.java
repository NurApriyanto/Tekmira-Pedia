package org.alfredo.tekmirapedia.helper;

import android.provider.BaseColumns;

public class DbContract {
    public static final class FavoriteKamus implements BaseColumns {
        public static String TABLE_KAMUS = "kamus";
        public static String COLUMN_INDO = "indo";
        public static String COLUMN_INGGRIS = "inggris";
        public static String COLUMN_URAIAN = "uraian";
    }

    public static final class FavoriteEnsiklopedia implements BaseColumns{
        public static String TABLE_ENSIKLOPEDIA = "ensiklopedia";
        public static String COLUMN_INDO = "indo";
        public static String COLUMN_INGGRIS = "inggris";
        public static String COLUMN_URAIAN = "uraian";
    }
}
