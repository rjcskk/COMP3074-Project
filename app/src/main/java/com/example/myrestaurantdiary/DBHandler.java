package com.example.myrestaurantdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "restaurantManager";
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_RATING = "rating";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_PHONE_NUMBER + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_TAGS + " TEXT," + KEY_RATING + " REAL" + ")";
        db.execSQL(CREATE_RESTAURANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }

    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_ADDRESS, restaurant.getAddress());
        values.put(KEY_PHONE_NUMBER, restaurant.getPhoneNumber());
        values.put(KEY_DESCRIPTION, restaurant.getDescription());
        values.put(KEY_TAGS, restaurant.getTags());
        values.put(KEY_RATING, restaurant.getRating());

        db.insert(TABLE_RESTAURANTS, null, values);
        db.close();
    }

    public Restaurant getRestaurant(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESTAURANTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_ADDRESS, KEY_PHONE_NUMBER, KEY_DESCRIPTION, KEY_TAGS, KEY_RATING }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Restaurant restaurant = new Restaurant(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getFloat(6));

        return restaurant;
    }

    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> restaurantList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(Integer.parseInt(cursor.getString(0)));
                restaurant.setName(cursor.getString(1));
                restaurant.setAddress(cursor.getString(2));
                restaurant.setPhoneNumber(cursor.getString(3));
                restaurant.setDescription(cursor.getString(4));
                restaurant.setTags(cursor.getString(5));
                restaurant.setRating(cursor.getFloat(6));

                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }

        return restaurantList;
    }

    public void updateRating(int id, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RATING, rating);

        db.update(TABLE_RESTAURANTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}