package com.victuallist.winereviewer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.CellarObject;

import java.util.ArrayList;

public class CellarAdapter extends RecyclerView.Adapter<CellarAdapter.ViewHolder>{

    String LOG_TAG = "CellarAdapter";

    public interface CellarOnClickListener{
        void onClickCellarItem(int id);
        void onClickCellarReviewButton(int id);
    }

    CellarAdapter.CellarOnClickListener cellarOnClickListener;

    ArrayList<CellarObject> cellarItems;

    public CellarAdapter(ArrayList<CellarObject> c) {
        cellarItems = c;
        setHasStableIds(true);
    }


    public void setCellarOnClickListener(CellarAdapter.CellarOnClickListener cellarOnClickListener){
        this.cellarOnClickListener = cellarOnClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mainCardLinearLayout;
        public TextView textViewName, textViewVineyard, textViewVarietal, textViewOrigin, textViewVintage,
        textViewPrice, textViewBottles, textViewSize, textViewBuyDate, textViewLocation,
        textViewDrinkBy, textViewValue, textViewNotes;

        public RatingBar ratingBar;
        public Button rateButton;

        public LinearLayout priceLinearLayout, countLinearLayout, sizeLinearLayout,
                purchaseDateLinearLayout, locationLinearLayout, drinkByLinearLayout,
                valueLinearLayout, notesLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mainCardLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutMain);
            textViewName = (TextView) view.findViewById(R.id.cellarCardWineName);

            ratingBar = (RatingBar) view.findViewById(R.id.cellarCardReviewBar);
            rateButton = (Button) view.findViewById(R.id.cellarCardRateButton);

            textViewVineyard = (TextView) view.findViewById(R.id.cellarCardWineVineyard);
            textViewVarietal = (TextView) view.findViewById(R.id.cellarCardWineVarietal);
            textViewOrigin = (TextView) view.findViewById(R.id.cellarCardWineOrigin);
            textViewVintage = (TextView) view.findViewById(R.id.cellarCardWineVintage);
            textViewPrice = (TextView) view.findViewById(R.id.cellarCardWinePrice);
            textViewBottles = (TextView) view.findViewById(R.id.cellarCardWineBottles);
            textViewSize = (TextView) view.findViewById(R.id.cellarCardWineSize);
            textViewBuyDate = (TextView) view.findViewById(R.id.cellarCardWineBuyDate);
            textViewLocation = (TextView) view.findViewById(R.id.cellarCardWineLocation);
            textViewDrinkBy = (TextView) view.findViewById(R.id.cellarCardWineDrinkBy);
            textViewValue = (TextView) view.findViewById(R.id.cellarCardWineValue);
            textViewNotes = (TextView) view.findViewById(R.id.cellarCardWineNotes);

            priceLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutPrice);
            countLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutBottles);
            sizeLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutSize);
            purchaseDateLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutBuyDate);
            locationLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutLocation);
            drinkByLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutDrinkBy);
            valueLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutValue);
            notesLinearLayout = (LinearLayout) view.findViewById(R.id.cellarCardLinearLayoutNotes);

        }
    }


    @NonNull
    @Override
    public CellarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cellar, parent, false);

        return new CellarAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CellarAdapter.ViewHolder holder, int position) {

        final CellarObject cellarItem = cellarItems.get(position);
        holder.textViewName.setText(cellarItem.getName());
        holder.textViewVineyard.setText(cellarItem.getVineyard());
        holder.textViewVarietal.setText(cellarItem.getVarietal());
        holder.textViewOrigin.setText(cellarItem.getOrigin());
        holder.textViewVintage.setText(cellarItem.getVintage());

        String price = cellarItem.getPurchasePrice();
        if(price.length() > 0){
            holder.priceLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewPrice.setText(price);
        }else{
            holder.priceLinearLayout.setVisibility(View.GONE);
        }

        String bottleCount = String.valueOf(cellarItem.getBottleCount());
        if(bottleCount.length() > 0){
            holder.countLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewBottles.setText(bottleCount);
        }else{
            holder.countLinearLayout.setVisibility(View.GONE);
        }

        String bottleSize = cellarItem.getBottleSize();
        if(bottleSize.length() > 0){
            holder.sizeLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewSize.setText(bottleSize);
        }else{
            holder.sizeLinearLayout.setVisibility(View.GONE);
        }

        String purchaseDate = cellarItem.getPurchaseDate();
        if(purchaseDate.length() > 0){
            holder.purchaseDateLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewBuyDate.setText(purchaseDate);
        }else{
            holder.purchaseDateLinearLayout.setVisibility(View.GONE);
        }

        String location = cellarItem.getLocation();
        if(location.length() > 0){
            holder.locationLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewLocation.setText(location);
        }else{
            holder.locationLinearLayout.setVisibility(View.GONE);
        }

        String drinkByDate = cellarItem.getDrinkByDate();
        if(drinkByDate.length() > 0){
            holder.drinkByLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewDrinkBy.setText(drinkByDate);
        }else{
            holder.drinkByLinearLayout.setVisibility(View.GONE);
        }

        String currentValue = cellarItem.getCurrentValue();
        if(currentValue.length() > 0){
            holder.valueLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewValue.setText(currentValue);
        }else{
            holder.valueLinearLayout.setVisibility(View.GONE);
        }

        String notes = cellarItem.getNotes();
        if(notes.length() > 0){
            holder.notesLinearLayout.setVisibility(View.VISIBLE);
            holder.textViewNotes.setText(notes);
        }else{
            holder.notesLinearLayout.setVisibility(View.GONE);
        }

        if(cellarItem.getRelatedReviews().size() > 0){
            holder.rateButton.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.VISIBLE);
            float rating = cellarItem.getRelatedReviews().get(0).getFloatRating();
            holder.ratingBar.setRating(rating);
        }else{
            holder.ratingBar.setVisibility(View.GONE);
            holder.rateButton.setVisibility(View.VISIBLE);
            holder.rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellarOnClickListener.onClickCellarReviewButton(cellarItem.getSqlID());
                }
            });
        }

        holder.mainCardLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cellarOnClickListener.onClickCellarItem(cellarItem.getSqlID());
            }
        });

    }


    @Override
    public int getItemCount() {
        return cellarItems.size();
    }


    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public int getItemViewType(int position){
        return position;
    }


}
