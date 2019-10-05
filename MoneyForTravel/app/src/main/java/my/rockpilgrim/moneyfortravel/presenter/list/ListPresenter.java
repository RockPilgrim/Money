package my.rockpilgrim.moneyfortravel.presenter.list;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import my.rockpilgrim.moneyfortravel.App;
import my.rockpilgrim.moneyfortravel.view.list.IMvpListView;
import my.rockpilgrim.moneyfortravel.model.Model;
import my.rockpilgrim.moneyfortravel.model.database.Transaction;

@InjectViewState
public class ListPresenter extends MvpPresenter<IMvpListView> implements IChangeListener,IChangeActivity{


    public static final String TAG = "ListPresenter";

    @Inject
    public Model model;

    public ListPresenter() {
        App.getComponent().inject(this);
        Log.i(TAG, "Constructor");

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        model.setChangeListener(this);
        Log.i(TAG, "onFirstViewAttach");
    }

    private void setBalance() {
        float balance=0;
        for (Transaction o:model.transactionList) {
            balance +=o.getCount();
        }
        Log.i(TAG, "Balance " + balance);
        getViewState().setBalance(balance);
    }

    public void deleteTransaction(int position) {
        Disposable disposable = model.deleteTransaction(model.transactionList.get(position))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    model.updateChangeListener();
                    Log.i(TAG, s);
                }, throwable -> {
                    Log.e(TAG, "delete", throwable);
                });
    }

    public float getCount(int position) {
        return model.transactionList.get(position).getCount();
    }

    public String getTag(int position) {
        return model.transactionList.get(position).getTag();
    }

    public String getDescription(int position) {
        return model.transactionList.get(position).getDescription();
    }

    public int getSize() {
        return model.transactionList.size();
    }

    //// From Model to RecyclerAdapter
    @Override
    public void update() {
        getViewState().updateList();
        setBalance();
    }

    //// To ListFragment
    @Override
    public void changeActivity(int position) {
        getViewState().changeActivity(position);
    }
}
