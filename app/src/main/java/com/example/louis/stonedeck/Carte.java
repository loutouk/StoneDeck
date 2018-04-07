package com.example.louis.stonedeck;

/**
 * Created by Louis on 28/03/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class Carte implements Parcelable, Comparable<Carte>, Serializable {

    public static final Parcelable.Creator<Carte> CREATOR = new Parcelable.Creator<Carte>() {
        @Override
        public Carte createFromParcel(Parcel source) {
            return new Carte(source);
        }

        @Override
        public Carte[] newArray(int size) {
            return new Carte[size];
        }
    };

    @Json(name = "cardId")
    public String cardId;
    @Json(name = "dbfId")
    public String dbfId;
    @Json(name = "name")
    public String name;
    @Json(name = "cardSet")
    public String cardSet;
    @Json(name = "type")
    public String type;
    @Json(name = "rarity")
    public String rarity;
    @Json(name = "health")
    public Integer health;
    @Json(name = "collectible")
    public Boolean collectible;
    @Json(name = "playerClass")
    public String playerClass;
    @Json(name = "img")
    public String img;
    @Json(name = "imgGold")
    public String imgGold;
    @Json(name = "locale")
    public String locale;
    @Json(name = "text")
    public String text;
    @Json(name = "mechanics")
    public List<Mechanic> mechanics = null;
    @Json(name = "artist")
    public String artist;
    @Json(name = "attack")
    public Integer attack;
    @Json(name = "race")
    public String race;
    @Json(name = "faction")
    public String faction;
    @Json(name = "cost")
    public Integer cost;
    @Json(name = "flavor")
    public String flavor;
    @Json(name = "howToGet")
    public String howToGet;
    @Json(name = "howToGetGold")
    public String howToGetGold;
    @Json(name = "durability")
    public Integer durability;
    @Json(name = "elite")
    public Boolean elite;

    /**
     * No args constructor for use in serialization
     */
    public Carte() {
    }

    /**
     * @param cardId
     * @param text
     * @param durability
     * @param locale
     * @param elite
     * @param flavor
     * @param rarity
     * @param img
     * @param faction
     * @param cardSet
     * @param type
     * @param health
     * @param cost
     * @param race
     * @param howToGetGold
     * @param imgGold
     * @param name
     * @param mechanics
     * @param collectible
     * @param playerClass
     * @param dbfId
     * @param howToGet
     * @param attack
     * @param artist
     */
    public Carte(String cardId, String dbfId, String name, String cardSet, String type, String rarity, Integer health, Boolean collectible, String playerClass, String img, String imgGold, String locale, String text, List<Mechanic> mechanics, String artist, Integer attack, String race, String faction, Integer cost, String flavor, String howToGet, String howToGetGold, Integer durability, Boolean elite) {
        super();
        this.cardId = cardId;
        this.dbfId = dbfId;
        this.name = name;
        this.cardSet = cardSet;
        this.type = type;
        this.rarity = rarity;
        this.health = health;
        this.collectible = collectible;
        this.playerClass = playerClass;
        this.img = img;
        this.imgGold = imgGold;
        this.locale = locale;
        this.text = text;
        this.mechanics = mechanics;
        this.artist = artist;
        this.attack = attack;
        this.race = race;
        this.faction = faction;
        this.cost = cost;
        this.flavor = flavor;
        this.howToGet = howToGet;
        this.howToGetGold = howToGetGold;
        this.durability = durability;
        this.elite = elite;
    }

    protected Carte(Parcel in) {
        this.cardId = in.readString();
        this.dbfId = in.readString();
        this.name = in.readString();
        this.cardSet = in.readString();
        this.type = in.readString();
        this.rarity = in.readString();
        this.health = (Integer) in.readValue(Integer.class.getClassLoader());
        this.collectible = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.playerClass = in.readString();
        this.img = in.readString();
        this.imgGold = in.readString();
        this.locale = in.readString();
        this.text = in.readString();
        this.mechanics = in.createTypedArrayList(Mechanic.CREATOR);
        this.artist = in.readString();
        this.attack = (Integer) in.readValue(Integer.class.getClassLoader());
        this.race = in.readString();
        this.faction = in.readString();
        this.cost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.flavor = in.readString();
        this.howToGet = in.readString();
        this.howToGetGold = in.readString();
        this.durability = (Integer) in.readValue(Integer.class.getClassLoader());
        this.elite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carte)) return false;

        Carte carte = (Carte) o;

        return cardId != null ? cardId.equals(carte.cardId) : carte.cardId == null;
    }

    @Override
    public int hashCode() {
        return cardId != null ? cardId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", cardSet='" + cardSet + '\'' +
                ", type='" + type + '\'' +
                ", rarity='" + rarity + '\'' +
                ", health=" + health +
                ", collectible=" + collectible +
                ", playerClass='" + playerClass + '\'' +
                ", text='" + text + '\'' +
                ", mechanics=" + mechanics +
                ", artist='" + artist + '\'' +
                ", attack=" + attack +
                ", race='" + race + '\'' +
                ", faction='" + faction + '\'' +
                ", cost=" + cost +
                ", flavor='" + flavor + '\'' +
                ", elite=" + elite +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardId);
        dest.writeString(this.dbfId);
        dest.writeString(this.name);
        dest.writeString(this.cardSet);
        dest.writeString(this.type);
        dest.writeString(this.rarity);
        dest.writeValue(this.health);
        dest.writeValue(this.collectible);
        dest.writeString(this.playerClass);
        dest.writeString(this.img);
        dest.writeString(this.imgGold);
        dest.writeString(this.locale);
        dest.writeString(this.text);
        dest.writeTypedList(this.mechanics);
        dest.writeString(this.artist);
        dest.writeValue(this.attack);
        dest.writeString(this.race);
        dest.writeString(this.faction);
        dest.writeValue(this.cost);
        dest.writeString(this.flavor);
        dest.writeString(this.howToGet);
        dest.writeString(this.howToGetGold);
        dest.writeValue(this.durability);
        dest.writeValue(this.elite);
    }

    public String getCardId() {
        return cardId;
    }

    public String getDbfId() {
        return dbfId;
    }

    public String getName() {
        return name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public Integer getHealth() {
        return health;
    }

    public Boolean getCollectible() {
        return collectible;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public String getImg() {
        return img;
    }

    public String getImgGold() {
        return imgGold;
    }

    public String getLocale() {
        return locale;
    }

    public String getText() {
        return text;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getAttack() {
        return attack;
    }

    public String getRace() {
        return race;
    }

    public String getFaction() {
        return faction;
    }

    public Integer getCost() {
        return cost;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public String getHowToGetGold() {
        return howToGetGold;
    }

    public Integer getDurability() {
        return durability;
    }

    public Boolean getElite() {
        return elite;
    }

    @Override
    public int compareTo(@NonNull Carte carte) {
        // Cost sort
        return carte.cost.compareTo(this.cost);
    }
}