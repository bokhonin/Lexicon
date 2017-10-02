package ru.bokhonin.lexicon.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.bokhonin.lexicon.model.DbSchema.TranslatedWordTable;

public class TransBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "transBase.db";

    public TransBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TranslatedWordTable.NAME + " (_id integer primary key autoincrement, "
        + TranslatedWordTable.Cols.UUID + ", "
        + TranslatedWordTable.Cols.ENWORD + ", "
        + TranslatedWordTable.Cols.RUWORD + ", "
        + TranslatedWordTable.Cols.DATEUPLOAD +
        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
