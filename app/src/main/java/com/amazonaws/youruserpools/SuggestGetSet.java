package com.amazonaws.youruserpools;

public class SuggestGetSet {

    String Id,Station;
    public SuggestGetSet(String Id, String Station){
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
