package ru.bokhonin.lexicon.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public List<TranslationWord> getWordsForTraining(Context context) {

        List<TranslationWord> translationWords = getVocabulary();
        List<TranslationWord> translationWordsForTraining = new ArrayList<>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String w = prefs.getString("num_words", "10");
        int numWords = Integer.parseInt(w);

        int sizeVocabulary = translationWords.size();

        Random rand = new Random();
        for (int i = 0; i < numWords; i++) {
            TranslationWord transWord = translationWords.get(rand.nextInt(sizeVocabulary));

            translationWordsForTraining.add(transWord);
        }

        return translationWordsForTraining;
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

    public void deleteTranslationWord(UUID id) {

        TranslationWord translationWord = getTranslationWord(id);
        String uuidString = translationWord.getUUID().toString();
//        ContentValues values = getContentValues(translationWord);

        mDatabase.delete(DbSchema.TranslatedWordTable.NAME,DbSchema.TranslatedWordTable.Cols.UUID + " = ?", new String[] {uuidString});

    }


    public static int getSizeVocabulary(Context context) {
        Vocabulary voc = Vocabulary.get(context);
        List<TranslationWord> translationWords = voc.getVocabulary();

        return translationWords.size();
    }

    public static int getSizeTypeWords(Context context, int type) {
        Vocabulary voc = Vocabulary.get(context);
        List<TranslationWord> translationWords = voc.getVocabulary();

        int amount = 0;

        for (int i = 0; i < translationWords.size(); i++) {
            if (translationWords.get(i).getStatusLearning() == type) {
                amount++;
            }
        }

        return amount;
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
                DbSchema.TranslatedWordTable.Cols.DATEUPLOAD + " DESC"
        );

        return new TranslationWordCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(TranslationWord translationWord) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.TranslatedWordTable.Cols.UUID, translationWord.getUUID().toString());
        values.put(DbSchema.TranslatedWordTable.Cols.ENWORD, translationWord.getEnWord());
        values.put(DbSchema.TranslatedWordTable.Cols.RUWORD, translationWord.getRuWord());
        values.put(DbSchema.TranslatedWordTable.Cols.DATEUPLOAD, translationWord.getDateUpload().getTime());
        values.put(DbSchema.TranslatedWordTable.Cols.DATETRAINING, translationWord.getDateTraining().getTime());
        values.put(DbSchema.TranslatedWordTable.Cols.STATUSLEARNING, translationWord.getStatusLearning());

        return values;
    }



}
