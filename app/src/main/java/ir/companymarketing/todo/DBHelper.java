package ir.companymarketing.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_DONE = "is_done";
    public static final String COLUMN_ID = "id";

    public static final String TABLE_NAME = "todos";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_DETAIL + " TEXT, "
            + COLUMN_DONE + " SHORT)";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "todo";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
