package tse.projects;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tse.projects.utils.Query;

import java.rmi.Naming;

@SpringBootApplication
public class QueryApplication {
    public static void main(String[] args) throws InterruptedException {

        DatabaseServer.boot();

        while (!DatabaseServer.booted) {
            Thread.sleep(5000);
            DatabaseServer.boot();
        }

        SpringApplication.run(QueryApplication.class, args);
    }

}
