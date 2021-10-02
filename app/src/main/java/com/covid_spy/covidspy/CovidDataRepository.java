package com.covid_spy.covidspy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public final class CovidDataRepository {
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static final String LOG_TAG = CovidDataRepository.class.getSimpleName();

    private static final String NON_US_COVID_QUERY_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/latest/owid-covid-latest.json";
    private static final String US_COVID_QUERY_URL = "https://api.covidactnow.org/v2/states.json?apiKey=155eb52698244dbb9aac1ab01a47ddd4";
    
    public interface UpdateUICallback {
        void onCompleted(HashMap<String, CovidData> nonUSData, HashMap<String, CovidData> usData);
    }

    public static void extractCovidData(final UpdateUICallback callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, CovidData> nonUS = new HashMap<>();
                HashMap<String, CovidData> us = new HashMap<>();

                try {
                    JSONObject nonUSJson = fetchNonUSJson();

                    if(nonUSJson != null) {
                        Iterator<String> countries = nonUSJson.keys();
                        while (countries.hasNext()) {
                            String key = countries.next();
                            String location = nonUSJson.getJSONObject(key).getString("location");

                            double populationDensity = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("population_density")) {
                                populationDensity = nonUSJson.getJSONObject(key).getDouble("population_density");
                            }

                            double totalCases = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("total_cases")) {
                                totalCases = nonUSJson.getJSONObject(key).getDouble("total_cases");
                            }

                            double totalDeaths = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("total_deaths")) {
                                totalDeaths = nonUSJson.getJSONObject(key).getDouble("total_deaths");
                            }

                            double newCasesSmoothed = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("new_cases_smoothed")) {
                                newCasesSmoothed = nonUSJson.getJSONObject(key).getDouble("new_cases_smoothed");
                            }

                            double newDeathsSmoothed = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("new_deaths_smoothed")) {
                                newDeathsSmoothed = nonUSJson.getJSONObject(key).getDouble("new_deaths_smoothed");
                            }

                            double totalVaccination = 0;
                            if(!nonUSJson.getJSONObject(key).isNull("total_vaccinations")) {
                                totalVaccination = nonUSJson.getJSONObject(key).getDouble("total_vaccinations");
                            }

                            CovidData newData = new CovidData(location, populationDensity);
                            newData.setTotalCases(totalCases);
                            newData.setTotalDeaths(totalDeaths);
                            newData.setNewCasesSmoothed(newCasesSmoothed);
                            newData.setNewDeathsSmoothed(newDeathsSmoothed);
                            newData.setTotalVaccination(totalVaccination);

                            nonUS.put(key, newData);
                        }
                    }

                    // US Part
                    JSONObject usJson = fetchUSJson();

                    if(usJson != null) {
                        JSONArray states = usJson.getJSONArray("states");
                        for (int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            String key = state.getString("state");
                            String location = state.getString("country");

                            double populationDensity = 0;

                            double totalCases = state.getJSONObject("actuals").getDouble("cases");

                            double totalDeaths = state.getJSONObject("actuals").getDouble("deaths");

                            double newCasesSmoothed = state.getJSONObject("actuals").getDouble("newCases");

                            double newDeathsSmoothed = state.getJSONObject("actuals").getDouble("newDeaths");

                            double totalVaccination = state.getJSONObject("actuals").getDouble("vaccinationsCompleted");

                            CovidData newData = new CovidData(location, populationDensity);
                            newData.setTotalCases(totalCases);
                            newData.setTotalDeaths(totalDeaths);
                            newData.setNewCasesSmoothed(newCasesSmoothed);
                            newData.setNewDeathsSmoothed(newDeathsSmoothed);
                            newData.setTotalVaccination(totalVaccination);

                            us.put(key, newData);
                        }
                    }

                    callback.onCompleted(nonUS, us);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static JSONObject fetchNonUSJson() throws IOException, JSONException {
        URL nonUSUrl = new URL(NON_US_COVID_QUERY_URL);

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        StringBuilder jsonStringBuilder = new StringBuilder();

        try {
            urlConnection = (HttpsURLConnection) nonUSUrl.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }

                bufferedReader.close();
            } else {
                Log.e(LOG_TAG, "Connection Error-NON US: Unexpected response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception Occurred", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        if (jsonStringBuilder.length() == 0) {
            return null;
        } else {
            return new JSONObject(jsonStringBuilder.toString());
        }
    }

    private static JSONObject fetchUSJson() throws IOException, JSONException {
        URL usUrl = new URL(US_COVID_QUERY_URL);

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        StringBuilder jsonStringBuilder = new StringBuilder();
        jsonStringBuilder.append("{states:");

        try {
            urlConnection = (HttpsURLConnection) usUrl.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }

                bufferedReader.close();
            } else {
                Log.e(LOG_TAG, "Connection Error-US: Unexpected response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception Occurred", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        if (jsonStringBuilder.length() == 0) {
            return null;
        } else {
            jsonStringBuilder.append("}");
            return new JSONObject(jsonStringBuilder.toString());
        }
    }
}
