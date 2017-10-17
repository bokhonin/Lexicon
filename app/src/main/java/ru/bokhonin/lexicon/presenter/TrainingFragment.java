package ru.bokhonin.lexicon.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment {

    private TextView word;


    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView - TrainingFragment", Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.fragment_training, container, false);

        word = (TextView)view.findViewById(R.id.training_word);
        word.setText("Test");


        ImageView imageEye = (ImageView)view.findViewById(R.id.image_eye);
        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setText("Working!!!!!!");
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


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


}
