package com.example.louis.stonedeck;

/**
 * Created by Louis on 28/03/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Mechanic implements Parcelable, Serializable {

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Mechanic createFromParcel(Parcel in) {
            return new Mechanic(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
    @Json(name = "name")
    public String name;

    /**
     * No args constructor for use in serialization
     */
    public Mechanic() {
    }

    /**
     * @param name
     */
    public Mechanic(String name) {
        super();
        this.name = name;
    }

    //Second constructeur qui sera appelé lors de la "Deparcelablisation"
    public Mechanic(Parcel in) {
        this.getFromParcel(in);
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in) {
        this.setName(in.readString());
    }
}
