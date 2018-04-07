package com.example.louis.stonedeck;

/**
 * Created by Louis on 30/03/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarteAdapter extends ArrayAdapter<Carte> {

    private Context mContext;
    private List<Carte> cardsList = new ArrayList<>();

    public CarteAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Carte> list) {
        super(context, R.layout.list_item, list);
        mContext = context;
        cardsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        // Get the data item for this position
        Carte currentCard = cardsList.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.classe = (TextView) convertView.findViewById(R.id.textView_class);
            viewHolder.type = (TextView) convertView.findViewById(R.id.textView_type);
            viewHolder.faction = (TextView) convertView.findViewById(R.id.textView_faction);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            // We've just avoided calling findViewById() on resource everytime
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.

        // Some cards do not have image
        // Resize in the good ratio to otpimize memory use
        // fit() measures the dimensions of the target ImageView and internally uses resize() to reduce the image size to the dimensions of the ImageView
        if (currentCard != null && currentCard.getImg() != null && currentCard.getImg().length() > 0) {
            // Load default template card back on 404 not found
            Picasso.with(mContext).load(currentCard.getImg()).error(R.drawable.cardbackdefault).fit().centerInside().into(viewHolder.image);
        } else {
            // Set ImageView image from drawable resource when there is no image
            int id = convertView.getResources().getIdentifier("com.example.louis.stonedeck:drawable/" + "cardbackdefault", null, null);
            viewHolder.image.setImageResource(id);
        }

        viewHolder.name.setText(currentCard.name);
        viewHolder.classe.setText(currentCard.getPlayerClass());
        viewHolder.type.setText(currentCard.type);
        viewHolder.faction.setText(currentCard.faction);

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView classe;
        TextView type;
        TextView faction;
    }
}
