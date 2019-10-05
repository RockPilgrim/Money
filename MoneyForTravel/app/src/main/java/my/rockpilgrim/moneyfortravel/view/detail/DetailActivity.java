package my.rockpilgrim.moneyfortravel.view.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.detail.DetailPresenter;
import my.rockpilgrim.moneyfortravel.view.list.ListFragment;

public class DetailActivity extends MvpAppCompatActivity implements IMvpDetailView {

    public static final String TAG = "DetailActivity";

    @BindView(R.id.detail_cardView)
    public CardView cardView;
    /////////// Info View ////////////////
    @BindView(R.id.info_container)
    public LinearLayout infoContainer;

    @BindView(R.id.tag_textView)
    public TextView tagTextView;

    @BindView(R.id.description_textView)
    public TextView descriptionTextView;

    @BindView(R.id.count_textView)
    public TextView countTextView;

    @BindView(R.id.date_textView)
    public TextView dateTextView;
    /////////// Edit View ////////////////
    @BindView(R.id.edit_container)
    public LinearLayout editContainer;

    @BindView(R.id.count_editText)
    public EditText countEditText;

    @BindView(R.id.tag_editText)
    public EditText tagEditText;

    @BindView(R.id.description_editText)
    public EditText descriptionEditText;

    @BindView(R.id.date_editText)
    public EditText dateEditText;

    @BindView(R.id.edit_button)
    public Button editButton;

    ////////// Other /////////////////
    private InputMethodManager imm;

    @InjectPresenter
    public DetailPresenter presenter;

    @ProvidePresenter
    public DetailPresenter providePresenter() {
        return new DetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i(TAG, "onCreate");

        ButterKnife.bind(this);
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        presenter.setPosition(getIntent().getIntExtra(ListFragment.EXTRA_POSITION, 0));
    }

    @Override
    public void initUI(String count, String tag, String description, String date) {
        countTextView.setText(count);
        tagTextView.setText(tag);
        if (description.equals(""))
            descriptionTextView.setVisibility(View.GONE);
        else {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(description);
        }
        dateTextView.setText(date);
    }

    @OnLongClick(R.id.detail_cardView)
    public void onCardLongClick() {
        if (infoContainer.getVisibility() == View.VISIBLE) {
            infoContainer.setVisibility(View.GONE);
            editContainer.setVisibility(View.VISIBLE);

            setEdit();
        }/* else {
            infoContainer.setVisibility(View.VISIBLE);
            editContainer.setVisibility(View.GONE);
        }*/
    }

    private void setEdit() {
        countEditText.setText(presenter.getCount());
        tagEditText.setText(presenter.getTag());
        descriptionEditText.setText(presenter.getDescription());
        dateEditText.setText(presenter.getDate());
    }

    @OnClick(R.id.edit_button)
    public void onEditClick() {
        if (tagEditText.getText().toString().equals("")||countEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Add count or tag", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "OnEdinClick: dateEditText: " + dateEditText.getText().toString());
        presenter.updateTransaction(Float.parseFloat(countEditText.getText().toString())
                , tagEditText.getText().toString()
                , descriptionEditText.getText().toString()
                , new Date().getTime());

        infoContainer.setVisibility(View.VISIBLE);
        editContainer.setVisibility(View.GONE);
        closeKeyboard(infoContainer);
    }

    private void closeKeyboard(View view) {
        Log.i(TAG, "closeKeyboard");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
