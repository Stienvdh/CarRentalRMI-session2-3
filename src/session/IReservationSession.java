package session;

import rental.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReservationSession extends Remote {

    String getClientName() throws RemoteException;
    Quote createQuote(ReservationConstraints constraints, String companyName) throws RemoteException, ReservationException;
    List<Quote> getCurrentQuotes() throws RemoteException;
    List<Reservation> confirmQuotes() throws RemoteException, ReservationException;
    Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;

    CarType getCheapestCarType(Date start, Date end) throws RemoteException;
}
