package com.victuallist.winereviewer.data.localpersistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.objects.ReviewObject;

public class CellarHub {
	
	Cursor cursor;
	SQLiteDatabase db = null;
	ContentValues databaseValues = null;
	DataHandler dataHandler;
	int DATABASE_VERSION_NUMBER = 4;


	public void saveItemInCellar (Context context, CellarObject newCellarItem) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		
		if(newCellarItem.getUPC() != null) {databaseValues.put("upc", newCellarItem.getUPC()); }
		if (newCellarItem.getName() != null) {databaseValues.put("name", newCellarItem.getName()); }
		databaseValues.put("vineyard", newCellarItem.getVineyard());
		databaseValues.put("varietal", newCellarItem.getVarietal());
		databaseValues.put("vintage", newCellarItem.getVintage());
		if (newCellarItem.getOrigin() != null) { databaseValues.put("origin", newCellarItem.getOrigin()); }
		if (newCellarItem.getPurchasePrice() != null) { databaseValues.put("price", newCellarItem.getPurchasePrice()); }
		databaseValues.put("bottles", newCellarItem.getBottleCount()); 
		if (newCellarItem.getBottleSize() != null) { databaseValues.put("size", newCellarItem.getBottleSize()); }

		//TODO:
		if (newCellarItem.getPurchaseDate() != null) { databaseValues.put("buydate", (newCellarItem.getPurchaseDate())); }
		if (newCellarItem.getLocation() != null) { databaseValues.put("location", newCellarItem.getLocation()); }

		//TODO:
		if (newCellarItem.getDrinkByDate() != null) { databaseValues.put("drinkby", newCellarItem.getDrinkByDate()); }
		if (newCellarItem.getCurrentValue() != null) { databaseValues.put("value", newCellarItem.getCurrentValue()); }
		if (newCellarItem.getNotes() != null) { databaseValues.put("notes", newCellarItem.getNotes()); }
		
		if (newCellarItem.getUploaded()) {
			databaseValues.put("uploaded", "1");
		} else {
			databaseValues.put("uploaded", "0");
		}
		
		if (newCellarItem.getShared()) {
			databaseValues.put("shared", "1");
		} else {
			databaseValues.put("shared", "0");
		}
		
		db.insert("localcellar", "", databaseValues);
		databaseValues.clear();
		

		db.close();
		
	}


	public void updateCellarItem (Context context, CellarObject cellarObject) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
	
		SQLiteStatement insertStatement = db.compileStatement("UPDATE localcellar " +
				"SET name = ?, " +
        		"vineyard = ?, " +
				"varietal = ?, " +
        		"vintage = ?, " +
				"origin = ?, " +
        		"price = ?, " +
				"bottles = ?, " +
        		"size = ?, " +
				"buydate = ?, " +
        		"location = ?, " +
				"drinkby = ?, " +
        		"value = ?, " +
				"notes = ?" +
				"WHERE _id = ?");
		
		if (cellarObject.getName() != null) {
			insertStatement.bindString(1, cellarObject.getName());
		} else {
			insertStatement.bindNull(1);
		}

		Log.i("CellarHub", cellarObject.getVineyard());

		insertStatement.bindString(2, cellarObject.getVineyard());
		insertStatement.bindString(3, cellarObject.getVarietal());
		insertStatement.bindString(4, cellarObject.getVintage());
		
		if (cellarObject.getOrigin() != null) {
			insertStatement.bindString(5, cellarObject.getOrigin());
		} else {
			insertStatement.bindNull(5);
		}
		if (cellarObject.getPurchasePrice() != null) {
			insertStatement.bindString(6, cellarObject.getPurchasePrice());
		} else {
			insertStatement.bindNull(6);
		}
		
		insertStatement.bindDouble(7, cellarObject.getBottleCount());
		
			
		if (cellarObject.getBottleSize() != null) {
			insertStatement.bindString(8, cellarObject.getBottleSize());
		} else {
			insertStatement.bindNull(8);
		}
		if (cellarObject.getPurchaseDate() != null) {
			insertStatement.bindString(9, cellarObject.getPurchaseDate());
		} else {
			insertStatement.bindNull(9);
		}
		if (cellarObject.getLocation() != null) {
			insertStatement.bindString(10, cellarObject.getLocation());
		} else {
			insertStatement.bindNull(10);
		}
		if (cellarObject.getDrinkByDate() != null) {
			insertStatement.bindString(11, cellarObject.getDrinkByDate());
		} else {
			insertStatement.bindNull(11);
		}
		if (cellarObject.getCurrentValue() != null) {
			insertStatement.bindString(12, cellarObject.getCurrentValue());
		} else {
			insertStatement.bindNull(12);
		}
		if (cellarObject.getNotes() != null) {
			insertStatement.bindString(13, cellarObject.getNotes());
		} else {
			insertStatement.bindNull(13);
		}

		Log.i("CellarHub", String.valueOf(cellarObject.getSqlID()));

		insertStatement.bindDouble(14, cellarObject.getSqlID());
					
		insertStatement.executeInsert();

//		insertStatement.executeUpdateDelete();

		db.close();
		
	}


	public void deleteCellarItem(Context context, CellarObject cellarObject) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
				
		SQLiteStatement  deleteStatement = db.compileStatement("DELETE FROM localcellar WHERE _id = ? ");
		
		deleteStatement.bindDouble(1, cellarObject.getSqlID());
							
		deleteStatement.execute();
		
		db.close();
	}


	public ArrayList<CellarObject> loadCellar(Context context) {

		ArrayList<CellarObject> loadedCellarItems = new ArrayList<CellarObject>();

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		cursor = db.rawQuery("SELECT _id, " +
				"upc, " + "name, " +
				"vineyard, " + "varietal, " +
				"vintage, " + "origin, " +
				"price, " +	"bottles, " +
				"size, " + "buydate, " +
				"location, " + "drinkby, " +
				"value, " + "notes," +
				"uploaded, " + "shared " +
				"FROM localcellar ORDER BY vineyard;", null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			for(int i = 1; i <= cursor.getCount(); i++) {

				try {

					CellarObject objectToInsert = new CellarObject();


					objectToInsert.setSqlID(cursor.getInt(0));

					if(cursor.getString(1) != null) {
						objectToInsert.setUPC(cursor.getString(1));
					}
					if(cursor.getString(2) != null){
						objectToInsert.setName(cursor.getString(2));
					}

					objectToInsert.setVineyard(cursor.getString(3));
					objectToInsert.setVarietal(cursor.getString(4));
					objectToInsert.setVintage(cursor.getString(5));

					if(cursor.getString(6) != null){
						objectToInsert.setOrigin(cursor.getString(6));
					}

					if(cursor.getString(7) != null){
						objectToInsert.setPurchasePrice(cursor.getString(7));
					}

					if(cursor.getString(8) != null){
						objectToInsert.setBottleCount(cursor.getInt(8));
					}

					if(cursor.getString(9) != null){
						objectToInsert.setBottleSize(cursor.getString(9));
					}

					//TODO:
					if(cursor.getString(10) != null){
						objectToInsert.setPurchaseDate(cursor.getString(10));
					}

					if(cursor.getString(11) != null){
						objectToInsert.setLocation(cursor.getString(11));
					}

					//TODO:
					if(cursor.getString(12) != null){
						objectToInsert.setDrinkByDate(cursor.getString(12));
					}

					if(cursor.getString(13) != null){
						objectToInsert.setCurrentValue(cursor.getString(13));
					}

					if(cursor.getString(14) != null){
						objectToInsert.setNotes(cursor.getString(14));
					}

					// the following are being stored as Integers and need
					// to be converted to booleans
					if (cursor.getString(15).equals("1")) {
						objectToInsert.setUploaded(true);
					}

					if (cursor.getString(16).equals("1")) {
						objectToInsert.setShared(true);
					}

//        			objectToInsert.setRelatedReviews(this.loadReviews(context, objectToInsert));


					loadedCellarItems.add(objectToInsert);

					cursor.moveToNext();

				} catch (Exception e) {}

			}



		}


		cursor.close();
		db.close();

		return loadedCellarItems;

	}


	public ArrayList<ReviewObject> loadReviews (Context context, CellarObject cellarObject) {
		
		ArrayList<ReviewObject> loadedReviews = new ArrayList<ReviewObject>();
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		String[] returnColumns = new String[] {"_id", "vineyard", "varietal", "vintage", "rating", "origin", "price", "pairing", "color", "taste", "smell", "uploaded", "shared", "name"};
		String whereClause = "vineyard = ? AND varietal = ? AND vintage = ?";
		String[] whereValues = new String[] {cellarObject.getVineyard(), cellarObject.getVarietal(), cellarObject.getVintage()};
		
		cursor = db.query("localreviews", 
						returnColumns, 
						whereClause, 
						whereValues, 
						null, 
						null, 
						null);
		        
        cursor.moveToFirst();
        
        while (!cursor.isAfterLast()) {
        	
        	for(int i = 1; i <= cursor.getCount(); i++) {
        		
        		try {
        			
        			ReviewObject objectToInsert = new ReviewObject(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
        			
        			objectToInsert.setSQLID(cursor.getInt(0));
        			
        			// the following might be null
        			// need to do something about that here.
        			if (cursor.getString(5) != null) {
        				objectToInsert.setOrigin(cursor.getString(5));
        			}
        			if (cursor.getString(6) != null) {
        				objectToInsert.setPrice(cursor.getString(6));
        			}
        			if (cursor.getString(7) != null) {
        				objectToInsert.setPairing(cursor.getString(7));
        			}
        			if (cursor.getString(8) != null) {
        				objectToInsert.setColor(cursor.getString(8));
        			}
        			if (cursor.getString(9) != null) {
        				objectToInsert.setTaste(cursor.getString(9));
        			}
        			if (cursor.getString(10) != null) {
        				objectToInsert.setSmell(cursor.getString(10));
        			}
        			        			
        			// the following are being stored as Integers and need
        			// to be converted to booleans
        			if (cursor.getString(11).equals("1")) {
        				objectToInsert.setUploaded(true);
        			}
        			
        			if (cursor.getString(12).equals("1")) {
        				objectToInsert.setShared(true);
        			}
        			
        			if (cursor.getString(13) != null) {
        				objectToInsert.setName(cursor.getString(13));
        			}
        			
        			loadedReviews.add(objectToInsert);
        		
        			cursor.moveToNext();

        		} catch (Exception e) {}
        		
        	}
        	

    	
        }
        

        cursor.close();
        db.close();
        
        return loadedReviews;
	}


}
