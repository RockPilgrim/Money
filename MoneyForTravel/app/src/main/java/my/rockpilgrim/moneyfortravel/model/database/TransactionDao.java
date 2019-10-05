package my.rockpilgrim.moneyfortravel.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transactions_table")
    Single<List<Transaction>> getAll();

    @Query("SELECT * FROM transactions_table WHERE tag=:tag")
    Single<List<Transaction>> getTag(String tag);

//    @Query("SELECT * FROM transactions_table WHERE tag=:tag AND count>=:count")
//    Single<List<Transaction>> getTag(String tag, int count);

    @Insert
    void add(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Update
    void update(Transaction transaction);
}
