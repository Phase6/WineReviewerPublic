package com.victuallist.winereviewer.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.activities.CellarActivity;
import com.victuallist.winereviewer.activities.ReviewActivity;
import com.victuallist.winereviewer.adapters.ReviewsAdapter;
import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.localpersistence.DataHub;
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ReviewsFragment extends Fragment implements View.OnClickListener, ReviewsAdapter.ReviewOnClickListener {

    String LOG_TAG = "Reviews Fragment";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    int CURRENT_SORT_TYPE = 0;
    int SORT_DEFAULT_ALPHA = 0;
    int SORT_DEFAULT_RATING = 1;

    DataHub dataHub;
    ArrayList<ReviewObject> reviewsMasterAL, reviewsToDisplay;

    Context parentContext;

    Menu fragmentMenu;

    RecyclerView recyclerView;
    ReviewsAdapter reviewsAdapter;
    RecyclerView.LayoutManager layoutManager;

    CardView welcomeCard;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentContext = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        welcomeCard = (CardView) rootView.findViewById(R.id.reviewWelcomCard);

        recyclerView = rootView.findViewById(R.id.reviewsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(parentContext);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton newItemFloatingButton = (FloatingActionButton) rootView.findViewById(R.id.newReviewFloatingActionButton);
        newItemFloatingButton.setOnClickListener(this);

        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        dataHub = new DataHub();
        reviewsMasterAL = dataHub.loadReviews(parentContext);
        for(int i = 0; i < reviewsMasterAL.size(); i++){
            ArrayList<CellarObject> cellarObjects = dataHub.loadRelatedCellarItems(parentContext, reviewsMasterAL.get(i));
            reviewsMasterAL.get(i).setRelatedCellarItems(cellarObjects);
        }
        reviewsToDisplay = reviewsMasterAL;

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        CURRENT_SORT_TYPE = sharedPreferences.getInt("REVIEWS_SORT_PREFERENCE", 0);

        if(CURRENT_SORT_TYPE == SORT_DEFAULT_ALPHA){
            sortReviewsByAlphabetical();
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
            sortReviewsByRating();
        }else{
            reviewsToDisplay = reviewsMasterAL;
            refreshAdapter();
        }

        if(reviewsMasterAL.size() == 0){
            welcomeCard.setVisibility(View.VISIBLE);
        }else{
            welcomeCard.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

    }


    public void refreshAdapter(){
        reviewsAdapter = new ReviewsAdapter(reviewsToDisplay);
        reviewsAdapter.setReviewOnClickListener(this);
        recyclerView.setAdapter(reviewsAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.review_fragment_action_menu, menu);
        fragmentMenu = menu;
        if(CURRENT_SORT_TYPE == SORT_DEFAULT_ALPHA){
            fragmentMenu.findItem(R.id.sortReviewsAlphabetical).setVisible(true);
            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(false);
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(true);
            fragmentMenu.findItem(R.id.sortReviewsAlphabetical).setVisible(false);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        MenuItem alphaItem = fragmentMenu.findItem(R.id.sortReviewsAlphabetical);
        MenuItem ratingItem = fragmentMenu.findItem(R.id.sortReviewsRating);
        spEditor = sharedPreferences.edit();
        switch (item.getItemId()) {
            case R.id.sortReviewsAlphabetical:
                alphaItem.setVisible(false);
                ratingItem.setVisible(true);
                spEditor.putInt("REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_RATING).commit();
                sortReviewsByRating();
                return true;
            case R.id.sortReviewsRating:
                ratingItem.setVisible(false);
                alphaItem.setVisible(true);
                spEditor.putInt("REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_ALPHA).commit();
                sortReviewsByAlphabetical();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sortReviewsByAlphabetical(){
        Collections.sort(reviewsToDisplay, new CompareByVineyardName());
        refreshAdapter();
    }


    class CompareByVineyardName implements Comparator<ReviewObject>{

        @Override
        public int compare(ReviewObject reviewObject, ReviewObject t1) {
            String v1 = reviewObject.getVineyard().toUpperCase();
            String v2 = t1.getVineyard().toUpperCase();
            return v1.compareTo(v2);
        }
    }


    public void sortReviewsByRating(){
        Collections.sort(reviewsToDisplay, new CompareByRating());
        refreshAdapter();
    }


    class CompareByRating implements Comparator<ReviewObject>{

        @Override
        public int compare(ReviewObject reviewObject, ReviewObject t1) {
            return    reviewObject.getFloatRating() > t1.getFloatRating() ? -1
                    : reviewObject.getFloatRating() < t1.getFloatRating() ? 1
                    : 0;
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.newReviewFloatingActionButton){
            Intent intent = new Intent(parentContext, ReviewActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClickReview(int id) {
        ReviewObject selectedReview = new ReviewObject("", "", "", "");
        for(int i = 0; i < reviewsMasterAL.size(); i++){
            if(reviewsMasterAL.get(i).getSQLID() == id){
                selectedReview = reviewsMasterAL.get(i);
                break;
            }
        }
        Intent intent = new Intent(parentContext, ReviewActivity.class);
        intent.putExtra("SELECTEDREVIEW", selectedReview);
        startActivity(intent);
    }


    @Override
    public void onClickAddToCellar(int id) {
        ReviewObject selectedReview = new ReviewObject("", "", "", "");
        for(int i = 0; i < reviewsMasterAL.size(); i++){
            if(reviewsMasterAL.get(i).getSQLID() == id){
                selectedReview = reviewsMasterAL.get(i);
                break;
            }
        }
        Intent intent = new Intent(parentContext, CellarActivity.class);
        intent.putExtra("SELECTEDREVIEWTOADDTOCELLAR", selectedReview);
        startActivity(intent);
    }


}
