package ru.bokhonin.lexicon.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.bokhonin.lexicon.R;

import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    protected Fragment createFragment() {
        UUID id = (UUID)getIntent().getSerializableExtra(DetailFragment.EXTRA_ID);

        return DetailFragment.newInstance(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentDetailContainer);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentDetailContainer, fragment)
                    .commit();
        }
    }
}
