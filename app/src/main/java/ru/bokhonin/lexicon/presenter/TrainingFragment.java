package ru.bokhonin.lexicon.presenter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment{

    private TextView word;
    private TranslationWord mTranslationWord;


    public TrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID id = (UUID)getArguments().getSerializable("testing");
        mTranslationWord = Vocabulary.get(getActivity()).getTranslationWord(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView - TrainingFragment", Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.fragment_training, container, false);

        word = (TextView)view.findViewById(R.id.training_word);
        word.setText(mTranslationWord.getEnWord());

//        TextView text1 = (TextView)getActivity().findViewById(R.id.text1);
//        text1.setText(mTranslationWord.getEnWord());

        ImageView imageEye = (ImageView)view.findViewById(R.id.image_eye);
        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setText(mTranslationWord.getRuWord());
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

    @Override
    public void onResume() {
        super.onResume();

//        TextView text1 = (TextView)getActivity().findViewById(R.id.text1);
//        text1.setText(mTranslationWord.getEnWord());
    }







    public static TrainingFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable("testing", id);

        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(args);

        return fragment;
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


}
