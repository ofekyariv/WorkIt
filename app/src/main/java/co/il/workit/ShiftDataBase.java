package co.il.workit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Collections.sort;

public class ShiftDataBase extends SQLiteOpenHelper {
    private static final String DATABASENAME = "workit.db";
    private static final String TABLE_RECORD = "tblshift";
    private static final int DATABASEVERSION = 1;
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_DATE = "Date";
    private static final String[] allColumns = {COLUMN_ID, COLUMN_TIME, COLUMN_DATE};

    private static ArrayList<Shift> Shifts;

    public static ArrayList<Shift> getShifts()
    {
        return Shifts;
    }

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_TIME + " TEXT," +
            COLUMN_DATE + " TEXT );";

    private SQLiteDatabase database; // access to table

    public ShiftDataBase(Context context) { // Context gives access to resource libary
        super(context, DATABASENAME, null, DATABASEVERSION);
            getAllRecords();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // sqlLiteDataBase is created in db and creates a new table with .execSQL
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    public void setRecord(Shift Shift) {
        Shift current = null;
        for (Shift p: Shifts)
        {
            if(p.getId()== Shift.getId())
            {
                current=p;
                break;
            }
        }
        if(current==null) {
            Shifts.add(Shift);
            createRecord(Shift);
        }
        else {
            updateByRow(current);
        }
        //Collections.sort(shifts);
    }

    public Shift createRecord(Shift record) {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, record.getDate());
        values.put(COLUMN_TIME, record.getTime());
        long id = database.insert(TABLE_RECORD, null, values);
        record.setId(id);
        database.close(); // close
        return record;
    }

    private ArrayList<Shift> getAllRecords() {
        database = getReadableDatabase(); // get access to read the database
        Shifts = new ArrayList<>();
        String sortOrder = COLUMN_DATE + " DESC"; // sorting by score
        Cursor cursor = database.query(TABLE_RECORD, allColumns, null, null, null, null, sortOrder); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            //SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
            while (cursor.moveToNext()) {
                try {
                    //Date date = format.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    long time = cursor.getLong(cursor.getColumnIndex(COLUMN_TIME));
                    long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                    Shift record = new Shift(date, time, id);
                    Shifts.add(record);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        database.close(); // close
        //sort(shifts);
        return Shifts;
    }

    public void deleteShiftByRow(long id) {
        database = getWritableDatabase(); // get access to write the data
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        getAllRecords();
        database.close(); // close the database
    }

    public void updateByRow(Shift Shift) {
        database = getWritableDatabase();// get access to write the data
        ContentValues values = new ContentValues();
        //SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
        values.put(COLUMN_ID, Shift.getId());
        values.put(COLUMN_DATE, Shift.getDate());
        values.put(COLUMN_TIME, Shift.getTime());
        database.update(TABLE_RECORD, values, COLUMN_ID + "=" + Shift.getId(), null);
        database.close(); // close
    }

}
