package com.amazonaws.youruserpools;

public class Class_Get_Station_Name {

    String Id,Station;
    public Class_Get_Station_Name(String Id, String Station){
        this.setId(Id);
        this.setStation(Station);
    }
    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getStation() {
        return Station;
    }

    public void setStation(String Station) {
        this.Station = Station;
    }

}
