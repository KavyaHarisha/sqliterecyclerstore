package kavya.sqliterecyclerstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import kavya.sqliterecyclerstore.model.UserInfo;


public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userInfoManager";

    // UserInfo table name
    private static final String TABLE_USERINFO = "userinfo";

    // UserInfo Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN_DATE_TIME = "user_login_date_time";
    private static final String KEY_LOGOUT_DATE_TIME = "user_logout_date_time";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_USERINFO+"("
                +KEY_ID+" INTEGER PRIMARY KEY,"
                + KEY_LOGIN_DATE_TIME +" TEXT,"
                + KEY_LOGOUT_DATE_TIME +" TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USERINFO);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new user
    public void addUserInfo(UserInfo uInfo){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(KEY_LOGIN_DATE_TIME,uInfo.getmUserLoginDateTime());
        values.put(KEY_LOGOUT_DATE_TIME,uInfo.getmUserLogoutDateTime());
        database.insert(TABLE_USERINFO,null,values);
    }

    // Getting All Contacts
    public List<UserInfo> getUserInfo(){
        List<UserInfo> userInfoList =  new ArrayList<>();

        // Select All Query
        String GET_ALL_INFO =  "SELECT * FROM "+TABLE_USERINFO;
        SQLiteDatabase database =  this.getWritableDatabase();
        Cursor cursor = database.rawQuery(GET_ALL_INFO,null);
        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do {
                UserInfo info = new UserInfo();
                info.setmId(cursor.getInt(0));
                info.setmUserLoginDateTime(cursor.getString(1));
                info.setmUserLogoutDateTime(cursor.getString(2));
                userInfoList.add(info);
            }while (cursor.moveToNext());

        }

        return userInfoList;
    }

    // Getting user Count
    public int getUserCount(){
        SQLiteDatabase database =  this.getWritableDatabase();
        String USER_COUNT = "SELECT * FROM "+TABLE_USERINFO;
        Cursor cursor = database.rawQuery(USER_COUNT,null);
        cursor.close();
        return cursor.getCount();
    }

    // Updating single user
    public int updateUserInfo(UserInfo uInfo){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(KEY_LOGIN_DATE_TIME,uInfo.getmUserLoginDateTime());
        contentValues.put(KEY_LOGOUT_DATE_TIME,uInfo.getmUserLogoutDateTime());
        // updating row
        return database.update(TABLE_USERINFO,contentValues,KEY_ID+" =?",new String[]{String.valueOf(uInfo.getmId())});
    }

    // Deleting single user
    public void deleteSingleUser(UserInfo userInfo){
        SQLiteDatabase database =  this.getWritableDatabase();
        database.delete(TABLE_USERINFO,KEY_ID+" =?",new String[]{String.valueOf(userInfo.getmId())});
        database.close();
    }

    // Deleting all Users
    public void deleteUsers(){
        SQLiteDatabase database =  this.getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_USERINFO);
        database.close();
    }

    // Getting single user
    UserInfo getSingleUserInfo(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_USERINFO,new String[]{KEY_ID, KEY_LOGIN_DATE_TIME, KEY_LOGOUT_DATE_TIME},KEY_ID+" =?"
        ,new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
            UserInfo userInfo =  new UserInfo(cursor.getString(1), cursor.getString(2));
        return userInfo;
    }
}
