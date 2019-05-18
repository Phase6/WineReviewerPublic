package com.victuallist.winereviewer;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.victuallist.winereviewer.data.objects.GuideObject;
import com.victuallist.winereviewer.fragments.AboutFragment;
import com.victuallist.winereviewer.fragments.CellarFragment;
import com.victuallist.winereviewer.fragments.GuideDetailFragment;
import com.victuallist.winereviewer.fragments.GuideFragment;
import com.victuallist.winereviewer.fragments.LoginFragment;
import com.victuallist.winereviewer.fragments.PopularFragment;
import com.victuallist.winereviewer.fragments.PreferencesFragment;
import com.victuallist.winereviewer.fragments.ReviewsFragment;
import com.victuallist.winereviewer.network.AnonymousReviewsUpload;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    String LOG_TAG = "MainActivity";

    int fragmentContainer;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Menu activityMenu;

    int SEED_DATA_VERSION = 1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    BottomNavigationView navigation;
    int LAST_FRAGMENT_DISPLAYED = 0;
    int REVIEWS_FRAGMENT_ID = 0;
    int CELLAR_FRAGMENT_ID = 1;
    int GUIDE_FRAGMENT_ID = 2;
    int GUIDE_DETAIL_FRAGMENT_ID = 3;
    int POPULAR_FRAGMENT_ID = 4;
    int PREFERENCES_FRAGMENT_ID = 5;
    int ABOUT_FRAGMENT_ID = 6;
    int LOGIN_FRAGMENT_ID = 7;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_reviews:
                    LAST_FRAGMENT_DISPLAYED = REVIEWS_FRAGMENT_ID;
                    ReviewsFragment reviewsFragment = new ReviewsFragment();
                    fragmentTransaction.replace(fragmentContainer, reviewsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_cellar:
                    LAST_FRAGMENT_DISPLAYED = CELLAR_FRAGMENT_ID;
                    CellarFragment cellarFragment = new CellarFragment();
                    fragmentTransaction.replace(fragmentContainer, cellarFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_guide:
                    LAST_FRAGMENT_DISPLAYED = GUIDE_FRAGMENT_ID;
                    GuideFragment guideFragment = new GuideFragment();
                    fragmentTransaction.replace(fragmentContainer,  guideFragment);
                    fragmentTransaction.commit();
                    return true;
                    //TODO: THE FOLLOWING CODE IS FOR THE POPULAR REVIEWS FRAGMENT
                    //TODO: COMMENT IT OUT UNTIL IT IS READY FOR PRODUCTION
                case R.id.navigation_popular_reviews:
                    LAST_FRAGMENT_DISPLAYED = POPULAR_FRAGMENT_ID;
                    PopularFragment popularFragment = new PopularFragment();
                    fragmentTransaction.replace(fragmentContainer, popularFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);

        int currentSeedDataVersion = sharedPreferences.getInt("seed_data_version", 0);
        switch (currentSeedDataVersion){

            case 0:
                unpackSeedDataForAutoCompleteTextViews();
                updateSeedDataVersion();
                break;

            case 1:
                //updateSeedDataVersion();
                break;

            default:
                break;
        }

        fragmentContainer = R.id.fragment_container;
        fragmentManager = getSupportFragmentManager();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


//TODO: IMPLEMENT FRAGMENTS FOR THE ACTIVITY ACTION MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_activity_menu, menu);
//        activityMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMenuItemPreferences:
                setPreferencesFragment();
                return true;
            case R.id.actionMenuItemAbout:
                setAboutFragment();
                return true;
            case R.id.actionMenueItemLogIn:
                setLoginFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void unpackSeedDataForAutoCompleteTextViews(){
        Set<String> vineyards = new HashSet<>(Arrays.asList(getResources().getStringArray(R.array.vineyards)));
        spEditor = sharedPreferences.edit();
        spEditor.putStringSet("VINEYARDS", vineyards);
        spEditor.commit();

        Set<String> varietals = new HashSet<>(Arrays.asList(getResources().getStringArray(R.array.varietals)));
        spEditor = sharedPreferences.edit();
        spEditor.putStringSet("VARIETALS", varietals);
        spEditor.commit();

        Set<String> origins = new HashSet<>(Arrays.asList(getResources().getStringArray(R.array.origins)));
        spEditor = sharedPreferences.edit();
        spEditor.putStringSet("ORIGINS", origins);
        spEditor.commit();
    }


    public void updateSeedDataVersion(){
        spEditor = sharedPreferences.edit();
        spEditor.putInt("seed_data_version", SEED_DATA_VERSION);
        spEditor.commit();
    }


    @Override
    public void onResume(){
        super.onResume();
        LAST_FRAGMENT_DISPLAYED = sharedPreferences.getInt("LAST_FRAGMENT_DISPLAYED", 0);
        if(LAST_FRAGMENT_DISPLAYED == REVIEWS_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_reviews);
        }else if(LAST_FRAGMENT_DISPLAYED == CELLAR_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_cellar);
        }else if(LAST_FRAGMENT_DISPLAYED == GUIDE_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_guide);
        }else if(LAST_FRAGMENT_DISPLAYED == GUIDE_DETAIL_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_guide);
        }else if(LAST_FRAGMENT_DISPLAYED == POPULAR_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_popular_reviews);
        }

        //TODO: THIS CODE IS COMMENTED OUT FOR THE PUBLIC VERSION - WITHOUT A PROPER URL IN AnonymousReviewsUpload IT WILL THROW AN ERROR
//        Runnable networkTest = new AnonymousReviewsUpload(this);
//        Thread runnableThread = new Thread(networkTest);
//        runnableThread.start();
    }


    @Override
    public void onPause(){
        super.onPause();
        spEditor = sharedPreferences.edit();
        spEditor.putInt("LAST_FRAGMENT_DISPLAYED", LAST_FRAGMENT_DISPLAYED).commit();
    }


    @Override
    public void onBackPressed() {

        if(LAST_FRAGMENT_DISPLAYED != REVIEWS_FRAGMENT_ID && LAST_FRAGMENT_DISPLAYED != GUIDE_DETAIL_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_reviews);
        }else if(LAST_FRAGMENT_DISPLAYED == GUIDE_DETAIL_FRAGMENT_ID){
            navigation.setSelectedItemId(R.id.navigation_guide);
        }else{
            super.onBackPressed();
        }

    }


    public void setGuideDetailFragment(GuideObject guideObject){

        fragmentTransaction = fragmentManager.beginTransaction();
        GuideDetailFragment guideDetailFragment = new GuideDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("SELECTEDGUIDEDETAILOBJECT", guideObject);
        guideDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(fragmentContainer, guideDetailFragment);
        fragmentTransaction.commit();
        LAST_FRAGMENT_DISPLAYED = GUIDE_DETAIL_FRAGMENT_ID;

    }


    public void setPreferencesFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        PreferencesFragment preferencesFragment = new PreferencesFragment();
        fragmentTransaction.replace(fragmentContainer, preferencesFragment);
        fragmentTransaction.commit();
        LAST_FRAGMENT_DISPLAYED = PREFERENCES_FRAGMENT_ID;
    }


    public void setAboutFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        AboutFragment aboutFragment = new AboutFragment();
        fragmentTransaction.replace(fragmentContainer, aboutFragment);
        fragmentTransaction.commit();
        LAST_FRAGMENT_DISPLAYED = ABOUT_FRAGMENT_ID;
    }


    public void setLoginFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(fragmentContainer, loginFragment);
        fragmentTransaction.commit();
        LAST_FRAGMENT_DISPLAYED = LOGIN_FRAGMENT_ID;
    }


}
