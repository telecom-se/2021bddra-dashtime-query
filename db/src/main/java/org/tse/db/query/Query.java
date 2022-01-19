package org.tse.db.query;

import java.util.ArrayList;
import java.util.List;

import org.tse.db.loaddatabase.DataGenerator;
import org.tse.db.telecom.Collections;
import org.tse.db.types.Series;
import org.tse.db.types.Timestamp;

public class Query {
	
	private Collections collec = new Collections();

	public Query(String nom) {
		DataGenerator dg = new DataGenerator();
		dg.ReadData(this.collec, nom);
	}


	public List<Series> getAllSeries() {
		int taille = collec.getCollection().size();
		List<Series> series = new ArrayList<Series>();
		for ( String key : collec.getCollection().keySet()) {
			series.add(collec.getSerie(key));
		}
		return series;
	}
	
	public Series getSeriesByID(String id) {
		return collec.getSerie(id);
	}
	
	public Series createSerie(String id, Series serie) {
		collec.addSerie(id, serie);
		return serie;
	}

}
