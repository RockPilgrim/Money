package my.rockpilgrim.moneyfortravel.view.sort;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.sort.SortPresenter;

public class SortFragment extends MvpAppCompatFragment implements IMvpSortView {

    public static final String TAG = "SortFragment";
    @BindView(R.id.count_editText)
    public EditText countEditText;

    @BindView(R.id.tag_editText)
    public EditText tagEditText;

    @BindView(R.id.description_editText)
    public EditText descriptionEditText;

    @BindView(R.id.compare_button)
    public MultiToggleButton compareButton;

    @BindView(R.id.sort_button)
    public Button sortButton;

    @BindView(R.id.clear_button)
    public Button clearButton;

    @InjectPresenter
    public SortPresenter presenter;

    private InputMethodManager imm;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @ProvidePresenter
    public SortPresenter providePresenter() {
        Log.i(TAG, "providePresenter");
        return new SortPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sort_layout, container, false);
        ButterKnife.bind(this, root);
        Log.i(TAG, "onCreateView");
        setListeners();
        return root;
    }

    private void setListeners() {
        descriptionEditText.setOnKeyListener((v, keyCode, event) -> {
            Log.i(TAG, "setListener in onCreateView" + keyCode);
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onSortClick();
                    Log.i(TAG, "setListener in onCreateView");
                    return true;
                }
            }
            return false;
        });
    }


    @OnClick(R.id.sort_button)
    public void onSortClick() {
        if (countEditText.getText().toString().equals("")) {
            presenter.sortList(tagEditText.getText().toString(), descriptionEditText.getText().toString());
        } else {
            presenter.sortList(tagEditText.getText().toString()
                    , descriptionEditText.getText().toString()
                    , Float.parseFloat(countEditText.getText().toString()));
        }
        closeKeyboard();
    }

    @OnClick(R.id.clear_button)
    public void onClearClick() {
        presenter.sortList("", "");
        clearFields();
        closeKeyboard();
    }

    @OnClick({R.id.compare_button})
    public void onCompareClick() {
        presenter.setCompareSing(compareButton.state);
    }


    private void clearFields() {
        countEditText.setText("");
        tagEditText.setText("");
        descriptionEditText.setText("");
    }

    private void closeKeyboard() {
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
