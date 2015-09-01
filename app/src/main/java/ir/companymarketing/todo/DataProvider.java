package ir.companymarketing.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataProvider {

    public static final String[] columns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE, DBHelper.COLUMN_DETAIL, DBHelper.COLUMN_DONE};
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    public DataProvider(Context context) {
        openHelper = new DBHelper(context);
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void addData(ToDo todo) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, todo.getTitle());
        values.put(DBHelper.COLUMN_DONE, todo.isDone());
        values.put(DBHelper.COLUMN_DETAIL, todo.getText());

        todo.setId(database.insert(DBHelper.TABLE_NAME, null, values));

    }

    public void close() {
        openHelper.close();
    }

    public ArrayList<ToDo> getData() {
        ArrayList<ToDo> todos = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ToDo todo = new ToDo();
                todo.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)));
                todo.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                todo.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DETAIL)));
                todo.setDone(cursor.getShort(cursor.getColumnIndex(DBHelper.COLUMN_DONE)) == 1);
                todos.add(todo);
                Log.d("DataProvider", "" + todo.getId());
            }
        }
        return todos;
    }

    public boolean remove(ToDo todo) {
        String where = DBHelper.COLUMN_ID + " = " + todo.getId();
        int result = database.delete(DBHelper.TABLE_NAME, where, null);
        Log.d("DataProvider", "" + result);
        return (result == 1);
    }

    public boolean update(ToDo todo) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, todo.getTitle());
        values.put(DBHelper.COLUMN_DONE, todo.isDone());
        values.put(DBHelper.COLUMN_DETAIL, todo.getText());
        String where = DBHelper.COLUMN_ID + " = " + todo.getId();
        int result = database.update(DBHelper.TABLE_NAME, values, where, null);
        return (result == 1);
    }
}
