package rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertUser(String username, String password, String email) {
        this.open();

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.USERNAME, username);
        contentValue.put(DatabaseHelper.PASSWORD, password);
        contentValue.put(DatabaseHelper.EMAIL, email);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);

        this.close();
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USERNAME, DatabaseHelper.PASSWORD, DatabaseHelper.EMAIL };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        this.open();

        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USERNAME, DatabaseHelper.PASSWORD, DatabaseHelper.EMAIL };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));

            users.add(new User(email,username,password,id));
        }

        cursor.close();
        this.close();

        return users;
    }

    public int updateUser(String username, String password, String email) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PASSWORD, password);

        //This is more elegant whay to do multiple argument where clause but its not working
//        // Defining the WHERE clause
//        String whereClause = String.format("%s = ? AND %s = ?", DatabaseHelper.USERNAME, DatabaseHelper.EMAIL);
//        Toast.makeText(context.getApplicationContext(), whereClause, Toast.LENGTH_SHORT).show();
//
//        // Defining the values for the WHERE clause
//        String[] whereArgs = {username, password};

        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues
                , DatabaseHelper.USERNAME + " = " + "\"" +username + "\"" + " AND " + DatabaseHelper.EMAIL + " = " + "\"" +email + "\""
                , null);

        this.close(); //Mozda treba da se obrise
        return i;
    }

//    public int updateUser(long _id, String username, String password, String email) {
//        this.open();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.USERNAME, username);
//        contentValues.put(DatabaseHelper.PASSWORD, password);
//        contentValues.put(DatabaseHelper.EMAIL, email);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//
//        this.close(); //Mozda treba da se obrise
//        return i;
//    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
