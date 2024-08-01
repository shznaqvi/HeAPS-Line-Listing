package edu.aku.hassannaqvi.heapslinelisting.models;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.listings;

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
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ListingTable;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;

public class Listing extends BaseObservable implements Observable {

    final String TAG = "Listing";


    // FIELD VARIABLES for Building group (BG01 - BG10)
    public String bg01 = _EMPTY_;
    public String bg02 = _EMPTY_;
    public String bg03 = _EMPTY_;
    public String bg04 = _EMPTY_;
    public String bg05 = _EMPTY_;
    public String bg06 = _EMPTY_;
    public String bg07 = _EMPTY_;
    public String bg08 = _EMPTY_;
    public String bg09 = _EMPTY_;
    public String bg09a = _EMPTY_;
    public String bg10 = _EMPTY_;
    public String bg11 = _EMPTY_;
    public String bg12 = _EMPTY_;

    public String hh01 = _EMPTY_; // interviewer
    public String hh02 = _EMPTY_; // district name
    public String hh03 = _EMPTY_; // area name
    public String hh04 = _EMPTY_; // cluster number
    public String hh05 = _EMPTY_; // street number
    public String hh06 = _EMPTY_; // structure number

    public String fl01 = _EMPTY_;
    public String fl02 = _EMPTY_;
    public String hh10 = _EMPTY_;
    public String hh11 = _EMPTY_;
    public String hh12 = _EMPTY_;
    public String hh12a = _EMPTY_;
    public String hh12b = _EMPTY_;
    public String hh12c = _EMPTY_;
    public String hh12d = _EMPTY_;
    public String hh12e = _EMPTY_;
    public String hh13 = _EMPTY_;


    public String hh14 = _EMPTY_;


    public String hh15 = _EMPTY_; // Household
    public String hh16 = _EMPTY_; // Apartments on the floor


    // APP VARIABLES
    String projectName = PROJECT_NAME;
    String id = _EMPTY_;
    String uid = _EMPTY_;
    String stuid = _EMPTY_;
    String userName = _EMPTY_;
    String sysDate = _EMPTY_;
    String districtID = _EMPTY_;
    String districtName = _EMPTY_;
    String area = _EMPTY_;
    String clusterCode = _EMPTY_;
    String streetNum = _EMPTY_;
    String tabNo = _EMPTY_;
    String structureNo = _EMPTY_;
    String hhid = _EMPTY_;
    String deviceId = _EMPTY_;
    String appver = _EMPTY_;
    String endTime = _EMPTY_;
    String synced = _EMPTY_;
    String syncDate = _EMPTY_;
    String entryType = _EMPTY_;
    String gpsLat = _EMPTY_;
    String gpsLng = _EMPTY_;
    String gpsDT = _EMPTY_;
    String gpsAcc = _EMPTY_;
    String gpsProvider = _EMPTY_;
    String iStatus = _EMPTY_;
    String iStatus96x = _EMPTY_;

    public Listing() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
    }



    public void populateMeta() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());  // Ensure this is properly set in your application
        setDeviceId(MainApp.deviceid);  // Ensure this is properly set in your application
        setAppver(MainApp.appInfo.getAppVersion());      // Ensure this is properly set in your application
        setProjectName(PROJECT_NAME); // Ensure this is properly set in your application
        listings.setUserName(MainApp.user.getUserName());
        listings.setDistrictID(MainApp.selectedDistrict);
        listings.setDistrictName(MainApp.selectedDistrictnName);
        listings.setArea(MainApp.selectedArea);
        listings.setClusterCode(MainApp.selectedCluster.getClusterCode());
        listings.setStreetNum(MainApp.streets.getstreetNum());
        listings.setTabNo(MainApp.selectedTab);
        setGPS();
    }

    private void setGPS() {
        String latitude = MainApp.sharedPref.getString("latitude", "0");
        String longitude = MainApp.sharedPref.getString("longitude", "0");
        String accuracy = String.valueOf(MainApp.sharedPref.getFloat("accuracy", 0));
        long datetime = MainApp.sharedPref.getLong("datetime", 0);
        String provider = MainApp.sharedPref.getString("provider", "");

        // Convert timestamp to formatted date string
        String formattedDateTime = getFormattedDateTime(datetime);

        // Display formatted date time in the db
        setGpsLat(latitude);
        setGpsLng(longitude);
        setGpsAcc(accuracy);
        setGpsDT(formattedDateTime);
        setGpsProvider(provider);
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

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    // DistrictCode
    @Bindable
    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
        this.hh02 = districtID;
        notifyPropertyChanged(BR.districtID);
    }

    // Area
    @Bindable
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
        setHh03(area);
        notifyPropertyChanged(BR.area);
    }

    // StreetNum
    @Bindable
    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
        setHh05(streetNum);
        notifyPropertyChanged(BR.streetNum);
    }

    // ClusterCode
    @Bindable
    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
        setHh04(clusterCode);
        notifyPropertyChanged(BR.clusterCode);
    }

    // StructureNo
    @Bindable
    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
        setHh06(structureNo);
        notifyPropertyChanged(BR.structureNo);
    }

    // Hhid
    @Bindable
    public String getHhid() {
        return hhid;
    }

    public void setHhid(String hhid) {
        this.hhid = hhid;
        notifyPropertyChanged(BR.hhid);
    }
    // DistrictName
    @Bindable
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
        notifyPropertyChanged(BR.districtName);
    }

    // UserName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        setHh01(userName);
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

    // TabNo
    public String getTabNo() {
        return tabNo;
    }

    public void setTabNo(String tabNo) {
        this.tabNo = tabNo;
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

    // GPSProvider
    @Bindable
    public String getGpsProvider() {
        return gpsProvider;
    }

    public void setGpsProvider(String gpsProvider) {
        this.gpsProvider = gpsProvider;
        notifyPropertyChanged(BR.gpsProvider);
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



    // bg01
    @Bindable
    public String getBg01() {
        return bg01;
    }

    public void setBg01(String bg01) {
        this.bg01 = bg01;
        notifyPropertyChanged(BR.bg01);
    }

    // bg02
    @Bindable
    public String getBg02() {
        return bg02;
    }

    public void setBg02(String bg02) {
        this.bg02 = bg02;
        notifyPropertyChanged(BR.bg02);
    }

    // bg03
    @Bindable
    public String getBg03() {
        return bg03;
    }

    public void setBg03(String bg03) {
        this.bg03 = bg03;
        notifyPropertyChanged(BR.bg03);
    }

    // bg04
    @Bindable
    public String getBg04() {
        return bg04;
    }

    public void setBg04(String bg04) {
        this.bg04 = bg04;
        notifyPropertyChanged(BR.bg04);
    }

    // bg05
    @Bindable
    public String getBg05() {
        return bg05;
    }

    public void setBg05(String bg05) {
        this.bg05 = bg05;
        notifyPropertyChanged(BR.bg05);
    }

    // bg06
    @Bindable
    public String getBg06() {
        return bg06;
    }

    public void setBg06(String bg06) {
        this.bg06 = bg06;
        setBg07(bg06.equals("8") || bg06.equals("9") ? "" : bg07);
        notifyPropertyChanged(BR.bg06);
    }

    // bg07
    @Bindable
    public String getBg07() {
        return bg07;
    }

    public void setBg07(String bg07) {
        this.bg07 = bg07;
        setBg08(bg07.equals("1") ? bg08 : "");
        setBg10(bg07.equals("1") ? bg10 : "");
        notifyPropertyChanged(BR.bg07);

    }

    // bg08
    @Bindable
    public String getBg08() {
        return bg08;
    }

    public void setBg08(String bg08) {
        this.bg08 = bg08;
        setBg09(bg08.equals("H") ? bg09 : "");
        notifyPropertyChanged(BR.bg08);
    }

    // bg09
    @Bindable
    public String getBg09() {
        return bg09;
    }

    public void setBg09(String bg09) {
        this.bg09 = bg09;
        notifyPropertyChanged(BR.bg09);
    }

    // bg09a
    @Bindable
    public String getBg09a() {
        return bg09a;
    }

    public void setBg09a(String bg09a) {
        this.bg09a = bg09a;
        notifyPropertyChanged(BR.bg09a);
    }

    // bg10
    @Bindable
    public String getBg10() {
        return bg10;
    }

    public void setBg10(String bg10) {
        this.bg10 = bg10;
        notifyPropertyChanged(BR.bg10);
    }

    @Bindable
    public String getBg12() {
        return bg12;
    }

    public void setBg12(String bg12) {
        this.bg12 = bg12;
        notifyPropertyChanged(BR.bg12);
    }

    @Bindable
    public String getBg11() {
        return bg11;
    }

    public void setBg11(String bg11) {
        this.bg11 = bg11;
        notifyPropertyChanged(BR.bg11);
    }

    @Bindable
    public String getHh01() {
        return hh01;
    }

    public void setHh01(String hh01) {
        this.hh01 = hh01;
        notifyPropertyChanged(BR.hh01);
    }

    @Bindable
    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
        notifyPropertyChanged(BR.hh02);
    }

    @Bindable
    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
        notifyPropertyChanged(BR.hh03);
    }

    @Bindable
    public String getHh04() {
        return hh04;
    }

    public void setHh04(String hh04) {
        this.hh04 = hh04;
        notifyPropertyChanged(BR.hh04);
    }

    @Bindable
    public String getHh05() {
        return hh05;
    }

    public void setHh05(String hh05) {
        this.hh05 = hh05;
        notifyPropertyChanged(BR.hh05);
    }

    @Bindable
    public String getHh06() {
        return hh06;
    }

    public void setHh06(String hh06) {
        this.hh06 = hh06;
        notifyPropertyChanged(BR.hh06);
    }

    // FL01
    @Bindable
    public String getFl01() {
        return fl01;
    }

    public void setFl01(String fl01) {
        this.fl01 = fl01;
        notifyPropertyChanged(BR.fl01);
    }

    // FL02
    @Bindable
    public String getFl02() {
        return fl02;
    }

    public void setFl02(String fl02) {
        this.fl02 = fl02;
        notifyPropertyChanged(BR.fl02);
    }

    // HH10
    @Bindable
    public String getHh10() {
        return hh10;
    }

    public void setHh10(String hh10) {
        this.hh10 = hh10;
        setHh11(hh10.equals("2") ? "" : hh11);
        setHh12(hh10.equals("2") ? "" : hh12);
        setHh12a(hh10.equals("2") ? "" : hh12a);
        setHh12b(hh10.equals("2") ? "" : hh12b);
        setHh12c(hh10.equals("2") ? "" : hh12c);
        setHh12d(hh10.equals("2") ? "" : hh12d);
        setHh12e(hh10.equals("2") ? "" : hh12e);
        setHh13(hh10.equals("2") ? "" : hh13);
        setHh14(hh10.equals("2") ? "" : hh14);
        notifyPropertyChanged(BR.hh10);
    }

    // HH11
    @Bindable
    public String getHh11() {
        return hh11;
    }

    public void setHh11(String hh11) {
        this.hh11 = hh11;
        notifyPropertyChanged(BR.hh11);
    }

    // HH12
    @Bindable
    public String getHh12() {
        return hh12;
    }

    public void setHh12(String hh12) {
        this.hh12 = hh12;
        notifyPropertyChanged(BR.hh12);
    }

    // HH12a
    @Bindable
    public String getHh12a() {
        return hh12a;
    }

    public void setHh12a(String hh12a) {
        this.hh12a = hh12a;
        notifyPropertyChanged(BR.hh12a);
    }

    // HH12b
    @Bindable
    public String getHh12b() {
        return hh12b;
    }

    public void setHh12b(String hh12b) {
        this.hh12b = hh12b;
        notifyPropertyChanged(BR.hh12b);
    }

    // HH12c
    @Bindable
    public String getHh12c() {
        return hh12c;
    }

    public void setHh12c(String hh12c) {
        this.hh12c = hh12c;
        notifyPropertyChanged(BR.hh12c);
    }

    // HH12d
    @Bindable
    public String getHh12d() {
        return hh12d;
    }

    public void setHh12d(String hh12d) {
        this.hh12d = hh12d;
        notifyPropertyChanged(BR.hh12d);
    }

    // HH12e
    @Bindable
    public String getHh12e() {
        return hh12e;
    }

    public void setHh12e(String hh12e) {
        this.hh12e = hh12e;
        notifyPropertyChanged(BR.hh12e);
    }

    // HH13
    @Bindable
    public String getHh13() {
        return hh13;
    }

    public void setHh13(String hh13) {
        this.hh13 = hh13;
        notifyPropertyChanged(BR.hh13);
    }



    // HH14
    @Bindable
    public String getHh14() {
        return hh14;
    }

    public void setHh14(String hh14) {
        this.hh14 = hh14;
        notifyPropertyChanged(BR.hh14);
    }





    // HH15
    @Bindable
    public String getHh15() {
        return hh15;
    }

    public void setHh15(String hh15) {
        this.hh15 = hh15;
        setHh16(hh15.equals('1') ? "" : hh16);
        notifyPropertyChanged(BR.hh15);
    }

    // HH16
    @Bindable
    public String getHh16() {
        return hh16;
    }

    public void setHh16(String hh16) {
        this.hh16 = hh16;
        notifyPropertyChanged(BR.hh16);
    }

    public Listing hydrate(Cursor cursor) throws JSONException {
        if (cursor == null) {
            throw new IllegalArgumentException("Cursor must not be null.");
        }

        this.id = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_UID));
        this.stuid = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_STUID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_PROJECT_NAME));
        this.districtID = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_DISTRICT_ID));
        this.area = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_AREA));
        this.clusterCode = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_CLUSTER_CODE));
        this.streetNum = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_STREET_NUMBER));
        this.structureNo = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_STRUCTURE_NUMBER));
        this.hhid = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_HHID));

        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SYNC_DATE));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSLNG));
        this.gpsProvider = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSPRO));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSACC));

        // Hydrate sHH fields
        String sHHJsonString = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SHH));
        if (sHHJsonString != null) {
            sHHHydrate(sHHJsonString);
        }

        // Hydrate sBG fields
        String sBGJsonString = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SBG));
        if (sBGJsonString != null) {
            sBGHydrate(sBGJsonString);
        }

        return this;
    }


    public void sHHHydrate(String jsonString) throws JSONException {
        Log.d(TAG, "sHHHydrate: " + jsonString);
        if (jsonString != null) {
            JSONObject json = new JSONObject(jsonString);

            this.hh01 = json.getString("hh01");
            this.hh02 = json.getString("hh02");
            this.hh03 = json.getString("hh03");
            this.hh04 = json.getString("hh04");
            this.hh05 = json.getString("hh05");
            this.hh06 = json.getString("hh06");

            // HH (11-15 and subcolumns)
            this.fl01 = json.getString("fl01");
            this.fl02 = json.getString("fl02");
            this.hh10 = json.getString("hh10");
            this.hh11 = json.getString("hh11");
            this.hh12 = json.getString("hh12");
            this.hh12a = json.getString("hh12a");
            this.hh12b = json.getString("hh12b");
            this.hh12c = json.getString("hh12c");
            this.hh13 = json.getString("hh13");

            this.hh14 = json.getString("hh14");

            this.hh12d = json.getString("hh12d");

            this.hh12e = json.getString("hh12e");
            this.hh15 = json.getString("hh15");
            this.hh16 = json.getString("hh16");
        }
    }

    public void sBGHydrate(String jsonString) throws JSONException {
        Log.d(TAG, "sBGHydrate: " + jsonString);
        if (jsonString != null) {
            JSONObject json = new JSONObject(jsonString);

            // BG (1-10)
            this.bg01 = json.getString("bg01");
            this.bg02 = json.getString("bg02");
            this.bg03 = json.getString("bg03");
            this.bg04 = json.getString("bg04");
            this.bg05 = json.getString("bg05");
            this.bg06 = json.getString("bg06");
            this.bg07 = json.getString("bg07");
            this.bg08 = json.getString("bg08");
            this.bg09 = json.getString("bg09");
            this.bg09a = json.getString("bg09a");
            this.bg10 = json.getString("bg10");
            this.bg11 = json.getString("bg11");
            this.bg12 = json.getString("bg12");
        }
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        // Common fields
        json.put(ListingTable.COLUMN_ID, this.id);
        json.put(ListingTable.COLUMN_UID, this.uid);
        json.put(ListingTable.COLUMN_STUID, this.stuid);
        json.put(ListingTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(ListingTable.COLUMN_DISTRICT_ID, this.districtID);
        json.put(ListingTable.COLUMN_AREA, this.area);
        json.put(ListingTable.COLUMN_CLUSTER_CODE, this.clusterCode);
        json.put(ListingTable.COLUMN_STREET_NUMBER, this.streetNum);
        json.put(ListingTable.COLUMN_STRUCTURE_NUMBER, this.structureNo);
        json.put(ListingTable.COLUMN_HHID, this.hhid);
        json.put(ListingTable.COLUMN_USERNAME, this.userName);
        json.put(ListingTable.COLUMN_SYSDATE, this.sysDate);
        json.put(ListingTable.COLUMN_DEVICEID, this.deviceId);
        json.put(ListingTable.COLUMN_ISTATUS, this.iStatus);
        json.put(ListingTable.COLUMN_SYNCED, this.synced);
        json.put(ListingTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(ListingTable.COLUMN_APPVERSION, this.appver);
        json.put(ListingTable.COLUMN_GPSLAT, this.gpsLat);
        json.put(ListingTable.COLUMN_GPSLNG, this.gpsLng);
        json.put(ListingTable.COLUMN_GPSPRO, this.gpsProvider);
        json.put(ListingTable.COLUMN_GPSDATE, this.gpsDT);
        json.put(ListingTable.COLUMN_GPSACC, this.gpsAcc);

        // Convert sHH and sBG groups to JSONObjects
        json.put(ListingTable.COLUMN_SHH, new JSONObject(sHHtoString()));
        json.put(ListingTable.COLUMN_SBG, new JSONObject(sBGtoString()));

        return json;
    }


    public void initListing(Cluster selectedCluster) {
        this.setDistrictID(selectedCluster.getClusterCode());
        this.setDistrictName(selectedCluster.getDistName());
        this.setClusterCode(selectedCluster.getClusterCode());
    }

    public String sHHtoString() throws JSONException {
        Log.d(TAG, "sHHtoString: ");

        JSONObject json = new JSONObject();

        json.put("hh01", hh01);
        json.put("hh02", hh02);
        json.put("hh03", hh03);
        json.put("hh04", hh04);
        json.put("hh05", hh05);
        json.put("hh06", hh06);

        json.put("hh10", hh10);
        json.put("hh11", hh11);
        json.put("fl01", fl01);
        json.put("fl02", fl02);
        json.put("hh12", hh12);
        json.put("hh12a", hh12a);
        json.put("hh12b", hh12b);
        json.put("hh12c", hh12c);
        json.put("hh13", hh13);

        json.put("hh14", hh14);

        json.put("hh12d", hh12d);

        json.put("hh12e", hh12e);
        json.put("hh15", hh15);
        json.put("hh16", hh16);

        return json.toString();
    }

    public String sBGtoString() throws JSONException {
        Log.d(TAG, "sBGtoString: ");

        JSONObject json = new JSONObject();
        json.put("bg01", bg01);
        json.put("bg02", bg02);
        json.put("bg03", bg03);
        json.put("bg04", bg04);
        json.put("bg05", bg05);
        json.put("bg06", bg06);
        json.put("bg07", bg07);
        json.put("bg08", bg08);
        json.put("bg09", bg09);
        json.put("bg09a", bg09a);
        json.put("bg10", bg10);
        json.put("bg11", bg11);
        json.put("bg12", bg12);

        return json.toString();
    }

    public void setBGClear() {
        // BG (1-10)
        setHhid(String.valueOf(MainApp.hhid));

        this.uid = "";
        this.bg01 = "";
        this.bg02 = "";
        this.bg03 = "";
        this.bg04 = "";
        this.bg05 = "";
        this.bg06 = "";
        this.bg07 = "";
        this.bg08 = "";
        this.bg09 = "";
        this.bg09a = "";
        this.bg10 = "";
        this.bg11 = "";
        this.bg12 = "";
    }

    public void setHHClear() {
        // BG (1-10)
        setHhid(String.valueOf(MainApp.hhid));

        this.uid = "";
        this.hh01 = "";
        this.hh02 = "";
        this.hh03 = "";
        this.hh04 = "";
        this.hh05 = "";
        this.hh06 = "";
        this.fl01 = "";
        this.fl02 = "";
        this.hh10 = "";
        this.hh11 = "";
        this.hh12 = "";
        this.hh12a = "";
        this.hh12b = "";
        this.hh12c = "";
        this.hh13 = "";

        this.hh14 = "";

        this.hh12d = "";

        this.hh12e = "";
        this.hh15 = "";
        this.hh16 = "";
    }
}