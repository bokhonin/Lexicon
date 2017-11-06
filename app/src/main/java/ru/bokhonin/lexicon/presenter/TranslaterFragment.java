package ru.bokhonin.lexicon.presenter;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;
import ru.bokhonin.lexicon.utils.Translater;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslaterFragment extends Fragment {

    /* The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String BOOKMARK_STATE = "bookmarkState";
    private static final String TAG_DEBUG = "Lexy";
    private EditText sourceWordTextView;
    private EditText translatedWordTextView;
    private EditText translatedWordTextViewDetail;
    private ImageView mBookmarkButton;
    private boolean mAddBookmark;


    public TranslaterFragment() {
        // Required empty public constructor
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG_DEBUG, "onActivityCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView - TranslaterFragment", Toast.LENGTH_SHORT).show();

        Log.i(TAG_DEBUG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_translater, container, false);
        sourceWordTextView = view.findViewById(R.id.source_word);
        translatedWordTextView = view.findViewById(R.id.translated_word);
        translatedWordTextViewDetail = view.findViewById(R.id.translated_word_detail);
        mBookmarkButton = view.findViewById(R.id.btn_bookmark);

//        setRetainInstance(true);
        // В случае использования FragmentStatePagerAdapter этот кусок нужно использовать
//        if (savedInstanceState != null) {
//            setBookmark(savedInstanceState.getBoolean(BOOKMARK_STATE, false));
//        }

        setBookmark(mAddBookmark);


        Button mTranslateButton = view.findViewById(R.id.btn_translate);
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourceWord = sourceWordTextView.getText().toString();
                new GetTranslateTask().execute(sourceWord);

//                textView.setFocusable(false);

                // Спрячем клавиатуру после перевода
                hideKeyboard();
                setBookmark(false);
            }
        });


        ImageButton mClearButton = view.findViewById(R.id.btn_clear);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceWordTextView.setText("");
                translatedWordTextView.setText("");
                translatedWordTextViewDetail.setText("");

                // Покажем клавиатуру после очистки поля
//                showKeyboard();
                setBookmark(false);
            }
        });

        mBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourceWord = sourceWordTextView.getText().toString();
                String translatedWord = translatedWordTextView.getText().toString();

                if ((sourceWord.isEmpty() == true) || (translatedWord.isEmpty() == true)) {
                    Toast.makeText(getActivity(), "Не выбрано слово!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Vocabulary vocab = Vocabulary.get(getActivity());
                vocab.addTranslationWord(new TranslationWord(sourceWord, translatedWord));

                setBookmark(true);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                List<Fragment> listFragments = fm.getFragments();

                if (listFragments != null) {
                    Fragment fragment = listFragments.get(1);
                    fragment.onResume();
                }

                Toast.makeText(getActivity(), "Добавлено в словарь!", Toast.LENGTH_SHORT).show();
            }
        });


        ImageView mEditButton = view.findViewById(R.id.btn_edit);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Edit!", Toast.LENGTH_SHORT).show();
                translatedWordTextView.setEnabled(true);
                translatedWordTextView.setCursorVisible(true);
                translatedWordTextView.setFocusableInTouchMode(true);
                translatedWordTextView.setFocusable(true);

                translatedWordTextView.setSelection(translatedWordTextView.getText().length());
            }
        });


        return view;
    }

    private void setBookmark(boolean newState) {
        mAddBookmark = newState;

        if (mAddBookmark == true) {
            mBookmarkButton.setImageResource(R.mipmap.ic_favorite_pink);
        } else {
            mBookmarkButton.setImageResource(R.drawable.ic_favorite_border2);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BOOKMARK_STATE, mAddBookmark);
    }

    private void showKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();
//        onSaveInstanceState(new Bundle());
        Log.i(TAG_DEBUG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG_DEBUG, "onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG_DEBUG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG_DEBUG, "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG_DEBUG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG_DEBUG, "onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG_DEBUG, "onDetach");
    }

    public void setTrans(String str) {
        translatedWordTextView.setText(str);
    }

    public void setTransDetail(String str) {
        translatedWordTextViewDetail.setText(str);
    }


    private class GetTranslateTask extends AsyncTask<String, Void, Void> {

        String translatedWord = "";
        String translatedWordDetail = "";

        @Override
        protected Void doInBackground(String... params) {
            String word = params[0];

            try {
                translatedWord = new Translater().translateTrans("en-ru", word);
                translatedWordDetail = new Translater().translateDict("en-ru", word);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setTrans(translatedWord);
            setTransDetail(translatedWordDetail);
//            Log.d("test____!!!", translatedWord);
        }
    }


}
