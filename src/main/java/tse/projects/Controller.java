package tse.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tse.projects.utils.Condition;
import tse.projects.utils.Query;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class Controller {
    Gson gson = new Gson();

    @Autowired
    private ObjectMapper mapper;

    @GetMapping(value = "/companies", produces = "application/json")
    public ResponseEntity<String> getCompanies() {
        try {
            Query query = new Query();
            query.setTable("company");
            query.addSelect("*");

            String response = DatabaseServer.server.runQuery(this.gson.toJson(query));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/companies/{symbol}", produces = "application/json")
    public ResponseEntity<String> getCompany(@PathVariable("symbol") String symbol) {
        try {
            Query query = new Query();
            query.setTable("company");
            query.addSelect("*");
            query.addWhere(new Condition("symbol", "=", symbol));
            query.setLimit(1);

            String response = DatabaseServer.server.runQuery(this.gson.toJson(query));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/companies/{symbol}/stocks", produces = "application/json")
    public ResponseEntity<String> getStocks(@PathVariable("symbol") String symbol,
                                            @RequestParam(required = false) Optional<Long> from,
                                            @RequestParam(required = false) Optional<Long> to) {
        try {
            Query query = new Query();
            query.setTable("stock");
            query.addSelect("*");

            query.addWhere(new Condition("symbol", "=", symbol));
            from.ifPresent(f -> query.addWhere(new Condition("timestamp", ">=", f.toString())));
            to.ifPresent(t -> query.addWhere(new Condition("timestamp", "<=", t.toString())));
            query.addWhere(new Condition("symbol", "=", symbol));

            String response = DatabaseServer.server.runQuery(this.gson.toJson(query));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (
                Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
