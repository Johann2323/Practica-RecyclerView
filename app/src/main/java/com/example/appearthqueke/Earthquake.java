package com.example.appearthqueke;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Earthquake implements Parcelable {
    private String id;
    private String place;
    private double magnitude;
    private int time;
    private double latitude;
    private double longitude;

    public Earthquake() {
    }

    public Earthquake(String id, String place, double magnitude, int time, double latitude, double longitude) {
        this.id = id;
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Earthquake(Parcel in) {
        id = in.readString();
        place = in.readString();
        magnitude = in.readDouble();
        time = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Earthquake> CREATOR = new Creator<Earthquake>() {
        @Override
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        @Override
        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
//mod
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(that.magnitude, magnitude) == 0 && time == that.time && Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && id.equals(that.id) && place.equals(that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitude, time, latitude, longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(place);
        dest.writeDouble(magnitude);
        dest.writeInt(time);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
