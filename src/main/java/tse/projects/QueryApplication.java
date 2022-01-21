package tse.projects;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tse.projects.utils.Query;

import java.rmi.Naming;

@SpringBootApplication
public class QueryApplication {
    public static void main(String[] args) {
        DatabaseServer.InitializeServer();
        SpringApplication.run(QueryApplication.class, args);

//            Query query = new Query();
//            query.setTable("stock");
//            query.addSelect("id");
//            query.addSelect("symbol_fk");
//            query.addSelect("timestamp");
//            query.addSelect("value");
//            query.addWhere(new Condition("symbol", "=", "AAPL"));
//
//            Gson gson = new Gson();
//
//            String result = server.runQuery(gson.toJson(query));

    }

}
