package ru.bokhonin.lexicon.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vocabulary {

    private static Vocabulary sVocabulary;
    private List<TranslationWord> mTranslationWords;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public Vocabulary(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TransBaseHelper(mContext).getWritableDatabase();

//        mTranslationWords = new ArrayList<>();

//        String[] word = {"immediate", "angel", "head", "interaction", "goose", "table", "responsibilities", "debug", "monitor", "version"};
//        String[] tran = {"немедленный", "ангел", "голова", "взаимодействие", "гусь", "стол", "круг обязанностей", "отладка", "монитор", "версия"};
//
//        for (int i = 0; i < 10; i++) {
//            TranslationWord trWord = new TranslationWord();
//            trWord.setEnWord(word[i]);
//            trWord.setRuWord(tran[i]);
//            mTranslationWords.add(trWord);
//        }

    }

    public static Vocabulary get(Context context) {
        if (sVocabulary == null) {
            sVocabulary = new Vocabulary(context);
        }

        return sVocabulary;
    }

    public TranslationWord getTranslationWord(UUID id) {
        TranslationWordCursorWrapper cursor = queryTranslationWord(DbSchema.TranslatedWordTable.Cols.UUID + " = ?",
                new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTranslationWord();
        } finally {
            cursor.close();
        }
    }

    public List<TranslationWord> getVocabulary() {
//        return mTranslationWords;
//        return new ArrayList<>();

        List<TranslationWord> translationWords = new ArrayList<>();

        TranslationWordCursorWrapper cursor = queryTranslationWord(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                translationWords.add(cursor.getTranslationWord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return translationWords;
    }

    public void addTranslationWord(TranslationWord translationWord) {
//        mTranslationWords.add(translationWord);
        ContentValues values = getContentValues(translationWord);

        mDatabase.insert(DbSchema.TranslatedWordTable.NAME, null, values);
    }

    public void updateTranslationWord(TranslationWord translationWord) {
        String uuidString = translationWord.getUUID().toString();
        ContentValues values = getContentValues(translationWord);

        mDatabase.update(DbSchema.TranslatedWordTable.NAME, values, DbSchema.TranslatedWordTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    @NonNull
    private TranslationWordCursorWrapper queryTranslationWord(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DbSchema.TranslatedWordTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TranslationWordCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(TranslationWord translationWord) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.TranslatedWordTable.Cols.UUID, translationWord.getUUID().toString());
        values.put(DbSchema.TranslatedWordTable.Cols.ENWORD, translationWord.getEnWord().toString());
        values.put(DbSchema.TranslatedWordTable.Cols.RUWORD, translationWord.getRuWord().toString());
        values.put(DbSchema.TranslatedWordTable.Cols.DATEUPLOAD, translationWord.getDateUpload().toString());

        return values;
    }


}
