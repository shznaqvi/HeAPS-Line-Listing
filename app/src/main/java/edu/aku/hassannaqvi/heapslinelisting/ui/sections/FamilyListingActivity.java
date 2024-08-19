package edu.aku.hassannaqvi.heapslinelisting.ui.sections;


import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.hhid;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.listings;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Check if the extras are set
        boolean hasFloorExtra = intent.hasExtra("floor");
        boolean hasAptExtra = intent.hasExtra("apt");

        bi = DataBindingUtil.setContentView(this, R.layout.activity_family_listing);
        bi.setCallback(this);
        bi.setListings(MainApp.listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
        listings.setHHClear();

        MainApp.hhid++;
        listings.setHhid(String.valueOf(MainApp.hhid));


        bi.fl01.setEnabled(false);
        bi.fl02.setEnabled(false);
        listings.setFl01(String.valueOf(MainApp.currentFloor));
        listings.setFl02(String.valueOf(MainApp.currentApartment));

        bi.btnEnd.setVisibility(MainApp.hhid == 1 ? View.GONE : View.VISIBLE);
        //bi.fl01.setHint(String.valueOf(MainApp.currentFloor));
/*        if (hasFloorExtra) {
            listings.setFl01(intent.getStringExtra("floor"));
            bi.fl01.setEnabled(false);
        }
        if (hasAptExtra) {
            listings.setFl02(intent.getStringExtra("apt"));
            bi.fl02.setEnabled(false);
            setID();

        }*/

//        bi.hhid.setText("HFP-" + MainApp.listings.getHh01() + "\n" + MainApp.selectedTab + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));


        Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();

        bi.fl01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                setID();


            }
        });
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
                if (!listings.getFl02().equals("")) {
                    String floor = listings.getFl01();
                    String apartment = listings.getFl02();
                    MainApp.civilID2 = MainApp.civilID + "-" + floor + "-" + apartment + "-" + String.format("%02d", hhid);

                    bi.hhid.setText(MainApp.civilID2);
                    bi.hhid.setVisibility(View.VISIBLE);

                }
            }
        });


    }

    private void setID() {
        //Cluster—Street—Structure—Floors—Apartment—Houehold
        bi.hhid.setVisibility(View.GONE);
        String floor = listings.getFl01();
        bi.fl02.setText("");
        String apartment = listings.getFl02();
        MainApp.civilID2 = MainApp.civilID + "-" + floor + "-" + apartment + "-" + String.format("%02d", hhid);

        bi.hhid.setText(MainApp.civilID2);

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
            listings.setFullid(MainApp.civilID2);

            if (!insertNewRecord()) return;


            Intent i = null;
            // More Households in apartment
            if (MainApp.listings.getHh15().equals("1")) {
                i = new Intent(this, FamilyListingActivity.class);
                i.putExtra("floor", bi.fl01.getText().toString());
                i.putExtra("apt", bi.fl02.getText().toString());
                // MainApp.hhid++; Done in onCreate of this activity

            }
            // More Appartments on the floor
            else if (MainApp.listings.getHh16().equals("1")) {
                MainApp.currentApartment++;
                i = new Intent(this, FamilyListingActivity.class);
                i.putExtra("floor", bi.fl01.getText().toString());

                MainApp.hhid = 0;

            }
            // More Floors in this structure
            else if (MainApp.totalFloors >= ++MainApp.currentFloor) {
                MainApp.currentApartment = 0;

                i = new Intent(this, FamilyListingActivity.class);
                MainApp.hhid = 0;
            } else {
                i = new Intent(this, AddStructureActivity.class);
                MainApp.currentApartment = 0;

                MainApp.hhid = 0;
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
            listings.setStructureNo(String.format("%03d", MainApp.maxStructure));

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
        bi.hh11.setText("0000000");
        if (formValidation()) {

            if (insertNewRecord()) {
                Intent i;
                finish();
                MainApp.currentApartment = 1;

                if (MainApp.totalFloors >= ++MainApp.currentFloor) {
                    i = new Intent(this, FamilyListingActivity.class);
                    MainApp.hhid = 0;
                } else {
                    i = new Intent(this, AddStructureActivity.class);
                    MainApp.hhid = 0;
                }
                startActivity(i);

            } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) return false;

        if (bi.hh10.equals("1")) {
            int totalMem = Integer.parseInt(bi.hh12.getText().toString());
            int totalMale = Integer.parseInt(bi.hh12a.getText().toString());
            int totalFemale = Integer.parseInt(bi.hh12b.getText().toString());
            int totalMWRA = Integer.parseInt(bi.hh12c.getText().toString());
            int totalPreg = Integer.parseInt(bi.hh12d.getText().toString());


            int calTotalMembers = totalMale + totalFemale;

            if (totalMem != calTotalMembers) {
                Validator.emptyCustomTextBox(this, bi.hh12, "Total members do not match Male and Female count");
                return false;
            }

            if (totalMWRA > totalFemale) {
                Validator.emptyCustomTextBox(this, bi.hh12c, "Total MWRA cannot be more than Total Females in the household");
                return false;
            }

            if (totalPreg > totalFemale) {
                Validator.emptyCustomTextBox(this, bi.hh12e, "Total pregnant women cannot be more than Total Females in the household");
                return false;
            }

            if (totalPreg > totalMWRA) {
                Validator.emptyCustomTextBox(this, bi.hh12e, "Total pregnant women cannot be more than Total MWRA");
                return false;
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {

    }


}
