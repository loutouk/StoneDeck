package com.example.louis.stonedeck;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Louis on 31/03/2018.
 */

public class DeckManagerSingleton {

    private static final String fileName = "saveFile";
    private static DeckManagerSingleton ourInstance = new DeckManagerSingleton();

    // The private field prevents the user from creating another instance
    private DeckManagerSingleton() {
    }

    public static DeckManagerSingleton getInstance() {
        return ourInstance;
    }

    public DeckCollection load(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(DeckManagerSingleton.fileName);
            // File does not exist
            if (fis == null) return null;
        } catch (FileNotFoundException e) {
            // File does not exist
            e.printStackTrace();
            return null;
        }
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeckCollection decks = null;
        try {
            decks = (DeckCollection) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decks;
    }

    public void save(DeckCollection metier, Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(DeckManagerSingleton.fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.writeObject(metier);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
