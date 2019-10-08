package my.rockpilgrim.moneyfortravel.view.list;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.rockpilgrim.moneyfortravel.R;
import my.rockpilgrim.moneyfortravel.presenter.list.IChangeActivity;
import my.rockpilgrim.moneyfortravel.other.ConvertFloat;

public class ListRecyclerHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "ListRecyclerHolder";

    @BindView(R.id.info_cardView)
    public CardView cardView;

    @BindView(R.id.position_textView)
    public TextView positionTextView;

    @BindView(R.id.tag_textView)
    public TextView tagTextView;

    @BindView(R.id.description_textView)
    public TextView descriptionTextView;

    @BindView(R.id.count_textView)
    public TextView countTextView;


    @BindColor(R.color.colorPositive)
    public int colorPositive;

    @BindColor(R.color.colorNegative)
    public int colorNegative;

    private IChangeActivity activity;

    public ListRecyclerHolder(@NonNull View itemView, IChangeActivity activity) {
        super(itemView);
        this.activity = activity;
        Log.i(TAG, "Constructor");
        ButterKnife.bind(this, itemView);
    }

    public void bind(int position) {
        Log.i(TAG, "bind +2");
//        positionTextView.setText(String.valueOf(position+1));
        positionTextView.setText(getAdapterPosition() + 1);
    }

    public void bind(int position, float count, String tag, String description) {

        positionTextView.setText(String.valueOf(position + 1));
        tagTextView.setText(tag);
        setDescription(description);
        setCount(count);

        Log.i(TAG, "bind "+position);
    }

    private void setCount(float count) {
        if (count >= 0) {
            countTextView.setText(ConvertFloat.getLine(count));
            countTextView.setTextColor(colorPositive);
        }else {
            countTextView.setText(ConvertFloat.getLine(count));
            countTextView.setTextColor(colorNegative);
        }
    }

    private void setDescription(String description) {
        if (!description.equals("")) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(description);
            tagTextView.setTextSize(16);
        }
        else{
            descriptionTextView.setVisibility(View.GONE);
            tagTextView.setTextSize(24);
        }
    }

    @OnClick(R.id.info_cardView)
    public void onClickCard() {
        activity.changeActivity(getAdapterPosition());
    }
}
