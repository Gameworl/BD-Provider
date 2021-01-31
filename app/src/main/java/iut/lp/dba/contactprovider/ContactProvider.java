package iut.lp.dba.contactprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class ContactProvider extends ContentProvider {
    static final String PROVIDER_NAME = "iut.lp.dba.contactprovider";
    static final String URL = "content://" +PROVIDER_NAME + "/contacts";
    static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String TABLE_NAME = "contact";


    //Schéma Contact
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_MAIL = "mail";

    static final int CONTACTS = 1;
    static final int CONTACT = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS);
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", CONTACT);
    }

    DataBaseHandler databaseManager;
    SQLiteDatabase database;

    public static HashMap<String, String> contactMap;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseManager = new DataBaseHandler(context);
        database = databaseManager.getWritableDatabase();

        if (database == null)
            return false;
        else
            return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)){
            //maps all database columns names
            case CONTACTS:
                queryBuilder.setProjectionMap(contactMap);
                break;
            case CONTACT:
                queryBuilder.appendWhere(KEY_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // no sorting => sort on names by default
            sortOrder = KEY_NAME;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row = database.insert(TABLE_NAME, "", values);

        //if record is added successfully
        if (row >0){
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case CONTACTS:
                return "vnd.android.cursor.dir/vnd.contactprovider.contacts";
            case CONTACT:
                return "vnd.android.cursor.dir/vnd.contactprovider.contacts";
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);

        }
    }



}
