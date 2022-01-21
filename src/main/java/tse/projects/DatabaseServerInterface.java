package tse.projects;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DatabaseServerInterface extends Remote {
    String runQuery(String queryStr) throws RemoteException;
}