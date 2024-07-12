package edu.aku.hassannaqvi.heapslinelisting.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityIdentificationBinding;
import edu.aku.hassannaqvi.heapslinelisting.models.Cluster;
import edu.aku.hassannaqvi.heapslinelisting.models.Listing;
import edu.aku.hassannaqvi.heapslinelisting.ui.sections.AddStructureActivity;


public class IdentificationActivity extends AppCompatActivity {

    private static final String TAG = "IdentificationActivity";
    ActivityIdentificationBinding bi;
    private DatabaseHelper db;
    private int c, c1;
    private ArrayList<String> distNames, distCodes, areaNames, areaCodes;
    private String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApp) getApplication()).resetInactivityTimer();

        MainApp.setLocale(this);
        setTheme(MainApp.langRTL ? MainApp.selectedLanguage == 1 ? R.style.AppThemeUrdu : R.style.AppThemeSindhi : R.style.AppThemeEnglish1);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_identification);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;
        MainApp.selectedCluster = new Cluster();
        MainApp.selectedArea = new Listing();
        MainApp.listings = new Listing();
        bi.setListing(MainApp.listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        setSupportActionBar(bi.toolbar);
        populateSpinner();

        // bi.btnContinue.setText(R.string.open_hh_form);
        if (MainApp.superuser)
            bi.btnContinue.setText("Review Listing");
   /*     bi.distName.setText(db.getDistrictName(MainApp.user.getDist_id()));
        bi.lhwName.setText(MainApp.user.getFullname());*/

        MainApp.listings = new Listing();
        //populateSpinner();
        Log.d(TAG, "onCreate(Language): " + Locale.getDefault().getDisplayLanguage());

        bi.clusterCode.addTextChangedListener(new TextWatcher() {
            private boolean isEditing = false;
            private boolean isDeleting = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //  Log.d(TAG, "beforeTextChanged: charSequence-"+charSequence+" i-"+i+ " i1-"+i1 +" i2-"+i2);
                c = charSequence.length();
                isDeleting = i1 > i2;

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //if (isEditing) return;
                isEditing = true;

                c1 = charSequence.length();
                String txt = charSequence.toString();
                Log.d(TAG, "onTextChanged: c-" + c + " c1-" + c1 + "\t\t\tCHAR: " + charSequence);
                Log.d(TAG, "onTextChanged: i-" + i + " i1-" + i1 + " i2-" + i2 + "\t\t\tCHAR: " + charSequence);
                // K-C01-S01-B001-A/H-HH001
                if (!isDeleting) {
                    if (c == 0 && c1 == 1) {
                        Log.d(TAG, "onTextChanged: added K- ");
                        bi.clusterCode.setText(bi.clusterCode.getText().toString() + "-"); // K-
                        bi.clusterCode.setInputType(InputType.TYPE_CLASS_NUMBER);
                   /* bi.clusterCode.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else if (isAlphabeticPosition(position)) {
                    bi.clusterCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);*/
                    } /*else if (c == 3 && c1 == 4) {

                    Log.d(TAG, "onTextChanged: added K-01- ");
                    bi.clusterCode.setText(bi.clusterCode.getText().toString() + "-"); // K-01-
                }else if (c == 6 && c1 == 5) {
                    Log.d(TAG, "onTextChanged: Removed K-01 ");

                    bi.clusterCode.setText(bi.clusterCode.getText().toString().substring(0, 6)); // K-01
                }*/
                } else {
                    if (c == 2 && c1 == 1) {

                        //bi.clusterCode.setText(bi.clusterCode.getText().toString().substring(0, 1)); // K
                        bi.clusterCode.setText(""); // K
                        Log.d(TAG, "onTextChanged: Removed K ");
                        bi.clusterCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                    } else if (c1 > 1 && charSequence.charAt(1) != '-') {

                        txt = txt.charAt(0) + "-" + txt.substring(1);
                        Log.d(TAG, "onTextChanged: txt:  " + txt);
                        bi.clusterCode.setText(txt);
                    } else if (c1 > 4 && charSequence.charAt(4) != '-') {
                        txt = txt.substring(0, 4) + "-" + txt.substring(4);
                        Log.d(TAG, "onTextChanged: txt:  " + txt);

                        bi.clusterCode.setText(txt);
                    }

                }

                bi.clusterCode.setSelection(bi.clusterCode.getText().length());
                isEditing = false;

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    public void checkCluster(View view) {

        bi.fldGrpCVbl04.setVisibility(View.GONE);
        if (!formValidation()) return;

        MainApp.selectedCluster = null;

        MainApp.selectedCluster = db.checkCluster(bi.clusterCode.getText().toString());

        if (MainApp.selectedCluster != null) {
            MainApp.listings.initListing(MainApp.selectedCluster);
            bi.clusterCode.setError(null); // Clear the error
            MainApp.clusterInfo = MainApp.sharedPref.getString(MainApp.selectedCluster.getClusterCode(), "0|0").split("\\|");
            MainApp.maxStructure = Integer.parseInt(MainApp.clusterInfo[0]);

            if (!MainApp.clusterInfo[0].equals("0")) {

                MainApp.listings.setTabNo(MainApp.clusterInfo[1]);
                bi.fldGrpCVbl04.setVisibility(View.GONE);
                bi.buttonsPanel.setVisibility(View.GONE);
                MainApp.selectedTab = MainApp.listings.getTabNo();
            } else {
                bi.bl04.clearCheck();
                MainApp.listings.setTabNo("");
                bi.fldGrpCVbl04.setVisibility(View.VISIBLE);
                bi.buttonsPanel.setVisibility(View.VISIBLE);
            }
        } else {
            bi.bl04.clearCheck();
            MainApp.maxStructure = 0;
            MainApp.selectedTab = "";
            MainApp.selectedCluster = null;
            bi.clusterCode.setError("Invalid Cluster Code");
            bi.fldGrpCVbl04.setVisibility(View.GONE);
            bi.buttonsPanel.setVisibility(View.GONE);
        }
    }


    public void btnContinue(View view) {

        if (!formValidation()) return;
        MainApp.selectedDistrict = distCodes.get(bi.district.getSelectedItemPosition());
        MainApp.listings.populateMeta();
        // MainApp.selectedCluster = bi.clusterCode.getText().toString();  //<= moved to checkCluster()
        //MainApp.selectedHousehold = bi.bl03.getText().toString();

       // if (!formValidation()) return;

        if (bi.fldGrpCVbl04.getVisibility() == View.VISIBLE)
            MainApp.selectedTab = bi.bl0401.isChecked() ? "A" : "B";
        else
            MainApp.selectedTab = MainApp.selectedArea.getTabNo();
        MainApp.listings.setTabNo(MainApp.selectedTab);
        finish();
        startActivity(new Intent(this, AddStructureActivity.class));


    }

    public void btnEnd(View view) {
        finish();
        // startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
    }




    private void populateSpinner() {


        //  Collection<Districts> districts = new ArrayList<>();
        distNames = new ArrayList<>();
        distCodes = new ArrayList<>();

        distNames.add("...");
        distCodes.add("...");
/*
        for (Districts d : districts) {
            distNames.add(d.getDistrictName());
            distCodes.add(d.getDistrictCode());
        }*/
        distNames.add("Karachi");
        distNames.add("Matiari");
        distCodes.add("K");
        distCodes.add("M");
        if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu")) {
            distNames.add("Test");
            distCodes.add("9");
        }
        // Apply the adapter to the spinner
        bi.district.setAdapter(new ArrayAdapter<>(this, R.layout.custom_spinner, distNames));


    }
    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        //  startActivity(new Intent(this, MainActivity.class));
    }

}
