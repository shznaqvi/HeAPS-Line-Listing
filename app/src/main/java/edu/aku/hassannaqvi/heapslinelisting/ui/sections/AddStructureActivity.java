package edu.aku.hassannaqvi.heapslinelisting.ui.sections;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.editor;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.listings;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.selectedCluster;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityAddStructureBinding;
import edu.aku.hassannaqvi.heapslinelisting.models.Streets;
import edu.aku.hassannaqvi.heapslinelisting.ui.MainActivity;

public class AddStructureActivity extends AppCompatActivity {

    private static final String TAG = "AddStructureActivity";
    ActivityAddStructureBinding bi;
    String st = "";
    private DatabaseHelper db;

    ActivityResultLauncher<Intent> addStreetLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        populateSpinner();
                        /*if (!MainApp.streets.getUid().equals("")) {
                         *//*  MainApp.streetList.add(MainApp.streets);
                            MainApp.streetCount++;*//*

                        }*/
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(AddStructureActivity.this, "No street added.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_structure);
        bi.setCallback(this);
        bi.setListings(listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
       /* setupSkips();
        setGPS();*/
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
        listings.setBGClear();
        listings.setHHClear();
        populateSpinner();
        MainApp.maxstreet = db.maxStreetNumber(selectedCluster.getClusterCode());
        MainApp.maxStructure++;
        MainApp.hhid = 0;
        MainApp.totalFloors = 0;
        MainApp.currentFloor = 1;

//        bi.bg07.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                if (bi.bg0702.isChecked()) {
//                    bi.fldGrpCVBg08.setVisibility(View.GONE);
//                    bi.fldGrpCVbg10.setVisibility(View.GONE);
//                    listings.setBg07(_EMPTY_);
//                    bi.bg08.clearCheck();
//                    listings.setBg10(_EMPTY_);
//                } else {
//                    bi.fldGrpCVBg08.setVisibility(View.VISIBLE);
//                    bi.fldGrpCVbg10.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    private void populateSpinner() {
        // Example list of streets with names and codes
        //List<Streets> streetsList = new ArrayList<>();

        // Add more streets as needed

        List<String> streetNames = new ArrayList<>();
        List<String> streetNum = new ArrayList<>();

        streetNames.add("Select Street...");  // Default selection prompt
        streetNum.add("");  // Empty code for default prompt

        try {
            MainApp.streetsList = db.getStreetsByCluster(MainApp.selectedCluster.getClusterCode());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "ProcessStart: JSONException(Streets): " + e.getMessage());
            Toast.makeText(this, "JSONException(Streets): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Populate streetNames and streetCodes from your list
        for (Streets street : MainApp.streetsList) {
            streetNames.add("Street #" + street.getstreetNum());
            streetNum.add(street.getstreetNum());
        }

        // Create an ArrayAdapter using the streetNames list
        ArrayAdapter<String> streetAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, streetNames);

        // Specify the layout to use when the list of choices appears
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        bi.street.setAdapter(streetAdapter);

        bi.street.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (bi.street.getSelectedItemPosition() != 0) {
                    MainApp.streets = MainApp.streetsList.get(bi.street.getSelectedItemPosition() - 1);
                    listings.populateMeta();
                    listings.setStructureNo(String.format("%03d", MainApp.maxStructure));


                    //Cluster—Street—Structure—Floors—Apartment—Houehold

                    String cluster = selectedCluster.getClusterCode();
                    String street = String.format("%02d", Integer.parseInt(listings.getStreetNum()));
                    String structure = listings.getTabNo() + String.format("%03d", MainApp.maxStructure);

                    MainApp.civilID = cluster + "-" + street + "-" + structure;
                    listings.setFullid(MainApp.civilID);
                    // set label in layout
                    bi.hhid.setText(MainApp.civilID);
                    //bi.hhid.setText(listings.getClusterCode() + "-" + String.format("%02d", Integer.parseInt(streetNum.get(bi.street.getSelectedItemPosition()))) + "\n" + listings.getTabNo() + String.format("%03d", MainApp.maxStructure));
                    bi.hhid.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void btnContinue() {
        if (formValidation()) {
            MainApp.structureType = listings.getBg08().equals("1") ? "H" : listings.getBg08().equals("1") ? "B" : "";
            if (bi.bg0608.isChecked() || bi.bg0609.isChecked()) {
                MainApp.maxStructure--;
                //listings.setBl06("");
            }
            editor.putString(MainApp.selectedCluster.getClusterCode(), MainApp.maxStructure + "|" + MainApp.selectedTab);
            editor.apply();

            Intent i;
            if (listings.getBg06().equals("8") || listings.getBg06().equals("9")) {
                if (!insertNewRecord()) return;
                i = new Intent(this, MainActivity.class);

            } else if (listings.getBg07().equals("1")) {
                i = new Intent(this, FamilyListingActivity.class);
                //MainApp.hhid = 0;
                MainApp.totalFloors = Integer.parseInt(bi.bg09a.getText().toString());

            } else {
                if (!insertNewRecord()) return;
                i = new Intent(this, AddStructureActivity.class);

            }

            startActivity(i);

        }
    }

    private boolean updateDB() {
        if (MainApp.superuser) return true;

        db = MainApp.appInfo.getDbHelper();
        long updcount = 0;
        try {
            updcount = db.updatesFormColumn(TableContracts.ListingTable.COLUMN_SBG, listings.sBGtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db + e.getMessage());
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount > 0) return true;
        else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void btnEnd() {
        Intent returnIntent = new Intent();
        //  returnIntent.putExtra("requestCode", requestCode);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
        /*finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));*/
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.mainGrp);
    }

    private boolean insertNewRecord() {
        listings.populateMeta();
        long rowId = 0;
        try {
            rowId = db.addListing(listings);
        } catch (JSONException | SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        listings.setId(String.valueOf(rowId));
        if (rowId > 0) {
            listings.setUid(listings.getDeviceId() + listings.getId());
            db.updateListingColumn(TableContracts.ListingTable.COLUMN_UID, listings.getUid());
            int updCount = 0;
            try {
                updCount = db.updateListingColumn(TableContracts.ListingTable.COLUMN_SBG, listings.sBGtoString());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "JSONException(Listing): ", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (updCount > 0) {

                editor.putString(MainApp.selectedCluster.getClusterCode(), MainApp.maxStructure + "|" + MainApp.selectedTab);
                editor.apply();

                return true;
            }

            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void addStreet() {
        Intent intent = new Intent(this, AddStreetsActivity.class);
        addStreetLauncher.launch(intent);
    }



    @Override
    public void onBackPressed() {

        // Allow BackPress
/*        super.onBackPressed();
        setResult(RESULT_CANCELED);
finish();*/
        // Dont Allow BackPress
        super.onBackPressed();
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();

    }
}