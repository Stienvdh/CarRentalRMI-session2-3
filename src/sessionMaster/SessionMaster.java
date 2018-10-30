package sessionMaster;

import naming.INamingService;
import session.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionMaster implements ISessionMaster {

    private Map<String, Session> activeSessions = new HashMap<String,Session>();
    private INamingService namingService;

    private INamingService getNamingService() {return this.namingService; }

    public SessionMaster(INamingService namingService) {
        this.namingService = namingService;
    }

    public Map<String,Session> getActiveSessions() {
        return this.activeSessions;
    }

    public IReservationSession getReservationSession(String sessionid, String clientName) throws RemoteException {
        if (this.getActiveSessions().containsKey(sessionid)) {
            return (IReservationSession) this.getActiveSessions().get(sessionid);
        }
        Registry registry = LocateRegistry.getRegistry();
        IReservationSession session = new ReservationSession(this.getNamingService(), sessionid, clientName);
        IReservationSession stub =
                (IReservationSession) UnicastRemoteObject.exportObject(session, 0);
        registry.rebind(sessionid, stub);
        return stub;
    }

    public IManagerSession getManagerSession(String sessionid) throws RemoteException {
        if (this.getActiveSessions().containsKey(sessionid)) {
            return (IManagerSession) this.getActiveSessions().get(sessionid);
        }
        Registry registry = LocateRegistry.getRegistry();
        IManagerSession session = new ManagerSession(this.getNamingService(), sessionid);
        IManagerSession stub =
                (IManagerSession) UnicastRemoteObject.exportObject(session, 0);
        registry.rebind(sessionid, stub);
        return stub;
    }

    public void terminateSession(String sessionid) throws RemoteException, IllegalArgumentException {
        if (! this.getActiveSessions().containsKey(sessionid)) {
            throw new IllegalArgumentException("Session" + sessionid + "does not exist.");
        }
        this.getActiveSessions().remove(sessionid);
    }

}
