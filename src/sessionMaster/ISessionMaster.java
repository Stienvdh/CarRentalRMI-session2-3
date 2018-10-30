package sessionMaster;

import session.IManagerSession;
import session.IReservationSession;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ISessionMaster extends Remote {

    Map<String, Object> getActiveSessions() throws RemoteException;
    IReservationSession getReservationSession(String sessionid, String clientName) throws RemoteException;
    IManagerSession getManagerSession(String sessionid) throws RemoteException;
    void terminateSession(String sessionid) throws RemoteException, IllegalArgumentException;

}
