package ru.bokhonin.lexicon.presenter;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.utils.Translater;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslaterFragment extends Fragment {

    /* The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";

    public TranslaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translater, container, false);

        final Button mTranslateButton = (Button)view.findViewById(R.id.btn_translate);
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView)getActivity().findViewById(R.id.source_word);
                String sourceWord = textView.getText().toString();
                new GetTranslateTask().execute(sourceWord);

//                Toast.makeText(getActivity(), ARG_SECTION_NUMBER, Toast.LENGTH_SHORT).show();
            }
        });


        ImageButton mClearButton = (ImageButton)view.findViewById(R.id.btn_clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView)getActivity().findViewById(R.id.source_word);
                textView.setText("");

                TextView textTrans = (TextView)getActivity().findViewById(R.id.translated_word);
                textTrans.setText("");

                ImageView imageView = (ImageView)getActivity().findViewById(R.id.btn_bookmark);
                imageView.setImageResource(R.mipmap.ic_bookmark_border);
            }
        });

        ImageButton mBookmarkButton = (ImageButton)view.findViewById(R.id.btn_bookmark);
        mBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView)getActivity().findViewById(R.id.btn_bookmark);
                imageView.setImageResource(R.mipmap.ic_bookmark);

                TextView textView = (TextView)getActivity().findViewById(R.id.source_word);
                String sourceWord = textView.getText().toString();

                Toast.makeText(getActivity(), sourceWord + " добавлено в словарь", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TranslaterFragment newInstance(int sectionNumber) {
        TranslaterFragment fragment = new TranslaterFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    public void setTrans(String str) {
        TextView textView = (TextView)getActivity().findViewById(R.id.translated_word);
        textView.setText(str);
    }

    private class GetTranslateTask extends AsyncTask<String, Void, Void> {

        String translatedWord = "";

        @Override
        protected Void doInBackground(String... params) {
            String word = params[0];

            try {
                translatedWord = new Translater().translate("en-ru", word);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setTrans(translatedWord);
//            Log.d("test____!!!", translatedWord);
        }
    }



}
