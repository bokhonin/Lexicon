package ru.bokhonin.lexicon.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TrainingActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<TranslationWord> mTranslationWords;
    private List<TranslationWord> mTranslationWords2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
//
//        if (fragment == null) {
//            fragment = createFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragmentContainer, fragment)
//                    .commit();
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_pager);


        mViewPager = (ViewPager)findViewById(R.id.training_view_pager);

        Vocabulary vocab = Vocabulary.get(this);
        mTranslationWords = vocab.getVocabulary();
        mTranslationWords2 = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            TranslationWord transWord = mTranslationWords.get(i);
            mTranslationWords2.add(transWord);

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TranslationWord translationWord = mTranslationWords2.get(position);
//                Toast.makeText(TrainingActivity.this, "aaa", Toast.LENGTH_SHORT);
//                return CrimeFragment.newInstance(crime.getId());

                return TrainingFragment.newInstance(translationWord.getUUID());

            }

            @Override
            public int getCount() {
                return mTranslationWords2.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setNumberPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setNumberPage(0);



//        TextView button = (TextView)findViewById(R.id.text1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Vocabulary vocab = Vocabulary.get(TrainingActivity.this);
//                List<TranslationWord> translationWords = vocab.getVocabulary();
//
//                for (int i = 0; i < translationWords.size(); i++) {
//                    TranslationWord transWord = translationWords.get(i);
//                    transWord.setDateTraining(transWord.getDateUpload());
//                    transWord.setStatusLearning(0);
//
//                    vocab.updateTranslationWord(transWord);
//                }
//            }
//        });





//        for (int i = 0; i < mCrimes.size(); i++) {
//            if (mTranslationWords.get(i).getUUID().equals(crimeId)) {
//                mViewPager.setCurrentItem(i);
//                break;
//            }
//        }




    }

    private void setNumberPage(int position) {
        TextView text1 = (TextView)TrainingActivity.this.findViewById(R.id.text1);

        int size = mViewPager.getAdapter().getCount();

        String str = new Integer(position + 1).toString() + " / " + new Integer(size).toString();
        text1.setText(str);
    }

}
