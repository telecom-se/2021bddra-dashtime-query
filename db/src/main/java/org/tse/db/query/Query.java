package org.tse.db.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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


	public ArrayNode getAllSeries(ObjectMapper mapper) {
		ArrayNode sensors = mapper.createArrayNode();

		for ( String key : collec.getCollection().keySet()) {
			ObjectNode sensor = mapper.createObjectNode();
			sensor.put("id", key);
			sensor.set("series", collec.getSerie(key).serialize(mapper));
			sensors.add(sensor);
		}
		return sensors;
	}
	
	public Series getSeriesByID(String id) {
		return collec.getSerie(id);
	}
	
	public String createSerie(String id, LinkedList<Data> newDataList) {
		if (collec.getSerie(id) != null)
			return "La serie existe deja, utilisez le put pour ajouter des donnees";
		Series serie = new Series();
		serie.setDataList(newDataList);
		collec.addSerie(id, serie);
		return "La serie a ete ajoutees";
	}

	public String addToSerie(String id, LinkedList<Data> newDataList) {
		if (collec.getSerie(id) == null)
			return "La serie n'existe pas";
		Series serie = collec.getSerie(id);
		System.out.println(newDataList.size());
		for (int i = 0; i < newDataList.size(); i++) {
			System.out.println(i);
			serie.addData(newDataList.get(i));
		}
		return "Les données on ete ajoutees";

	}
}
