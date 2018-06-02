package com.example.a1.whereami;

public class Station {
    private int bosstopId;
    private int distance;
    private String busstopname;
    private String stoptype;

    public Station(int bosstopId, int destination, String busstopname, String stoptype) {
        this.bosstopId = bosstopId;
        this.distance = destination;
        this.busstopname = busstopname;
        this.stoptype = stoptype;
    }

    public int getBosstopId() {
        return bosstopId;
    }

    public void setBosstopId(int bosstopId) {
        this.bosstopId = bosstopId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int destination) {
        this.distance = destination;
    }

    public String getBusstopname() {
        return busstopname;
    }

    public void setBusstopname(String busstopname) {
        this.busstopname = busstopname;
    }

    public String getStoptype() {
        return stoptype;
    }

    public void setStoptype(String stoptype) {
        this.stoptype = stoptype;
    }
}
