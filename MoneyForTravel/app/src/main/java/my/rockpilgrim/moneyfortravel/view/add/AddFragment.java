package my.rockpilgrim.moneyfortravel.view.add;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.add.AddPresenter;

public class AddFragment extends MvpAppCompatFragment implements IMvpAddView {

    public static final String TAG = "AddFragment";


    @BindView(R.id.count_editText)
    public EditText countEditText;

    @BindView(R.id.tag_editText)
    public EditText tagEditText;

    @BindView(R.id.description_editText)
    public EditText descriptionEditText;

    @BindView(R.id.add_button)
    public Button addButton;

    @InjectPresenter
    public AddPresenter presenter;

    private InputMethodManager imm;


    @ProvidePresenter
    public AddPresenter providePresenter() {
        Log.i(TAG, "providePresenter");
        return new AddPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        closeKeyboard();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_layout, container, false);
        ButterKnife.bind(this, root);
        setListeners();
        return root;
    }

    private void setListeners() {
        descriptionEditText.setOnKeyListener((v, keyCode, event) -> {
            Log.i(TAG, "setListener in onCreateView " + keyCode);
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onAddClick();
                    Log.i(TAG, "setListener in onCreateView");
                    return true;
                }
            }
            return false;
        });
    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        if (countEditText.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Error: add count", Toast.LENGTH_SHORT).show();
            closeKeyboard();
            return;
        }
        presenter.addTransaction(Float.parseFloat(countEditText.getText().toString())
                , tagEditText.getText().toString()
                , descriptionEditText.getText().toString()
                , new Date().getTime());

        clearFields();
        closeKeyboard();
    }

    private void clearFields() {
        countEditText.setText("");
        descriptionEditText.setText("");
        tagEditText.setText("");
    }

    private void closeKeyboard() {
        Log.i(TAG, "closeKeyboard");
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}
