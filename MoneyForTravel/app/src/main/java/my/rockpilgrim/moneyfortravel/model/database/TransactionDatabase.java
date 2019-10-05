package my.rockpilgrim.moneyfortravel.model.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Transaction.class,version = 2,exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
