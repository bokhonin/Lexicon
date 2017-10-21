package ru.bokhonin.lexicon.presenter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;
import ru.bokhonin.lexicon.presenter.TrainingActivity;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class VocabularyFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

    private static final String DIALOG_DEL = "ru.bokhonin.lexicon.dialog_del";
    private static final int REQUEST_DEL = 0;

    private static final String TAG_DEBUG = "Lexy";

    public VocabularyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView - VocabularyFragment", Toast.LENGTH_SHORT).show();

        Log.i(TAG_DEBUG, "onCreateView-VocFrag");

        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TrainingActivity.class);
                startActivity(intent);
            }
        });


        mRecyclerView = (RecyclerView)view.findViewById(R.id.vocabulary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //int sectionNumber = (int)getArguments().getSerializable(ARG_SECTION_NUMBER);

        updateUI();

        return view;
    }





    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VocabularyFragment newInstance(int sectionNumber) {
        VocabularyFragment fragment = new VocabularyFragment();
        return fragment;
    }


    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TranslationWord mTranslationWord;

        private TextView mWordTextView;
        private TextView mTranslationTextView;
        private TextView mUploadDate;
        private TextView mTag;

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trans_word, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mWordTextView = (TextView)itemView.findViewById(R.id.word);
            mTranslationTextView = (TextView)itemView.findViewById(R.id.translation);
            mUploadDate = (TextView)itemView.findViewById(R.id.uploadDate);
            mTag = (TextView)itemView.findViewById(R.id.tag);
        }

        public void bind(TranslationWord transWord) {
            mTranslationWord = transWord;
            mWordTextView.setText(mTranslationWord.getEnWord());
            mTranslationTextView.setText(mTranslationWord.getRuWord());
            mUploadDate.setText(mTranslationWord.getDateUploadFormat());
            mTag.setText(mTranslationWord.getStatusLearningString());
        }

        @Override
        public void onClick(View view) {
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
//            Toast.makeText(getActivity(), this.mTranslationWord.getEnWord(), Toast.LENGTH_SHORT).show();

            FragmentManager manager = getFragmentManager();
            DialogVocabList dialog = DialogVocabList.newInstance(this.mTranslationWord.getUUID(), this.getLayoutPosition());

            dialog.setTargetFragment(VocabularyFragment.this, REQUEST_DEL);

            dialog.show(manager, DIALOG_DEL);

            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DEL) {
            UUID id = (UUID)data.getSerializableExtra(DialogVocabList.ARG_ID);
            int position = (int)data.getSerializableExtra(DialogVocabList.ARG_POS);

            // Удалим слово из списка
            Vocabulary vocab = Vocabulary.get(getActivity());
            vocab.deleteTranslationWord(id);
//            updateUI();


            List<TranslationWord> translationWords = vocab.getVocabulary();
            mAdapter.setTranslationWords(translationWords);
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRemoved(position);

//            Toast.makeText(getActivity(), "Слово удалено!", Toast.LENGTH_SHORT).show();
            Snackbar.make(getActivity().findViewById(R.id.vocabulary_recycler_view), "Слово удалено!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {
        private List<TranslationWord> mTranslationWords;

//        private final View.OnLongClickListener mLongClickListener;

        public Adapter(List<TranslationWord> translationWords) {
            mTranslationWords = translationWords;
//            , View.OnLongClickListener longClickListener
//            mLongClickListener = longClickListener;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            TranslationWord translationWord = mTranslationWords.get(position);
            holder.bind(translationWord);
        }

        @Override
        public int getItemCount() {
            return mTranslationWords.size();
        }

        public void setTranslationWords(List<TranslationWord> translationWords) {
            mTranslationWords = translationWords;
        }
    }


    public void updateUI() {
        Vocabulary vocab = Vocabulary.get(getActivity());
        List<TranslationWord> translationWords = vocab.getVocabulary();

//        Toast.makeText(getActivity(), "in updateUI", Toast.LENGTH_SHORT).show();

        // Моя добавка разделителя между элементами списка
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        VocabularyFragment.this.getActivity(),
                        R.drawable.divider_list
                )
        );

        mRecyclerView.addItemDecoration(
                itemDecoration
        );



        if (mAdapter == null) {
            mAdapter = new Adapter(translationWords);
            mRecyclerView.setAdapter(mAdapter);

//            Toast.makeText(getActivity(), "in updateUI 1", Toast.LENGTH_SHORT).show();
        }
        else {
//            Toast.makeText(getActivity(), "in updateUI 2", Toast.LENGTH_SHORT).show();

            mAdapter.setTranslationWords(translationWords);
            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onStart() {
        Log.i(TAG_DEBUG, "onStart-VocFrag");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG_DEBUG, "onResume-VocFrag");
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        Log.i(TAG_DEBUG, "onPause-VocFrag");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG_DEBUG, "onStop-VocFrag");
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG_DEBUG, "onCreate-VocFrag");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG_DEBUG, "onAttach-VocFrag");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG_DEBUG, "onDetach-VocFrag");
        super.onDetach();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.i(TAG_DEBUG, "onAttachFragment-VocFrag");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG_DEBUG, "onViewCreated-VocFrag");
        super.onViewCreated(view, savedInstanceState);
    }
}
