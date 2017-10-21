package ru.bokhonin.lexicon.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TranslationWord {

    private UUID mId;
    private String mEnWord;
    private String mRuWord;
    private Date mDateUpload;
    private Date mDateTraining;
    private int mStatusLearning;

    public TranslationWord() {
        this(UUID.randomUUID());
    }

    public TranslationWord(UUID id) {
        mId = id;
        mDateUpload = new Date();
        mDateTraining = new Date();
        mStatusLearning = 0;
    }

    public TranslationWord(String enWord, String ruWord) {
        mId = UUID.randomUUID();
        mDateUpload = new Date();
        mDateTraining = new Date();
        mEnWord = enWord;
        mRuWord = ruWord;
        mStatusLearning = 0;
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

    public Date getDateTraining() {
        return mDateTraining;
    }

    public int getStatusLearning() {
        return mStatusLearning;
    }

    public String getStatusLearningString() {
        String status = "";

        if (mStatusLearning == 0 || mStatusLearning == 1) {
            status = "⋆ новое";
        } else if (mStatusLearning == 2) {
            status = "⋆ завтра";
        } else if (mStatusLearning == 3) {
            status = "⋆ неделя";
        } else if (mStatusLearning == 4) {
            status = "⋆ месяц";
        } else if (mStatusLearning == 4) {
            status = "⋆ выучено";
        };

        return status;
    }

    public String getDateUploadFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(mDateUpload);
    }

    public void setDateUpload(Date dateUpload) {
        mDateUpload = dateUpload;
    }

    public void setStatusLearning(int statusLearning) {
        mStatusLearning = statusLearning;
    }

    public void changeStatusLearning(boolean statusUp) {
        // Status:
        // 0 - not begin training
        // 1 - today
        // 2 - tomorrow
        // 3 - week
        // 4 - month
        // 5 - on demand

        setDateTraining(new Date());

        if (statusUp) {
            if (mStatusLearning == 5) {
                //
            } else if (mStatusLearning == 0){
                mStatusLearning = 2;
            } else {
                mStatusLearning = mStatusLearning + 1;
            }
        }
        else {
            if (mStatusLearning == 0 || mStatusLearning == 1) {
                mStatusLearning = 1;
            } else {
                mStatusLearning = 2;
            }
        }
    }

    public void setDateTraining(Date dateTraining) {
        mDateTraining = dateTraining;
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
