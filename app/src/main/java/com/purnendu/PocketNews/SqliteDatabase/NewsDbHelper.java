package com.purnendu.PocketNews.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.purnendu.PocketNews.Activities.MainActivity;
import com.purnendu.PocketNews.Model.NewsModel;

import java.util.ArrayList;

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NewsDatabase";
    private final Context context;


    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table trending " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");

        db.execSQL(
                "create table entertainment " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");

        db.execSQL(
                "create table business " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");


        db.execSQL(
                "create table tech " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");


        db.execSQL(
                "create table sports " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");


        db.execSQL(
                "create table health " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");


        db.execSQL(
                "create table science " +
                        "(id integer primary key autoincrement, title text,description text,newsurl text,posterurl text,date text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS trending");
        db.execSQL("DROP TABLE IF EXISTS entertainment");
        db.execSQL("DROP TABLE IF EXISTS business");
        db.execSQL("DROP TABLE IF EXISTS tech");
        db.execSQL("DROP TABLE IF EXISTS sports");
        db.execSQL("DROP TABLE IF EXISTS science");
        db.execSQL("DROP TABLE IF EXISTS health");
        onCreate(db);
    }

    public boolean insertNews(String tableName, String title, String description, String newsurl, String posterurl, String date) {

        try {
            if (getMaxId(tableName) > 50000)  //When id will be greater than 50000,deleting all records,resetting autoincrement id,recreating activity
            {
                deleteAllRecords(tableName);
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + tableName + "'");
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                Runtime.getRuntime().exit(0);
            }

            if (numberOfRows(tableName) > 99)   //Deleting news after inserting 100 news so that always contains 100 news
            {
                deleteNews(tableName);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }


        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("description", description);
            contentValues.put("newsurl", newsurl);
            contentValues.put("posterurl", posterurl);
            contentValues.put("date", date);
            db.insert(tableName, null, contentValues);

        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private int numberOfRows(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + tableName, null);
    }

    public ArrayList<NewsModel.articles> getAllNews(String tableName) {
        ArrayList<NewsModel.articles> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + tableName, null);
        if (res.moveToFirst()) {
            do {
                String title = res.getString(1);
                String desc = res.getString(2);
                String newsUrl = res.getString(3);
                String posterUrl = res.getString(4);
                String date = res.getString(5);
                array_list.add(new NewsModel.articles(title, desc, posterUrl, newsUrl, date));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public String getLastNews(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE Id = (SELECT MAX(Id) FROM " + tableName + ")", null);
        if (res != null && res.moveToFirst()) {
            String title = res.getString(1);
            res.close();
            return title;
        }
        return null;

    }

    private void deleteNews(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " WHERE " + "Id = (SELECT MIN(Id) FROM " + tableName + ")");
    }

    private long getMaxId(String tableName) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT MAX(id) FROM " + tableName, null);
        long maxId = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        cursor.close();
        return maxId;
    }

    private void deleteAllRecords(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + tableName);
    }

    public void deleteAllTableTogether() {
        String[] tableNames = {"trending", "entertainment", "business", "tech", "health", "science"};
        SQLiteDatabase db = this.getWritableDatabase();
        for (String name : tableNames) {
            deleteAllRecords(name);
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + name + "'");
        }
    }
}
