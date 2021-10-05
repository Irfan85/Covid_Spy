package com.covid_spy.covidspy;

public class CovidData {
    private String location;
    private double populationDensity;
    private double totalCases;
    private double totalDeaths;
    private double newCasesSmoothed;
    private double newDeathsSmoothed;
    private double totalVaccination;

    public CovidData(String location, double populationDensity) {
        this.location = location;
        this.populationDensity = populationDensity;

        this.totalCases = -1;
        this.totalDeaths = -1;
        this.newCasesSmoothed = -1;
        this.newDeathsSmoothed = -1;
        this.totalVaccination = -1;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(double populationDensity) {
        this.populationDensity = populationDensity;
    }

    public double getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(double totalCases) {
        this.totalCases = totalCases;
    }

    public double getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(double totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public double getNewCasesSmoothed() {
        return newCasesSmoothed;
    }

    public void setNewCasesSmoothed(double newCasesSmoothed) {
        this.newCasesSmoothed = newCasesSmoothed;
    }

    public double getNewDeathsSmoothed() {
        return newDeathsSmoothed;
    }

    public void setNewDeathsSmoothed(double newDeathsSmoothed) {
        this.newDeathsSmoothed = newDeathsSmoothed;
    }

    public double getTotalVaccination() {
        return totalVaccination;
    }

    public void setTotalVaccination(double totalVaccination) {
        this.totalVaccination = totalVaccination;
    }
}
