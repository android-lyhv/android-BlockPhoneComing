package halo.com.limitedphone_receviesms_contentporvider.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by HoVanLy on 4/22/2016.
 */

abstract class AbstractDataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ContactDb.sqlite";
    private Context mContext;
    private File mDatabasePath;
    private SQLiteDatabase mSQLiteDatabase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public AbstractDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
        mDatabasePath = mContext.getDatabasePath(DATABASE_NAME);
        onCreateDataBase();
        onOpenDataBase();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void onCreateDataBase() {
        if (!mDatabasePath.exists()) {
            copyDataBase();
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    public void copyDataBase() {
        try {
            File file = new File(mDatabasePath.getParent());
            if (!file.exists()) {
                file.mkdir();
            }
            InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(mDatabasePath, false);
            byte[] buffer = new byte[1024];
            int size;
            while ((size = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, size);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onOpenDataBase() {
        mSQLiteDatabase = SQLiteDatabase.openDatabase(mDatabasePath.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No-op
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No-op
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return mSQLiteDatabase;
    }
}
