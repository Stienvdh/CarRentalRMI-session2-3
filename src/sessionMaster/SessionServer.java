package sessionMaster;

import naming.INamingService;
import naming.NamingService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SessionServer {

    public static void main(String[] args) throws RemoteException, Exception {
        System.setSecurityManager(null);

        Registry registry = LocateRegistry.getRegistry();
        INamingService namingService = (INamingService) registry.lookup("naming");
        ISessionMaster sessionMaster = new SessionMaster(namingService);
        ISessionMaster stub =
                (ISessionMaster) UnicastRemoteObject.exportObject(sessionMaster, 0);
        registry.rebind("master", stub);
    }

}
