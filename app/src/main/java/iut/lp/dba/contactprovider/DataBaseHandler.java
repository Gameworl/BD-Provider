package iut.lp.dba.contactprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "db_contact";
    public static final String TABLE_NAME = "contact";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME ="name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_EMAIL = "email";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME +"(" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_NAME +" TEXT,"+
                KEY_PHONE_NUMBER + " TEXT,"+
                KEY_EMAIL + " TEXT"+
                ")" ;
        db.execSQL(create_table);
        Log.d("dataBaseHandler", "db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_table = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(drop_table);
        onCreate(db);
    }

    public int UpdateContact() {
        return 0;
    }

    public void DeleteContact(){

    }
}
