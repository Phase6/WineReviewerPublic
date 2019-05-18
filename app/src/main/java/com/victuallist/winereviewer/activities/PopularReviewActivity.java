package com.victuallist.winereviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.adapters.DetailReviewAdapter;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;
import com.victuallist.winereviewer.data.objects.DetailReviewObject;


import java.util.ArrayList;


public class PopularReviewActivity extends AppCompatActivity implements View.OnClickListener {

    String LOG_TAG = "PopularReviewActivity";

    CoreReviewObject coreReviewObject;
    ArrayList<DetailReviewObject> reviewDetails;

    //CORE CARD UI ELEMENTS
    TextView nameTextView, numberOfReviewsTextView, vineyardTextView, varietalTextView, originTextView, vintageTextView;
    RatingBar ratingBar;
    ImageButton favoriteImageButton;

    //REVIEW DETAILS RECYCLERVIEW
    RecyclerView detailsRecyclerView;
    DetailReviewAdapter reviewsAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_review);

        instantiateUIElements();

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            coreReviewObject = (CoreReviewObject) bundle.getSerializable("SELECTEDPOPULARREVIEW");
            reviewDetails = coreReviewObject.getReviewDetails();
            populateCoreReviewCard();
            populateDetailsInRecyclerListView();
        }catch(Exception e){
            Log.i(LOG_TAG, e.toString());
        }

    }

    public void instantiateUIElements(){
        nameTextView = (TextView) findViewById(R.id.popularReviewCoreCardWineName);
        numberOfReviewsTextView = (TextView) findViewById(R.id.popularReviewCoreCardNumberOfReviews);
        vineyardTextView = (TextView) findViewById(R.id.popularReviewCoreCardWineVineyard);
        varietalTextView = (TextView) findViewById(R.id.popularReviewCoreCardWineVarietal);
        originTextView = (TextView) findViewById(R.id.popularReviewCoreCardWineOrigin);
        vintageTextView = (TextView) findViewById(R.id.popularReviewCoreCardWineVintage);
        ratingBar = (RatingBar) findViewById(R.id.popularReviewCoreCardReviewBar);
        favoriteImageButton = (ImageButton) findViewById(R.id.popularReviewCoreCardFavoriteImageButton);
        favoriteImageButton.setOnClickListener(this);


        detailsRecyclerView = (RecyclerView) findViewById(R.id.popularReviewCoreCardDetailsRecyclerView);


    }

    public void populateCoreReviewCard(){
        nameTextView.setText(coreReviewObject.getName());
        numberOfReviewsTextView.setText(coreReviewObject.getNumberOfReviewsString());
        vineyardTextView.setText(coreReviewObject.getVineyard());
        varietalTextView.setText(coreReviewObject.getVarietal());
        originTextView.setText(coreReviewObject.getOrigin());
        vintageTextView.setText(coreReviewObject.getVintage());
        ratingBar.setRating(coreReviewObject.getFloatRating());

        if(coreReviewObject.isFavorite()){
            favoriteImageButton.setImageResource(R.drawable.baseline_favorite_white_36dp);
        }else{
            favoriteImageButton.setImageResource(R.drawable.baseline_favorite_border_white_36dp);
        }

        detailsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        detailsRecyclerView.setLayoutManager(layoutManager);
        reviewsAdapter = new DetailReviewAdapter(reviewDetails);
        detailsRecyclerView.setAdapter(reviewsAdapter);
    }

    public void populateDetailsInRecyclerListView(){



    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.popularReviewCoreCardFavoriteImageButton){
            coreReviewObject.setFavorite(!coreReviewObject.isFavorite());
            if(coreReviewObject.isFavorite()){
                favoriteImageButton.setImageResource(R.drawable.baseline_favorite_white_36dp);
            }else{
                favoriteImageButton.setImageResource(R.drawable.baseline_favorite_border_white_36dp);
            }
        }
    }


}
