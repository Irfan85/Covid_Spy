package com.covid_spy.covidspy.ui.notifications;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.covid_spy.covidspy.CovidData;
import com.covid_spy.covidspy.R;
import com.covid_spy.covidspy.Util.Utils;
import com.covid_spy.covidspy.databinding.FragmentNotificationsBinding;
import com.covid_spy.covidspy.ui.home.HomeViewModel;

import java.util.HashMap;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private HomeViewModel viewModel;


    private final double BANGLADESH_BASELINE = 5936.7;

    private String riskLevel = "";
    private String recommendedMaskType = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getNonUSLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, CovidData>>() {
            @Override
            public void onChanged(HashMap<String, CovidData> stringCovidDataHashMap) {
                CovidData bangladeshData = stringCovidDataHashMap.get("BGD");
                riskLevel = getRiskLevel(Math.round(bangladeshData.getNewCasesSmoothed()));
                recommendedMaskType = getMaskType(riskLevel);
            }
        });


        Button showNotificationButton = binding.showNotificationButton;
        showNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), Utils.COVID_SPY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.outline_warning_24)
                        .setContentTitle(riskLevel + " risk")
                        .setContentText("Use " + recommendedMaskType + " masks")
                        .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
                notificationManagerCompat.notify(Utils.ALERT_NOTIFICATION_ID, builder.build());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getRiskLevel(long infectionRate) {
        double ratio = (infectionRate * 1.0)/ BANGLADESH_BASELINE;

        if(ratio >= 0.1 && ratio <= 1) {
            return "Moderate";
        } else if(ratio < 0.1) {
            return "Low";
        } else {
            return "High";
        }
    }

    private String getMaskType(String riskLevel) {
        switch (riskLevel) {
            case "Moderate":
                return "Surgical";
            case "Low":
                return "Cloth";
            case "High":
                return "N95";
            default:
                return "";
        }
    }

}