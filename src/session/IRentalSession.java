package session;

import java.rmi.Remote;

public interface IRentalSession extends Remote {

    Quote createQuote();
    void confirmQuote(ReservationConstraints constraints);

}
