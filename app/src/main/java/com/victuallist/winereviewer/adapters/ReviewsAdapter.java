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
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    String LOG_TAG = "ReviewsAdapter";

    public interface ReviewOnClickListener{
        void onClickReview(int id);
        void onClickAddToCellar(int id);
    }

    ReviewOnClickListener reviewOnClickListener;

    ArrayList<ReviewObject> reviews;

    public ReviewsAdapter(ArrayList<ReviewObject> r) {
        reviews = r;
        setHasStableIds(true);
    }


    public void setReviewOnClickListener(ReviewOnClickListener reviewOnClickListener){
        this.reviewOnClickListener = reviewOnClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout reviewCardLinearLayoutMain;
        public TextView wineName;
        public RatingBar wineRating;
        public TextView wineVineyard;
        public TextView wineVarietal;
        public TextView wineOrigin;
        public TextView wineVintage;
        public TextView winePrice;
        public TextView winePairing;
        public TextView wineTaste;
        public TextView wineSmell;
        public TextView wineColor;
        public LinearLayout priceLinearLayout, pairingLinearLayout, tasteLinearLayout,
                smellLinearLayout, colorLinearLayout;

        public TextView numberOfBottlesInCellarTextView;
        public Button addWineToCellarButton;


        public ViewHolder(View view) {
            super(view);
            reviewCardLinearLayoutMain = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutMain);
            wineName = (TextView) view.findViewById(R.id.reviewCardWineName);
            wineRating = (RatingBar) view.findViewById(R.id.reviewCardReviewBar);
            wineVineyard = (TextView) view.findViewById(R.id.reviewCardWineVineyard);
            wineVarietal = (TextView) view.findViewById(R.id.reviewCardWineVarietal);
            wineOrigin = (TextView) view.findViewById(R.id.reviewCardWineOrigin);
            wineVintage = (TextView) view.findViewById(R.id.reviewCardWineVintage);
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
            numberOfBottlesInCellarTextView = (TextView) view.findViewById(R.id.reviewCardNumberOfBottlesTextView);
            addWineToCellarButton = (Button) view.findViewById(R.id.reviewCardAddToCellarButton);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ReviewObject review = reviews.get(position);
        holder.wineName.setText(review.getName());
        holder.wineRating.setRating(review.getFloatRating());
        holder.wineVineyard.setText(review.getVineyard());
        holder.wineVarietal.setText(review.getVarietal());
        holder.wineOrigin.setText(review.getOrigin());
        holder.wineVintage.setText(review.getVintage());

        String price = review.getPrice();
        if(price.length() > 0){
            holder.priceLinearLayout.setVisibility(View.VISIBLE);
            holder.winePrice.setText(price);
        }else{
            holder.priceLinearLayout.setVisibility(View.GONE);
        }

        String winePairing = review.getPairing();
        if(winePairing.length() > 0){
            holder.pairingLinearLayout.setVisibility(View.VISIBLE);
            holder.winePairing.setText(winePairing);
        }else{
            holder.pairingLinearLayout.setVisibility(View.GONE);
        }

        String wineTaste = review.getTaste();
        if(wineTaste.length() > 0){
            holder.tasteLinearLayout.setVisibility(View.VISIBLE);
            holder.wineTaste.setText(wineTaste);
        }else{
            holder.tasteLinearLayout.setVisibility(View.GONE);
        }

        String wineSmell = review.getSmell();
        if(wineSmell.length() > 0){
            holder.smellLinearLayout.setVisibility(View.VISIBLE);
            holder.wineSmell.setText(wineSmell);
        }else{
            holder.smellLinearLayout.setVisibility(View.GONE);
        }

        String wineColor = review.getColor();
        if(wineColor.length() > 0){
            holder.colorLinearLayout.setVisibility(View.VISIBLE);
            holder.wineColor.setText(wineColor);
        }else{
            holder.colorLinearLayout.setVisibility(View.GONE);
        }

        if(review.getRelatedCellarItems().size() > 0){
            holder.numberOfBottlesInCellarTextView.setVisibility(View.VISIBLE);
            holder.addWineToCellarButton.setVisibility(View.GONE);
            String bottlesString = String.valueOf(review.getRelatedCellarItems().get(0).getBottleCount());
            holder.numberOfBottlesInCellarTextView.setText(bottlesString + " bottles");
        }else{
            holder.addWineToCellarButton.setVisibility(View.VISIBLE);
            holder.numberOfBottlesInCellarTextView.setVisibility(View.GONE);
            holder.addWineToCellarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reviewOnClickListener.onClickAddToCellar(review.getSQLID());
                }
            });
        }


        holder.reviewCardLinearLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewOnClickListener.onClickReview(review.getSQLID());
            }
        });

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
