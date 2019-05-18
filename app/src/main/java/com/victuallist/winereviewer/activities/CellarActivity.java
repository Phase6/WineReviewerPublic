package com.victuallist.winereviewer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.victuallist.winereviewer.R;
import com.victuallist.winereviewer.data.localpersistence.CellarHub;
import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.objects.ReviewObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class CellarActivity extends AppCompatActivity {

    String LOG_TAG = "CellarActivity";

    boolean isNewCellarItem;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    CellarHub cellarHub;
    CellarObject oldCellarItem, cellarItem;
    ReviewObject reviewToAddToCellar;

    TextView nameLabel, vineyardLabel, varietalLabel, vintageLabel, originLabel, priceLabel,
    bottlesLabel, sizeLabel, buyDateLabel, locationLabel, drinkByLabel, valueLabel, notesLabel;

    AutoCompleteTextView nameACTextView, vineyardACTextView, varietalACTextView, vintageACTextView,
            originACTextView, priceACTextView, bottlesACTextView, sizeACTextView, buyDateACTextView,
            locationACTextView, drinkByACTextView, valueACTextView, notesACTextView;

    String[] vineyardsArray, varietalsArray, vintagesArray, originsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellar);

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            oldCellarItem = (CellarObject) bundle.getSerializable("SELECTEDCELLARITEM");
        }catch(Exception e){

        }

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            reviewToAddToCellar = (ReviewObject) bundle.getSerializable("SELECTEDREVIEWTOADDTOCELLAR");
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

        cellarHub = new CellarHub();

        if(oldCellarItem == null){
            isNewCellarItem = true;
        }else{
//            Log.i(LOG_TAG, String.valueOf(oldCellarItem.getSqlID()));
            isNewCellarItem = false;
            cellarItem = oldCellarItem;
            populateFormWithReceivedData();
        }

        if(reviewToAddToCellar != null){
            populateFormWithReviewData();
        }


    }


    public void instantiateLabels(){
        nameLabel = (TextView) findViewById(R.id.cellarActivityLabelName);
        vineyardLabel = (TextView) findViewById(R.id.cellarActivityLabelVineyard);
        varietalLabel = (TextView) findViewById(R.id.cellarActivityLabelVarietal);
        vintageLabel = (TextView) findViewById(R.id.cellarActivityLabelVintage);
        originLabel = (TextView) findViewById(R.id.cellarActivityLabelOrigin);
        priceLabel = (TextView) findViewById(R.id.cellarActivityLabelPrice);
        bottlesLabel = (TextView) findViewById(R.id.cellarActivityLabelBottles);
        sizeLabel = (TextView) findViewById(R.id.cellarActivityLabelSize);
        buyDateLabel = (TextView) findViewById(R.id.cellarActivityLabelBuyDate);
        locationLabel = (TextView) findViewById(R.id.cellarActivityLabelLocation);
        drinkByLabel = (TextView) findViewById(R.id.cellarActivityLabelDrinkBy);
        valueLabel = (TextView) findViewById(R.id.cellarActivityLabelValue);
        notesLabel = (TextView) findViewById(R.id.cellarActivityLabelNotes);
    }


    public void instantiateAutoCompleteTextViews(){
        nameACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteName);

        vineyardACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteVineyard);
        ArrayAdapter<String> adapterVineyards = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, vineyardsArray);
        vineyardACTextView.setAdapter(adapterVineyards);

        varietalACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteVarietal);
        ArrayAdapter<String> adapterVarietals = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, varietalsArray);
        varietalACTextView.setAdapter(adapterVarietals);

        vintageACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteVintage);
        ArrayAdapter<String> adapterVintages = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, vintagesArray);
        vintageACTextView.setAdapter(adapterVintages);

        originACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteOrigin);
        ArrayAdapter<String> adapterOrigins = new ArrayAdapter<String>(getApplicationContext(), R.layout.autocomplete_dropdown_line, originsArray);
        originACTextView.setAdapter(adapterOrigins);

        priceACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompletePrice);

        bottlesACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteBottles);
        sizeACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteSize);
        buyDateACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteBuyDate);
        locationACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteLocation);
        drinkByACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteDrinkBy);
        valueACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteValue);
        notesACTextView = (AutoCompleteTextView) findViewById(R.id.cellarActivityAutoCompleteNotes);

    }


    public void populateFormWithReceivedData(){
        nameACTextView.setText(oldCellarItem.getName());
        vineyardACTextView.setText(oldCellarItem.getVineyard());
        varietalACTextView.setText(oldCellarItem.getVarietal());
        vintageACTextView.setText(oldCellarItem.getVintage());
        originACTextView.setText(oldCellarItem.getOrigin());
        priceACTextView.setText(oldCellarItem.getPurchasePrice());
        bottlesACTextView.setText(String.valueOf(oldCellarItem.getBottleCount()));
        sizeACTextView.setText(oldCellarItem.getBottleSize());
        buyDateACTextView.setText(oldCellarItem.getPurchaseDate());
        locationACTextView.setText(oldCellarItem.getLocation());
        valueACTextView.setText(oldCellarItem.getCurrentValue());
        notesACTextView.setText(oldCellarItem.getNotes());
    }


    public void populateFormWithReviewData(){
        nameACTextView.setText(reviewToAddToCellar.getName());
        vineyardACTextView.setText(reviewToAddToCellar.getVineyard());
        varietalACTextView.setText(reviewToAddToCellar.getVarietal());
        vintageACTextView.setText(reviewToAddToCellar.getVintage());
        originACTextView.setText(reviewToAddToCellar.getOrigin());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.review_activity_action_buttons, menu);
        if(isNewCellarItem){
            MenuItem deleteItem = menu.findItem(R.id.deleteReviewButton);
            deleteItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveReviewButton:

                if(validateReview() && isNewCellarItem){
                    Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show();
                    cellarHub.saveItemInCellar(this, cellarItem);
                    finish();
                }else if(validateReview() && !isNewCellarItem) {
                    Toast.makeText(this, "Entry updated!", Toast.LENGTH_SHORT).show();
                    cellarHub.updateCellarItem(this, cellarItem);
                    finish();
                }else{
                    Toast.makeText(this, "Missing information", Toast.LENGTH_LONG).show();
                }


                return true;

            case R.id.deleteReviewButton:

                Toast.makeText(this, "Delete!", Toast.LENGTH_SHORT).show();
                cellarHub.deleteCellarItem(this, oldCellarItem);
                finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public boolean validateReview(){

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

        if(formIsValid){
            String name = nameACTextView.getText().toString();

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

            String price = priceACTextView.getText().toString();
            String bottles = bottlesACTextView.getText().toString();
            String size = sizeACTextView.getText().toString();
            String buyDate = buyDateACTextView.getText().toString();
            String location = locationACTextView.getText().toString();
            String drinkDate = drinkByACTextView.getText().toString();
            String value = valueACTextView.getText().toString();
            String notes = notesACTextView.getText().toString();


            if(oldCellarItem == null){
                cellarItem = new CellarObject();
            }


            cellarItem.setName(name);
            cellarItem.setVineyard(vineyard);
            cellarItem.setVarietal(varietal);
            cellarItem.setVintage(vintage);
            cellarItem.setOrigin(origin);
            cellarItem.setPurchasePrice(price);
            int bottleCount = 0;
            try{
                bottleCount = Integer.parseInt(bottles);
            }catch (Exception e){

            }
            cellarItem.setBottleCount(bottleCount);
            cellarItem.setBottleSize(size);
            cellarItem.setPurchaseDate(buyDate);
            cellarItem.setLocation(location);
            cellarItem.setDrinkByDate(drinkDate);
            cellarItem.setCurrentValue(value);
            cellarItem.setNotes(notes);
        }



        return formIsValid;
    }


}
