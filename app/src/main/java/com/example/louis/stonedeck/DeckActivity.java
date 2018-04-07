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

    // Used to separate the deck name and the informations about it
    // Should not be contains in the deck name
    public static final String DECK_NAME_SEPARATOR = "-";

    private DeckCollection deckCollection;
    private ListView lv;
    private DeckAdapter arrayAdapter;
    private ArrayList<Deck> decksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        lv = (ListView) findViewById(R.id.listViewDecks);
        deckCollection = new DeckCollection();
        decksList = new ArrayList<>();
        arrayAdapter = new DeckAdapter(this, decksList);
        lv.setAdapter(arrayAdapter);

        DeckCollection sauvegarde = DeckManagerSingleton.getInstance().load(getBaseContext());

        // No save file
        if (sauvegarde == null) {
            Toast.makeText(getBaseContext(), "No deck found !", Toast.LENGTH_SHORT).show();
        }
        // Load save file
        else {
            deckCollection = sauvegarde;
            decksList.addAll(deckCollection.getDecks());
            arrayAdapter.notifyDataSetChanged();
        }

        Button addButton = (Button) findViewById(R.id.buttonAddDeck);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText textViewDeckName = (EditText) findViewById(R.id.editTextDeckName);
                String deckName = textViewDeckName.getText().toString().trim();
                // Cant create a deck with empty name
                if (deckName != null && deckName.length() > 0) {

                    if(deckName.contains(DeckActivity.DECK_NAME_SEPARATOR)){
                        Toast.makeText(getBaseContext(), "Please don't use " + DeckActivity.DECK_NAME_SEPARATOR + " in the deck name", Toast.LENGTH_SHORT).show();
                    }else{
                        // Each deck has to have a unique name in order to delete them safely
                        boolean nameAlreadyExists = false;
                        for(Deck d : decksList){
                            if(d.getName().equals(deckName)){
                                nameAlreadyExists = true;
                            }
                        }
                        if(nameAlreadyExists){
                            Toast.makeText(getBaseContext(), deckName + " already exists !", Toast.LENGTH_SHORT).show();
                        }else{
                            // Empty the field
                            textViewDeckName.setText("");
                            Deck newDeck = new Deck(deckName);
                            deckCollection.addDeck(newDeck);
                            DeckManagerSingleton.getInstance().save(deckCollection, getBaseContext());
                            decksList.add(newDeck);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Enter a deck name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (decksList.get(position) != null) {
                    Intent myIntent = new Intent(DeckActivity.this, DisplayQueryActivity.class);
                    CartesList parcelableCards = new CartesList();
                    for (Carte c : decksList.get(position).getCards()) {
                        parcelableCards.add(c);
                    }
                    myIntent.putExtra(DisplayQueryActivity.CARDS_LIST_INTENT_INDEX, (Parcelable) parcelableCards);
                    DeckActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO reload the deck
    }
}
