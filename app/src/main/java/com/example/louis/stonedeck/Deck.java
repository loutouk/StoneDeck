package com.example.louis.stonedeck;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Louis on 02/04/2018.
 */

public class Deck implements Serializable {

    private String name;
    private ArrayList<Carte> cards;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Carte card) {
        this.cards.add(card);
    }

    public void removeCard(Carte card) {
        this.cards.remove(card);
    }

    public ArrayList<Carte> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Carte> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return name;
    }
}
