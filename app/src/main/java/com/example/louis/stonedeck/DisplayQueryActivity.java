package com.example.louis.stonedeck;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayQueryActivity extends AppCompatActivity {

    private final String[] availableSorts = new String[]{"Name", "Cost", "Attack", "Health"};
    private final int nameSortIndex = 0;
    private final int costSortIndex = 1;
    private final int attackSortIndex = 2;
    private final int healthSortIndex = 3;
    private ListView listView;
    private CarteAdapter mAdapter;
    private ArrayList<Carte> cards;
    private Spinner spinnerSortChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_display);

        listView = (ListView) findViewById(R.id.cards_list);
        spinnerSortChoice = (Spinner) findViewById(R.id.spinnerSortChoice);

        List<String> sortList = new ArrayList<>();
        Collections.addAll(sortList, availableSorts);
        ArrayAdapter aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortList);
        spinnerSortChoice.setAdapter(aa);


        cards = this.getIntent().getExtras().getParcelable("cards");
        // Populating cards data into Listview
        mAdapter = new CarteAdapter(this, cards);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cards.get(position) != null) {
                    Intent myIntent = new Intent(DisplayQueryActivity.this, DisplayCarteActivity.class);
                    myIntent.putExtra("card", (Parcelable) cards.get(position));
                    DisplayQueryActivity.this.startActivity(myIntent);
                }
            }
        });

        spinnerSortChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected = spinnerSortChoice.getSelectedItem().toString();
                if (selected.trim().equals(availableSorts[nameSortIndex])) {
                    Collections.sort(cards, new Comparator<Carte>() {
                        @Override
                        public int compare(Carte o1, Carte o2) {
                            if (o1.getName() == null && o2.getName() == null) return 0;
                            if (o1.getName() == null) return -1;
                            if (o2.getName() == null) return 1;
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                } else if (selected.trim().equals(availableSorts[costSortIndex])) {
                    Collections.sort(cards, new Comparator<Carte>() {
                        @Override
                        public int compare(Carte o1, Carte o2) {
                            if (o1.getCost() == null && o2.getCost() == null) return 0;
                            if (o1.getCost() == null) return -1;
                            if (o2.getCost() == null) return 1;
                            return o1.getCost().compareTo(o2.getCost());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                } else if (selected.trim().equals(availableSorts[attackSortIndex])) {
                    Collections.sort(cards, new Comparator<Carte>() {
                        @Override
                        public int compare(Carte o1, Carte o2) {
                            if (o1.getAttack() == null && o2.getAttack() == null) return 0;
                            if (o1.getAttack() == null) return -1;
                            if (o2.getAttack() == null) return 1;
                            return o1.getAttack().compareTo(o2.getAttack());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                } else if (selected.trim().equals(availableSorts[healthSortIndex])) {
                    Collections.sort(cards, new Comparator<Carte>() {
                        @Override
                        public int compare(Carte o1, Carte o2) {
                            if (o1.getHealth() == null && o2.getHealth() == null) return 0;
                            if (o1.getHealth() == null) return -1;
                            if (o2.getHealth() == null) return 1;
                            return o1.getHealth().compareTo(o2.getHealth());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }
}
