package com.covid_spy.covidspy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.covid_spy.covidspy.CovidData;
import com.covid_spy.covidspy.R;
import com.covid_spy.covidspy.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    List<String> countries = new ArrayList<>();
    HashMap<String, CovidData> countryCovidData = new HashMap<>();

    private final double BANGLADESH_BASELINE = 5936.7;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner countrySpinner = binding.countrySpinner;


        TextView locationTextView = binding.locationTextView;
        TextView confirmedCasesTextView = binding.confirmedCasesTextView;
        TextView confirmedDeathsTextView = binding.confirmedDeathsTextView;
        TextView confirmedVaccinationTextView = binding.confirmedVaccinationTextView;
        TextView riskLevelTextView = binding.riskLevelTextView;
        TextView suggestedMaskTextView = binding.suggestedMaskTextView;
        TextView infectionTrendTextView = binding.infectionTrendTextView;

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (countryCovidData.size() > 0) {
                    CovidData countryData = countryCovidData.get(countrySpinner.getItemAtPosition(i));
                    locationTextView.setText(countryData.getLocation());

                    long reportedCases = Math.round(countryData.getNewCasesSmoothed());
                    String reportedCasesString = "Cases Reported: ".concat(String.valueOf(reportedCases));
                    confirmedCasesTextView.setText(reportedCasesString);

                    String reportedDeathsString = "Deaths Reported: ".concat(String.valueOf(Math.round(countryData.getNewDeathsSmoothed())));
                    confirmedDeathsTextView.setText(reportedDeathsString);

                    String reportedVaccinationsString = "Vaccinations Confirmed: ".concat(String.valueOf(countryData.getTotalVaccination()));
                    confirmedVaccinationTextView.setText(reportedVaccinationsString);

                    String riskLevel = getRiskLevel(reportedCases);
                    String riskLevelString = "Risk Level: ".concat(riskLevel);
                    riskLevelTextView.setText(riskLevelString);

                    String suggestedMaskString = "Suggested Mask: ".concat(getMaskType(riskLevel));
                    suggestedMaskTextView.setText(suggestedMaskString);

                    String infectionTrend = getInfectionTrend(reportedCases);
                    String infectionTrendString = "Infection Trend: ".concat(infectionTrend);
                    infectionTrendTextView.setText(infectionTrendString);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        homeViewModel.getNonUSLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, CovidData>>() {
            @Override
            public void onChanged(HashMap<String, CovidData> stringCovidDataHashMap) {
                if(stringCovidDataHashMap != null) {
                    if(countries.size() == 0) {
                        int bangladeshPos = 0;
                        int i = 0;
                        for(String key : stringCovidDataHashMap.keySet()){
                            if(key.equals("BGD")) {
                                bangladeshPos = i;
                            }
                           countries.add(key);
                            i++;
                        }

                        ArrayAdapter<String> countrySpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countries);
                        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(countrySpinnerAdapter);
                        countrySpinner.setSelection(bangladeshPos);
                    }

                    countryCovidData.clear();
                    countryCovidData.putAll(stringCovidDataHashMap);

                    CovidData bangladeshData = stringCovidDataHashMap.get("BGD");
                    locationTextView.setText(bangladeshData.getLocation());

                    long reportedCases = Math.round(bangladeshData.getNewCasesSmoothed());
                    String reportedCasesString = "Cases Reported: ".concat(String.valueOf(reportedCases));
                    confirmedCasesTextView.setText(reportedCasesString);

                    String reportedDeathsString = "Deaths Reported: ".concat(String.valueOf(Math.round(bangladeshData.getNewDeathsSmoothed())));
                    confirmedDeathsTextView.setText(reportedDeathsString);

                    String reportedVaccinationsString = "Vaccinations Confirmed: ".concat(String.valueOf(bangladeshData.getTotalVaccination()));
                    confirmedVaccinationTextView.setText(reportedVaccinationsString);

                    String riskLevel = getRiskLevel(reportedCases);
                    String riskLevelString = "Risk Level: ".concat(riskLevel);
                    riskLevelTextView.setText(riskLevelString);

                    String suggestedMaskString = "Suggested Mask: ".concat(getMaskType(riskLevel));
                    suggestedMaskTextView.setText(suggestedMaskString);

                    String infectionTrend = getInfectionTrend(reportedCases);
                    String infectionTrendString = "Infection Trend: ".concat(infectionTrend);
                    infectionTrendTextView.setText(infectionTrendString);
                }
            }
        });

        return root;
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

    private String getInfectionTrend(long infectionRate) {
        if (infectionRate >= BANGLADESH_BASELINE)
            return "Upward";
        else
            return "Downward";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}