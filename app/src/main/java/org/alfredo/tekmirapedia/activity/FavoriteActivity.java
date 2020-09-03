package org.alfredo.tekmirapedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.adapter.TabAdapter;
import org.alfredo.tekmirapedia.fragment.FavoriteEnsiklopediaFragment;
import org.alfredo.tekmirapedia.fragment.FavoriteKamusFragment;

public class FavoriteActivity extends AppCompatActivity {

    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ViewPager viewPager = findViewById(R.id.view_pager_favorite);
        TabLayout tabLayout = findViewById(R.id.tabs_favorite);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FavoriteEnsiklopediaFragment(), getString(R.string.ensiklopedia));
        tabAdapter.addFragment(new FavoriteKamusFragment(), getString(R.string.kamus));

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
