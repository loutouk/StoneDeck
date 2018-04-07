package com.example.louis.stonedeck;

/**
 * Created by Louis on 05/04/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends ArrayAdapter<Deck> {

    private Context mContext;
    private List<Deck> decksList = new ArrayList<>();

    public DeckAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Deck> list) {
        super(context, R.layout.deck_item, list);
        mContext = context;
        decksList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItem = inflater.inflate(R.layout.deck_item, null);
        }

        // Get the data item for this position
        // TODO the final for the Deck is dirty coding in order to dell the Deck when user click on the corresponding bin
        // TODO see problems when a ListView has got clickable items like button, it lost its clickability
        final Deck currentDeck = decksList.get(position);

        TextView deckName = (TextView) listItem.findViewById(R.id.textViewDeckName);
        int cardsNumber = currentDeck.getCards() != null ? currentDeck.getCards().size() : 0;
        String cardWord = cardsNumber > 1 ? " cards" : " card";
        deckName.setText(currentDeck.getName() + " - " + currentDeck.getCards().size() + cardWord);

        ImageView img = (ImageView) listItem.findViewById(R.id.imageViewDellDeck);

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Deck toRemove = currentDeck;
                if(toRemove != null){
                    DeckCollection sauvegarde = DeckManagerSingleton.getInstance().load(mContext);
                    sauvegarde.removeDeck(toRemove);
                    DeckManagerSingleton.getInstance().save(sauvegarde, mContext);
                    decksList.remove(toRemove);
                    Toast.makeText(mContext, toRemove.getName() + " removed", Toast.LENGTH_LONG).show();
                }
            }
        });

        this.notifyDataSetChanged();

        // Return the completed view to render on screen
        return listItem;
    }
}
