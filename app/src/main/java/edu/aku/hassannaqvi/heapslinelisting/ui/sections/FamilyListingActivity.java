package edu.aku.hassannaqvi.heapslinelisting.ui.sections;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

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


        bi.btnEnd.setVisibility(MainApp.hhid == 1 ? View.GONE : View.VISIBLE);

//        bi.hhid.setText("HFP-" + MainApp.listings.getHh01() + "\n" + MainApp.selectedTab + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));
        bi.hhid.setText(MainApp.listings.getDistrictID() + "-" + MainApp.listings.getClusterCode() + "-" + String.format("%02d", MainApp.listings.getStreetNum() + "\n" + MainApp.listings.getTabNo()+MainApp.maxStructure));
        bi.hhid.setVisibility(View.VISIBLE);        Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();


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
        if (!formValidation()) return;
        if (!updateDB())  return;
            finish();


}


    public void btnEnd(View view) {
        bi.hh11.setText("Deleted");
        bi.hh14a.setText("Deleted");
        bi.hh13a.setText("00");

        if (updateDB()) {
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
