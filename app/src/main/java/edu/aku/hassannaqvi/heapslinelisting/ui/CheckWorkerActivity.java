package edu.aku.hassannaqvi.heapslinelisting.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

import edu.aku.hassannaqvi.heapslinelisting.R;
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts;
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp;
import edu.aku.hassannaqvi.heapslinelisting.databinding.ActivityCheckWorkerBinding;
import edu.aku.hassannaqvi.heapslinelisting.models.WorkerStatusViewModel;

public class CheckWorkerActivity extends AppCompatActivity {

    private WorkManager workManager;
    private WorkInfo.State[] tagStatuses;
    private ActivityCheckWorkerBinding binding;

    private WorkerStatusViewModel viewModel;
    private MutableLiveData<WorkInfo.State[]> tagStatusesLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MainApp) getApplication()).resetInactivityTimer();

        super.onCreate(savedInstanceState);
        // Initialize ViewModel and set up data binding
        WorkerStatusViewModel viewModel = new ViewModelProvider(this).get(WorkerStatusViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_worker);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Observe the LiveData in ViewModel
        viewModel.tagStatuses.observe(this, tagStatuses -> {
            // Update your UI elements using the data-binding
        });

        // Get the WorkManager instance
         workManager = WorkManager.getInstance(this);
        // Call your function to update the LiveData
        tagStatuses = new WorkInfo.State[]{WorkInfo.State.SUCCEEDED, WorkInfo.State.FAILED};
        viewModel.updateTagStatuses(tagStatuses);


// Define your worker tags in an array
        String[] workerTags = {TableContracts.ListingTable.TABLE_NAME, TableContracts.FamilyMembersTable.TABLE_NAME};


        setUpObserversForTags(workerTags);

    }



    // Function to set up observers for multiple tags
    private void setUpObserversForTags(String[] tags) {
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i];
            LiveData<List<WorkInfo>> workInfoListLiveData = workManager.getWorkInfosByTagLiveData(tag);
            int finalI = i; // To capture the correct index in the lambda expression
            workInfoListLiveData.observe(this, workInfos -> {
                if (workInfos != null && !workInfos.isEmpty()) {
                    // Get the latest WorkInfo object
                    WorkInfo latestWorkInfo = workInfos.get(0);

                    // Get the work status
                    WorkInfo.State state = latestWorkInfo.getState();

                    // Store the status in the array
                    tagStatuses[finalI] = state;

                    // Check if statuses for all tags have been collected
                    boolean allStatusesCollected = true;
                    for (WorkInfo.State status : tagStatuses) {
                        if (status == null) {
                            allStatusesCollected = false;
                            break;
                        }
                    }

                    if (allStatusesCollected) {

                        // Now you have collected statuses for all tags in the tagStatuses array
                        // You can access the statuses using tagStatuses array
                        // For example: tagStatuses[0] will give the status for the first tag
                    }
                }
            });
        }
    }
}