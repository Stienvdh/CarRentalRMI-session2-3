package sessionMaster;

import naming.INamingService;
import naming.NamingService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SessionServer {

    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager(null);

        ISessionMaster sessionMaster = new SessionMaster();
        ISessionMaster stub =
                (ISessionMaster) UnicastRemoteObject.exportObject(sessionMaster, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("master", stub);
    }

}
