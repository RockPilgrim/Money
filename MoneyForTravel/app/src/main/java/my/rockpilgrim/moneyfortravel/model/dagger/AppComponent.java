package my.rockpilgrim.moneyfortravel.model.dagger;

import javax.inject.Singleton;

import dagger.Component;
import my.rockpilgrim.moneyfortravel.presenter.detail.DetailPresenter;
import my.rockpilgrim.moneyfortravel.presenter.list.ListPresenter;
import my.rockpilgrim.moneyfortravel.presenter.add.AddPresenter;
import my.rockpilgrim.moneyfortravel.presenter.sort.SortPresenter;
import my.rockpilgrim.moneyfortravel.view.detail.DetailActivity;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(AddPresenter presenter);
    void inject(SortPresenter presenter);
    void inject(ListPresenter presenter);
    void inject(DetailPresenter presenter);
}
