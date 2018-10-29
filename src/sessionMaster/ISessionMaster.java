package sessionMaster;

import session.IManagerSession;
import session.IReservationSession;
import session.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISessionMaster extends Remote {

    List<Session> getActiveSessions() throws RemoteException;
    IReservationSession addReservationSession(String sessionid) throws RemoteException;
    IManagerSession addManagerSession(String sessionid) throws RemoteException;
    IReservationSession getReservationSession(String sessionid) throws RemoteException;
    IManagerSession getManagerSession(String sessionid) throws RemoteException;
    void terminateSession(String sessionid) throws RemoteException, IllegalArgumentException;

}
