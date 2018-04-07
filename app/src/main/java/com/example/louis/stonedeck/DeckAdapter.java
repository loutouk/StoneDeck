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

public class DeckAdapter extends ArrayAdapter<Deck> implements View.OnClickListener{

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
        Deck currentDeck = decksList.get(position);

        TextView deckName = (TextView) listItem.findViewById(R.id.textViewDeckName);
        int cardsNumber = currentDeck.getCards() != null ? currentDeck.getCards().size() : 0;
        String cardWord = cardsNumber > 1 ? " cards" : " card";
        deckName.setText(currentDeck.getName() + " " + DeckActivity.DECK_NAME_SEPARATOR + " " + currentDeck.getCards().size() + cardWord);

        ImageView img = (ImageView) listItem.findViewById(R.id.imageViewDellDeck);

        img.setOnClickListener(this);

        this.notifyDataSetChanged();

        // Return the completed view to render on screen
        return listItem;
    }

    @Override
    public void onClick(View view) {
        View imgView = view;
        // We need to wind up to the parent in order to access the deck name
        View listItem = (View) imgView.getParent();

        if(listItem == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItem = inflater.inflate(R.layout.deck_item, null);
        }

        TextView deckNameTxtView = (TextView) listItem.findViewById(R.id.textViewDeckName);
        String toRemoveName = deckNameTxtView.getText().toString().trim();
        // We customized the text previsoulsy
        // So we have to retrieve the part correspinding to the deck name only
        toRemoveName = toRemoveName.split(DeckActivity.DECK_NAME_SEPARATOR)[0].trim();
        for(Deck d : decksList){
            if(d.getName().equals(toRemoveName)){
                // Remove from the list
                decksList.remove(d);
                // Remove from the save file
                DeckCollection sauvegarde = DeckManagerSingleton.getInstance().load(mContext);
                sauvegarde.removeDeck(d);
                DeckManagerSingleton.getInstance().save(sauvegarde, mContext);
                Toast.makeText(mContext, toRemoveName + " removed", Toast.LENGTH_SHORT).show();
                // update the display
                this.notifyDataSetChanged();
                break;
            }
        }
    }
}
