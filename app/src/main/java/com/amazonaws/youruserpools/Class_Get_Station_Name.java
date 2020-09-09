package com.amazonaws.youruserpools;

public class Class_Get_Station_Name {

    String Id,Station,crs;
//    public Class_Get_Station_Name(String Id, String Station){
//        this.setId(Id);
//        this.setStation(Station);
//    }
//    public String getId() {
//        return Id;
//    }
//
//    public void setId(String Id) {
//        this.Id = Id;
//    }
//
//    public String getStation() {
//        return Station;
//    }
//
//    public void setStation(String Station) {
//        this.Station = Station;
//    }


    public Class_Get_Station_Name(String id, String station, String crs) {
        Id = id;
        Station = station;
        this.crs = crs;
    }

    public Class_Get_Station_Name(String Id, String Station){
        this.setId(Id);
        this.setStation(Station);
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }
}
