package my.rockpilgrim.moneyfortravel.view.detail;

import moxy.MvpView;

public interface IMvpDetailView extends MvpView {

    void initUI(String count,String tag,String description,String date);
}
