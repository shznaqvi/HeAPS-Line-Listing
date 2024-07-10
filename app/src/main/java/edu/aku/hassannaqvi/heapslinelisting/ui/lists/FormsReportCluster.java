package edu.aku.hassannaqvi.heapslinelisting.ui.lists;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.adapters.ListingAdapter;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityFormsReportBinding;
import edu.aku.hassannaqvi.heapslinelisting.models.Listing;

public class FormsReportCluster extends AppCompatActivity {
    DatabaseHelper db;
    List<Listing> fc;
    String sysdateToday = new SimpleDateFormat("dd-MM-yy").format(new Date());
    ActivityFormsReportBinding bi;
    private RecyclerView.Adapter formsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_forms_report);
        bi.setCusterReport(this);
        //recyclerView = findViewById(R.id.fc_recycler_view);
        setSupportActionBar(bi.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        bi.fcRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        bi.fcRecyclerView.setLayoutManager(layoutManager);
        bi.clusterFilter.setVisibility(View.VISIBLE);
        db = MainApp.appInfo.dbHelper;
        fc = db.getFormsByCluster("0000000");

        // specify an adapter (see also next example)
        formsAdapter = new ListingAdapter(fc, this);
        bi.fcRecyclerView.setAdapter(formsAdapter);
    }

    public void filterForms(View view) {
        Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        fc = db.getFormsByCluster(bi.clusterFilter.getText().toString());
        formsAdapter = new ListingAdapter(fc, this);
        formsAdapter.notifyDataSetChanged();
        bi.fcRecyclerView.setAdapter(formsAdapter);

    }
}