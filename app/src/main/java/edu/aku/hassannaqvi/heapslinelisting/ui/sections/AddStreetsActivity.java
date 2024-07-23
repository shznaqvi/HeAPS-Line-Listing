package edu.aku.hassannaqvi.heapslinelisting.ui.sections;

import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.selectedCluster;
import static edu.aku.hassannaqvi.heapslinelisting.core.MainApp.streets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityAddStreetsBinding;
import edu.aku.hassannaqvi.heapslinelisting.models.Streets;
import edu.aku.hassannaqvi.heapslinelisting.ui.TakePhoto;

public class AddStreetsActivity extends AppCompatActivity {

    ActivityAddStreetsBinding bi;
    private DatabaseHelper db;
    private int maxstreet = 0;
    private int PhotoSerial = 1;
    // Declare ActivityResultLauncher
    private final ActivityResultLauncher<Intent> takePhotoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String fileName = result.getData().getStringExtra("FileName");
                    if (fileName != null) {
                        // Update the file name in the EditText
                        EditText st11EditText = findViewById(R.id.st11);
                        st11EditText.setText(fileName);

                        // Update the PhotoSerial and UI
                        PhotoSerial++;
                        bi.st11.setText(bi.st11.getText() + String.valueOf(PhotoSerial) + " - " + fileName + ";\r\n");
                        Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Photo not saved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApp) getApplication()).resetInactivityTimer();

        MainApp.setLocale(this);

        setTheme(MainApp.langRTL ? MainApp.selectedLanguage == 1 ? R.style.AppThemeUrdu : R.style.AppThemeSindhi : R.style.AppThemeEnglish1);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_streets);

        streets = new Streets();
        bi.setStreets(streets);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;


        maxstreet = db.maxStreetNumber(selectedCluster.getClusterCode());
        maxstreet++;
        streets.setstreetNum(String.valueOf(maxstreet));

        bi.streetnum.setText(MainApp.listings.getClusterCode() + "-" + String.format("%02d", maxstreet));
        //   if (MainApp.superuser)
        //         bi.btnContinue.setText("Review Next");
    }

    private boolean insertNewRecord() {
        if (!streets.getUid().equals("") || MainApp.superuser) return true;
        streets.populateMeta();
        long rowId = 0;
        try {
            rowId = db.addStreet(streets);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        streets.setId(String.valueOf(rowId));
        if (rowId > 0) {
            streets.setUid(streets.getDeviceId() + streets.getId());
            db.updateStreetColumn(TableContracts.StreetsTable.COLUMN_UID, streets.getUid());
            try {
                db.updateStreetColumn(TableContracts.StreetsTable.COLUMN_SST, streets.sSTtoString());
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "JSONException(Listing): ", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


   /* private boolean updateDB() {
        if (MainApp.superuser) return true;

        int updcount = 0;
        try {
            updcount = db.updateStreetColumn(TableContracts.StreetsTable.COLUMN_SA1, streets.sA1toString());
        } catch (JSONException e) {
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/


    public void btnContinue() {
        if (!formValidation()) return;
        if (!insertNewRecord()) return;

        Intent returnIntent = new Intent();
        //  returnIntent.putExtra("requestCode", requestCode);
        setResult(RESULT_OK, returnIntent);
        finish();

      /*  // saveDraft();
        if (updateDB()) {
          *//*  setResult(RESULT_OK);
            Intent i;
            i = new Intent(this, SectionB1Activity.class).putExtra("complete", true).setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(i);
            finish();*//*

            if (streets.getA110().equals("1")) {
                Intent i;
                i = new Intent(this, SectionB1Activity.class).putExtra("complete", true).setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(i);
                finish();
            } else {
                Intent returnIntent = new Intent();
                //  returnIntent.putExtra("requestCode", requestCode);
                setResult(RESULT_OK, returnIntent);
                finish();
            }*/

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.mainGrp);
    }


    private boolean formValidationEnd() {
        return Validator.emptyCheckingContainer(this, bi.mainGrp);
    }


    public void btnEnd() {
        Intent returnIntent = new Intent();
        //  returnIntent.putExtra("requestCode", requestCode);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
        /*finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));*/
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, TakePhoto.class);

        // Adjust Identification Information to uniquely identify every photo and link to form
        intent.putExtra("picID", bi.streetnum.getText().toString() + "_" + PhotoSerial);

        // Provide information for which photo is being taken like ChildName
        intent.putExtra("forInfo", "Street# " + streets.getstreetNum());

        //    intent.putExtra("picView", bi.streetnum.getText().toString() + "_" + PhotoSerial);
        takePhotoLauncher.launch(intent); // Launch the activity using ActivityResultLauncher

    }


    @Override
    public void onBackPressed() {

        // Allow BackPress
        super.onBackPressed();
        setResult(RESULT_CANCELED);

        // Dont Allow BackPress
        // Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();

    }
}