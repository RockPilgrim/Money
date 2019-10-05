package my.rockpilgrim.moneyfortravel.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import my.rockpilgrim.moneyfortravel.App;
import my.rockpilgrim.moneyfortravel.presenter.list.IChangeListener;
import my.rockpilgrim.moneyfortravel.model.database.Transaction;
import my.rockpilgrim.moneyfortravel.model.database.TransactionDao;
import my.rockpilgrim.moneyfortravel.other.OnNextListener;
import my.rockpilgrim.moneyfortravel.view.sort.MultiToggleButton;

public class Model {


    public static final String TAG = "Model";
    private TransactionDao transactionDao;
    public List<Transaction> transactionList;
    private IChangeListener changeListener;
    private boolean isListContainAll;

    public Model() {
        transactionList = new ArrayList<>();
        connectToDb();
        Log.i(TAG, "Constructor");
    }

    public void setChangeListener(IChangeListener changeListener) {
        this.changeListener = changeListener;
    }


    private void connectToDb() {
        Log.i(TAG, "connectToDb");
        transactionDao = App.getDatabase().transactionDao();
        getAll();
    }

    public void getAll() {
        Log.i(TAG, "getAll");
        getAllList(this::updateChangeListener);
    }

    //// MainThread /////
    private void getAllList(OnNextListener listener) {
        Single<List<Transaction>> single = transactionDao.getAll();
        Disposable disposable = single
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(transactions -> {
                    Log.i(TAG, "getAllList");
                    transactionList = transactions;
                    isListContainAll = true;
                    listener.onNext();
                }, throwable -> {
                    Log.i(TAG, "getAllList", throwable);
                });
    }

    //// MainThread /////
    private void sortTag(String tag) {
        Single<List<Transaction>> single = transactionDao.getTag(tag);
        Disposable disposable = single
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(transactions -> {
                    Log.i(TAG, "sortTag");
                    transactionList = transactions;
                    isListContainAll = false;
                    updateChangeListener();
                }, throwable -> {
                    Log.e(TAG, "sortTag", throwable);
                });
    }

    //// MainThread /////
    private void sortTag(String tag, OnNextListener iSortListener) {
        Single<List<Transaction>> single = transactionDao.getTag(tag);
        Disposable disposable = single
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(transactions -> {
                    transactionList = transactions;
                    iSortListener.onNext();
                    updateChangeListener();
                    Log.i(TAG, "sortTag");
                }, throwable -> {
                    Log.e(TAG, "sortTag", throwable);
                });
    }

    //// Tag and Description
    public void sort(String tag, String description) {
        if (tag.equals("") && description.equals("")) {
            if (!isListContainAll)
                getAll();
        } else if (!tag.equals("") && !description.equals("")) {
            sortTag(tag, () -> sortDescription(description));
        } else if (!description.equals("")) {
            if (!isListContainAll)
                getAllList(() -> sortDescription(description));
            else
                sortDescription(description);
        } else {
            sortTag(tag);
        }
    }

    ///// Tag and Description and Count
    public void sort(String tag, String description, float count, int compareSing) {
        if (tag.equals("") && description.equals("")) {
            if (!isListContainAll)
                getAllList(() -> sortCount(count, compareSing, description));
            else
                sortCount(count, compareSing, description);
            Log.i(TAG, "sort1");
        } else if (!tag.equals("")) {
            sortTag(tag, () -> sortCount(count, compareSing, description));
            Log.i(TAG, "sort2");
        }
    }

    private void sortDescription(String description) {
        Completable completable = Observable.create(emitter -> {
            List descriptionList = new ArrayList();
            for (Transaction t : transactionList) {
                if (t.getDescription().equals(description)) {
                    descriptionList.add(t);
//                    transactionList.remove(t);
                }
            }
            transactionList = descriptionList;
            isListContainAll = false;
            Log.i(TAG, "sortDescription " + description);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).ignoreElements();
        Disposable disposable = completable.observeOn(AndroidSchedulers.mainThread()).subscribe(this::updateChangeListener);
    }

    private void sortCount(float count, int compareSing, String description) {
        Completable completable = Observable.create(emitter -> {
            List<Transaction> sortList = new ArrayList<>();
            for (int i = 0; i < transactionList.size(); i++) {
                if (description.equals("") || transactionList.get(i).getDescription().equals(description)) {
                    if (compareSing == MultiToggleButton.LESS) {
                        if (transactionList.get(i).getCount() <= count) {
                            sortList.add(transactionList.get(i));
                        }
                    } else if (compareSing == MultiToggleButton.MORE) {
                        if (transactionList.get(i).getCount() >= count) {
                            sortList.add(transactionList.get(i));
                        }
                    } else if (compareSing == MultiToggleButton.EQUALS) {
                        if (transactionList.get(i).getCount() == count) {
                            sortList.add(transactionList.get(i));
                        }
                    } else {
                        sortList = transactionList;
                        Log.i(TAG, "onNext fail");
                        break;
                    }
                }
            }
            transactionList = sortList;
            isListContainAll = false;
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).ignoreElements();
        Disposable disposable = completable.observeOn(AndroidSchedulers.mainThread()).subscribe(this::updateChangeListener
                ,throwable -> Log.e("sortCount fail",throwable.getMessage()));
    }

    public void sortCount(int count, int compareSing) {
        List<Transaction> sortList = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            if (compareSing == MultiToggleButton.LESS) {
                if (transactionList.get(i).getCount() <= count) {
                    sortList.add(transactionList.get(i));
                }
            } else if (compareSing == MultiToggleButton.MORE) {
                if (transactionList.get(i).getCount() >= count) {
                    sortList.add(transactionList.get(i));
                }
            } else if (compareSing == MultiToggleButton.EQUALS) {
                if (transactionList.get(i).getCount() == count) {
                    sortList.add(transactionList.get(i));
                }
            } else {
                sortList = transactionList;
                Log.i(TAG, "onNext fail");
                break;
            }
        }
        transactionList = sortList;
        updateChangeListener();
    }


    //// NOT MainThread (from ) /////
    public Single<String> addTransaction(Transaction transaction) {
        return Single.create((SingleOnSubscribe<String>) emitter -> {
            transactionDao.add(transaction);
            emitter.onSuccess(String.format("Transaction %s added ", transaction.getId()));
            transactionList.add(transaction);
        }).subscribeOn(Schedulers.io());
    }

    //// NOT MainThread /////
    public Single<String> deleteTransaction(Transaction transaction) {
        return Single.create((SingleOnSubscribe<String>) emitter -> {
            transactionDao.delete(transaction);
            emitter.onSuccess(String.format("Deleted %s transaction", transaction.getId()));
            transactionList.remove(transaction);
        }).subscribeOn(Schedulers.io());
    }

    public Completable updateTransaction(Transaction transaction) {
        return Completable.create(emitter -> {
            transactionDao.update(transaction);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    public void updateChangeListener() {
        try {
            changeListener.update();
            Log.i(TAG, "updateChangeListener");
        } catch (NullPointerException e) {
            Log.e(TAG, "updateChangeListener", e);
        } catch (Exception e) {
            Log.e(TAG, "updateChangeListener", e);
        }
    }
}