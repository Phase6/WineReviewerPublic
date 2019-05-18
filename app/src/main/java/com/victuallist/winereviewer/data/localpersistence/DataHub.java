package com.victuallist.winereviewer.data.localpersistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.victuallist.winereviewer.data.objects.CellarObject;
import com.victuallist.winereviewer.data.objects.CoreReviewObject;
import com.victuallist.winereviewer.data.objects.ReviewObject;


public class DataHub {
	
	Cursor cursor;
	SQLiteDatabase db = null;
	ContentValues databaseValues = null;
	DataHandler dataHandler;
	int DATABASE_VERSION_NUMBER = 4;


	
	public void saveReview (Context context, ReviewObject newReview) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		
		databaseValues.put("vineyard", newReview.getVineyard());
		databaseValues.put("varietal", newReview.getVarietal());
		databaseValues.put("vintage", newReview.getVintage());
		databaseValues.put("rating", newReview.getRating());
		if (newReview.getName() != null) {databaseValues.put("name", newReview.getName()); }
		if (newReview.getOrigin() != null) { databaseValues.put("origin", newReview.getOrigin()); }
		if (newReview.getPrice() != null) { databaseValues.put("price", newReview.getPrice()); }
		if (newReview.getPairing() != null) { databaseValues.put("pairing", newReview.getPairing()); }
		if (newReview.getTaste() != null) { databaseValues.put("taste", newReview.getTaste()); }
		if (newReview.getSmell() != null) { databaseValues.put("smell", newReview.getSmell()); }
		if (newReview.getColor() != null) { databaseValues.put("color", newReview.getColor()); }
		
		if (newReview.getUploaded()) {
			databaseValues.put("uploaded", "1");
		} else {
			databaseValues.put("uploaded", "0");
		}
		
		if (newReview.getShared()) {
			databaseValues.put("shared", "1");
		} else {
			databaseValues.put("shared", "0");
		}
		
		db.insert("localreviews", "", databaseValues);
		databaseValues.clear();
		

		db.close();
		
	}


	public void setReviewSharedStatus (Context context, ReviewObject uploadedReview) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		
		SQLiteStatement insertStatement = db.compileStatement("UPDATE localreviews " +
				"SET shared = ? " +
				"WHERE vineyard = ? " +
				"AND varietal = ? " +
				"AND vintage = ? " +
				"AND rating = ?");
		
		if (uploadedReview.getShared()) {
			insertStatement.bindString(1, "2");
		} else if (!uploadedReview.getShared()) {
			insertStatement.bindString(1,  "0");
		}
		
		insertStatement.bindString(2, uploadedReview.getVineyard());
		insertStatement.bindString(3, uploadedReview.getVarietal());
		insertStatement.bindString(4, uploadedReview.getVintage());
		insertStatement.bindString(5, uploadedReview.getRating());
		insertStatement.executeInsert();
		

		db.close();
		
	}


	public ArrayList<ReviewObject> loadReviews(Context context) {
		
		ArrayList<ReviewObject> loadedReviews = new ArrayList<ReviewObject>();
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
        cursor = db.rawQuery("SELECT _id, vineyard, varietal, vintage, rating, origin, price, pairing, color, taste, smell, uploaded, shared, name FROM localreviews ORDER BY vineyard;", null);
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


	public void updateReview (Context context, ReviewObject oldReview, ReviewObject newReview) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		
		
		
		SQLiteStatement insertStatement = db.compileStatement("UPDATE localreviews " +
				"SET vineyard = ?, " +
				"varietal = ?, " +
				"vintage = ?, " +
				"rating = ?, " +
				"origin = ?, " +
				"price = ?, " +
				"pairing = ?, " +
				"taste = ?, " +
				"smell = ?, " +
				"color = ?, " +
				"name = ? " +
				"WHERE vineyard = ? " +
				"AND varietal = ? " +
				"AND vintage = ? " +
				"AND rating = ?");

		insertStatement.bindString(1, newReview.getVineyard());
		insertStatement.bindString(2, newReview.getVarietal());
		insertStatement.bindString(3, newReview.getVintage());
		insertStatement.bindString(4, newReview.getRating());
		
		if (newReview.getOrigin() != null) {
			insertStatement.bindString(5, newReview.getOrigin());
		} else {
			insertStatement.bindNull(5);
		}
		if (newReview.getPrice() != null) {
			insertStatement.bindString(6, newReview.getPrice());
		} else {
			insertStatement.bindNull(6);
		}
		if (newReview.getPairing() != null) {
			insertStatement.bindString(7, newReview.getPairing());
		} else {
			insertStatement.bindNull(7);
		}
		if (newReview.getTaste() != null) {
			insertStatement.bindString(8, newReview.getTaste());
		} else {
			insertStatement.bindNull(8);
		}
		if (newReview.getSmell() != null) {
			insertStatement.bindString(9, newReview.getSmell());
		} else {
			insertStatement.bindNull(9);
		}
		if (newReview.getColor() != null) {
			insertStatement.bindString(10, newReview.getColor());
		} else {
			insertStatement.bindNull(10);
		}
		if(newReview.getName() != null) {
			insertStatement.bindString(11, newReview.getName());
		} else {
			insertStatement.bindNull(11);
		}
					
		insertStatement.bindString(12, oldReview.getVineyard());
		insertStatement.bindString(13, oldReview.getVarietal());
		insertStatement.bindString(14, oldReview.getVintage());
		insertStatement.bindString(15, oldReview.getRating());
					
		insertStatement.executeInsert();
		

		db.close();
		
	}


	public void deleteReview(Context context, ReviewObject reviewToDelete) {
		
		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		
				
		SQLiteStatement  deleteStatement = db.compileStatement("DELETE FROM localreviews " +
																	"WHERE _id = ? " +
																	"AND vineyard = ? " +
																	"AND varietal = ? " +
																	"AND vintage = ? " +
																	"AND rating = ?");
		
		deleteStatement.bindDouble(1, reviewToDelete.getSQLID());
		
		deleteStatement.bindString(2, reviewToDelete.getVineyard());
		
		deleteStatement.bindString(3, reviewToDelete.getVarietal());
		
		deleteStatement.bindString(4, reviewToDelete.getVintage());
		
		deleteStatement.bindString(5, reviewToDelete.getRating());
			
		deleteStatement.execute();
		
		db.close();
	}


	public ArrayList<CellarObject> loadRelatedCellarItems(Context context, ReviewObject reviewObject){

		ArrayList<CellarObject> loadedItems = new ArrayList<>();

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();

		//TODO: THIS IS THE BARE MINIMUM
		String[] returnColumns = new String[] {"_id", "bottles"};
		String whereClause = "vineyard = ? AND varietal = ? AND vintage = ?";
		String[] whereValues = new String[] {reviewObject.getVineyard(), reviewObject.getVarietal(), reviewObject.getVintage()};

		cursor = db.query("localcellar",
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

					CellarObject cellarObjectToInsert = new CellarObject();

					cellarObjectToInsert.setSqlID(cursor.getInt(0));
					cellarObjectToInsert.setBottleCount(cursor.getInt(1));

					loadedItems.add(cellarObjectToInsert);

					cursor.moveToNext();

				} catch (Exception e) {}

			}

		}

		cursor.close();
		db.close();

		return loadedItems;
	}


	public ArrayList<ReviewObject> loadUnsharedReviews(Context context){
		ArrayList<ReviewObject> loadedReviews = new ArrayList<ReviewObject>();

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		cursor = db.rawQuery("SELECT _id, vineyard, varietal, vintage, rating, origin, price, pairing, color, taste, smell, uploaded, shared, name FROM localreviews WHERE shared != ?;", new String[] {"2"});
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


	public void addPopularFavorite(Context context, int favID){

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();

		databaseValues.put("fav_id", favID);

		db.insert("popularfavorites", "", databaseValues);
		databaseValues.clear();

		db.close();

	}


	public void deletePopularFavorite(Context context, int favID){

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();


		SQLiteStatement  deleteStatement = db.compileStatement("DELETE FROM popularfavorites " +
				"WHERE fav_id = ? ");

		deleteStatement.bindDouble(1, favID);

		deleteStatement.execute();

		db.close();

	}


	public List<Integer> loadPopularFavorites(Context context){
		List<Integer> popularFavorites = new ArrayList<>();

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		cursor = db.rawQuery("SELECT fav_id FROM popularfavorites;", null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			for(int i = 1; i <= cursor.getCount(); i++) {

				try {

					popularFavorites.add(cursor.getInt(0));

					cursor.moveToNext();

				} catch (Exception e) {}

			}

		}

		cursor.close();
		db.close();

		return popularFavorites;
	}


	public boolean userHasReviewedPopularWine(Context context, CoreReviewObject coreReviewObject){

		boolean hasReviewed = false;

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		String[] returnColumns = new String[] {"_id", "vineyard", "varietal", "vintage", "rating", "origin", "price", "pairing", "color", "taste", "smell", "uploaded", "shared", "name"};
		String whereClause = "vineyard = ? AND varietal = ? AND vintage = ? AND origin = ?";
		String[] whereValues = new String[] {coreReviewObject.getVineyard(),
				coreReviewObject.getVarietal(), coreReviewObject.getVintage(), coreReviewObject.getOrigin()};

		cursor = db.query("localreviews",
				returnColumns,
				whereClause,
				whereValues,
				null,
				null,
				null);


		hasReviewed = cursor.moveToFirst();

		cursor.close();
		db.close();

		return hasReviewed;
	}





/*
	public void saveDriveConsent (Context context, boolean consent) {

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();

		databaseValues.put("categories", "drivepermission");
		databaseValues.put("setting", String.valueOf(consent));


		db.insert("preferences", "", databaseValues);
		databaseValues.clear();


		db.close();

	}

	public int loadDriveConsent (Context context) {
		int driveConsent = 0;

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		cursor = db.rawQuery("SELECT categories, setting FROM preferences WHERE categories = 'drivepermission';", null);


		if (cursor.getCount() > 0) {

			try {

				cursor.moveToFirst();
				if(cursor.getString(1).equals("true")) {
					driveConsent = 1;
				}else {
					driveConsent = 0;
				}

			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
			}

		} else {
			driveConsent = 2;
		}


		cursor.close();
		db.close();



		return driveConsent;
	}

	public void editDriveConsent (Context context, boolean newConsent) {

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();

		SQLiteStatement insertStatement = db.compileStatement("UPDATE preferences SET setting = ? WHERE categories = ?");
		insertStatement.bindString(1, String.valueOf(newConsent));
		insertStatement.bindString(2, "drivepermission");
		insertStatement.executeInsert();

		db.close();
	}

	public void saveAccount (Context context, String account) {

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();

		databaseValues.put("categories", "account");
		databaseValues.put("setting", account);


		db.insert("preferences", "", databaseValues);
		databaseValues.clear();


		db.close();

	}

	public String loadAccount (Context context) {
		String account = null;

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();
		databaseValues = new ContentValues();
		cursor = db.rawQuery("SELECT categories, setting FROM preferences WHERE categories = 'account';", null);


		if (cursor.getCount() > 0) {

			try {

				cursor.moveToFirst();
				account = cursor.getString(1);

			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
			}

		} else {

			account = "first run";
		}


		cursor.close();
		db.close();




		return account;
	}

	public void editAccount (Context context, String newAccount) {

		dataHandler = new DataHandler(context, "drivetest1_db", null, DATABASE_VERSION_NUMBER);
		db = dataHandler.getWritableDatabase();

		SQLiteStatement insertStatement = db.compileStatement("UPDATE preferences SET setting = ? WHERE categories = ?");
		insertStatement.bindString(1, newAccount);
		insertStatement.bindString(2, "account");
		insertStatement.executeInsert();

		db.close();
	}
*/
}
