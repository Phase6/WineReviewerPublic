package com.victuallist.winereviewer.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.victuallist.winereviewer.R;

public class PreferencesFragment extends Fragment {

    String LOG_TAG = "Preferences Fragment";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    Context parentContext;

    Menu fragmentMenu;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentContext = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);

        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
//        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
//        setHasOptionsMenu(true);
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Auto-generated method stub
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.review_fragment_action_menu, menu);
//        fragmentMenu = menu;
//        if(CURRENT_SORT_TYPE == SORT_DEFAULT_ALPHA){
//            fragmentMenu.findItem(R.id.sortReviewsAlphabetical).setVisible(true);
//            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(false);
//        }else if(CURRENT_SORT_TYPE == SORT_DEFAULT_RATING){
//            fragmentMenu.findItem(R.id.sortReviewsRating).setVisible(true);
//            fragmentMenu.findItem(R.id.sortReviewsAlphabetical).setVisible(false);
//        }
//
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle item selection
//        MenuItem alphaItem = fragmentMenu.findItem(R.id.sortReviewsAlphabetical);
//        MenuItem ratingItem = fragmentMenu.findItem(R.id.sortReviewsRating);
//        spEditor = sharedPreferences.edit();
//        switch (item.getItemId()) {
//            case R.id.sortReviewsAlphabetical:
//                alphaItem.setVisible(false);
//                ratingItem.setVisible(true);
//                spEditor.putInt("REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_RATING).commit();
//                sortReviewsByRating();
//                return true;
//            case R.id.sortReviewsRating:
//                ratingItem.setVisible(false);
//                alphaItem.setVisible(true);
//                spEditor.putInt("REVIEWS_SORT_PREFERENCE", SORT_DEFAULT_ALPHA).commit();
//                sortReviewsByAlphabetical();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
