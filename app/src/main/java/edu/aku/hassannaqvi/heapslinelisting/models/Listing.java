package edu.aku.hassannaqvi.heapslinelisting.models;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.selectedCluster;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.selectedDistrict;

import android.database.Cursor;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.heapslinelisting.BR;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ListingTable;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;

public class Listing extends BaseObservable implements Observable {

    final String TAG = "Listing";




    // FIELD VARIABLES for BL group (BL01 - BL02)
    public String bl01 = _EMPTY_;
    public String bl02 = _EMPTY_;
    public String bl03 = _EMPTY_;
    public String bl04 = _EMPTY_;

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
    public String bg10 = _EMPTY_;

    public String hh11 = _EMPTY_;
    public String hh12 = _EMPTY_;
    public String hh12a = _EMPTY_;
    public String hh12b = _EMPTY_;
    public String hh12c = _EMPTY_;
    public String hh13 = _EMPTY_;
    public String hh1301 = _EMPTY_;
    public String hh1302 = _EMPTY_;
    public String hh13a = _EMPTY_;
    public String hh14 = _EMPTY_;
    public String hh1401 = _EMPTY_;
    public String hh1402 = _EMPTY_;
    public String hh14a = _EMPTY_;
    public String hh12d = _EMPTY_;
    public String hh12d1 = _EMPTY_;
    public String hh12d2 = _EMPTY_;
    public String hh12e = _EMPTY_;
    public String hh15 = _EMPTY_;

    transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    // APP VARIABLES
    String projectName = "PROJECT_NAME";
    String id = _EMPTY_;
    String uid = _EMPTY_;
    String userName = _EMPTY_;
    String sysDate = _EMPTY_;
    String districtID = _EMPTY_;
    String districtName = _EMPTY_;
    String clusterCode = _EMPTY_;
    String streetNum = _EMPTY_;
    String area = _EMPTY_;
    String hhid = _EMPTY_;
    String tabNo = _EMPTY_;
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
    String iStatus = _EMPTY_;
    String iStatus96x = _EMPTY_;

    public Listing() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
    }

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

    public void populateMeta() {
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());  // Ensure this is properly set in your application
        setDeviceId(MainApp.deviceid);  // Ensure this is properly set in your application
        setAppver(MainApp.appInfo.getAppVersion());      // Ensure this is properly set in your application
        setProjectName(PROJECT_NAME); // Ensure this is properly set in your application
        setDistrictID(selectedDistrict); // Ensure this is properly set in your application
        setClusterCode(selectedCluster.getClusterCode()); // Ensure this is properly set in your application
        setDistrictName(selectedCluster.getDistName()); // Ensure this is properly set in your application
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
        notifyPropertyChanged(BR.districtID);
    }

    // StreetNum
    @Bindable
    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
        notifyPropertyChanged(BR.streetNum);
    }
    
    // ClusterCode
    @Bindable
    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
        notifyPropertyChanged(BR.clusterCode);
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

    @Bindable
    public String getBl04() {
        return bl04;
    }

    public void setBl04(String bl04) {
        this.bl04 = bl04;
        notifyPropertyChanged(BR.bl04);
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
        notifyPropertyChanged(BR.bg06);
    }

    // bg07
    @Bindable
    public String getBg07() {
        return bg07;
    }

    public void setBg07(String bg07) {
        this.bg07 = bg07;
        notifyPropertyChanged(BR.bg07);
    }

    // bg08
    @Bindable
    public String getBg08() {
        return bg08;
    }

    public void setBg08(String bg08) {
        this.bg08 = bg08;
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

    // bg10
    @Bindable
    public String getBg10() {
        return bg10;
    }

    public void setBg10(String bg10) {
        this.bg10 = bg10;
        notifyPropertyChanged(BR.bg10);
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

    // HH13
    @Bindable
    public String getHh13() {
        return hh13;
    }

    public void setHh13(String hh13) {
        this.hh13 = hh13;
        notifyPropertyChanged(BR.hh13);
    }

    // HH1301
    @Bindable
    public String getHh1301() {
        return hh1301;
    }

    public void setHh1301(String hh1301) {
        this.hh1301 = hh1301;
        notifyPropertyChanged(BR.hh1301);
    }

    // HH1302
    @Bindable
    public String getHh1302() {
        return hh1302;
    }

    public void setHh1302(String hh1302) {
        this.hh1302 = hh1302;
        notifyPropertyChanged(BR.hh1302);
    }

    // HH13a
    @Bindable
    public String getHh13a() {
        return hh13a;
    }

    public void setHh13a(String hh13a) {
        this.hh13a = hh13a;
        notifyPropertyChanged(BR.hh13a);
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

    // HH1401
    @Bindable
    public String getHh1401() {
        return hh1401;
    }

    public void setHh1401(String hh1401) {
        this.hh1401 = hh1401;
        notifyPropertyChanged(BR.hh1401);
    }

    // HH1402
    @Bindable
    public String getHh1402() {
        return hh1402;
    }

    public void setHh1402(String hh1402) {
        this.hh1402 = hh1402;
        notifyPropertyChanged(BR.hh1402);
    }

    // HH14a
    @Bindable
    public String getHh14a() {
        return hh14a;
    }

    public void setHh14a(String hh14a) {
        this.hh14a = hh14a;
        notifyPropertyChanged(BR.hh14a);
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

    // HH12d1
    @Bindable
    public String getHh12d1() {
        return hh12d1;
    }

    public void setHh12d1(String hh12d1) {
        this.hh12d1 = hh12d1;
        notifyPropertyChanged(BR.hh12d1);
    }

    // HH12d2
    @Bindable
    public String getHh12d2() {
        return hh12d2;
    }

    public void setHh12d2(String hh12d2) {
        this.hh12d2 = hh12d2;
        notifyPropertyChanged(BR.hh12d2);
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

    // HH15
    @Bindable
    public String getHh15() {
        return hh15;
    }

    public void setHh15(String hh15) {
        this.hh15 = hh15;
        notifyPropertyChanged(BR.hh15);
    }

    public Listing Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_UID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_PROJECT_NAME));
        this.districtID = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_DISTRICT_ID));
        this.clusterCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_CLUSTER_CODE));
        this.hhid = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_HHID));

        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_SYNC_DATE));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_GPSACC));

        // BL (1-2)
        this.bl01 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BL01));
        this.bl02 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BL02));



        // BG (1-10)
        this.bg01 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG01));
        this.bg02 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG02));
        this.bg03 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG03));
        this.bg04 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BG04));
        this.bg05 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BG05));
        this.bg06 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BG06));
        this.bg07 = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.ListingTable.COLUMN_BG07));
        this.bg08 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG08));
        this.bg09 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG09));
        this.bg10 = cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_BG10));

        return this;
    }



    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();


        // Common fields
        json.put(ListingTable.COLUMN_ID, this.id);
        json.put(TableContracts.ListingTable.COLUMN_UID, this.uid);
        json.put(TableContracts.ListingTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(ListingTable.COLUMN_DISTRICT_ID, this.districtID);
        json.put(ListingTable.COLUMN_CLUSTER_CODE, this.clusterCode);
        json.put(TableContracts.ListingTable.COLUMN_HHID, this.hhid);
        json.put(TableContracts.ListingTable.COLUMN_USERNAME, this.userName);
        json.put(TableContracts.ListingTable.COLUMN_SYSDATE, this.sysDate);
        json.put(ListingTable.COLUMN_DEVICEID, this.deviceId);
        json.put(ListingTable.COLUMN_ISTATUS, this.iStatus);
        json.put(ListingTable.COLUMN_SYNCED, this.synced);
        json.put(TableContracts.ListingTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(TableContracts.ListingTable.COLUMN_APPVERSION, this.appver);
        json.put(TableContracts.ListingTable.COLUMN_GPSLAT, this.gpsLat);
        json.put(ListingTable.COLUMN_GPSLNG, this.gpsLng);
        json.put(ListingTable.COLUMN_GPSDATE, this.gpsDT);
        json.put(TableContracts.ListingTable.COLUMN_GPSACC, this.gpsAcc);


        // Line listing variables (BG group)
        json.put(ListingTable.COLUMN_BG01, this.bg01);
        json.put(TableContracts.ListingTable.COLUMN_BG02, this.bg02);
        json.put(ListingTable.COLUMN_BG03, this.bg03);
        json.put(TableContracts.ListingTable.COLUMN_BG04, this.bg04);
        json.put(ListingTable.COLUMN_BG05, this.bg05);
        json.put(TableContracts.ListingTable.COLUMN_BG06, this.bg06);
        json.put(TableContracts.ListingTable.COLUMN_BG07, this.bg07);
        json.put(TableContracts.ListingTable.COLUMN_BG08, this.bg08);
        json.put(TableContracts.ListingTable.COLUMN_BG09, this.bg09);
        json.put(TableContracts.ListingTable.COLUMN_BG10, this.bg10);

        // Line listing variables (BL group)
        json.put(TableContracts.ListingTable.COLUMN_BL01, this.bl01);
        json.put(TableContracts.ListingTable.COLUMN_BL02, this.bl02);

        return json;
    }

    public void initListing(Cluster selectedCluster) {
        this.setDistrictID(selectedCluster.getClusterCode());
        this.setDistrictName(selectedCluster.getDistName());
        this.setClusterCode(selectedCluster.getClusterCode());
    }

    public String sBGtoString() throws JSONException {
        Log.d(TAG, "sBGtoString: ");
        JSONObject json = new JSONObject();

        json.put("bg01", bg01)
                .put("bg02", bg02)
                .put("bg03", bg03)
                .put("bg04", bg04)
                .put("bg05", bg05)
                .put("bg06", bg06)
                .put("bg07", bg07)
                .put("bg08", bg08)
                .put("bg09", bg09)
                .put("bg10", bg10);

        return json.toString();
    }

    public String sHHtoString() throws JSONException {
        Log.d(TAG, "sHHtoString: ");
        JSONObject json = new JSONObject();

        json.put("hh11", hh11)
                .put("hh12", hh12)
                .put("hh12a", hh12a)
                .put("hh12b", hh12b)
                .put("hh12c", hh12c)
                .put("hh13", hh13)
                .put("hh1301", hh1301)
                .put("hh1302", hh1302)
                .put("hh13a", hh13a)
                .put("hh14", hh14)
                .put("hh1401", hh1401)
                .put("hh1402", hh1402)
                .put("hh14a", hh14a)
                .put("hh12d", hh12d)
                .put("hh12d1", hh12d1)
                .put("hh12d2", hh12d2)
                .put("hh12e", hh12e)
                .put("hh15", hh15);

        return json.toString();
    }
}