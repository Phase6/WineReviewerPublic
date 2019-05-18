package com.victuallist.winereviewer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;

import java.util.ArrayList;

public class CoreReviewAdapter extends RecyclerView.Adapter<CoreReviewAdapter.ViewHolder> {

    String LOG_TAG = "CoreReviewsAdapter";

    public interface ReviewOnClickListener{
        void onToggleFavorite(int id);
        void onClickReviewForDetials(int id);
        void rateThisWine(int id);
    }

    ReviewOnClickListener reviewOnClickListener;

    ArrayList<CoreReviewObject> reviews;

    public CoreReviewAdapter(ArrayList<CoreReviewObject> r) {
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
        public TextView numberOfReviews;
        public TextView wineVineyard;
        public TextView wineVarietal;
        public TextView wineOrigin;
        public TextView wineVintage;
        public Button rateItButton;
        public ImageButton coreReviewHeartImageButton;
        public ImageButton coreReviewBookmarkImageButton;

        public ViewHolder(View view) {
            super(view);
            reviewCardLinearLayoutMain = (LinearLayout) view.findViewById(R.id.reviewCardLinearLayoutMain);
            wineName = (TextView) view.findViewById(R.id.reviewCardWineName);
            wineRating = (RatingBar) view.findViewById(R.id.reviewCardReviewBar);
            numberOfReviews = (TextView) view.findViewById(R.id.reviewCardNumberOfReviews);
            wineVineyard = (TextView) view.findViewById(R.id.reviewCardWineVineyard);
            wineVarietal = (TextView) view.findViewById(R.id.reviewCardWineVarietal);
            wineOrigin = (TextView) view.findViewById(R.id.reviewCardWineOrigin);
            wineVintage = (TextView) view.findViewById(R.id.reviewCardWineVintage);
            rateItButton = (Button) view.findViewById(R.id.coreReviewCardRateButton);
            coreReviewHeartImageButton = (ImageButton) view.findViewById(R.id.coreReviewFavoriteImageButtonHeart);
            coreReviewBookmarkImageButton = (ImageButton) view.findViewById(R.id.coreReviewFavoriteImageButtonBookmark);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_core_review, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final CoreReviewObject review = reviews.get(position);
        holder.wineName.setText(review.getName());
        holder.wineRating.setRating(review.getFloatRating());
        holder.numberOfReviews.setText(review.getNumberOfReviewsString());
        holder.wineVineyard.setText(review.getVineyard());
        holder.wineVarietal.setText(review.getVarietal());
        holder.wineOrigin.setText(review.getOrigin());
        holder.wineVintage.setText(review.getVintage());

        if(review.isFavorite()){
            holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_white_36dp);
            holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_white_24dp);
            if(!review.isUserHasReviewed()){
                holder.rateItButton.setVisibility(View.VISIBLE);
            }else{
                holder.rateItButton.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_border_white_36dp);
            holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_border_white_24dp);
            holder.rateItButton.setVisibility(View.INVISIBLE);
        }

        holder.reviewCardLinearLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewOnClickListener.onClickReviewForDetials(review.getSQLID());
            }
        });

        holder.rateItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewOnClickListener.rateThisWine(review.getSQLID());
            }
        });

        holder.coreReviewHeartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewOnClickListener.onToggleFavorite(review.getSQLID());
                if(review.isFavorite()){
                    holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_white_36dp);
                    holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_white_24dp);
                    if(!review.isUserHasReviewed()){
                        holder.rateItButton.setVisibility(View.VISIBLE);
                    }else{
                        holder.rateItButton.setVisibility(View.INVISIBLE);
                    }
                }else{
                    holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_border_white_36dp);
                    holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_border_white_24dp);
                    holder.rateItButton.setVisibility(View.INVISIBLE);
                }

            }
        });

        holder.coreReviewBookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewOnClickListener.onToggleFavorite(review.getSQLID());
                if(review.isFavorite()){
                    holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_white_36dp);
                    holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_white_24dp);
                    if(!review.isUserHasReviewed()){
                        holder.rateItButton.setVisibility(View.VISIBLE);
                    }else{
                        holder.rateItButton.setVisibility(View.INVISIBLE);
                    }
                }else{
                    holder.coreReviewHeartImageButton.setImageResource(R.drawable.baseline_favorite_border_white_36dp);
                    holder.coreReviewBookmarkImageButton.setImageResource(R.drawable.baseline_bookmark_border_white_24dp);
                    holder.rateItButton.setVisibility(View.INVISIBLE);
                }

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
