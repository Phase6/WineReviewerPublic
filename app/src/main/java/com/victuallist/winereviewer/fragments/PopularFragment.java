package com.victuallist.winereviewer.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.activities.PopularReviewActivity;
import com.victuallist.winereviewer.activities.ReviewActivity;
import com.victuallist.winereviewer.adapters.CoreReviewAdapter;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;
import com.victuallist.winereviewer.data.localpersistence.DataHub;
import com.victuallist.winereviewer.data.viewmodels.PersistentReviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PopularFragment extends Fragment implements CoreReviewAdapter.ReviewOnClickListener {

    String LOG_TAG = "Popular Fragment";

    Context parentContext;

    DataHub dataHub;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    int CURRENT_SORT_TYPE = 0;
    int SORT_DEFAULT_RATING = 0;
    int SORT_DEFAULT_FAVORITE = 1;

    RecyclerView recyclerView;
    CoreReviewAdapter reviewsAdapter;
    RecyclerView.LayoutManager layoutManager;

    ProgressBar progressBar;

    ArrayList<CoreReviewObject> reviewsToDisplay = new ArrayList<>();

    Menu fragmentMenu;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentContext = context;
        dataHub = new DataHub();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);

        recyclerView = rootView.findViewById(R.id.popularReviewsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(parentContext);
        recyclerView.setLayoutManager(layoutManager);

        progressBar = rootView.findViewById(R.id.progressBarPopularFragment);

        refreshAdapter();

        setHasOptionsMenu(true);
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        CURRENT_SORT_TYPE = sharedPreferences.getInt("POPULAR_REVIEWS_SORT_PREFERENCE", 0);

        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();


        //TODO: This code is commented out for the public version - without a proper url in PersistentReviews it will throw an error
//        PersistentReviews.Factory factory = new PersistentReviews.Factory(getActivity().getApplication());
//        PersistentReviews model = ViewModelProviders.of(this, factory).get(PersistentReviews.class);
//        model.getPopularReviews().observe(this, new Observer<ArrayList<CoreReviewObject>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<CoreReviewObject> coreReviewObjects) {
//                reviewsToDisplay = coreReviewObjects;
//
//                if(CURRENT_SORT_TYPE == SORT_DEFAULT_FAVORITE){
//                    sortReviewsByFavorite();
//                }else if (CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
//                    sortReviewsByRating();
//                }else{
//                    sortReviewsByRating();
//                }
//            }
//        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popular_fragment_action_menu, menu);
        fragmentMenu = menu;
        if(CURRENT_SORT_TYPE == SORT_DEFAULT_FAVORITE){
            fragmentMenu.findItem(R.id.sortReviewsFavorite).setVisible(true);
            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(false);
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(true);
            fragmentMenu.findItem(R.id.sortReviewsFavorite).setVisible(false);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        MenuItem favItem = fragmentMenu.findItem(R.id.sortReviewsFavorite);
        MenuItem ratingItem = fragmentMenu.findItem(R.id.sortReviewsRating);
        spEditor = sharedPreferences.edit();
        switch (item.getItemId()) {
            case R.id.sortReviewsFavorite:
                favItem.setVisible(false);
                ratingItem.setVisible(true);
                spEditor.putInt("POPULAR_REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_RATING).commit();
                sortReviewsByRating();
                return true;
            case R.id.sortReviewsRating:
                ratingItem.setVisible(false);
                favItem.setVisible(true);
                spEditor.putInt("POPULAR_REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_FAVORITE).commit();
                sortReviewsByFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void refreshAdapter(){
        reviewsAdapter = new CoreReviewAdapter(reviewsToDisplay);
        reviewsAdapter.setReviewOnClickListener(this);
        recyclerView.setAdapter(reviewsAdapter);
    }


    public void sortReviewsByRating(){
        Collections.sort(reviewsToDisplay, new PopularFragment.CompareByRating());
        refreshAdapter();
    }


    class CompareByRating implements Comparator<CoreReviewObject>{

        @Override
        public int compare(CoreReviewObject reviewObject, CoreReviewObject t1) {
            return    reviewObject.getFloatRating() > t1.getFloatRating() ? -1
                    : reviewObject.getFloatRating() < t1.getFloatRating() ? 1
                    : 0;
        }
    }


    public void sortReviewsByFavorite(){
        //TODO: !!!!!
        Collections.sort(reviewsToDisplay, new PopularFragment.CompareByFavorite());
        refreshAdapter();
    }


    class CompareByFavorite implements Comparator<CoreReviewObject>{
        @Override
        public int compare(CoreReviewObject reviewObject, CoreReviewObject t1) {
            return -(Boolean.compare(reviewObject.isFavorite(), t1.isFavorite()));
        }
    }


    @Override
    public void onToggleFavorite(int id) {
        for(int i = 0; i < reviewsToDisplay.size(); i++){
            if(reviewsToDisplay.get(i).getSQLID() == id){
                reviewsToDisplay.get(i).setFavorite(!reviewsToDisplay.get(i).isFavorite());
                //TODO: SAVE OR DELETE FAVORITE ID LOCALLY
                if(reviewsToDisplay.get(i).isFavorite()){
                    dataHub.addPopularFavorite(parentContext, id);
                }else{
                    dataHub.deletePopularFavorite(parentContext, id);
                }
                break;
            }
        }
    }


    @Override
    public void onClickReviewForDetials(int id) {
        CoreReviewObject coreReviewObject = null;
        for(int i = 0; i < reviewsToDisplay.size(); i++) {
            if (reviewsToDisplay.get(i).getSQLID() == id) {
                coreReviewObject = reviewsToDisplay.get(i);
                break;
            }
        }
        if(coreReviewObject != null){
            Intent intent = new Intent(parentContext, PopularReviewActivity.class);
            intent.putExtra("SELECTEDPOPULARREVIEW", coreReviewObject);
            startActivity(intent);
        }else{
            Toast.makeText(parentContext, "Details not available for this review.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void rateThisWine(int id) {
        CoreReviewObject coreReviewObject = null;
        for(int i = 0; i < reviewsToDisplay.size(); i++) {
            if (reviewsToDisplay.get(i).getSQLID() == id) {
                coreReviewObject = reviewsToDisplay.get(i);
                break;
            }
        }
        if(coreReviewObject != null){
            Intent intent = new Intent(parentContext, ReviewActivity.class);
            intent.putExtra("SELECTEDPOPULARREVIEW", coreReviewObject);
            startActivity(intent);
        }else{
            Toast.makeText(parentContext, "Feature unavailable for this selection.", Toast.LENGTH_SHORT).show();
        }
    }


}
