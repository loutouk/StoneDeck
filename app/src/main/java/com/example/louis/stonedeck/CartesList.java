package com.example.louis.stonedeck;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Louis on 30/03/2018.
 * Used to send the Carte List between activities thanks to the Intent as a parcelable extra
 */

public class CartesList extends ArrayList<Carte> implements Parcelable {

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CartesList createFromParcel(Parcel in) {
            return new CartesList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    public CartesList() {

    }

    public CartesList(Parcel in) {
        this.getFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            Carte c = this.get(i); //On vient lire chaque objet Carte
            dest.writeString(c.cardId);
            dest.writeString(c.dbfId);
            dest.writeString(c.name);
            dest.writeString(c.cardSet);
            dest.writeString(c.type);
            dest.writeString(c.rarity);
            dest.writeValue(c.health);
            dest.writeValue(c.collectible);
            dest.writeString(c.playerClass);
            dest.writeString(c.img);
            dest.writeString(c.imgGold);
            dest.writeString(c.locale);
            dest.writeString(c.text);
            dest.writeTypedList(c.mechanics);
            dest.writeString(c.artist);
            dest.writeValue(c.attack);
            dest.writeString(c.race);
            dest.writeString(c.faction);
            dest.writeValue(c.cost);
            dest.writeString(c.flavor);
            dest.writeString(c.howToGet);
            dest.writeString(c.howToGetGold);
            dest.writeValue(c.durability);
            dest.writeValue(c.elite);
        }
    }

    public void getFromParcel(Parcel in) {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for (int i = 0; i < size; i++) {
            Carte c = new Carte();
            c.cardId = in.readString();
            c.dbfId = in.readString();
            c.name = in.readString();
            c.cardSet = in.readString();
            c.type = in.readString();
            c.rarity = in.readString();
            c.health = (Integer) in.readValue(Integer.class.getClassLoader());
            c.collectible = (Boolean) in.readValue(Boolean.class.getClassLoader());
            c.playerClass = in.readString();
            c.img = in.readString();
            c.imgGold = in.readString();
            c.locale = in.readString();
            c.text = in.readString();
            c.mechanics = in.createTypedArrayList(Mechanic.CREATOR);
            c.artist = in.readString();
            c.attack = (Integer) in.readValue(Integer.class.getClassLoader());
            c.race = in.readString();
            c.faction = in.readString();
            c.cost = (Integer) in.readValue(Integer.class.getClassLoader());
            c.flavor = in.readString();
            c.howToGet = in.readString();
            c.howToGetGold = in.readString();
            c.durability = (Integer) in.readValue(Integer.class.getClassLoader());
            c.elite = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.add(c);
        }

    }
}
