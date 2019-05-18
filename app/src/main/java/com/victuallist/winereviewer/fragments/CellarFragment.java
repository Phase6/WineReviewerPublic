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
import com.victuallist.winereviewer.adapters.CellarAdapter;
import com.victuallist.winereviewer.data.localpersistence.CellarHub;
import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CellarFragment extends Fragment implements View.OnClickListener, CellarAdapter.CellarOnClickListener {

    String LOG_TAG = "Cellar Fragment";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    int CURRENT_SORT_TYPE = 0;
    int SORT_DEFAULT_ALPHA = 0;
    int SORT_DEFAULT_RATING = 1;
    int SORT_DEFAULT_BOTTLES = 2;

    CellarHub cellarHub;
    ArrayList<CellarObject> cellarMasterAL, cellarToDisplay;

    Context parentContext;

    Menu fragmentMenu;

    RecyclerView recyclerView;
    CellarAdapter cellarAdapter;
    RecyclerView.LayoutManager layoutManager;

    CardView welcomeCard;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentContext = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cellar, container, false);

        welcomeCard = (CardView) rootView.findViewById(R.id.cellarWelcomCard);

        recyclerView = rootView.findViewById(R.id.cellarRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(parentContext);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton newItemFloatingButton = (FloatingActionButton) rootView.findViewById(R.id.newCellarItemFloatingActionButton);
        newItemFloatingButton.setOnClickListener(this);

        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        cellarHub = new CellarHub();
        cellarMasterAL = cellarHub.loadCellar(parentContext);
        for(int i = 0; i < cellarMasterAL.size(); i++){
            ArrayList<ReviewObject> reviews = cellarHub.loadReviews(parentContext, cellarMasterAL.get(i));
            cellarMasterAL.get(i).setRelatedReviews(reviews);
        }

        cellarToDisplay = cellarMasterAL;

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        CURRENT_SORT_TYPE = sharedPreferences.getInt("CELLAR_SORT_PREFERENCE", 0);

        if(CURRENT_SORT_TYPE == SORT_DEFAULT_ALPHA){
            sortCellarByAlphabetical();
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
            sortCellarByRating();
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_BOTTLES){
            sortCellarByBottles();
        }else{
            cellarToDisplay = cellarMasterAL;
            refreshAdapter();
        }

        if(cellarMasterAL.size() == 0){
            welcomeCard.setVisibility(View.VISIBLE);
        }else{
            welcomeCard.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

    }


    public void refreshAdapter(){
        cellarAdapter = new CellarAdapter(cellarToDisplay);
        cellarAdapter.setCellarOnClickListener(this);
        recyclerView.setAdapter(cellarAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cellar_fragment_action_menu, menu);
        fragmentMenu = menu;
        if(CURRENT_SORT_TYPE == SORT_DEFAULT_ALPHA){
            fragmentMenu.findItem(R.id.sortCellarAlphabetical).setVisible(true);
            fragmentMenu.findItem(R.id.sortCellarsRating).setVisible(false);
            fragmentMenu.findItem(R.id.sortCellarBottles).setVisible(false);
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
            fragmentMenu.findItem(R.id.sortCellarsRating).setVisible(true);
            fragmentMenu.findItem(R.id.sortCellarAlphabetical).setVisible(false);
            fragmentMenu.findItem(R.id.sortCellarBottles).setVisible(false);
        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_BOTTLES){
            fragmentMenu.findItem(R.id.sortCellarBottles).setVisible(true);
            fragmentMenu.findItem(R.id.sortCellarsRating).setVisible(false);
            fragmentMenu.findItem(R.id.sortCellarAlphabetical).setVisible(false);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        MenuItem alphaItem = fragmentMenu.findItem(R.id.sortCellarAlphabetical);
        MenuItem ratingItem = fragmentMenu.findItem(R.id.sortCellarsRating);
        MenuItem bottleItem = fragmentMenu.findItem(R.id.sortCellarBottles);
        spEditor = sharedPreferences.edit();
        switch (item.getItemId()) {
            case R.id.sortCellarAlphabetical:
                alphaItem.setVisible(false);
                ratingItem.setVisible(true);
                bottleItem.setVisible(false);
                spEditor.putInt("CELLAR_SORT_PREFERENCE", SORT_DEFAULT_RATING).commit();
                sortCellarByRating();
                return true;
            case R.id.sortCellarsRating:
                ratingItem.setVisible(false);
                alphaItem.setVisible(false);
                bottleItem.setVisible(true);
                spEditor.putInt("CELLAR_SORT_PREFERENCE", SORT_DEFAULT_BOTTLES).commit();
                sortCellarByBottles();
                return true;
            case R.id.sortCellarBottles:
                ratingItem.setVisible(false);
                alphaItem.setVisible(true);
                bottleItem.setVisible(false);
                spEditor.putInt("CELLAR_SORT_PREFERENCE", SORT_DEFAULT_ALPHA).commit();
                sortCellarByAlphabetical();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sortCellarByAlphabetical(){
        Collections.sort(cellarToDisplay, new CompareByVineyardName());
        refreshAdapter();
    }


    class CompareByVineyardName implements Comparator<CellarObject> {

        @Override
        public int compare(CellarObject cellarObject, CellarObject t1) {
            String v1 = cellarObject.getVineyard().toUpperCase();
            String v2 = t1.getVineyard().toUpperCase();
            return v1.compareTo(v2);
        }
    }


    public void sortCellarByRating(){
        Collections.sort(cellarToDisplay, new CompareByRating());
        refreshAdapter();
    }


    class CompareByRating implements Comparator<CellarObject>{

        @Override
        public int compare(CellarObject cellarObject, CellarObject t1) {
            float ratingC1 = 0;
            if(cellarObject.getRelatedReviews().size() > 0){
                ratingC1 = cellarObject.getRelatedReviews().get(0).getFloatRating();
            }
            float ratingC2 = 0;
            if(t1.getRelatedReviews().size() > 0){
                ratingC2 = t1.getRelatedReviews().get(0).getFloatRating();
            }

            return    ratingC1 > ratingC2 ? -1
                    : ratingC1 < ratingC2 ? 1
                    : 0;
        }
    }


    public void sortCellarByBottles(){
        Collections.sort(cellarToDisplay, new CompareByBottleNumber());
        refreshAdapter();
    }


    class CompareByBottleNumber implements Comparator<CellarObject>{

        @Override
        public int compare(CellarObject reviewObject, CellarObject t1) {
            return    reviewObject.getBottleCount() > t1.getBottleCount() ? -1
                    : reviewObject.getBottleCount() < t1.getBottleCount() ? 1
                    : 0;
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.newCellarItemFloatingActionButton){
            Intent intent = new Intent(parentContext, CellarActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClickCellarItem(int id) {
        CellarObject selectedCellarItem = new CellarObject();
        for(int i = 0; i < cellarMasterAL.size(); i++){
            if(cellarMasterAL.get(i).getSqlID() == id){
                selectedCellarItem = cellarMasterAL.get(i);
                break;
            }
        }
        Intent intent = new Intent(parentContext, CellarActivity.class);
        intent.putExtra("SELECTEDCELLARITEM", selectedCellarItem);
        startActivity(intent);
    }


    @Override
    public void onClickCellarReviewButton(int id) {
        CellarObject selectedCellarItem = new CellarObject();
        for(int i = 0; i < cellarMasterAL.size(); i++){
            if(cellarMasterAL.get(i).getSqlID() == id){
                selectedCellarItem = cellarMasterAL.get(i);
                break;
            }
        }
        Intent intent = new Intent(parentContext, ReviewActivity.class);
        intent.putExtra("SELECTEDCELLARITEMTOREVIEW", selectedCellarItem);
        startActivity(intent);
    }


}
