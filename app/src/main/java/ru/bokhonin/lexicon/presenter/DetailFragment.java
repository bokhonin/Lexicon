package ru.bokhonin.lexicon.presenter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.UUID;

import static android.text.InputType.TYPE_CLASS_TEXT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment{

    public static final String EXTRA_ID = "ru.bokhonin.lexicon.detail_id";

    TranslationWord translationWord;
    TextView word;
    TextView translation;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID id = (UUID)getArguments().getSerializable(EXTRA_ID);
//        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        translationWord = Vocabulary.get(getActivity()).getTranslationWord(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        word = view.findViewById(R.id.detail_word);
        word.setText(translationWord.getEnWord());

        word.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(getActivity(), translationWord.getEnWord(), Toast.LENGTH_SHORT).show();
                word.setFocusableInTouchMode(true);

                return false;
            }
        });

        word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                translationWord.setEnWord(word.getText().toString());

                Vocabulary voc = new Vocabulary(getActivity());
                voc.updateTranslationWord(translationWord);
            }
        });

        translation = view.findViewById(R.id.detail_translation);
        translation.setText(translationWord.getRuWord());

        translation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                translation.setFocusableInTouchMode(true);

                return false;
            }
        });

        translation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                translationWord.setRuWord(translation.getText().toString());

                Vocabulary voc = new Vocabulary(getActivity());
                voc.updateTranslationWord(translationWord);
            }
        });

        return view;
    }

    public static DetailFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ID, id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }


}
