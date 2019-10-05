package my.rockpilgrim.moneyfortravel.presenter.add;

import android.util.Log;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import my.rockpilgrim.moneyfortravel.App;
import my.rockpilgrim.moneyfortravel.view.add.IMvpAddView;
import my.rockpilgrim.moneyfortravel.model.Model;
import my.rockpilgrim.moneyfortravel.model.database.Transaction;

@InjectViewState
public class AddPresenter extends MvpPresenter<IMvpAddView> {

    public static final String TAG = "AddPresenter";

    @Inject
    public Model model;

    public AddPresenter() {
        App.getComponent().inject(this);
        Log.i(TAG, "Constructor");
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach");
    }

    public void addTransaction(float count, String tag, String description,long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yy");
        Log.i(TAG, "Date: " + dateFormat.format(date));
        if (count == 0 || tag == null) {
            return;
        }
        Transaction transaction = new Transaction(count, tag, description,date);
        Disposable subscribe = model.addTransaction(transaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    model.updateChangeListener();
                    Log.i(TAG, s);
                }, throwable -> {
                    Log.e(TAG, "add", throwable);
                });

        Log.i("AddPresenter", "add");
    }


}
