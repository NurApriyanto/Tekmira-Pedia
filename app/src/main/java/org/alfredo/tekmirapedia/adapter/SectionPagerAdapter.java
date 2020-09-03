package org.alfredo.tekmirapedia.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.fragment.EnsiklopediaFragment;
import org.alfredo.tekmirapedia.fragment.KamusFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_JUDUL = new int[]{
            R.string.ensiklopedia,
            R.string.kamus
    };

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_JUDUL[position]);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new EnsiklopediaFragment();
                break;
            case 1:
                fragment = new KamusFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
