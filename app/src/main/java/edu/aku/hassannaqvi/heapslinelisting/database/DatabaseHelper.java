package edu.aku.hassannaqvi.heapslinelisting.database;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.IBAHC;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_;
import static edu.aku.hassannaqvi.heapslinelisting.core.UserAuth.checkPassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import net.zetetic.database.sqlcipher.SQLiteDatabase;
import net.zetetic.database.sqlcipher.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ChildTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ClusterTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.EntryLogTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.FamilyMembersTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ListingTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.StreetsTable;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.models.Cluster;
import edu.aku.hassannaqvi.heapslinelisting.models.EntryLog;
import edu.aku.hassannaqvi.heapslinelisting.models.Listing;
import edu.aku.hassannaqvi.heapslinelisting.models.Streets;
import edu.aku.hassannaqvi.heapslinelisting.models.Users;

/**
 * @author hassan.naqvi on 11/30/2016.
 * @update ali azaz on 01/07/21
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    public static final String DATABASE_COPY = PROJECT_NAME + "_copy.db";
    public static final String DATABASE_PASSWORD = IBAHC;
    private static final int DATABASE_VERSION = 1;
    private final String TAG = "DatabaseHelper";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateTable.SQL_CREATE_USERS);
        db.execSQL(CreateTable.SQL_CREATE_CLUSTERS);
        //db.execSQL(CreateTable.SQL_CREATE_CLUSTERS);
        //db.execSQL(CreateTable.SQL_CREATE_RANDOM_HH);

        db.execSQL(CreateTable.SQL_CREATE_LISTING);
        db.execSQL(CreateTable.SQL_CREATE_STREETS);
        db.execSQL(CreateTable.SQL_CREATE_ENTRYLOGS);
        //db.execSQL(CreateTable.SQL_CREATE_CHILD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:

                // DO NOT BREAK AFTER EACH VERSION
                //break;
            case 2:
                db.execSQL(CreateTable.SQL_CREATE_DISTRICTS);
            case 3:
               // db.execSQL(CreateTable.SQL_ALTER_DISTRICT_CODE);
               // db.execSQL(CreateTable.SQL_ALTER_DISTRICT_NAME);
            default:

        }
    }


    //ADDITION in DB
    public long addListing(Listing listing) throws JSONException, SQLiteException {
        SQLiteDatabase db = null;
        long newRowId = -1;


        db = this.getWritableDatabase();

        if (db == null) {
            return newRowId; // Return -1 or handle the error as needed
        }

        ContentValues values = new ContentValues();
        values.put(ListingTable.COLUMN_PROJECT_NAME, listing.getProjectName());
        values.put(ListingTable.COLUMN_UID, listing.getUid());
        values.put(ListingTable.COLUMN_STUID, listing.getStuid());
        values.put(ListingTable.COLUMN_DISTRICT_ID, listing.getDistrictID());
        values.put(ListingTable.COLUMN_AREA, listing.getArea());
        values.put(ListingTable.COLUMN_CLUSTER_CODE, listing.getClusterCode());
        values.put(ListingTable.COLUMN_STREET_NUMBER, listing.getStreetNum());
        values.put(ListingTable.COLUMN_TAB_NUMBER, listing.getTabNo());
        values.put(ListingTable.COLUMN_STRUCTURE_NUMBER, listing.getStructureNo());
        values.put(ListingTable.COLUMN_HHID, listing.getHhid());
        values.put(ListingTable.COLUMN_USERNAME, listing.getUserName());
        values.put(ListingTable.COLUMN_SYSDATE, listing.getSysDate());
        values.put(ListingTable.COLUMN_GPSLAT, listing.getGpsLat());
        values.put(ListingTable.COLUMN_GPSLNG, listing.getGpsLng());
        values.put(ListingTable.COLUMN_GPSPRO, listing.getGpsProvider());
        values.put(ListingTable.COLUMN_GPSDATE, listing.getGpsDT());
        values.put(ListingTable.COLUMN_GPSACC, listing.getGpsAcc());
        values.put(ListingTable.COLUMN_ISTATUS, listing.getiStatus());
        values.put(ListingTable.COLUMN_DEVICEID, listing.getDeviceId());
        values.put(ListingTable.COLUMN_APPVERSION, listing.getAppver());
        values.put(ListingTable.COLUMN_SYNCED, listing.getSynced());
        values.put(ListingTable.COLUMN_SYNC_DATE, listing.getSyncDate());
        values.put(ListingTable.COLUMN_SHH, listing.sHHtoString());


        // Insert into database
        newRowId = db.insertOrThrow(ListingTable.TABLE_NAME, null, values);


        if (db != null) {
            db.close(); // Close the database connection
        }


        return newRowId;
    }


    public Long addStreet(Streets streets) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(StreetsTable.COLUMN_PROJECT_NAME, streets.getProjectName());
        values.put(StreetsTable.COLUMN_UID, streets.getUid());
        values.put(StreetsTable.COLUMN_DISTRICT_ID, streets.getDistrictID());
        values.put(StreetsTable.COLUMN_CLUSTER_CODE, streets.getClusterCode());
        values.put(StreetsTable.COLUMN_STREET_NUMBER, streets.getstreetNum());

        // values.put(StreetsTable.COLUMN_SNO, streets.getSno());
        values.put(StreetsTable.COLUMN_USERNAME, streets.getUserName());
        values.put(StreetsTable.COLUMN_SYSDATE, streets.getSysDate());
        values.put(StreetsTable.COLUMN_GPSLAT, streets.getGpsLat());
        values.put(StreetsTable.COLUMN_GPSLNG, streets.getGpsLng());
        values.put(StreetsTable.COLUMN_GPSDATE, streets.getGpsDT());
        values.put(StreetsTable.COLUMN_GPSACC, streets.getGpsAcc());


         /*   values.put(StreetsTable.COLUMN_SSS, streets.sMtoString());
            values.put(StreetsTable.COLUMN_SCB, streets.sNtoString());
            values.put(StreetsTable.COLUMN_IM, streets.sOtoString());*/

        values.put(StreetsTable.COLUMN_ISTATUS, streets.getiStatus());
    /*
            values.put(StreetsTable.COLUMN_ENTRY_TYPE, streets.getEntryType());
    */
        values.put(StreetsTable.COLUMN_DEVICEID, streets.getDeviceId());
        values.put(StreetsTable.COLUMN_APPVERSION, streets.getAppver());
        values.put(StreetsTable.COLUMN_SYNCED, streets.getSynced());
        values.put(StreetsTable.COLUMN_SYNC_DATE, streets.getSyncDate());

        // Populate ContentValues with data from the Listing object
        values.put(StreetsTable.COLUMN_BL01, streets.bl01);
        values.put(StreetsTable.COLUMN_BL02, streets.bl02);
        values.put(StreetsTable.COLUMN_SST, streets.st01);


        // Insert the row into the Sheets table
        long newRowId;
        try {
            newRowId = db.insertOrThrow(
                    StreetsTable.TABLE_NAME,
                    StreetsTable.COLUMN_NAME_NULLABLE,
                    values);
        } catch (SQLiteException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            newRowId = -1; // Return an error value if insertion fails
        }

        return newRowId;
    }

    public Long addEntryLog(EntryLog entryLog) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntryLogTable.COLUMN_PROJECT_NAME, entryLog.getProjectName());
        values.put(EntryLogTable.COLUMN_UUID, entryLog.getUuid());
        values.put(EntryLogTable.COLUMN_EB_CODE, entryLog.getEbCode());
        values.put(EntryLogTable.COLUMN_HHID, entryLog.getHhid());
        values.put(EntryLogTable.COLUMN_USERNAME, entryLog.getUserName());
        values.put(EntryLogTable.COLUMN_SYSDATE, entryLog.getSysDate());
        values.put(EntryLogTable.COLUMN_IStatus, entryLog.getIStatus());
        values.put(EntryLogTable.COLUMN_IStatus96x, entryLog.getIStatus96x());
        values.put(EntryLogTable.COLUMN_ENTRY_TYPE, entryLog.getEntryType());
        values.put(EntryLogTable.COLUMN_ENTRY_DATE, entryLog.getEntryDate());
        values.put(EntryLogTable.COLUMN_DEVICEID, entryLog.getDeviceId());
        values.put(EntryLogTable.COLUMN_SYNCED, entryLog.getSynced());
        values.put(EntryLogTable.COLUMN_SYNC_DATE, entryLog.getSyncDate());
        values.put(EntryLogTable.COLUMN_APPVERSION, entryLog.getAppver());

        long newRowId;
        newRowId = db.insertOrThrow(
                EntryLogTable.TABLE_NAME,
                EntryLogTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }



    //UPDATE in DB
    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = ListingTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.listings.getId())};

        return db.update(ListingTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateListingColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = ListingTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.listings.getId())};

        return db.update(ListingTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateStreetColumn(String column, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = StreetsTable._ID + " = ?";
        String[] selectionArgs = { String.valueOf(MainApp.streets.getId()) };

        int count = db.update(
                StreetsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public int updatesEntryLogColumn(String column, String value, String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = EntryLogTable._ID + " =? ";
        String[] selectionArgs = {id};

        return db.update(EntryLogTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }








    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingTable.COLUMN_ISTATUS, MainApp.listings.getiStatus());

        // Which row to update, based on the ID
        String selection = ListingTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.listings.getId())};

        return db.update(ListingTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Functions that dealing with table data
    public boolean doLogin(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalArgumentException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause = UsersTable.COLUMN_USERNAME + "=? ";
        String[] whereArgs = {username};
        String groupBy = null;
        String having = null;
        String orderBy = UsersTable.COLUMN_ID + " ASC";

        Users loggedInUser = new Users();
        c = db.query(
                UsersTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            loggedInUser = new Users().hydrate(c);

        }
        boolean countCheck = c.getCount() > 0;
        if (c != null && !c.isClosed()) {
            c.close();
        }

        if (checkPassword(password, loggedInUser.getPassword())) {
            MainApp.user = loggedInUser;
            MainApp.selectedDistrict = loggedInUser.getDist_id();
            MainApp.selectedLHW = String.valueOf(loggedInUser.getUserID());
            return countCheck;
        } else {
            return false;
        }
    }



    public ArrayList<Listing> getFormsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ListingTable._ID,
                ListingTable.COLUMN_UID,
                ListingTable.COLUMN_SYSDATE,
                ListingTable.COLUMN_USERNAME,
                ListingTable.COLUMN_ISTATUS,
                ListingTable.COLUMN_SYNCED,

        };
        String whereClause = ListingTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
        String groupBy = null;
        String having = null;
        String orderBy = ListingTable.COLUMN_ID + " ASC";
        ArrayList<Listing> allListings = new ArrayList<>();

            c = db.query(
                    ListingTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
        while (c.moveToNext()) {
            Listing forms = new Listing();
            forms.setId(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ID)));
            forms.setUid(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_UID)));
            forms.setSysDate(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE)));
            forms.setUserName(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_USERNAME)));
            allListings.add(forms);
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        return allListings;
    }


    public ArrayList<Cursor> getDatabaseManagerData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    public int syncversionApp(JSONArray VersionList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        long count = 0;

        JSONObject jsonObjectVersion = ((JSONArray) VersionList.getJSONObject(0).get("elements")).getJSONObject(0);


        String appPath = jsonObjectVersion.getString("outputFile");
        String versionCode = jsonObjectVersion.getString("versionCode");
        String versionName = jsonObjectVersion.getString("versionName");
        String applicationId = VersionList.getJSONObject(0).getString("applicationId");


        // Get the application's package name

        String packageName = context.getPackageName();
        Log.d(TAG, "syncversionApp(applicationId): " + applicationId);
        Log.d(TAG, "syncversionApp(packageName): " + packageName);


        if (applicationId.equals(packageName)) {

            MainApp.editor.putString("outputFile", jsonObjectVersion.getString("outputFile"));
            MainApp.editor.putString("versionCode", jsonObjectVersion.getString("versionCode"));
            MainApp.editor.putString("versionName", jsonObjectVersion.getString("versionName") + ".");
            MainApp.editor.putString("applicationId", jsonObjectVersion.getString("applicationId") + ".");
            MainApp.editor.apply();
            count++;
          /*  VersionApp Vc = new VersionApp();
            Vc.sync(jsonObjectVersion);

            ContentValues values = new ContentValues();

            values.put(VersionTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }*/
        }
        return (int) count;
    }

    public int syncClusters(JSONArray clusters) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableContracts.ClusterTable.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < clusters.length(); i++) {

            JSONObject jsonObjectDist = clusters.getJSONObject(i);

            Cluster cluster = new Cluster();
            cluster.sync(jsonObjectDist);
            ContentValues values = new ContentValues();

            values.put(ClusterTable.COLUMN_DISTRICT_ID, cluster.getDistId());
            values.put(ClusterTable.COLUMN_CLUSTER_CODE, cluster.getClusterCode());
            values.put(ClusterTable.COLUMN_DIST_NAME, cluster.getDistName());
            values.put(ClusterTable.COLUMN_AREA, cluster.getArea());

            long rowID = db.insertOrThrow(ClusterTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }


        return insertCount;
    }

    public int syncAppUser(JSONArray userList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < userList.length(); i++) {

            JSONObject jsonObjectUser = userList.getJSONObject(i);

            Users user = new Users();
            user.sync(jsonObjectUser);
            ContentValues values = new ContentValues();

            values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
            values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
            values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
            values.put(UsersTable.COLUMN_ENABLED, user.getEnabled());
            values.put(UsersTable.COLUMN_ISNEW_USER, user.getNewUser());
            values.put(UsersTable.COLUMN_PWD_EXPIRY, user.getPwdExpiry());
            values.put(UsersTable.COLUMN_DESIGNATION, user.getDesignation());
            values.put(UsersTable.COLUMN_DISTRICT_ID, user.getDist_id());
            long rowID = db.insertOrThrow(UsersTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }


        db.close();

        db.close();

        return insertCount;
    }

    //get UnSyncedTables
   /* public JSONArray getUnsyncedListing() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = ListingTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                ListingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            */

    /**
     * WorkManager Upload
     * /*Listing fc = new Listing();
     * allFC.add(fc.Hydrate(c));
     *//*
            Log.d(TAG, "getUnsyncedFormHH: " + c.getCount());
            Listing listing = new Listing();
            allForms.put(listing.hydrate(c).toJSONObject());


        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        Log.d(TAG, "getUnsyncedFormHH: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormHH: " + allForms);
        return allForms;
    }*/
    public JSONArray getUnsyncedListing() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = ListingTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable._ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                ListingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Listing fc = new Listing();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormHH: " + c.getCount());
            Listing listing = new Listing();
            allForms.put(listing.hydrate(c).toJSONObject());


        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        Log.d(TAG, "getUnsyncedFormHH: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormHH: " + allForms);
        return allForms;
    }

    public JSONArray getUnsyncedStreets() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = StreetsTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = StreetsTable._ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                StreetsTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Streets fc = new Streets();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormHH: " + c.getCount());
            Streets streets = new Streets();
            allForms.put(streets.hydrate(c).toJSONObject());


        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        Log.d(TAG, "getUnsyncedFormHH: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormHH: " + allForms);
        return allForms;
    }


    public boolean checkHHFormStatus(String uid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = ListingTable.COLUMN_UID + "=? AND " +
                ListingTable.COLUMN_ISTATUS + " !=? ";

        String[] whereArgs = {uid, _EMPTY_ };

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " ASC";

        Listing listing = null;

        c = db.query(
                ListingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy,
                "1"// The sort order
        );

        int cCount = c.getCount();

        return cCount > 0;
    }

    public Cluster checkCluster(String clusterCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = ClusterTable.COLUMN_CLUSTER_CODE + "=? ";

        String[] whereArgs = {clusterCode};

        String groupBy = null;
        String having = null;

        String orderBy = ClusterTable.COLUMN_ID + " ASC";

        Cluster cluster = null;

        c = db.query(
                ClusterTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy,
                "1"// The sort order
        );

        // Check if any rows were returned
        if (c != null && c.moveToFirst()) {
            // Extract data from the cursor into a Cluster object
            cluster = new Cluster().hydrate(c);
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        return cluster;
    }

    public JSONArray getUnsyncedEntryLog() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = EntryLogTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = EntryLogTable.COLUMN_ID + " ASC";

        JSONArray all = new JSONArray();
        c = db.query(
                EntryLogTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedEntryLog: " + c.getCount());
            EntryLog entryLog = new EntryLog();
            all.put(entryLog.Hydrate(c).toJSONObject());
        }
        Log.d(TAG, "getUnsyncedEntryLog: " + all.toString().length());
        Log.d(TAG, "getUnsyncedEntryLog: " + all);
        return all;
    }


    //update SyncedTables
    public void updateSyncedListing(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "updateSyncedListing: " + id);
// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingTable.COLUMN_SYNCED, true);
        values.put(ListingTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ListingTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                ListingTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //update SyncedTables
    public void updateSyncedStreets(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "updateSyncedStreets: " + id);
// New value for one column
        ContentValues values = new ContentValues();
        values.put(StreetsTable.COLUMN_SYNCED, true);
        values.put(StreetsTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = StreetsTable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                StreetsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedFamilyMember(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG, "updateSyncedForms: " + id);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FamilyMembersTable.COLUMN_SYNCED, true);
        values.put(FamilyMembersTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FamilyMembersTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FamilyMembersTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedChildren(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_SYNCED, true);
        values.put(ChildTable.COLUMN_SYNC_DATE, new Date().toString());
        String where = ChildTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                ChildTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedEntryLog(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntryLogTable.COLUMN_SYNCED, true);
        values.put(EntryLogTable.COLUMN_SYNC_DATE, new Date().toString());
        String where = EntryLogTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                EntryLogTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    public Listing getFormByPsuhhid(String ebCode, String hhid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = ListingTable.COLUMN_CLUSTER_CODE + "=? AND " +
                ListingTable.COLUMN_HHID + " =? ";

        String[] whereArgs = {ebCode, hhid};

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " ASC";

        Listing listing = null;
            c = db.query(
                    ListingTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
        while (c.moveToNext()) {
            listing = new Listing().hydrate(c);
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        return listing;
    }


    public List<Listing> getFormsByCluster(String cluster) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause = ListingTable.COLUMN_CLUSTER_CODE + " = ? ";
        String[] whereArgs = new String[]{cluster};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                ListingTable.COLUMN_ID + " ASC";

        List<Listing> allFC = new ArrayList<>();
            c = db.query(
                    ListingTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Listing fc = new Listing();
                fc.setId(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ID)));
                fc.setUid(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_UID)));
                fc.setSysDate(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE)));
                fc.setDistrictID(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_DISTRICT_ID)));

             //   fc.setSno(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SNO)));
                fc.setiStatus(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        return allFC;
    }

    public List<Listing> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause = ListingTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " DESC";

        List<Listing> allFC = new ArrayList<>();
            c = db.query(
                    ListingTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Listing fc = new Listing();
                fc.setId(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ID)));
                fc.setUid(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_UID)));
                fc.setSysDate(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE)));
                fc.setDistrictID(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_CLUSTER_CODE)));
                fc.setiStatus(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        if (c != null) {
            c.close();
        }
        if (db != null) {
            db.close();
        }
        return allFC;
    }


    public List<Listing> getPendingForms() {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;
        String whereClause = ListingTable.COLUMN_ISTATUS + " = ?";
        String[] whereArgs = new String[]{""};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " DESC";

        List<Listing> allFC = new ArrayList<>();
            c = db.query(
                    ListingTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Listing fc = new Listing();
                fc.setId(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ID)));
                fc.setUid(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_UID)));
                fc.setSysDate(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE)));
                fc.setDistrictID(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_DISTRICT_ID)));

               // fc.setSno(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SNO)));
                fc.setiStatus(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndexOrThrow(ListingTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return allFC;
    }


    public int updatePassword(String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN_PASSWORD, hashedPassword);
        values.put(UsersTable.COLUMN_ISNEW_USER, "");

        String selection = UsersTable.COLUMN_USERNAME + " =? ";
        String[] selectionArgs = {MainApp.user.getUserName()};

        return db.update(UsersTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int getMembersByUUID(String UUID) {
        String countQuery = "SELECT  * FROM " + FamilyMembersTable.TABLE_NAME + " WHERE " + FamilyMembersTable.COLUMN_UUID + " = '" + UUID + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getChildrenPhotoCheck(String UID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME +
                " WHERE " + ChildTable.COLUMN_UUID + " = '" + UID +
                "' AND " + ChildTable.COLUMN_CSTATUS + " = '1' " +
                " AND (" + ChildTable.COLUMN_SIM + " NOT LIKE '%\"frontFileName\":\"\"%' " +
                " OR " + ChildTable.COLUMN_SIM + " NOT LIKE '%\"backFileName\":\"\"%') ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getChildrenCardCheck(String UID) {
        String countQuery = "SELECT  * FROM " + ChildTable.TABLE_NAME +
                " WHERE " + ChildTable.COLUMN_UUID + " = '" + UID +
                "' AND " + ChildTable.COLUMN_CSTATUS + " = '1' " +
                " AND " + ChildTable.COLUMN_SIM + " LIKE '%\"im01\":\"1\"%' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //get UnSyncedTables
    public JSONArray getUnlockedUnsyncedFormHH() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        //whereClause = null;
        whereClause = ListingTable.COLUMN_SYNCED + " = '9' AND " +
                ListingTable.COLUMN_ISTATUS + "!= ''";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                ListingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Listing fc = new Listing();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormHH: " + c.getCount());
            Listing listing = new Listing();
            allForms.put(listing.hydrate(c).toJSONObject());


        }

        c.close();
        db.close();

        Log.d(TAG, "getUnsyncedFormHH: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormHH: " + allForms);
        return allForms;
    }



    public Listing getFormByUid(String uid) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        Boolean distinct = false;
        String tableName = ListingTable.TABLE_NAME;
        String[] columns = null;
        String whereClause = ListingTable.COLUMN_UID + "= ? ";
        String[] whereArgs = {uid};
        String groupBy = null;
        String having = null;
        String orderBy = ListingTable.COLUMN_SYSDATE + " ASC";
        String limitRows = "1";

        c = db.query(
                distinct,       // Distinct values
                tableName,      // The table to query
                columns,        // The columns to return
                whereClause,    // The columns for the WHERE clause
                whereArgs,      // The values for the WHERE clause
                groupBy,        // don't group the rows
                having,         // don't filter by row groups
                orderBy,
                limitRows
        );

        Listing listing = new Listing();
        while (c.moveToNext()) {
            listing = (new Listing().hydrate(c));
        }

        c.close();
        db.close();
        return listing;

    }


    public int syncUnlocked(JSONArray list) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingTable.COLUMN_SYNCED, "9");
        values.put(ListingTable.COLUMN_SYNC_DATE, "");
        values.put(ListingTable.COLUMN_ISTATUS, "");
        String where = ListingTable.COLUMN_UID + " = ? AND " +
                ListingTable.COLUMN_SYSDATE + " = ? ";
        int insertCount = 0;

        for (int i = 0; i < list.length(); i++) {

            JSONObject json = list.getJSONObject(i);

            // Removed '_x' from Unlocked UID
            String originalUID = json.getString(ListingTable.COLUMN_UID).split("_")[0];
            String[] whereArgs = {originalUID, json.getString(ListingTable.COLUMN_SYSDATE)};
            int rowID = db.update(
                    ListingTable.TABLE_NAME,
                    values,
                    where,
                    whereArgs);
            if (rowID != -1) insertCount++;
        }

        db.close();

        // Open all linked tables using Forms UID received from server
        syncUnlockedChildren(list);


        return insertCount;
    }

    // Call sync function for all linked tables sync function inside main table's sync function (syncUnlockedForms())
    public int syncUnlockedChildren(JSONArray list) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_SYNCED, "9");
        values.put(ChildTable.COLUMN_SYNC_DATE, "");
        String where = ChildTable.COLUMN_UUID + " = ? AND " +
                ChildTable.COLUMN_SYSDATE + " = ? AND " +
                ChildTable.COLUMN_SYNCED + " !='9'";
        int insertCount = 0;

        for (int i = 0; i < list.length(); i++) {

            JSONObject json = list.getJSONObject(i);

            //Remove '_x' from Unlocked UID
            //IMPORTANT: Getting "UID" field and "SYSDATE" from FORMS json
            String originalUID = json.getString(ListingTable.COLUMN_UID).split("_")[0];
            String[] whereArgs = {originalUID, json.getString(ListingTable.COLUMN_SYSDATE)};

            int rowID = db.update(
                    ChildTable.TABLE_NAME,
                    values,
                    where,
                    whereArgs);
            if (rowID != -1) insertCount++;
        }

        db.close();
        db.close();

        return insertCount;
    }

    public Listing getFormByKNO() throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;

        Boolean distinct = false;
        String tableName = ListingTable.TABLE_NAME;
        String[] columns = null;
        String whereClause = ListingTable.COLUMN_CLUSTER_CODE + "= ? AND " +
                ListingTable.COLUMN_HHID + "= ? ";
        String[] whereArgs = {MainApp.selectedLHW, MainApp.selectedHousehold};
        String groupBy = null;
        String having = null;
        String orderBy = ListingTable.COLUMN_SYSDATE + " ASC";
        String limitRows = "1";

        c = db.query(
                distinct,       // Distinct values
                tableName,      // The table to query
                columns,        // The columns to return
                whereClause,    // The columns for the WHERE clause
                whereArgs,      // The values for the WHERE clause
                groupBy,        // don't group the rows
                having,         // don't filter by row groups
                orderBy,
                limitRows
        );

        Listing listing = new Listing();
        while (c.moveToNext()) {
            listing = (new Listing().hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        return listing;

    }
    
  

    public Listing getFormBYUID(String uid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = TableContracts.ListingTable.COLUMN_UID + "=?";

        String[] whereArgs = {uid};

        String groupBy = null;
        String having = null;

        String orderBy = ListingTable.COLUMN_ID + " ASC";

        Listing listingByUID = new Listing();
        c = db.query(
                ListingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            listingByUID = new Listing().hydrate(c);

        }
        if (c != null) {
            c.close();
        }

        return listingByUID;
    }


   /* public String getDistrictName(String dist_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = TableContracts.TableDistricts.COLUMN_DISTRICT_ID + "=?";

        String[] whereArgs = {dist_id};

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.TableDistricts.COLUMN_ID + " ASC";

        Districts districts = new Districts();
        c = db.query(
                TableContracts.TableDistricts.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            districts = new Districts().hydrate(c);

        }
        if (c != null) {
            c.close();
        }

        return districts.getDistrictName();    }*/

    public List<Streets> getStreetsByCluster(String clusterCode) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor c = null;
        String[] columns = null;

        String whereClause = StreetsTable.COLUMN_CLUSTER_CODE + " =? ";

        String[] whereArgs = {clusterCode};

        String groupBy = null;
        String having = null;

        String orderBy = StreetsTable.COLUMN_CLUSTER_CODE + " ASC";

            List<Streets> e = new ArrayList<>();
            c = db.query(
                    StreetsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy
            );
            while (c.moveToNext()) {
                e.add(new Streets().hydrate(c));

            }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return e;


    }

    public int maxStreetNumber(String clusterCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        int maxStreetNumber = 0;

        // Define the query to get max street number for the given cluster code
        String query = "SELECT MAX(" + StreetsTable.COLUMN_STREET_NUMBER + ") AS " + StreetsTable.COLUMN_STREET_NUMBER +
                " FROM " + StreetsTable.TABLE_NAME +
                " WHERE " + StreetsTable.COLUMN_CLUSTER_CODE + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{clusterCode});

        if (cursor.moveToFirst()) {
            String maxStreetNumberStr = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_STREET_NUMBER));
            if (maxStreetNumberStr != null) {
                maxStreetNumber = Integer.parseInt(maxStreetNumberStr);
            } else {
                maxStreetNumber = 0;
            }
            cursor.close();
        } else {
            maxStreetNumber = 0;
        }

        return maxStreetNumber;
    }
}
