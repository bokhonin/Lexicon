package ru.bokhonin.lexicon.model;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {

    private static Vocabulary sVocabulary;
    private List<TranslationWord> mTranslationWords;

    public Vocabulary() {
        mTranslationWords = new ArrayList<>();

        String[] word = {"immediate", "angel", "head", "interaction", "goose", "table", "responsibilities", "debug", "monitor", "version"};
        String[] tran = {"немедленный", "ангел", "голова", "взаимодействие", "гусь", "стол", "круг обязанностей", "отладка", "монитор", "версия"};

        for (int i = 0; i < 10; i++) {
            TranslationWord trWord = new TranslationWord();
            trWord.setEnWord(word[i]);
            trWord.setRuWord(tran[i]);
            mTranslationWords.add(trWord);
        }

    }

    public static Vocabulary get() {
        if (sVocabulary == null) {
            sVocabulary = new Vocabulary();
        }

        return sVocabulary;
    }

    public List<TranslationWord> getVocabulary() {
        return mTranslationWords;
    }

    public void addTranslationWord(TranslationWord translationWord) {
        mTranslationWords.add(translationWord);
    }
}
