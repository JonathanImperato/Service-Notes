package notes.service.com.servicenotes.data.source.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Ayudante para manejo de bases de datos SQLite.
 *
 * @author Imperato Jonathan
 * @see <a href="http://bit.ly/1s5nUI5"> Create a Database Using a SQL Helper</a>
 */
public class NotesDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = NotesDatabaseHelper.class.getSimpleName();
    private static final String DATABASE_SCHEMA_FILE_NAME_PATTERN = "notes_schema-v%s.sql";
    private static final String DATABASE_NAME = "notes.db";
    private static final String note = "note";
    private static final String NAME = "name";
    private static final String ROWID = "_id";

    //content2 is the address
    private static final String content2 = "content2";
    private static final String emails = "emails";
    private static final String phone = "phone";
    private static final String gender = "gender";
    private static final int DATABASE_VERSION = 2;
    SQLiteDatabase db;
    private final Context context;

    /**
     * Construye un NotesDatabaseHelper.
     *
     * @param context el contexto donde se crea este NotesDatabaseHelper.
     */
    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "Creating database version " + DATABASE_VERSION + "...");
        InputStream fileStream = null;
        try {
            // lee archivo notes_schema-v%s.sql para extraer las sentencias SQL
            fileStream = context.getAssets().open(String.format(DATABASE_SCHEMA_FILE_NAME_PATTERN, DATABASE_VERSION));
            String[] statements = SQLFileParser.getSQLStatements(fileStream);
            // ejecuta las sentencias
            for (String statement : statements) {
                Log.v(TAG, statement);
                db.execSQL(statement);
            }
        } catch (IOException ex) {
            Log.e(TAG, "Unable to open schema file", ex);
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException ex) {
                    Log.e(TAG, "Unable to close stream", ex);
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        String address = "ALTER TABLE " + note + " ADD COLUMN " + content2 + " TEXT";
        String email = "ALTER TABLE " + note + " ADD COLUMN " + emails + " TEXT";
        String phones = "ALTER TABLE " + note + " ADD COLUMN " + phone + " TEXT";

        if (oldVersion < newVersion) {

            db.execSQL(address);
            db.execSQL(email);
            db.execSQL(phones);
        } else
            onCreate(db);
    }

}