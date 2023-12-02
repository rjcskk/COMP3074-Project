package com.example.myrestaurantdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "RestaurantDB";
    private static final String RESTAURANTS_TABLE = "restaurant";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String DESCRIPTION = "description";
    private static final String TAGS = "tags";

    public DBHandler(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANTS_TABLE =
                "CREATE TABLE " + RESTAURANTS_TABLE
                        + "(" + ID + " INTEGER PRIMARY KEY autoincrement,"
                        + NAME + " TEXT,"
                        + ADDRESS + " TEXT,"
                        + PHONE_NUMBER + " TEXT,"
                        + DESCRIPTION + " TEXT, "
                        + TAGS + " TEXT)";
        db.execSQL(CREATE_RESTAURANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + RESTAURANTS_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public void addRestaurant(Restaurant restaurant){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, restaurant.getName());
        values.put(ADDRESS, restaurant.getAddress());
        values.put(PHONE_NUMBER, restaurant.getPhoneNumber());
        values.put(DESCRIPTION, restaurant.getDescription());
        values.put(TAGS, restaurant.getTags());

        db.insert(RESTAURANTS_TABLE, null, values);
        db.close();
    }

    public Restaurant getRestaurant(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                RESTAURANTS_TABLE,
                new String[]{ID, NAME, ADDRESS, PHONE_NUMBER, DESCRIPTION, TAGS},
                ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        Restaurant restaurant;

        if(cursor != null){
            cursor.moveToFirst();
            restaurant = new Restaurant(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );

            return restaurant;
        } else{
            return null;
        }
    }

    public List<Restaurant> getAllRestaurant(){
        SQLiteDatabase db = getReadableDatabase();
        List<Restaurant> restaurants = new ArrayList<>();

        String query = "SELECT * FROM " + RESTAURANTS_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setId(Integer.parseInt(cursor.getString(0)));
                restaurant.setName(cursor.getString(1));
                restaurant.setAddress(cursor.getString(2));
                restaurant.setPhoneNumber(cursor.getString(3));
                restaurant.setDescription(cursor.getString(4));
                restaurant.setTags(cursor.getString(5));

                restaurants.add(restaurant);
            }
            while (cursor.moveToNext());
        }
        return restaurants;
    }

    public int updateRestaurant(Restaurant restaurant){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, restaurant.getName());
        values.put(ADDRESS, restaurant.getAddress());
        values.put(PHONE_NUMBER, restaurant.getPhoneNumber());
        values.put(DESCRIPTION, restaurant.getDescription());
        values.put(TAGS, restaurant.getTags());

        return db.update(
                RESTAURANTS_TABLE,
                values,
                ID + "= ?",
                new String[]{String.valueOf(restaurant.getId())}
        );
    }

    public void deleteRestaurant(Restaurant restaurant){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(
                RESTAURANTS_TABLE,
                ID + "= ?",
                new String[]{String.valueOf(restaurant.getId())}
        );
        db.close();
    }

    public int getRestaurantCount(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + RESTAURANTS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}
