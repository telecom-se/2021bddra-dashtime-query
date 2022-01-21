package tse.projects;

import com.google.gson.Gson;

import java.rmi.Naming;

public class DatabaseServer {
    public static boolean booted = false;
    public static DatabaseServerInterface server;

    public static void InitializeServer() {
        if (DatabaseServer.booted) return;

        try {
            DatabaseServer.server = (DatabaseServerInterface) Naming.lookup("//localhost/AionDB");
            System.out.println("Database successfully connected");

            DatabaseServer.booted = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
