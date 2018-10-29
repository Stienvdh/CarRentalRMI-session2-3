package sessionMaster;

import session.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISessionMaster extends Remote {

    List<Session> getActiveSessions() throws RemoteException;

}
