package com.example.louis.stonedeck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.TransactionTooLargeException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartesActivity extends AppCompatActivity {

    // As the purpose of this app is to search card and create decks, the main search is by class
    // Though differents request are available on https://market.mashape.com/omgvamp/hearthstone
    private final String JSONCardsString = "https://omgvamp-hearthstone-v1.p.mashape.com/cards/classes/";
    // Some spinner have a "all races" or "all factions" item so we use a pattern to identify a lack of choice
    private final String leftBlankPattern = "All";
    // Hearthstone API key from https://market.mashape.com/omgvamp/hearthstone
    private final String myKey = "ZhgRNoSBpRmshex0Y18bLAH9QUkap1O9zuZjsnTN2dZT2KFx3e";
    // Hearthstone static datas
    // TODO think about import it automatically via GET info REST API
    // TODO Didn't do it yet because some informations were useless or unworkable
    private final String[] cardTypes = new String[]{
            leftBlankPattern,
            "Hero",
            "Minion",
            "Spell",
            "Enchantment",
            "Weapon",
            "Hero Power"};
    private final String[] cardSets = new String[]{
            leftBlankPattern,
            "Basic",
            "Classic",
            "Promo",
            "Hall of Fame",
            "Naxxramas",
            "Goblins vs Gnomes",
            "Blackrock Mountain",
            "The Grand Tournament",
            "The League of Explorers",
            "Whispers of the Old Gods",
            "One Night in Karazhan",
            "Mean Streets of Gadgetzan",
            "Journey to Un'Goro",
            "Knights of the Frozen Throne",
            "Kobolds & Catacombs",
            "Tavern Brawl",
            "Hero Skins",
            "Missions",
            "Credits",
            "System",
            "Debug"};
    private final String[] cardRaces = new String[]{
            leftBlankPattern,
            "Demon",
            "Dragon",
            "Elemental",
            "Mech",
            "Murloc",
            "Beast",
            "Pirate",
            "Totem"};
    private final String[] cardClasses = new String[]{
            "Death Knight",
            "Druid",
            "Hunter",
            "Mage",
            "Paladin",
            "Priest",
            "Rogue",
            "Shaman",
            "Warlock",
            "Warrior",
            "Dream",
            "Neutral"};
    private final String[] cardRaretes = new String[]{
            leftBlankPattern,
            "Free",
            "Common",
            "Rare",
            "Epic",
            "Legendary"};
    // Displays loading animation to inform the user the app is searching for cards
    // Explicit boolean declaration, we are ready to load JSON
    private boolean isLoadingJSON = false;
    // Form inputs
    private ProgressBar progressBar;
    private FloatingActionButton searchButton;
    private TextView cardName;
    private Spinner typeSpinner;
    private Spinner setSpinner;
    private Spinner raceSpinner;
    private Spinner classeSpinner;
    private Spinner raritySpinner;

    private List<String> spinnerType = new ArrayList<>();
    private List<String> spinnerSet = new ArrayList<>();
    private List<String> spinnerRace = new ArrayList<>();
    private List<String> spinnerClasse = new ArrayList<>();
    private List<String> spinnerRarete = new ArrayList<>();

    // ArrayAdapters
    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> setAdapter;
    private ArrayAdapter<String> raceAdapter;
    private ArrayAdapter<String> classeAdapter;
    private ArrayAdapter<String> rareteAdapter;

    // TODO Think about the search algorithm to optimize it
    // TODO Use hashmap to correlate the french text input search form with the english REST search text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartes);
        // Retrieving the inputs
        cardName = (TextView) findViewById(R.id.editTextNomCarte);
        searchButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Hide the progressbar
        progressBar.setVisibility(View.GONE);

        Collections.addAll(spinnerType, cardTypes);
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerType);
        typeSpinner = (Spinner) findViewById(R.id.spinnerTypeCarte);
        typeSpinner.setAdapter(typeAdapter);

        Collections.addAll(spinnerSet, cardSets);
        setAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerSet);
        setSpinner = (Spinner) findViewById(R.id.spinnerSetCarte);
        setSpinner.setAdapter(setAdapter);

        Collections.addAll(spinnerRace, cardRaces);
        raceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerRace);
        raceSpinner = (Spinner) findViewById(R.id.spinnerRaceCarte);
        raceSpinner.setAdapter(raceAdapter);

        Collections.addAll(spinnerClasse, cardClasses);
        classeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerClasse);
        classeSpinner = (Spinner) findViewById(R.id.spinnerClasseCarte);
        classeSpinner.setAdapter(classeAdapter);

        Collections.addAll(spinnerRarete, cardRaretes);
        rareteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerRarete);
        raritySpinner = (Spinner) findViewById(R.id.spinnerRareteCarte);
        raritySpinner.setAdapter(rareteAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Block search button while searching
                if (!isLoadingJSON) {
                    isLoadingJSON = true;
                    // We will hide the snack bar or loading bar on JSON response
                    progressBar.setVisibility(View.VISIBLE);
                    // Build the url from the user input and the given url
                    String url = JSONCardsString + classeSpinner.getSelectedItem().toString();
                    try {
                        requestJSONCards(url);
                    } catch (TransactionTooLargeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void requestJSONCards(String request) throws TransactionTooLargeException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = request;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        // Log.d("DEBUGJSON " , response);
                        List<Carte> cardsFromJSON = parseJSONCards(response);
                        // Now we have to sort out cards to keep only those that match what the user enter
                        ArrayList<Carte> cardsToSort = new ArrayList<>();
                        cardsToSort.addAll(cardsFromJSON);
                        sortCardsByInput(cardsToSort);

                        // TODO When there are too much datas to parcel between Activity, there is a TransactionTooLargeException
                        // TODO Right now, we're just removing some Carte from the Arraylist
                        // TODO So some results will not appear if the result is too large
                        // TODO Display Toast
                        if (cardsToSort.size() > 500) {
                            List<Carte> cutOutList = new ArrayList<>(cardsToSort.subList(0, 500));
                            cardsToSort.clear();
                            cardsToSort.addAll(cutOutList);
                            Toast.makeText(CartesActivity.this, "Too many cards. Show only 500.", Toast.LENGTH_LONG).show();
                        }

                        loadDisplayQueryActivity(cardsToSort);
                        // hide the loading bar
                        isLoadingJSON = false;
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hide the loading bar
                        Log.d("ERROR", "error => " + error.toString());
                        Toast.makeText(CartesActivity.this, "ERROR. " + error.toString(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        isLoadingJSON = false;

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", myKey);
                return params;
            }
        };
        queue.add(getRequest);

    }

    private void loadDisplayQueryActivity(List<Carte> cardsToReturn) {
        Intent myIntent = new Intent(CartesActivity.this, DisplayQueryActivity.class);
        CartesList parcelableCards = new CartesList();
        for (Carte c : cardsToReturn) {
            parcelableCards.add(c);
        }
        myIntent.putExtra("cards", (Parcelable) parcelableCards);
        CartesActivity.this.startActivity(myIntent);
    }

    public List<Carte> parseJSONCards(String JSON) {
        String cardsJsonResponse = JSON;
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Carte.class);
        JsonAdapter<List<Carte>> adapter = moshi.adapter(type);
        List<Carte> cardsToReturn = null;
        try {
            cardsToReturn = adapter.fromJson(cardsJsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardsToReturn;
    }

    public void sortCardsByInput(ArrayList<Carte> toSort) {
        boolean checkName = false;
        boolean checkType = false;
        boolean checkSet = false;
        boolean checkRace = false;
        boolean checkRarity = false;

        String selectedName = cardName.getText().toString().trim().toUpperCase();
        String selectedType = typeSpinner.getSelectedItem().toString().trim();
        String selectedSet = setSpinner.getSelectedItem().toString().trim();
        String selectedRace = raceSpinner.getSelectedItem().toString().trim();
        String selectedRarity = raritySpinner.getSelectedItem().toString().trim();

        if (selectedName.length() > 0) checkName = true;
        if (selectedType != leftBlankPattern) checkType = true;
        if (selectedSet != leftBlankPattern) checkSet = true;
        if (selectedRace != leftBlankPattern) checkRace = true;
        if (selectedRarity != leftBlankPattern) checkRarity = true;

        for (int i = 0; i < toSort.size(); i++) {
            Carte c = toSort.get(i);
            if (
                    (((checkName && c.getName() == null) || checkName && !c.getName().toUpperCase().contains(selectedName))) ||
                            ((checkType && c.getType() == null) || checkType && !c.getType().equals(selectedType)) ||
                            ((checkSet && c.getCardSet() == null) || checkSet && !c.getCardSet().equals(selectedSet)) ||
                            ((checkRace && c.getRace() == null) || checkRace && !c.getRace().equals(selectedRace)) ||
                            ((checkRarity && c.getRarity() == null) || checkRarity && !c.getRarity().equals(selectedRarity))
                    ) {
                toSort.remove(c);
                i--;
                continue;
            }
        }

    }
}
