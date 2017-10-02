package ru.bokhonin.lexicon.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class TranslationWordCursorWrapper extends CursorWrapper{

    public TranslationWordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public TranslationWord getTranslationWord() {

        String uuidString = getString(getColumnIndex(DbSchema.TranslatedWordTable.Cols.UUID));
        String enWord = getString(getColumnIndex(DbSchema.TranslatedWordTable.Cols.ENWORD));
        String ruWord = getString(getColumnIndex(DbSchema.TranslatedWordTable.Cols.RUWORD));
        long dateUpload = getLong(getColumnIndex(DbSchema.TranslatedWordTable.Cols.DATEUPLOAD));

        TranslationWord translationWord = new TranslationWord(UUID.fromString(uuidString));
        translationWord.setEnWord(enWord);
        translationWord.setRuWord(ruWord);
        translationWord.setDateUpload(new Date(dateUpload));

        return translationWord;
    }

}
