package naming;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NamingServer {

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(null);

        INamingService namingService = new NamingService();
        INamingService stub =
                (INamingService) UnicastRemoteObject.exportObject(namingService, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("naming", stub);
    }

}
