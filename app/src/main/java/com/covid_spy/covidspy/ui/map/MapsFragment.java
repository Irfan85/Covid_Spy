package com.covid_spy.covidspy.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.covid_spy.covidspy.CovidData;
import com.covid_spy.covidspy.R;
import com.covid_spy.covidspy.databinding.FragmentMapsBinding;
import com.covid_spy.covidspy.ui.home.HomeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;

    private static final float STOKE_WIDTH = 2.0f;
    private final double US_BASELINE = 3877.284523;
    private final double BANGLADESH_BASELINE = 5936.7;

    private HomeViewModel viewModel;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng dhaka = new LatLng(23.8103, 90.4125);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));

            viewModel = new ViewModelProvider(getParentFragment()).get(HomeViewModel.class);

            // Bangladesh
            viewModel.getNonUSLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, CovidData>>() {
                @Override
                public void onChanged(HashMap<String, CovidData> stringCovidDataHashMap) {
                    try {
                        GeoJsonLayer bangladeshLayer = new GeoJsonLayer(googleMap, R.raw.bangladesh, getContext());
                        bangladeshLayer.getDefaultPolygonStyle().setPolygonFillColor(getNonUSColor(stringCovidDataHashMap.get("BGD").getNewCasesSmoothed()));
                        bangladeshLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        bangladeshLayer.addLayerToMap();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            // US States
            viewModel.getUSLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, CovidData>>() {
                @Override
                public void onChanged(HashMap<String, CovidData> stringCovidDataHashMap) {
                    try {
                        // Alaska
                        GeoJsonLayer alaskaLayer = new GeoJsonLayer(googleMap, R.raw.ak, getContext());
                        alaskaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("AK").getNewCasesSmoothed()));
                        alaskaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        alaskaLayer.addLayerToMap();

                        // Alabama
                        GeoJsonLayer alabamaLayer = new GeoJsonLayer(googleMap, R.raw.al, getContext());
                        alabamaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        alabamaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("AL").getNewCasesSmoothed()));
                        alabamaLayer.addLayerToMap();

                        // Arkansas
                        GeoJsonLayer arkansasLayer = new GeoJsonLayer(googleMap, R.raw.ar, getContext());
                        arkansasLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("AR").getNewCasesSmoothed()));
                        arkansasLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        arkansasLayer.addLayerToMap();

                        // Arizona
                        GeoJsonLayer arizonaLayer = new GeoJsonLayer(googleMap, R.raw.az, getContext());
                        arizonaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("AZ").getNewCasesSmoothed()));
                        arizonaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        arizonaLayer.addLayerToMap();

                        // California
                        GeoJsonLayer californiaLayer = new GeoJsonLayer(googleMap, R.raw.ca, getContext());
                        californiaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("CA").getNewCasesSmoothed()));
                        californiaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        californiaLayer.addLayerToMap();

                        // Colorado
                        GeoJsonLayer coloradoLayer = new GeoJsonLayer(googleMap, R.raw.co, getContext());
                        coloradoLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("CO").getNewCasesSmoothed()));
                        coloradoLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        coloradoLayer.addLayerToMap();

                        // Connecticut
                        GeoJsonLayer connecticutLayer = new GeoJsonLayer(googleMap, R.raw.ct, getContext());
                        connecticutLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("CT").getNewCasesSmoothed()));
                        connecticutLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        connecticutLayer.addLayerToMap();

                        // DC
                        GeoJsonLayer dcLayer = new GeoJsonLayer(googleMap, R.raw.dc, getContext());
                        dcLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("DC").getNewCasesSmoothed()));
                        dcLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        dcLayer.addLayerToMap();

                        // Delaware
                        GeoJsonLayer delawareLayer = new GeoJsonLayer(googleMap, R.raw.de, getContext());
                        delawareLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("DE").getNewCasesSmoothed()));
                        delawareLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        delawareLayer.addLayerToMap();

                        // Florida
                        GeoJsonLayer floridaLayer = new GeoJsonLayer(googleMap, R.raw.fl, getContext());
                        floridaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("FL").getNewCasesSmoothed()));
                        floridaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        floridaLayer.addLayerToMap();

                        // Georgia
                        GeoJsonLayer georgiaLayer = new GeoJsonLayer(googleMap, R.raw.ga, getContext());
                        georgiaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("GA").getNewCasesSmoothed()));
                        georgiaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        georgiaLayer.addLayerToMap();

                        // Iowa
                        GeoJsonLayer iowaLayer = new GeoJsonLayer(googleMap, R.raw.ia, getContext());
                        iowaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("IA").getNewCasesSmoothed()));
                        iowaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        iowaLayer.addLayerToMap();

                        // Indiana
                        GeoJsonLayer indianaLayer = new GeoJsonLayer(googleMap, R.raw.in, getContext());
                        indianaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("IN").getNewCasesSmoothed()));
                        indianaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        indianaLayer.addLayerToMap();

                        // Idaho
                        GeoJsonLayer idahoLayer = new GeoJsonLayer(googleMap, R.raw.id, getContext());
                        idahoLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("ID").getNewCasesSmoothed()));
                        idahoLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        idahoLayer.addLayerToMap();

                        // Illinois
                        GeoJsonLayer illinoisLayer = new GeoJsonLayer(googleMap, R.raw.il, getContext());
                        illinoisLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("IL").getNewCasesSmoothed()));
                        illinoisLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        illinoisLayer.addLayerToMap();

                        // Kansas
                        GeoJsonLayer kansasLayer = new GeoJsonLayer(googleMap, R.raw.ks, getContext());
                        kansasLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("KS").getNewCasesSmoothed()));
                        kansasLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        kansasLayer.addLayerToMap();

                        // Kentucky
                        GeoJsonLayer kentuckyLayer = new GeoJsonLayer(googleMap, R.raw.ky, getContext());
                        kentuckyLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("KY").getNewCasesSmoothed()));
                        kentuckyLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        kentuckyLayer.addLayerToMap();

                        // Louisiana
                        GeoJsonLayer louisianaLayer = new GeoJsonLayer(googleMap, R.raw.la, getContext());
                        louisianaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("LA").getNewCasesSmoothed()));
                        louisianaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        louisianaLayer.addLayerToMap();

                        // Maryland
                        GeoJsonLayer marylandLayer = new GeoJsonLayer(googleMap, R.raw.md, getContext());
                        marylandLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MD").getNewCasesSmoothed()));
                        marylandLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        marylandLayer.addLayerToMap();

                        // Maine
                        GeoJsonLayer maineLayer = new GeoJsonLayer(googleMap, R.raw.me, getContext());
                        maineLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("ME").getNewCasesSmoothed()));
                        maineLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        maineLayer.addLayerToMap();

                        // Massachusetts
                        GeoJsonLayer massachusettsLayer = new GeoJsonLayer(googleMap, R.raw.ma, getContext());
                        massachusettsLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MA").getNewCasesSmoothed()));
                        massachusettsLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        massachusettsLayer.addLayerToMap();

                        // Michigan
                        GeoJsonLayer michiganLayer = new GeoJsonLayer(googleMap, R.raw.mi, getContext());
                        michiganLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MI").getNewCasesSmoothed()));
                        michiganLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        michiganLayer.addLayerToMap();

                        // Minnesota
                        GeoJsonLayer minnesotaLayer = new GeoJsonLayer(googleMap, R.raw.mn, getContext());
                        minnesotaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MN").getNewCasesSmoothed()));
                        minnesotaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        minnesotaLayer.addLayerToMap();

                        // Montana
                        GeoJsonLayer montanaLayer = new GeoJsonLayer(googleMap, R.raw.mt, getContext());
                        montanaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MT").getNewCasesSmoothed()));
                        montanaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        montanaLayer.addLayerToMap();

                        // Mississippi
                        GeoJsonLayer mississippiLayer = new GeoJsonLayer(googleMap, R.raw.ms, getContext());
                        mississippiLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MS").getNewCasesSmoothed()));
                        mississippiLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        mississippiLayer.addLayerToMap();

                        // Missouri
                        GeoJsonLayer missouriLayer = new GeoJsonLayer(googleMap, R.raw.mo, getContext());
                        missouriLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("MO").getNewCasesSmoothed()));
                        missouriLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        missouriLayer.addLayerToMap();

                        // North Carolina
                        GeoJsonLayer northCarolinaLayer = new GeoJsonLayer(googleMap, R.raw.nc, getContext());
                        northCarolinaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NC").getNewCasesSmoothed()));
                        northCarolinaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        northCarolinaLayer.addLayerToMap();

                        // North Dakota
                        GeoJsonLayer northDakotaLayer = new GeoJsonLayer(googleMap, R.raw.nd, getContext());
                        northDakotaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("ND").getNewCasesSmoothed()));
                        northDakotaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        northDakotaLayer.addLayerToMap();

                        // Nebraska
                        GeoJsonLayer nebraskaLayer = new GeoJsonLayer(googleMap, R.raw.ne, getContext());
                        nebraskaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NE").getNewCasesSmoothed()));
                        nebraskaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        nebraskaLayer.addLayerToMap();

                        // New Hampshire
                        GeoJsonLayer newHampshireLayer = new GeoJsonLayer(googleMap, R.raw.nh, getContext());
                        newHampshireLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NH").getNewCasesSmoothed()));
                        newHampshireLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        newHampshireLayer.addLayerToMap();

                        // New Jersey
                        GeoJsonLayer newJerseyLayer = new GeoJsonLayer(googleMap, R.raw.nj, getContext());
                        newJerseyLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NJ").getNewCasesSmoothed()));
                        newJerseyLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        newJerseyLayer.addLayerToMap();

                        // New Mexico
                        GeoJsonLayer newMexicoLayer = new GeoJsonLayer(googleMap, R.raw.nm, getContext());
                        newMexicoLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NM").getNewCasesSmoothed()));
                        newMexicoLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        newMexicoLayer.addLayerToMap();

                        // Nevada
                        GeoJsonLayer nevadaLayer = new GeoJsonLayer(googleMap, R.raw.nv, getContext());
                        nevadaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NV").getNewCasesSmoothed()));
                        nevadaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        nevadaLayer.addLayerToMap();

                        // New York
                        GeoJsonLayer newYorkLayer = new GeoJsonLayer(googleMap, R.raw.ny, getContext());
                        newYorkLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("NY").getNewCasesSmoothed()));
                        newYorkLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        newYorkLayer.addLayerToMap();

                        // Ohio
                        GeoJsonLayer ohioLayer = new GeoJsonLayer(googleMap, R.raw.oh, getContext());
                        ohioLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("OH").getNewCasesSmoothed()));
                        ohioLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        ohioLayer.addLayerToMap();

                        // Oklahoma
                        GeoJsonLayer oklahomaLayer = new GeoJsonLayer(googleMap, R.raw.ok, getContext());
                        oklahomaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("OK").getNewCasesSmoothed()));
                        oklahomaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        oklahomaLayer.addLayerToMap();

                        // Oregon
                        GeoJsonLayer oregonLayer = new GeoJsonLayer(googleMap, R.raw.or, getContext());
                        oregonLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("OR").getNewCasesSmoothed()));
                        oregonLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        oregonLayer.addLayerToMap();

                        // Pennsylvania
                        GeoJsonLayer pennsylvaniaLayer = new GeoJsonLayer(googleMap, R.raw.pa, getContext());
                        pennsylvaniaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("PA").getNewCasesSmoothed()));
                        pennsylvaniaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        pennsylvaniaLayer.addLayerToMap();

                        // Rhode Island
                        GeoJsonLayer rhodeIslandLayer = new GeoJsonLayer(googleMap, R.raw.ri, getContext());
                        rhodeIslandLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("RI").getNewCasesSmoothed()));
                        rhodeIslandLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        rhodeIslandLayer.addLayerToMap();

                        // South Carolina
                        GeoJsonLayer southCarolinaLayer = new GeoJsonLayer(googleMap, R.raw.sc, getContext());
                        southCarolinaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("SC").getNewCasesSmoothed()));
                        southCarolinaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        southCarolinaLayer.addLayerToMap();

                        // South Dakota
                        GeoJsonLayer southDakotaLayer = new GeoJsonLayer(googleMap, R.raw.sd, getContext());
                        southDakotaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("SD").getNewCasesSmoothed()));
                        southDakotaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        southDakotaLayer.addLayerToMap();

                        // Tennessee
                        GeoJsonLayer tennesseeLayer = new GeoJsonLayer(googleMap, R.raw.tn, getContext());
                        tennesseeLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("TN").getNewCasesSmoothed()));
                        tennesseeLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        tennesseeLayer.addLayerToMap();

                        // Texas
                        GeoJsonLayer texasLayer = new GeoJsonLayer(googleMap, R.raw.tx, getContext());
                        texasLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("TX").getNewCasesSmoothed()));
                        texasLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        texasLayer.addLayerToMap();

                        // Utah
                        GeoJsonLayer utahLayer = new GeoJsonLayer(googleMap, R.raw.ut, getContext());
                        utahLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("UT").getNewCasesSmoothed()));
                        utahLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        utahLayer.addLayerToMap();

                        // Virginia
                        GeoJsonLayer virginiaLayer = new GeoJsonLayer(googleMap, R.raw.va, getContext());
                        virginiaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("VA").getNewCasesSmoothed()));
                        virginiaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        virginiaLayer.addLayerToMap();

                        // Vermont
                        GeoJsonLayer vermontLayer = new GeoJsonLayer(googleMap, R.raw.vt, getContext());
                        vermontLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("VT").getNewCasesSmoothed()));
                        vermontLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        vermontLayer.addLayerToMap();

                        // Washington
                        GeoJsonLayer washingtonLayer = new GeoJsonLayer(googleMap, R.raw.wa, getContext());
                        washingtonLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("WA").getNewCasesSmoothed()));
                        washingtonLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        washingtonLayer.addLayerToMap();

                        // Wisconsin
                        GeoJsonLayer wisconsinLayer = new GeoJsonLayer(googleMap, R.raw.wi, getContext());
                        wisconsinLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("WI").getNewCasesSmoothed()));
                        wisconsinLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        wisconsinLayer.addLayerToMap();

                        // West Virginia
                        GeoJsonLayer westVirginiaLayer = new GeoJsonLayer(googleMap, R.raw.wv, getContext());
                        westVirginiaLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("WV").getNewCasesSmoothed()));
                        westVirginiaLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        westVirginiaLayer.addLayerToMap();

                        // Wyoming
                        GeoJsonLayer wyomingLayer = new GeoJsonLayer(googleMap, R.raw.wy, getContext());
                        wyomingLayer.getDefaultPolygonStyle().setPolygonFillColor(getColor(stringCovidDataHashMap.get("WY").getNewCasesSmoothed()));
                        wyomingLayer.getDefaultPolygonStyle().setPolygonStrokeWidth(STOKE_WIDTH);
                        wyomingLayer.addLayerToMap();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(binding.map.getId());
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private int getColor(double confirmedCases) {
        double ratio = confirmedCases / US_BASELINE;

        if (ratio >= 0.1 && ratio <= 1) {
            return getResources().getColor(R.color.risk_moderate);
        } else if (ratio < 0.1) {
            return getResources().getColor(R.color.risk_low);
        } else {
            return getResources().getColor(R.color.risk_high);
        }
    }

    private int getNonUSColor(double confirmedCases) {
        double ratio = confirmedCases / BANGLADESH_BASELINE;

        if (ratio >= 0.1 && ratio <= 1) {
            return getResources().getColor(R.color.risk_moderate);
        } else if (ratio < 0.1) {
            return getResources().getColor(R.color.risk_low);
        } else {
            return getResources().getColor(R.color.risk_high);
        }
    }
}