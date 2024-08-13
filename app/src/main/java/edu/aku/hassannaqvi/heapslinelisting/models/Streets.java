package edu.aku.hassannaqvi.heapslinelisting.models;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.selectedCluster;

import android.database.Cursor;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.heapslinelisting.BR;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.StreetsTable;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;

public class Streets extends BaseObservable implements Observable {

    final String TAG = "Sheets";


    // FIELD VARIABLES for BL group (BL01 - BL02)
    public String bl01 = _EMPTY_;
    public String bl02 = _EMPTY_;


    // FIELD VARIABLES for ST group (ST01 - ST12)
    public String st01 = _EMPTY_;
    public String st02 = _EMPTY_;
    public String st0301 = _EMPTY_;
    public String st0302 = _EMPTY_;
    public String st0303 = _EMPTY_;
    public String st0304 = _EMPTY_;
    public String st0305 = _EMPTY_;
    public String st0306 = _EMPTY_;
    public String st0307 = _EMPTY_;
    public String st04 = _EMPTY_;
    public String st05 = _EMPTY_;
    public String st0501 = _EMPTY_;
    public String st0502 = _EMPTY_;
    public String st0503 = _EMPTY_;
    public String st0504 = _EMPTY_;
    public String st0505 = _EMPTY_;
    public String st06 = _EMPTY_;
    public String st0601 = _EMPTY_;
    public String st0602 = _EMPTY_;
    public String st0603 = _EMPTY_;
    public String st0604 = _EMPTY_;
    public String st0605 = _EMPTY_;
    public String st07 = _EMPTY_;
    public String st08 = _EMPTY_;
    // String variables for ST09 options
    public String st0901 = _EMPTY_;
    public String st0902 = _EMPTY_;
    public String st0903 = _EMPTY_;
    public String st0904 = _EMPTY_;
    public String st0905 = _EMPTY_;
    public String st0906 = _EMPTY_;
    public String st0907 = _EMPTY_;
    public String st0908 = _EMPTY_;
    public String st0909 = _EMPTY_;
    public String st0910 = _EMPTY_;
    public String st0911 = _EMPTY_;
    public String st0912 = _EMPTY_;
    public String st0913 = _EMPTY_;
    public String st0914 = _EMPTY_;
    public String st10 = _EMPTY_;
    public String st11 = _EMPTY_;
    public String st12 = _EMPTY_;

    // Additional variables
    public String projectName = PROJECT_NAME;
    public String uid = _EMPTY_;
    public String userName = _EMPTY_;
    public String sysDate = _EMPTY_;
    public String deviceId = _EMPTY_;
    public String appver = _EMPTY_;
    public String endTime = _EMPTY_;
    public String synced = _EMPTY_;
    public String syncDate = _EMPTY_;
    public String entryType = _EMPTY_;
    public String gpsLat = _EMPTY_;
    public String gpsLng = _EMPTY_;
    public String gpsDT = _EMPTY_;
    public String gpsAcc = _EMPTY_;
    public String iStatus = _EMPTY_;
    public String iStatus96x = _EMPTY_;
    String id = _EMPTY_;

    String districtID = _EMPTY_;
    String clusterCode = _EMPTY_;
    String streetNum = _EMPTY_;
/*

    transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }
*/

    public Streets() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void populateMeta() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());  // Ensure this is properly set in your application
        setDeviceId(MainApp.deviceid);  // Ensure this is properly set in your application
        setAppver(MainApp.appInfo.getAppVersion());      // Ensure this is properly set in your application
        setProjectName(projectName); // Ensure this is properly set in your application
        setDistrictID(selectedCluster.getDistId()); // Ensure this is properly set in your application
        setClusterCode(selectedCluster.getClusterCode()); // Ensure this is properly set in your application
        setGPS();
    }

    private void setGPS() {
        String latitude = MainApp.sharedPref.getString("latitude", "0");
        String longitude = MainApp.sharedPref.getString("longitude", "0");
        String accuracy = String.valueOf(MainApp.sharedPref.getFloat("accuracy", 0));
        long datetime = MainApp.sharedPref.getLong("datetime", 0);
        //String provider = MainApp.sharedPref.getString("provider", "");

        // Convert timestamp to formatted date string
        String formattedDateTime = getFormattedDateTime(datetime);

        // Display formatted date time in the db
        setGpsLat(latitude);
        setGpsLng(longitude);
        setGpsAcc(accuracy);
        setGpsDT(formattedDateTime);
    }

    private String getFormattedDateTime(long timestamp) {
        if (timestamp == 0) return "0";
        // Create a SimpleDateFormat object with desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // Convert timestamp to Date object
        Date date = new Date(timestamp);

        // Format the Date object to desired format
        return sdf.format(date);
    }

    // Getters and Setters for basic fields

    // ProjectName
    @Bindable
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        notifyPropertyChanged(BR.projectName);
    }

    // ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // UID
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // DistrictCode
    @Bindable
    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
        this.bl01 = districtID;
        notifyPropertyChanged(BR.districtID);
    }

    // DistrictID
    @Bindable
    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String ClusterCode) {
        this.clusterCode = ClusterCode;
        notifyPropertyChanged(BR.clusterCode);
    }

    // UserName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getstreetNum() {
        return streetNum;
    }

    public void setstreetNum(String streetNum) {
        this.streetNum = streetNum;
        setSt02(streetNum);
    }

    // SysDate
    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    // DeviceId
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    // Appver
    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    // EndTime
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // Synced
    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    // SyncDate
    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    // GPSLat
    @Bindable
    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
        notifyPropertyChanged(BR.gpsLat);
    }

    // GPSLng
    @Bindable
    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
        notifyPropertyChanged(BR.gpsLng);
    }

    // GPSDT
    @Bindable
    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
        notifyPropertyChanged(BR.gpsDT);
    }

    // GPSAcc
    @Bindable
    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
        notifyPropertyChanged(BR.gpsAcc);
    }

    // IStatus
    @Bindable
    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
        notifyPropertyChanged(BR.iStatus);
    }

    // IStatus96x
    @Bindable
    public String getiStatus96x() {
        return iStatus96x;
    }

    public void setiStatus96x(String iStatus96x) {
        this.iStatus96x = iStatus96x;
        notifyPropertyChanged(BR.iStatus96x);
    }

    // BL01
    @Bindable
    public String getBl01() {
        return bl01;
    }

    public void setBl01(String bl01) {
        this.bl01 = bl01;
        notifyPropertyChanged(BR.bl01);
    }

    // BL02
    @Bindable
    public String getBl02() {
        return bl02;
    }

    public void setBl02(String bl02) {
        this.bl02 = bl02;
        notifyPropertyChanged(BR.bl02);
    }

    // ST01
    @Bindable
    public String getSt01() {
        return st01;
    }

    public void setSt01(String st01) {
        this.st01 = st01;
        notifyPropertyChanged(BR.st01);
    }

    // ST02
    @Bindable
    public String getSt02() {
        return st02;
    }

    public void setSt02(String st02) {
        this.st02 = st02;
        notifyPropertyChanged(BR.st02);
    }

    // ST03
    @Bindable
    public String getSt0301() {
        return st0301;
    }

    public void setSt0301(String st0301) {
        this.st0301 = st0301;
        notifyPropertyChanged(BR.st0301);
    }

    @Bindable
    public String getSt0302() {
        return st0302;
    }

    public void setSt0302(String st0302) {
        this.st0302 = st0302;
        notifyPropertyChanged(BR.st0302);
    }

    @Bindable
    public String getSt0303() {
        return st0303;
    }

    public void setSt0303(String st0303) {
        this.st0303 = st0303;
        notifyPropertyChanged(BR.st0303);
    }

    @Bindable
    public String getSt0304() {
        return st0304;
    }

    public void setSt0304(String st0304) {
        this.st0304 = st0304;
        notifyPropertyChanged(BR.st0304);
    }

    @Bindable
    public String getSt0305() {
        return st0305;
    }

    public void setSt0305(String st0305) {
        this.st0305 = st0305;
        notifyPropertyChanged(BR.st0305);
    }

    @Bindable
    public String getSt0306() {
        return st0306;
    }

    public void setSt0306(String st0306) {
        this.st0306 = st0306;
        notifyPropertyChanged(BR.st0306);
    }

    @Bindable
    public String getSt0307() {
        return st0307;
    }

    public void setSt0307(String st0307) {
        this.st0307 = st0307;
        notifyPropertyChanged(BR.st0307);
    }

    // ST04
    @Bindable
    public String getSt04() {
        return st04;
    }

    public void setSt04(String st04) {
        this.st04 = st04;
        notifyPropertyChanged(BR.st04);
    }

    // ST05
    @Bindable
    public String getSt05() {
        return st05;
    }

    public void setSt05(String st05) {
        this.st05 = st05;
        notifyPropertyChanged(BR.st05);
    }

    @Bindable
    public String getSt0501() {
        return st0501;
    }

    public void setSt0501(String st0501) {
        this.st0501 = st0501;
        calculateAndSetAverageSt05();

        notifyPropertyChanged(BR.st0501);
    }

    @Bindable
    public String getSt0502() {
        return st0502;
    }

    public void setSt0502(String st0502) {
        this.st0502 = st0502;
        calculateAndSetAverageSt05();

        notifyPropertyChanged(BR.st0502);
    }

    @Bindable
    public String getSt0503() {
        return st0503;
    }

    public void setSt0503(String st0503) {
        this.st0503 = st0503;
        calculateAndSetAverageSt05();

        notifyPropertyChanged(BR.st0503);
    }

    @Bindable
    public String getSt0504() {
        return st0504;
    }

    public void setSt0504(String st0504) {
        this.st0504 = st0504;
        calculateAndSetAverageSt05();

        notifyPropertyChanged(BR.st0504);
    }

    @Bindable
    public String getSt0505() {
        return st0505;
    }

    public void setSt0505(String st0505) {
        this.st0505 = st0505;
        calculateAndSetAverageSt05();


        notifyPropertyChanged(BR.st0505);
    }

    // ST06 Getters and Setters


    // ST06
    @Bindable
    public String getSt06() {
        return st06;
    }

    public void setSt06(String st06) {
        this.st06 = st06;
        notifyPropertyChanged(BR.st06);
    }

    @Bindable
    public String getSt0601() {
        return st0601;
    }

    public void setSt0601(String st0601) {
        this.st0601 = st0601;
        calculateAndSetAverageSt06();

        notifyPropertyChanged(BR.st0601);
    }

    @Bindable
    public String getSt0602() {
        return st0602;
    }

    public void setSt0602(String st0602) {
        this.st0602 = st0602;
        calculateAndSetAverageSt06();
        notifyPropertyChanged(BR.st0602);
    }

    @Bindable
    public String getSt0603() {
        return st0603;
    }

    public void setSt0603(String st0603) {
        this.st0603 = st0603;
        calculateAndSetAverageSt06();
        notifyPropertyChanged(BR.st0603);
    }

    @Bindable
    public String getSt0604() {
        return st0604;
    }

    public void setSt0604(String st0604) {
        this.st0604 = st0604;
        calculateAndSetAverageSt06();
        notifyPropertyChanged(BR.st0604);
    }

    @Bindable
    public String getSt0605() {
        return st0605;
    }

    public void setSt0605(String st0605) {
        this.st0605 = st0605;
        calculateAndSetAverageSt06();
        notifyPropertyChanged(BR.st0605);
    }

    // ST07
    @Bindable
    public String getSt07() {
        return st07;
    }

    public void setSt07(String st07) {
        this.st07 = st07;
        notifyPropertyChanged(BR.st07);
    }

    // ST08
    @Bindable
    public String getSt08() {
        return st08;
    }

    public void setSt08(String st08) {
        this.st08 = st08;
        notifyPropertyChanged(BR.st08);
    }

    // Getter and setter methods for ST09 variables
    @Bindable
    public String getSt0901() {
        return st0901;
    }

    public void setSt0901(String st0901) {
        this.st0901 = st0901;
        notifyPropertyChanged(BR.st0901);
    }

    @Bindable
    public String getSt0902() {
        return st0902;
    }

    public void setSt0902(String st0902) {
        this.st0902 = st0902;
        notifyPropertyChanged(BR.st0902);
    }

    @Bindable
    public String getSt0903() {
        return st0903;
    }

    public void setSt0903(String st0903) {
        this.st0903 = st0903;
        notifyPropertyChanged(BR.st0903);
    }

    @Bindable
    public String getSt0904() {
        return st0904;
    }

    public void setSt0904(String st0904) {
        this.st0904 = st0904;
        notifyPropertyChanged(BR.st0904);
    }

    @Bindable
    public String getSt0905() {
        return st0905;
    }

    public void setSt0905(String st0905) {
        this.st0905 = st0905;
        notifyPropertyChanged(BR.st0905);
    }

    @Bindable
    public String getSt0906() {
        return st0906;
    }

    public void setSt0906(String st0906) {
        this.st0906 = st0906;
        notifyPropertyChanged(BR.st0906);
    }

    @Bindable
    public String getSt0907() {
        return st0907;
    }

    public void setSt0907(String st0907) {
        this.st0907 = st0907;
        notifyPropertyChanged(BR.st0907);
    }

    @Bindable
    public String getSt0908() {
        return st0908;
    }

    public void setSt0908(String st0908) {
        this.st0908 = st0908;
        notifyPropertyChanged(BR.st0908);
    }

    @Bindable
    public String getSt0909() {
        return st0909;
    }

    public void setSt0909(String st0909) {
        this.st0909 = st0909;
        notifyPropertyChanged(BR.st0909);
    }

    @Bindable
    public String getSt0910() {
        return st0910;
    }

    public void setSt0910(String st0910) {
        this.st0910 = st0910;
        notifyPropertyChanged(BR.st0910);
    }

    @Bindable
    public String getSt0911() {
        return st0911;
    }

    public void setSt0911(String st0911) {
        this.st0911 = st0911;
        notifyPropertyChanged(BR.st0911);
    }

    @Bindable
    public String getSt0912() {
        return st0912;
    }

    public void setSt0912(String st0912) {
        this.st0912 = st0912;
        notifyPropertyChanged(BR.st0912);
    }

    @Bindable
    public String getSt0913() {
        return st0913;
    }

    public void setSt0913(String st0913) {
        this.st0913 = st0913;
        notifyPropertyChanged(BR.st0913);
    }

    @Bindable
    public String getSt0914() {
        return st0914;
    }

    public void setSt0914(String st0914) {
        this.st0914 = st0914;
        notifyPropertyChanged(BR.st0914);
    }

    // ST10
    @Bindable
    public String getSt10() {
        return st10;
    }

    public void setSt10(String st10) {
        this.st10 = st10;
        notifyPropertyChanged(BR.st10);
    }

    // ST11
    @Bindable
    public String getSt11() {
        return st11;
    }

    public void setSt11(String st11) {
        this.st11 = st11;
        notifyPropertyChanged(BR.st11);
    }

    // ST12
    @Bindable
    public String getSt12() {
        return st12;
    }

    public void setSt12(String st12) {
        this.st12 = st12;
        notifyPropertyChanged(BR.st12);
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();


        // Common fields
        json.put(StreetsTable._ID, this.id);
        json.put(StreetsTable.COLUMN_UID, this.uid);
        json.put(StreetsTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(StreetsTable.COLUMN_DISTRICT_ID, this.districtID);
        json.put(StreetsTable.COLUMN_CLUSTER_CODE, this.clusterCode);

        json.put(StreetsTable.COLUMN_USERNAME, this.userName);
        json.put(StreetsTable.COLUMN_SYSDATE, this.sysDate);
        json.put(StreetsTable.COLUMN_DEVICEID, this.deviceId);
        json.put(StreetsTable.COLUMN_ISTATUS, this.iStatus);
//        json.put(StreetsTable.COLUMN_SYNCED, this.synced);
//        json.put(StreetsTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(StreetsTable.COLUMN_APPVERSION, this.appver);
        json.put(StreetsTable.COLUMN_GPSLAT, this.gpsLat);
        json.put(StreetsTable.COLUMN_GPSLNG, this.gpsLng);
        json.put(StreetsTable.COLUMN_GPSDATE, this.gpsDT);
        json.put(StreetsTable.COLUMN_GPSACC, this.gpsAcc);

        // Line listing variables (ST group)
        json.put(StreetsTable.COLUMN_SST, new JSONObject(sSTtoString()));


        // Line listing variables (BL group)
        json.put(StreetsTable.COLUMN_BL01, this.bl01);
        json.put(StreetsTable.COLUMN_BL02, this.bl02);

        return json;
    }

    public Streets hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable._ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_UID));
        this.districtID = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_DISTRICT_ID));
        this.clusterCode = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_CLUSTER_CODE));
        this.streetNum = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_STREET_NUMBER));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_APPVERSION));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_GPSACC));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_SYNC_DATE));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_ISTATUS));
        this.iStatus96x = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_ISTATUS96x));
        this.bl01 = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_BL01));
        this.bl02 = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_BL02));

        sStHydrate(cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_SST)));


        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_PROJECT_NAME));
        this.endTime = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_END_TIME));
        this.entryType = cursor.getString(cursor.getColumnIndexOrThrow(StreetsTable.COLUMN_ENTRY_TYPE));

        return this;
    }

    public void sStHydrate(String jsonString) throws JSONException {
        Log.d(TAG, "sStHydrate: " + jsonString);

        if (jsonString != null) {
            JSONObject json = new JSONObject(jsonString);

            this.st01 = json.optString("st01");
            this.st02 = json.optString("st02");
            this.st0301 = json.optString("st0301", _EMPTY_);
            this.st0302 = json.optString("st0302", _EMPTY_);
            this.st0303 = json.optString("st0303", _EMPTY_);
            this.st0304 = json.optString("st0304", _EMPTY_);
            this.st0305 = json.optString("st0305", _EMPTY_);
            this.st0306 = json.optString("st0306", _EMPTY_);
            this.st0307 = json.optString("st0307", _EMPTY_);
            this.st04 = json.optString("st04");
            this.st05 = json.optString("st05");
            this.st0501 = json.optString("st0501");
            this.st0502 = json.optString("st0502");
            this.st0503 = json.optString("st0503");
            this.st0504 = json.optString("st0504");
            this.st0505 = json.optString("st0505");
            this.st06 = json.optString("st06");
            this.st0601 = json.optString("st0601");
            this.st0602 = json.optString("st0602");
            this.st0603 = json.optString("st0603");
            this.st0604 = json.optString("st0604");
            this.st0605 = json.optString("st0605");
            this.st07 = json.optString("st07");
            this.st08 = json.optString("st08");
            this.st0901 = json.optString("st0901");
            this.st0902 = json.optString("st0902");
            this.st0903 = json.optString("st0903");
            this.st0904 = json.optString("st0904");
            this.st0905 = json.optString("st0905");
            this.st0906 = json.optString("st0906");
            this.st0907 = json.optString("st0907");
            this.st0908 = json.optString("st0908");
            this.st0909 = json.optString("st0909");
            this.st0910 = json.optString("st0910");
            this.st0911 = json.optString("st0911");
            this.st0912 = json.optString("st0912");
            this.st0913 = json.optString("st0913");
            this.st0914 = json.optString("st0914");
            this.st10 = json.optString("st10");
            this.st11 = json.optString("st11");
            this.st12 = json.optString("st12");
        }
    }

    public String sSTtoString() throws JSONException {
        Log.d(TAG, "toJSONString: ");

        JSONObject json = new JSONObject();

        json.put("st01", st01);
        json.put("st02", st02);
        json.put("st0301", st0301);
        json.put("st0302", st0302);
        json.put("st0303", st0303);
        json.put("st0304", st0304);
        json.put("st0305", st0305);
        json.put("st0306", st0306);
        json.put("st0307", st0307);
        json.put("st04", st04);
        json.put("st05", st05);
        json.put("st0501", st0501);
        json.put("st0502", st0502);
        json.put("st0503", st0503);
        json.put("st0504", st0504);
        json.put("st0505", st0505);
        json.put("st06", st06);
        json.put("st0601", st0601);
        json.put("st0602", st0602);
        json.put("st0603", st0603);
        json.put("st0604", st0604);
        json.put("st0605", st0605);
        json.put("st07", st07);
        json.put("st08", st08);
        json.put("st0901", st0901);
        json.put("st0902", st0902);
        json.put("st0903", st0903);
        json.put("st0904", st0904);
        json.put("st0905", st0905);
        json.put("st0906", st0906);
        json.put("st0907", st0907);
        json.put("st0908", st0908);
        json.put("st0909", st0909);
        json.put("st0910", st0910);
        json.put("st0911", st0911);
        json.put("st0912", st0912);
        json.put("st0913", st0913);
        json.put("st0914", st0914);
        json.put("st10", st10);
        json.put("st11", st11);
        json.put("st12", st12);

        return json.toString();
    }


    private int parseValue(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void calculateAndSetAverageSt05() {
        // Initialize total and count
        int total = 0;
        int count = 0;


        // Calculate total and count non-empty variables
        total += parseValue(st0501);
        count += st0501.isEmpty() ? 0 : 1;
        total += parseValue(st0502);
        count += st0502.isEmpty() ? 0 : 1;
        total += parseValue(st0503);
        count += st0503.isEmpty() ? 0 : 1;
        total += parseValue(st0504);
        count += st0504.isEmpty() ? 0 : 1;
        total += parseValue(st0505);
        count += st0505.isEmpty() ? 0 : 1;

        // Calculate average
        double average = count > 0 ? (double) total / count : 0;

        // Convert average to string and set to st05
        setSt05(String.valueOf(average));
    }

    public void calculateAndSetAverageSt06() {
        // Array of values for st0601 to st0605
        int[] values = {
                parseValue(st0601),
                parseValue(st0602),
                parseValue(st0603),
                parseValue(st0604),
                parseValue(st0605)
        };

        int total = 0;
        int count = 0;

        // Calculate total and count of non-zero values
        for (int value : values) {
            if (value != 0) {  // Only include non-zero values
                total += value;
                count++;
            }
        }

        // Calculate average
        String avgSt06 = count > 0 ? String.valueOf((double) total / count) : "0";

        // Set the average value
        setSt06(avgSt06);
    }


}