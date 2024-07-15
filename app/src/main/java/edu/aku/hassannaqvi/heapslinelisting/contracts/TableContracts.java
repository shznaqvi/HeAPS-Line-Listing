package edu.aku.hassannaqvi.heapslinelisting.contracts;

import android.provider.BaseColumns;

public class TableContracts {


    public static abstract class FamilyMembersTable implements BaseColumns {
        public static final String TABLE_NAME = "FamilyMember";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_LHW_CODE = "lhwCode";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
        public static final String COLUMN_KHANDAN_NO = "kno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_INDEXED = "indexed";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_SA1 = "sA1";
        public static final String COLUMN_SB1 = "sB1";
        public static final String COLUMN_SC1 = "sC1";
        public static final String COLUMN_SD1 = "sD1";
        public static final String COLUMN_SE1 = "sE1";
        public static final String COLUMN_SF1 = "sF1";
        public static final String COLUMN_SG1 = "sG1";
        public static final String COLUMN_SH1 = "sH1";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
       // public static final String COLUMN_ISTATUS = "istatus";
    }
    public static abstract class ListingTable implements BaseColumns {
        public static final String TABLE_NAME = "Listing";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_STUID = "_stuid";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
        public static final String COLUMN_CLUSTER_CODE = "clusterCode";
        public static final String COLUMN_STREET_NUMBER = "streetNum";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_GPSLAT = "xlt";
        public static final String COLUMN_GPSLNG = "xlg";
        public static final String COLUMN_GPSPRO = "xpro";
        public static final String COLUMN_GPSDATE = "xdt";
        public static final String COLUMN_GPSACC = "xac";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "iStatus";
        public static final String COLUMN_DEVICEID = "deviceid";

        public static final String COLUMN_SBG = "sBG";
        public static final String COLUMN_SHH = "sHH";

    }

    public static abstract class StreetsTable implements BaseColumns {
        public static final String TABLE_NAME = "Streets";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_BL01 = "bl01";
        public static final String COLUMN_BL02 = "bl02";
        public static final String COLUMN_ST01 = "st01";
        public static final String COLUMN_ST02 = "st02";
        public static final String COLUMN_ST03 = "st03";
        public static final String COLUMN_ST04 = "st04";
        public static final String COLUMN_ST05 = "st05";
        public static final String COLUMN_ST06 = "st06";
        public static final String COLUMN_ST07 = "st07";
        public static final String COLUMN_ST08 = "st08";
        public static final String COLUMN_ST0901 = "st0901";
        public static final String COLUMN_ST0902 = "st0902";
        public static final String COLUMN_ST0903 = "st0903";
        public static final String COLUMN_ST0904 = "st0904";
        public static final String COLUMN_ST0905 = "st0905";
        public static final String COLUMN_ST0906 = "st0906";
        public static final String COLUMN_ST0907 = "st0907";
        public static final String COLUMN_ST0908 = "st0908";
        public static final String COLUMN_ST0909 = "st0909";
        public static final String COLUMN_ST0910 = "st0910";
        public static final String COLUMN_ST0911 = "st0911";
        public static final String COLUMN_ST0912 = "st0912";
        public static final String COLUMN_ST0913 = "st0913";
        public static final String COLUMN_ST0914 = "st0914";
        public static final String COLUMN_ST10 = "st10";
        public static final String COLUMN_ST11 = "st11";
        public static final String COLUMN_ST12 = "st12";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
        public static final String COLUMN_CLUSTER_CODE = "clusterCode";
        public static final String COLUMN_STREET_NUMBER = "streetNum";

        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_USERNAME = "userName";
        public static final String COLUMN_SYSDATE = "sysDate";
        public static final String COLUMN_DEVICEID = "deviceId";
        public static final String COLUMN_APPVERSION = "appver";
        public static final String COLUMN_END_TIME = "endTime";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "syncDate";
        public static final String COLUMN_ENTRY_TYPE = "entryType";
        public static final String COLUMN_GPSLAT = "gpsLat";
        public static final String COLUMN_GPSLNG = "gpsLng";
        public static final String COLUMN_GPSDATE = "gpsDT";
        public static final String COLUMN_GPSACC = "gpsAcc";
        public static final String COLUMN_ISTATUS = "iStatus";
        public static final String COLUMN_ISTATUS96x = "iStatus96x";
    }

    public static abstract class EntryLogTable implements BaseColumns {
        public static final String TABLE_NAME = "EntryLog";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_EB_CODE = "ebCode";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_ENTRY_DATE = "entryDate";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_ENTRY_TYPE = "entry_type";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_IStatus = "iStatus";
        public static final String COLUMN_IStatus96x = "iStatus96x";
    }




    public static abstract class UsersTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "AppUser";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_LHWID = "lhwid";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "passwordEnc";
        public static final String COLUMN_FULLNAME = "full_name";
        public static final String COLUMN_DESIGNATION = "designation";
        public static final String COLUMN_ENABLED = "enabled";
        public static final String COLUMN_ISNEW_USER = "isNewUser";
        public static final String COLUMN_PWD_EXPIRY = "pwdExpiry";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
    }

/*    public static abstract class VillagesTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "villages";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_COUNTRY = "clusterNo";
        public static final String COLUMN_CCODE = "geoarea";
        public static final String COLUMN_PROVINCE = "distId";
        public static final String COLUMN_PROVCODE = "provcode";
        public static final String COLUMN_DISTRICT_NAME = "districtName";
        public static final String COLUMN_DCODE = "dcode";
        public static final String COLUMN_TEHSIL_NAME = "tehsilName";
        public static final String COLUMN_TCODE = "tcode";
        public static final String COLUMN_UC_NAME = "ucName";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_VILLAGE = "village";
        public static final String COLUMN_VCODE = "vcode";
        public static final String COLUMN_PSUCODE = "psucode";
    }*/


    public static abstract class ClusterTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "Clusters";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
        public static final String COLUMN_DIST_NAME = "districtName";
        public static final String COLUMN_AREA = "area";

        public static final String COLUMN_CLUSTER_CODE = "clusterCode";

    }

    public static abstract class RandomHHTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "Randomised";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RANDOMDT = "randDT";
        public static final String COLUMN_LUID = "UID";
        public static final String COLUMN_EB_CODE = "a102c";
        public static final String COLUMN_STRUCTURE_NO = "hh03";
        public static final String COLUMN_FAMILY_EXT_CODE = "hh07";
        public static final String COLUMN_HH_NO = "hh";
        public static final String COLUMN_HH_HEAD = "hh08";
        public static final String COLUMN_CONTACT = "hh09";
        public static final String COLUMN_HH_SELECTED_STRUCT = "hhss";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_TAB_NO = "tabNo";
        public static String COLUMN_DISTRICT_ID = "dist_id";
    }


    public static abstract class ChildTable implements BaseColumns {
        public static final String TABLE_NAME = "Children";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_EB_CODE = "ebCode";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CSTATUS = "cstatus";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_GPSLAT = "xlt";
        public static final String COLUMN_GPSLNG = "xlg";
        public static final String COLUMN_GPSDATE = "xdt";
        public static final String COLUMN_GPSACC = "xac";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_SCH = "sch";
        public static final String COLUMN_SCB = "scb";
        public static final String COLUMN_SIM = "sim";

        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
    }


    public static abstract class TableLhw implements BaseColumns {

        public static final String TABLE_NAME = "lhw";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_HF_CODE = "hfcode";
        public static final String COLUMN_LHW_NAME = "lhw_name";
        public static final String COLUMN_LHW_CODE = "lhw_code";

    }

    public static abstract class TableHealthFacilities implements BaseColumns {
        public static final String TABLE_NAME = "HealthFacility";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_DISTRICT_ID = "dist_id";
        public static final String COLUMN_HF_CODE = "hfcode";
        public static final String COLUMN_HF_NAME = "hf_name";
    }

    public static abstract class TableDistricts implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "district";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DISTRICT_NAME = "districtName";
        public static final String COLUMN_DISTRICT_ID = "dist_id";

    }

    public static abstract class VersionTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "versionApp";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_VERSION_PATH = "elements";
        public static final String COLUMN_VERSION_CODE = "versionCode";
        public static final String COLUMN_VERSION_NAME = "versionName";
        public static final String COLUMN_PATH_NAME = "outputFile";
        public static final String SERVER_URI = "output-metadata.json";

    }
}
