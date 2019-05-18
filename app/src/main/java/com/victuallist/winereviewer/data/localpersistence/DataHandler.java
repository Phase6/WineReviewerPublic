package com.victuallist.winereviewer.data.localpersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler extends SQLiteOpenHelper {

	public DataHandler (Context context, String dBName, SQLiteDatabase.CursorFactory factory, int version) {
		super (context, dBName, factory, version);
	}
	
	@Override
	public void onCreate (SQLiteDatabase db) {

		String createString2 = "CREATE TABLE IF NOT EXISTS localreviews "
				+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, "
				+ "vineyard TEXT NOT NULL, "
				+ "varietal TEXT NOT NULL,"
				+ "vintage TEXT NOT NULL,"
				+ "rating TEXT NOT NULL,"
				+ "origin TEXT,"
				+ "price TEXT,"
				+ "pairing TEXT,"
				+ "taste TEXT,"
				+ "smell TEXT,"
				+ "color TEXT,"
				+ "uploaded TEXT NOT NULL,"
				+ "shared TEXT NOT NULL);";
		
		db.execSQL(createString2);
		
		String createString3 = "CREATE TABLE IF NOT EXISTS localcellar "
				+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "upc TEXT, "
				+ "name TEXT, "
				+ "vineyard TEXT NOT NULL, "
				+ "varietal TEXT NOT NULL, "
				+ "vintage TEXT NOT NULL, "
				+ "origin TEXT, "
				+ "price TEXT, "
				+ "bottles INTEGER, "
				+ "size TEXT, "
				+ "buydate TEXT, "
				+ "location TEXT, "
				+ "drinkby TEXT, "
				+ "value TEXT, "
				+ "notes TEXT, "
				+ "uploaded TEXT NOT NULL, "
				+ "shared TEXT NOT NULL);";
				
				
		db.execSQL(createString3);


		String createStringPopularFavorites = "CREATE TABLE IF NOT EXISTS popularfavorites "
				+ "( fav_id INTEGER PRIMARY KEY);";

		db.execSQL(createStringPopularFavorites);
		
		
						
	}
	
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if (newVersion == 2) {

			String addNameColumn = "ALTER TABLE localreviews ADD COLUMN name TEXT";
			db.execSQL(addNameColumn);
			
			String createString3 = "CREATE TABLE IF NOT EXISTS localcellar "
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "upc TEXT, "
					+ "name TEXT, "
					+ "vineyard TEXT NOT NULL, "
					+ "varietal TEXT NOT NULL, "
					+ "vintage TEXT NOT NULL, "
					+ "origin TEXT, "
					+ "price TEXT, "
					+ "bottles INTEGER, "
					+ "size TEXT, "
					+ "buydate TEXT, "
					+ "location TEXT, "
					+ "drinkby TEXT, "
					+ "value TEXT, "
					+ "notes TEXT, "
					+ "uploaded TEXT NOT NULL, "
					+ "shared TEXT NOT NULL);";
					
					
			db.execSQL(createString3);
			
		}else if(newVersion == 4){

			String dropPreferencesTableString = "DROP TABLE IF EXISTS preferences;";
			db.execSQL(dropPreferencesTableString);

			String createStringPopularFavorites = "CREATE TABLE IF NOT EXISTS popularfavorites "
					+ "( fav_id INTEGER PRIMARY KEY);";

			db.execSQL(createStringPopularFavorites);

		}
		
		

/*		

		
		String dropString2 = "DROP TABLE IF EXISTS localreviews;";
		db.execSQL(dropString2);
*/		
	}
	
	

}
