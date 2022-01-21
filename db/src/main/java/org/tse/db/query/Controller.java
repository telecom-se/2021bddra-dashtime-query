package org.tse.db.query;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import javax.management.ObjectName;


@RestController
@RequestMapping("/api")
public class Controller {

    private Query query = new Query("500");

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/sensors/{sensor_id}/weathers")
    public ResponseEntity<List<Double>> getGuerySerie(@PathVariable("sensor_id") String sensor_id,
                                                      @RequestParam(required = false) String aggregate,
                                                      @RequestParam(required = false) String from,
                                                      @RequestParam(required = false) String to,
                                                      @RequestParam(required = false) String equal
    ) {
        try {

            if (aggregate == null)
                aggregate = "";
            if (from == null)
                from = "0";
            Long x = null;
            if (to == null) {
                x = Long.MAX_VALUE;
                to = x.toString();
            }
            Long equalint = null;
            if (equal != null)
                equalint = Long.parseLong(equal);

            Long toint = Long.parseLong(to);
            Long fromint = Long.parseLong(from);

            Series serie = this.query.getSeriesByID(sensor_id);

            List<Double> values = new ArrayList<Double>();
            List<Double> valueequal = new ArrayList<Double>();

            for (int i = 0; i < serie.getDataList().size(); i++) {
                if (equal == null && serie.getDataList().get(i).getTimeStamp().getTimestamp() < toint && serie.getDataList().get(i).getTimeStamp().getTimestamp() > fromint)
                    values.add(serie.getDataList().get(i).getValue());
                if (equal != null && serie.getDataList().get(i).getTimeStamp().getTimestamp().equals(equalint)) {
                    valueequal.add(serie.getDataList().get(i).getValue());
                }
            }

            if (aggregate.equals("avg") && equal == null) {
                double sum = (double) 0;
                for (Double value : values) {
                    sum = sum + value;
                }
                List<Double> average = List.of(sum / (values.size()));
                return new ResponseEntity<>(average, HttpStatus.OK);
            } else {
                if (equal != null) {
                    return new ResponseEntity<>(valueequal, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(values, HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@GetMapping("/sensors/{sensor_id}/time")
	public ResponseEntity<List<Long>> getQueryTime(@PathVariable("sensor_id") String sensor_id,
			@RequestParam(required = false) String aggregate, @RequestParam(required = false) String from,
			@RequestParam(required = false) String to, @RequestParam(required = false) String equal) {
		try {

			if (aggregate == null)
				aggregate = "";
			if (from == null)
				from = "0";
			Double x = null;
			if (to == null) {
				x = Double.MAX_VALUE;
				to = x.toString();
			}
			Double equalint = null;
			if (equal != null)
				equalint = Double.parseDouble(equal);

			Double toint = Double.parseDouble(to);
			Double fromint = Double.parseDouble(from);

			Series serie = this.query.getSeriesByID(sensor_id);

			List<Long> values = new ArrayList<Long>();
			List<Long> valueequal = new ArrayList<Long>();

			for (int i = 0; i < serie.getDataList().size(); i++) {
				if (equal == null && serie.getDataList().get(i).getValue() < toint
						&& serie.getDataList().get(i).getValue() > fromint)
					values.add(serie.getDataList().get(i).getTimeStamp().getTimestamp());
				if (equal != null && serie.getDataList().get(i).getValue().equals(equalint)) {
					valueequal.add(serie.getDataList().get(i).getTimeStamp().getTimestamp());
				}
			}

			if (aggregate.equals("avg") && equal == null) {
				Long sum = (long) 0;
				for (int i = 0; i < values.size(); i++) {
					sum = sum + values.get(i);
				}
				List<Long> average = Arrays.asList(sum / (values.size()));
				return new ResponseEntity<>(average, HttpStatus.OK);
			}

			else {
				if (equal != null) {
					return new ResponseEntity<>(valueequal, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(values, HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    @GetMapping("/sensors/{sensor_id}")
    public ResponseEntity<ObjectNode> getAllSerie(@PathVariable("sensor_id") String sensor_id) {
        try {
            Series series = this.query.getSeriesByID(sensor_id);

            ObjectNode sensor = this.mapper.createObjectNode();
            sensor.put("id", sensor_id);
            sensor.set("series", series.serialize(this.mapper));

            return new ResponseEntity<>(sensor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/sensors")
    public ResponseEntity<ArrayNode> getAllSerie2() {
        try {
            ArrayNode series = this.query.getAllSeries(this.mapper);

            return new ResponseEntity<>(series, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@PostMapping("/sensors/{id}")
	String postQuerySerie(@RequestBody LinkedList<Data> newDataList, @PathVariable("id") String id) {
		String res = this.query.createSerie(id, newDataList);
		return res;
	}

	@PutMapping("/sensors/{id}")
	String putQuerySerie(@RequestBody LinkedList<Data> newDataList, @PathVariable("id") String id) {
		String res = this.query.addToSerie(id, newDataList);
		return res;
	}
}
