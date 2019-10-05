package my.rockpilgrim.moneyfortravel.view.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.list.ListPresenter;
import my.rockpilgrim.moneyfortravel.other.ConvertFloat;
import my.rockpilgrim.moneyfortravel.view.detail.DetailActivity;

public class ListFragment extends MvpAppCompatFragment implements IMvpListView {

    public static final String TAG = "ListFragment";
    public static final String EXTRA_POSITION = "position";
    private ListRecyclerAdapter adapter;

    @BindView(R.id.list_recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.sum_textView)
    public TextView sumTextView;

    @BindColor(R.color.colorPositive)
    public int colorPositive;

    @BindColor(R.color.colorNegative)
    public int colorNegative;

    @InjectPresenter
    public ListPresenter presenter;

    @ProvidePresenter
    public ListPresenter providePresenter() {
        return new ListPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

//        Intent intent
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_layout, container, false);
        assert container != null;

        ButterKnife.bind(this, root);
        initRecycler(container);

        Log.i(TAG, "onCreateView");
        return root;
    }

    private void initRecycler(ViewGroup container) {
        adapter = new ListRecyclerAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        SwipeController swipeController = new SwipeController(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void setBalance(float balance) {
        Log.i(TAG, "" + balance + "/ " + balance % 1);
        if (balance >= 0) {
            sumTextView.setText(ConvertFloat.getLine(balance));
            sumTextView.setTextColor(colorPositive);
        } else {
            sumTextView.setText(ConvertFloat.getLine(balance));
            sumTextView.setTextColor(colorNegative);
        }
    }

    @Override
    public void changeActivity(int position) {
        Log.i(TAG, "changeActivity " + position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void updateList() {
        adapter.updateList();
    }
}
