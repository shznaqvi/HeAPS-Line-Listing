package edu.aku.hassannaqvi.heapslinelisting.ui.sections;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.hhid;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.listings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import net.sqlcipher.database.SQLiteException;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityFamilyListingBinding;


public class FamilyListingActivity extends AppCompatActivity {
    private static final String TAG = "FamilyListingActivity";
    ActivityFamilyListingBinding bi;
    String st = "";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_family_listing);
        bi.setCallback(this);
        bi.setListings(MainApp.listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
        listings.setHHClear();

        MainApp.hhid++;

        bi.btnEnd.setVisibility(MainApp.hhid == 1 ? View.GONE : View.VISIBLE);

//        bi.hhid.setText("HFP-" + MainApp.listings.getHh01() + "\n" + MainApp.selectedTab + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));


        Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();

        bi.fl02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                //Cluster—Street—Structure—Floors—Apartment—Houehold

                String floor = listings.getFl01();
                String apartment = listings.getFl02();
                MainApp.civilID2 = MainApp.civilID + "-" + floor + "-" + apartment + "-" + String.format("%02d", hhid) + MainApp.listings.getBg08();

                bi.hhid.setText(MainApp.civilID2);
                bi.hhid.setVisibility(View.VISIBLE);


            }
        });


    }

    private boolean updateDB() {
        long updcount = 0;
        try {
            updcount = db.updateListingColumn(TableContracts.ListingTable.COLUMN_SHH, MainApp.listings.sHHtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db_form + e.getMessage());
            Toast.makeText(this, R.string.upd_db_form + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount > 0) return true;
        else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }




    public void btnContinue(View view) {
        if (formValidation()) {

            if (!insertNewRecord()) return;


            Intent i = null;
            if (MainApp.listings.getHh15().equals("1")) {
                i = new Intent(this, FamilyListingActivity.class);
                //MainApp.hhid = 0;

            } else {
                i = new Intent(this, AddStructureActivity.class);
            }

            startActivity(i);

            Intent returnIntent = new Intent();
            //  returnIntent.putExtra("requestCode", requestCode);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else
            Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();


    }

    private boolean insertNewRecord() {

        MainApp.listings.populateMeta();
        long rowId = 0;
        try {
            rowId = db.addListing(MainApp.listings);
        } catch (JSONException | SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        MainApp.listings.setId(String.valueOf(rowId));
        if (rowId > 0) {
            MainApp.listings.setUid(MainApp.listings.getDeviceId() + MainApp.listings.getId());
            db.updateListingColumn(TableContracts.ListingTable.COLUMN_UID, MainApp.listings.getUid());
            try {
                db.updateListingColumn(TableContracts.ListingTable.COLUMN_SBG, MainApp.listings.sBGtoString());
                db.updateListingColumn(TableContracts.ListingTable.COLUMN_SHH, MainApp.listings.sHHtoString());
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

    public void btnEnd(View view) {
        bi.hh11.setText("Deleted");


        if (insertNewRecord()) {
            finish();
            startActivity(new Intent(this, AddStructureActivity.class));
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }


    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) return false;

        if (!bi.hh12.getText().toString().equals("")) {
            if (Integer.parseInt(bi.hh12c.getText().toString()) > Integer.parseInt(bi.hh12b.getText().toString())) {
                Validator.emptyCustomTextBox(this, bi.hh12c, "Total maried women cannot be more than Total Females");
                return false;
            } else if (MainApp.listings.getHh12().equals("0")) {
                Validator.emptyCustomTextBox(this, bi.hh12, "Total members cannot be 0");
                return false;
            }
        }

        if (!bi.hh12e.getText().toString().equals("")) {
            if (Integer.parseInt(bi.hh12e.getText().toString()) > Integer.parseInt(bi.hh12b.getText().toString())) {
                Validator.emptyCustomTextBox(this, bi.hh12e, "Total pregnant women cannot be more than Total Females");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }


}
