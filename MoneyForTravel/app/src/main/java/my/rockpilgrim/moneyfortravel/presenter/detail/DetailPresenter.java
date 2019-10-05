package my.rockpilgrim.moneyfortravel.presenter.detail;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import my.rockpilgrim.moneyfortravel.App;
import my.rockpilgrim.moneyfortravel.model.Model;
import my.rockpilgrim.moneyfortravel.model.database.Transaction;
import my.rockpilgrim.moneyfortravel.other.ConvertFloat;
import my.rockpilgrim.moneyfortravel.view.detail.IMvpDetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<IMvpDetailView> {

    public static final String TAG = "DetailPresenter";

    public int position;

    @Inject
    public Model model;

    public DetailPresenter() {
        App.getComponent().inject(this);
        Log.i(TAG, "Constructor");
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, model.transactionList.get(position).getTag());
        Log.i(TAG, "onFirstViewAttach");
    }

    @Override
    public Set<IMvpDetailView> getAttachedViews() {
        Log.i(TAG, "getAttachedViews");
        updateViewState();
        return super.getAttachedViews();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCount() {
        return ConvertFloat.getLine(model.transactionList.get(position).getCount());
    }

    public String getTag() {
        return model.transactionList.get(position).getTag();
    }

    public String getDescription() {
        return model.transactionList.get(position).getDescription();
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yy");
        if (model.transactionList.get(position).getDate() == 0) {
            return "N/A";
        }
        return dateFormat.format(model.transactionList.get(position).getDate());
    }

    public void updateTransaction(float count, String tag, String description, long date) {
        if (Math.abs(count) <= 0.01)
            return;
        Transaction transaction = model.transactionList.get(position)
                .setAll(count, tag, description, date);
        Disposable disposable = model.updateTransaction(transaction).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Log.i(TAG, "updateTransaction was success");
                    model.updateChangeListener();
                    updateViewState();
                }, throwable -> {
                    Log.e(TAG, "updateTransaction error", throwable);
                });
    }

    private void updateViewState() {
        getViewState().initUI(getCount()
                , getTag()
                , getDescription()
                , getDate());
    }
}