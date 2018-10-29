package session;

import rental.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRentalSession extends Remote {

    String getClientName() throws RemoteException;
    Quote createQuote(ReservationConstraints constraints);
    void confirmQuote();

}
