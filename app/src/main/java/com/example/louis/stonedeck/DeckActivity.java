package com.example.louis.stonedeck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeckActivity extends AppCompatActivity {

    private DeckCollection deckCollection;
    private ListView lv;
    private DeckAdapter arrayAdapter;
    private ArrayList<Deck> deckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        lv = (ListView) findViewById(R.id.listViewDecks);
        deckCollection = new DeckCollection();
        deckList = new ArrayList<>();
        arrayAdapter = new DeckAdapter(this, deckList);
        lv.setAdapter(arrayAdapter);

        DeckCollection sauvegarde = DeckManagerSingleton.getInstance().load(getBaseContext());

        // No save file
        if (sauvegarde == null) {
            Toast.makeText(getBaseContext(), "No deck found !", Toast.LENGTH_LONG).show();
        }
        // Load save file
        else {
            deckCollection = sauvegarde;
            deckList.addAll(deckCollection.getDecks());
            arrayAdapter.notifyDataSetChanged();
        }

        Button addButton = (Button) findViewById(R.id.buttonAddDeck);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText textViewDeckName = (EditText) findViewById(R.id.editTextDeckName);
                String deckName = textViewDeckName.getText().toString().trim();
                // Cant create a deck with empty name
                if (deckName != null && deckName.length() > 0) {
                    // Empty the field
                    textViewDeckName.setText("");
                    Deck newDeck = new Deck(deckName);
                    deckCollection.addDeck(newDeck);
                    DeckManagerSingleton.getInstance().save(deckCollection, getBaseContext());
                    deckList.add(newDeck);
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getBaseContext(), "Enter a deck name", Toast.LENGTH_LONG).show();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (deckList.get(position) != null) {
                    Intent myIntent = new Intent(DeckActivity.this, DisplayQueryActivity.class);
                    CartesList parcelableCards = new CartesList();
                    for (Carte c : deckList.get(position).getCards()) {
                        parcelableCards.add(c);
                    }
                    myIntent.putExtra("cards", (Parcelable) parcelableCards);
                    DeckActivity.this.startActivity(myIntent);
                }
            }
        });
    }


}
