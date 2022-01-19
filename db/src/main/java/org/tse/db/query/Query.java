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
	
	public Series createSerie(String id, Series serie) {
		collec.addSerie(id, serie);
		return serie;
	}

}
