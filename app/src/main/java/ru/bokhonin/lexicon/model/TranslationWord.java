package ru.bokhonin.lexicon.model;

import java.util.Date;
import java.util.UUID;

public class TranslationWord {

    private UUID mId;
    private String mEnWord;
    private String mRuWord;
    private Date mDateUpload;

    public TranslationWord() {
        this(UUID.randomUUID());
    }

    public TranslationWord(UUID id) {
        mId = id;
        mDateUpload = new Date();
    }

    public TranslationWord(String enWord, String ruWord) {
        mId = UUID.randomUUID();
        mDateUpload = new Date();
        mEnWord = enWord;
        mRuWord = ruWord;
    }

    public UUID getUUID() {
        return mId;
    }

    public String getEnWord() {
        return mEnWord;
    }

    public String getRuWord() {
        return mRuWord;
    }

    public Date getDateUpload() {
        return mDateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        mDateUpload = dateUpload;
    }

    public void setEnWord(String enWord) {
        mEnWord = enWord;
    }

    public void setRuWord(String ruWord) {
        mRuWord = ruWord;
    }

    @Override
    public String toString() {
        return mEnWord + " - " + mRuWord;
    }
}
