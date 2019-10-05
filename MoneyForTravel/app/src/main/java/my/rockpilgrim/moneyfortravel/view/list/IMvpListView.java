package my.rockpilgrim.moneyfortravel.view.list;

import moxy.MvpView;

public interface IMvpListView extends MvpView {

    void setBalance(float balance);

    void changeActivity(int position);

    void updateList();
}
