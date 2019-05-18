package com.victuallist.winereviewer.data.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.victuallist.winereviewer.data.localpersistence.DataHub;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;
import com.victuallist.winereviewer.data.objects.DetailReviewObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PersistentReviews extends AndroidViewModel implements ViewModelProvider.Factory{

    String LOG_TAG = "PersistentReviews";

    private MutableLiveData<ArrayList<CoreReviewObject>> loadedPopularReviews;

    DataHub dataHub;
    Context context;
     List<Integer> popularReviewsList;

    public PersistentReviews(@NonNull Application application) {
        super(application);
        dataHub = new DataHub();
        this.context = application.getApplicationContext();
        popularReviewsList = dataHub.loadPopularFavorites(context);

    }


    public LiveData<ArrayList<CoreReviewObject>> getPopularReviews(){
        if(loadedPopularReviews == null){
            loadedPopularReviews = new MutableLiveData<ArrayList<CoreReviewObject>>();
            loadReviews();
        }
        return loadedPopularReviews;
    }


    private void loadReviews(){

        Log.i(LOG_TAG, "LOAD REVIEWS");
        Runnable getPopularReviews = new GetPopularReviews();
        Thread runnableThread = new Thread(getPopularReviews);
        runnableThread.start();

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }


    class GetPopularReviews implements Runnable {

        String LOG_TAG = "GetPopularReviews";


        @Override
        public void run() {

            ArrayList<CoreReviewObject> reviews = new ArrayList<>();
            Gson gson = new Gson();
            HttpURLConnection urlConnection = null;
            //TODO: REDACTED FOR PUBLIC REPOSITORY
            String urlString = "[URL HERE]";

            try {

                URL url = new URL(urlString);

                String dataPayload = gson.toJson(popularReviewsList).toString();

                Log.i(LOG_TAG, dataPayload);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream os =  urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8")
                );
                writer.write(dataPayload);

                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();

                int responseCodeInt = urlConnection.getResponseCode();

                if (responseCodeInt == 200) {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String responseString = readStream(in);

                    JSONArray responseArray = new JSONArray(responseString);
                    for (int i = 0; i < responseArray.length(); i++) {
                        String jsonObject = responseArray.getJSONObject(i).toString();
                        Log.i(LOG_TAG, jsonObject);
                        int reviewID = (Integer) responseArray.getJSONObject(i).get("review_id");
                        String name = (String) responseArray.getJSONObject(i).get("name");
                        String vineyard = (String) responseArray.getJSONObject(i).get("vineyard");
                        String varietal = (String) responseArray.getJSONObject(i).get("varietal");
                        String vintage = (String) responseArray.getJSONObject(i).get("vintage");
                        String origin = (String) responseArray.getJSONObject(i).get("origin");
                        String lastReviewDate = (String) responseArray.getJSONObject(i).get("last_review_date");
                        String rating = (String) responseArray.getJSONObject(i).get("rating");
                        int numberOfRatings = (Integer) responseArray.getJSONObject(i).get("number_of_ratings");

                        CoreReviewObject review = new CoreReviewObject(vineyard, varietal, vintage, rating);
                        review.setName(name);
                        review.setSQLID(reviewID);
                        review.setOrigin(origin);
                        review.setNumberOfReviewsInt(numberOfRatings);

                        if(popularReviewsList.contains(reviewID)){
                            review.setFavorite(true);
                        }

                        JSONArray detailsArray = responseArray.getJSONObject(i).getJSONArray("details");
                        for (int k = 0; k < detailsArray.length(); k++) {

                            DetailReviewObject detail = new DetailReviewObject();

                            try {
                                String ratingDetail = (String) detailsArray.getJSONObject(k).get("rating");
                                detail.setRating(ratingDetail);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castRatingDetail");
                                detail.setRating("");
                            }

                            try {
                                String price = (String) detailsArray.getJSONObject(k).get("price");
                                detail.setPrice(price);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castPriceDetail");
                                detail.setPrice("");
                            }

                            try {
                                String pairing = (String) detailsArray.getJSONObject(k).get("pairing");
                                detail.setPairing(pairing);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castPairingDetail");
                                detail.setPairing("");
                            }

                            try {
                                String taste = (String) detailsArray.getJSONObject(k).get("taste");
                                detail.setTaste(taste);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castTasteDetail");
                                detail.setTaste("");
                            }

                            try {
                                String smell = (String) detailsArray.getJSONObject(k).get("smell");
                                detail.setSmell(smell);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castSmellDetail");
                                detail.setSmell("");
                            }

                            try {
                                String color = (String) detailsArray.getJSONObject(k).get("color");
                                detail.setColor(color);
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castColorDetail");
                                detail.setColor("");
                            }

                            try {
                                String lastReviewDateDetail = (String) detailsArray.getJSONObject(k).get("last_review_date");
                            } catch (ClassCastException e) {
                                Log.i(LOG_TAG, "ClassCastException: castLastReviewDateDetail");
                            }

                            review.addReviewDetail(detail);

                            review.setUserHasReviewed(dataHub.userHasReviewedPopularWine(context, review));

                            Log.i(LOG_TAG, String.valueOf(review.isUserHasReviewed()));

                        }


                        reviews.add(review);
                    }

                    loadedPopularReviews.postValue(reviews);

                } else {

                    Log.i(LOG_TAG, "responseCode not 200");
                    String responseMessage = urlConnection.getResponseMessage();
                    Log.i(LOG_TAG, "response message: " + responseMessage);

                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "IOException: " + e.toString());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException: " + e.toString());
            } finally {
                urlConnection.disconnect();
            }

        }


        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PersistentReviews(mApplication);
        }



    }


}
