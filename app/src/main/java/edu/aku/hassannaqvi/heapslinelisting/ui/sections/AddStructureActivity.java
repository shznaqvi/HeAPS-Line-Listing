package edu.aku.hassannaqvi.heapslinelisting.ui.sections;



import android.app.Activity;
import android.content.Intent;
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
                        if (!MainApp.streets.getUid().equals("")) {
                            MainApp.streetList.add(MainApp.streets);
                            MainApp.streetCount++;
                            populateSpinner();
                        }
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
        bi.setListing(MainApp.listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
       /* setupSkips();
        setGPS();*/
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        populateSpinner();

        MainApp.maxStructure++;
        MainApp.hhid = 0;


    }

    private void populateSpinner() {
        // Example list of streets with names and codes
        List<Streets> streetsList = new ArrayList<>();

        // Add more streets as needed

        List<String> streetNames = new ArrayList<>();
        List<String> streetNum = new ArrayList<>();

        streetNames.add("Select Street...");  // Default selection prompt
        streetNum.add("");  // Empty code for default prompt

        try {
            streetsList = db.getStreetsByCluster(MainApp.selectedCluster.getClusterCode());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "ProcessStart: JSONException(Streets): " + e.getMessage());
            Toast.makeText(this, "JSONException(Streets): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Populate streetNames and streetCodes from your list
        for (Streets street : streetsList) {
            streetNames.add(street.getSt01());
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

                MainApp.listings.setStreetNum(streetNum.get(bi.street.getSelectedItemPosition()));

                bi.hhid.setText(MainApp.listings.getDistrictID() + "-" + MainApp.listings.getClusterCode() + "-" + String.format("%02d", streetNum.get(bi.street.getSelectedItemPosition())) + "-" + MainApp.listings.getTabNo()+MainApp.maxStructure);
                bi.hhid.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void btnContinue() {
        if (!formValidation()) return;
        if (!insertNewRecord()) return;


        Intent i = null;
        if (MainApp.listings.getBg06().equals("8")|| MainApp.listings.getBg06().equals("9")) {
            i = new Intent(this, MainActivity.class);

        } else if (MainApp.listings.getBg07().equals("1")) {
            i = new Intent(this, FamilyListingActivity.class);
            MainApp.hhid = 0;

        } else {
            i = new Intent(this, AddStructureActivity.class);
        }

        startActivity(i);

        Intent returnIntent = new Intent();
        //  returnIntent.putExtra("requestCode", requestCode);
        setResult(RESULT_OK, returnIntent);
        finish();
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
        if (!MainApp.listings.getUid().equals("") || MainApp.superuser) return true;
        MainApp.listings.populateMeta();
        long rowId = 0;
        try {
            rowId = db.addListing(MainApp.listings);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        MainApp.listings.setId(String.valueOf(rowId));
        if (rowId > 0) {
            MainApp.listings.setUid(MainApp.listings.getDeviceId() + MainApp.listings.getId());
            db.updateStreetColumn(TableContracts.ListingTable.COLUMN_UID, MainApp.listings.getUid());
            try {
                db.updateStreetColumn(TableContracts.ListingTable.COLUMN_SBG, MainApp.listings.sBGtoString());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "JSONException(Listing): ", Toast.LENGTH_SHORT).show();
                return false;
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
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();

    }
}