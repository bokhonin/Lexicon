package ru.bokhonin.lexicon.model;

public class DbSchema {

    public static final class TranslatedWordTable {
        public static final String NAME = "translationWords";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String ENWORD = "enWord";
            public static final String RUWORD = "ruWord";
            public static final String DATEUPLOAD = "dateUpload";
            public static final String DATETRAINING = "dateTraining";
            public static final String STATUSLEARNING = "statusLearning";
        }
    }
}
