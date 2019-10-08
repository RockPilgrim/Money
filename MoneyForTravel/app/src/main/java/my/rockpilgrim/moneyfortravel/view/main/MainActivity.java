package my.rockpilgrim.moneyfortravel.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

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

    public TabLayout tabLayout;
    public TabItem addButton;
    public TabItem sortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Log.i(TAG, "Ost " + (-1.05 % 1));

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yy");
        Log.i(TAG, "Date: " + dateFormat.format(date));

        ButterKnife.bind(this);
        initFragment();
        initPager();
        initTabs();
    }



    private void initTabs() {
        tabLayout = findViewById(R.id.tab_layout);
        addButton = findViewById(R.id.add_button);
        sortButton = findViewById(R.id.sort_button);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
}
