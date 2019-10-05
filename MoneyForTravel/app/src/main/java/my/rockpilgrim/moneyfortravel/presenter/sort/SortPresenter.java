package my.rockpilgrim.moneyfortravel.presenter.sort;

import android.util.Log;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import my.rockpilgrim.moneyfortravel.App;
import my.rockpilgrim.moneyfortravel.model.Model;
import my.rockpilgrim.moneyfortravel.view.sort.IMvpSortView;

@InjectViewState
public class SortPresenter extends MvpPresenter<IMvpSortView> {

    public static final String TAG = "SortPresenter";

    @Inject
    public Model model;

    private int compareSing;
    private float countCompare;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach");
    }

    public SortPresenter() {
        App.getComponent().inject(this);
        Log.i(TAG, "Constructor");
    }

    public void sortList(String tag,String description) {
//        if (tag == null || tag.equals("")){
//            Log.i(TAG, "Enter Tag: tag is "+tag);
////            model.getAll();
//            return;
//        }
        model.sort(tag, description);
        Log.i(TAG, "sortList");
    }
    public void sortList(String tag,String description,float count) {
        countCompare = count;

        model.sort(tag, description, count, compareSing);
        Log.i(TAG, "sortList");
    }

    public void setCompareSing(int compareSing) {
        this.compareSing = compareSing;
    }
}
