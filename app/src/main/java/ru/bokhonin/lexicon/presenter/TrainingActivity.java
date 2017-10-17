package ru.bokhonin.lexicon.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.bokhonin.lexicon.R;
import ru.bokhonin.lexicon.model.TranslationWord;
import ru.bokhonin.lexicon.model.Vocabulary;

import java.util.List;
import java.util.UUID;

public class TrainingActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<TranslationWord> mTranslationWords;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TranslationWord translationWord = mTranslationWords.get(position);
//                return CrimeFragment.newInstance(crime.getId());
                return new TrainingFragment();

            }

            @Override
            public int getCount() {
                return mTranslationWords.size();
            }
        });

//        for (int i = 0; i < mCrimes.size(); i++) {
//            if (mTranslationWords.get(i).getUUID().equals(crimeId)) {
//                mViewPager.setCurrentItem(i);
//                break;
//            }
//        }



    }

    protected Fragment createFragment() {
        return new TrainingFragment();
    }

}
