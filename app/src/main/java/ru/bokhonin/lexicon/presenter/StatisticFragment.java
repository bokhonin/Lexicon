package ru.bokhonin.lexicon.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.Vocabulary;

public class StatisticFragment extends Fragment {

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        TextView amountWords = view.findViewById(R.id.amount_words);
        amountWords.setText("Всего слов в словаре: " + Vocabulary.getSizeVocabulary(getActivity()));

        TextView amountWords0 = view.findViewById(R.id.amount_words_0);
        amountWords0.setText("Новые слова: " + Vocabulary.getSizeTypeWords(getActivity(), 0));

        TextView amountWords1 = view.findViewById(R.id.amount_words_1);
        amountWords1.setText("Повторить сегодня: " + Vocabulary.getSizeTypeWords(getActivity(), 1));

        TextView amountWords2 = view.findViewById(R.id.amount_words_2);
        amountWords2.setText("Повторить завтра: " + Vocabulary.getSizeTypeWords(getActivity(), 2));

        TextView amountWords3 = view.findViewById(R.id.amount_words_3);
        amountWords3.setText("Повторить через неделю: " + Vocabulary.getSizeTypeWords(getActivity(), 3));

        TextView amountWords4 = view.findViewById(R.id.amount_words_4);
        amountWords4.setText("Повторить через месяц: " + Vocabulary.getSizeTypeWords(getActivity(), 4));

        TextView amountWords5 = view.findViewById(R.id.amount_words_5);
        amountWords5.setText("Выучено слов: " + Vocabulary.getSizeTypeWords(getActivity(), 5));

        return view;
    }

}
