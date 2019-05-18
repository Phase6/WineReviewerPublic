package com.victuallist.winereviewer.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;
import com.victuallist.winereviewer.data.localpersistence.DataHub;
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class ReviewActivity extends AppCompatActivity {

    String LOG_TAG = "ReviewActivity";

    boolean isNewReview;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    DataHub dataHub;
    ReviewObject oldReview, review;
    CellarObject cellarObjectToReview;
    CoreReviewObject coreReviewObjectToReview;

    TextView    nameLabel, vineyardLabel, varietalLabel, vintageLabel, originLabel, ratingLabel,
                priceLabel, pairingLabel, tasteLabel, smellLabel, colorLabel;

    AutoCompleteTextView nameACTextView, vineyardACTextView, varietalACTextView, vintageACTextView,
                            originACTextView, priceACTextView, pairingACTextView, tasteACTextView,
                            smellACTextView, colorACTextView;

    String[] vineyardsArray, varietalsArray, vintagesArray, originsArray;

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            oldReview = (ReviewObject) bundle.getSerializable("SELECTEDREVIEW");
        }catch(Exception e){

        }

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            cellarObjectToReview = (CellarObject) bundle.getSerializable("SELECTEDCELLARITEMTOREVIEW");
        }catch(Exception e){

        }

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            coreReviewObjectToReview = (CoreReviewObject) bundle.getSerializable("SELECTEDPOPULARREVIEW");
        }catch(Exception e){

        }


        sharedPreferences = getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();

        vineyardsArray = sharedPreferences.getStringSet("VINEYARDS", null).toArray(new String[0]);
        varietalsArray = sharedPreferences.getStringSet("VARIETALS", null).toArray(new String[0]);
        vintagesArray = new String[20];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = 0; i < 20; i++){
            vintagesArray[i] = String.valueOf(currentYear - i);
        }
        originsArray = sharedPreferences.getStringSet("ORIGINS", null).toArray(new String[0]);



        instantiateLabels();
        instantiateAutoCompleteTextViews();
        ratingBar = (RatingBar) findViewById(R.id.reviewActivityRatingBar);

        dataHub = new DataHub();

        if(oldReview == null){
            isNewReview = true;
        }else{
            isNewReview = false;
            review = oldReview;
            populateFormWithReceivedData();
        }

        if(cellarObjectToReview != null){
            populateReviewWithCellarData();
        }

        if(coreReviewObjectToReview != null){
            populateReviewWithCoreReviewData();
        }


    }


    public void instantiateLabels(){
        nameLabel = (TextView) findViewById(R.id.reviewActivityLabelName);
        vineyardLabel = (TextView) findViewById(R.id.reviewActivityLabelVineyard);
        varietalLabel = (TextView) findViewById(R.id.reviewActivityLabelVarietal);
        vintageLabel = (TextView) findViewById(R.id.reviewActivityLabelVintage);
        originLabel = (TextView) findViewById(R.id.reviewActivityLabelOrigin);
        ratingLabel = (TextView) findViewById(R.id.reviewActivityLabelRating);
        priceLabel = (TextView) findViewById(R.id.reviewActivityLabelPrice);
        pairingLabel = (TextView) findViewById(R.id.reviewActivityLabelPairing);
        tasteLabel = (TextView) findViewById(R.id.reviewActivityLabelTaste);
        smellLabel = (TextView) findViewById(R.id.reviewActivityLabelSmell);
        colorLabel = (TextView) findViewById(R.id.reviewActivityLabelColor);
    }


    public void instantiateAutoCompleteTextViews(){
        nameACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteName);

        vineyardACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteVineyard);
        ArrayAdapter<String> adapterVineyards = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, vineyardsArray);
        vineyardACTextView.setAdapter(adapterVineyards);

        varietalACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteVarietal);
        ArrayAdapter<String> adapterVarietals = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, varietalsArray);
        varietalACTextView.setAdapter(adapterVarietals);

        vintageACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteVintage);
        ArrayAdapter<String> adapterVintages = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, vintagesArray);
        vintageACTextView.setAdapter(adapterVintages);

        originACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteOrigin);
        ArrayAdapter<String> adapterOrigins = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, originsArray);
        originACTextView.setAdapter(adapterOrigins);

        priceACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompletePrice);
        pairingACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompletePairing);
        tasteACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteTaste);
        smellACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteSmell);
        colorACTextView = (AutoCompleteTextView) findViewById(R.id.reviewActivityAutoCompleteColor);
    }


    public void populateFormWithReceivedData(){
        nameACTextView.setText(review.getName());
        vineyardACTextView.setText(review.getVineyard());
        varietalACTextView.setText(review.getVarietal());
        vintageACTextView.setText(review.getVintage());
        originACTextView.setText(review.getOrigin());
        ratingBar.setRating(review.getFloatRating());
        priceACTextView.setText(review.getPrice());
        pairingACTextView.setText(review.getPairing());
        tasteACTextView.setText(review.getTaste());
        smellACTextView.setText(review.getSmell());
        colorACTextView.setText(review.getColor());
    }


    public void populateReviewWithCellarData(){
        nameACTextView.setText(cellarObjectToReview.getName());
        vineyardACTextView.setText(cellarObjectToReview.getVineyard());
        varietalACTextView.setText(cellarObjectToReview.getVarietal());
        vintageACTextView.setText(cellarObjectToReview.getVintage());
        originACTextView.setText(cellarObjectToReview.getOrigin());
    }


    public void populateReviewWithCoreReviewData(){
        nameACTextView.setText(coreReviewObjectToReview.getName());
        vineyardACTextView.setText(coreReviewObjectToReview.getVineyard());
        varietalACTextView.setText(coreReviewObjectToReview.getVarietal());
        vintageACTextView.setText(coreReviewObjectToReview.getVintage());
        originACTextView.setText(coreReviewObjectToReview.getOrigin());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_activity_action_buttons, menu);
        if(isNewReview){
            MenuItem deleteItem = menu.findItem(R.id.deleteReviewButton);
            deleteItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveReviewButton:
                validateReview();
                return true;

            case R.id.deleteReviewButton:

                Toast.makeText(this, "Delete!", Toast.LENGTH_SHORT).show();
                dataHub.deleteReview(this, oldReview);
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void validateReview(){

        boolean essentailsCompleted = validateEssentialElementsOfForm();

        if(essentailsCompleted && ratingBar.getRating() > 0){
            createOrUpdateAndFinish();
        }else if(essentailsCompleted && ratingBar.getRating() == 0){
            //TODO: DISPLAY DIALOG TO CONFIRM 0 RATING WITH USER
            //TODO: DIALOG WILL EITHER BE OKAY OR CANCEL - IF OKAY, THEN CALL createOrUpdateAndFinish
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rating");
            builder.setMessage("You have rated this wine zero stars.  Is that what yout want?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    createOrUpdateAndFinish();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else if(!essentailsCompleted){
            Toast.makeText(this, "Missing information", Toast.LENGTH_LONG).show();
        }

    }


    public boolean validateEssentialElementsOfForm(){

        boolean formIsValid = true;
        int black = getResources().getColor(R.color.black);
        int red = getResources().getColor(R.color.red);

        String vineyard = vineyardACTextView.getText().toString();
        if(vineyard.length() < 1){
            formIsValid = false;
            vineyardLabel.setTextColor(red);
        }else{
            vineyardLabel.setTextColor(black);
        }

        String varietal = varietalACTextView.getText().toString();
        if(varietal.length() < 1){
            formIsValid = false;
            varietalLabel.setTextColor(red);
        }else{
            varietalLabel.setTextColor(black);
        }

        String vintage = vintageACTextView.getText().toString();
        if(vintage.length() < 1){
            formIsValid = false;
            vintageLabel.setTextColor(red);
        }else{
            vintageLabel.setTextColor(black);
        }

        return formIsValid;

    }


    public void createOrUpdateAndFinish(){

        String name = nameACTextView.getText().toString();
        String vineyard = vineyardACTextView.getText().toString();
        String varietal = varietalACTextView.getText().toString();
        String vintage = vintageACTextView.getText().toString();


        if(!Arrays.asList(vineyardsArray).contains(vineyard)){
            Set<String> vineyards = new HashSet<>(Arrays.asList(vineyardsArray));
            vineyards.add(vineyard);
            spEditor = sharedPreferences.edit();
            spEditor.putStringSet("VINEYARDS", vineyards);
            spEditor.commit();
        }

        if(!Arrays.asList(varietalsArray).contains(varietal)){
            Set<String> varietals = new HashSet<>(Arrays.asList(varietalsArray));
            varietals.add(varietal);
            spEditor = sharedPreferences.edit();
            spEditor.putStringSet("VARIETALS", varietals);
            spEditor.commit();
        }

        String origin = originACTextView.getText().toString();
        if(!Arrays.asList(originsArray).contains(origin)){
            Set<String> origins = new HashSet<>(Arrays.asList(originsArray));
            origins.add(origin);
            spEditor = sharedPreferences.edit();
            spEditor.putStringSet("ORIGINS", origins);
            spEditor.commit();
        }
        float rating = ratingBar.getRating();

        String ratingString = String.valueOf(rating);

        String price = priceACTextView.getText().toString();

        String pairing = pairingACTextView.getText().toString();

        String taste = tasteACTextView.getText().toString();

        String smell = smellACTextView.getText().toString();

        String color = colorACTextView.getText().toString();

        review = new ReviewObject(vineyard, varietal, vintage, ratingString);
        review.setName(name);
        review.setOrigin(origin);
        review.setPrice(price);
        review.setPairing(pairing);
        review.setTaste(taste);
        review.setSmell(smell);
        review.setColor(color);

        if(isNewReview){
            Toast.makeText(this, "Review saved!", Toast.LENGTH_SHORT).show();
            dataHub.saveReview(this, review);
            finish();
        }else if(!isNewReview) {
            Toast.makeText(this, "Review updated!", Toast.LENGTH_SHORT).show();
            dataHub.updateReview(this, oldReview, review);
            finish();
        }

    }


}
