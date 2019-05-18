package com.victuallist.winereviewer.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.victuallist.winereviewer.data.localpersistence.DataHub;
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AnonymousReviewsUpload implements Runnable {

    String LOG_TAG = "AnonymousReviewsUpload";

    DataHub dataHub;

    Context context;

    public AnonymousReviewsUpload(Context c){
        this.context = c;
    }

    @Override
    public void run() {

        dataHub = new DataHub();
        ArrayList<ReviewObject> reviews = new ArrayList<>();
        reviews = dataHub.loadUnsharedReviews(context);

        if(reviews.size() > 0){

            Gson gson = new Gson();
            HttpURLConnection urlConnection = null;

            try {

                //TODO: REDACTED FOR PUBLIC REPOSITORY
                String urlString = "[URL HERE]";
                URL url = new URL(urlString);

                String dataPayload = gson.toJson(reviews).toString();

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

                if(responseCodeInt == 200){

                    for(int i = 0; i < reviews.size(); i++){

                        reviews.get(i).setShared(true);
                        dataHub.setReviewSharedStatus(context, reviews.get(i));

                    }

                }

            }catch (IOException e){
                Log.e(LOG_TAG, "IOException: " + e.toString());
            }finally {
                urlConnection.disconnect();
            }

        }

    }

}
