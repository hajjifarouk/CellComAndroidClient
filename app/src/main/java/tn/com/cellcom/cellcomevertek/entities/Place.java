package tn.com.cellcom.cellcomevertek.entities;

/**
 * Created by farouk on 06/07/2017.
 */

public class Place {
    private double longitude;
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
