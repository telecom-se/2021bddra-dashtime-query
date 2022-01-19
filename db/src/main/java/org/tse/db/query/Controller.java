package org.tse.db.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tse.db.loaddatabase.DataGenerator;
import org.tse.db.types.Data;
import org.tse.db.types.Series;


@RestController
@RequestMapping("/api")
public class Controller {
	
	private Query query = new Query("500");

	@GetMapping("/sensors/{sensor_id}/weathers")
	public ResponseEntity<List<Double>> getGuerySerie(@PathVariable("sensor_id") String sensor_id,
			@RequestParam(required=false) String aggregate,
			@RequestParam(required=false) String from,
			@RequestParam(required=false) String to,
			@RequestParam(required=false) String equal
			) {
		try {

			if (aggregate==null)
				aggregate="";
			if (from==null)
				from="0";
			Long x = null;
			if (to==null) {
				x=Long.MAX_VALUE;
				to=x.toString();
			}
			Long equalint = null;
			if (equal!=null)
				equalint =Long.parseLong(equal);
				
			Long toint =Long.parseLong(to);
			Long fromint =Long.parseLong(from);
			
			Series serie = this.query.getSeriesByID(sensor_id);
			
			List<Double> values= new ArrayList<Double>();
			List<Double> valueequal= new ArrayList<Double>();
			
			for (int i=0; i<serie.getDataList().size(); i++) {
				if (equal==null && serie.getDataList().get(i).getTimeStamp().getTimestamp() <toint && serie.getDataList().get(i).getTimeStamp().getTimestamp() >fromint)
					values.add(serie.getDataList().get(i).getValue());
				if(equal!=null && serie.getDataList().get(i).getTimeStamp().getTimestamp().equals(equalint)) {
					valueequal.add(serie.getDataList().get(i).getValue());
				}
			}
						
			if (aggregate.equals("avg") && equal==null) {
				Double sum=(double) 0;
				for (int i=0; i<values.size(); i++) {
					sum=sum+values.get(i);
				}
				List<Double> average=Arrays.asList(sum/(values.size()));
				return new ResponseEntity<>(average, HttpStatus.OK);
			}
			
			else {
				if(equal!=null) {
					return new ResponseEntity<>(valueequal, HttpStatus.OK);					
				}
				else {return new ResponseEntity<>(values, HttpStatus.OK);}			
			}
			
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sensors/{sensor_id}")
	public ResponseEntity<LinkedList<Data>> getAllSerie(@PathVariable("sensor_id") String sensor_id) {
		try {
			LinkedList<Data> serie = this.query.getSeriesByID(sensor_id).getDataList();
		    return new ResponseEntity<>(serie, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// @CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/sensors")
	public ResponseEntity<List<Series>> getAllSerie2() {
		try {
			List<Series> series = this.query.getAllSeries();
			if (series.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		    return new ResponseEntity<>(series, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/sensors")
	void postGuerySerie(@RequestBody Series serie, @RequestParam() String id) {
		this.query.createSerie(id, serie);
	}
}
