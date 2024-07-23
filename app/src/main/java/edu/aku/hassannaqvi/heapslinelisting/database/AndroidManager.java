package edu.aku.hassannaqvi.heapslinelisting.database;

//all required import files

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.Cursor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AndroidManager extends Activity implements OnItemClickListener {

    //in the below line Change the text 'yourCustomSqlHelper' with your custom sqlitehelper class name.
    //Do not change the variable name dbm
    DatabaseHelper dbm;
    // Constants
    private static final int PAGE_SIZE = 10;
    // all global variables
    TableLayout tableLayout;
    LayoutParams tableRowParams;
    HorizontalScrollView hsv;
    ScrollView mainscrollview;
    LinearLayout mainLayout;
    TextView tvmessage;
    Button previous;
    Button next;
    Spinner select_table;
    TextView tv;
    IndexInfo info = new IndexInfo();
    private LinearLayout secondrow;
    private LinearLayout thirdrow;
    private Spinner spinnertable;
    private TextView help;
    private EditText customquerytext;
    private Button submitQuery;
    private Button customQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the DatabaseHelper instance
        dbm = new DatabaseHelper(this);

        // Initialize the main ScrollView and LinearLayout
        mainscrollview = new ScrollView(this);
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainscrollview.addView(mainLayout);

        // Set the main content view
        setContentView(mainscrollview);

        // Create and configure the first row layout with a TextView and Spinner
        LinearLayout firstrow = new LinearLayout(this);
        firstrow.setPadding(0, 10, 0, 20);
        LinearLayout.LayoutParams firstrowlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        firstrowlp.weight = 1;

        TextView maintext = new TextView(this);
        maintext.setText("Select Table");
        maintext.setTextSize(22);
        maintext.setLayoutParams(firstrowlp);

        select_table = new Spinner(this);
        select_table.setLayoutParams(firstrowlp);

        firstrow.addView(maintext);
        firstrow.addView(select_table);
        mainLayout.addView(firstrow);

        // Initialize the HorizontalScrollView for table content
        hsv = new HorizontalScrollView(this);
        tableLayout = new TableLayout(this);
        tableLayout.setHorizontalScrollBarEnabled(true);
        hsv.addView(tableLayout);

        // Create and configure the second row layout for record count
        secondrow = new LinearLayout(this);
        secondrow.setPadding(0, 20, 0, 10);
        LinearLayout.LayoutParams secondrowlp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        secondrowlp.weight = 1;

        TextView secondrowtext = new TextView(this);
        secondrowtext.setText("No. Of Records : ");
        secondrowtext.setTextSize(20);
        secondrowtext.setLayoutParams(secondrowlp);

        tv = new TextView(this);
        tv.setTextSize(20);
        tv.setLayoutParams(secondrowlp);

        secondrow.addView(secondrowtext);
        secondrow.addView(tv);
        mainLayout.addView(secondrow);

        // Create the custom query input field and submit button
        customquerytext = new EditText(this);
        customquerytext.setVisibility(View.GONE);
        customquerytext.setHint("Enter Your Query here and Click on Submit Query Button. Results will be displayed below");
        mainLayout.addView(customquerytext);

        submitQuery = new Button(this);
        submitQuery.setVisibility(View.GONE);
        submitQuery.setText("Submit Query");
        submitQuery.setBackgroundColor(Color.parseColor("#BAE7F6"));
        mainLayout.addView(submitQuery);

        // Create and add a help TextView
        help = new TextView(this);
        help.setText("Click on the row below to update values or delete the tuple");
        help.setPadding(0, 5, 0, 5);
        mainLayout.addView(help);

        // Create and add the spinner for table actions
        spinnertable = new Spinner(this);
        mainLayout.addView(spinnertable);

        // Add the HorizontalScrollView
        hsv.setPadding(0, 10, 0, 10);
        hsv.setScrollbarFadingEnabled(false);
        hsv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        mainLayout.addView(hsv);

        // Create and configure the third row layout with pagination buttons
        thirdrow = new LinearLayout(this);
        thirdrow.setPadding(0, 10, 0, 10);

        previous = new Button(this);
        previous.setText("Previous");
        previous.setBackgroundColor(Color.parseColor("#BAE7F6"));
        previous.setLayoutParams(secondrowlp);

        next = new Button(this);
        next.setText("Next");
        next.setBackgroundColor(Color.parseColor("#BAE7F6"));
        next.setLayoutParams(secondrowlp);

        TextView tvblank = new TextView(this);
        tvblank.setLayoutParams(secondrowlp);

        thirdrow.addView(previous);
        thirdrow.addView(tvblank);
        thirdrow.addView(next);
        mainLayout.addView(thirdrow);

        // Create and configure the message TextView
        tvmessage = new TextView(this);
        tvmessage.setText("Error Messages will be displayed here");
        tvmessage.setTextSize(18);
        mainLayout.addView(tvmessage);

        // Create and configure the custom query button
        customQuery = new Button(this);
        customQuery.setText("Custom Query");
        customQuery.setBackgroundColor(Color.parseColor("#BAE7F6"));
        mainLayout.addView(customQuery);

        // Set onClickListener for the custom query button
        customQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexInfo.isCustomQuery = true;
                secondrow.setVisibility(View.GONE);
                spinnertable.setVisibility(View.GONE);
                help.setVisibility(View.GONE);
                customquerytext.setVisibility(View.VISIBLE);
                submitQuery.setVisibility(View.VISIBLE);
                select_table.setSelection(0);
                customQuery.setVisibility(View.GONE);
            }
        });

        // Set onClickListener for the submit query button
        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                customQuery.setVisibility(View.GONE);

                String query = customquerytext.getText().toString();
                Log.d("query", query);

                ArrayList<Cursor> results = dbm.getData(query);
                Cursor resultCursor = results.get(0);
                Cursor messageCursor = results.get(1);
                messageCursor.moveToLast();

                if (messageCursor.getString(0).equalsIgnoreCase("Success")) {
                    tvmessage.setBackgroundColor(Color.parseColor("#2ecc71"));
                    if (resultCursor != null) {
                        tvmessage.setText("Query Executed successfully. Number of rows returned: " + resultCursor.getCount());
                        if (resultCursor.getCount() > 0) {
                            IndexInfo.mainCursor = resultCursor;
                            refreshTable(1);
                        }
                    } else {
                        tvmessage.setText("Query Executed successfully");
                        refreshTable(1);
                    }
                } else {
                    tvmessage.setBackgroundColor(Color.parseColor("#e74c3c"));
                    tvmessage.setText("Error: " + messageCursor.getString(0));
                }
            }
        });


        // Define layout parameters for table rows
        tableRowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(0, 0, 2, 0);


        String Query = "SELECT name FROM sqlite_master WHERE type='table';";

        // Fetch table names and error messages
        ArrayList<Cursor> alc = dbm.getData(Query);
        final Cursor c = alc.get(0);
        Cursor messageCursor = alc.get(1);

        messageCursor.moveToLast();
        String msg = messageCursor.getString(0);
        Log.d("Message from SQL = ", msg);

        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add("Click here");

        if (c != null) {
            c.moveToFirst();
            do {
                tableNames.add(c.getString(0));
            } while (c.moveToNext());
        }

        // Setup spinner adapter for table names
        ArrayAdapter<String> tableNamesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tableNames) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(20);
                textView.setBackgroundColor(Color.WHITE);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                view.setBackgroundColor(Color.WHITE);
                return view;
            }
        };

        tableNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_table.setAdapter(tableNamesAdapter);

        select_table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0 && !IndexInfo.isCustomQuery) {
                    hideViews();
                } else {
                    showViews();
                    c.moveToPosition(pos - 1);
                    IndexInfo.cursorPosition = pos - 1;
                    IndexInfo.tableName = c.getString(0);
                    tvmessage.setText("Error Messages will be displayed here");
                    tvmessage.setBackgroundColor(Color.WHITE);

                    tableLayout.removeAllViews();
                    setupTableOptions();
                    displayTableContents(c.getString(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private void hideViews() {
        secondrow.setVisibility(View.GONE);
        hsv.setVisibility(View.GONE);
        thirdrow.setVisibility(View.GONE);
        spinnertable.setVisibility(View.GONE);
        help.setVisibility(View.GONE);
        tvmessage.setVisibility(View.GONE);
        customquerytext.setVisibility(View.GONE);
        submitQuery.setVisibility(View.GONE);
        customQuery.setVisibility(View.GONE);
    }

    private void showViews() {
        secondrow.setVisibility(View.VISIBLE);
        spinnertable.setVisibility(View.VISIBLE);
        help.setVisibility(View.VISIBLE);
        customquerytext.setVisibility(View.GONE);
        submitQuery.setVisibility(View.GONE);
        customQuery.setVisibility(View.VISIBLE);
        hsv.setVisibility(View.VISIBLE);
        tvmessage.setVisibility(View.VISIBLE);
        thirdrow.setVisibility(View.VISIBLE);
    }

    private void setupTableOptions() {
        ArrayList<String> spinnerTableValues = new ArrayList<>();
        spinnerTableValues.add("Click here to change this table");
        spinnerTableValues.add("Add row to this table");
        spinnerTableValues.add("Delete this table");
        spinnerTableValues.add("Drop this table");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerTableValues) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(20);
                textView.setBackgroundColor(Color.WHITE);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                view.setBackgroundColor(Color.WHITE);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertable.setAdapter(adapter);
        spinnertable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLACK);

                String selectedItem = spinnertable.getSelectedItem().toString();

                switch (selectedItem) {
                    case "Drop this table":
                        confirmAndDropTable();
                        break;

                    case "Delete this table":
                        confirmAndDeleteTable();
                        break;

                    case "Add row to this table":
                        showAddRowDialog();
                        break;

                    default:
                        // Do nothing
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // Do nothing
            }
        });
    }


    private void confirmAndDropTable() {
        new AlertDialog.Builder(AndroidManager.this)
                .setTitle("Are you sure?")
                .setMessage("Pressing yes will remove " + IndexInfo.tableName + " table from database")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String query = "DROP TABLE " + IndexInfo.tableName;
                    executeQuery(query, IndexInfo.tableName + " dropped successfully");
                })
                .setNegativeButton("No", (dialog, which) -> spinnertable.setSelection(0))
                .create().show();
    }

    private void confirmAndDeleteTable() {
        new AlertDialog.Builder(AndroidManager.this)
                .setTitle("Are you sure?")
                .setMessage("Clicking on yes will delete all the contents of " + IndexInfo.tableName + " table from database")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String query = "DELETE FROM " + IndexInfo.tableName;
                    executeQuery(query, IndexInfo.tableName + " table content deleted successfully");
                })
                .setNegativeButton("No", (dialog, which) -> spinnertable.setSelection(0))
                .create().show();
    }

    private void showAddRowDialog() {
        final LinkedList<TextView> addNewRowNames = new LinkedList<>();
        final LinkedList<EditText> addNewRowValues = new LinkedList<>();
        final ScrollView addRowScrollView = new ScrollView(AndroidManager.this);

        Cursor cursor = IndexInfo.mainCursor;
        if (IndexInfo.isEmpty) {
            getcolumnnames();
            for (String columnName : IndexInfo.emptyTableColumnNames) {
                TextView tv = new TextView(getApplicationContext());
                tv.setText(columnName);
                addNewRowNames.add(tv);
            }
        } else {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView tv = new TextView(getApplicationContext());
                tv.setText(cursor.getColumnName(i));
                addNewRowNames.add(tv);
            }
        }

        for (int i = 0; i < addNewRowNames.size(); i++) {
            EditText et = new EditText(getApplicationContext());
            addNewRowValues.add(et);
        }

        final RelativeLayout addNewLayout = new RelativeLayout(AndroidManager.this);
        RelativeLayout.LayoutParams addNewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addNewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        for (int i = 0; i < addNewRowNames.size(); i++) {
            TextView tv = addNewRowNames.get(i);
            EditText et = addNewRowValues.get(i);
            int t = i + 400;
            int k = i + 500;
            int lid = i + 600;

            tv.setId(t);
            tv.setTextColor(Color.BLACK);
            et.setBackgroundColor(Color.parseColor("#F2F2F2"));
            et.setTextColor(Color.BLACK);
            et.setId(k);

            final LinearLayout ll = new LinearLayout(AndroidManager.this);
            LinearLayout.LayoutParams tvl = new LinearLayout.LayoutParams(0, 100);
            tvl.weight = 1;
            ll.addView(tv, tvl);
            ll.addView(et, tvl);
            ll.setId(lid);

            RelativeLayout.LayoutParams rll = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rll.addRule(RelativeLayout.BELOW, ll.getId() - 1);
            rll.setMargins(0, 20, 0, 0);
            addNewLayout.addView(ll, rll);
        }

        addNewLayout.setBackgroundColor(Color.WHITE);
        addRowScrollView.addView(addNewLayout);

        new AlertDialog.Builder(AndroidManager.this)
                .setTitle("Values")
                .setCancelable(false)
                .setView(addRowScrollView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String query = "INSERT INTO " + IndexInfo.tableName + " (";
                    for (int i = 0; i < addNewRowNames.size(); i++) {
                        TextView tv = addNewRowNames.get(i);
                        query += i == addNewRowNames.size() - 1 ? tv.getText().toString() : tv.getText().toString() + ", ";
                    }
                    query += ") VALUES (";
                    for (int i = 0; i < addNewRowNames.size(); i++) {
                        EditText et = addNewRowValues.get(i);
                        query += i == addNewRowNames.size() - 1 ? "'" + et.getText().toString() + "' )" : "'" + et.getText().toString() + "', ";
                    }

                    executeQuery(query, "New row added successfully to " + IndexInfo.tableName);
                })
                .setNegativeButton("Close", (dialog, which) -> spinnertable.setSelection(0))
                .create().show();
    }

    private void executeQuery(String query, String successMessage) {
        try {
            ArrayList<Cursor> result = dbm.getData(query);
            Cursor tempc = result.get(1);
            tempc.moveToLast();
            if (tempc.getString(0).equalsIgnoreCase("Success")) {
                tvmessage.setBackgroundColor(Color.parseColor("#2ecc71"));
                tvmessage.setText(successMessage);
                refreshactivity();
            } else {
                tvmessage.setBackgroundColor(Color.parseColor("#e74c3c"));
                tvmessage.setText("Error: " + tempc.getString(0));
                spinnertable.setSelection(0);
            }
        } catch (Exception e) {
            tvmessage.setBackgroundColor(Color.parseColor("#e74c3c"));
            tvmessage.setText("Error: " + e.getMessage());
            spinnertable.setSelection(0);
        }
    }

    private void refreshactivity() {
        // Implement your activity refresh logic here
    }

    private void getcolumnnames() {
        // Implement your logic to get column names here
    }

    private void displayTableContents(String tableName) {
        String query = "SELECT * FROM " + tableName;
        ArrayList<Cursor> alc2 = dbm.getData(query);
        final Cursor c2 = alc2.get(0);
        IndexInfo.mainCursor = c2;

        if (c2 != null) {
            int count = c2.getCount();
            IndexInfo.isEmpty = false;
            Log.d("Counts", "" + count);
            tv.setText("" + count);

            TableRow tableHeader = new TableRow(this);
            tableHeader.setBackgroundColor(Color.BLACK);
            tableHeader.setPadding(0, 2, 0, 2);

            for (int i = 0; i < c2.getColumnCount(); i++) {
                LinearLayout cell = new LinearLayout(this);
                cell.setBackgroundColor(Color.WHITE);
                cell.setLayoutParams(tableRowParams);

                TextView columnHeader = new TextView(this);
                columnHeader.setPadding(0, 0, 4, 3);
                columnHeader.setText(c2.getColumnName(i));
                columnHeader.setTextColor(Color.BLACK);

                cell.addView(columnHeader);
                tableHeader.addView(cell);
            }
            tableLayout.addView(tableHeader);
            c2.moveToFirst();
            paginateTable();
        } else {
            showEmptyTableMessage();
        }
    }

    private void showEmptyTableMessage() {
        help.setVisibility(View.GONE);
        tableLayout.removeAllViews();

        TableRow tableHeader = new TableRow(this);
        tableHeader.setBackgroundColor(Color.BLACK);
        tableHeader.setPadding(0, 2, 0, 2);

        LinearLayout cell = new LinearLayout(this);
        cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(tableRowParams);

        TextView message = new TextView(this);
        message.setPadding(0, 0, 4, 3);
        message.setText("   Table   Is   Empty   ");
        message.setTextSize(30);
        message.setTextColor(Color.RED);

        cell.addView(message);
        tableHeader.addView(cell);
        tableLayout.addView(tableHeader);

        tv.setText("0");
    }

    // Get column names of the empty table and save them in an ArrayList
    public void getColumnNames() {
        ArrayList<Cursor> alc3 = dbm.getData("PRAGMA table_info(" + IndexInfo.tableName + ")");
        Cursor c5 = alc3.get(0);

        // Initialize the empty table column names list
        ArrayList<String> emptyTableColumnNames = new ArrayList<>();

        if (c5 != null) {
            try {
                IndexInfo.isEmpty = true;  // Set the table as empty initially

                // Move to the first row of the cursor
                c5.moveToFirst();

                // Iterate through the cursor and add column names to the list
                do {
                    emptyTableColumnNames.add(c5.getString(1)); // Column name is in the second column (index 1)
                } while (c5.moveToNext());

                // Update the static variable with the list of column names
                IndexInfo.emptyTableColumnNames = emptyTableColumnNames;
            } finally {
                // Always close the cursor to avoid memory leaks
                c5.close();
            }
        } else {
            // Handle the case where the cursor is null (optional)
            IndexInfo.isEmpty = false;
        }
    }

    // Displays an alert dialog for updating or deleting a row
    public void updateDeletePopup(int row) {
        Cursor cursor = IndexInfo.mainCursor;
        if (cursor == null || !cursor.moveToPosition(row)) {
            // Handle cursor being null or row index out of bounds
            return;
        }

        // Spinner options
        ArrayList<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Click Here to Change this row");
        spinnerOptions.add("Update this row");
        spinnerOptions.add("Delete this row");

        // Create layout for column names and values
        final ArrayList<String> valueStrings = new ArrayList<>(IndexInfo.valueStrings);
        final LinkedList<TextView> columnNames = new LinkedList<>();
        final LinkedList<EditText> columnValues = new LinkedList<>();

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            TextView columnNameView = new TextView(getApplicationContext());
            columnNameView.setText(columnName);
            columnNames.add(columnNameView);

            EditText columnValueView = new EditText(getApplicationContext());
            columnValueView.setText(cursor.getString(i)); // Get the value for each column
            columnValues.add(columnValueView);
        }

        // Layout setup
        final RelativeLayout layout = new RelativeLayout(AndroidManager.this);
        layout.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        final ScrollView scrollView = new ScrollView(AndroidManager.this);
        LinearLayout linearLayout = new LinearLayout(AndroidManager.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Spinner setup
        final Spinner spinner = new Spinner(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AndroidManager.this,
                android.R.layout.simple_spinner_item, spinnerOptions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(Color.WHITE);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(20);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                view.setBackgroundColor(Color.WHITE);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        spinnerParams.setMargins(0, 20, 0, 0);
        linearLayout.addView(spinner, spinnerParams);

        // Add column names and values to layout
        for (int i = 0; i < columnNames.size(); i++) {
            TextView columnNameView = columnNames.get(i);
            EditText columnValueView = columnValues.get(i);

            LinearLayout rowLayout = new LinearLayout(AndroidManager.this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setBackgroundColor(Color.WHITE);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            rowParams.weight = 1;

            columnNameView.setLayoutParams(rowParams);
            columnValueView.setLayoutParams(rowParams);

            rowLayout.addView(columnNameView);
            rowLayout.addView(columnValueView);

            linearLayout.addView(rowLayout);
        }

        scrollView.addView(linearLayout);
        layout.addView(scrollView, layoutParams);

        // Show alert dialog
        runOnUiThread(() -> {
            if (!isFinishing()) {
                new AlertDialog.Builder(AndroidManager.this)
                        .setTitle("Update/Delete Row")
                        .setView(layout)
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> {
                            String selectedOption = spinner.getSelectedItem().toString();

                            if (selectedOption.equalsIgnoreCase("Update this row")) {
                                StringBuilder updateQuery = new StringBuilder("UPDATE " + IndexInfo.tableName + " SET ");

                                for (int i = 0; i < columnNames.size(); i++) {
                                    TextView columnNameView = columnNames.get(i);
                                    EditText columnValueView = columnValues.get(i);

                                    String columnName = columnNameView.getText().toString();
                                    String columnValue = columnValueView.getText().toString();

                                    if (!columnValue.equals("null")) {
                                        updateQuery.append(columnName).append(" = '").append(columnValue).append("'");
                                        if (i < columnNames.size() - 1) {
                                            updateQuery.append(", ");
                                        }
                                    }
                                }

                                updateQuery.append(" WHERE ");
                                for (int i = 0; i < columnNames.size(); i++) {
                                    TextView columnNameView = columnNames.get(i);
                                    String columnValue = valueStrings.get(i);

                                    if (!columnValue.equals("null")) {
                                        updateQuery.append(columnNameView.getText().toString()).append(" = '").append(columnValue).append("'");
                                        if (i < columnNames.size() - 1) {
                                            updateQuery.append(" AND ");
                                        }
                                    }
                                }

                                Log.d("Update Query", updateQuery.toString());
                                ArrayList<Cursor> result = dbm.getData(updateQuery.toString());
                                Cursor resultCursor = result.get(1);
                                resultCursor.moveToLast();
                                Log.d("Update Message", resultCursor.getString(0));

                                if (resultCursor.getString(0).equalsIgnoreCase("Success")) {
                                    tvmessage.setBackgroundColor(Color.parseColor("#2ecc71"));
                                    tvmessage.setText(IndexInfo.tableName + " table updated successfully");
                                    refreshTable(0);
                                } else {
                                    tvmessage.setBackgroundColor(Color.parseColor("#e74c3c"));
                                    tvmessage.setText("Error: " + resultCursor.getString(0));
                                }
                            }

                            if (selectedOption.equalsIgnoreCase("Delete this row")) {
                                StringBuilder deleteQuery = new StringBuilder("DELETE FROM " + IndexInfo.tableName + " WHERE ");

                                for (int i = 0; i < columnNames.size(); i++) {
                                    TextView columnNameView = columnNames.get(i);
                                    String columnValue = valueStrings.get(i);

                                    if (!columnValue.equals("null")) {
                                        deleteQuery.append(columnNameView.getText().toString()).append(" = '").append(columnValue).append("'");
                                        if (i < columnNames.size() - 1) {
                                            deleteQuery.append(" AND ");
                                        }
                                    }
                                }

                                Log.d("Delete Query", deleteQuery.toString());
                                ArrayList<Cursor> result = dbm.getData(deleteQuery.toString());
                                Cursor resultCursor = result.get(1);
                                resultCursor.moveToLast();
                                Log.d("Delete Message", resultCursor.getString(0));

                                if (resultCursor.getString(0).equalsIgnoreCase("Success")) {
                                    tvmessage.setBackgroundColor(Color.parseColor("#2ecc71"));
                                    tvmessage.setText("Row deleted from " + IndexInfo.tableName + " table");
                                    refreshTable(0);
                                } else {
                                    tvmessage.setBackgroundColor(Color.parseColor("#e74c3c"));
                                    tvmessage.setText("Error: " + resultCursor.getString(0));
                                }
                            }
                        })
                        .setNegativeButton("Close", (dialog, which) -> {
                        })
                        .create().show();
            }
        });
    }

    public void refreshActivity() {
        // Restart the activity to refresh
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void refreshTable(int d) {
        Cursor cursor = null;
        tableLayout.removeAllViews(); // Clear the previous table content

        // Determine which cursor to use
        if (d == 0) {
            String query = "SELECT * FROM " + IndexInfo.tableName;
            ArrayList<Cursor> cursors = dbm.getData(query);
            cursor = cursors.get(0);
            // Save the cursor to the static IndexInfo class for reuse
            IndexInfo.mainCursor = cursor;
        } else if (d == 1) {
            cursor = IndexInfo.mainCursor;
        }

        // Display data if cursor is not null
        if (cursor != null) {
            int count = cursor.getCount();
            Log.d("counts", "" + count);
            tv.setText("" + count);

            // Create and add table header row
            TableRow tableHeader = new TableRow(getApplicationContext());
            tableHeader.setBackgroundColor(Color.BLACK);
            tableHeader.setPadding(0, 2, 0, 2);

            for (int k = 0; k < cursor.getColumnCount(); k++) {
                String columnName = cursor.getColumnName(k);
                TextView headerTextView = createTextView(columnName);
                LinearLayout cell = new LinearLayout(this);
                cell.setBackgroundColor(Color.WHITE);
                cell.setLayoutParams(tableRowParams);
                cell.addView(headerTextView);
                tableHeader.addView(cell);
            }
            tableLayout.addView(tableHeader);

            // Initialize the cursor position and display the first page of data
            cursor.moveToFirst();
            paginateTable();
        } else {
            // Handle case when cursor is null (empty table)
            TableRow emptyTableRow = new TableRow(getApplicationContext());
            emptyTableRow.setBackgroundColor(Color.BLACK);
            emptyTableRow.setPadding(0, 2, 0, 2);

            LinearLayout cell = new LinearLayout(this);
            cell.setBackgroundColor(Color.WHITE);
            cell.setLayoutParams(tableRowParams);

            TextView emptyTextView = createTextView("   Table   Is   Empty   ");
            emptyTextView.setTextSize(30);
            emptyTextView.setTextColor(Color.RED);
            cell.addView(emptyTextView);
            emptyTableRow.addView(cell);

            tableLayout.addView(emptyTableRow);
            tv.setText("" + 0);
        }
    }


    // Function to display tuples from the database in a table layout
    public void paginateTable() {

        if (IndexInfo.mainCursor != null) {
            displayPage(IndexInfo.mainCursor);
        }
        final Cursor cursor = IndexInfo.mainCursor;

        if (cursor == null || cursor.getCount() == 0) {
            // Handle the case where the cursor is null or empty
            tableLayout.removeAllViews();
            IndexInfo.numberOfPages = 0;
            IndexInfo.currentPage = 0;
            return;
        }

        IndexInfo.numberOfPages = (cursor.getCount() + PAGE_SIZE - 1) / PAGE_SIZE; // Ceiling division
        IndexInfo.currentPage = 1;

        cursor.moveToFirst();

        // Display the first page of data
        displayPage(cursor);

        // Set up button listeners
        setupPaginationButtons(cursor);
    }


    // Function to display the current page of data
    private void displayPage(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            // Handle the case where the cursor is null or empty
            tableLayout.removeAllViews();
            return;
        }

        tableLayout.removeAllViews(); // Clear previous content

        int startRow = (IndexInfo.currentPage - 1) * PAGE_SIZE;
        int endRow = Math.min(startRow + PAGE_SIZE, cursor.getCount());

        if (startRow >= cursor.getCount()) {
            // Ensure startRow is within valid range
            startRow = cursor.getCount() - PAGE_SIZE;
            endRow = cursor.getCount();
            IndexInfo.currentPage = (cursor.getCount() + PAGE_SIZE - 1) / PAGE_SIZE; // Adjust current page
        }

        cursor.moveToPosition(startRow);

        // Display rows
        for (int i = startRow; i < endRow; i++) {
            TableRow tableRow = createTableRow(cursor);
            tableLayout.addView(tableRow);
            cursor.moveToNext();
        }

        IndexInfo.index = endRow; // Update index to the end of the displayed page
    }


    // Function to create a TableRow from the cursor
    private TableRow createTableRow(Cursor cursor) {
        TableRow tableRow = new TableRow(getApplicationContext());
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(0, 2, 0, 2);

        // Iterate through each column in the cursor
        for (int j = 0; j < cursor.getColumnCount(); j++) {
            // Retrieve column data and create a TextView for it
            String columnData = getColumnData(cursor, j);
            TextView columnView = createTextView(columnData);

            // Create a LinearLayout for each cell
            LinearLayout cell = new LinearLayout(this);
            cell.setBackgroundColor(Color.WHITE);
            cell.setLayoutParams(tableRowParams);
            cell.addView(columnView);

            // Add cell to TableRow
            tableRow.addView(cell);
        }

        // Set OnClickListener for the TableRow
        tableRow.setOnClickListener(v -> {
            List<String> values = new ArrayList<>();
            // Extract values from the TableRow's cells
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView textView = (TextView) ((LinearLayout) tableRow.getChildAt(i)).getChildAt(0);
                values.add(textView.getText().toString());
            }
            // Update IndexInfo with the extracted values
            IndexInfo.valueStrings = values;  // Direct access
            // Call function to show update/delete popup
            updateDeletePopup(0);
        });

        return tableRow;
    }


    // Function to get column data, handling non-string data gracefully
    private String getColumnData(Cursor cursor, int columnIndex) {
        try {
            // Try to get the data as a string
            return cursor.getString(columnIndex);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            Log.e("getColumnData", "Error retrieving column data", e);
            // Return an empty string if there is an error or if data is not a string
            return "";
        }
    }


    // Function to create a TextView with the specified text
    private TextView createTextView(String text) {
        // Create a new TextView instance
        TextView textView = new TextView(getApplicationContext());

        // Set the text and appearance
        textView.setText(text);
        textView.setTextColor(Color.BLACK); // Use predefined color constant
        textView.setPadding(0, 0, 4, 3);

        // Optionally, set default text size and style
        // textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        // textView.setTypeface(Typeface.DEFAULT_BOLD);

        return textView;
    }

    // Function to set up pagination buttons
    private void setupPaginationButtons(final Cursor cursor) {
        // Set up "Previous" button
        previous.setOnClickListener(v -> {
            if (IndexInfo.currentPage > 1) {
                IndexInfo.currentPage--;
                cursor.moveToPosition((IndexInfo.currentPage - 1) * PAGE_SIZE);
                displayPage(cursor);
            } else {
                Toast.makeText(getApplicationContext(), "This is the first page", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up "Next" button
        next.setOnClickListener(v -> {
            if (IndexInfo.currentPage < IndexInfo.numberOfPages) {
                IndexInfo.currentPage++;
                cursor.moveToPosition((IndexInfo.currentPage - 1) * PAGE_SIZE);
                displayPage(cursor);
            } else {
                Toast.makeText(getApplicationContext(), "This is the last page", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    // A static class to store and manage shared data and state information
    static class IndexInfo {
        public static int index = 10;
        public static int numberOfPages = 0;
        public static int currentPage = 0;
        public static String tableName = "";
        public static Cursor mainCursor;
        public static int cursorPosition = 0;
        public static List<String> tableHeaderNames;
        public static List<String> emptyTableColumnNames;
        private static List<String> valueStrings = new ArrayList<>(); // Private list of value strings
        public static boolean isEmpty;
        public static boolean isCustomQuery;

        // Getter for valueStrings
        public static List<String> getValueStrings() {
            return valueStrings;
        }

        // Setter for valueStrings
        public static void setValueStrings(List<String> valueStrings) {
            IndexInfo.valueStrings = valueStrings;
        }
    }
}


