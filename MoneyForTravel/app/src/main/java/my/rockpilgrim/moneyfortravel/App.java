package my.rockpilgrim.moneyfortravel;

import android.app.Application;

import androidx.room.Room;

import my.rockpilgrim.moneyfortravel.model.dagger.AppComponent;
import my.rockpilgrim.moneyfortravel.model.dagger.AppModule;
import my.rockpilgrim.moneyfortravel.model.dagger.DaggerAppComponent;
import my.rockpilgrim.moneyfortravel.model.database.Transaction;
import my.rockpilgrim.moneyfortravel.model.database.TransactionDatabase;

public class App extends Application {

    private static TransactionDatabase database;
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        database = initDatabase();
        component = initComponent();
    }

    private AppComponent initComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static TransactionDatabase getDatabase() {
        return database;
    }

    private TransactionDatabase initDatabase() {
        return Room.databaseBuilder(this, TransactionDatabase.class, "transaction_db")
                .addMigrations(Transaction.MIGRATION_1_2)
                .build();
    }

}
