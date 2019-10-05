package my.rockpilgrim.moneyfortravel.model.database;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Entity(tableName = "transactions_table")
public class Transaction {

    public static final String TAG = "Transaction";
    @PrimaryKey(autoGenerate = true)
    private int id;
    private float count;
    private String tag;
    private String description;
    private long date;

    public Transaction(float count, String tag/*, Date date*/) {
        this.count = count;
        this.tag = tag;
//        this.date = date;
        Log.i(TAG, "Constructor");
    }

    @Ignore
    public Transaction(float count, String tag, String description, long date) {
        this.count = count;
        this.tag = tag;
        this.description = description;
        this.date = date;
        Log.i(TAG, "Constructor");
    }

    public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE transactions_table ADD COLUMN date INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public Transaction setAll(float count, String tag, String description, long date) {
        this.count = count;
        this.tag = tag;
        this.description = description;
        this.date = date;
        return this;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
