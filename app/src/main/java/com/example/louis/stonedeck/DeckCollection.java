package com.example.louis.stonedeck;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Louis on 02/04/2018.
 */

public class DeckCollection implements Serializable {

    private ArrayList<Deck> decks = new ArrayList<>();

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void removeDeck(Deck toRemove) {
        for (Deck aDeck : decks) {
            if (aDeck.getName().equals(toRemove.getName())) {
                decks.remove(aDeck);
                return;
            }
        }
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    @Override
    public String toString() {
        return "DeckCollection{" +
                "decks=" + decks.toString() +
                '}';
    }
}
