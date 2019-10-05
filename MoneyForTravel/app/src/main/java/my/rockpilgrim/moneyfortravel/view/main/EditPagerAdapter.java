package my.rockpilgrim.moneyfortravel.view.main;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import my.rockpilgrim.moneyfortravel.view.add.AddFragment;
import my.rockpilgrim.moneyfortravel.view.sort.SortFragment;

public class EditPagerAdapter extends FragmentPagerAdapter {

    public static final String TAG = "EditPagerAdapter";
    public static final int ADD_FRAGMENT = 0;
    public static final int SORT_FRAGMENT = 1;

    private AddFragment addFragment;
    private SortFragment sortFragment;


    public EditPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getItem " + position);
        if (position == ADD_FRAGMENT) {
            return new AddFragment();
        }else
            return new SortFragment();
    }

    public void setFragment(int fragmentCode) {
        if (fragmentCode == ADD_FRAGMENT) {
            getItem(ADD_FRAGMENT);
        } else if (fragmentCode == SORT_FRAGMENT) {
            getItem(SORT_FRAGMENT);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
