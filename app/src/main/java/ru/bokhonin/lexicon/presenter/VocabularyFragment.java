package ru.bokhonin.lexicon.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VocabularyFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;



    public VocabularyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);

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


    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TranslationWord mTranslationWord;

        private TextView mWordTextView;
        private TextView mTranslationTextView;

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trans_word, parent, false));
            itemView.setOnClickListener(this);

            mWordTextView = (TextView)itemView.findViewById(R.id.word);
            mTranslationTextView = (TextView)itemView.findViewById(R.id.translation);
        }

        public void bind(TranslationWord transWord) {
            mTranslationWord = transWord;
            mWordTextView.setText(mTranslationWord.getEnWord());
            mTranslationTextView.setText(mTranslationWord.getRuWord());
        }

        @Override
        public void onClick(View view) {
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);
        }
    }




    private class Adapter extends RecyclerView.Adapter<Holder> {
        private List<TranslationWord> mTranslationWords;

        public Adapter(List<TranslationWord> translationWords) {
            mTranslationWords = translationWords;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            TranslationWord crime = mTranslationWords.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mTranslationWords.size();
        }
    }


    private void updateUI() {
        Vocabulary vocab = Vocabulary.get();
        List<TranslationWord> translationWords = vocab.getVocabulary();

        if (mAdapter == null) {
            mAdapter = new Adapter(translationWords);
            mRecyclerView.setAdapter(mAdapter);

            // Моя добавка разделителя между элементами списка
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

            DividerItemDecoration itemDecoration = new DividerItemDecoration(
                    mRecyclerView.getContext(),
                    layoutManager.getOrientation()
            );

            itemDecoration.setDrawable(
                    ContextCompat.getDrawable(
                            VocabularyFragment.this.getActivity(),
                            R.drawable.divider_white
                    )
            );

            mRecyclerView.addItemDecoration(
                    itemDecoration
            );
        }
        else {
            mAdapter.notifyDataSetChanged();
        }
    }



}
