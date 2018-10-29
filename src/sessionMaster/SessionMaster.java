package sessionMaster;

import naming.INamingService;
import session.IManagerSession;
import session.IReservationSession;
import session.Session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SessionMaster implements ISessionMaster {

    private List<Session> activeSessions = new ArrayList<Session>();
    private INamingService namingService;

    public SessionMaster(INamingService namingService) {
        this.namingService = namingService;
    }

    public List<Session> getActiveSessions() {
        return this.activeSessions;
    }

    public IReservationSession getReservationSession(String sessionid) throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public IManagerSession getManagerSession(String sessionid) throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public void terminateSession(String sessionid) throws RemoteException, IllegalArgumentException {
        //TODO
    }

}
