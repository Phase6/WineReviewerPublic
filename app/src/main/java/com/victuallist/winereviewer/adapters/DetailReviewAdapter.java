package com.victuallist.winereviewer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.DetailReviewObject;

import java.util.ArrayList;


public class DetailReviewAdapter extends RecyclerView.Adapter<DetailReviewAdapter.ViewHolder> {

    String LOG_TAG = "DetailReviewAdapter";

    ArrayList<DetailReviewObject> reviews;

    public DetailReviewAdapter(ArrayList<DetailReviewObject> r) {
        reviews = r;
        setHasStableIds(true);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RatingBar wineRating;
        public TextView winePrice;
        public TextView winePairing;
        public TextView wineTaste;
        public TextView wineSmell;
        public TextView wineColor;
        public LinearLayout priceLinearLayout, pairingLinearLayout, tasteLinearLayout,
                smellLinearLayout, colorLinearLayout;


        public ViewHolder(View view) {
            super(view);
            wineRating = (RatingBar) view.findViewById(R.id.reviewCardReviewBar);
            winePrice = (TextView) view.findViewById(R.id.reviewCardWinePrice);
            winePairing = (TextView) view.findViewById(R.id.reviewCardWinePairingText);
            wineTaste = (TextView) view.findViewById(R.id.reviewCardWineTasteText);
            wineSmell = (TextView) view.findViewById(R.id.reviewCardWineSmellText);
            wineColor = (TextView) view.findViewById(R.id.reviewCardWineColorText);
            priceLinearLayout = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutPrice);
            pairingLinearLayout = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutPairing);
            tasteLinearLayout = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutTaste);
            smellLinearLayout = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutSmell);
            colorLinearLayout = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutColor);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail_review, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DetailReviewObject review = reviews.get(position);
        final String wineRating = review.getRating();
        holder.wineRating.setRating(review.getFloatRating());

        final String winePrice = review.getPrice();
        if(winePrice.length() > 0){
            holder.priceLinearLayout.setVisibility(View.VISIBLE);
            holder.winePrice.setText(winePrice);
        }else{
            holder.priceLinearLayout.setVisibility(View.GONE);
        }

        final String winePairing = review.getPairing();
        if(winePairing.length() > 0){
            holder.pairingLinearLayout.setVisibility(View.VISIBLE);
            holder.winePairing.setText(winePairing);
        }else{
            holder.pairingLinearLayout.setVisibility(View.GONE);
        }

        final String wineTaste = review.getTaste();
        if(wineTaste.length() > 0){
            holder.tasteLinearLayout.setVisibility(View.VISIBLE);
            holder.wineTaste.setText(wineTaste);
        }else{
            holder.tasteLinearLayout.setVisibility(View.GONE);
        }

        final String wineSmell = review.getSmell();
        if(wineSmell.length() > 0){
            holder.smellLinearLayout.setVisibility(View.VISIBLE);
            holder.wineSmell.setText(wineSmell);
        }else{
            holder.smellLinearLayout.setVisibility(View.GONE);
        }

        final String wineColor = review.getColor();
        if(wineColor.length() > 0){
            holder.colorLinearLayout.setVisibility(View.VISIBLE);
            holder.wineColor.setText(wineColor);
        }else{
            holder.colorLinearLayout.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return reviews.size();
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
