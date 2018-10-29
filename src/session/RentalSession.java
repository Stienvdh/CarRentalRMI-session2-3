package session;

import rental.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RentalSession extends Session implements IRentalSession {

    private String clientName;
    public List<Quote> currentQuotes = new ArrayList<Quote>();

    public RentalSession(INamingService namingService, String sessionId, String clientName) {
        super(namingService, sessionId);
        this.clientName = clientName;
    }

    @Override
    public String getClientName() throws RemoteException {
        return this.clientName;
    }

    public Quote createQuote(ReservationConstraints constraints, String companyName) throws RemoteException, ReservationException {
        ICarRentalCompany carRentalCompany = namingService.getRegisteredCompany(companyName);
        Quote quote = carRentalCompany.createQuote(constraints, getClientName());
        currentQuotes.add(quote);
        return quote;
    }

    public void confirmQuotes() {

    }

}
