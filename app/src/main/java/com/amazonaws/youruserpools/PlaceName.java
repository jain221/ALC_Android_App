package com.amazonaws.youruserpools;

public class PlaceName {
    private int id;
    private String StationName;
    private String CRS_code;

    public PlaceName(int id, String stationName, String CRS_code) {
        this.id = id;
        StationName = stationName;
        this.CRS_code = CRS_code;
    }

    public String getCRS_code() {
        return CRS_code;
    }

    public void setCRS_code(String CRS_code) {
        this.CRS_code = CRS_code;
    }

        public PlaceName(int id, String stationName) {
        this.id = id;
        StationName = stationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }
}
