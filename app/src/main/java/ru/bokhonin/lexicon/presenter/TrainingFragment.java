package ru.bokhonin.lexicon.presenter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment {

    private TextView word;
    private TextView lang;
    private TranslationWord translationWord;
    private ImageView imageSmile;
    private ImageView imageSadSmile;

    private boolean mVisibleEnWord;


    public TrainingFragment() {
        // Required empty public constructor
    }

    public static TrainingFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable("testing", id);

        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID id = (UUID) getArguments().getSerializable("testing");
        translationWord = Vocabulary.get(getActivity()).getTranslationWord(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView - TrainingFragment", Toast.LENGTH_SHORT).show();

        mVisibleEnWord = true;

        View view = inflater.inflate(R.layout.fragment_training, container, false);

        word = view.findViewById(R.id.training_word);
        word.setText(translationWord.getEnWord());

        lang = view.findViewById(R.id.lang);
        setLang(mVisibleEnWord);

        ImageView imageEye = view.findViewById(R.id.image_eye);
        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVisibleEnWord) {
                    word.setText(translationWord.getRuWord());
                    mVisibleEnWord = false;
                } else {
                    word.setText(translationWord.getEnWord());
                    mVisibleEnWord = true;
                }
                setLang(mVisibleEnWord);
            }
        });


        imageSmile = view.findViewById(R.id.image_smile);
        imageSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vocabulary vocab = Vocabulary.get(getActivity());
                translationWord.changeStatusLearning(true);
                vocab.updateTranslationWord(translationWord);

                Toast.makeText(getActivity(), "знаю )", Toast.LENGTH_SHORT).show();
            }
        });

        imageSadSmile = view.findViewById(R.id.image_sad_smile);
        imageSadSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vocabulary vocab = Vocabulary.get(getActivity());
                translationWord.changeStatusLearning(false);
                vocab.updateTranslationWord(translationWord);

                Toast.makeText(getActivity(), "не знаю (", Toast.LENGTH_SHORT).show();
            }
        });

        return view;


//        Button button = (Button)view.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Vocabulary vocab = Vocabulary.get(getActivity());
//                List<TranslationWord> translationWords = vocab.getVocabulary();
//
////                for (int i = 0; i < translationWords.size(); i++) {
////                    TranslationWord transWord = translationWords.get(i);
////                    transWord.setDateUpload(new Date());
////
////                    vocab.updateTranslationWord(transWord);
////                }
//
//            }
//        });


    }

    private void setLang(boolean visibleEnWord) {
        if (visibleEnWord) {
            lang.setText("En");
            lang.setTextColor(Color.parseColor("#03a9f4"));
        } else {
            lang.setText("Ru");
            lang.setTextColor(Color.parseColor("#43a047"));
//            word.setTextColor(Color.parseColor("#43a047"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        TextView text1 = (TextView)getActivity().findViewById(R.id.text1);
//        text1.setText(mTranslationWord.getEnWord());
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


}
