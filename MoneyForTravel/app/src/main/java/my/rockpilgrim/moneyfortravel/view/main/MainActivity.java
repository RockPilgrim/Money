package my.rockpilgrim.moneyfortravel.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatActivity;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.view.add.IMvpAddView;
import my.rockpilgrim.moneyfortravel.view.list.ListFragment;

public class MainActivity extends MvpAppCompatActivity implements IMvpAddView {

    public static final String TAG = "MainActivity";

    @BindView(R.id.viewPager)
    public ViewPager viewPager;

    @BindView(R.id.add_button)
    public Button addButton;

    @BindView(R.id.sort_button)
    public Button sortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Log.i(TAG, "Ost " + (-1.05 % 1));

        Date date = new Date();
//        Log.i(TAG, String.format("Date: %t", date.toString()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yy");
        Log.i(TAG, "Date: " + dateFormat.format(date));

        ButterKnife.bind(this);
        initFragment();
        initPager();
    }

    private void initPager() {
        viewPager.setAdapter(new EditPagerAdapter(getSupportFragmentManager()));
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = fragmentManager.findFragmentById(R.id.list_container);

        if (listFragment == null) {
            listFragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_container, listFragment)
                    .commit();
        }
    }

    @OnClick({R.id.add_button, R.id.sort_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                Log.i(TAG, "AddButton pressed");
                viewPager.setCurrentItem(EditPagerAdapter.ADD_FRAGMENT, true);
                break;
            case R.id.sort_button:
                Log.i(TAG, "SortButton pressed");
                viewPager.setCurrentItem(EditPagerAdapter.SORT_FRAGMENT, true);
                break;
        }
    }
}
