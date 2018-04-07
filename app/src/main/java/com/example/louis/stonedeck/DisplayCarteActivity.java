package com.example.louis.stonedeck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Louis on 31/03/2018.
 */

public class DisplayCarteActivity extends AppCompatActivity {

    private TextView name;
    private TextView infos;
    private TextView flavor;
    private ImageView image;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_carte);

        // The card the user clicked on
        final Carte card = getIntent().getExtras().getParcelable("card");

        name = (TextView) findViewById(R.id.textViewCardName);
        infos = (TextView) findViewById(R.id.textViewInfoCard);
        image = (ImageView) findViewById(R.id.imageViewCardImg);
        flavor = (TextView) findViewById(R.id.textViewFlavor);
        spinner = (Spinner) findViewById(R.id.spinnerDeckAdd);

        // Populate our fields from the Card attribute
        name.setText(card.getName());
        flavor.setText(card.getFlavor());
        Picasso.with(getBaseContext()).load(card.getImg()).error(R.drawable.cardbackdefault).fit().centerInside().into(image);
        String infoStr = "";
        infoStr += "CLASS: " + card.getPlayerClass() + "\n";
        infoStr += "TYPE: " + card.getType() + "\n";
        infoStr += "SET: " + card.getCardSet() + "\n";
        infoStr += "FACTION: " + card.getFaction() + "\n";
        infoStr += "ARTIST: " + card.getArtist() + "\n";
        infos.setText(infoStr);

        // Fill in the spinner with the user decks by loading the DeckCollection serialized file
        ArrayList<String> deckList = new ArrayList<>();
        DeckCollection deckCollection = DeckManagerSingleton.getInstance().load(getBaseContext());
        if (deckCollection != null) {
            for (Deck d : deckCollection.getDecks()) deckList.add(d.getName());
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deckList);
            spinner = (Spinner) findViewById(R.id.spinnerDeckAdd);
            spinner.setAdapter(aa);
        }


        // Adding the card to the selected deck when the user click on the corresponding button
        Button addButton = (Button) findViewById(R.id.buttonAddCardDeck);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeckCollection deckCollection = DeckManagerSingleton.getInstance().load(getBaseContext());
                if (deckCollection != null) {
                    // Search the deck from the serialized file by checking their names with the selected one from spinner
                    for (Deck d : deckCollection.getDecks()) {
                        if (d.getName().trim().equals(spinner.getSelectedItem().toString().trim())) {
                            // Add the card to the deck
                            d.addCard(card);
                            // Save to the file
                            DeckManagerSingleton.getInstance().save(deckCollection, getBaseContext());
                            Toast.makeText(DisplayCarteActivity.this, card.getName() + " added to " + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });


    }
}